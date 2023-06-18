package game.input;

import game.Component;
import physics.Vector2D;

public class Door extends Component {
    private boolean open = false; // Zeit wird zwischen den Türen nicht synchronisiert, mir fällt jedoch kein Fall ein, indem die Türen aus dem Rhythmus kommen könnten
    private float time = 0;
    private Vector2D offset;
    private boolean delay;

    public Door(float ox, float oy, boolean delay) {
        offset = new Vector2D(ox, oy);
        this.delay = delay;
    }

    @Override
    public void update(float dt) {
        if (delay && time > 1000) {
            delay = false;
            time = 0;
        }
        if (time > 2500) {
            parent.getTransform().translate(open ? offset.invert() : offset);
            open = !open;
            time = 0;
        }
        time += dt;
    }
}
