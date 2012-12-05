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

package nl.nitori.Takatsuki.AI;

import java.util.List;

public interface SearchNode<T> {
    /**
     * @return A list of successor nodes. Note that if this node has no
     *         successors, then this may return null or an empty list
     */
    public List<T> getSuccessors();

    /**
     * @return What node type this is
     */
    public SearchNodeTeam getSearchTeam();

    /**
     * @return A heuristic score, where a higher score benefits MAX
     */
    public float getSearchScore();

    /**
     * @return The probability of this node if parent.getSearchTeam() == CHANCE.
     *         Any value if otherwise
     */
    public float getChance();
}
