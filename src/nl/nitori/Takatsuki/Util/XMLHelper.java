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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLHelper {
    public static Document readDocumentFromFile(String fileName) {
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            Log.error("There was an error loading the file " + fileName + ": "
                    + e.getMessage());
            return null;
        }
    }

    public static boolean writeDocumentToFile(Document doc, String fileName) {
        try {
            return writeDocumentToStream(doc, new FileOutputStream(new File(
                    fileName)));
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean writeDocumentToStream(Document doc,
            OutputStream stream) {
        try {
            Source src = new DOMSource(doc);
            Result dest = new StreamResult(new OutputStreamWriter(stream,
                    "utf-8")); // Needed for some reason (probably encoding)

            // I feel quite limited by the TransformerFactory creation process,
            // and I may need to come back and implement a
            // TransformerFactoryFactory to make the process more customizable
            TransformerFactory f = TransformerFactory.newInstance();
            f.setAttribute("indent-number", new Integer(2));
            Transformer t = f.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");

            t.transform(src, dest);
            return true;
        } catch (TransformerFactoryConfigurationError | TransformerException
                | UnsupportedEncodingException e) {
            Log.exception(e);
            return false;
        }
    }
}
