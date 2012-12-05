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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import nl.nitori.Takatsuki.ANN.Example;
import nl.nitori.Takatsuki.ANN.Examples;
import nl.nitori.Takatsuki.ANN.NeuralNetwork;
import nl.nitori.Takatsuki.Util.Log;

public class NeuralNetDriver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java NeuralNetDriver <inputfilename>");
            System.exit(1);
        }

        Log.registerLogger();

        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileReader f = new FileReader(args[0]);
            BufferedReader read = new BufferedReader(f);
            String line = read.readLine();
            while (line != null) {
                lines.add(line);
                line = read.readLine();
            }
            read.close();
        } catch (IOException e) {
            Log.error("Cound not read file " + args[0]);
            System.exit(1);
        }

        if (lines.size() < 5) {
            Log.error("Unexpected EOF in file " + args[0]);
            System.exit(1);
        }

        Examples examples = null;
        try {
            examples = new Examples(lines.get(0), 1);
        } catch (FileNotFoundException e) {
            Log.error("Could not find file " + lines.get(0));
            System.exit(1);
        }

        int[] top = null;
        try {
            int hidC = Integer.parseInt(lines.get(1));
            top = new int[hidC + 2];
            top[0] = examples.getInputCount();
            top[hidC + 1] = examples.getOutputCount();
            for (int i = 1; i < hidC + 1; i++) {
                top[i] = Integer.parseInt(lines.get(i + 1));
            }
        } catch (NumberFormatException e) {
            Log.error("Unexpected non-integer line in file " + args[0]);
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            Log.error("Unexpected EOF in file " + args[0]);
            System.exit(1);
        }
        NeuralNetwork net = null;
        double error = 0.0;
        try {
            double tol = Double.parseDouble(lines.get(lines.size() - 1));
            double alpha = Double.parseDouble(lines.get(lines.size() - 2));
            net = new NeuralNetwork(top);
            error = net.trainOnExamples(examples.getEverything(),
                    examples.getScale(), tol, alpha);
        } catch (NumberFormatException e) {
            Log.error("Unexpected non-number line in file " + args[0]);
            System.exit(1);
        }

        String toPrint = "Network to true output comparison: \n";
        for (Example e : examples.getEverything()) {
            toPrint += e.displayDifference(net, examples.getScale()) + "\n\n";
        }
        toPrint += "\nThe total error is: " + error;
        Log.info(toPrint);
    }
}
