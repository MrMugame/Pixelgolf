package scenes.mainmenu.settingsmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.SliderListener;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIPassthroughConstraint;
import gui.constraints.UIRelativeConstraint;
import input.MouseListener;
import physics.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class UISlider extends UIComponent {
    private static final float USABLE_BAR = 0.97f;
    private ArrayList<SliderListener> listeners = new ArrayList<>();
    private float min, max, value;
    private boolean dragging = false;
    private boolean setup = true;
    private String name;

    private UIClickable knob;
    private UIComponent bar;

    public UISlider(String name, float initial, float min, float max) {
        this.value = (initial - min) / (max - min);
        this.min = min;
        this.max = max;
        this.name = name;
    }

    @Override
    protected void update() {
        // Unschön
        if (setup && bar.getConstraints().width != 0 && knob.getConstraints().width != 0) {
            float barWidth = bar.getConstraints().width;
            float knobWidth = knob.getConstraints().width;

            float pixelMin = barWidth*(1f - USABLE_BAR);
            float pixelMax = barWidth*USABLE_BAR - knobWidth;

            float pos = (value * (pixelMax - pixelMin) + pixelMin) / barWidth;

            knob.getConstraints().addX(new UIRelativeConstraint(pos));
            setup = false;
        }

        if (dragging) {
            Vector2D mouse = MouseListener.get().getMousePositionScreen();

            float barWidth = bar.getConstraints().width;
            float knobWidth = knob.getConstraints().width;

            float pixelMin = barWidth*(1f - USABLE_BAR);
            float pixelMax = barWidth*USABLE_BAR - knobWidth;

            float pos = mouse.x - bar.getConstraints().x - knobWidth/2; // Pixel position of knob left edge
            float clampedPos = Math.max(pixelMin, Math.min(pixelMax, pos));

            knob.getConstraints().addX(new UIRelativeConstraint(clampedPos/barWidth));


            float valuePercent = (clampedPos - pixelMin) / (pixelMax - pixelMin);

            if (valuePercent != value) {
                value = valuePercent;
                for (SliderListener listener : listeners) {
                    listener.run(min + (max - min) * value);
                }
            }
        }
    }

    @Override
    protected void init() {
        bar = new UIImage("ui/settings_slider_bar.png");
        bar.setConstraints(ConstraintFactory.fullscreen());
        bar.getConstraints().addHeight(new UIImageAspectConstraint());
        add(bar);

        UIText text = new UIText(name, Color.BLACK, Assets.DEFAULT_FONT, 14, false);
        text.setConstraints(ConstraintFactory.fullscreen());
        text.getConstraints().addX(new UIRelativeConstraint(0.1f));
        add(text);

        knob = new UIClickable() {
            @Override
            protected void onPressStart() {
                dragging = true;
            }

            @Override
            protected void onPressEnd() {
                dragging = false;
            }
        };
        knob.getConstraints().addHeight(new UIRelativeConstraint(0.8f));
        knob.getConstraints().addWidth(new UIPassthroughConstraint());
        knob.getConstraints().addY(new UICenterConstraint());
        knob.getConstraints().addX(new UIRelativeConstraint(0));

        UIImage img = new UIImage("ui/settings_slider_knob.png");
        img.setConstraints(ConstraintFactory.fullscreen());
        img.getConstraints().addWidth(new UIImageAspectConstraint());
        knob.add(img);

        add(knob);
    }

    public void addListener(SliderListener listener) {
        listeners.add(listener);
    }
}
