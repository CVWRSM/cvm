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
  * Shows the MTS in a MTS Table
  *
  * @author Nicky Sandhu
  * @version $Id: ListMTSListener.java,v 1.1.2.4 2000/12/20 20:07:15 amunevar Exp $
  */
public class ListMTSListener extends GuiTaskListener{
  /**
    *
    */
  public ListMTSListener(){
    super("Listing...");
  }
  /**
    * Invoked when a menu item is clickedon.
    *
    */
  public void doWork(){
    JMenuItem mi = (JMenuItem) super.getComponent();
    String mtsName = mi.getText().toUpperCase().trim();
    try {
      if ( DEBUG )System.out.println("finding mts " + mtsName);
      MultipleTimeSeries mts = AppUtils.findMTS(mtsName);
      if ( mts == null ) return;
      if ( DEBUG ) System.out.println("Showing in a table: " + mts.getName());
      JFrame fr = new DefaultFrame(new MTSTable(mts));
      fr.setSize(400,250);
      fr.setVisible(true);
    }catch( Exception ex){
      vista.gui.VistaUtils.displayException(mi,ex);
    }
  }
  public static boolean DEBUG = true;
}
