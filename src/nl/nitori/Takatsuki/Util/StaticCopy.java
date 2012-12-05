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

/**
 * This should be implemented by any class associated with the battle state. The
 * purpose is to allow multiple game trees to be explored without needing to
 * roll-back changes to aliased variables.
 */
public interface StaticCopy<T> {
    /**
     * Used for copying items that are considered mutable between game states
     * 
     * @return If the object can be modified during a battle, this should return
     *         a new copy. If the object will remain static for the particular
     *         battle (but not necessarily throughout the entire game), then it
     *         is safe to return <i>this</i>.
     */
    public T getStaticCopy();
}
