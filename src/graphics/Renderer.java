package graphics;

import java.awt.*;
import java.util.ArrayList;

import game.GameObject;

public class Renderer {
    private ArrayList<LayerRenderer> layers = new ArrayList<LayerRenderer>();

    public Renderer() {}

    public void add(GameObject go) {
        for (LayerRenderer layer : layers) {
            if (go.getTransform().Zindex == layer.Zindex) {
                layer.add(go);
                return;
            }
        }

        LayerRenderer layer = new LayerRenderer(go.getTransform().Zindex);
        layer.add(go);
        layers.add(layer);
    }

    private void sortLayers() {
        layers.sort((o1, o2) -> o1.Zindex > o2.Zindex ? 1 : -1);
    }

    public void render(Graphics2D g) {
        sortLayers();
        for (LayerRenderer layer : layers) {
            layer.render(g);
        }
    }
}
