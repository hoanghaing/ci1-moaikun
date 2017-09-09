package moaimoai.scenes;

import bases.GameObject;
import bases.Vector2D;
import bases.renderers.ImageRenderer;
import moaimoai.inputs.InputManager;
import moaimoai.settings.Settings;
import tklibs.SpriteUtils;

public class Tutorial extends GameObject{
    private ImageRenderer imageRenderer;
    private final float imageHeight;
    public Tutorial(int type) {
        super();
        String tut = "assets/images/tutorials/tut" + type +".png";
        this.imageRenderer = new ImageRenderer(
                SpriteUtils.loadImage(tut)
        );
        this.imageRenderer.getAnchor().set(0, 1);
        this.position.set(0, Settings.instance.getGamePlayHeight());
        this.imageHeight = imageRenderer.image.getHeight();
        this.renderer = imageRenderer;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
    }
}
