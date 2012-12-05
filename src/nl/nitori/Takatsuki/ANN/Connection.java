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
 * Represents a connection between two neurons
 */
public class Connection {
    private NeuralNode send;
    private NeuralNode receive;
    private double weight;

    public Connection(NeuralNode send, NeuralNode receive, double weight) {
        this.send = send;
        this.receive = receive;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public NeuralNode getSend() {
        return send;
    }

    public NeuralNode getReceive() {
        return receive;
    }
}
