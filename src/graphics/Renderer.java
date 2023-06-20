package graphics;

import game.GameObject;
import game.graphics.GraphicComponent;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

public class Renderer {
    private final HashMap<Integer, LayerRenderer> layers = new HashMap<>();

    public Renderer() {}

    public void remove(GameObject go) {
        if (go == null) return;
        if (layers.containsKey(go.getTransform().Zindex)) {
            layers.get(go.getTransform().Zindex).remove(go);
        }
    }

    public void add(GameObject go) {
        if (go.get(GraphicComponent.class) == null) return;

        if (layers.containsKey(go.getTransform().Zindex)) {
            layers.get(go.getTransform().Zindex).add(go);
            return;
        }

        LayerRenderer layer = new LayerRenderer(go.getTransform().Zindex);
        layer.add(go);
        layers.put(go.getTransform().Zindex, layer);
    }

    public void render(Graphics2D g) {
        for (Iterator<Integer> it = layers.keySet().stream().sorted().iterator(); it.hasNext(); ) {
            layers.get(it.next()).render(g);
        }
    }
}
