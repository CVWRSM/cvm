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
import java.awt.BorderLayout;
import javax.swing.*;
//import java.awt.event.*;
/**
  * A default frame understands MPanel's. It queries
  * the panel for its menu bar and adds it to itself.
  *
  * @author Nicky Sandhu
  * @version $Id: DefaultFrame.java,v 1.1.4.5 2000/12/20 20:07:05 amunevar Exp $
  */
public class DefaultFrame extends JFrame{
  /**
   * creates a frame with the given MPanel and sets
   * its menu bar accordingly
   * @see MPanel
   */
  public DefaultFrame(MPanel panel){
    getContentPane().setLayout(new BorderLayout());
    //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setMPanel(panel);
    /*
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent evt){
          cleanup();
        }
        public void windowClosed(WindowEvent evt){
          cleanup();
        }
    });
    */
  }

  public void cleanup() {
    getContentPane().removeAll();
    super.dispose();
  }

  /**
   * sets the main panel to this MPanel
   * @see MPanel
   */
  public void setMPanel(MPanel panel){
    getContentPane().removeAll();
    getContentPane().add(panel);
    setJMenuBar(panel.getJMenuBar());
    setTitle(panel.getFrameTitle());
  }
}
