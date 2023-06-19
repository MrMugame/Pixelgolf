package game.input;

import assets.Assets;
import game.GameObject;
import game.graphics.StaticGraphic;
import game.physics.ActivePhysicsComponent;
import game.physics.BallPhysics;
import graphics.GameWindow;
import input.MouseListener;
import physics.CollisionMath;
import physics.Vector2D;
import scenes.levels.Level;
import sound.SoundSystem;

import static java.awt.event.MouseEvent.BUTTON1;

public class BallInput extends InputComponent {
    private static final float VISUAL_FACTOR = 0.60f;
    private static final float POWER_FACTOR = 0.65f;
    private static final int MAX = 6;

    private boolean dragging = false;

    private GameObject drag, arrow; // TODO: Move to get by name instead of references

    public BallInput() {
        arrow = new GameObject("arrow", new Vector2D(), new Vector2D(1, 1*(13.0f/14)), 0, 3);
        arrow.add(new StaticGraphic("game/drag_arrow.png"));
        arrow.get(StaticGraphic.class).setAnchor(new Vector2D(0.5f, 0.25f));

        drag = new GameObject("drag", new Vector2D(), new Vector2D(2, 2), 0, 3);
        drag.add(new StaticGraphic("game/drag_1.png"));
        drag.get(StaticGraphic.class).setAnchor(new Vector2D(1, 0.25f));
    }

    @Override
    public void update(float dt) {
        MouseListener listener = MouseListener.get();

        if (dragging) {
            Vector2D dragVector = parent.getTransform().position.sub(listener.getMousePosition());
            drag.getTransform().rotation = dragVector.getAngle() - 0.5f * (float) Math.PI;

            drag.get(StaticGraphic.class).changePath("game/drag_" + (int) Math.max(1, Math.min(Math.floor(dragVector.magnitude() / VISUAL_FACTOR), MAX)) + ".png");

            arrow.getTransform().rotation = (float) Math.PI + (dragVector.getAngle() - 0.5f * (float) Math.PI);
        }

        if (parent.get(ActivePhysicsComponent.class).velocity.magnitudeSquared() < 0.001f && !dragging && listener.isPressed(BUTTON1) && CollisionMath.pointCircle(parent.getTransform().position , parent.getTransform().size.x / 2 , listener.getMousePosition())) {
            dragging = true;

            drag.getTransform().position = parent.getTransform().position;
            GameWindow.get().getScene().addGameObject(drag);

            arrow.getTransform().position = parent.getTransform().position;
            GameWindow.get().getScene().addGameObject(arrow);

        } else if (dragging && !listener.isPressed(BUTTON1)) {
            BallPhysics physics = (BallPhysics) parent.get(ActivePhysicsComponent.class);
            Vector2D mouseVector = parent.getTransform().position.sub(listener.getMousePosition());
            if (mouseVector.magnitude()/VISUAL_FACTOR >= MAX) {
                // mag/factor = max
                // mag = max*factor
                // mag^2 * power_factor = (max*factor)^2 * power_factor
                physics.velocity = mouseVector.normalize().scale((MAX*VISUAL_FACTOR)*(MAX*VISUAL_FACTOR) * POWER_FACTOR);
            } else {
                physics.velocity = mouseVector.normalize().scale(mouseVector.magnitudeSquared() * POWER_FACTOR);
            }

            // TODO: AHAHDHAHDAHDHAHDHAHD HAH AH H HAH HH SINGLETON (SOGAR NOCH EIN... TYPE CASTS) ICH HASSE SINGLETONS ABER JOA NE. ein observer pattern wäre hier geiler aber joa ne ... aufwändig
            ((Level) GameWindow.get().getScene()).getLogic().addStroke();

            SoundSystem.get().play(Assets.loadSound("sound/stroke_sound.wav"));

            GameWindow.get().getScene().removeGameObject(drag);
            GameWindow.get().getScene().removeGameObject(arrow);
            dragging = false;
        }

    }
}
