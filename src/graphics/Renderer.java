package graphics;

import java.awt.*;
import java.util.ArrayList;

import game.GameObject;
import game.graphics.GraphicComponent;

public class Renderer {
    // TODO: Maybe use a map
    private ArrayList<LayerRenderer> layers = new ArrayList<LayerRenderer>();

    public Renderer() {}

    public void remove(GameObject go) {
        if (go == null) return;
        for (LayerRenderer layer : layers) {
            if (go.getTransform().Zindex == layer.Zindex) {
                layer.remove(go);
                return;
            }
        }
    }

    public void add(GameObject go) {
        if (go.get(GraphicComponent.class) == null) return;

        for (LayerRenderer layer : layers) {
            if (go.getTransform().Zindex == layer.Zindex) {
                layer.add(go);
                return;
            }
        }

        LayerRenderer layer = new LayerRenderer(go.getTransform().Zindex);
        layer.add(go);
        layers.add(layer);
        sortLayers();
    }

    private void sortLayers() {
        layers.sort((o1, o2) -> o1.Zindex > o2.Zindex ? 1 : -1);
    }

    public void render(Graphics2D g) {
        for (LayerRenderer layer : layers) {
            layer.render(g);
        }
    }
}
