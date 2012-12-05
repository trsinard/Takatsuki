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

import nl.nitori.Takatsuki.Game.AttackAttributes.AttackType;

public abstract class Attack extends EntityAction {
    private AttackAttributes attr;

    public Attack(Entity sen, Entity rec, AttackAttributes attr) {
        super(sen, rec);
        this.attr = attr;
    }

    public String getName() {
        return attr.getName();
    }

    public String getDescription() {
        return attr.getDescription();
    }

    public ElementalType getElement() {
        return attr.getElement();
    }

    public AttackType getType() {
        return attr.getType();
    }

    @Override
    public String toString() {
        return "Attack " + getName() + " from " + getActor().getName() + ":"
                + getActor().getId() + " to " + getReceiver().getName() + ":"
                + getReceiver().getId();
    }
}
