package moaimoai.players;

import bases.GameObject;
import bases.Vector2D;
import bases.renderers.Animation;
import tklibs.SpriteUtils;

import java.awt.*;

public class PlayerDeath extends GameObject{
    private Animation animation;
    private final int SPEED = 2;

    public PlayerDeath(){
        this.animation = new Animation(
                7,
                false,
                false,
                SpriteUtils.loadImage("assets/images/player/left/dead/1.png"),
                SpriteUtils.loadImage("assets/images/player/left/dead/2.png")
        );
        this.renderer = animation;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.position.y -= SPEED;
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
