package scenes;

import game.Component;
import game.GameObject;
import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.input.NoInput;
import game.physics.BallPhysics;
import game.physics.NoPhysics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import physics.Vector2D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LevelSerializer {

    public static Map<String, Class> classes = Map.of(
                "StaticGraphic", StaticGraphic.class,
                "BallInput", BallInput.class,
                "BallPhysics", BallPhysics.class,
                "NoInput", NoInput.class,
                "NoPhysics", NoPhysics.class
            );

    public static ArrayList<GameObject> loadLevel(String path) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File(LevelSerializer.class.getResource("example/scene.xml").getPath()));

            doc.getDocumentElement().normalize();

            ArrayList<GameObject> objects = new ArrayList<>();
            NodeList objectsRaw = doc.getElementsByTagName("Scene").item(0).getChildNodes();

            for (int i = 0; i < objectsRaw.getLength(); i++) {
                if (objectsRaw.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
                Element node = (Element) objectsRaw.item(i);

                String name = node.getAttribute("name");
                float x = Float.parseFloat(node.getAttribute("x"));
                float y = Float.parseFloat(node.getAttribute("y"));
                float width = Float.parseFloat(node.getAttribute("width"));
                float height = Float.parseFloat(node.getAttribute("height"));
                int z = Integer.parseInt(node.getAttribute("zindex"));

                GameObject g = new GameObject(name, new Vector2D(x, y), new Vector2D(width, height), z);

                NodeList components = node.getChildNodes();

                for (int j = 0; j < components.getLength(); j++) {
                    if (components.item(j).getNodeType() != Node.ELEMENT_NODE) continue;
                    Element component = (Element) components.item(j);

                    Class T = classes.get(component.getTagName());
                    Parameter[] parameters = T.getConstructors()[0].getParameters();

                    Object[] arguments = new Object[parameters.length];

                    for (int k = 0; k < parameters.length; k++) {
                        String str = component.getAttributes().item(k).getNodeValue();
                        arguments[k] = toObject(parameters[k].getType(), str);
                    }

                    g.add((Component) T.getConstructors()[0].newInstance(arguments));
                }

                objects.add(g);
            }


            return objects;
        } catch (ParserConfigurationException | SAXException | IOException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public static Object toObject( Class clazz, String value ) {
        if( "boolean".equals(clazz.getTypeName()) ) return Boolean.parseBoolean(value);
        if( "int".equals(clazz.getTypeName()) ) return Integer.parseInt(value);
        if( "float".equals(clazz.getTypeName()) ) return Float.parseFloat(value);
        return value;
    }
}
