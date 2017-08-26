package bases.renderers;

import bases.Vector2D;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer implements Renderer {
    public BufferedImage image;
    private Vector2D anchor;


    public ImageRenderer(BufferedImage image) {
        this.image = image;
        this.anchor = new Vector2D(0.5f, 0.5f);
    }

    public void render(Graphics2D g2d, Vector2D position) {
        g2d.drawImage(image,
                (int)(position.x - (image.getWidth() * anchor.x)),
                (int)(position.y - (image.getHeight() * anchor.y)),
                null);
    }

    public Vector2D getAnchor() {
        return anchor;
    }

    public static ImageRenderer create(String url){
        return new ImageRenderer(SpriteUtils.loadImage(url));
    }
}
