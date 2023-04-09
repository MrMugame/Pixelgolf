package scenes.mainmenu.components;

import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.components.UIPage;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIUnitConstraint;
import scenes.DebugScene;

public class UIMainScreen extends UIComponent {

    public UIMainScreen() {}

    @Override
    protected void init() {
        UIComponent image = new UIImage("ui/heading.png");
        image.getConstraints().addX(new UIUnitConstraint(3.5f));
        image.getConstraints().addY(new UIUnitConstraint(3));
        image.getConstraints().addWidth(new UIImageAspectConstraint());
        image.getConstraints().addHeight(new UIUnitConstraint(6));
        add(image);

        UIClickable playbutton = new UIMenuButton("Spielen");
        playbutton.setConstraints(ConstraintFactory.unitConstrains(3.5f, -30, 9, 2.5f));
        playbutton.addListener(() -> {
            GameWindow.get().changeScene(new DebugScene());
        });
        add(playbutton);

        UIClickable optionbutton = new UIMenuButton("Optionen");
        optionbutton.setConstraints(ConstraintFactory.unitConstrains(3.5f, -26.5f, 10, 2.5f));
        optionbutton.addListener(() -> {
            ((UIPage) getParent()).switchPage(1);
        });
        add(optionbutton);


        UIClickable exitbutton = new UIMenuButton("Exit");
        exitbutton.setConstraints(ConstraintFactory.unitConstrains(3.5f, -23f, 4.5f, 2.5f));
        exitbutton.addListener(() -> {
            GameWindow.get().exit();
        });
        add(exitbutton);
    }
}
