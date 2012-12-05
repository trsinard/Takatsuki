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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import nl.nitori.Takatsuki.Util.Log;
import nl.nitori.Takatsuki.Util.Random;

/**
 * Collection of Example objects
 */
public class Examples {
    private ArrayList<ArrayList<Example>> bins;
    private ArrayList<Example> everything;
    private double scale;

    /**
     * Load examples from a file
     * 
     * @param file
     *            The file to read from
     * @param binCount
     *            How many bins to split the input into
     * @throws FileNotFoundException
     *             If the file cannot be opened
     */
    public Examples(String file, int binCount) throws FileNotFoundException {
        FileReader f = new FileReader(file);
        BufferedReader input = new BufferedReader(f);
        ArrayList<Example> examples = new ArrayList<Example>();

        try {
            ArrayList<String> lines = new ArrayList<String>();
            String line = input.readLine();
            while (line != null) {
                lines.add(line);
                line = input.readLine();
            }
            input.close();

            scale = Double.parseDouble(lines.get(0));
            String[] io = lines.get(1).split(",");
            int inputC = Integer.parseInt(io[0]);
            int outputC = Integer.parseInt(io[1]);

            for (int i = 2; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                if (data.length != inputC + outputC) {
                    Log.warning("Malformed input on line " + i + " of file "
                            + file);
                } else {
                    double[] eIn = new double[inputC];
                    double[] eOut = new double[outputC];
                    for (int j = 0; j < inputC; j++) {
                        eIn[j] = Double.parseDouble(data[j]);
                    }
                    for (int j = 0; j < outputC; j++) {
                        eOut[j] = Double.parseDouble(data[j + inputC]);
                    }
                    Example ex = new Example(eIn, eOut);
                    examples.add(ex);
                }
            }
        } catch (NumberFormatException e) {
            Log.error("Invalid number format in file " + file);
        } catch (IOException e) {
            Log.exception(e);
        } catch (IndexOutOfBoundsException e) {
            Log.error("Unexpected EOF in file " + f);
        }
        init(examples, binCount);
    }

    public Examples(ArrayList<Example> data, int binCount, double scale) {
        init(data, binCount);
        this.scale = scale;
    }

    private void init(ArrayList<Example> data, int binCount) {
        Log.info("Loaded an example file with " + data.size()
                + " examples, dividing into " + binCount + " bins");
        everything = new ArrayList<Example>(data);

        bins = new ArrayList<ArrayList<Example>>();
        for (int i = 0; i < binCount; i++) {
            bins.add(new ArrayList<Example>());
        }

        int i = 0;
        while (!data.isEmpty()) {
            bins.get(i)
                    .add(data.remove(Random.getRangeInt(0, data.size() - 1)));
            i++;
            if (i >= binCount)
                i = 0;
        }

    }

    public int getInputCount() {
        return bins.get(0).get(0).getInput().length;
    }

    public int getOutputCount() {
        return bins.get(0).get(0).getOutput().length;
    }

    public double getScale() {
        return scale;
    }

    public int getBinCount() {
        return bins.size();
    }

    public ArrayList<Example> getValidationBin(int valIndex) {
        assert (valIndex < bins.size());
        return bins.get(valIndex);
    }

    public ArrayList<Example> getTrainingBin(int valIndex) {
        ArrayList<Example> toRet = new ArrayList<Example>();
        for (int i = 0; i < bins.size(); i++) {
            if (i != valIndex)
                toRet.addAll(bins.get(i));
        }
        return toRet;
    }

    public ArrayList<Example> getEverything() {
        return everything;
    }
}
