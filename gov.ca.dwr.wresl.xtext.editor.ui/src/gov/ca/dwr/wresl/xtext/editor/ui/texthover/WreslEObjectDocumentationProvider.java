package gov.ca.dwr.wresl.xtext.editor.ui.texthover;

import gov.ca.dwr.wresl.xtext.editor.wreslEditor.DVar;
import gov.ca.dwr.wresl.xtext.editor.wreslEditor.impl.DefineImpl;
import gov.ca.dwr.wresl.xtext.editor.wreslEditor.impl.GoalImpl;
import gov.ca.dwr.wresl.xtext.editor.wreslEditor.impl.IdentImpl;
import gov.ca.dwr.wresl.xtext.editor.wreslEditor.impl.TermImpl;
import gov.ca.dwr.wresl.xtext.editor.wreslEditor.impl.UnaryImpl;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;

import wrimsv2_plugin.debugger.core.DebugCorePlugin;
import wrimsv2_plugin.debugger.exception.WPPException;
import wrimsv2_plugin.debugger.model.WPPValue;

public class WreslEObjectDocumentationProvider implements
		IEObjectDocumentationProvider {
	
	public String getDocumentation(EObject o) {
		if (o instanceof DefineImpl){
			String name=((DefineImpl) o).getName();
			return "value : "+getValue(name);
		}else if (o instanceof GoalImpl){
			String name=((GoalImpl) o).getName();
			return "constraint : "+getConstraint(name);
		}else if (o instanceof IdentImpl){
			String name=((IdentImpl) o).getName();
			return "value : "+getValue(name);
		}else{
			return null;
		}
	}

	private String getValue(String varName){  
		if (!DebugCorePlugin.isDebugging) return "Debugging is not started yet";
        if (DebugCorePlugin.dataStack==null) return "This variable is not in this cycle.";
        WPPValue[] dataStack=(WPPValue[]) DebugCorePlugin.dataStack;
        
        for (int i = 0; i < dataStack.length; i++) {
            if (varName.equalsIgnoreCase(dataStack[i].getVariableString())){
            	try {
					if (dataStack[i].hasVariables()){
						String hoverInfo="";
						WPPValue[] subValues=(WPPValue[]) dataStack[i].getValues();
						for (int j=0; j<subValues.length; j++){
							hoverInfo=hoverInfo+subValues[j].getVariableString()+"= "+subValues[j].getValueString()+"; ";
						}
						return hoverInfo;
					}else{
						return dataStack[i].getValueString();
					}
				} catch (DebugException e) {
					WPPException.handleException(e);
				}
            }
        }
        return "This variable is not in this cycle.";
	}
	
	private String getConstraint(String gName){  
		if (!DebugCorePlugin.isDebugging) return "Debugging is not started yet";
        if (DebugCorePlugin.goalStack==null) return "This goal is not in this cycle.";
        WPPValue[] goalStack=(WPPValue[]) DebugCorePlugin.goalStack;
        
        for (int i = 0; i < goalStack.length; i++) {
            if (gName.equalsIgnoreCase(goalStack[i].getVariableString())){
            	try {
					if (goalStack[i].hasVariables()){
						String hoverInfo="";
						WPPValue[] subValues=(WPPValue[]) goalStack[i].getValues();
						for (int j=0; j<subValues.length; j++){
							hoverInfo=hoverInfo+subValues[j].getVariableString()+"= "+subValues[j].getValueString()+"; ";
						}
						return hoverInfo;
					}else{
						return goalStack[i].getValueString();
					}
				} catch (DebugException e) {
					WPPException.handleException(e);
				}
            }
        }
        return "This goal is not in this cycle.";
	}
}