package scenes.levels;

import assets.Assets;
import game.Component;
import game.GameObject;
import game.Transform;
import game.graphics.DynamicGraphic;
import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.physics.Angle;
import game.physics.BallPhysics;
import game.physics.Flagpole;
import game.physics.Wall;
import graphics.GameWindow;
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

    private final int number;
    private Map map;

    public LevelLoader(int number) {
        this.number = number;
    }

    // TODO: Make the Exceptions nice && Cleanup
    public void load() throws Exception {
        map = new Map();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(Assets.loadLevel(number));
        document.getDocumentElement().normalize();

        // Check for correct root element
        Element root = document.getDocumentElement();
        map.width = Float.parseFloat(root.getAttribute("width"));
        map.height = Float.parseFloat(root.getAttribute("height"));

        map.oneStar = Integer.parseInt(root.getAttribute("oneStar"));
        map.twoStar = Integer.parseInt(root.getAttribute("twoStar"));
        map.threeStar = Integer.parseInt(root.getAttribute("threeStar"));


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

            NodeList components = element.getChildNodes();
            for (int j = 0; j < components.getLength(); j++) {
                if (!(components.item(j) instanceof Element)) continue;
                Element component = (Element) components.item(j);
                switch (component.getTagName()) {
                    case "StaticGraphic":
                        String path = component.getAttribute("path");
                        StaticGraphic graphic = new StaticGraphic(path);

                        try {
                            float originX = Float.parseFloat(component.getAttribute("ox"));
                            float originY = Float.parseFloat(component.getAttribute("oy"));
                            graphic.setAnchor(new Vector2D(originX, originY));
                        } catch (NullPointerException | NumberFormatException ignored) {}

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
                    case "Rectangle":
                        object.add(new game.physics.Rectangle());
                        break;
                    case "Angle":
                        object.add(new Angle());
                        break;
                    default:
                        System.err.println("Kann Component nicht verarbeiten: " + component.getTagName());
                }
            }

            map.dynamics.add(object);
        }
    }

    public GameObject renderBackground() {
        Vector2D margin = Transform.fromScreenPosition(new Vector2D(0, 0)).invertX();

        margin = margin.scale(1.05f); // Ein bisschen Puffer

        float worldAspect = (float) GameWindow.get().WIDTH / GameWindow.get().HEIGHT;
        float mapAspect = map.width / map.height;

        if (mapAspect < worldAspect) {
            // width is smaller
            // (map.width + margin.x) / (map.height + margin.y) = worldAspect
            // map.width + margin.x = worldAspect * (map.height + margin.y)
            // margin.x = worldAspect * (map.height + margin.y) - map.width
            margin.x = worldAspect * (map.height + margin.y) - map.width;
        } else {
            // height is smaller
            // (map.width + margin.x) / (map.height + margin.y) = worldAspect
            // (map.width + margin.x) / worldAspect = map.height + margin.y
            // (map.width + margin.x) / worldAspect - map.height = margin.y
            margin.y = (map.width + margin.x) / worldAspect - map.height;
        }

        Polygon polygon = map.track.toAWTPolygon(TILESIZE);

        BufferedImage texture = new BufferedImage((int) ((map.width+margin.x*2)*TILESIZE), (int) ((map.height+margin.y*2)*TILESIZE), TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) texture.getGraphics();

        BufferedImage outsideTexture = Assets.loadImage(map.outsideTexture);
        g.setPaint(new TexturePaint(outsideTexture, new Rectangle(0, 0, outsideTexture.getWidth(), outsideTexture.getHeight())));
        g.fillRect(0, 0, texture.getWidth(), texture.getHeight());

        g.translate(margin.x*TILESIZE, margin.y*TILESIZE);

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

        GameObject go = new GameObject("background", margin.invertX(), new Vector2D(map.width+(2*margin.x), map.height+(2*margin.y)), 0, 0);
        go.add(new DynamicGraphic(texture));

        return go;
    }

    private static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public ArrayList<GameObject> getDynamicObjects() {
        return map.dynamics;
    }

    public Map getMap() {
        return map;
    }

    public int getNumber() {
        return number;
    }
}
