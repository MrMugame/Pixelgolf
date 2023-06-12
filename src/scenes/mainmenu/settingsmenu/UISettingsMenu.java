package scenes.mainmenu.settingsmenu;

import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.*;
import gui.constraints.*;
import scenes.mainmenu.settingsmenu.components.UIBackButton;
import scenes.mainmenu.settingsmenu.components.UISelectButton;
import scenes.mainmenu.settingsmenu.components.UISlider;
import sound.SoundSystem;
import state.GameState;

public class UISettingsMenu extends UIComponent {

    public UISettingsMenu() {}

    @Override
    protected void init() {
        UIComponent background = new UIImage("ui/background_2.png");
        background.setConstraints(ConstraintFactory.fullscreenFitImage());
        add(background);

        UIBackButton backButton = new UIBackButton();
        backButton.setConstraints(ConstraintFactory.unitConstrains(2, 2, 6, 0));
        backButton.getConstraints().addHeight(new UIPassthroughConstraint());
        backButton.addListener(() -> {
            ((UIPage) getParent()).switchPage(0);
        });
        add(backButton);

        UIComponent block = new UIImage("ui/settings_container.png");
        block.getConstraints().addX(new UICenterConstraint());
        block.getConstraints().addY(new UICenterConstraint());
        block.getConstraints().addWidth(new UIUnitConstraint(50));
        block.getConstraints().addHeight(new UIImageAspectConstraint());

        UIContainer container = new UIContainer();
        container.getConstraints().addX(new UICenterConstraint());
        container.getConstraints().addY(new UICenterConstraint());
        container.getConstraints().addWidth(new UIRelativeConstraint(0.7f));
        container.getConstraints().addHeight(new UIRelativeConstraint(0.80f));
        block.add(container);

        UISelectButton buttonScreen = new UISelectButton(GameWindow.get().isFullscreen() ? 1 : 0, "Fullscreen: Off", "Fullscreen: On");
        buttonScreen.getConstraints().addX(new UIUnitConstraint(0));
        buttonScreen.getConstraints().addY(new UIUnitConstraint(0));
        buttonScreen.getConstraints().addWidth(new UIUnitConstraint(15));
        buttonScreen.getConstraints().addHeight(new UIPassthroughConstraint());
        buttonScreen.addListener((i) -> {
            switch (i) {
                case 0 -> GameWindow.get().setWindowed();
                case 1 -> GameWindow.get().setFullscreen();
            }
        });
        container.add(buttonScreen);

        UISelectButton buttonFPS = new UISelectButton(GameWindow.get().FPS_LIMIT == 60 ? 0 : GameWindow.get().FPS_LIMIT == 120 ? 1 : 2, "Framerate: 60", "Framerate: 120", "Framerate: MAX");
        buttonFPS.getConstraints().addX(new UIUnitConstraint(0));
        buttonFPS.getConstraints().addY(new UIUnitConstraint(5));
        buttonFPS.getConstraints().addWidth(new UIUnitConstraint(15));
        buttonFPS.getConstraints().addHeight(new UIPassthroughConstraint());
        buttonFPS.addListener((i) -> {
            switch (i) {
                case 0 -> GameWindow.get().FPS_LIMIT = 60;
                case 1 -> GameWindow.get().FPS_LIMIT = 120;
                case 2 -> GameWindow.get().FPS_LIMIT = 999999999;
            }
        });
        container.add(buttonFPS);

        UISelectButton buttonDebug = new UISelectButton(GameWindow.get().DEBUG ? 0 : 1, "Debug: On", "Debug: Off");
        buttonDebug.getConstraints().addX(new UIUnitConstraint(0));
        buttonDebug.getConstraints().addY(new UIUnitConstraint(10));
        buttonDebug.getConstraints().addWidth(new UIUnitConstraint(15));
        buttonDebug.getConstraints().addHeight(new UIPassthroughConstraint());
        buttonDebug.addListener((i) -> {
            switch (i) {
                case 0 -> GameWindow.get().DEBUG = true;
                case 1 -> GameWindow.get().DEBUG = false;
            }
        });
        container.add(buttonDebug);

        UISelectButton tutorialDebug = new UISelectButton(0, "Reset Tutorial");
        tutorialDebug.getConstraints().addX(new UIUnitConstraint(0));
        tutorialDebug.getConstraints().addY(new UIUnitConstraint(15));
        tutorialDebug.getConstraints().addWidth(new UIUnitConstraint(15));
        tutorialDebug.getConstraints().addHeight(new UIPassthroughConstraint());
        tutorialDebug.addListener((i) -> {
            GameState.get().setProperty("tutorialPlayed", false);
        });
        container.add(tutorialDebug);

        UISlider sliderSFX = new UISlider("Effects", SoundSystem.get().getSFXVolume(), 0, 2);
        sliderSFX.getConstraints().addX(new UIEndAlignContstraint());
        sliderSFX.getConstraints().addY(new UIUnitConstraint(0));
        sliderSFX.getConstraints().addWidth(new UIUnitConstraint(15));
        sliderSFX.getConstraints().addHeight(new UIPassthroughConstraint());
        sliderSFX.addListener((value -> {
            SoundSystem.get().setSFXVolume(value);
        }));
        container.add(sliderSFX);

        UISlider sliderMusic = new UISlider("Music", SoundSystem.get().getMusicVolume(), 0, 2);
        sliderMusic.getConstraints().addX(new UIEndAlignContstraint());
        sliderMusic.getConstraints().addY(new UIUnitConstraint(5));
        sliderMusic.getConstraints().addWidth(new UIUnitConstraint(15));
        sliderMusic.getConstraints().addHeight(new UIPassthroughConstraint());
        sliderMusic.addListener((value -> {
            SoundSystem.get().setMusicVolume(value);
        }));
        container.add(sliderMusic);

        add(block);
    }
}