package physics;

import game.GameObject;

public class Collider { // TODO: Clean this and collision code up (maybe make this a generic tuple)
    public GameObject gameObject;
    public Polygon polygon;

    public Collider(GameObject gameObject, Polygon polygon) {
        this.gameObject = gameObject;
        this.polygon = polygon;
    }
}
