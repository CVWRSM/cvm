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
import com.sun.java.util.collections.Comparator;
/**
 * A class to compare derived time series by name
 *
 * @author Nicky Sandhu
 * @version $Id: DTSComparator.java,v 1.1.2.2 2000/12/20 20:07:03 amunevar Exp $
 */
public class DTSComparator implements Comparator{
  public int compare(Object obj1, Object obj2){
    if ( obj1 == null ) return -1;
    if ( obj2 == null ) return 1;
    if (!( obj1 instanceof DerivedTimeSeries )) return -1;
    if (!( obj2 instanceof DerivedTimeSeries )) return 1;
    DerivedTimeSeries dts1 = (DerivedTimeSeries) obj1;
    DerivedTimeSeries dts2 = (DerivedTimeSeries) obj2;
    return dts1.getName().compareTo(dts2.getName());
  }
  public boolean equals(Object obj) {  //CB TODO
    return false;
  }
}
