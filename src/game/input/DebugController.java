package game.input;

import input.MouseListener;
import physics.Vector2D;

public class DebugController extends InputComponent {
    public DebugController() {}
    @Override
    public void update(float dt) {
        //KeyboardListener listener = KeyboardListener.get();
        MouseListener listener = MouseListener.get();

        Vector2D centerOffset = parent.getTransform().size.scale(0.5f);

        parent.getTransform().position = listener.getMousePosition().sub(centerOffset.invertY());
    }
}
