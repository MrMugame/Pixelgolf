package state;

import java.io.Serializable;

public class LevelState implements Serializable {
    private int stars = 0;

    public LevelState() {}

    public LevelState(int stars) {
        this.stars = stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public LevelState copy() {
        LevelState level = new LevelState();
        level.setStars(stars);
        return level;
    }
}
