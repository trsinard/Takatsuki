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
 * A helper Node that always has an activation of 1
 */
public class BiasNode extends NeuralNode {
    public BiasNode() {
        super();
    }

    @Override
    public void setDelta(double d) {
        Log.error("Blocking delta set on bias node");
    }

    @Override
    public double getDelta() {
        Log.error("Blocking delta get on bias node");
        return Double.NaN;
    }

    @Override
    public double getActivation() {
        return 1.0;
    }

    @Override
    public ArrayList<Connection> getInputs() {
        Log.error("Invalid use of Bias Node");
        return null;
    }

    @Override
    public double getInput() {
        Log.error("Invalid use of Bias Node");
        return Double.NaN;
    }

    @Override
    public void setInput(double input) {
        Log.error("Invalid use of Bias Node");
    }

    @Override
    public void setActivation(double act) {
        Log.error("Invalid use of Bias Node");
    }

}
