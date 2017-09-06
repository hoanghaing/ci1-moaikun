package moaimoai.allies;

import bases.Vector2D;
import bases.renderers.Animation;
import bases.renderers.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;

public class AllyAnimator implements Renderer{

    private Animation orangeAllyAnimation = new Animation(
          SpriteUtils.loadImage("assets/images/peoples/orange/people1.png"),
            SpriteUtils.loadImage("assets/images/peoples/orange/people2.png"),
            SpriteUtils.loadImage("assets/images/peoples/orange/people3.png"),
            SpriteUtils.loadImage("assets/images/peoples/orange/people4.png"),
            SpriteUtils.loadImage("assets/images/peoples/orange/people5.png")
            );

    private Animation pinkAllyAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/peoples/pink/1.png"),
            SpriteUtils.loadImage("assets/images/peoples/pink/2.png"),
            SpriteUtils.loadImage("assets/images/peoples/pink/3.png"),
            SpriteUtils.loadImage("assets/images/peoples/pink/4.png"),
            SpriteUtils.loadImage("assets/images/peoples/pink/5.png")

    );
    private Animation currentAnimation = pinkAllyAnimation;
    @Override
    public void render(Graphics2D g2d, Vector2D position) {
        currentAnimation.render(g2d, position);
    }
}
