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
import calsim.app.*;
import javax.swing.*;
//import java.awt.event.*;
//import vista.gui.*;
/**
  *	Retrieves a DTS and displays in correct view
  *
  * @author Nicky Sandhu
  * @version $Id: RetrieveDTSListener.java,v 1.1.2.9 2001/07/12 01:59:57 amunevar Exp $
  */
public class RetrieveDTSListener extends GuiTaskListener{
  /**
    *
    */
  public RetrieveDTSListener(){
    super("Retrieving...");
  }
  /**
    *
    */
  public RetrieveDTSListener(JMenuItem mi, String dtsName){
    super("Retrieving " + dtsName + "...");
    _mi = mi;
    _dtsName = dtsName;
  }
  /**
    * Invoked when a menu item is clickedon.
    *
    */
  public void doWork(){
    JMenuItem mi = null;
    String dtsName = null;
    if ( _mi == null )
      mi = (JMenuItem) super.getComponent();
    else
      mi = _mi;
    if (_dtsName == null )
      dtsName = mi.getText().toUpperCase();
    else
      dtsName = _dtsName;
    try {
      // first try the current project
      DerivedTimeSeries dts = AppUtils.getCurrentProject().getDTS(dtsName);
      if ( dts == null ) {
	dts=AppUtils.getGlobalDTS(dtsName);
      }
      if ( dts == null ) return;
      GuiUtils.displayDTS(dts);
    }catch(Exception ex){
      vista.gui.VistaUtils.displayException(mi,ex);
    }
  }
  public static boolean DEBUG = true;
  private JMenuItem _mi = null;
  private String _dtsName = null;
}