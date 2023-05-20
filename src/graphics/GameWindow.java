package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

import input.MouseListener;
import scenes.Scene;
import scenes.mainmenu.MainMenu;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

public class GameWindow {
    private static GameWindow instance;

    public int WIDTH, HEIGHT;
    private JFrame window;
    private Scene currentScene;

    private boolean fullscreend = false;

    public int FPS_LIMIT = 60;

    private GameWindow() {
        WIDTH = 1200;
        HEIGHT = 800;
    }

    public static GameWindow get() {
        if (instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }

    public Window getWindow() {
        return window;
    }

    public void setup() {
        window = new JFrame("Golf");
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.setIgnoreRepaint(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        window.pack();

        window.setLocationRelativeTo(null);
        //setFullscreen();
        window.setVisible(true);

        window.createBufferStrategy(2);

        changeScene(new MainMenu());
    }

    public void run() {
        BufferStrategy strategy = window.getBufferStrategy();
        Graphics2D g = null;

        long lastTime = System.nanoTime();
        long timeElapsed = 0;
        float fps = 0;

        while (true) {
            //TODO: Only do this on size change
            Rectangle size = window.getBounds();
            Insets insets = window.getInsets();
            WIDTH = size.width - insets.left - insets.right;
            HEIGHT = size.height - insets.bottom - insets.top;

            g = (Graphics2D) strategy.getDrawGraphics();
            g.translate(insets.left, insets.top);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            long now = System.nanoTime();
            long frameTime = now - lastTime;
            timeElapsed += frameTime;
            lastTime = now;

            if (timeElapsed > 1e9 * 0.1) {
                fps = (float) 1e9 / frameTime;
                timeElapsed = 0;
            }

            currentScene.update((float)(frameTime/1e6));

            currentScene.render(g);

            g.setFont(new Font("Calibri", Font.PLAIN, 12));
            g.setColor(Color.GREEN);
            g.drawString(String.format("FPS: %.0f", fps), 0, 10);

            g.dispose();
            if (!strategy.contentsLost()) strategy.show();

            long sleepTime = (long) 1e9/FPS_LIMIT - frameTime;
            if (sleepTime > 0) {
                try {
                    Thread.sleep((long)(sleepTime/1e6));
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public void setFullscreen() {
        if (fullscreend) return;

        GraphicsDevice gd = getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            window.setVisible(false);
            gd.setFullScreenWindow(window);

            DisplayMode mode = gd.getDisplayMode();
            window.setPreferredSize(new Dimension(mode.getWidth(), mode.getHeight()));
            window.setVisible(true);
            MouseListener.get().reset();

            fullscreend = true;
        }

    }

    public void setWindowed() {
        if (!fullscreend) return;

        window.setVisible(false);

        GraphicsDevice gd = getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(null);

        window.setPreferredSize(new Dimension(1200, 800));
        window.setVisible(true);
        // Regestriert sonst die Knopfdrücke nicht richtig da beim wechsel ja die Maus zwangsweise gedrückt ist (vielleicht wegen window.setvisible?)
        MouseListener.get().reset();

        fullscreend = false;
    }

    public void exit() {
        System.exit(0);
    }

    public void changeScene(Scene s) {
        currentScene = s;
        currentScene.init();
    }

    public Scene getScene() {
        return currentScene;
    }
}
