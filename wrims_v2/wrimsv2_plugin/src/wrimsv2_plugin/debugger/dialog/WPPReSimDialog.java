package wrimsv2_plugin.debugger.dialog;

import java.util.ArrayList;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import wrimsv2_plugin.debugger.core.DebugCorePlugin;
import wrimsv2_plugin.debugger.exception.WPPException;
import wrimsv2_plugin.debugger.view.WPPVarDetailView;

public class WPPReSimDialog extends PopupDialog {
	Combo year;
	Combo month;
	Combo day;
	Combo cycle;
	
	
	public WPPReSimDialog(Shell parent, int shellStyle,
			boolean takeFocusOnOpen, boolean persistSize,
			boolean persistLocation, boolean showDialogMenu,
			boolean showPersistActions, String titleText, String infoText) {
		super(parent, shellStyle, takeFocusOnOpen, persistSize, persistLocation,
				showDialogMenu, showPersistActions, titleText, infoText);
		// TODO Auto-generated constructor stub
	}

	public void open(int i){
		create();
		getShell().setSize(450, 250);
		open();
	}

	@Override
	 protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		RowLayout layout=new RowLayout(SWT.VERTICAL);
		dialogArea.setLayout(layout);
		
		Composite line1=new Composite(dialogArea, SWT.NONE);
		layout=new RowLayout(SWT.HORIZONTAL);
		layout.justify=true;
		layout.pack=true;
		line1.setLayout(layout);
		Label label1=new Label(line1, SWT.NONE);
		label1.setText("Re-simulate from:");
		
		Composite line2=new Composite(dialogArea, SWT.NONE);
		line2.setLayout(layout);
		final Button but1=new Button(line2, SWT.RADIO);
		but1.setText("Cycle");
		cycle=new Combo(line2, SWT.BORDER);
		String currCycle=DebugCorePlugin.debugSet.getComboCycle().getText();
		fillCombo(cycle, 1, Integer.parseInt(currCycle));
		cycle.setText(currCycle);
		Label label2=new Label(line2, SWT.NONE);
		label2.setText("of current step");
		
		Composite line3=new Composite(dialogArea, SWT.NONE);
		line3.setLayout(layout);
		final Button but2=new Button(line3, SWT.RADIO);
		but2.setText("1st cycle of year");
		year=new Combo(line3, SWT.BORDER);
		String currYear=DebugCorePlugin.debugSet.getComboYear().getText();
		fillCombo(year, DebugCorePlugin.startYear, Integer.parseInt(currYear));
		year.setText(currYear);
		Label label3=new Label(line3, SWT.NONE);
		label3.setText("month");
		month=new Combo(line3, SWT.BORDER);
		String currMonth=DebugCorePlugin.debugSet.getComboMonth().getText();
		fillCombo(month, 1, 12);
		month.setText(currMonth);
		Label label4=new Label(line3, SWT.NONE);
		label4.setText("day");
		day=new Combo(line3,SWT.BORDER);
		String currDay=DebugCorePlugin.debugSet.getComboDay().getText();
		fillCombo(day, 1, 31);
		day.setText(currDay);
		
		Button but3=new Button(dialogArea, SWT.CHECK);
		but3.setText("Re-compile wresl code");
		
		Button but4=new Button(dialogArea, SWT.CHECK);
		but4.setText("Re-read data from SV file");
		
		Composite line6=new Composite(dialogArea, SWT.NONE);
		line6.setLayout(layout);
		Button ok = new Button(line6, SWT.PUSH);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				
				close();
			}
		});
		
		Button cancel = new Button(line6, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				close();
			}
		});
		
		but1.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				but2.setSelection(false);
			}
			
		});
		
		but2.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				but1.setSelection(false);
			}
			
		});
		
		return dialogArea;
	 }
	
	public void fillCombo(Combo combo, int start, int end){
		combo.removeAll();
		for (int i=start; i<=end; i++){
			combo.add(String.valueOf(i));
		}
	}
}