package scenes.levels;

import physics.Polygon;

import java.util.ArrayList;

public class Map {
    public float width, height;
    public Polygon track;
    public String trackTexture;
    public String outsideTexture;

    public static class StaticGrpahic {
        public float width, height, x, y;
        public String texture;
    }
    public ArrayList<StaticGrpahic> statics = new ArrayList<>();
}
