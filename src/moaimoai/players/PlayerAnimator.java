
package moaimoai.players;

import bases.Vector2D;
import bases.renderers.Animation;
import bases.renderers.Renderer;
import moaimoai.inputs.InputManager;
import tklibs.SpriteUtils;

import java.awt.*;

public class PlayerAnimator implements Renderer {
    private boolean left = true;
    private boolean right;
    InputManager inputManager = InputManager.instance;

    private Animation standingAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/left/stand/1.png")
    );

    private Animation leftStandingAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/left/stand/1.png")
    );

    private Animation rightStandingAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/stand/1.png")
    );

    //SITTING (Ngồi xuống, dùng khi đặt bom)
    private Animation leftSittingAnimation = new Animation(

            SpriteUtils.loadImage("assets/images/player/left/sit/2.png")
    );

    private Animation rightSittingAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/sit/2.png")
    );

    //MOVING (Di chuyển trái phải)
    private Animation leftMovingAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/left/move/1.png"),
            SpriteUtils.loadImage("assets/images/player/left/move/2.png")
    );
    private Animation rightMovingAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/move/1.png"),
            SpriteUtils.loadImage("assets/images/player/right/move/2.png")
    );


    //ATTACK (Đánh đầu tấn công)
    private Animation leftAttackAnimation = new Animation(10, false, false,
//            SpriteUtils.loadImage("assets/images/player/left/attack/1.png"),
            SpriteUtils.loadImage("assets/images/player/left/attack/2.png")
    );
    private Animation rightAttackAnimation = new Animation(10, false, false,
//            SpriteUtils.loadImage("assets/images/player/right/attack/1.png"),
            SpriteUtils.loadImage("assets/images/player/right/attack/2.png")

    );

    //FALL FROM HIGH PLACE (Khi rơi tự do từ trên cao xuống)
    private Animation leftFallAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/left/fall/1.png")
    );

    private Animation rightFallAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/fall/2.png")
    );

    //LAY DOWN WHEN HURT (Khi nhảy từ trên cao xuống bị đau)

    private Animation leftLaydownAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/left/lay/1.png"),
            SpriteUtils.loadImage("assets/images/player/left/lay/2.png")
    );
    private Animation rightLaydownAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/lay/1.png"),
            SpriteUtils.loadImage("assets/images/player/right/lay/1.png")
    );

    //RUN (Khi chạy trên băng lạnh)

    private Animation leftRunintheICEAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/left/run/1.png"),
            SpriteUtils.loadImage("assets/images/player/left/run/2.png"),
            SpriteUtils.loadImage("assets/images/player/left/run/3.png")
    );

    private Animation rightRunintheICEAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/run/1.png"),
            SpriteUtils.loadImage("assets/images/player/right/run/2.png"),
            SpriteUtils.loadImage("assets/images/player/right/run/3.png")
    );

    //DEAD : (Khi chết toi) (Hơi xấu , có thể làm nổ tung hoặc làm như Mario

    private Animation deadAnimation = new Animation(
            SpriteUtils.loadImage("assets/images/player/right/dead/1.png"),
            SpriteUtils.loadImage("assets/images/player/right/dead/2.png")
    );

    private Animation currentAnimation = standingAnimation;
    private int check;

    public void update(Player player) {
        Vector2D velocity = player.getVelocity();

        if (InputManager.instance.leftPressed) {
            currentAnimation = leftMovingAnimation;
            left = true;
            right = false;
        }
        if (InputManager.instance.rightPressed) {
            currentAnimation = rightMovingAnimation;
            right = true;
            left = false;
        }

        if (velocity.x == 0 && left) {
            currentAnimation = leftStandingAnimation;
        }
        if (velocity.x == 0 && right) {
            currentAnimation = rightStandingAnimation;
        }
        if (inputManager.downPressed) {
            InputManager.instance.rightPressed = false;
            InputManager.instance.leftPressed = false;
            InputManager.instance.cPressed = false;
            if (left)
                currentAnimation = leftSittingAnimation;
            if (right)
                currentAnimation = rightSittingAnimation;
        }
        if (inputManager.cPressed) {
            if (left)
                currentAnimation = leftFallAnimation;
            if (right)
                currentAnimation = rightFallAnimation;
        }

        if (inputManager.xPressed && !inputManager.downPressed) {
            if (left) {
                currentAnimation = leftAttackAnimation;
            }
            if (right) {
                currentAnimation = rightAttackAnimation;
            }
        }


    }

    @Override
    public void render(Graphics2D g2d, Vector2D position) {
        currentAnimation.render(g2d, position);
    }
}

