package gui.components;

import assets.Assets;
import gui.UIComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class UIText extends UIComponent {
    private String text;
    private Color color;
    private int fontSize;
    private String path;

    private boolean center;

    public UIText(String text, Color color, String path, int fontSize, boolean center) {
        this.text = text;
        this.color = color;
        this.fontSize = fontSize;
        this.path = path;
        this.center = center;
    }

    @Override
    protected void render(Graphics2D g) {
        g.setColor(color);

        try {
            g.setFont(Objects.requireNonNull(Assets.loadFont(path)).deriveFont((float) fontSize));
        } catch (NullPointerException ignored) {}


        FontMetrics metrics = g.getFontMetrics();
        ArrayList<String> lines = calculateLines(metrics);
        int padding = (getHeight() - lines.size() * metrics.getHeight()) / 2 - metrics.getDescent();
        int y = getY() + padding;

        for (String line : lines) {
            y += metrics.getHeight();
            int offset = center ? (getWidth() - metrics.stringWidth(line)) / 2 : 0;
            g.drawString(line, getX() + offset, y);
        }

    }

    private ArrayList<String> calculateLines(FontMetrics metrics) {
        ArrayList<String> lines = new ArrayList<>();

        String[] words = text.split(" ");
        String line = "";
        // TODO: Add support for explicit line breaks
        for (String word : words) {
            if (metrics.stringWidth(line + word) >= getWidth()) {
                lines.add(line);
                line = "";
            }

            line += line.length() == 0 ? word : " " + word;
        }

        if(line.trim().length() > 0) {
            lines.add(line);
        }

        return lines;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
