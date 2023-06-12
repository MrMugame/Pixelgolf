package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

import assets.Assets;
import input.MouseListener;
import scenes.Scene;
import scenes.mainmenu.MainMenu;
import sound.SoundSystem;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

public class GameWindow {
    private static GameWindow instance;

    public int WIDTH, HEIGHT;
    private JFrame window;
    private Scene currentScene;

    private boolean fullscreen = false;

    public boolean DEBUG = false;
    public int FPS_LIMIT = 120;

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
        // OpenGL acceleration einschalten wegen komischem Bug, welcher Images die auf den Canvas gemalt werden und über den Canvas herausstehen verzerrt damit keine halben Pixels am Canvas-Rand gemalt werden müssen (Warum auch immer, vermutlich ein Bug?! Vielleicht ein Mac Problem?). Auf jeden Fall ist es mit opengl nicht so
        System.setProperty("sun.java2d.opengl", "true");
        window = new JFrame("PixelGolf");
        window.setIconImage(new ImageIcon(Assets.loadImage("icon.png")).getImage());
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.setIgnoreRepaint(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.createBufferStrategy(2);

        changeScene(new MainMenu());
    }

    public void run() {
        BufferStrategy strategy = window.getBufferStrategy();
        Graphics2D g;

        long lastTime = System.nanoTime();
        long timeElapsed = 0;
        int frames = 0;
        float fps = 0;


        while (true) {
            // TODO: Rerender Level background on size change
            Rectangle size = window.getBounds();
            Insets insets = window.getInsets();
            WIDTH = size.width - insets.left - insets.right;
            HEIGHT = size.height - insets.bottom - insets.top;

            g = (Graphics2D) strategy.getDrawGraphics();
            g.translate(insets.left, insets.top);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            frames += 1;
            long now = System.nanoTime();
            long frameTime = now - lastTime;
            timeElapsed += frameTime;
            lastTime = now;

            if (timeElapsed > 1e9 * 0.2) {
                fps = (float) (frames * 1e9 / timeElapsed);
                timeElapsed = 0;
                frames = 0;
            }

            currentScene.update((float) (frameTime/1e6));

            currentScene.render(g);

            if (DEBUG) {
                g.setFont(new Font("Calibri", Font.PLAIN, 12));
                g.setColor(Color.RED);
                g.drawString(String.format("FPS: %.0f", fps), 0, 10);
                g.drawString(String.format("Mouse: %f; %f", MouseListener.get().getMousePosition().x, MouseListener.get().getMousePosition().y), 0, 20);
                long totalMemroy = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                g.drawString(String.format("Memory: %.0f/%.0fMB",(totalMemroy - freeMemory)/1e6, totalMemroy/1e6), 0, 30);

            }

            g.dispose();
            if (!strategy.contentsLost()) strategy.show();

            SoundSystem.get().update();

            // Sleep call macht die Bildrate inakkurat (Sleep call dauert meist länger als er soll) aufgrund vom Kernel Scheduler. Unterscheidet sich somit auch von Betriebssystem zu Betriebssystem. Alle anderen Lösungen sind jedoch Mord an der CPU
            long sleepTime = (long) 1e9/FPS_LIMIT - frameTime;
            if (sleepTime > 0) {
                try {
                    Thread.sleep((long)(sleepTime/1e6));
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public void setFullscreen() {
        if (fullscreen) return;

        GraphicsDevice gd = getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            window.setVisible(false);
            window.dispose();
            window.setUndecorated(true);
            gd.setFullScreenWindow(window);

            DisplayMode mode = gd.getDisplayMode();
            window.setPreferredSize(new Dimension(mode.getWidth(), mode.getHeight()));
            window.setVisible(true);
            window.pack();
            window.createBufferStrategy(2);
            MouseListener.get().reset();

            fullscreen = true;
        }

    }

    public void setWindowed() {
        if (!fullscreen) return;

        window.setVisible(false);
        window.dispose();

        GraphicsDevice gd = getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.getFullScreenWindow() == window) gd.setFullScreenWindow(null);

        window.setPreferredSize(new Dimension(1200, 800));
        window.setUndecorated(false);
        window.setVisible(true);
        // Um sicherzugehen, dass kein Mist passiert
        MouseListener.get().reset();

        fullscreen = false;
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

    public boolean isFullscreen() {
        return fullscreen;
    }
}
