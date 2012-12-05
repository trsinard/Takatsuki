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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implemented by things that can be stored as XML data. While (obviously) not
 * possible to require from the interface, it is expected that any class that
 * can be deserialized from XML data should provide a constructor that accepts
 * an Element
 */
public interface XMLSerializable {
    /**
     * Store the object as an XML element
     * 
     * @param doc
     *            Document that the element will be added to. Note that the
     *            function should not actual insert the item into the document
     *            hierarchy directly (this would be done at the caller's
     *            discretion)
     * @return An XML element representing the object
     */
    public Element serialize(Document doc);
}
