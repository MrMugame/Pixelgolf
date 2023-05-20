package scenes.mainmenu.startmenu;

import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.components.UIPage;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIPassthroughConstraint;
import gui.constraints.UIUnitConstraint;
import scenes.DebugScene;
import scenes.mainmenu.startmenu.components.UIImageButton;

public class UIMainScreen extends UIComponent {

    public UIMainScreen() {}

    @Override
    protected void init() {
        UIComponent background = new UIImage("ui/background_1.png");
        background.setConstraints(ConstraintFactory.fullscreenAspect());
        add(background);


        UIComponent heading = new UIImage("ui/heading.png");
        heading.getConstraints().addX(new UIUnitConstraint(3.5f));
        heading.getConstraints().addY(new UIUnitConstraint(3));
        heading.getConstraints().addWidth(new UIImageAspectConstraint());
        heading.getConstraints().addHeight(new UIUnitConstraint(12));
        add(heading);

        float delta = -10;

        UIClickable playbutton = new UIImageButton("ui/start_button.png");
        playbutton.setConstraints(ConstraintFactory.unitConstrains(5.5f, delta + -15, 9, 5.5f, false, true));
        playbutton.getConstraints().addWidth(new UIPassthroughConstraint());
        playbutton.addListener(() -> {
            //GameWindow.get().changeScene(new DebugScene());
            ((UIPage) getParent()).switchPage(2);
        });
        add(playbutton);

        UIClickable optionbutton = new UIImageButton("ui/options_button.png");
        optionbutton.setConstraints(ConstraintFactory.unitConstrains(5.5f, delta + -8.5f, 10, 5.5f, false, true));
        optionbutton.getConstraints().addWidth(new UIPassthroughConstraint());
        optionbutton.addListener(() -> {
            ((UIPage) getParent()).switchPage(1);
        });
        add(optionbutton);

        UIClickable exitbutton = new UIImageButton("ui/exit_button.png");
        exitbutton.setConstraints(ConstraintFactory.unitConstrains(5.5f,  delta + -2f, 10.5f, 5.5f, false, true));
        exitbutton.getConstraints().addWidth(new UIPassthroughConstraint());
        exitbutton.addListener(() -> {
            GameWindow.get().exit();
        });
        add(exitbutton);
    }
}
