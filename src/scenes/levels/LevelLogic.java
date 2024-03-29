package scenes.levels;

import event.Event;
import event.EventSystem;
import event.EventType;

public class LevelLogic {
    private int strokes = 0;
    private int oneStar = 10, twoStar = 5, threeStar = 1;

    public LevelLogic() {

    }

    public void setStarBoundaries(int oneStar, int twoStar, int threeStar) {
        this.oneStar = oneStar;
        this.twoStar = twoStar;
        this.threeStar = threeStar;
    }

    public int getStars() {
        if (strokes <= threeStar) {
            return 3;
        } else if (strokes <= twoStar) {
            return 2;
        } else if (strokes <= oneStar) {
            return 1;
        } else {
            return 0;
        }
    }

    public void addStroke() {
        if (strokes == 0) EventSystem.notify(new Event<>(EventType.TUTORIAL, "firststroke"));
        strokes += 1;
    }

    public void reset() {
        strokes = 0;
    }

    public int getStrokes() {
        return strokes;
    }
}
