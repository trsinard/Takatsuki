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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.nitori.Takatsuki.Util.Log;
import nl.nitori.Takatsuki.Util.Random;

public class NeuralNetwork {
    /** The maximum number of times to run over a training set */
    public static final int MAX_ITERATIONS = 500000;
    private int iterations;

    private int[] netTopology;
    private ArrayList<NeuralNode> inputs;
    private ArrayList<NeuralNode> outputs;
    private ArrayList<ArrayList<NeuralNode>> layers;
    private ActivationFunction func;

    /**
     * Create a randomly initialized network with given topology
     * 
     * @param topology
     *            Each item represents the number of nodes in the layer. The
     *            first layer is treated as the input, and the last as the
     *            output. Must have at least 2 entries
     */
    public NeuralNetwork(int[] topology) {
        assert (topology.length > 1);
        netTopology = topology.clone(); // Make sure this doesn't change later

        // Generate the weights of the connections
        ArrayList<double[][]> layout = new ArrayList<double[][]>();
        for (int i = 0; i < topology.length - 1; i++) {
            int outputCount = topology[i];
            int inputCount = topology[i + 1];
            double[][] toAdd = new double[outputCount][inputCount];
            for (int j = 0; j < outputCount; j++) {
                for (int k = 0; k < inputCount; k++) {
                    toAdd[j][k] = Random.getRangeDouble(-0.1, 0.1);
                }
            }

            layout.add(toAdd);
        }

        init(layout);
    }

    private void init(List<double[][]> layout) {
        func = new SigmoidActivation();

        inputs = new ArrayList<NeuralNode>();
        layers = new ArrayList<ArrayList<NeuralNode>>();

        // Setup the input nodes
        assert (layout.size() > 1);
        for (int i = 0; i < layout.get(0).length; i++) {
            inputs.add(new InputNode());
        }

        // Add each layer successively
        ArrayList<NeuralNode> curLayer = inputs;
        ArrayList<NeuralNode> nextLayer = new ArrayList<NeuralNode>();
        for (int i = 0; i < layout.size(); i++) {
            double[][] weights = layout.get(i);
            for (int j = 0; j < weights[0].length; j++) {
                if (i != layout.size() - 1) {
                    nextLayer.add(new NeuralNode());
                } else {
                    nextLayer.add(new OutputNode());
                }
            }

            for (int j = 0; j < curLayer.size(); j++) {
                for (int k = 0; k < nextLayer.size(); k++) {
                    curLayer.get(j).addChild(nextLayer.get(k), weights[j][k]);
                }
            }
            layers.add(curLayer);
            curLayer = nextLayer;
            nextLayer = new ArrayList<NeuralNode>();
        }
        // The last generated layer is the output layer
        outputs = curLayer;
        layers.add(outputs);

        // Add bias nodes to all nodes in all layers except inputs
        for (int i = 1; i < layers.size(); i++) {
            ArrayList<NeuralNode> layer = layers.get(i);
            for (int j = 1; j < layer.size(); j++) {
                (new BiasNode()).addChild(layer.get(j),
                        Random.getRangeDouble(-0.1, 0.1));
            }
        }
    }

    public void setActivationFunction(ActivationFunction func) {
        this.func = func;
    }

    /**
     * Collect the current network output
     * 
     * @param scale
     *            Factor by which the output is scaled back to
     * @return Activation of the output nodes returned back to their proper
     *         scale
     */
    public double[] getActivation(double scale) {
        double[] toRet = new double[outputs.size()];
        for (int i = 0; i < outputs.size(); i++) {
            toRet[i] = outputs.get(i).getActivation() * scale;
        }
        return toRet;
    }

    public void computeActivation(double[] inputVal) {
        assert (inputVal.length == inputs.size());

        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).setActivation(inputVal[i]);
        }

        for (int i = 1; i < layers.size(); i++) {
            ArrayList<NeuralNode> nextLayer = layers.get(i);
            for (NeuralNode n : nextLayer) {
                double sum = 0.0;
                for (Connection in : n.getInputs()) {
                    sum += in.getWeight() * in.getSend().getActivation();
                }
                n.setInput(sum);
                n.setActivation(func.func(sum));
            }
        }
    }

    public double trainOnExamples(ArrayList<Example> examples, double scale,
            double tol, double alpha) {
        Log.debug("Training net on " + examples.size() + " examples at "
                + scale + " scale with learning rate " + alpha
                + " stopping at error " + tol);
        examples = new ArrayList<Example>(examples);
        iterations = 0;
        double error = 0.0;
        do {
            error = 0.0;
            iterations++;

            for (int exI = 0; exI < examples.size(); exI++) {
                Example ex = examples.get(exI);
                double[] input = ex.getInput();
                double[] output = ex.getOutput();

                computeActivation(input);
                double[] tentativeOutput = getActivation(scale);

                for (int i = 0; i < outputs.size(); i++) {
                    NeuralNode n = outputs.get(i);
                    assert (n instanceof OutputNode);
                    double delta = (func.derivative(n.getInput()) * ((output[i] - tentativeOutput[i]) / scale));
                    assert (delta != 0.0);
                    n.setDelta(delta);
                }

                for (int i = layers.size() - 2; i > 0; i--) {
                    ArrayList<NeuralNode> curLayer = layers.get(i);
                    for (NeuralNode n : curLayer) {
                        double sum = 0.0;
                        for (Connection con : n.getOutputs()) {
                            sum += con.getWeight()
                                    * con.getReceive().getDelta();
                        }
                        n.setDelta(func.derivative(n.getInput()) * sum);
                    }
                }

                for (int i = layers.size() - 1; i >= 0; i--) {
                    ArrayList<NeuralNode> curLayer = layers.get(i);
                    for (NeuralNode n : curLayer) {
                        for (Connection con : n.getInputs()) {
                            double wij = con.getWeight();
                            con.setWeight(wij + alpha
                                    * con.getSend().getActivation()
                                    * con.getReceive().getDelta());
                        }
                    }
                }

                computeActivation(input);
                double[] newOutput = getActivation(scale);
                error += ex.computeError(newOutput);
            }

            // This is surprisingly able to reduce the average iteration count
            // by about 43%
            Collections.shuffle(examples);
            error /= scale;

            if (error <= tol) {
                Log.debug("Stopping training after " + iterations
                        + " iterations reaching training error of " + error);
                break;
            }

            if (iterations > MAX_ITERATIONS) {
                Log.warning("Stopping training after reaching max iteration count with error "
                        + error);
                break;
            }
        } while (true);
        return error;
    }

    public int getIterations() {
        return iterations;
    }

    @Override
    public String toString() {
        String toReturn = "Network Layout: ";
        for (int i : netTopology) {
            toReturn += i + " ";
        }
        toReturn = "\nNetwork Weights: \n";
        for (ArrayList<NeuralNode> layer : layers) {
            toReturn += "Layer out weights: \n";
            for (int i = 0; i < layer.size(); i++) {
                toReturn += i + " : ";
                for (Connection c : layer.get(i).getOutputs()) {
                    toReturn += c.getWeight() + " ";
                }
                toReturn += "\n";
            }
        }

        return toReturn;
    }
}
