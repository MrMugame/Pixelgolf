package scenes.levels;

import assets.Assets;
import game.Component;
import game.GameObject;
import game.graphics.DynamicGraphic;
import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.physics.BallPhysics;
import game.physics.Flagpole;
import game.physics.Wall;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import physics.Vector2D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class LevelLoader {
    // Anzahl der Pixel pro Ingame-Einheit auf dem Hintergrundbild
    private static final int TILESIZE = 10;

    private final String filepath;
    private Map map;

    public LevelLoader(String filepath) {
        this.filepath = filepath;
    }

    // TODO: Make the Exceptions nice && Cleanup
    public void load() throws Exception {
        map = new Map();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
        Document document = builder.parse(Assets.getFile(LevelLoader.class, filepath));
        document.getDocumentElement().normalize();

        // Check for correct root element
        Element root = document.getDocumentElement();
        map.width = Float.parseFloat(root.getAttribute("width"));
        map.height = Float.parseFloat(root.getAttribute("height"));

        // Check for multiple Tracks
        Element track = (Element) root.getElementsByTagName("Track").item(0);
        map.trackTexture = track.getAttribute("insideTex");
        map.outsideTexture = track.getAttribute("outsideTex");

        map.track = new physics.Polygon();
        // Check that all childs are p and have correct formatting for point
        NodeList points = track.getElementsByTagName("p");
        for (int i = 0; i < points.getLength(); i++) {
            String point = points.item(i).getTextContent();
            String[] xy = point.split(";");
            map.track.addPoint(new Vector2D(Float.parseFloat(xy[0]), Float.parseFloat(xy[1])));
        }

        NodeList statics = root.getElementsByTagName("Static");
        for (int i = 0; i < statics.getLength(); i++) {
            Element element = (Element) statics.item(i);
            Map.StaticGrpahic g = new Map.StaticGrpahic();

            g.x = Float.parseFloat(element.getAttribute("x"));
            g.y = Float.parseFloat(element.getAttribute("y"));
            g.texture = element.getAttribute("tex");

            map.statics.add(g);
        }

        NodeList dynamics = root.getElementsByTagName("Dynamic");
        for (int i = 0; i < dynamics.getLength(); i++) {
            Element element = (Element) dynamics.item(i);

            String name = element.getAttribute("name");
            float x = Float.parseFloat(element.getAttribute("x"));
            float y = Float.parseFloat(element.getAttribute("y"));
            float width = Float.parseFloat(element.getAttribute("w"));
            float height = Float.parseFloat(element.getAttribute("h"));
            float rotation = Float.parseFloat(element.getAttribute("r"));
            int z = Integer.parseInt(element.getAttribute("z"));

            GameObject object = new GameObject(name, new Vector2D(x, y), new Vector2D(width, height), (float)(rotation * Math.PI), z);

            try {
                float originX = Float.parseFloat(element.getAttribute("ox"));
                float originY = Float.parseFloat(element.getAttribute("oy"));
                object.getTransform().origin = new Vector2D(originX, originY);
            } catch (NullPointerException | NumberFormatException ignored) {}


            NodeList components = element.getChildNodes();
            for (int j = 0; j < components.getLength(); j++) {
                if (!(components.item(j) instanceof Element)) continue;
                Element component = (Element) components.item(j);
                switch (component.getTagName()) {
                    case "StaticGraphic":
                        String path = component.getAttribute("path");
                        StaticGraphic graphic = new StaticGraphic(path);
                        object.add(graphic);
                        break;
                    case "BallPhysics":
                        float mass = Float.parseFloat(component.getAttribute("mass"));
                        BallPhysics physics = new BallPhysics(mass);
                        object.add(physics);
                        break;
                    case "BallInput":
                        object.add(new BallInput());
                        break;
                    case "Flagpole":
                        object.add(new Flagpole());
                        break;
                    default:
                        System.err.println("Kann Component nicht verarbeiten: " + component.getTagName());
                }
            }

            map.dynamics.add(object);
        }
    }

    public GameObject renderBackground() {
        // TODO: Make Margin Camera dependent somehow dont ask me how pls
        float margin = 8;
        //         ^ (+)
        // (-) <---|---> (+)
        //         v (-)
        Polygon polygon = map.track.toAWTPolygon(TILESIZE);

        BufferedImage texture = new BufferedImage((int) (map.width+margin*2)*TILESIZE, (int) (map.height+margin*2)*TILESIZE, TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) texture.getGraphics();

        BufferedImage outsideTexture = Assets.loadImage(map.outsideTexture);
        g.setPaint(new TexturePaint(outsideTexture, new Rectangle(0, 0, outsideTexture.getWidth(), outsideTexture.getHeight())));
        g.fillRect(0, 0, texture.getWidth(), (int) texture.getHeight());

        g.translate(margin*TILESIZE, margin*TILESIZE);

        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(polygon);

        BufferedImage insideTexture = Assets.loadImage(map.trackTexture);
        g.setPaint(new TexturePaint(insideTexture, new Rectangle(0, 0, insideTexture.getWidth(), insideTexture.getHeight())));
        g.fillPolygon(polygon);

        for (Map.StaticGrpahic graphic : map.statics) {
            g.drawImage(Assets.loadImage(graphic.texture), (int) (graphic.x*TILESIZE), (int) -(graphic.y*TILESIZE), null);
        }

        g.dispose();

        GameObject go = new GameObject("background", new Vector2D(-margin, margin), new Vector2D(map.width+2*margin, map.height+2*margin), 0, 0);
        go.add(new DynamicGraphic(texture));

        return go;
    }

    public ArrayList<GameObject> getDynamicObjects() {
        return map.dynamics;
    }

    public Map getMap() {
        return map;
    }

    public String getPath() {
        return filepath;
    }

    public String getNextPath() {
        // TODO: Don't really like this
        int number = Integer.parseInt(filepath.split("_")[1].split("\\.")[0]);
        String nextPath = "maps/level_" + (number + 1) + ".xml";
        try {
            File file = new File(new URI(Objects.requireNonNull(LevelLoader.class.getResource(nextPath)).toString()).getPath());
            if(file.exists() && !file.isDirectory()) {
                return nextPath;
            } else {
                return null;
            }
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }
}
