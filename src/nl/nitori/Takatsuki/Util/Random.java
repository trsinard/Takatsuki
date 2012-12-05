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

package nl.nitori.Takatsuki.Util;

import java.util.ArrayList;

public class Random {
    private static java.util.Random jrand = new java.util.Random();

    public static double getRangeDouble(double low, double high) {
        return (jrand.nextDouble() * (high - low) + low);
    }

    public static int getRangeInt(int low, int high) {
        return low + jrand.nextInt(high + 1);
    }

    public static boolean getBool() {
        return jrand.nextBoolean();
    }

    public static float getChiSquare(int k) {
        float sum = 0.0f;
        while (k-- > 0) {
            sum += Math.pow(jrand.nextGaussian(), 2);
        }
        return sum;
    }

    public static float getNormal(float avg, float sd) {
        return (float) ((jrand.nextGaussian() + avg) / sd);
    }

    public static <T> void shuffleList(ArrayList<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T temp = list.get(i);
            int exchangeWith = getRangeInt(0, list.size() - 1);
            list.set(i, list.get(exchangeWith));
            list.set(exchangeWith, temp);
        }
    }
}
