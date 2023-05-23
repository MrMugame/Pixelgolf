package game.input;

import game.physics.BallPhysics;
import game.physics.PhysicsComponent;
import input.MouseListener;
import physics.CollisionMath;

import static java.awt.event.MouseEvent.BUTTON1;

public class BallInput extends InputComponent {
    private boolean dragging = false;

    public BallInput() {}

    @Override
    public void update(float dt) {
        MouseListener listener = MouseListener.get();

        if (!dragging && listener.isPressed(BUTTON1) && CollisionMath.pointCircle(parent.getTransform().getCenter() , parent.getTransform().size.x / 2 , listener.getMousePosition())) {
            dragging = true;
        } else if (dragging && !listener.isPressed(BUTTON1)) {
            BallPhysics physics = (BallPhysics) parent.get(PhysicsComponent.class);
            physics.velocity = parent.getTransform().getCenter().sub(listener.getMousePosition());
            dragging = false;
        }
    }
}
