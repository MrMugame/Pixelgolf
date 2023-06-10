package scenes.levels;

import game.GameObject;
import physics.Polygon;

import java.util.ArrayList;

public class Map {
    public float width, height;

    public int oneStar, twoStar, threeStar;

    public Polygon track;
    public String trackTexture;
    public String outsideTexture;

    public static class StaticGrpahic {
        public float x, y;
        public boolean top;
        public String texture;
    }

    public ArrayList<StaticGrpahic> statics = new ArrayList<>();

    public ArrayList<GameObject> dynamics = new ArrayList<>();


}
