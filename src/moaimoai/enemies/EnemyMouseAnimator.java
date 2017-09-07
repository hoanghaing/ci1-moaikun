package moaimoai.enemies;

import bases.Vector2D;
import bases.renderers.Animation;
import bases.renderers.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;

/**
 * Created by NguyenGiaThe on 9/6/2017.
 */
public class EnemyMouseAnimator implements Renderer {

    private Animation leftMoving = new Animation(
            SpriteUtils.loadImage("assets/images/enemies/level1/left/1.png"),
            SpriteUtils.loadImage("assets/images/enemies/level1/left/2.png"),
            SpriteUtils.loadImage("assets/images/enemies/level1/left/3.png"),
            SpriteUtils.loadImage("assets/images/enemies/level1/left/4.png")
    );

    private Animation rightMoving = new Animation(
            SpriteUtils.loadImage("assets/images/enemies/level1/right/1.png"),
            SpriteUtils.loadImage("assets/images/enemies/level1/right/2.png"),
            SpriteUtils.loadImage("assets/images/enemies/level1/right/3.png"),
            SpriteUtils.loadImage("assets/images/enemies/level1/right/4.png")
    );

    private Animation current = rightMoving;

    public void update(EnemyRabit enemy){
        Vector2D velocity = enemy.getVelocity();
        if (velocity.x > 0){
            current = rightMoving;
        }
        if (velocity.x < 0){
            current = leftMoving;
        }
    }

    @Override
    public void render(Graphics2D g2d, Vector2D position) {
        current.render(g2d, position);
    }
}
