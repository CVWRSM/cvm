/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Bjorn Freeman-Benson - initial API and implementation
 *******************************************************************************/
package wrimsv2_plugin.debugger.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCConnectionProfileConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import wrimsv2_plugin.calsimhydro.CalSimHydro;
import wrimsv2_plugin.debugger.core.CBCSetting;
import wrimsv2_plugin.debugger.core.DebugCorePlugin;
import wrimsv2_plugin.debugger.exception.WPPException;
import wrimsv2_plugin.debugger.model.WPPDebugTarget;
import wrimsv2_plugin.debugger.msr.MSRDataTransfer;
import wrimsv2_plugin.debugger.msr.MSRProcRun;
import wrimsv2_plugin.debugger.msr.MSRUtil;
import wrimsv2_plugin.debugger.pa.PAProcDV;
import wrimsv2_plugin.debugger.pa.PAProcInit;
import wrimsv2_plugin.debugger.pa.PAProcRun;
import wrimsv2_plugin.sensitivity.SensitivityRun;
import wrimsv2_plugin.tools.Encryption;
import wrimsv2_plugin.tools.FileProcess;
import wrimsv2_plugin.tools.TimeOperation;

import java.lang.Runtime;


public class WPPLaunchDelegate extends LaunchConfigurationDelegate {
	private String externalPath;
	private String gwDataFolder;
	private String mainFile;
	private String svarFile;
	private String initFile;
	private String dvarFile;
	private String svFPart;
	private String initFPart;
	private String aPart;
	private String timeStep;
	private int startYear;
	private int startMonth;
	private int startDay;
	private int endYear;
	private int endMonth;
	private int endDay;
	private String wreslPlus;
	private String freeXA;
	private String allowSvTsInit;
	private String jarXA="XAOptimizer.jar";
	private int sid=1;
	private boolean afterFirstRound=false;
	private int ms=1;
	private boolean useMainFile=true;
	private String databaseURL="none";
	private String sqlGroup="calsim";
	private String ovOption="0";
	private String ovFile="";
	private boolean isSensitivity=false;
	private int sri=1;
	private String allRestartFiles;
	private String numberRestartFiles;

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException{		
		String chr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_CALSIMHYDRORUN, "0");
		if (chr.equals("1")){
			CalSimHydro ch = new CalSimHydro();
			ch.run(configuration);
		}
		
		String sensitivityString=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ISSENSITIVITYRUN, "no");
		if (sensitivityString.equalsIgnoreCase("yes")){
			isSensitivity=true;
			sensitivityLaunch(configuration, mode, launch, monitor);
		} 
		else{
			isSensitivity=false;
			singleLaunch(configuration, mode, launch, monitor);
		}
	}
	
	public void sensitivityLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException{		
		SensitivityRun sr=new SensitivityRun(configuration);
		for (sri=1; sri<=sr.getNumRuns(); sri++){
			sr.createSensitivityTable(sri);
			singleLaunch(configuration, mode, launch, monitor);
		}
	}
	
	public void singleLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException{		
		if (DebugCorePlugin.target !=null && !DebugCorePlugin.target.isTerminated()){		
			DebugCorePlugin.target.sendRequest("terminate");
		}
		
		ms=Integer.parseInt(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_MULTISTUDY, "1"));
		String lt=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_LAUNCHTYPE, "0");
		DebugCorePlugin.launchType=Integer.parseInt(lt);
		
		getStartEndDate(configuration);
		afterFirstRound=false;
		sid=1;
		
		if (ms==1){	
			switch (DebugCorePlugin.launchType){
				case 0:
					useMainFile=true;
					regularLaunch(configuration, mode, launch);
					break;
				case 1:
					paLaunch(configuration, mode, launch);
					break;
			}
		}else{
			multiStudyRun(configuration, mode, launch);
		}
	}
	
	public int regularLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch) throws CoreException{
		
		int terminateCode=0;
		int requestPort = -1;
		int eventPort = -1;
		requestPort = findFreePort();
		eventPort = findFreePort();
		if (requestPort == -1 || eventPort == -1) {
			abort("Unable to find free port", null);
		}
			
		createBatch(configuration, requestPort, eventPort, mode);
		
		try {
			if (mode.equals("debug")){
				DebugCorePlugin.debugSet.reset();
				Process process = Runtime.getRuntime().exec("WRIMSv2_Engine.bat");
				IProcess p = DebugPlugin.newProcess(launch, process, "DebugWPP");
				IDebugTarget target = new WPPDebugTarget(launch, p, requestPort, eventPort);
				launch.addDebugTarget(target);
				process.waitFor();
				terminateCode=process.exitValue();
			}else{
				Process process = Runtime.getRuntime().exec("WRIMSv2_Engine.bat");
				IProcess p = DebugPlugin.newProcess(launch, process, "RunWPP");
				process.waitFor();
				terminateCode=process.exitValue();
				Runtime.getRuntime().exec("wmic Process Where \"CommandLine Like '%-Dname="+requestPort+"%'\" Call Terminate");
			}
		} catch (Exception e) {
			WPPException.handleException(e);
		}
		
		return terminateCode;
	}
	
	public int paLaunch(ILaunchConfiguration configuration, String mode, ILaunch launch) throws CoreException{
		paProcConfig(configuration);
		PAProcInit procInit = new PAProcInit(configuration, sid);
		PAProcRun procRun = new PAProcRun();
		PAProcDV procDV = new PAProcDV(configuration);
		
		procInit.createPAInit(configuration);
		procRun.initialPATime();
		procDV.deleteDVFile(configuration);
		
		int terminateCode=0;
		useMainFile=true;
		
		while (procRun.continueRun() && terminateCode==0){				
			int requestPort = -1;
			int eventPort = -1;
			requestPort = findFreePort();
			eventPort = findFreePort();
			if (requestPort == -1 || eventPort == -1) {
				abort("Unable to find free port", null);
			}
			
			createBatch(configuration, requestPort, eventPort, mode);
		
			try {
				if (mode.equals("debug")){
					DebugCorePlugin.debugSet.reset();
					Process process = Runtime.getRuntime().exec("WRIMSv2_Engine.bat");
					IProcess p = DebugPlugin.newProcess(launch, process, "DebugWPP");
					IDebugTarget target = new WPPDebugTarget(launch, p, requestPort, eventPort);
					launch.addDebugTarget(target);
					process.waitFor();
					terminateCode=process.exitValue();
					launch.removeDebugTarget(target);
					process.destroy();
				}else{
					Process process = Runtime.getRuntime().exec("WRIMSv2_Engine.bat");
					IProcess p = DebugPlugin.newProcess(launch, process, "RunWPP");
					process.waitFor();
					terminateCode=process.exitValue();
					process.destroy();
					Runtime.getRuntime().exec("wmic Process Where \"CommandLine Like '%-Dname="+requestPort+"%'\" Call Terminate");
				}
			} catch (Exception e) {
				WPPException.handleException(e);
			}
			
			procDV.resetDVStartDate();
			procRun.updatePATime();
			procInit.createInitData(procRun);
			useMainFile=false;
		}
		procInit.deletePAInit();
		
		return terminateCode;
	}
	
	public void multiStudyRun(ILaunchConfiguration configuration, String mode, ILaunch launch) throws CoreException{
		
		int terminateCode=0;
		
		String isFixDuration=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ISFIXDURATION, "yes");
		if (isFixDuration.equals("yes")){
			DebugCorePlugin.msDuration=new int[1];
			DebugCorePlugin.msDuration[0]=Integer.parseInt(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_FIXEDDURATION, "12"));
		}else{
			String variableDuration=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_VARIABLEDURATION, "");
			DebugCorePlugin.msDuration=MSRUtil.loadMSDuration(variableDuration, configuration);
		}
			
		MSRDataTransfer dataTxfr=new MSRDataTransfer();
		dataTxfr.procDataTxfrFile(configuration, ms, isSensitivity, sri);
		
		MSRProcRun msr=new MSRProcRun();
		msr.initialMSTime();
		startYear=DebugCorePlugin.msStartYear;
		startMonth=DebugCorePlugin.msStartMonth;
		startDay=DebugCorePlugin.msStartDay;
		endYear=DebugCorePlugin.msEndYear;
		endMonth=DebugCorePlugin.msEndMonth;
		endDay=DebugCorePlugin.msEndDay;
		
		useMainFile=true;
		while (msr.continueRun() && terminateCode==0){
			sid=1;
			while (sid<=ms && terminateCode==0){
				switch (DebugCorePlugin.launchType){
				case 0:
					terminateCode=regularLaunch(configuration, mode, launch);
					break;
				case 1:
					terminateCode=paLaunch(configuration, mode, launch);
					break;
				}
				if (sid<ms) dataTxfr.dataTxfr(sid);
				sid++;
			}
			msr.updateMSTime();
			startYear=DebugCorePlugin.msStartYear;
			startMonth=DebugCorePlugin.msStartMonth;
			startDay=DebugCorePlugin.msStartDay;
			endYear=DebugCorePlugin.msEndYear;
			endMonth=DebugCorePlugin.msEndMonth;
			endDay=DebugCorePlugin.msEndDay;
			afterFirstRound=true;
			useMainFile=false;
		}
	}
	
	public void paProcConfig(ILaunchConfiguration configuration){
		try {
			String paStartIntervalStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PASTARTINTERVAL, "12");
			DebugCorePlugin.paStartInterval=Integer.parseInt(paStartIntervalStr);
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
		
		try {
			String paDurationStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PADURATION, "12");
			DebugCorePlugin.paDuration=Integer.parseInt(paDurationStr);
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
		
		try {
			String initDelStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PADELINIT, "yes");
			if (initDelStr.equals("yes")){
				DebugCorePlugin.deletePAInit=true;
			}else{
				DebugCorePlugin.deletePAInit=false;
			}
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
		
		try {
			String defaultPADVStartYearStr;
			if (Calendar.getInstance().get(Calendar.MONTH)<=9){
				defaultPADVStartYearStr=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1);
			}else{
				defaultPADVStartYearStr=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			}
			String paDVStartYearStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PADVSTARTYEAR, defaultPADVStartYearStr);
			DebugCorePlugin.paDVStartYear=Integer.parseInt(paDVStartYearStr);
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
		
		try {
			String paDVStartMonthStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PADVSTARTMONTH, "10");
			DebugCorePlugin.paDVStartMonth=Integer.parseInt(paDVStartMonthStr);
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
		
		try {
			String paDVStartDayStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PADVSTARTDAY, "1");
			DebugCorePlugin.paDVStartDay=Integer.parseInt(paDVStartDayStr);
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
		
		try {
			String resetDVStartStr = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PARESETDVSTART, "no");
			if (resetDVStartStr.equals("yes")){
				DebugCorePlugin.resetOutputStart=true;
			}else{
				DebugCorePlugin.resetOutputStart=false;
			}
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
	}
	
	public void getStartEndDate(ILaunchConfiguration configuration){
		try{	
			startYear = Integer.parseInt(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_STARTYEAR, (String)null));
			startMonth = TimeOperation.monthValue(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_STARTMONTH, (String)null));
			DebugCorePlugin.startYear=startYear;
			DebugCorePlugin.startMonth=startMonth;
		
			endYear = Integer.parseInt(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ENDYEAR, (String)null));
			endMonth = TimeOperation.monthValue(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ENDMONTH, (String)null));
			DebugCorePlugin.endYear=endYear;
			DebugCorePlugin.endMonth=endMonth;
		
			startDay= Integer.parseInt(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_STARTDAY, (String)null));
			endDay=Integer.parseInt(configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ENDDAY, (String)null));
			DebugCorePlugin.startDay=startDay;
			DebugCorePlugin.endDay=endDay;
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
	}
	
	public void createBatch(ILaunchConfiguration configuration, int requestPort, int eventPort, String mode){
		
		String suffix="";
		if (sid>1) suffix="_MS"+sid;
		
		try {
			String lastSuffix="_MS"+ms;
			
			String studyName=null;
			studyName = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_STUDY, (String)null);
					
			String author=null;
			author = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_AUTHOR, (String)null);
				
			String date=null;
			date = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_DATE, (String)null);
			
			String description=null;
			description = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_DESCRIPTION, (String)null);
				
			mainFile = null;
			mainFile = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_PROGRAM+suffix, (String)null);
			
			dvarFile = null;
			dvarFile = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_DVARFILE+suffix, (String)null);
			
			svarFile = null;
			svarFile = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_SVARFILE+suffix, (String)null);
			
			initFile = null;
			initFile = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_INITFILE+suffix, (String)null);
			if (DebugCorePlugin.launchType==1){
				initFile=DebugCorePlugin.paInitFile;
			}else{
				if (afterFirstRound) {
					String lastDvarFile = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_DVARFILE+lastSuffix, (String)null);
					initFile=lastDvarFile;
				}
			}
			
			gwDataFolder = null;
			gwDataFolder = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_GWDATAFOLDER+suffix, (String)null)+File.separator;
			
			aPart = null;
			aPart = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_APART+suffix, (String)null);
			DebugCorePlugin.aPart=aPart;
			
			svFPart = null;
			svFPart = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_SVFPART+suffix, (String)null);
			DebugCorePlugin.svFPart=svFPart;
			
			initFPart = null;
			initFPart = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_INITFPART+suffix, (String)null);
			if (afterFirstRound){
				String lastSvFPart=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_SVFPART+lastSuffix, (String)null);
				initFPart=lastSvFPart;
			}
			DebugCorePlugin.initFPart=initFPart;
			
			timeStep = null;
			timeStep = configuration.getAttribute(DebugCorePlugin.ATTR_WPP_TIMESTEP+suffix, (String)null);
			DebugCorePlugin.timeStep=timeStep;
						
			if (DebugCorePlugin.launchType==1){
				startYear=DebugCorePlugin.paStartYear;
				startMonth=DebugCorePlugin.paStartMonth;
				startDay = DebugCorePlugin.paStartDay;
				endYear=DebugCorePlugin.paEndYear;
				endMonth=DebugCorePlugin.paEndMonth;
				endDay = DebugCorePlugin.paEndDay;
			}
			
			wreslPlus=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_WRESLPLUS, "no");
			freeXA=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_FREEXA, "no");
			if (freeXA.equalsIgnoreCase("yes")){
				jarXA="CalLiteV16.jar";
			}else{
				jarXA="XAOptimizer.jar";
			}
			allowSvTsInit=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ALLOWSVTSINIT, "no");
			allRestartFiles=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_ALLRESTARTFILES, "no");
			numberRestartFiles=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_NUMBERRESTARTFILES, "12");
			
			databaseURL=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_DATABASEURL, "none");
			sqlGroup=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_SQLGROUP, "calsim");
			ovOption=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_OVOPTION, "0");
			ovFile=configuration.getAttribute(DebugCorePlugin.ATTR_WPP_OVFILE, "");
			
			String mainFileAbsPath;
			if (new File(mainFile).isAbsolute()){
				mainFileAbsPath = mainFile;
			}else{
				mainFileAbsPath = FileProcess.procRelativePath(mainFile, configuration);
			}
			int index = mainFileAbsPath.lastIndexOf(File.separator);
			String mainDirectory = mainFileAbsPath.substring(0, index + 1);
			externalPath = mainDirectory + "External";
			
			String engineFileFullPath = "WRIMSv2_Engine.bat";
			try {
				String configFilePath = generateConfigFile(configuration, mainFileAbsPath);
				FileWriter engineFile = new FileWriter(engineFileFullPath);
				PrintWriter out = new PrintWriter(engineFile);
				generateBatch(out, mode, requestPort, eventPort, configFilePath);
			}catch (IOException e) {
				WPPException.handleException(e);
			}
			
			if (dvarFile.toLowerCase().endsWith(".mysqlr") || dvarFile.toLowerCase().endsWith(".mysqlc") || dvarFile.toLowerCase().endsWith(".sqlsvr")){
				generateDatabaseUserProfileFile(mainFileAbsPath);
			}
		} catch (CoreException e) {
			WPPException.handleException(e);
		}
	}
	
	public void generateBatch(PrintWriter out, String mode, int requestPort, int eventPort, String configFilePath){
		out.println("@echo off");
		out.println();
		out.println("set path=" + externalPath + ";"+"lib;%path%");
		out.println("set temp_wrims2=jre\\bin");
		out.println();
		/*
		String xmx="1280m";
		if (System.getProperty("os.arch").equalsIgnoreCase("amd64")){
			xmx="4096m";
		}
		*/
		if (mode.equals("debug")){
			out.println("jre\\bin\\java -Xmx"+DebugCorePlugin.xmx+"m -Xss1024K -Duser.timezone=UTC -Djava.library.path=\"" + externalPath + ";lib\" -cp \""+externalPath+";"+"lib\\external;lib\\WRIMSv2.jar;lib\\jep-3.8.2.jar;lib\\jna-3.5.1.jar;lib\\commons-io-2.1.jar;lib\\"+jarXA+";lib\\lpsolve55j.jar;lib\\coinor.jar;lib\\gurobi.jar;lib\\heclib.jar;lib\\jnios.jar;lib\\jpy.jar;lib\\misc.jar;lib\\pd.jar;lib\\vista.jar;lib\\guava-11.0.2.jar;lib\\javatuples-1.2.jar;lib\\kryo-2.24.0.jar;lib\\minlog-1.2.jar;lib\\objenesis-1.2.jar;lib\\jarh5obj.jar;lib\\jarhdf-2.10.0.jar;lib\\jarhdf5-2.10.0.jar;lib\\jarhdfobj.jar;lib\\slf4j-api-1.7.5.jar;lib\\slf4j-nop-1.7.5.jar;lib\\mysql-connector-java-5.1.42-bin.jar;lib\\sqljdbc4-2.0.jar\" wrimsv2.components.DebugInterface "+requestPort+" "+eventPort+" "+"-config="+configFilePath);
		}else{
			out.println("jre\\bin\\java -Xmx"+DebugCorePlugin.xmx+"m -Xss1024K -Duser.timezone=UTC -Dname="+requestPort+" -Djava.library.path=\"" + externalPath + ";lib\" -cp \""+externalPath+";"+"lib\\external;lib\\WRIMSv2.jar;lib\\jep-3.8.2.jar;lib\\jna-3.5.1.jar;lib\\commons-io-2.1.jar;lib\\"+jarXA+";lib\\lpsolve55j.jar;lib\\coinor.jar;lib\\gurobi.jar;lib\\heclib.jar;lib\\jnios.jar;lib\\jpy.jar;lib\\misc.jar;lib\\pd.jar;lib\\vista.jar;lib\\guava-11.0.2.jar;lib\\javatuples-1.2.jar;lib\\kryo-2.24.0.jar;lib\\minlog-1.2.jar;lib\\objenesis-1.2.jar;lib\\jarh5obj.jar;lib\\jarhdf-2.10.0.jar;lib\\jarhdf5-2.10.0.jar;lib\\jarhdfobj.jar;lib\\slf4j-api-1.7.5.jar;lib\\slf4j-nop-1.7.5.jar;lib\\mysql-connector-java-5.1.42-bin.jar;lib\\sqljdbc4-2.0.jar\" wrimsv2.components.ControllerBatch "+"-config="+configFilePath);
		}
		out.close();
	}
	
	public String generateConfigFile(ILaunchConfiguration configuration, String mainFileAbsPath){
		
		Map<String, String> configMap = new HashMap<String, String>();
		String configFilePath = null;
		
		try {				
			
			configMap.put("MainFile".toLowerCase(), mainFile);
			configMap.put("DvarFile".toLowerCase(),   dvarFile);
			configMap.put("SvarFile".toLowerCase(),   svarFile);
			configMap.put("SvarAPart".toLowerCase(),  aPart);
			configMap.put("SvarFPart".toLowerCase(),  svFPart);
			configMap.put("InitFile".toLowerCase(),   initFile);
			configMap.put("InitFPart".toLowerCase(),  initFPart);
			configMap.put("TimeStep".toLowerCase(),   timeStep);
			configMap.put("StartYear".toLowerCase(),  String.valueOf(startYear));
			configMap.put("StopYear".toLowerCase(),   String.valueOf(endYear));
			configMap.put("StartMonth".toLowerCase(), String.valueOf(startMonth));
			configMap.put("StopMonth".toLowerCase(),  String.valueOf(endMonth));
			configMap.put("StartDay".toLowerCase(), String.valueOf(startDay));
			configMap.put("StopDay".toLowerCase(), String.valueOf(endDay));
			
			if (gwDataFolder.length()>0) {
				configMap.put("groundwaterdir".toLowerCase(), gwDataFolder);
			} else {
				configMap.put("groundwaterdir".toLowerCase(), ".");
			}
			
			configMap.put("ShowWreslLog".toLowerCase(), "No");			
			if (DebugCorePlugin.solver.equals("XA") && DebugCorePlugin.log.equals("Log")){
				configMap.put("Solver".toLowerCase(), DebugCorePlugin.solver+"LOG");
			}else{
				configMap.put("Solver".toLowerCase(), DebugCorePlugin.solver);
			}
				
			if (DebugCorePlugin.log.equals("Log") || DebugCorePlugin.log.equals("xa_cbc") || DebugCorePlugin.log.equals("cbc_xa")){
				configMap.put("IlpLog".toLowerCase(), "Yes");
				if (DebugCorePlugin.solver.equals("XA")){
					configMap.put("IlpLogFormat".toLowerCase(), "CplexLp");
				}else if (DebugCorePlugin.solver.equals("LPSolve")){
					configMap.put("IlpLogFormat".toLowerCase(), "LpSolve");
				}else if (DebugCorePlugin.solver.equals("CBC")){
					configMap.put("IlpLogFormat".toLowerCase(), "CplexLp");
				}
				configMap.put("IlpLogVarValue".toLowerCase(), "Yes");
				configMap.put("IlpLogAllCycles".toLowerCase(), "Yes");
			} else {
				configMap.put("IlpLog".toLowerCase(), "No");
				configMap.put("IlpLogFormat".toLowerCase(), "None");
				configMap.put("IlpLogVarValue".toLowerCase(), "No");
				configMap.put("IlpLogAllCycles".toLowerCase(), "No");
			}
			
			if (DebugCorePlugin.log.equals("xa_cbc")){
				configMap.put("solvecompare","xa_cbc");
			}else if (DebugCorePlugin.log.equals("cbc_xa")){
				configMap.put("solvecompare","cbc_xa");
			}
			configMap.put("WreslPlus".toLowerCase(), wreslPlus);
			configMap.put("AllowSvTsInit".toLowerCase(), allowSvTsInit);
			configMap.put("AllRestartFiles".toLowerCase(), allRestartFiles);
			configMap.put("NumberRestartFiles".toLowerCase(), numberRestartFiles);
			
			configMap.put("DatabaseURL".toLowerCase(), databaseURL);
			configMap.put("SQLGroup".toLowerCase(), sqlGroup);
						
			configMap.put("OVOption".toLowerCase(), ovOption);
			
			if (ovFile.trim().equals("")){
				configMap.put("OVOption".toLowerCase(), "0");
				configMap.put("OVFile".toLowerCase(), ".");
			}else if (new File(ovFile).isAbsolute()){
				configMap.put("OVFile".toLowerCase(), ovFile);
			}else{
				configMap.put("OVFile".toLowerCase(), FileProcess.procRelativePath(ovFile, configuration));
			}
			
			if (DebugCorePlugin.launchType==1 || (ms>1 && afterFirstRound)){
				configMap.put("prefixinittodvarfile", "no");
			}
			
			if (DebugCorePlugin.outputCycleToDss){
				configMap.put("outputcycledatatodss", "yes");
			}else{
				configMap.put("outputcycledatatodss", "no");
			}
			
			if (DebugCorePlugin.outputAllCycles){
				configMap.put("outputallcycledata", "yes");
			}else{
				configMap.put("outputallcycledata", "no");
			}
			
			configMap.put("selectedcycleoutput", DebugCorePlugin.outputCycles.replace(" ", ""));
			
			String studyDir = new File(mainFileAbsPath).getParentFile().getParentFile().getAbsolutePath();
			String configName = "__study.config";
			File f = new File(studyDir, configName);
			File dir = new File(f.getParent());
			dir.mkdirs();
			f.createNewFile();
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			 
			out.println("##################################################################################");
			out.println("# Command line Example:");
			out.println("# C:\\wrimsv2_SG\\bin\\runConfig_limitedXA.bat D:\\example\\EXISTING_BO.config");
			out.println("# ");	
			out.println("# Note:");			
			out.println("# 1. This config file and the RUN directory must be placed in the same directory.");
			out.println("# 2. Use relative path to increase the portability.");
			out.println("#    For example, use RUN\\main.wresl for MainFile and DSS\\INIT.dss for InitFile");
			out.println("##################################################################################");	
			out.println("");
			out.println("");
			
			if (useMainFile){
				out.println("MainFile           "+mainFileAbsPath);
			}else{
				out.println("MainFile           "+new File(mainFileAbsPath).getParentFile()+File.separator+new File(configName).getName()+".par");
			}
			out.println("Solver             "+configMap.get("solver".toLowerCase()));
			if (DebugCorePlugin.log.equals("xa_cbc") || DebugCorePlugin.log.equals("cbc_xa")){
				out.println("solvecompare       "+configMap.get("solvecompare".toLowerCase()));
			}
			if (new File(dvarFile).isAbsolute()){
				out.println("DvarFile           "+dvarFile);
				DebugCorePlugin.savedDvFileName=dvarFile;
			}else{
				String procDvarFile=FileProcess.procRelativePath(dvarFile, configuration);
				if (isSensitivity) procDvarFile=FileProcess.createSensitivityFilePath(procDvarFile, sri);
				out.println("DvarFile           " + procDvarFile);
				DebugCorePlugin.savedDvFileName=procDvarFile;
			}
			if (new File(svarFile).isAbsolute()){
				out.println("SvarFile           "+svarFile);
				DebugCorePlugin.savedSvFileName=svarFile;
			}else{
				String procSvarFile=FileProcess.procRelativePath(svarFile, configuration);
				out.println("SvarFile           "+procSvarFile);
				DebugCorePlugin.savedSvFileName=procSvarFile;
			}
			if (new File(gwDataFolder).isAbsolute()){
				out.println("GroundwaterDir     "+gwDataFolder);
			}else{
				out.println("GroundwaterDir     "+FileProcess.procRelativePath(gwDataFolder, configuration));
			}
			out.println("SvarAPart          "+configMap.get("SvarAPart".toLowerCase()));
			out.println("SvarFPart          "+configMap.get("SvarFPart".toLowerCase()));
			if (new File(initFile).isAbsolute()){
				out.println("InitFile           "+initFile);
				DebugCorePlugin.savedInitFileName=initFile;
			}else{
				String procInitFile=FileProcess.procRelativePath(initFile, configuration);
				out.println("InitFile           "+procInitFile);
				DebugCorePlugin.savedInitFileName=procInitFile;
			}
			out.println("InitFPart          "+configMap.get("InitFPart".toLowerCase()));
			out.println("TimeStep           "+configMap.get("TimeStep".toLowerCase()));
			out.println("StartYear          "+configMap.get("StartYear".toLowerCase()));
			out.println("StartMonth         "+configMap.get("StartMonth".toLowerCase()));
			out.println("StartDay           "+configMap.get("StartDay".toLowerCase()));
			out.println("StopYear           "+configMap.get("StopYear".toLowerCase()));
			out.println("StopMonth          "+configMap.get("StopMonth".toLowerCase()));
			out.println("StopDay            "+configMap.get("StopDay".toLowerCase()));
			out.println("IlpLog             "+configMap.get("IlpLog".toLowerCase()));
			out.println("IlpLogFormat       "+configMap.get("IlpLogFormat".toLowerCase()));
			out.println("IlpLogVarValue     "+configMap.get("IlpLogVarValue".toLowerCase()));
			out.println("IlpLogAllCycles    "+configMap.get("IlpLogAllCycles".toLowerCase()));
			out.println("WreslPlus          "+configMap.get("WreslPlus".toLowerCase()));
			out.println("AllowSvTsInit      "+configMap.get("AllowSvTsInit".toLowerCase()));
			out.println("AllRestartFiles    "+configMap.get("AllRestartFiles".toLowerCase()));
			out.println("NumberRestartFiles "+configMap.get("NumberRestartFiles".toLowerCase()));
			out.println("DatabaseURL        "+configMap.get("DatabaseURL".toLowerCase()));
			out.println("SQLGroup           "+configMap.get("SQLGroup".toLowerCase()));
			out.println("OVOption           "+configMap.get("OVOption".toLowerCase()));
			out.println("OVFile             "+configMap.get("OVFile".toLowerCase()));
			out.println("OutputCycleDatatoDss "+configMap.get("OutputCycleDatatoDss".toLowerCase()));
			out.println("OutputAllCycleData "+configMap.get("OutputAllCycleData".toLowerCase()));
			out.println("SelectedCycleOutput "+configMap.get("SelectedCycleOutput".toLowerCase()));
			
			if (DebugCorePlugin.solver.equalsIgnoreCase("LpSolve")) {
				
				out.println("LpSolveConfigFile         callite.lpsolve");
				out.println("LpSolveNumberOfRetries    2");				
				
			}
			if (DebugCorePlugin.launchType==1 || (ms>1 && afterFirstRound)){
				out.println("prefixinittodvarfile  "+configMap.get("prefixinittodvarfile"));
			}
			
			CBCSetting.changeSetting = true;
			if (CBCSetting.changeSetting){
				out.println("cbcTolerancePrimal        "+CBCSetting.cbcTolerancePrimal);
				out.println("cbcTolerancePrimalRelax   "+CBCSetting.cbcTolerancePrimalRelax);
				out.println("cbcToleranceWarmPrimal    "+CBCSetting.cbcToleranceWarmPrimal);
				out.println("cbcToleranceInteger       "+CBCSetting.cbcToleranceInteger);
				out.println("cbcToleranceIntegerCheck  "+CBCSetting.cbcToleranceIntegerCheck);
				out.println("cbcToleranceZero          "+CBCSetting.cbcToleranceZero);
			}
			
			out.close();
		
			configFilePath= new File(studyDir, configName).getAbsolutePath();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return configFilePath;
			
	}
	
	public void generateDatabaseUserProfileFile(String mainFileAbsPath){
		IConnectionProfile[] profiles = ProfileManager.getInstance().getProfiles();
		int i=0;
		boolean isProfileCreated=false;
		while (i<profiles.length && !isProfileCreated){
			Properties baseProperties = profiles[i].getBaseProperties();
			String urlProperty=baseProperties.getProperty(IJDBCConnectionProfileConstants.URL_PROP_ID);
			if (urlProperty.equalsIgnoreCase(databaseURL)){
				String username=baseProperties.getProperty(IJDBCConnectionProfileConstants.USERNAME_PROP_ID);
				String password=baseProperties.getProperty(IJDBCConnectionProfileConstants.PASSWORD_PROP_ID);
				
				String studyDir = new File(mainFileAbsPath).getParentFile().getParentFile().getAbsolutePath();
				String profileFileName = "__study.config.dpf";
				File f = new File(studyDir, profileFileName);
				File dir = new File(f.getParent());
				dir.mkdirs();
				try {
					f.createNewFile();
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
					out.println(Encryption.procEncryption(username));
					out.println(Encryption.procEncryption(password));
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isProfileCreated=true;
			}
			i++;
		}
	}
		
	/**
	 * Throws an exception with a new status containing the given
	 * message and optional exception.
	 * 
	 * @param message error message
	 * @param e underlying exception
	 * @throws CoreException
	 */
	private void abort(String message, Throwable e) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, DebugCorePlugin.getDefault().getDescriptor().getUniqueIdentifier(), 0, message, e));
	}
	
	/**
	 * Returns a free port number on localhost, or -1 if unable to find a free port.
	 * 
	 * @return a free port number on localhost, or -1 if unable to find a free port
	 */
	public static int findFreePort() {
		ServerSocket socket= null;
		try {
			socket= new ServerSocket(0);
			return socket.getLocalPort();
		} catch (IOException e) { 
			WPPException.handleException(e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					WPPException.handleException(e);
				}
			}
		}
		return -1;		
	}		
}
