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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import nl.nitori.Takatsuki.Util.Log;
import nl.nitori.Takatsuki.Util.XMLHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Driver {
    public static void main(String[] args) {
        Log.registerLogger();

        // MinimaxSearch.minimax(new TestGame(1), 800);
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            EntityStaticAttributes attr = new EntityStaticAttributes(1, 2, 3,
                    4, 5, 6, 7, 8, 9);
            Element elem = doc.createElement("rooty-tooty");
            doc.appendChild(elem);
            Element serial = attr.serialize(doc);
            serial.appendChild(doc.createElement("ichitacosan"));
            elem.appendChild(serial);

            XMLHelper.writeDocumentToFile(doc, "output2.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        }
    }
}
