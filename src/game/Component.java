package game;

public abstract class Component {
    public GameObject parent;
    public abstract void update(float dt);
}
