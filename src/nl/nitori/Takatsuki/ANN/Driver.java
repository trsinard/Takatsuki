/* Copyright (C) 2012 Justin Wilcox
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.nitori.Takatsuki.ANN;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import nl.nitori.Takatsuki.Util.Log;

public class Driver {
    public static void main(String[] args) {
        Log.registerLogger();
        int[] top = new int[3];
        top[0] = 1;
        top[1] = 5;
        top[2] = 1;
        NeuralNetwork net = new NeuralNetwork(top);

        Examples ex = null;
        try {
            ex = new Examples("solar_radiation.txt", 10);
        } catch (FileNotFoundException e) {
            Log.exception(e);
        }

        ArrayList<Example> examples = ex.getEverything();
        net.trainOnExamples(examples, ex.getScale(), 0.2, 0.5);
        for (Example e : examples) {
            Log.info(e.displayDifference(net, ex.getScale()));
        }

        // int i = 100;
        // while (i-- > 0)
        // System.out.println();
        // double[] in = new double[1];
        // for (i = 0; i <= 11 * 11; i++) {
        // in[0] = i / 11.0 + 1;
        // net.computeActivation(in);
        // double[] out = net.getActivation(400);
        // String p = in[0] + "," + out[0] + ",";
        // for (Example x : examples) {
        // if (Math.abs(x.getInput()[0] - in[0]) < 0.000001)
        // p += x.getOutput()[0];
        // }
        // System.out.println(p);
        // }

        // ArrayList<Example> examprus = new ArrayList<Example>();
        // double max = 0.0;
        // for (int i = 0; i < 100; i++) {
        // double[] input = new double[2];
        // double[] output = new double[1];
        // input[0] = Random.getRangeDouble(10, 100);
        // input[1] = Random.getRangeDouble(10, 100);
        // output[0] = input[0] + input[1] + Random.getRangeDouble(0.01, 0.1);
        // if (output[0] > max)
        // max = output[0];
        // double[][] ex = new double[2][];
        // ex[0] = input;
        // ex[1] = output;
        // examprus.add(new Example(input, output));
        // }
        //
        // net.trainOnExamples(examprus, max, 10, 0.1);
        //
        // double[] in = new double[2];
        // double[] out = new double[1];
        // in[0] = 40;
        // in[1] = 70;
        // out[0] = 40 + 70;
        // Example ex = new Example(in, out);
        // Log.info(ex.displayDifference(net, max));

    }
}
