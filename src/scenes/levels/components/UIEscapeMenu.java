package scenes.levels.components;

import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.components.UIPage;
import gui.constraints.*;
import input.KeyboardListener;
import scenes.mainmenu.MainMenu;
import scenes.mainmenu.settingsmenu.UISettingsMenu;

import java.awt.event.KeyEvent;

public class UIEscapeMenu extends UIComponent {
    private boolean pressed = false;

    private enum State { // TODO: Come up with a better idea because this many flag variables is just insane
        OFF,
        PENDING_ACTIVE,
        PENDING_OFF,
        ACTIVE
    }
    private State state = State.OFF;
    private UIComponent menu = new UIContainer();
    private UIPage pages;

    public UIEscapeMenu() {}

    @Override
    protected void update() {
        KeyboardListener listener = KeyboardListener.get();

        if (listener.isPressed(KeyEvent.VK_ESCAPE) && state == State.OFF && !pressed) {
            pressed = true;
            state = State.PENDING_ACTIVE;
        } else if (listener.isPressed(KeyEvent.VK_ESCAPE) && state == State.ACTIVE && !pressed) {
            pressed = true;
            if (pages.getIndex() == 0) {
                state = State.PENDING_OFF;
            } else {
                pages.switchPage(0);
            }
        } else if (!listener.isPressed(KeyEvent.VK_ESCAPE) && pressed) {
            pressed = false;
        }

        if (state == State.PENDING_ACTIVE) {
            add(pages);
            GameWindow.get().getScene().pause();
            state = State.ACTIVE;
        } else if (state == State.PENDING_OFF) {
            remove(pages);
            GameWindow.get().getScene().pursue();
            state = State.OFF;
        }
    }

    @Override
    protected void init() {
        pages = new UIPage(menu, new UISettingsMenu());
        pages.setConstraints(ConstraintFactory.fullscreen());

        menu.getConstraints().addX(new UICenterConstraint());
        menu.getConstraints().addY(new UICenterConstraint());
        menu.getConstraints().addHeight(new UIUnitConstraint(30));
        menu.getConstraints().addWidth(new UIPassthroughConstraint());

        UIImage background = new UIImage("ui/escape_menu.png");
        background.setConstraints(ConstraintFactory.fullscreen());
        background.getConstraints().addWidth(new UIImageAspectConstraint());
        menu.add(background);

        UIEscapeButton backButton = new UIEscapeButton("ui/escape_back_button.png", "ui/escape_small_button_hover.png", "ui/escape_small_button_press.png");
        backButton.getConstraints().addX(new UICenterConstraint());
        backButton.getConstraints().addY(new UIUnitConstraint(3));
        backButton.getConstraints().addHeight(new UIUnitConstraint(6));
        backButton.getConstraints().addWidth(new UIPassthroughConstraint());
        backButton.addListener(() -> {
            state = State.PENDING_OFF;
        });
        menu.add(backButton);

        UIContainer container = new UIContainer();
        container.getConstraints().addX(new UICenterConstraint());
        container.getConstraints().addY(new UIUnitConstraint(-3, true));
        container.getConstraints().addHeight(new UIPassthroughConstraint());
        container.getConstraints().addWidth(new UIPassthroughConstraint());
        menu.add(container);

        UIEscapeButton exitButton = new UIEscapeButton("ui/escape_exit_button.png", "ui/escape_small_button_hover.png", "ui/escape_small_button_press.png");
        exitButton.getConstraints().addX(new UICenterConstraint());
        exitButton.getConstraints().addY(new UIRelativeConstraint(-1));
        exitButton.getConstraints().addHeight(new UIUnitConstraint(6));
        exitButton.getConstraints().addWidth(new UIPassthroughConstraint());
        exitButton.addListener(() -> {
            GameWindow.get().changeScene(new MainMenu());
        });
        container.add(exitButton);

        UIEscapeButton optionsButton = new UIEscapeButton("ui/escape_options_button.png", "ui/escape_big_button_hover.png", "ui/escape_big_button_press.png");
        optionsButton.getConstraints().addX(new UICenterConstraint());
        optionsButton.getConstraints().addY(new UICenterConstraint());
        optionsButton.getConstraints().addHeight(new UIUnitConstraint(6));
        optionsButton.getConstraints().addWidth(new UIPassthroughConstraint());
        optionsButton.addListener(() -> {
            pages.switchPage(1);
        });
        menu.add(optionsButton);
    }
}
