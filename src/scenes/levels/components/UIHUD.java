package scenes.levels.components;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.UIEndAlignContstraint;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIUnitConstraint;
import scenes.levels.Level;

import java.awt.*;

public class UIHUD extends UIComponent {
    private int strokes, stars = 0;
    private UIComponent text;
    private UIComponent hud;

    public UIHUD() {}

    @Override
    protected void update() {
        int currentStrokes = ((Level) GameWindow.get().getScene()).getLogic().getStrokes();
        if (currentStrokes != strokes) {
            strokes = currentStrokes;
            strokeText(strokes);
        }

        int currentStars = ((Level) GameWindow.get().getScene()).getLogic().getStars();
        if (currentStars != stars) {
            stars = currentStars;
            updateHUD(stars);
        }
    }

    @Override
    protected void init() {
        hud = new UIContainer();
        hud.setConstraints(ConstraintFactory.fullscreen());
        add(hud);

        strokeText(strokes);
        updateHUD(stars);
    }

    private void updateHUD(int stars) {
        hud.removeAll();
        UIImage image = new UIImage("ui/hud_star_" + stars + ".png");
        image.getConstraints().addX(new UIEndAlignContstraint());
        image.getConstraints().addY(new UIUnitConstraint(0));
        image.getConstraints().addWidth(new UIUnitConstraint(10));
        image.getConstraints().addHeight(new UIImageAspectConstraint());
        hud.add(image);
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
