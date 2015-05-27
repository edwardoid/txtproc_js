/*
	jtxtproc
	Copyright (C) 2014  Edward Sargsyan
	avr_libs is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	jtxtproc is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	You should have received a copy of the GNU General Public License
	along with avr_libs.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.ssc.txtproc;

/**
 * Created by eduards on 5/27/2015.
 */
public class Options {

    public Options() {

    }

    public  boolean StemByPorter = false;
    public  boolean StemByLancaster = false;
    public  boolean MeasureByLeveshtineDistance = false;
    public  boolean MeasureByJaroWinkler = false;
    public  int     GetNGrams = 0;
    public  boolean GetTokens = false;
    public  boolean GetStops = false;
    public  boolean GetNonStops = false;
    public  boolean GetOriginalText = true;
}
