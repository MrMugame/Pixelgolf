package scenes.levels;

public class LevelLogic {
    private int strokes = 0;

    public LevelLogic() {

    }

    public void addStroke() {
        strokes += 1;
    }

    public int getStrokes() {
        return strokes;
    }
}
