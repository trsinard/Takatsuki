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

package nl.nitori.Takatsuki.Game;

public class AttackAttributes {
    public enum AttackType {
        MELEE, RANGED,
    }

    private String name;
    private String description;
    private AttackType type;
    private ElementalType element;
    private float baseDamage;

    public AttackAttributes(String name, String description, AttackType type,
            ElementalType element, float baseDamage) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.baseDamage = baseDamage;
        this.element = element;
    }

    public ElementalType getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AttackType getType() {
        return type;
    }

    public float getBaseDamage() {
        return baseDamage;
    }
}
