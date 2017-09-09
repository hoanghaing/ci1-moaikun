package bases.renderers;

import bases.Vector2D;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextRenderer implements  Renderer{
    public String text;
    private Vector2D anchor;
    private Font font;
    private Color color;

    public TextRenderer(String text) {
        this.text = text;
        this.anchor = new Vector2D(0.5f, 0.5f);
        font = new Font("", Font.BOLD, 20);
        color = Color.BLACK;
    }


    public void render(Graphics2D g2d, Vector2D position) {
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text,
                (int)(position.x),
                (int)(position.y));
     }

    public Vector2D getAnchor() {
        return anchor;
    }

    public static TextRenderer create(String text){
        return new TextRenderer(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
