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

package calsim.wreslcoder.wresl;
import java.io.PrintWriter;

/**
 * Organizes the bounds on an LP decision variable.
 * Generates a goal that provides bounds on a decision variable.
 * <p>
 * XA default bounds are 0 < dvar < MAXDOUBLE
 * @author Armin Munevar
 * @version $Id: DvarBounder.java,v 1.1.2.3 2001/07/12 02:00:06 amunevar Exp $
 */
public class DvarBounder {
    private String name;
    private String lowerBound = "0";
    private String upperBound = "1.e38";
    private String integerPriority = "16000";
//    private boolean lowerIsUnbounded = false;
    private boolean upperIsUnbounded = true;
    private boolean lowerIsDefault = true;
    private boolean isInteger = false;

    /**
      * Creates a new decision variable bounding object with default bounds.
      *
      * @param nm The name of this decision variable.
      */
    public DvarBounder(String nm) {name = nm;}

    /**
      * Changes the lower bound of this variable to <i>unbounded</i>
      */
    public void setLower() {		// no arguments:  unbounds
//        lowerIsUnbounded = true;
		lowerIsDefault = false;
        lowerBound = "-1.e38";
    }

    /**
      * Changes the lower bound of this variable to the supplied value.
      *
      * @param b The new bound, a Fortran expression.
      */
    public void setLower( String b) {
//        lowerIsUnbounded = false;
		lowerIsDefault = false;
        lowerBound = b;
    }

    /**
      * Changes the upper bound of this variable to <i>unbounded</i>
      */
    public void setUpper() {		// no arguments:  unbounds
        upperIsUnbounded = true;
        upperBound =  "1.e38";
    }

    /**
      * Changes the upper bound of this variable to the supplied value.
      *
      * @param b The new bound, a Fortran expression.
      */
    public void setUpper( String b) {
        upperIsUnbounded = false;
        upperBound = b;
    }

    /**
      * Specifies that this decision variable is an <i>integer</i> variable.
      */
    public void setInteger() {  isInteger = true; }

    /**
      * Specifies that this decision variable is an <i>integer</i> variable with the supplied XA priority.
      *
      * @param s The desired priority.  The default is 16000.
      */
    public void setIntegerPriority( String s) {
        integerPriority = s;
    }

    /**
      * Writes a representation of the bounds of this variable to the supplied stream.  This is
      * subsequently used by another process.
      *
      * @param s The PrintWriter to use.
      */
    public void output( PrintWriter s) {
        s.println( "BOUND");
		if (isInteger) {
	    	if (upperIsUnbounded && lowerIsDefault) {
				s.println( "-4   "+"            "+integerPriority);          //  means this is a standard dvar bound definition
				s.println( name);
	    	} else {
				s.println( "-3   "+"            "+integerPriority);          //  means this is a nonstandard dvar bound definition
				s.println( name);
				s.println( upperBound);
				s.println( lowerBound);
	    	}
		} else {
	    	if (upperIsUnbounded && lowerIsDefault) {
				s.println( "-2");          //  means this is a standard dvar bound definition
				s.println( name);
	    	} else {
				s.println( "-1");          //  means this is a nonstandard dvar bound definition
				s.println( name);
				s.println( upperBound);
				s.println( lowerBound);
	    	}
		}
    }
}
