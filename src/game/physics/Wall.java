package game.physics;

import physics.Polygon;

public class Wall extends StaticPhysicsComponent {

    public Wall(Polygon poly) {
        super(poly);
    }

    @Override
    public void update(float dt) {}
}
