package bases.platforms;

import bases.GameObject;
import bases.renderers.Animation;
import tklibs.SpriteUtils;

import java.awt.*;

public class BrokenPlatform extends GameObject{
    private Animation animation;

    public BrokenPlatform(){
        this.animation = new Animation(
                5,
                true,
                false,
                SpriteUtils.loadImage("assets/images/explosion/brick explosion/explosion2.png"),
                SpriteUtils.loadImage("assets/images/explosion/brick explosion/explosion4.png"),
                SpriteUtils.loadImage("assets/images/explosion/brick explosion/explosion6.png")
                );
        this.renderer = animation;
    }

    @Override
    public void reset() {
        super.reset();
        animation.reset();
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
        if(animation.isStopped()){
            this.isActive = false;
        }
    }
}
