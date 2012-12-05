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

import nl.nitori.Takatsuki.Util.Log;

/**
 * Marker class representing an input neuron
 */
public class InputNode extends NeuralNode {
    public InputNode() {
        super();
    }

    @Override
    public double getDelta() {
        Log.error("Should not ask for delta on input node");
        return 0.0;
    }

    @Override
    public void setDelta(double d) {
        Log.error("Should not be setting delta of input node");
    }

    @Override
    public double getInput() {
        Log.error("Cannot get in of InputNode (use getActivation)");
        return Double.NaN;
    }
}
