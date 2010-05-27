//package calsim.installer;  // comment out for external build

import java.io.*;
import java.util.*;
import com.sun.wizards.core.*;
//import com.sun.wizards.services.*;

/**
 * The generic task is a sample task that does
 * nothing.  The task is initialized with the
 * amount of time the task should take.  The
 * task merely waits for the specified time.
 */
public class BatchFileConfigTask extends Task implements Serializable {

      public static final String SEQUENCE_NAME = "Batch File Configuration";
	/**
	 * A flag indicating whether or not this task has been canceled.
	 */
//	private transient boolean canceled = false;

	/**
	 * Creates a CustomTask that waits the specified
	 * length of time, in seconds.
	 *
	 * @param completionTime	The number of seconds this task
	 *				takes to complete.
	 */
	public BatchFileConfigTask() {}

	/**
	 * Perform this task.  This method merely waits the amount
	 * of time specified in the constructor.
	 */
	public void perform() {
		setProgress(5);
		try {
			writeOutWinBatch();
			writeMSRWinBatch();
		} catch(IOException ioe){
			System.err.println("Error installing calsim");
		}
	setProgress(100);
    }

	/**
	 *
	 */
	public void writeMSRWinBatch() throws IOException{
		String idir= com.sun.install.products.InstallConstants.currentInstallDirectory;
		WizardState ws = getWizardState();
		idir = (String) ws.getData(idir);
		PrintWriter pw = new PrintWriter(new FileWriter(idir+"/bin/msr.bat"));
		pw.println("@echo off");
		pw.println("rem ###############");
		pw.println("rem Batch file for running calsim  client");
		pw.println("rem ###############");
		pw.println("rem generated by install script");
		pw.println("rem ###############");
		//pw.println("set CALSIM_HOME="+idir);
		//pw.println("");
		//pw.println("if \"%CALSIM_HOME%\" == \"\" goto nohome");
		//pw.println("");
		pw.println("rem ###############");
		pw.println("rem Add to the path the locations of the dlls and the schematic images");
		pw.println("rem ###############");
		//pw.println("");
		//pw.println("set path=%path%;%calsim_home%\\bin;");
		pw.println("set path="+idir+"/bin;"+idir+"/../wrims/Schematic/images;%path%"); //CB added images path, but not needed if in classpath
		pw.println("set path="+idir+"/bin%path%");
		//pw.println("rem set Path=%calsim_home%\\bin;%Path%");
		//pw.println("");
		pw.println("rem ###############");
		pw.println("rem starting calsim ");
		pw.println("rem ###############");
		//pw.println("start %calsim_home%/jre/bin/jre -mx44m -Dcalsim.home="+idir+" -cp \"%calsim_home%\\lib\\calsim.jar;%calsim_home%\\lib\\vista.jar;%calsim_home%\\lib\\Acme.jar;%calsim_home%\\lib\\COM.jar;%calsim_home%\\lib\\swingall.jar;%calsim_home%\\lib\\jpython.jar;%calsim_home%\\lib\\jhall.jar;%calsim_home%\\lib\\collections.jar;%calsim_home%\\lib\\calsim-help.jar;%calsim_home%\\lib\\xml.jar\" calsim.gui.CalsimGui %1");
		pw.println("start "+idir+/*CB "\\jre\\bin\\jre -mx172m -Dcalsim.home="*/ "/jre/bin/java -mx172m -Dcalsim.home="+idir+" -cp \""+idir+"/lib/calsim.jar;"+idir+"/lib/vista.jar;"+idir+"/lib/Acme.jar;"+idir+"/lib/COM.jar;"/*CB +idir+"/lib/swingall.jar;"*/+idir+"/lib/jpython.jar;"+idir+"/lib/jhall.jar;"+idir+"/lib/collections.jar;"+idir+"/lib/calsim-help.jar;"+idir+"/lib/data.jar;"+idir+"/lib/hec.jar;"+idir+"/lib/heclib.jar;"+idir+"/lib/JGo.jar;"+idir+"/lib/JGoSVG.jar;"+idir+"/lib/msgsystem.jar;"+idir+"/lib/rma.jar;"+idir+"/lib/WrimsSchematic.jar;"+idir+"/lib/xml.jar\" calsim.msw.MSWGui %1");
		pw.println("goto end");
		pw.println("");
		//pw.println(":nohome");
		//pw.println("echo \"Please set CALSIM_HOME to directory where calsim  is installed\"");
		//pw.println("echo \"Example: set CALSIM_HOME=c:/calsim\"");
		//pw.println("");
		pw.println(":end");
		pw.close();
	 }

	/**
	 *
	 */
	public void writeOutWinBatch() throws IOException {
		String idir= com.sun.install.products.InstallConstants.currentInstallDirectory;
		WizardState ws = getWizardState();
		idir = (String) ws.getData(idir);
		PrintWriter pw = new PrintWriter(new FileWriter(idir+"/bin/WRIMS.bat"));
		pw.println("@echo off");
		pw.println("rem ###############");
		pw.println("rem Batch file for running WRIMS client");
		pw.println("rem ###############");
		pw.println("rem generated by install script");
		pw.println("rem ###############");
		//pw.println("set CALSIM_HOME="+idir);
		//pw.println("");
		//pw.println("if \"%CALSIM_HOME%\" == \"\" goto nohome");
		//pw.println("");
		pw.println("rem ###############");
		pw.println("rem Add to the path the locations of the dlls and the schematic images");
		pw.println("rem ###############");
		//pw.println("");
		//pw.println("set path=%path%;%calsim_home%/bin;");
		pw.println("set path="+idir+"/bin;"+idir+"/../wrims/Schematic/images;%path%"); //CB added images path, but not needed if in classpath
		//pw.println("set path="+idir+"/bin%path%");
		//pw.println("rem set Path=%calsim_home%/bin;%Path%");
		//pw.println("");
		pw.println("rem ###############");
		pw.println("rem starting WRIMS ");
		pw.println("rem ###############");
		//pw.println("start %calsim_home%/jre/bin/jre -mx44m -Dcalsim.home="+idir+" -cp \"%calsim_home%/lib/calsim.jar;%calsim_home%/lib/vista.jar;%calsim_home%/lib/Acme.jar;%calsim_home%/lib/COM.jar;%calsim_home%/lib/swingall.jar;%calsim_home%/lib/jpython.jar;%calsim_home%/lib/jhall.jar;%calsim_home%/lib\\collections.jar;%calsim_home%/lib/calsim-help.jar;%calsim_home%/lib/xml.jar\" calsim.gui.CalsimGui %1");
		pw.println("start "+idir+/*CB "/jre/bin/jre -mx172m -Dcalsim.home="*/ "/jre/bin/java -mx172m -Dcalsim.home="+idir+" -cp \""+idir+"/lib/calsim.jar;"+idir+"/lib/vista.jar;"+idir+"/lib/Acme.jar;"+idir+"/lib/COM.jar;"/*CB+idir+"/lib/swingall.jar;"*/+idir+"/lib/jpython.jar;"+idir+"/lib/jhall.jar;"+idir+"/lib/collections.jar;"+idir+"/lib/calsim-help.jar;"+idir+"/lib/data.jar;"+idir+"/lib/hec.jar;"+idir+"/lib/heclib.jar;"+idir+"/lib/JGo.jar;"+idir+"/lib/JGoSVG.jar;"+idir+"/lib/msgsystem.jar;"+idir+"/lib/rma.jar;"+idir+"/lib/WrimsSchematic.jar;"+idir+"/lib/xml.jar\" calsim.gui.CalsimGui %1");
		pw.println("goto end");
		pw.println("");
		//pw.println(":nohome");
		//pw.println("echo \"Please set CALSIM_HOME to directory where calsim  is installed\"");
		//pw.println("echo \"Example: set CALSIM_HOME=c:/calsim\"");
		//pw.println("");
		pw.println(":end");
		pw.close();
	}

	/**
	 * Cancel this task.
	 */
	public void cancel() {
//		canceled = true;  IT'S CURRENTLY NOT READ ANYWHERE
	}

	/**
	 * Add the runtime class requirements to the specified vector.
	 * @param resourceVector The vector containing all runtime resources for this wizard.
	 */
	public void addRuntimeResources(Vector resourceVector) {
		resourceVector.addElement(new String[] {null, "BatchFileConfigTask"});
	}
}
