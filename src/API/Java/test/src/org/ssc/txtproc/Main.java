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

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        TextProcessor processor = new TextProcessor("http://localhost", 8080);
        Options opt = new Options();
        opt.StemByPorter = true;
        try {
            Result res = processor.process("I implemented many projects", opt);
            int i = 10;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
