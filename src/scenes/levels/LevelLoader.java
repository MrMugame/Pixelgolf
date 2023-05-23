package scenes.levels;

import assets.Assets;
import game.GameObject;
import game.graphics.DynamicGraphic;
import game.graphics.StaticGraphic;
import game.physics.Wall;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import physics.Vector2D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class LevelLoader {

    private static int TILESIZE = 10;

    private String filepath;
    private Map map;

    public LevelLoader(String filepath) {
        this.filepath = filepath;
    }

    // TODO: Make the Exceptions nice
    public void load() throws Exception {
        map = new Map();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
        Document document = builder.parse(new File(LevelLoader.class.getResource(filepath).getPath()));
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
    }

    public GameObject renderBackground() {
        //         ^ (+)
        // (-) <---|---> (+)
        //         v (-)
        Polygon polygon = map.track.toAWTPolygon(TILESIZE);

        BufferedImage texture = new BufferedImage((int) map.width*TILESIZE, (int) map.height*TILESIZE, TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) texture.getGraphics();

        g.setPaint(new TexturePaint(Assets.loadImage(map.outsideTexture), new Rectangle(0, 0, 16, 16)));
        g.fillRect(0, 0, (int) map.width*TILESIZE, (int) map.height*TILESIZE);

        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(polygon);

        g.setPaint(new TexturePaint(Assets.loadImage(map.trackTexture), new Rectangle(0, 0, 16, 16)));
        g.fillPolygon(polygon);

        g.dispose();

        GameObject go = new GameObject("background", new Vector2D(0, 0), new Vector2D(map.width, map.height), 0);
        go.add(new DynamicGraphic(texture));
        go.add(new Wall(map.track));

        return go;
    }

    public Map getMap() {
        return map;
    }
}
