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

import java.util.ArrayList;
import java.util.List;

public class TestGame implements SearchNode<TestGame> {
    private int depth;
    private float score;

    public TestGame(int depth) {
        this.depth = depth;
        score = 0.0f;
        superWork();
    }

    private void superWork() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                score += ((float) i) * j * depth;
            }
        }
    }

    @Override
    public List<TestGame> getSuccessors() {

        List<TestGame> toRet = new ArrayList<TestGame>();
        if (depth > 5)
            return toRet;

        for (int i = 0; i < 6; i++) {
            toRet.add(new TestGame(depth + 1));
        }

        return toRet;
    }

    @Override
    public SearchNodeTeam getSearchTeam() {
        if (depth % 3 == 1) {
            return SearchNodeTeam.MAX;
        } else if (depth % 3 == 2) {
            return SearchNodeTeam.MIN;
        } else {
            return SearchNodeTeam.CHANCE;
        }
    }

    @Override
    public float getSearchScore() {
        return score;
    }

}
