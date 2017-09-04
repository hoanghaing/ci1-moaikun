package moaimoai.enemies;

import bases.Vector2D;
import bases.renderers.Animation;
import bases.renderers.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;

/**
 * Created by NguyenGiaThe on 9/4/2017.
 */
public class Enemy1Aminator implements Renderer {
    private Animation leftMoveing = new Animation(
            SpriteUtils.loadImage("assets/images/enemies/level2/left/1.png"),
            SpriteUtils.loadImage("assets/images/enemies/level2/left/2.png"),
            SpriteUtils.loadImage("assets/images/enemies/level2/left/3.png"),
            SpriteUtils.loadImage("assets/images/enemies/level2/left/4.png")
    );

    private Animation rightMoving = new Animation(
            SpriteUtils.loadImage("assets/images/enemies/level2/right/1.png"),
            SpriteUtils.loadImage("assets/images/enemies/level2/right/2.png"),
            SpriteUtils.loadImage("assets/images/enemies/level2/right/3.png"),
            SpriteUtils.loadImage("assets/images/enemies/level2/right/4.png")
    );

    private Animation current = rightMoving;

    public void update(Enemy enemy){
        Vector2D velocity = enemy.getVelocity();
        if (velocity.x > 0){
            current = rightMoving;
        }
        if (velocity.x < 0){
            current = leftMoveing;
        }
    }


    @Override
    public void render(Graphics2D g2d, Vector2D position) {
        current.render(g2d, position);
    }
}
