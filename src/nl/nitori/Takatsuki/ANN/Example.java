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

/**
 * A single example to use with a NeuralNetwork
 */
public class Example {
    private double[] input;
    private double[] output;

    public Example(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }

    /**
     * Computes the error between the expected output and the computed output
     * 
     * @param compOutput
     *            The output computed by the NeuralNetwork
     * @return The <b>unscaled</b> error
     */
    public double computeError(double[] compOutput) {
        assert (compOutput.length == output.length);
        double err = 0.0;
        for (int i = 0; i < compOutput.length; i++) {
            err += Math.abs(output[i] - compOutput[i]);
        }
        return err;
    }

    /**
     * Shows the difference between the true output and what the given
     * NeuralNetwork computes
     * 
     * @param net
     *            The network to test
     * @param scale
     *            How to scale the network's output
     * @return A String showing the difference
     */
    public String displayDifference(NeuralNetwork net, double scale) {
        String toReturn = toString();
        toReturn += "\nnet-output: ";
        net.computeActivation(input);
        double[] act = net.getActivation(scale);
        for (int i = 0; i < act.length; i++) {
            String percent = Double.toString(
                    Math.abs(act[i] - output[i]) / output[i] * 100).substring(
                    0, 4);
            toReturn += act[i] + " (" + percent + "%) ";
        }
        return toReturn;
    }

    @Override
    public String toString() {
        String toReturn = "input: ";
        for (double x : input) {
            toReturn += x + " ";
        }
        toReturn += "\ntrue-output: ";
        for (double x : output) {
            toReturn += x + " ";
        }
        return toReturn;
    }
}
