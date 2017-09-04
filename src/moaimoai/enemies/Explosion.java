package moaimoai.enemies;

import bases.GameObject;
import bases.renderers.Animation;
import tklibs.SpriteUtils;

import java.awt.*;

/**
 * Created by NguyenGiaThe on 9/4/2017.
 */
public class Explosion extends GameObject {
    private Animation animation;

    public Explosion() {
        this.animation = new Animation(7, true,false,
                SpriteUtils.loadImage("assets/images/explosion/enemy explosion/expl_01_0003.png"),
                SpriteUtils.loadImage("assets/images/explosion/enemy explosion/expl_01_0004.png"),
                SpriteUtils.loadImage("assets/images/explosion/enemy explosion/expl_01_0005.png"),
                SpriteUtils.loadImage("assets/images/explosion/enemy explosion/expl_01_0006.png"),
                SpriteUtils.loadImage("assets/images/explosion/enemy explosion/expl_01_0007.png")
        );
        this.renderer = animation;
    }

    @Override
    public void reset() {
        animation.reset();
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
    }
}
