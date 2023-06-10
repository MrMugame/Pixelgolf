package game.physics;

import game.Component;
import physics.Vector2D;

public class Resetpoint extends Component {
    private final Vector2D reset;

    public Resetpoint(Vector2D reset) {
        this.reset = reset;
    }

    public Vector2D getReset() {
        return reset;
    }
}
