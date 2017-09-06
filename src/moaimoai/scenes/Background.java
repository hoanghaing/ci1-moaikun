package moaimoai.scenes;

import bases.GameObject;
import bases.Vector2D;
import bases.renderers.ImageRenderer;
import moaimoai.settings.Settings;
import tklibs.SpriteUtils;

public class Background extends GameObject {
    private ImageRenderer imageRenderer;
    private final float imageHeight;

    public Background(int type) {
        super();
        String background = "assets/images/backgrounds/background" + type + ".png" ;
        this.imageRenderer = new ImageRenderer(
                SpriteUtils.loadImage(background)
        );
        this.imageRenderer.getAnchor().set(0, 1);
        this.position.set(0, Settings.instance.getGamePlayHeight());
        this.imageHeight = imageRenderer.image.getHeight();
        this.renderer = imageRenderer;
    }

}
