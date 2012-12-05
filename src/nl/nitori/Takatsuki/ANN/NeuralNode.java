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

import nl.nitori.Takatsuki.Util.Log;

/**
 * A neuron in a neural network
 */
public class NeuralNode {
    private double activation;
    private double input;
    private double delta;

    private ArrayList<Connection> outputs;
    private ArrayList<Connection> inputs;

    public ArrayList<Connection> getOutputs() {
        return outputs;
    }

    public ArrayList<Connection> getInputs() {
        return inputs;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public NeuralNode() {
        outputs = new ArrayList<Connection>();
        inputs = new ArrayList<Connection>();
    }

    /**
     * Automatically creates a connection between two nodes
     * 
     * @param child
     *            The node that will be on the receiving end of the connection
     * @param weight
     *            The weight of the connection
     */
    public void addChild(NeuralNode child, double weight) {
        if (child instanceof InputNode) {
            Log.warning("Attempt to add an input node as a child. Blocked");
            return;
        }

        Connection c = new Connection(this, child, weight);
        outputs.add(c);
        child.inputs.add(c);
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public void setActivation(double act) {
        activation = act;
    }

    public double getActivation() {
        return activation;
    }
}
