package physics;

import game.GameObject;

public class Collider {
    public GameObject gameObject;
    public Polygon polygon;

    public Collider(GameObject gameObject, Polygon polygon) {
        this.gameObject = gameObject;
        this.polygon = polygon;
    }
}
