package wrimsv2.components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

import org.antlr.runtime.RecognitionException;

import wrimsv2.commondata.solverdata.SolverData;
import wrimsv2.commondata.wresldata.ModelDataSet;
import wrimsv2.commondata.wresldata.StudyDataSet;
import wrimsv2.evaluator.AssignPastCycleVariable;
import wrimsv2.evaluator.DssOperation;
import wrimsv2.evaluator.PreEvaluator;
import wrimsv2.evaluator.ValueEvaluatorParser;
import wrimsv2.hdf5.HDF5Writer;
import wrimsv2.ilp.ILP;
import wrimsv2.solver.CbcSolver;
import wrimsv2.solver.XASolver;
import wrimsv2.solver.SetXALog;
import wrimsv2.solver.InitialXASolver;
import wrimsv2.tools.General;
import wrimsv2.wreslparser.elements.StudyUtils;
import wrimsv2.wreslplus.elements.procedures.ErrorCheck;

public class ControllerSG {
	
	public ControllerSG() {
		long startTimeInMillis = Calendar.getInstance().getTimeInMillis();
		setControlData();
		//generateStudyFile();
		try {
			StudyDataSet sds = parse();
			if (StudyUtils.total_errors==0){
				if (!StudyUtils.loadParserData && !FilePaths.fullMainPath.endsWith(".par")){
					StudyUtils.writeObj(sds, FilePaths.mainDirectory+File.separator+StudyUtils.configFileName+".par");
				}
				new PreEvaluator(sds);
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
	}
	
	public ControllerSG(String[] args) {
		long startTimeInMillis = Calendar.getInstance().getTimeInMillis();
		setControlData(args);
		//generateStudyFile();
		try {
			StudyDataSet sds = parse();
			if (StudyUtils.total_errors==0){
				if (!StudyUtils.loadParserData && !FilePaths.fullMainPath.endsWith(".par")){
					StudyUtils.writeObj(sds, FilePaths.mainDirectory+File.separator+StudyUtils.configFileName+".par");
				}
				new PreEvaluator(sds);
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
		System.exit(0);
	}
	
	public void setControlData(){
		FilePaths.groundwaterDir="D:\\cvwrsm\\trunk\\calsim30\\calsim30_bo\\common\\CVGroundwater\\Data\\";
		FilePaths.setMainFilePaths("D:\\cvwrsm\\trunk\\calsim30\\calsim30_bo\\CONV\\Run\\mainCONV_30.wresl");
		FilePaths.setSvarFilePaths("D:\\cvwrsm\\trunk\\calsim30\\calsim30_bo\\common\\DSS\\CalSim30_06_SV.dss");
        FilePaths.setInitFilePaths("D:\\cvwrsm\\trunk\\calsim30\\calsim30_bo\\common\\DSS\\CalSim30_06Init.dss");   
        FilePaths.setDvarFilePaths("D:\\cvwrsm\\trunk\\calsim30\\calsim30_bo\\CONV\\DSS\\TestWRIMSV2DV.dss");
		ControlData cd=new ControlData();
		cd.svDvPartF="CALSIM30_06";
		cd.initPartF="CALSIM30_06";
		cd.partA = "CALSIM";
		cd.defaultTimeStep="1MON";
		cd.startYear=1921;
		cd.startMonth=10;
		cd.startDay=31;
		cd.endYear=2003;
		cd.endMonth=9;
		cd.endDay=30;
        cd.solverName="XA";
        FilePaths.csvFolderName="csv";
		cd.currYear=cd.startYear;
		cd.currMonth=cd.startMonth;
		cd.currDay=cd.startDay;        
	}
	
	public void setControlData(String[] args){
		FilePaths.groundwaterDir=args[0];
        FilePaths.setMainFilePaths(args[1]);
        FilePaths.setSvarFilePaths(args[2]);
        FilePaths.setInitFilePaths(args[3]);
        FilePaths.setDvarFilePaths(args[4]);
		ControlData cd=new ControlData();
		cd.svDvPartF=args[5];
		cd.initPartF=args[6];
		cd.partA = args[7];
		cd.defaultTimeStep = args[8];
		cd.startYear=Integer.parseInt(args[9]);
		cd.startMonth=Integer.parseInt(args[10]);
		cd.startDay=Integer.parseInt(args[11]);
		cd.endYear=Integer.parseInt(args[12]);
		cd.endMonth=Integer.parseInt(args[13]);
		cd.endDay=Integer.parseInt(args[14]);
		cd.solverName=args[15];
		FilePaths.csvFolderName = args[16];
		cd.currYear=cd.startYear;
		cd.currMonth=cd.startMonth;
		cd.currDay=cd.startDay;
		if (args.length>17){
			StudyUtils.useWreslPlus = false;
			if (args[17].equalsIgnoreCase("wreslplus")){
				StudyUtils.useWreslPlus = true;
			}
		}
	}
	
	public void generateStudyFile(){
		String outPath="study.sty";
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
			out.write(FilePaths.fullSvarFilePath+"\n");
			out.write(FilePaths.fullDvarDssPath+"\n");
			out.write(FilePaths.fullInitFilePath+"\n");
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
			out.write("NODEBUG\n");
			out.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public StudyDataSet parse()throws RecognitionException, IOException{		
		if(StudyUtils.loadParserData) {
			return StudyUtils.loadObject(StudyUtils.parserDataPath);
		}else{
			return StudyUtils.checkStudy(FilePaths.fullMainPath);
		}
	}
	
	public void runModel(StudyDataSet sds){
		System.out.println("=============Prepare Run Study===========");
		new PreRunModel(sds);
		System.out.println("==============Run Study Start============");
		if (ControlData.solverName.equalsIgnoreCase("XA") || ControlData.solverName.equalsIgnoreCase("XALOG") ){
			runModelXA(sds);
		} else if (ControlData.solverName.equalsIgnoreCase("Cbc")){
			runModelCbc(sds);
		}
		System.out.println("=================Run ends!================");
	}
	
	public void runModelXA(StudyDataSet sds){
		ArrayList<String> modelList=sds.getModelList();
		Map<String, ModelDataSet> modelDataSetMap=sds.getModelDataSetMap();		
		
		new InitialXASolver();
		if (Error.getTotalError()>0){
			System.out.println("Model run exits due to error.");
			System.exit(1);
		}
		ArrayList<ValueEvaluatorParser> modelConditionParsers=sds.getModelConditionParsers();
		boolean noError=true;
		VariableTimeStep.initialCurrTimeStep(modelList);
		VariableTimeStep.initialCycleStartDate();
		VariableTimeStep.setCycleEndDate(sds);
		while (VariableTimeStep.checkEndDate(ControlData.cycleStartDay, ControlData.cycleStartMonth, ControlData.cycleStartYear, ControlData.endDay, ControlData.endMonth, ControlData.endYear)<=0 && noError){
			if (ControlData.solverName.equalsIgnoreCase("XALOG")) SetXALog.enableXALog();
			ClearValue.clearValues(modelList, modelDataSetMap);
			sds.clearVarTimeArrayCycleValueMap();
			sds.clearVarCycleIndexByTimeStep();
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
							Error.writeEvaluationErrorFile("Error_evaluation.txt");
							Error.writeErrorLog();
							noError=false;
						}
						new XASolver();
						
						// check monitored dvar list. they are slack and surplus generated automatically 
						// from the weight group deviation penalty
						// give error if they are not zero or greater than a small tolerance.
						noError = !ErrorCheck.checkDeviationSlackSurplus(mds.deviationSlackSurplus_toleranceMap, mds.dvMap);
						
						if (ControlData.showRunTimeMessage) System.out.println("Solving Done.");
						if (Error.error_solving.size()<1){
							ControlData.isPostProcessing=true;
							mds.processAlias();
							if (ControlData.showRunTimeMessage) System.out.println("Assign Alias Done.");
						}else{
							Error.writeSolvingErrorFile("Error_solving.txt");
							Error.writeErrorLog();
							noError=false;
						}
						int cycleI=i+1;
						String strCycleI=cycleI+"";
						boolean isSelectedCycleOutput=General.isSelectedCycleOutput(strCycleI);
						if (ControlData.outputType==1){
							if (ControlData.isOutputCycle && isSelectedCycleOutput){
								HDF5Writer.writeOneCycleSv(mds, cycleI);
							}
						}
						System.out.println("Cycle "+cycleI+" in "+ControlData.currYear+"/"+ControlData.currMonth+"/"+ControlData.currDay+" Done. ("+model+")");
						if (Error.error_evaluation.size()>=1) noError=false;
						//if (ControlData.currTimeStep==0 && ControlData.currCycleIndex==2) new RCCComparison();
						ControlData.currTimeStep.set(ControlData.currCycleIndex, ControlData.currTimeStep.get(ControlData.currCycleIndex)+1);
						if (ControlData.timeStep.equals("1MON")){
							VariableTimeStep.currTimeAddOneMonth();
						}else{
							VariableTimeStep.currTimeAddOneDay();
						}
					}else{
						int cycleI=i+1;
						String strCycleI=cycleI+"";
						boolean isSelectedCycleOutput=General.isSelectedCycleOutput(strCycleI);
						if (ControlData.outputType==1){
							if (ControlData.isOutputCycle && isSelectedCycleOutput){
								HDF5Writer.skipOneCycle(mds, cycleI);
							}
						}
						System.out.println("Cycle "+cycleI+" in "+ControlData.currYear+"/"+ControlData.currMonth+"/"+ControlData.currDay+" skipped. ("+model+")");
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
		if (ControlData.outputType==1){
			HDF5Writer.createDvarAliasLookup();
			HDF5Writer.writeTimestepData();
			HDF5Writer.writeCyclesDvAlias();
			HDF5Writer.closeDataStructure();
		}else{
			if (ControlData.writeInitToDVOutput){
				DssOperation.writeInitDvarAliasToDSS();
			}
			DssOperation.writeDVAliasToDSS();
			ControlData.dvDss.close();
		}
	}
	
	public void runModelCbc(StudyDataSet sds){
		
		ArrayList<String> modelList=sds.getModelList();
		Map<String, ModelDataSet> modelDataSetMap=sds.getModelDataSetMap();		
		
		CbcSolver.init(false, sds); 	if (ControlData.cbc_debug_routeXA || ControlData.cbc_debug_routeCbc) {new InitialXASolver();}
		if (Error.getTotalError()>0){
			System.out.println("Model run exits due to error.");
			System.exit(1);
		}
		ArrayList<ValueEvaluatorParser> modelConditionParsers=sds.getModelConditionParsers();
		boolean noError=true;
		VariableTimeStep.initialCurrTimeStep(modelList);
		VariableTimeStep.initialCycleStartDate();
		VariableTimeStep.setCycleEndDate(sds);
		while (VariableTimeStep.checkEndDate(ControlData.cycleStartDay, ControlData.cycleStartMonth, ControlData.cycleStartYear, ControlData.endDay, ControlData.endMonth, ControlData.endYear)<=0 && noError){
					
			ClearValue.clearValues(modelList, modelDataSetMap);
			sds.clearVarTimeArrayCycleValueMap();
			sds.clearVarCycleIndexByTimeStep();
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
							Error.writeEvaluationErrorFile("Error_evaluation.txt");
							Error.writeErrorLog();
							noError=false;
						}
						
						HashSet<String> originalDvarKeys=null;
						
						if (ControlData.cbc_debug_routeCbc || ControlData.cbc_debug_routeXA) {
							originalDvarKeys = new LinkedHashSet<String>(SolverData.getDvarMap().keySet());
						}
						
						if (ControlData.cbc_debug_routeCbc) {
							new XASolver();
							SolverData.getDvarMap().keySet().retainAll(originalDvarKeys);
							if (Error.error_solving.size() > 0) {
								String msg = "XA solving error.";
								ILP.writeNoteLn("", msg);
								System.out.println("" + msg);
								Error.writeErrorLog();
								Error.error_solving.clear();
							}
							
						}
						
						CbcSolver.newProblem(); 
						
						if (ControlData.cbc_debug_routeXA) {
							SolverData.getDvarMap().keySet().retainAll(originalDvarKeys);
							new XASolver();
						}
						
						// check monitored dvar list. they are slack and surplus generated automatically 
						// from the weight group deviation penalty
						// give error if they are not zero or greater than a small tolerance.
						noError = !ErrorCheck.checkDeviationSlackSurplus(mds.deviationSlackSurplus_toleranceMap, mds.dvMap);
						
						if (ControlData.showRunTimeMessage) System.out.println("Solving Done.");
						if (Error.error_solving.size()<1){
							ControlData.isPostProcessing=true;
							mds.processAlias();
							if (ControlData.showRunTimeMessage) System.out.println("Assign Alias Done.");
						}else{
							Error.writeSolvingErrorFile("Error_solving.txt");
							Error.writeErrorLog();
							noError=false;
						}
						int cycleI=i+1;
						String strCycleI=cycleI+"";
						boolean isSelectedCycleOutput=General.isSelectedCycleOutput(strCycleI);
						if (ControlData.outputType==1){
							if (ControlData.isOutputCycle && isSelectedCycleOutput){
								HDF5Writer.writeOneCycleSv(mds, cycleI);
							}
						}
						System.out.println("Cycle "+cycleI+" in "+ControlData.currYear+"/"+ControlData.currMonth+"/"+ControlData.currDay+" Done. ("+model+")");
						if (Error.error_evaluation.size()>=1) noError=false;

						
						if (CbcSolver.intLog && (!ControlData.cbc_debug_routeXA && !ControlData.cbc_debug_routeCbc)) {
							
							String cbc_int = "";
							if (sds.cycIntDvMap != null && sds.cycIntDvMap.containsKey(ControlData.currCycleIndex)) {
								ArrayList<String> intDVs = new ArrayList<String>(sds.cycIntDvMap.get(ControlData.currCycleIndex));
								for (String v : sds.allIntDv) {
									if (intDVs.contains(v)){
										//xa_int  += " "+ Math.round(ControlData.xasolver.getColumnActivity(v));
										if (Error.getTotalError()==0) {
											cbc_int += " "+ Math.round(CbcSolver.varDoubleMap.get(v));
										} else {
											cbc_int += " ?";
										}
									} else {
										//xa_int  += "  ";
										cbc_int += "  ";
									}
								}
							}
							// write solve time
							cbc_int +=  "  "+String.format("%8.2f",ControlData.solverTime_cbc_this);
							
							// write solve name
							cbc_int +=  "  "+CbcSolver.solveName;

							ILP.writeNoteLn(ILP.getYearMonthCycle(), ""+ cbc_int, ILP._noteFile_cbc_int_log);
							
						}
						
						
						if (ControlData.cbc_debug_routeXA ||ControlData.cbc_debug_routeCbc ) {
																			
							// write watch var values
							boolean recordLP = false;
							double wa_cbc = 0;
							double wa_xa  = 0;
							if (ControlData.watchList != null) {
								String wa_cbc_str = "";
								String wa_xa_str = "";
								for (String s : ControlData.watchList) {
									if (ControlData.currModelDataSet.dvList.contains(s)){
									
										//System.out.println(s);
										wa_cbc = CbcSolver.varDoubleMap.get(s);
										wa_xa  = ControlData.xasolver.getColumnActivity(s);
										wa_cbc_str += String.format("%14.3f", wa_cbc) + "  ";
										wa_xa_str += String.format("%14.3f",  wa_xa) + "  ";
										
										if (Math.abs(wa_xa-wa_cbc)>ControlData.watchList_tolerance) recordLP=true; 
									}
								}
								ILP.writeNoteLn(ILP.getYearMonthCycle(), wa_cbc_str, ILP._watchFile_cbc);
								ILP.writeNoteLn(ILP.getYearMonthCycle(), wa_xa_str, ILP._watchFile_xa);
								
							}
							
							// write int value, time, and obj diff
							ILP.writeNoteLn(ILP.getYearMonthCycle(), ""+ControlData.xasolver.getObjective(), ILP._noteFile_xa_obj);
							ILP.writeNoteLn(ILP.getYearMonthCycle(), ""+ControlData.clp_cbc_objective, ILP._noteFile_cbc_obj);
							
							String xa_int = "";
							String cbc_int = "";
							if (sds.cycIntDvMap != null) {
								ArrayList<String> intDVs = new ArrayList<String>(sds.cycIntDvMap.get(ControlData.currCycleIndex));
								for (String v : sds.allIntDv) {
									if (intDVs.contains(v)){
										xa_int  += " "+ Math.round(ControlData.xasolver.getColumnActivity(v));
										if (Error.getTotalError()==0) {
											cbc_int += " "+ Math.round(CbcSolver.varDoubleMap.get(v));
										} else {
											cbc_int += " ?";
										}
									} else {
										xa_int  += "  ";
										cbc_int += "  ";
									}
								}
							}
							
							// write solve name
							cbc_int +=  "  "+CbcSolver.solveName;
							xa_int  +=  "  "+CbcSolver.solveName;
							
							if (Error.getTotalError() == 0) {
								if (recordLP) {
									CbcSolver.reloadAndWriteLp("_watch_diff", true, false);
								}
								
								double diff = ControlData.clp_cbc_objective - ControlData.xasolver.getObjective();
								if (Math.abs(diff) > CbcSolver.record_if_obj_diff) {
									CbcSolver.reloadAndWriteLp("_obj"+Math.round(diff), true, false);
								}
								if (Math.abs(diff) > CbcSolver.log_if_obj_diff) {
									xa_int += "  obj: " + String.format("%16.3f", diff);
									cbc_int += "  obj: " + String.format("%16.3f", diff);
									if (diff>CbcSolver.maxObjDiff){
										CbcSolver.maxObjDiff = diff;
										CbcSolver.maxObjDiff_id = ILP.getYearMonthCycle();
									} else if(diff<CbcSolver.maxObjDiff_minus){
										CbcSolver.maxObjDiff_minus = diff;
										CbcSolver.maxObjDiff_minus_id = ILP.getYearMonthCycle();										
									}
								}
							}
							ILP.writeNoteLn(ILP.getYearMonthCycle(), ""+ xa_int, ILP._noteFile_xa_int);
							ILP.writeNoteLn(ILP.getYearMonthCycle(), ""+ cbc_int, ILP._noteFile_cbc_int);
							
						}
						
						CbcSolver.resetModel();
						
						ControlData.currTimeStep.set(ControlData.currCycleIndex, ControlData.currTimeStep.get(ControlData.currCycleIndex)+1);
						if (ControlData.timeStep.equals("1MON")){
							VariableTimeStep.currTimeAddOneMonth();
						}else{
							VariableTimeStep.currTimeAddOneDay();
						}
					}else{
						int cycleI=i+1;
						String strCycleI=cycleI+"";
						boolean isSelectedCycleOutput=General.isSelectedCycleOutput(strCycleI);
						if (ControlData.outputType==1){
							if (ControlData.isOutputCycle && isSelectedCycleOutput){
								HDF5Writer.skipOneCycle(mds, cycleI);
							}
						}
						System.out.println("Cycle "+cycleI+" in "+ControlData.currYear+"/"+ControlData.currMonth+"/"+ControlData.currDay+" Skipped. ("+model+")");
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
		CbcSolver.close(); if (ControlData.cbc_debug_routeXA || ControlData.cbc_debug_routeCbc) {ControlData.xasolver.close();}
		
		if (ControlData.outputType==1){
			HDF5Writer.createDvarAliasLookup();
			HDF5Writer.writeTimestepData();
			HDF5Writer.writeCyclesDvAlias();
			HDF5Writer.closeDataStructure();
		}else{
			if (ControlData.writeInitToDVOutput){
				DssOperation.writeInitDvarAliasToDSS();
			}
			DssOperation.writeDVAliasToDSS();
			ControlData.dvDss.close();
		}
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
		new ControllerSG(args);
	}
}