package nl.nitori.Takatsuki.Game;

import java.util.HashMap;

import nl.nitori.Takatsuki.Util.Log;
import nl.nitori.Takatsuki.Util.StubException;
import nl.nitori.Takatsuki.Util.XMLHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AttributeDB {
    private HashMap<String, AttackAttributes> attacks;
    private HashMap<String, EntityStaticAttributes> entities;

    public AttributeDB() {
        attacks = new HashMap<String, AttackAttributes>();
        entities = new HashMap<String, EntityStaticAttributes>();
    }

    public boolean loadFile(String file) {
        Document d = XMLHelper.readDocumentFromFile(file);
        Element root = d.getDocumentElement();
        if (root.getNodeName().equals("entity-attributes")) {
            NodeList l = root.getElementsByTagName("entity-attribute");
            for (int i = 0; i < l.getLength(); i++) {
                Node n = l.item(i);
                if (n instanceof Element) {
                    Element e = (Element) n;
                    String id = e.getAttribute("id");
                    addEntityAttribs(id, new EntityStaticAttributes(e));
                }
            }
            return true;
        } else if (root.getNodeName().equals("attack-attributes")) {
            throw new StubException();
            // return true;
        } else {
            Log.error("Wrong XML type of file in " + file);
            return false;
        }
    }

    public void addEntityAttribs(String name, EntityStaticAttributes attr) {
        if (entities.containsKey(name)) {
            Log.warning("Blocking re-add of " + name + " to attributes DB");
        } else {
            entities.put(name, attr);
        }
    }

    public void addAttackAttribs(String name, AttackAttributes attr) {
        if (attacks.containsKey(name)) {
            Log.warning("Blocking re-add of " + name + " to attributes DB");
        } else {
            attacks.put(name, attr);
        }
    }

    public AttackAttributes getAttack(String name) {
        return attacks.get(name);
    }

    public EntityStaticAttributes getEntity(String name) {
        return entities.get(name);
    }
}
