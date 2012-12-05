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

import nl.nitori.Takatsuki.Util.Log;
import nl.nitori.Takatsuki.Util.StaticCopy;
import nl.nitori.Takatsuki.Util.XMLSerializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EntityStaticAttributes implements
        StaticCopy<EntityStaticAttributes>, XMLSerializable {
    private int strength, magicStrength;
    private int defense, magicDefense;
    private int speed, stanima, range;
    private int maxHP, maxSP;

    // Only for completeness. Generally too unwieldy to actually use
    public EntityStaticAttributes(int strength, int magicStrength, int defense,
            int magicDefense, int speed, int stanima, int range, int maxHP,
            int maxSP) {
        this.strength = strength;
        this.magicStrength = magicStrength;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.speed = speed;
        this.stanima = stanima;
        this.range = range;
        this.maxHP = maxHP;
        this.maxSP = maxSP;
    }

    public EntityStaticAttributes(Element ctor) {
        try {
            strength = Integer.parseInt(ctor.getAttribute("strength"));
            magicStrength = Integer.parseInt(ctor.getAttribute("mstrength"));
            defense = Integer.parseInt(ctor.getAttribute("defense"));
            magicDefense = Integer.parseInt(ctor.getAttribute("mdefense"));
            speed = Integer.parseInt(ctor.getAttribute("speed"));
            stanima = Integer.parseInt(ctor.getAttribute("stanima"));
            range = Integer.parseInt(ctor.getAttribute("range"));
        } catch (NumberFormatException e) {
            Log.error("Not a valid integer in EntityStaticAttributes attribute -"
                    + e.getMessage());
        }
    }

    @Override
    public EntityStaticAttributes getStaticCopy() {
        // These attributes should not be modified during battle
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public int getMagicStrength() {
        return magicStrength;
    }

    public int getDefence() {
        return defense;
    }

    public int getMagicDefence() {
        return magicDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStanima() {
        return stanima;
    }

    public int getRange() {
        return range;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxSP() {
        return maxSP;
    }

    @Override
    public Element serialize(Document doc) {
        Element rep = doc.createElement("entity-attributes");

        rep.setAttribute("strength", Integer.toString(strength));
        rep.setAttribute("mstrength", Integer.toString(magicStrength));
        rep.setAttribute("defense", Integer.toString(defense));
        rep.setAttribute("mdefense", Integer.toString(magicDefense));
        rep.setAttribute("speed", Integer.toString(speed));
        rep.setAttribute("stanima", Integer.toString(stanima));
        rep.setAttribute("range", Integer.toString(range));

        return rep;
    }

}
