package wrimsv2.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.Set;

import org.antlr.runtime.RecognitionException;
import vista.db.dss.DSSDataWriter;
import vista.db.dss.DSSUtil;
import wrimsv2.commondata.wresldata.Alias;
import wrimsv2.commondata.wresldata.Dvar;
import wrimsv2.commondata.wresldata.External;
import wrimsv2.commondata.wresldata.ModelDataSet;
import wrimsv2.commondata.wresldata.Param;
import wrimsv2.commondata.wresldata.StudyDataSet;
import wrimsv2.commondata.wresldata.Svar;
import wrimsv2.commondata.wresldata.Timeseries;
import wrimsv2.config.ConfigUtils;
import wrimsv2.evaluator.AssignPastCycleVariable;
import wrimsv2.evaluator.DataTimeSeries;
import wrimsv2.evaluator.DssDataSetFixLength;
import wrimsv2.evaluator.DssOperation;
import wrimsv2.evaluator.PreEvaluator;
import wrimsv2.evaluator.TimeOperation;
import wrimsv2.evaluator.ValueEvaluatorParser;
import wrimsv2.external.LoadAllDll;
import wrimsv2.ilp.ILP;
import wrimsv2.solver.LPSolveSolver;
import wrimsv2.solver.XASolver;
import wrimsv2.solver.initialXALog;
import wrimsv2.solver.initialXASolver;
import wrimsv2.wreslparser.elements.StudyUtils;

import lpsolve.*;

public class ControllerBatch {
	
	
	public ControllerBatch(String[] args) {
		ControlData.writeInitToDVOutput=false;
		long startTimeInMillis = Calendar.getInstance().getTimeInMillis();
		try {
			processArgs(args);
			StudyDataSet sds = parse(); 
			
			if (StudyUtils.total_errors+Error.getTotalError()==0 && !StudyUtils.compileOnly){
				new PreEvaluator(sds);
				new PreRunModel(sds);
				generateStudyFile();
				runModel(sds);
			} 
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTimeInMillis = Calendar.getInstance().getTimeInMillis();
		int runPeriod=(int) (endTimeInMillis-startTimeInMillis);
		System.out.println("=================Run Time is "+runPeriod/60000+"min"+Math.round((runPeriod/60000.0-runPeriod/60000)*60)+"sec====");
		if (!System.getenv().containsKey("WRIMS_DEBUG")) System.exit(0);
	}

	public void processArgs(String[] args){
	
		if(args[0].startsWith("-")) {
			ConfigUtils.loadArgs(args);
		} else {		
			setControlData(args);
		}	
		
	}
	
	public void setControlData(String[] args){
		FilePaths.groundwaterDir=args[0];
        FilePaths.setMainFilePaths(args[1]);
        FilePaths.setSvarDssPaths(args[2]);
        FilePaths.setInitDssPaths(args[3]);
        FilePaths.setDvarDssPaths(args[4]);
		ControlData.svDvPartF=args[5];
		ControlData.initPartF=args[6];
		ControlData.partA = args[7];
		ControlData.defaultTimeStep = args[8];
		ControlData.startYear=Integer.parseInt(args[9]);
		ControlData.startMonth=Integer.parseInt(args[10]);
		ControlData.startDay=Integer.parseInt(args[11]);
		ControlData.endYear=Integer.parseInt(args[12]);
		ControlData.endMonth=Integer.parseInt(args[13]);
		ControlData.endDay=Integer.parseInt(args[14]);
		ControlData.solverName=args[15];
		FilePaths.csvFolderName = args[16];
		ControlData.currYear=ControlData.startYear;
		ControlData.currMonth=ControlData.startMonth;
		ControlData.currDay=ControlData.startDay;
        ControlData.writeDssStartYear=ControlData.startYear;
        ControlData.writeDssStartMonth=ControlData.startMonth;
        ControlData.writeDssStartDay=ControlData.startDay;

	}
	
	public void generateStudyFile(){
		String outPath=System.getenv("Java_Bin")+"study.sty";
		FileWriter outstream;
		try {
			outstream = new FileWriter(outPath);
			BufferedWriter out = new BufferedWriter(outstream);
			out.write("Study File: Generated by WRIMS. Do Not Edit!\n");
			out.write("Study Name\n");
			out.write("Author\n");
			out.write("Time\n");
			out.write("Note\n");
			out.write("Version\n");
			out.write(FilePaths.groundwaterDir+"\n");
			out.write("StudyFileFullPath\n");
			out.write(FilePaths.fullMainPath+"\n");
			out.write(FilePaths.fullSvarDssPath+"\n");
			out.write(FilePaths.fullDvarDssPath+"\n");
			out.write(FilePaths.fullInitDssPath+"\n");
			out.write(ControlData.defaultTimeStep+"\n");
			out.write(VariableTimeStep.getTotalTimeStep(ControlData.defaultTimeStep)+"\n");
			out.write(ControlData.startDay+"\n");
			out.write(ControlData.startMonth+"\n");
			out.write(ControlData.startYear+"\n");
			out.write("SLP\n");
			out.write("CycleNumber\n");
			out.write("FALSE\n");
			out.write("NONE\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("FALSE\n");
			out.write("CALSIM\n");
			out.write(ControlData.initPartF+"\n");
			out.write(ControlData.svDvPartF+"\n");
			out.write("FALSE\n");
			out.write("TRUE\n");
			out.write("FALSE\n");
			out.write("SINGLE\n");
			out.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public StudyDataSet parse()throws RecognitionException, IOException{		
		
		if (Error.error_config.size()>0){
			
			System.out.println("============================================");
			System.out.println("Total errors in the config file: "+Error.error_config.size());
			System.out.println("============================================");

			return null;
			
		} else if(StudyUtils.loadParserData) {

			return StudyUtils.loadObject(StudyUtils.parserDataPath);
		
		} else if(StudyUtils.compileOnly) {
			
			return StudyUtils.compileStudy(FilePaths.fullMainPath);
		
		} else {
			
			return StudyUtils.checkStudy(FilePaths.fullMainPath);
		
		}
	}
	
	
	public void runModel(StudyDataSet sds){
		System.out.println("==============Run Study Start============");
		if (ILP.logging){
			runModelILP(sds);
	    } else if (ControlData.solverName.equalsIgnoreCase("XA") || ControlData.solverName.equalsIgnoreCase("XALOG") ){
			runModelXA(sds);
		} else {
			Error.addConfigError("Solver name not recognized: "+ControlData.solverName);
		}
//		}else if (ControlData.solverName.equalsIgnoreCase("LPSolve")){
//			try {
//				runModeLPSolve(sds);
//			} catch (LpSolveException e) {
//				e.printStackTrace();
//			}
//		}
		if (Error.getTotalError()>0){
			System.out.println("=================Run ends with errors====");
		} else {
			System.out.println("=================Run ends!================");
		}
	}
	
	public void runModelXA(StudyDataSet sds){
		ArrayList<String> modelList=sds.getModelList();
		Map<String, ModelDataSet> modelDataSetMap=sds.getModelDataSetMap();		
		
		new initialXASolver();
		ArrayList<ValueEvaluatorParser> modelConditionParsers=sds.getModelConditionParsers();
		boolean noError=true;
		VariableTimeStep.initialCurrTimeStep(modelList);
		VariableTimeStep.initialCycleStartDate();
		VariableTimeStep.setCycleEndDate(sds);
		while (VariableTimeStep.checkEndDate(ControlData.cycleStartDay, ControlData.cycleStartMonth, ControlData.cycleStartYear, ControlData.endDay, ControlData.endMonth, ControlData.endYear)<=0 && noError){
			if (ControlData.solverName.equalsIgnoreCase("XALOG")) new initialXALog();
			ClearValue.clearValues(modelList, modelDataSetMap);
			sds.clearVarTimeArrayCycleValueMap();
			int i=0;
			while (i<modelList.size()  && noError){  
				
				String model=modelList.get(i);
				ModelDataSet mds=modelDataSetMap.get(model);
				ControlData.currModelDataSet=mds;
				ControlData.currCycleName=model;
				ControlData.currCycleIndex=i;
				VariableTimeStep.setCycleTimeStep(sds);
				VariableTimeStep.setCurrentDate(sds, ControlData.cycleStartDay, ControlData.cycleStartMonth, ControlData.cycleStartYear);
				
				while(VariableTimeStep.checkEndDate(ControlData.currDay, ControlData.currMonth, ControlData.currYear, ControlData.cycleEndDay, ControlData.cycleEndMonth, ControlData.cycleEndYear)<0 && noError){
					ValueEvaluatorParser modelCondition=modelConditionParsers.get(i);
					boolean condition=false;
					try{
						modelCondition.evaluator();
						condition=modelCondition.evalCondition;
					}catch (Exception e){
						Error.addEvaluationError("Model condition evaluation has error.");
						condition=false;
					}
					modelCondition.reset();
				
					if (condition){
						ClearValue.clearCycleLoopValue(modelList, modelDataSetMap);
						ControlData.currSvMap=mds.svMap;
						ControlData.currSvFutMap=mds.svFutMap;
						ControlData.currDvMap=mds.dvMap;
						ControlData.currDvSlackSurplusMap=mds.dvSlackSurplusMap;
						ControlData.currAliasMap=mds.asMap;
						ControlData.currGoalMap=mds.gMap;
						ControlData.currTsMap=mds.tsMap;
						ControlData.isPostProcessing=false;
						mds.processModel();
						if (Error.error_evaluation.size()>=1){
							Error.writeEvaluationErrorFile("evaluation_error.txt");
							noError=false;
						}
						new XASolver();
						if (ControlData.showRunTimeMessage) System.out.println("Solving Done.");
						if (Error.error_solving.size()<1){
							ControlData.isPostProcessing=true;
							mds.processAlias();
							if (ControlData.showRunTimeMessage) System.out.println("Assign Alias Done.");
						}else{
							Error.writeSolvingErrorFile("solving_error.txt");
							noError=false;
						}
						System.out.println("Cycle "+(i+1)+" in "+ControlData.currYear+"/"+ControlData.currMonth+"/"+ControlData.currDay+" Done.");
						if (Error.error_evaluation.size()>=1) noError=false;
						//if (ControlData.currTimeStep==0 && ControlData.currCycleIndex==2) new RCCComparison();
						ControlData.currTimeStep.set(ControlData.currCycleIndex, ControlData.currTimeStep.get(ControlData.currCycleIndex)+1);
						if (ControlData.timeStep.equals("1MON")){
							VariableTimeStep.currTimeAddOneMonth();
						}else{
							VariableTimeStep.currTimeAddOneDay();
						}
					}else{
						new AssignPastCycleVariable();
						ControlData.currTimeStep.set(ControlData.currCycleIndex, ControlData.currTimeStep.get(ControlData.currCycleIndex)+1);
						if (ControlData.timeStep.equals("1MON")){
							VariableTimeStep.currTimeAddOneMonth();
						}else{
							VariableTimeStep.currTimeAddOneDay();
						}	
					}
				}
				i=i+1;
			}
			VariableTimeStep.setCycleStartDate(ControlData.cycleEndDay, ControlData.cycleEndMonth, ControlData.cycleEndYear);
			VariableTimeStep.setCycleEndDate(sds);
		}
		ControlData.xasolver.close();
		if (ControlData.writeInitToDVOutput){
			DssOperation.writeInitDvarAliasToDSS();
		}
		DssOperation.writeDVAliasToDSS();
		ControlData.writer.closeDSSFile();
	}

	public void writeOutputDssEveryTenYears(){
		if (ControlData.currMonth==12 && ControlData.currYear%10==0){
			if (ControlData.timeStep.equals("1MON")){
				DssOperation.writeDVAliasToDSS();
			}else if(ControlData.timeStep.equals("1DAY") && ControlData.currDay==31){
				DssOperation.writeDVAliasToDSS();
			}
		}
	}
	
	public static void main(String[] args){
		new ControllerBatch(args);
	}

	public void runModelILP(StudyDataSet sds){
		
		ILP.initializeIlp();
		
		ArrayList<String> modelList=sds.getModelList();
		Map<String, ModelDataSet> modelDataSetMap=sds.getModelDataSetMap();		
		
		if (ControlData.solverName.equalsIgnoreCase("lpsolve")) {
			ControlData.solverType = Param.SOLVER_LPSOLVE;
			// initiate lpsolve
		} else if (ControlData.solverName.toLowerCase().contains("xa")) {
			ControlData.solverType = Param.SOLVER_XA; //default
			new initialXASolver();
		} else {
			Error.addConfigError("Solver name not recognized: "+ControlData.solverName);
		}
		
		ArrayList<ValueEvaluatorParser> modelConditionParsers=sds.getModelConditionParsers();
		boolean noError=true;
		VariableTimeStep.initialCurrTimeStep(modelList);
		VariableTimeStep.initialCycleStartDate();
		VariableTimeStep.setCycleEndDate(sds);
		while (VariableTimeStep.checkEndDate(ControlData.cycleStartDay, ControlData.cycleStartMonth, ControlData.cycleStartYear, ControlData.endDay, ControlData.endMonth, ControlData.endYear)<=0 && noError){
			if (ControlData.solverType == Param.SOLVER_XA && ControlData.solverName.toLowerCase().contains("xalog")) new initialXALog();
			ClearValue.clearValues(modelList, modelDataSetMap);
			sds.clearVarTimeArrayCycleValueMap();
			int i=0;
			while (i<modelList.size()  && noError){  
				
				String model=modelList.get(i);
				ModelDataSet mds=modelDataSetMap.get(model);
				ControlData.currModelDataSet=mds;
				ControlData.currCycleName=model;
				ControlData.currCycleIndex=i;
				VariableTimeStep.setCycleTimeStep(sds);
				VariableTimeStep.setCurrentDate(sds, ControlData.cycleStartDay, ControlData.cycleStartMonth, ControlData.cycleStartYear);
				
				while(VariableTimeStep.checkEndDate(ControlData.currDay, ControlData.currMonth, ControlData.currYear, ControlData.cycleEndDay, ControlData.cycleEndMonth, ControlData.cycleEndYear)<0 && noError){
					ValueEvaluatorParser modelCondition=modelConditionParsers.get(i);
					boolean condition=false;
					try{
						modelCondition.evaluator();
						condition=modelCondition.evalCondition;
					}catch (Exception e){
						Error.addEvaluationError("Model condition evaluation has error.");
						condition=false;
					}
					modelCondition.reset();
				
					if (condition){
						ClearValue.clearCycleLoopValue(modelList, modelDataSetMap);
						ControlData.currSvMap=mds.svMap;
						ControlData.currSvFutMap=mds.svFutMap;
						ControlData.currDvMap=mds.dvMap;
						ControlData.currDvSlackSurplusMap=mds.dvSlackSurplusMap;
						ControlData.currAliasMap=mds.asMap;
						ControlData.currGoalMap=mds.gMap;
						ControlData.currTsMap=mds.tsMap;
						ControlData.isPostProcessing=false;
						mds.processModel();
					
						if (ILP.logging) {
							ILP.setIlpFile();
							ILP.writeIlp();
							if (ILP.loggingVariableValue) ILP.writeSvarValue();
						}
					
						if (Error.error_evaluation.size()>=1){
							Error.writeEvaluationErrorFile("evaluation_error.txt");
							noError=false;
						}
					
						// choose solver to solve. TODO: this is not efficient. need to be done outside ILP
				        if (ControlData.solverType == Param.SOLVER_LPSOLVE.intValue()) {
				            LPSolveSolver.setLP(ILP.lpSolveFilePath);
				            LPSolveSolver.solve();
				            if (Error.error_solving.size()<1) {
				            	if (ILP.logging) {
				            		ILP.writeObjValue_LPSOLVE();
				            		if (ILP.loggingVariableValue) ILP.writeDvarValue_LPSOLVE();
				            	}
				            }
				        } else {
							new XASolver(); // default
							if (ILP.logging) {
								ILP.writeObjValue_XA();
								if (ILP.loggingVariableValue) ILP.writeDvarValue_XA();
							}
				        }



						ILP.closeIlpFile();

						if (ControlData.showRunTimeMessage) System.out.println("Solving Done.");
						if (Error.error_solving.size()<1){
							ControlData.isPostProcessing=true;
							mds.processAlias();
							if (ControlData.showRunTimeMessage) System.out.println("Assign Alias Done.");
						}else{
							Error.writeSolvingErrorFile("solving_error.txt");
							noError=false;
						}
						System.out.println("Cycle "+(i+1)+" in "+ControlData.currYear+"/"+ControlData.currMonth+"/"+ControlData.currDay+" Done.");
						if (Error.error_evaluation.size()>=1) noError=false;
						//if (ControlData.currTimeStep==0 && ControlData.currCycleIndex==2) new RCCComparison();
						ControlData.currTimeStep.set(ControlData.currCycleIndex, ControlData.currTimeStep.get(ControlData.currCycleIndex)+1);
						if (ControlData.timeStep.equals("1MON")){
							VariableTimeStep.currTimeAddOneMonth();
						}else{
							VariableTimeStep.currTimeAddOneDay();
						}
					}else{
						new AssignPastCycleVariable();
						ControlData.currTimeStep.set(ControlData.currCycleIndex, ControlData.currTimeStep.get(ControlData.currCycleIndex)+1);
						if (ControlData.timeStep.equals("1MON")){
							VariableTimeStep.currTimeAddOneMonth();
						}else{
							VariableTimeStep.currTimeAddOneDay();
						}	
					}
				}
				i=i+1;
			}
			VariableTimeStep.setCycleStartDate(ControlData.cycleEndDay, ControlData.cycleEndMonth, ControlData.cycleEndYear);
			VariableTimeStep.setCycleEndDate(sds);
		}
		if (ControlData.solverType == Param.SOLVER_LPSOLVE) {
			//ControlData.lpssolver.deleteLp();
		} else {
			ControlData.xasolver.close();
		}
		if (ControlData.writeInitToDVOutput){
		DssOperation.writeInitDvarAliasToDSS();
		}
		DssOperation.writeDVAliasToDSS();
		ControlData.writer.closeDSSFile();
	}
}