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
import java.util.Iterator;
import java.util.List;

import nl.nitori.Takatsuki.Util.Log;
import nl.nitori.Takatsuki.Util.Random;

public class MinimaxSearch {
    /**
     * Finds the optimal move using the ExpectiMinimax algorithm
     * 
     * @param parent
     *            The root of the game tree
     * @param maxDepth
     *            How deep to search the tree. Pass -1 to have no bound
     * @return A pair containing both the optimal _next_ move, and the
     *         associated score. Note that on CHANCE nodes, each branch is
     *         treated as having equal probability for determining the score,
     *         and a random branch is selected as the return node
     */
    public static <T extends SearchNode<T>> ScoreNodePair<T> minimax(
            SearchNode<T> parent, int maxDepth) {
        ScoreNodePair<T> ret = asyncMinimaxLaunch(parent, maxDepth);
        Log.debug("Returning a node with score " + ret.score);
        return ret;
    }

    /*
     * Implements the standard minimax algorithm with rudimentary CHANCE
     * handling. Returns a pair containing the optimal score and _leaf_ node
     * associated with that score
     */
    private static <T extends SearchNode<T>> ScoreNodePair<T> syncMinimax(
            SearchNode<T> parent, int maxDepth) {
        if (maxDepth == 0) {
            return new ScoreNodePair<T>(parent, parent.getSearchScore());
        }

        List<T> successors = parent.getSuccessors();
        if (successors == null | successors.size() == 0) { // Terminal Node
            return new ScoreNodePair<T>(parent, parent.getSearchScore());
        }

        List<ScoreNodePair<T>> results = new ArrayList<ScoreNodePair<T>>();
        for (T suc : successors) {
            // Technically we should check if maxDepth is -1, but if we
            // underflow, we've run out of RAM anyways, so it doesn't really
            // matter
            results.add(syncMinimax(suc, maxDepth - 1));
        }

        ScoreNodePair<T> toReturn = null;
        Iterator<ScoreNodePair<T>> iterator = results.iterator();
        switch (parent.getSearchTeam()) {
        case CHANCE:
            // Calculates an average (equal probability) score and chooses a
            // random node as the return
            float totalScore = 0.0f;
            while (iterator.hasNext())
                totalScore += iterator.next().score;
            totalScore /= (results.size());
            toReturn = results.get(Random.getRangeInt(0, results.size() - 1));
            toReturn.score = totalScore;
            break;
        case MAX:
            toReturn = results.get(0);
            while (iterator.hasNext()) {
                ScoreNodePair<T> i = iterator.next();
                if (i.score > toReturn.score)
                    toReturn = i;
            }
            break;
        case MIN:
            toReturn = results.get(0);
            while (iterator.hasNext()) {
                ScoreNodePair<T> i = iterator.next();
                if (i.score < toReturn.score)
                    toReturn = i;
            }
            break;
        default:
            break;
        }

        return toReturn;
    }

    /*
     * Similar to the syncd version, but with a few minor differences. Creates a
     * thread for each branch of the given parent, and returns the optimal node
     * that directly descended from the parent (so finding the next single
     * optimal move is trivial)
     */
    private static <T extends SearchNode<T>> ScoreNodePair<T> asyncMinimaxLaunch(
            SearchNode<T> parent, int maxDepth) {
        Log.debug("Starting Minimax thread group");
        if (maxDepth == 0) {
            return new ScoreNodePair<T>(parent, parent.getSearchScore());
        }

        List<T> successors = parent.getSuccessors();
        if (successors == null || successors.size() == 0) { // Terminal Node
            return new ScoreNodePair<T>(parent, parent.getSearchScore());
        }

        List<MinimaxSearchThread<T>> containers = new ArrayList<MinimaxSearchThread<T>>();
        List<Thread> threads = new ArrayList<Thread>();
        for (T suc : successors) {
            MinimaxSearchThread<T> cont = new MinimaxSearchThread<T>(suc,
                    maxDepth - 1);
            Thread thread = new Thread(cont);
            thread.start();
            containers.add(cont);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            while (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                }
            }
        }
        Log.debug("Finished Minimax thread pool");

        MinimaxSearchThread<T> toReturn = null;
        Iterator<MinimaxSearchThread<T>> iterator = containers.iterator();
        switch (parent.getSearchTeam()) {
        case CHANCE:
            float totalScore = 0.0f;
            while (iterator.hasNext())
                totalScore += iterator.next().getResult().score;
            totalScore /= (containers.size());
            toReturn = containers.get(Random.getRangeInt(0,
                    containers.size() - 1));
            toReturn.getResult().score = totalScore;
            break;
        case MAX:
            toReturn = containers.get(0);
            while (iterator.hasNext()) {
                MinimaxSearchThread<T> i = iterator.next();
                if (i.getResult().score > toReturn.getResult().score)
                    toReturn = i;
            }
            break;
        case MIN:
            toReturn = containers.get(0);
            while (iterator.hasNext()) {
                MinimaxSearchThread<T> i = iterator.next();
                if (i.getResult().score < toReturn.getResult().score)
                    toReturn = i;
            }
            break;
        default:
            break;
        }

        toReturn.getResult().node = toReturn.parent;
        return toReturn.getResult();
    }

    private static class MinimaxSearchThread<T extends SearchNode<T>>
            implements Runnable {
        private SearchNode<T> parent;
        private int maxDepth;
        private ScoreNodePair<T> result;

        public MinimaxSearchThread(SearchNode<T> parent, int maxDepth) {
            this.parent = parent;
            this.maxDepth = maxDepth;
            result = null;
        }

        public ScoreNodePair<T> getResult() {
            return result;
        }

        @Override
        public void run() {
            Log.debug("Starting new Minimax search thread on "
                    + Thread.currentThread().getName());
            result = syncMinimax(parent, maxDepth);
        }
    }
}
