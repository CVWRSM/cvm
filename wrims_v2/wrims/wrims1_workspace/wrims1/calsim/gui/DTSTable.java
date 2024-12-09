/*

Copyright (C) 1998, 2000 State of California, Department of Water Resources.

This program is licensed to you under the terms of the GNU General Public
License, version 2, as published by the Free Software Foundation.

You should have received a copy of the GNU General Public License along
with this program; if not, contact Dr. Sushil Arora, below, or the
Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.

THIS SOFTWARE AND DOCUMENTATION ARE PROVIDED BY THE CALIFORNIA DEPARTMENT
OF WATER RESOURCES AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE CALIFORNIA DEPARTMENT OF WATER RESOURCES OR ITS
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OR SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

For more information, contact:

Dr. Sushil Arora
California Dept. of Water Resources
Office of State Water Project Planning, Hydrology and Operations Section
1416 Ninth Street
Sacramento, CA  95814
916-653-7921
sushil@water.ca.gov

*/

package calsim.gui;
import hec.io.DataContainer;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;

import vista.gui.CursorChangeListener;
import vista.gui.VistaUtils;
import vista.set.Pathname;
import calsim.app.AppUtils;
import calsim.app.DTSTableModel;
import calsim.app.DerivedTimeSeries;
import calsim.app.MTSTableModel;
import calsim.app.MultipleTimeSeries;
import calsim.app.Project;
//import java.io.*;
//import javax.swing.table.*;
//import javax.swing.text.*;
//import javax.swing.tree.*;
//import com.sun.java.util.collections.Arrays;
//import java.text.*;
/**
  * Derived Timeseries table
  *
  * @author Nicky Sandhu
  * @version $Id: DTSTable.java,v 1.1.4.45 2001/10/23 16:28:35 jfenolio Exp $
  */
public class DTSTable extends MPanel{
  public JButton opencurrent; 
  public static boolean DEBUG = false;
  public static String [] itemText = { "Print",
				       "Load",
				       "Save",
				       "Delete Row",
				       "Add Row",
				       "Insert Row",
				       "Quit",
				       "Retrieve"

  };
  public static char [] itemChars = { 'p','l','s','d','a','i','q','r'
  };
  public static String [] toolTipText = {"Prints table",
					 "Loads table from file",
					 "Saves table to file",
					 "Deletes selected row",
					 "Adds row",
					 "Inserts row at current selection",
					 "Closes frame",
					 "Retrieves and Calculates Data"
  };
  public static int [] itemKeys = { KeyEvent.VK_P,
				    KeyEvent.VK_L,
				    KeyEvent.VK_S,
				    KeyEvent.VK_D,
				    KeyEvent.VK_A,
				    KeyEvent.VK_I,
				    KeyEvent.VK_Q,
				    KeyEvent.VK_R
  };
  /** 
   * Provided for CalLite GUI access to _dts. Added by Tad Slawecki/LimnoTech 12/2013
   * 
   * @return
   */
  public DerivedTimeSeries getDTS() {
  return _dts;
  }

  /** 
   * Provided for CalLite GUI access to _mts. Added by Tad Slawecki/LimnoTech 12/2013
   * 
   * @return
   */
  public MultipleTimeSeries getMTS() {
  return _mts;
  }
  /**
   *
   */
  public DTSTable(DerivedTimeSeries dts){
    setLayout(new BorderLayout());
    _table = new JTable(new DTSTableModel(dts));
    _table.registerKeyboardAction(new CursorChangeListener() {
      public void doWork(){
				stopEditing();
      }
    }, null, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), JComponent.WHEN_IN_FOCUSED_WINDOW );
    int uw = 66;
    _table.getColumnModel().getColumn(0).setPreferredWidth(1*uw);
    _table.getColumnModel().getColumn(1).setPreferredWidth(5*uw);
    _table.getColumnModel().getColumn(2).setPreferredWidth(1*uw);
    _table.getColumnModel().getColumn(3).setPreferredWidth(5*uw);
    _table.getColumnModel().getColumn(4).setPreferredWidth(5*uw);
		_table.sizeColumnsToFit(-1);  //CB TODO  use doLayout method instead
//	_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	_lsm = _table.getSelectionModel();
    _nameField = new JTextField(dts.getName(),25);
    _nameField.setEditable(false);
    _nameField.addActionListener(new CursorChangeListener(){
      public void doWork(){
				changeNameToField();
      }
    });
    
    _table.setPreferredScrollableViewportSize(_table.getPreferredSize());  //CB added - but still CANNOT SEE TABLE
    
    JLabel nameLabel = new JLabel("Derived Time Series: ");
    JPanel namePanel = new JPanel();
    namePanel.setLayout(new BorderLayout());
    namePanel.add(nameLabel,BorderLayout.WEST);
    namePanel.add(_nameField,BorderLayout.CENTER);
    namePanel.add(createButtonPanel(),BorderLayout.SOUTH);
    add(namePanel, BorderLayout.NORTH);
    add(new JScrollPane(_table),BorderLayout.CENTER);
    setDTS(dts,null);
  }

  public JPanel createButtonPanel() {
	JPanel holder = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
	JButton addrow = new JButton("Add");
	JButton insertrow = new JButton("Insert");
	JButton deleterow = new JButton("Delete");
	opencurrent = new JButton("Open");
//	JLabel filler = new JLabel("                                                  ");
//	JLabel filler1 = new JLabel("      ");
//	JLabel filler2 = new JLabel("      ");
//	JLabel filler3 = new JLabel("      ");
    addrow.addActionListener( new CursorChangeListener(){
      public void doWork(){
	    add();
      }
    });
    insertrow.addActionListener( new CursorChangeListener(){
      public void doWork(){
	    insert();
      }
    });
    deleterow.addActionListener( new CursorChangeListener(){
      public void doWork(){
	    delete();
      }
    });
    //holder.add(filler);
    holder.add(addrow); //holder.add(filler1);
    holder.add(insertrow); //holder.add(filler2);
    holder.add(deleterow); //holder.add(filler3);
    holder.add(opencurrent);
    return holder;
  }
  /**
   *
   */
  public String getFrameTitle(){
    return "Derived Time Series";
  }
  /**
    *
    */
  public JMenuBar getJMenuBar(){
    if ( _mbar == null ) _mbar = createJMenuBar();
    return _mbar;
  }
  /**
   *
   */
  public JMenuBar createJMenuBar(){
/*    JMenuItem printItem = new JMenuItem(itemText[0]);
    printItem.setToolTipText(toolTipText[0]);
    printItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[0],KeyEvent.CTRL_MASK));
    printItem.setMnemonic(itemChars[0]);
    printItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	print();
      }
    });
    //
    JMenuItem loadItem = new JMenuItem(itemText[1]);
    loadItem.setToolTipText(toolTipText[1]);
    loadItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[1],KeyEvent.CTRL_MASK));
    loadItem.setMnemonic(itemChars[1]);
    loadItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	load();
      }
    });
    //
    JMenuItem saveItem = new JMenuItem(itemText[2]);
    saveItem.setToolTipText(toolTipText[2]);
    saveItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[2],KeyEvent.CTRL_MASK));
    saveItem.setMnemonic(itemChars[2]);
    saveItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	save();
      }
    });
    //
    JMenuItem exportItem = new JMenuItem("Export");
    exportItem.setToolTipText("Exports data to dss");
    exportItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	export();
      }
    });
    //
    JMenuItem quitItem = new JMenuItem(itemText[6]);
    quitItem.setToolTipText(toolTipText[6]);
    quitItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[6],KeyEvent.CTRL_MASK));
    quitItem.setMnemonic(itemChars[6]);
    quitItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	quit();
      }
    });
*/
    //
    JMenuItem deleteItem = new JMenuItem(itemText[3]);
    deleteItem.setToolTipText(toolTipText[3]);
    deleteItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[3],KeyEvent.CTRL_MASK));
    deleteItem.setMnemonic(itemChars[3]);
    deleteItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	delete();
      }
    });
    //
    JMenuItem addRowItem = new JMenuItem(itemText[4]);
    addRowItem.setToolTipText(toolTipText[4]);
    addRowItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[4],KeyEvent.CTRL_MASK));
    addRowItem.setMnemonic(itemChars[4]);
    addRowItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	add();
      }
    });
    //
    JMenuItem insertItem = new JMenuItem(itemText[5]);
    insertItem.setToolTipText(toolTipText[5]);
    insertItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[5],KeyEvent.CTRL_MASK));
    insertItem.setMnemonic(itemChars[5]);
    insertItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
	insert();
      }
    });
    //
    int index=7;
    JMenuItem retrieveItem = new JMenuItem(itemText[index]);
    retrieveItem.setToolTipText(toolTipText[index]);
    retrieveItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[index],KeyEvent.CTRL_MASK));
    retrieveItem.setMnemonic(itemChars[index]);
    retrieveItem.addActionListener( new CursorChangeListener(){
      public void doWork(){
				retrieve();
      }
    });
/*
    JMenu tableMenu = new JMenu("Table");
    tableMenu.add(loadItem); tableMenu.add(saveItem);
    tableMenu.addSeparator();
    tableMenu.add(exportItem);
    tableMenu.addSeparator();
    tableMenu.add(printItem);
    tableMenu.addSeparator();
    tableMenu.add(retrieveItem);
    tableMenu.addSeparator();
    tableMenu.add(quitItem);
    tableMenu.setMnemonic('t');
*/
    //
    JMenu editMenu = new JMenu("Edit");
    editMenu.add(addRowItem); editMenu.add(insertItem); editMenu.add(deleteItem);
    editMenu.setMnemonic('e');
    //
    _mbar = new JMenuBar();
//    _mbar.add(tableMenu);
//    _mbar.add(editMenu);
    return _mbar;
  }
  /**
   *
   */
  void print(){
    if (DEBUG) System.out.println("Print");
    stopEditing();
    GuiUtils.print(this);
  }
  /**
   *sets DTS in the table to null
   */
  public void setDTS() {
	_dts = null;
	_nameField.setText("");

  }
  /**
   * sets the DTS displayed in the table
   */
  public void setDTS(DerivedTimeSeries dts, MultipleTimeSeries mts){
    if (dts != null) {
			dtm = new DTSTableModel(dts);
			mtm = null;
      _table.setModel(dtm);
     } else {
			mtm = new MTSTableModel(mts);
			dtm = null;
	    _table.setModel(new MTSTableModel(mts));
    }
    _table.tableChanged(new TableModelEvent(_table.getModel()));
    if (dts != null) {
      _nameField.setText(dts.getName());
     } else {
      _nameField.setText(mts.getName());
    }
    _dts = dts;
    _mts = mts;
    _modified = false;
    //  set operation editor
    JComboBox opEditor = new JComboBox();
    if (dts != null) {
      opEditor.addItem("+");
      opEditor.addItem("-");
      opEditor.addItem("*");
      opEditor.addItem("/");
     } else {
	  opEditor.setEnabled(false);
    }
    // register tab as editing stopped
    _table.registerKeyboardAction( new AbstractAction("editingStopped"){
      public void actionPerformed(ActionEvent evt){
				stopEditing();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_TAB,0), JComponent.WHEN_FOCUSED);
    // set derived time series editor
/*    DerivedTimeSeries [] dtsArray = AppUtils.getGlobalDTSList();
    Project prj = AppUtils.getCurrentProject();
    DerivedTimeSeries [] dtsArray1 = prj.getDTSList();
    DerivedTimeSeries [] dtsArray2 = prj.getDTSList();
    if ( dtsArray1 != null ){
      dtsArray2 = new DerivedTimeSeries [dtsArray.length + dtsArray1.length];
      System.arraycopy(dtsArray, 0, dtsArray2, 0, dtsArray.length);
      System.arraycopy(dtsArray1, 0, dtsArray2, dtsArray.length, dtsArray1.length);
    }
    else
      dtsArray2 = dtsArray;
    //
    Arrays.sort(dtsArray2, new DTSComparator());
    dtsEditor.addItem("        ");
    for(int i = 0; i < dtsArray2.length; i++){
      if ( ! dtsArray2[i].getName().equals(dts.getName()))
	  dtsEditor.addItem(dtsArray2[i].getName());
    }

 */

      JComboBox dtsEditor = new JComboBox();// = new JComboBox();
//      JComboBox bpartEditor = new JComboBox();
      dtsArray = null;
      dtsArray = AppUtils.getCurrentProject().getDTSNames();
//      bs = null;
//			bs = AppUtils.getCurrentProject().getBParts();
			if (dtsArray != null)  {
        if (AppUtils.getCurrentProject().getDTSMod()) {
				  dtsArray = GuiUtils.sortArray(dtsArray);
          //dtsEditor.addItem(" ");
          dtsEditor = new JComboBox(dtsArray);
          //for(int i = 0; i < dtsArray.length; i++) dtsEditor.addItem(dtsArray[i]);
          _dtsEditor = dtsEditor;
/*
          bpartEditor.addItem(" ");
          if (bs == null) {_bpartEditor = bpartEditor; } else {
           bpartEditor = new JComboBox(bs);
          //for (int i=0; i<bs.length; i++) bpartEditor.addItem(bs[i]);
          _bpartEditor = bpartEditor;}
*/
        }
      } else {
				//String[] blank = {" "};
				dtsEditor = new JComboBox();
				_dtsEditor = dtsEditor;
			}
    AppUtils.getCurrentProject().setDTSMod(false);
		//make var type editor
    varEditor.addItem(AppUtils.SVAR);
    varEditor.addItem(AppUtils.DVAR);
    /*
    varEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	      System.out.println("3");
	      JComboBox bpartEditor = new JComboBox();
				int row = _table.getSelectedRow();
				String var = (String)varEditor.getSelectedItem();
				System.out.println(var);
				if (var == null) return;
				if ( var.equals(AppUtils.SVAR) ) {
					bpartEditor.addItem(" ");
					for (int i=0; i<bsv.length; i++) bpartEditor.addItem(bsv[i]);
					_bpartEditor = bpartEditor;
				} else {
					bpartEditor.addItem(" ");
					for (int i=0; i<bdv.length; i++) bpartEditor.addItem(bdv[i]);
					_bpartEditor = bpartEditor;
				}
			}
		});
    String [] bPartArray = {""}, cPartArray = {""};
    if ( AppUtils.getCurrentBParts() != null )
      bPartArray = AppUtils.getCurrentBParts();
    if ( AppUtils.getCurrentCParts() != null )
      cPartArray = AppUtils.getCurrentCParts();
    _firstBPart = bPartArray[0];
    _firstCPart = cPartArray[0];
    */
    _bpartEditor.setEditable(true);
    cpartEditor.setEditable(true);
    _bpartEditor.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
        int row = _table.getSelectedRow();
				if (!start) findSetCPart(dtm,mtm,row);
			}
		});
		start = false;
    varEditor.removeAllItems();
    varEditor.addItem(AppUtils.SVAR);
    varEditor.addItem(AppUtils.DVAR);
    varEditor.setSelectedIndex(1);

    //

    if (dts != null) {
      _table.getColumn("Derived Time Series").setCellEditor(new DefaultCellEditor(_dtsEditor));
      _table.getColumn("Operator").setCellEditor(new DefaultCellEditor(opEditor));
      _table.getColumn("Dvar/Svar").setCellEditor(new DefaultCellEditor(varEditor));
      _table.getColumn("B part").setCellEditor(new DefaultCellEditor(_bpartEditor));
      _table.getColumn("C part").setCellEditor(new DefaultCellEditor(cpartEditor));
    } else {
      _table.getColumn("Derived Time Series").setCellEditor(new DefaultCellEditor(_dtsEditor));
      _table.getColumn("Dvar/Svar").setCellEditor(new DefaultCellEditor(varEditor));
      _table.getColumn("B part").setCellEditor(new DefaultCellEditor(_bpartEditor));
      _table.getColumn("C part").setCellEditor(new DefaultCellEditor(cpartEditor));
    }
    _table.selectAll();
  }
  /**
   *gets the cpart associated with the chosen bpart in a dts or mts table
   */
  public void findSetCPart(DTSTableModel dtm, MTSTableModel mtm, int r) {
		String b = _bpartEditor.getText().toUpperCase();
		String var = (String)varEditor.getSelectedItem();
		_prj = AppUtils.getCurrentProject();
    Hashtable _svList = _prj.getSVHashtable();
    Hashtable _dvList = _prj.getDVHashtable();
		if (_svList == null || _dvList == null) return;
		if (var.equals(AppUtils.SVAR)) {
			String c = (String)_svList.get(b.toUpperCase());
			if (dtm != null) {
				dtm.setValueAt(c,r,4);
			} else {
			  mtm.setValueAt(c,r,3);
			}
		} else if (var.equals(AppUtils.DVAR)) {
			String c = (String)_dvList.get(b.toUpperCase());
			if (dtm != null) {
				dtm.setValueAt(c,r,4);
			} else {
			  mtm.setValueAt(c,r,3);
			}
		}
	}

  /**
   *
   */
  void load(){
    if (DEBUG) System.out.println("Load");
    stopEditing();
    try {
      String dtsfile = VistaUtils.getFilenameFromDialog(this,FileDialog.LOAD,
							"dts","DTS File");
      if ( dtsfile == null ) return;
      DerivedTimeSeries dts = DerivedTimeSeries.load(dtsfile);
      if (DEBUG) System.out.println("LOADED: " + dts.getName());
      GuiUtils.checkAndAddToProject(this,dts);
      if (DEBUG) System.out.println("Added to project: " + dts.getName());
      if ( _modified ){
	int opt = JOptionPane.showConfirmDialog
	  (this,
	   "Current table has been modified! Do you want to save to file?",
	   "Warning", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
	if ( opt == JOptionPane.YES_OPTION ){
	  save();
	  if ( ! _modified ) setDTS(dts,null);
	}else{
	  setDTS(dts,null);
	}
      }
    }catch(Exception e){
      VistaUtils.displayException(this,e);
    }
  }
  /**
    *
    */
  void export(){
    stopEditing();
    String pathname = (String)
      JOptionPane.showInputDialog(this,"Pathname : ","Input Pathname",
				  JOptionPane.PLAIN_MESSAGE,
				  null,null,
				  _dts.getPathname().toString());
    Pathname path = Pathname.createPathname(pathname);
    if ( path == null ) return;
    String dssfile = VistaUtils.getFilenameFromDialog(this,FileDialog.SAVE,
						      "dss","DSS File");
    if ( dssfile == null ) return;
    AppUtils.exportToDSS(_dts,dssfile,pathname);
  }
  /**
   *
   */

  void save() {
    DtsTreePanel.getCurrentModel().setNodeName(_dts.getName());
  }

/*
  void save(){
    try {
      stopEditing();
      String dtsfile = VistaUtils.getFilenameFromDialog(this,FileDialog.SAVE,
							"dts","DTS File");
      if ( dtsfile == null ) return;
	  File f = new File(dtsfile);
	  String fname = f.getName();
	  if (DtsTreeModel.getNewNode() != null) {
		DtsTreeModel.setNewNodeName(fname);
      }
      if ( dtsfile.indexOf((int)'.') == -1 )  //no extension
 	dtsfile += ".dts";  //set default extension
      _dts.save(new FileOutputStream(dtsfile));
      _modified = false;
    }catch(Exception e){
      VistaUtils.displayException(this,e);
    }
  }
*/
  /**
   * delete rows
   */
  void delete(){
    if (_dts != null) {
      if (DEBUG) System.out.println("Delete");
      // get user selected rows
      stopEditing();
      int [] ri = _table.getSelectedRows();
      if ( ri == null || ri.length == 0 ){
        JOptionPane.showMessageDialog(this,"Message",
	  			    "Select a row first!",
	  			    JOptionPane.PLAIN_MESSAGE);
        return;
      }
      int numberDeleted = 0;
      for(int i=0; i < ri.length; i++) {
        int currentIndex = ri[i] - numberDeleted;
	  if ( currentIndex >= _dts.getNumberOfDataReferences() ) {
	    continue;
	  }
	  _dts.remove(currentIndex);
	  numberDeleted++;
      }
      _modified = true;
      _table.tableChanged(new TableModelEvent(_table.getModel()));
     } else {
      if (DEBUG) System.out.println("Delete");
      stopEditing();
      // get user selected rows
      int[] ri = _table.getSelectedRows();
      if ( ri == null || ri.length == 0 ){
        JOptionPane.showMessageDialog(this,"Message",
	  			    "Select a row first!",
	  			    JOptionPane.PLAIN_MESSAGE);
        return;
      }
      int numberDeleted = 0;
      for(int i=0; i < ri.length; i++) {
        int currentIndex = ri[i] - numberDeleted;
	  if ( currentIndex >= _mts.getNumberOfDataReferences() ) {
	    continue;
	  }
	  _mts.remove(currentIndex);
	  numberDeleted++;
      }
      _modified = true;
      _table.tableChanged(new TableModelEvent(_table.getModel()));
    }
  }
  /**
   *
   */
  void add(){
    if (_dts != null) {
      if (DEBUG) System.out.println("Add");
      stopEditing();
      int i = _dts.getNumberOfDataReferences();
      _dts.setVarTypeAt(i,AppUtils.DVAR);
//      _dts.setBPartAt(i,_firstBPart);
//      _dts.setCPartAt(i,_firstCPart);
      _modified = true;
      _table.tableChanged(new TableModelEvent(_table.getModel()));
     } else {
      if (DEBUG) System.out.println("Add");
      stopEditing();
      int index = _mts.getNumberOfDataReferences();
      _mts.setVarTypeAt(index, AppUtils.DVAR);
//      _mts.setBPartAt(index,_firstBPart);
//      _mts.setCPartAt(index,_firstCPart);
      _modified = true;
      _table.tableChanged(new TableModelEvent(_table.getModel()));
    }
  }
  /**
   *
   */
  void insert(){
    if (_dts != null) {
      if (DEBUG) System.out.println("Insert");
      // get user selected row
      stopEditing();
      int ri = _table.getSelectedRow();
      if ( ri == -1 ){
        JOptionPane.showMessageDialog(this,"Message",
	  			    "Select a row first!",
				    JOptionPane.PLAIN_MESSAGE);
        return;
      }
      _dts.insertAt(ri);
      _modified = true;
      _table.tableChanged(new TableModelEvent(_table.getModel()));
     } else {
      if (DEBUG) System.out.println("Insert");
      // get user selected row
      stopEditing();
      int ri = _table.getSelectedRow();
      if ( ri == -1 ){
        JOptionPane.showMessageDialog(this,"Message",
  				    "Select a few rows first!",
  				    JOptionPane.PLAIN_MESSAGE);
        return;
      }
      _mts.insertAt(ri);
      _mts.setVarTypeAt(ri,AppUtils.DVAR);
      _modified = true;
      _table.tableChanged(new TableModelEvent(_table.getModel()));
    }
  }
  /**
   *
   */
  public void retrieve(){
    stopEditing();
    try {
			if (_dts != null) {
				System.out.println(_dts.getName());
			  GuiUtils.displayDTS(_dts);
			 } else if (_mts != null) {
				GuiUtils.displayMTS(_mts);
			}
    }catch(Exception e){
      VistaUtils.displayException(this,e);
    }
  }
  /**
  *
  */
 public Vector<DataContainer> retrieveData(){
   stopEditing();
   try {
			if (_dts != null) {
				System.out.println(_dts.getName());
				return AppUtils.retrieveDTSData(_dts);
			 } else if (_mts != null) {
				return AppUtils.retrieveMTSData(_mts);
			}
   }catch(Exception e){
     VistaUtils.displayException(this,e);
   }
   return new Vector<DataContainer>();
 }
  
  /**
   *
   */
  void quit(){
    if (DEBUG) System.out.println("Quit");
    stopEditing();
    if ( _modified ){
      int opt = JOptionPane.showConfirmDialog
	(this,
	 "Current table has been modified! Do you want to save to file?",
	 "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
      if ( opt == JOptionPane.YES_OPTION ){
	save();
	if ( ! _modified ) JOptionPane.getFrameForComponent(this).dispose();
      } else {
	JOptionPane.getFrameForComponent(this).dispose();
      }
    } else {
      JOptionPane.getFrameForComponent(this).dispose();
    }
  }
  /**
   *
   */
  void changeNameToField(){
    String name = _nameField.getText();
    if (DEBUG) System.out.println("Name: " + name);
    try {
      if ( name == null || name.equals("") ) return;
      if ( _dts != null ) _dts.setName(_nameField.getText());
      _modified = true;
    }catch(Exception e){
      VistaUtils.displayException(this,e);
    }
  }
  /**
   *
   */
  public void stopEditing(){
    changeNameToField();
    _modified = true;
    _table.editingStopped(new ChangeEvent(_table));
  }
  /**
   *
   */
  public JTable getTable() {
	return _table;
  }

  public void setTableModel(DerivedTimeSeries dts,MultipleTimeSeries mts) {
    setDTS(dts,mts);
    if (dts != null) {
	  _nameField.setText(dts.getName());
     } else {
	  _nameField.setText(mts.getName());
    }
    _dts = dts;
    _mts = mts;
  }

  public void setNameField(String name) {
	_nameField.setText(name);
  }

  private JTable _table;
  private JTextField _nameField;
  private DerivedTimeSeries _dts;
  private MultipleTimeSeries _mts;
  private boolean _modified;
//  private String _firstBPart, _firstCPart;
  private JMenuBar _mbar;
  public static String _name;
  Project _prj = AppUtils.getCurrentProject();
  String[] dtsArray;
  JComboBox _dtsEditor = new JComboBox();
//  Hashtable _svList = _prj.getSVHashtable();
//  Hashtable _dvList = _prj.getDVHashtable();
  JTextField _bpartEditor = new JTextField();
  JTextField cpartEditor = new JTextField();
  JComboBox varEditor = new JComboBox();
  private boolean start = true;
	DTSTableModel dtm;
	MTSTableModel mtm;
	String[] bs;
	int count = 0;

}
