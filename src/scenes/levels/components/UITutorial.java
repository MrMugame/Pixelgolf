package scenes.levels.components;

import assets.Assets;
import event.Event;
import event.EventSystem;
import event.EventType;
import event.Observer;
import gui.UIComponent;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.*;
import state.GameState;

import java.awt.*;

public class UITutorial extends UIComponent implements Observer {
    private UIComponent text, box;

    enum Stage {
        INIT(1),
        FIRST_STROKE(2),
        ZOOMED_OUT(3),
        FINISHED(4);

        private int i;
        Stage(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }
    }
    private Stage stage = Stage.INIT;

    public UITutorial() {
        EventSystem.addObserver(this);
    }

    @Override
    protected void init() {
        box = new UIImage("ui/tutorial_box.png");
        box.getConstraints().addX(new UIEndAlignContstraint(new UIUnitConstraint(1)));
        box.getConstraints().addY(new UIEndAlignContstraint(new UIUnitConstraint(1)));
        box.getConstraints().addWidth(new UIUnitConstraint(25));
        box.getConstraints().addHeight(new UIImageAspectConstraint());
        add(box);

        updateText("Press on the ball with your left mouse button and hold to start your stroke and move the mouse to control the direction. Let go to hit.");
    }

    @Override
    public void onNotify(Event<?> e) {
        if (e.getType() == EventType.TUTORIAL) {
            switch ((String) e.getData()) {
                case "zoomedout":
                    if (stage.getI() < Stage.ZOOMED_OUT.getI()) {
                        stage = Stage.ZOOMED_OUT;
                        updateText("Follow the course until you hit the hole! But be cautious and try to use as few strokes as possible. In the top right you see how many strokes you have taken so far.");
                    }
                    break;
                case "firststroke":
                    if (stage.getI() < Stage.FIRST_STROKE.getI()) {
                        stage = Stage.FIRST_STROKE;
                        updateText("You can press and hold C on your Keyboard to zoom out and see the full course. Try it!");
                    }
                    break;
                case "finished":
                    if (stage.getI() < Stage.FINISHED.getI()) {
                        stage = Stage.FINISHED;
                        removeAll();
                        GameState.get().setProperty("tutorialPlayed", true);
                    }
                    break;
            }
        }
    }

    private void updateText(String msg) {
        box.remove(text);
        text = new UIText(msg, Color.BLACK, Assets.DEFAULT_FONT, 14, false);
        text.getConstraints().addX(new UICenterConstraint());
        text.getConstraints().addY(new UICenterConstraint());
        text.getConstraints().addWidth(new UIRelativeConstraint(0.9f));
        text.getConstraints().addHeight(new UIRelativeConstraint(0.9f));
        box.add(text);
    }
}
