package wrimsv2.components;

import gurobi.GRBException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import com.sunsetsoft.xa.Optimizer;

import vista.db.dss.DSSUtil;
import wrimsv2.commondata.solverdata.SolverData;
import wrimsv2.commondata.wresldata.Alias;
import wrimsv2.commondata.wresldata.Dvar;
import wrimsv2.commondata.wresldata.External;
import wrimsv2.commondata.wresldata.Goal;
import wrimsv2.commondata.wresldata.ModelDataSet;
import wrimsv2.commondata.wresldata.StudyDataSet;
import wrimsv2.commondata.wresldata.Svar;
import wrimsv2.commondata.wresldata.Timeseries;
import wrimsv2.commondata.wresldata.WeightElement;
import wrimsv2.evaluator.DataTimeSeries;
import wrimsv2.evaluator.DssDataSetFixLength;
import wrimsv2.evaluator.DssOperation;
import wrimsv2.evaluator.EvalConstraint;
import wrimsv2.evaluator.EvalExpression;
import wrimsv2.evaluator.Evaluation;
import wrimsv2.evaluator.EvaluatorLexer;
import wrimsv2.evaluator.EvaluatorParser;
import wrimsv2.evaluator.PreEvaluator;
import wrimsv2.evaluator.PreEvaluatorTree;
import wrimsv2.evaluator.TimeOperation;
import wrimsv2.evaluator.ValueEvaluatorLexer;
import wrimsv2.evaluator.ValueEvaluatorParser;
import wrimsv2.evaluator.ValueEvaluatorTreeWalker;
import wrimsv2.external.LoadAllDll;
import wrimsv2.solver.GurobiSolver;
import wrimsv2.solver.XASolver;
import wrimsv2.solver.initialXASolver;
import wrimsv2.tools.RCCComparison;
import wrimsv2.wreslparser.elements.StudyUtils;


public class ControllerTree {
	
	public ControllerTree() {
		setControlData();
		generateStudyFile();
		try {
			StudyDataSet sds = parse();
			new PreEvaluatorTree(sds);
			runModel(sds);
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ControllerTree(String[] args) {
		setControlData(args);
		generateStudyFile();
		try {
			StudyDataSet sds = parse();
			new PreEvaluator(sds);
			runModel(sds);
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setControlData(){
		FilePaths.groundwaterDir="";
		FilePaths.setSvarDssPaths("D:\\CalLite_Beta_042611\\DSS\\CL_FUTURE_WHL042611_SV.dss");
        FilePaths.setInitDssPaths("D:\\CalLite_Beta_042611\\DSS\\CalLite2020D09EINIT.dss");
        FilePaths.setDvarDssPaths("D:\\CalLite_Beta_042611\\DSS\\TestWRIMSV2DV.dss");
        FilePaths.setMainFilePaths("D:\\CalLite_Beta_042611\\Run\\main_BO.wresl");
		ControlData cd=new ControlData();
        cd.svDvPartF="2020D09E";
        cd.initPartF="2020D09E";
		cd.partA = "CALSIM";
		cd.partE = "1MON";
		cd.timeStep="1MON";
        cd.startYear=1921;
        cd.startMonth=10;
        cd.startDay=31;
        cd.endYear=1921;
        cd.endMonth=10;
        cd.endDay=31;
        cd.simulationTimeFrame=TimeOperation.dssTimeFrame(cd.startYear, cd.startMonth, cd.startDay, cd.endYear, cd.endMonth, cd.endDay);
        cd.currYear=ControlData.startYear;
        cd.currMonth=ControlData.startMonth;
        cd.currDay=ControlData.startDay;
        cd.writeDssStartYear=ControlData.startYear;
        cd.writeDssStartMonth=ControlData.startMonth;
        cd.writeDssStartDay=ControlData.startDay;
        cd.solverName="XA";
        FilePaths.csvFolderName="csv";
        
		cd.totalTimeStep=getTotalTimeStep();
	}
	
	public void setControlData(String[] args){
		FilePaths.groundwaterDir=args[0];
        FilePaths.setMainFilePaths(args[1]);
        FilePaths.setSvarDssPaths(args[2]);
        FilePaths.setInitDssPaths(args[3]);
        FilePaths.setDvarDssPaths(args[4]);
		ControlData cd=new ControlData();
		cd.svDvPartF=args[5];
		cd.initPartF=args[6];
		cd.partA = args[7];
		cd.partE = args[8];
		cd.timeStep = args[8];
		cd.startYear=Integer.parseInt(args[9]);
		cd.startMonth=Integer.parseInt(args[10]);
		cd.startDay=Integer.parseInt(args[11]);
		cd.endYear=Integer.parseInt(args[12]);
		cd.endMonth=Integer.parseInt(args[13]);
		cd.endDay=Integer.parseInt(args[14]);
		cd.solverName=args[15];
		FilePaths.csvFolderName = args[16];
		cd.simulationTimeFrame=TimeOperation.dssTimeFrame(cd.startYear, cd.startMonth, cd.startDay, cd.endYear, cd.endMonth, cd.endDay);
		cd.currYear=cd.startYear;
		cd.currMonth=cd.startMonth;
		cd.currDay=cd.startDay;
        cd.writeDssStartYear=cd.startYear;
        cd.writeDssStartMonth=cd.startMonth;
        cd.writeDssStartDay=cd.startDay;
        
		cd.totalTimeStep=getTotalTimeStep();
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
			out.write(FilePaths.fullSvarDssPath+"\n");
			out.write(FilePaths.fullDvarDssPath+"\n");
			out.write(FilePaths.fullInitDssPath+"\n");
			out.write(ControlData.timeStep+"\n");
			out.write(ControlData.totalTimeStep+"\n");
			out.write(ControlData.startDay+"\n");
			out.write(ControlData.startMonth+"\n");
			out.write(ControlData.startDay+"\n");
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
		Calendar cal = Calendar.getInstance();
		System.out.println("Before Parsser: "+cal.getTimeInMillis());
		
		return StudyUtils.checkStudy(FilePaths.fullMainPath, true);
		
	}
	
	public void runModel(StudyDataSet sds){
		if (ControlData.solverName.equals("XA")){
			runModelXA(sds);
		}else if (ControlData.solverName.equals("Gurobi")){
			runModelGurobi(sds);
		}
	}
	
	public void runModelXA(StudyDataSet sds){
		ControlData.currStudyDataSet=sds;
		ArrayList<String> modelList=sds.getModelList();
		Map<String, ModelDataSet> modelDataSetMap=sds.getModelDataSetMap();		
		ControlData.startTime=new Date(ControlData.startYear-1900, ControlData.startMonth-1, ControlData.startDay);
				
		ControlData.groupInit= DSSUtil.createGroup("local", FilePaths.fullInitDssPath);
		ControlData.groupSvar= DSSUtil.createGroup("local", FilePaths.fullSvarDssPath);
		ControlData.allTsMap=sds.getTimeseriesMap();
		
		Calendar cal = Calendar.getInstance();
		System.out.println("After Parsser: "+cal.getTimeInMillis());
		readTimeseries();
		initialDvarAliasTS(ControlData.totalTimeStep);
		for (int i=0; i<modelList.size(); i++){
			String model=modelList.get(i);
			ModelDataSet mds=modelDataSetMap.get(model);
			ControlData.currModelDataSet=mds;
			ControlData.currCycleIndex=i;
			processExternal();
		}
		
		new initialXASolver();
		boolean noError=true;
		ControlData.currTimeStep=0;
		while (ControlData.currTimeStep<ControlData.totalTimeStep && noError){
			clearDvarValues(modelList, modelDataSetMap);
			int i=0;
			while (i<modelList.size() && noError){
				String model=modelList.get(i);
				ModelDataSet mds=modelDataSetMap.get(model);
				ControlData.currModelDataSet=mds;
				ControlData.currSvMap=mds.svMap;
				ControlData.currDvMap=mds.dvMap;
				ControlData.currAliasMap=mds.asMap;
				ControlData.currGoalMap=mds.gMap;
				ControlData.currTsMap=mds.tsMap;
				ControlData.currCycleIndex=i;
				ControlData.isPostProcessing=false;
				cal = Calendar.getInstance();
				System.out.println("Before Evaluation: "+cal.getTimeInMillis());
				processModel();
				if (Error.error_evaluation.size()>=1){
					Error.writeEvaluationErrorFile("evaluation_error.txt");
					noError=false;
				}
				cal = Calendar.getInstance();
				System.out.println(" After Evaluation: "+cal.getTimeInMillis());
				new XASolver();
				cal = Calendar.getInstance();
				System.out.println("    After solving: "+cal.getTimeInMillis());
				if (Error.error_solving.size()<1){
					cal = Calendar.getInstance();
					System.out.println("After assign dvar: "+cal.getTimeInMillis());
					ControlData.isPostProcessing=true;
					processAliasTimeseries();
				}else{
					Error.writeSolvingErrorFile("solving_error.txt");
					noError=false;
				}
				cal = Calendar.getInstance();
				System.out.println("      After alias: "+cal.getTimeInMillis());
				//new RCCComparison();
				i=i+1;
			}
			if (ControlData.timeStep.equals("1MON")){
				currTimeAddOneMonth();
			}else{
				currTimeAddOneDay();
			}
			System.out.println(ControlData.currYear+"/"+ControlData.currMonth);
			ControlData.currTimeStep=ControlData.currTimeStep+1;
		}
		DssOperation.writeInitDvarAliasToDSS();
		DssOperation.writeDVAliasToDSS();
		ControlData.xasolver.close();
	}
	
	public void runModelGurobi(StudyDataSet sds){
		ControlData.currStudyDataSet=sds;
		ArrayList<String> modelList=sds.getModelList();
		Map<String, ModelDataSet> modelDataSetMap=sds.getModelDataSetMap();		
		ControlData.startTime=new Date(ControlData.startYear-1900, ControlData.startMonth-1, ControlData.startDay);
				
		ControlData.groupInit= DSSUtil.createGroup("local", FilePaths.fullInitDssPath);
		ControlData.groupSvar= DSSUtil.createGroup("local", FilePaths.fullSvarDssPath);
		ControlData.allTsMap=sds.getTimeseriesMap();
		
		Calendar cal = Calendar.getInstance();
		System.out.println("After Parsser: "+cal.getTimeInMillis());
		readTimeseries();
		initialDvarAliasTS(ControlData.totalTimeStep);
		for (int i=0; i<modelList.size(); i++){
			String model=modelList.get(i);
			ModelDataSet mds=modelDataSetMap.get(model);
			ControlData.currModelDataSet=mds;
			ControlData.currCycleIndex=i;
			processExternal();
		}

		boolean noError=true;
		ControlData.currTimeStep=0;
		while (ControlData.currTimeStep<ControlData.totalTimeStep && noError){
			clearDvarValues(modelList, modelDataSetMap);
			int i=0;
			while (i<modelList.size() && noError){
				String model=modelList.get(i);
				ModelDataSet mds=modelDataSetMap.get(model);
				ControlData.currModelDataSet=mds;
				ControlData.currSvMap=mds.svMap;
				ControlData.currDvMap=mds.dvMap;
				ControlData.currAliasMap=mds.asMap;
				ControlData.currGoalMap=mds.gMap;
				ControlData.currTsMap=mds.tsMap;
				ControlData.currCycleIndex=i;
				ControlData.isPostProcessing=false;
				cal = Calendar.getInstance();
				System.out.println("Before Evaluation: "+cal.getTimeInMillis());
				processModel();
				if (Error.error_evaluation.size()>=1){
					Error.writeEvaluationErrorFile("evaluation_error.txt");
					noError=false;
				}
				cal = Calendar.getInstance();
				System.out.println(" After Evaluation: "+cal.getTimeInMillis());
				try{
					new GurobiSolver();
				}catch (GRBException e){
					Error.addSolvingError("Gurobi solving error: "+e.getMessage());
				}
				cal = Calendar.getInstance();
				System.out.println("    After solving: "+cal.getTimeInMillis());
				if (Error.error_solving.size()<1){
					cal = Calendar.getInstance();
					System.out.println("After assign dvar: "+cal.getTimeInMillis());
					ControlData.isPostProcessing=true;
					processAliasTimeseries();
				}else{
					Error.writeSolvingErrorFile("solving_error.txt");
					noError=false;
				}
				cal = Calendar.getInstance();
				System.out.println("      After alias: "+cal.getTimeInMillis());
				//new RCCComparison();
				i=i+1;
			}
			if (ControlData.timeStep.equals("1MON")){
				currTimeAddOneMonth();
			}else{
				currTimeAddOneDay();
			}
			System.out.println(ControlData.currYear+"/"+ControlData.currMonth);
			DssOperation.writeInitDvarAliasToDSS();
			DssOperation.writeDVAliasToDSS();
			ControlData.currTimeStep=ControlData.currTimeStep+1;
		}
	}
	
	public void clearDvarValues(ArrayList<String> modelList, Map<String, ModelDataSet> modelDataSetMap){
		for (int i=0; i<modelList.size(); i++){   
			String model=modelList.get(i);
			ModelDataSet mds=modelDataSetMap.get(model);
			ArrayList<String> dvList = mds.dvList;
			Map<String, Dvar> dvMap =mds.dvMap;
			for (String dvName: dvList){
				Dvar dvar=dvMap.get(dvName);
				dvar.setData(null);
			}
		}
	}
	
	public void processModel(){
		processTimeseries();
		Calendar cal = Calendar.getInstance();
		System.out.println(" After Timeseries: "+cal.getTimeInMillis());
		processSvar();
		cal = Calendar.getInstance();
		System.out.println("       After svar: "+cal.getTimeInMillis());
		processDvar();	
		cal = Calendar.getInstance();
		System.out.println("       After dvar: "+cal.getTimeInMillis());
		processGoal();	
		cal = Calendar.getInstance();
		System.out.println("       After goal: "+cal.getTimeInMillis());
		processWeight();
		cal = Calendar.getInstance();
		System.out.println("     After weight: "+cal.getTimeInMillis());
	}

	public void readTimeseries(){
		Map<String, Timeseries> tsMap=ControlData.currStudyDataSet.getTimeseriesMap();
		ControlData.currEvalTypeIndex=6;
		Set tsKeySet=tsMap.keySet();
		Iterator iterator=tsKeySet.iterator();
		while(iterator.hasNext()){
			String tsName=(String)iterator.next();
			//System.out.println("Reading svar timeseries "+tsName);
			//To Do: in the svar class, add flag to see if svTS has been loaded
			if (!DataTimeSeries.lookSvDss.contains(tsName)){ 
				DssOperation.getSVTimeseries(tsName, FilePaths.fullSvarDssPath);
				DataTimeSeries.lookSvDss.add(tsName);
			}
		}
	}
	
	public void	initialDvarAliasTS(long totalTimeStep){
		
	}
	
	public void processExternal(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> exList = mds.exList;
		Map<String, External> exMap =mds.exMap;
		ControlData.currExMap=exMap;
		ControlData.currEvalTypeIndex=4;
		for (String exName: exList){
			if (!ControlData.allExternalFunction.containsKey(exName)){
				ControlData.currEvalName=exName;
				External external=exMap.get(exName);
				ControlData.allExternalFunction.put(exName, external.type);
				if (!external.type.equals("f90") && !ControlData.allDll.contains(exName)){
					ControlData.allDll.add(external.type);
				}
			}
		}
		new LoadAllDll(ControlData.allDll);
	}
	
	public void processWeight(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> wtList = mds.wtList;
		Map<String, WeightElement> wtMap =mds.wtMap;
		SolverData.clearWeightMap();
		ControlData.currEvalTypeIndex=5;
		for (String wtName: wtList){
			ControlData.currEvalName=wtName;
			//System.out.println("Process weight "+wtName);
			WeightElement wt=wtMap.get(wtName);
			String wtString=wt.weight;
			String evalString="v: "+wtString;
			ANTLRStringStream stream = new ANTLRStringStream(evalString);
			ValueEvaluatorLexer lexer = new ValueEvaluatorLexer(stream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
			ValueEvaluatorParser evaluator = new ValueEvaluatorParser(tokenStream);
			try {
				evaluator.evaluator();
				wt.setValue(evaluator.evalValue.getData().doubleValue());
			} catch (RecognitionException e) {
				Error.addEvaluationError("weight definition has error");
				wt.setValue(0.0);
			}
			SolverData.getWeightMap().put(wtName, wt);
		}
	}
	
	public void processSvar(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> svList = mds.svList;
		Map<String, Svar> svMap =mds.svMap;
		ControlData.currEvalTypeIndex=0;
		for (String svName: svList){
			ControlData.currEvalName=svName;
			//System.out.println("Process svar "+svName);
			Svar svar=svMap.get(svName);
			ArrayList<ValueEvaluatorTreeWalker> caseConditions=svar.caseConditionWalkers;
			boolean condition=false;
			int i=-1;
			while(!condition && i<=caseConditions.size()-2){
				i=i+1;
				ValueEvaluatorTreeWalker caseCondition=caseConditions.get(i);
				try{
					caseCondition.evaluator();
					condition=caseCondition.evalCondition;
				}catch (Exception e){
					Error.addEvaluationError("Case condition evaluation has error.");
					condition=false;
				}
				caseCondition.reset();
			}
			if (condition){
				if (svName.equals("trinity_import1")){
					int x=0;
				}
				ArrayList<ValueEvaluatorTreeWalker> caseExpressions=svar.caseExpressionWalkers;
				ValueEvaluatorTreeWalker caseExpression=caseExpressions.get(i);
				try {
					caseExpression.evaluator();
					svar.setData(caseExpression.evalValue);
				} catch (RecognitionException e) {
					Error.addEvaluationError("Case expression evaluation has error.");
					svar.setData(new IntDouble(1.0, false));
				}
				caseExpression.reset();
			}else{
				Error.addEvaluationError("None of the case conditions is satisfied.");
				svar.setData(new IntDouble(1.0, false));
			}
		}
	}
	
	public void processTimeseries(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> tsList = mds.tsList;
		Map<String, Timeseries> tsMap =mds.tsMap;
		ControlData.currEvalTypeIndex=5;
		for (String tsName:tsList){
			ControlData.currEvalName=tsName;
			//System.out.println("process timeseries "+tsName);
			Timeseries ts=tsMap.get(tsName);
			ts.setData(new IntDouble(Evaluation.timeseries(tsName),false));
		}
	}
	
	public void processDvar(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> dvList = mds.dvList;
		Map<String, Dvar> dvMap =mds.dvMap;
		SolverData.clearDvarMap();
		ControlData.currDvMap=dvMap;
		ControlData.currEvalTypeIndex=1;
		for (String dvName: dvList){
			ControlData.currEvalName=dvName;
			//System.out.println("Process dvar "+dvName);
			Dvar dvar=dvMap.get(dvName);
			
			String evalString="v: "+dvar.lowerBound;
			ANTLRStringStream stream = new ANTLRStringStream(evalString);
			ValueEvaluatorLexer lexer = new ValueEvaluatorLexer(stream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
			ValueEvaluatorParser evaluator = new ValueEvaluatorParser(tokenStream);
			try {
				evaluator.evaluator();
				dvar.lowerBoundValue=evaluator.evalValue.getData();
			} catch (RecognitionException e) {
				Error.addEvaluationError("Lowerbound evaluation has error.");
				dvar.lowerBoundValue=-901.0;
			}
			
			evalString="v: "+dvar.upperBound;
			stream = new ANTLRStringStream(evalString);
			lexer = new ValueEvaluatorLexer(stream);
			tokenStream = new CommonTokenStream(lexer);
			evaluator = new ValueEvaluatorParser(tokenStream);
			try {
				evaluator.evaluator();
				dvar.upperBoundValue=evaluator.evalValue.getData();
			} catch (RecognitionException e) {
				Error.addEvaluationError("Lowerbound evaluation has error.");
				dvar.lowerBoundValue=-901.0;
			}
			SolverData.getDvarMap().put(dvName, dvar);
		}
	}
	
	public void processGoal(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> gList = mds.gList;
		Map<String, Goal> gMap =mds.gMap;
		ControlData.currEvalTypeIndex=3;
		SolverData.clearConstraintDataMap();
		for (String goalName: gList){
			ControlData.currEvalName=goalName;
			//System.out.println("Process constraint "+goalName);
			Goal goal=gMap.get(goalName);
			ArrayList<String> caseCondition=goal.caseCondition;
			boolean condition=false;
			int i=-1;
			while(!condition && i<=caseCondition.size()-2){
				i=i+1;
				String evalString="c: "+caseCondition.get(i);
				ANTLRStringStream stream = new ANTLRStringStream(evalString);
				ValueEvaluatorLexer lexer = new ValueEvaluatorLexer(stream);
				TokenStream tokenStream = new CommonTokenStream(lexer);
				ValueEvaluatorParser evaluator = new ValueEvaluatorParser(tokenStream);
				try{
					evaluator.evaluator();
					condition=evaluator.evalCondition;
				}catch (Exception e){
					Error.addEvaluationError("Case condition evaluation has error.");
					condition=false;
				}
			}
			if (condition){
				ANTLRStringStream stream = new ANTLRStringStream("g:"+goal.caseExpression.get(i));
				EvaluatorLexer lexer = new EvaluatorLexer(stream);
				TokenStream tokenStream = new CommonTokenStream(lexer);
				EvaluatorParser evaluator = new EvaluatorParser(tokenStream);
				try {
					evaluator.evaluator();
					SolverData.getConstraintDataMap().put(goalName,EvaluatorParser.evalConstraint);
				} catch (RecognitionException e) {
					Error.addEvaluationError("Case expression evaluation has error.");
				}	
			}
		}
	}
	
	public void processAlias(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> asList = mds.asList;
		Map<String, Alias> asMap =mds.asMap;
		ControlData.currEvalTypeIndex=2;
		for (String asName: asList){
			ControlData.currEvalName=asName;
			//System.out.println("Process alias "+asName);
			Alias alias=asMap.get(asName);
			
			String evalString="v: "+alias.expression;
			ANTLRStringStream stream = new ANTLRStringStream(evalString);
			ValueEvaluatorLexer lexer = new ValueEvaluatorLexer(stream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
			ValueEvaluatorParser evaluator = new ValueEvaluatorParser(tokenStream);
			try {
				evaluator.evaluator();
				IntDouble id=evaluator.evalValue;
				alias.data=id;
				if (!DataTimeSeries.dvAliasTS.containsKey(asName)){
					DssDataSetFixLength dds=new DssDataSetFixLength();
					double[] data=new double[ControlData.totalTimeStep];
					dds.setData(data);
					dds.setTimeStep(ControlData.partE);
					dds.setStartTime(ControlData.startTime);
					dds.setUnits(alias.units);
					dds.setKind(alias.kind);
					DataTimeSeries.dvAliasTS.put(asName,dds);
				}
				double[] dataList=DataTimeSeries.dvAliasTS.get(asName).getData();
				dataList[ControlData.currTimeStep]=id.getData().doubleValue();
			} catch (RecognitionException e) {
				Error.addEvaluationError("Alias evaluation has error.");
				alias.data=new IntDouble(-901.0,false);
				double[] dataList=DataTimeSeries.dvAliasTS.get(asName).getData();
				dataList[ControlData.currTimeStep]=-901.0;
			}
		}
	}
	
	public void processAliasTimeseries(){
		ModelDataSet mds=ControlData.currModelDataSet;
		ArrayList<String> asList = mds.asList;
		Map<String, Alias> asMap =mds.asMap;
		ControlData.currEvalTypeIndex=2;
		for (String asName: asList){
			ControlData.currEvalName=asName;
			//System.out.println("Process alias "+asName);
			Alias alias=asMap.get(asName);
			
			ValueEvaluatorParser evaluator = alias.expressionParser;
			try {
				evaluator.evaluator();
				IntDouble id=evaluator.evalValue;
				alias.data=id;
				if (!DataTimeSeries.dvAliasTS.containsKey(asName)){
					DssDataSetFixLength dds=new DssDataSetFixLength();
					double[] data=new double[ControlData.totalTimeStep];
					dds.setData(data);
					dds.setTimeStep(ControlData.partE);
					dds.setStartTime(ControlData.startTime);
					dds.setUnits(alias.units);
					dds.setKind(alias.kind);
					DataTimeSeries.dvAliasTS.put(asName,dds);
				}
				double[] dataList=DataTimeSeries.dvAliasTS.get(asName).getData();
				dataList[ControlData.currTimeStep]=id.getData().doubleValue();
			} catch (RecognitionException e) {
				Error.addEvaluationError("Alias evaluation has error.");
				alias.data=new IntDouble(-901.0,false);
				double[] dataList=DataTimeSeries.dvAliasTS.get(asName).getData();
				dataList[ControlData.currTimeStep]=-901.0;
			}
			evaluator.reset();
		}
	}
	
	public int getTotalTimeStep(){
		if (ControlData.timeStep.equals("1MON")){
			return (ControlData.endYear-ControlData.startYear)*12+(ControlData.endMonth-ControlData.startMonth)+1;
		}else{
			Date startDate = new Date (ControlData.startYear-1900, ControlData.startMonth-1, ControlData.startDay);
			Date endDate=new Date (ControlData.endYear-1900, ControlData.endMonth-1, ControlData.endDay);
			long startTime=startDate.getTime();
			long endTime=endDate.getTime();
			double timestep=(endTime-startTime)/(24*60*60*1000l)+1;
			return (int)timestep;
		}
	}
	
	public void currTimeAddOneMonth(){
		ControlData.currMonth=ControlData.currMonth+1;
		ControlData.currYear=ControlData.currYear;
		if (ControlData.currMonth>12){
			ControlData.currMonth=ControlData.currMonth-12;
			ControlData.currYear=ControlData.currYear+1;
		}
		ControlData.currDay=TimeOperation.numberOfDays(ControlData.currMonth, ControlData.currYear);
	}

	public void currTimeAddOneDay(){
		Date currDate = new Date (ControlData.currYear-1900, ControlData.currMonth-1, ControlData.currDay);
		long currTime=currDate.getTime()+1 * 24 * 60 * 60 * 1000l;
		currDate = new Date (currTime);
		ControlData.currMonth=currDate.getMonth()+1;
		ControlData.currYear=currDate.getYear()+1900;
		ControlData.currDay=currDate.getDate();
	}
	
	public static void main(String[] args){
		new ControllerTree(args);
	}
}