package scenes.levels.components;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.UIEndAlignContstraint;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIUnitConstraint;
import scenes.levels.Level;

import java.awt.*;

public class UIHUD extends UIComponent {
    private int strokes = 0;
    private UIComponent text;

    public UIHUD() {}

    @Override
    protected void update() {
        int currentStrokes = ((Level) GameWindow.get().getScene()).getLogic().getStrokes();
        if (currentStrokes != strokes) {
            strokes = currentStrokes;
            strokeText(strokes);
        }
    }

    @Override
    protected void init() {
        UIImage image = new UIImage("ui/hud_star_0.png");
        image.getConstraints().addX(new UIEndAlignContstraint());
        image.getConstraints().addY(new UIUnitConstraint(0));
        image.getConstraints().addWidth(new UIUnitConstraint(10));
        image.getConstraints().addHeight(new UIImageAspectConstraint());
        add(image);

        strokeText(strokes);
    }

    private void strokeText(int number) {
        remove(text);
        text = new UIText(Integer.toString(number), Color.BLACK, Assets.DEFAULT_FONT, 25, false);
        text.getConstraints().addX(new UIEndAlignContstraint());
        text.getConstraints().addY(new UIUnitConstraint(0));
        text.getConstraints().addWidth(new UIUnitConstraint(7.5f));
        text.getConstraints().addHeight(new UIUnitConstraint(4));
        add(text);
    }
}
