package game.input;

import game.Component;
import physics.Vector2D;

public class Door extends Component {
    private boolean open = false; // Zeit wird zwischen den Türen nicht synchronisiert, mir fällt jedoch kein Fall ein indem die Türen aus dem Rhythmus kommen sollten
    private float time = 0;
    private Vector2D offset;

    public Door(float ox, float oy) {
        offset = new Vector2D(ox, oy);
    }

    @Override
    public void update(float dt) {
        if (time > 2500) {
            parent.getTransform().translate(open ? offset.invert() : offset);
            open = !open;
            time = 0;
        }
        time += dt;
    }
}
