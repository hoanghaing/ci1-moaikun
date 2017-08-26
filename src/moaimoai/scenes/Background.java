package moaimoai.scenes;

import bases.GameObject;
import bases.Vector2D;
import bases.renderers.ImageRenderer;
import moaimoai.settings.Settings;
import tklibs.SpriteUtils;

public class Background extends GameObject {
    private ImageRenderer imageRenderer;
    private final float SPEED = 4;
    private final float imageHeight;

    public Background() {
        super();
        this.imageRenderer = new ImageRenderer(
                SpriteUtils.loadImage("assets/images/backgrounds/background1.png")
        );
        this.imageRenderer.getAnchor().set(0, 1);
        this.position.set(0, Settings.instance.getGamePlayHeight());
        this.imageHeight = imageRenderer.image.getHeight();
        this.renderer = imageRenderer;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        position.y += SPEED;
        if (position.y > imageHeight) {
            position.y = imageHeight;
        }
    }
}
