package scenes.levels;

import assets.Assets;
import game.GameObject;
import game.Transform;
import game.graphics.DynamicGraphic;
import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.input.Door;
import game.physics.*;
import graphics.GameWindow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import physics.Vector2D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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

        int poly = 0;
        map.tracks.add(new physics.Polygon());
        // Check that all childs are p and have correct formatting for point

        NodeList points = track.getElementsByTagName("*");
        for (int i = 0; i < points.getLength(); i++) {
            if (points.item(i).getNodeName().equals("p")) {
                String point = points.item(i).getTextContent();
                String[] xy = point.split(";");
                map.tracks.get(poly).addPoint(new Vector2D(Float.parseFloat(xy[0]), Float.parseFloat(xy[1])));
            } else if(points.item(i).getNodeName().equals("break")) {
                map.tracks.add(new physics.Polygon());
                poly += 1;
            } else {
                // TODO
            }
        }

        NodeList statics = root.getElementsByTagName("Static");
        for (int i = 0; i < statics.getLength(); i++) {
            Element element = (Element) statics.item(i);
            Map.StaticGrpahic g = new Map.StaticGrpahic();

            g.x = Float.parseFloat(element.getAttribute("x"));
            g.y = Float.parseFloat(element.getAttribute("y"));
            g.texture = element.getAttribute("tex");

            g.top = false;
            String top = element.getAttribute("top");
            if (top.equals("true")) g.top = true;

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
                    case "Resetpoint":
                        float resetX = Float.parseFloat(component.getAttribute("x"));
                        float resetY = Float.parseFloat(component.getAttribute("y"));
                        object.add(new Resetpoint(new Vector2D(resetX, resetY)));
                        break;
                    case "Collision":
                        // P 1 5 P 5 2
                        String[] box = component.getAttribute("box").split(" ");
                        // assert box.len != 2
                        float boxX = Float.parseFloat(box[0]);
                        float boxY = Float.parseFloat(box[1]);

                        String polygonPath = component.getAttribute("path");
                        Iterator<String> tokens = Arrays.stream(polygonPath.split(" ")).iterator();

                        physics.Polygon polygon = new physics.Polygon();

                        while (tokens.hasNext()) {
                            String token = tokens.next();
                            if (token.equals("P")) {
                                float pointX = Float.parseFloat(tokens.next()) / boxX * object.getTransform().size.x;
                                float pointY = Float.parseFloat(tokens.next()) / boxY * object.getTransform().size.y;
                                polygon.addPoint(new Vector2D(pointX, pointY));
                            } else {
                                // TODO: Exeption
                            }
                        }

                        String material = component.getAttribute("material");
                        MaterialType type = MaterialType.valueOf(material);

                        // TODO: Make material own component in xml
                        object.add(new Material(type));
                        object.add(new Custom(polygon));
                        break;
                    case "Portal":
                        int id = Integer.parseInt(component.getAttribute("id"));

                        object.add(new Portal(id));
                        break;
                    case "Door":
                        float ox = Float.parseFloat(component.getAttribute("ox"));
                        float oy = Float.parseFloat(component.getAttribute("oy"));
                        boolean delay = component.getAttribute("delay").equals("true");


                        object.add(new Door(ox, oy, delay));
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

        margin.y = (float) Math.floor(margin.y * 10) / 10;
        margin.x = (float) Math.floor(margin.x * 10) / 10;


        // Very implicit behaviour //TODO
        Area area = new Area();
        for (int i = 0; i < map.tracks.size(); i++) {
            if (i == 0) {
                area.add(new Area(map.tracks.get(i).toAWTPolygon(TILESIZE)));
            } else {
                area.subtract(new Area(map.tracks.get(i).toAWTPolygon(TILESIZE)));
            }
        }

        BufferedImage texture = new BufferedImage((int) ((map.width+margin.x*2)*TILESIZE), (int) ((map.height+margin.y*2)*TILESIZE), TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) texture.getGraphics();

        BufferedImage outsideTexture = Assets.loadImage(map.outsideTexture);
        g.setPaint(new TexturePaint(outsideTexture, new Rectangle(0, 0, outsideTexture.getWidth(), outsideTexture.getHeight())));
        g.fillRect(0, 0, texture.getWidth(), texture.getHeight());

        g.translate((int) (margin.x*TILESIZE), (int) (margin.y*TILESIZE));

        for (Map.StaticGrpahic graphic : map.statics) {
            if (graphic.top) continue;
            g.drawImage(Assets.loadImage(graphic.texture), (int) (graphic.x*TILESIZE), (int) -(graphic.y*TILESIZE), null);
        }

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2f));
        g.draw(area);

        BufferedImage insideTexture = Assets.loadImage(map.trackTexture);
        g.setPaint(new TexturePaint(insideTexture, new Rectangle(0, 0, insideTexture.getWidth(), insideTexture.getHeight())));
        g.fill(area);

        for (Map.StaticGrpahic graphic : map.statics) {
            if (!graphic.top) continue;
            g.drawImage(Assets.loadImage(graphic.texture), (int) (graphic.x*TILESIZE), (int) -(graphic.y*TILESIZE), null);
        }

        g.dispose();

        GameObject go = new GameObject("background", margin.invertX(), new Vector2D(map.width+(2*margin.x), map.height+(2*margin.y)), 0, 0);
        go.add(new DynamicGraphic(texture));

        return go;
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
