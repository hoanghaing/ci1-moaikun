package moaimoai.players;

import bases.Constraints;
import bases.FrameCounter;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.BrokenPlatform;
import bases.platforms.Platform;
import bases.renderers.ImageRenderer;
import moaimoai.inputs.InputManager;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public class Player extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private static final int SPEED = 3;
    private PlayerAnimator playerAnimator;
    private Constraints constraints;


    private Vector2D velocity;

    private static Player instance;

    public static Player getInstance(){
        return instance;
    }

    public Player(){
        super();
        this.velocity = new Vector2D();
        this.boxCollider = new BoxCollider(32, 32);
        this.children.add(boxCollider);
        this.playerAnimator = new PlayerAnimator();
    }

    public void setConstraints(Constraints constraints){
        this.constraints = constraints;
    }

    public void run(Vector2D parentPosition){
        super.run(parentPosition);
        playerAnimator.update(this);

        this.velocity.y += 0.7;
        velocity.x = 0;

        if (InputManager.instance.cPressed){

            if (Physics.collideWith(screenPosition,position.add(0, 1), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) != null) {
                this.velocity.y = -7;
            }
        }
        if (InputManager.instance.leftPressed)
                velocity.x -= SPEED;

        if (InputManager.instance.rightPressed)
                velocity.x += SPEED;


        if (constraints != null) {
            constraints.make(position);
        }

        if(InputManager.instance.xPressed){
            hitRock();
        }

        moveHorizontal(); // xu li va cham theo chieu ngang
        moveVertical(); // xu li va cham theo chieu doc, trong luc , bla bla
        this.position.addUp(velocity);

    }

    private void hitRock() {
        Vector2D checkPosition = screenPosition.add(playerAnimator.getCheck(), 0);
        Platform platform = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight() / 1000000, Platform.class);
        if(platform != null){
            platform.getHit();
            BrokenPlatform brokenPlatform = new BrokenPlatform();
            brokenPlatform.getPosition().set(platform.getPosition());
            GameObject.add(brokenPlatform);
        }
    }

    public Vector2D getVelocity() {
        return velocity;
    }
    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }


    private void moveHorizontal() {
        float deltaX = velocity.x > 0 ? 1: -1;
        PhysicsBody body = Physics.collideWith(screenPosition,position.add(velocity.x, 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (body != null) {
            while(Physics.collideWith(screenPosition,position.add(deltaX, 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(deltaX, 0);
            }
            this.velocity.x = 0;
        }
    }

    private void moveVertical() {
        float deltaY = velocity.y > 0 ? 1: -1;
        PhysicsBody body = Physics.collideWith(screenPosition,position.add(0, velocity.y), boxCollider.getWidth(), boxCollider.getHeight(),
                Platform.class);
        if (body != null) {
            while(Physics.collideWith(screenPosition,position.add(0, deltaY), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(0, deltaY);
            }
            this.velocity.y = 0;
        }
    }

    public void getHit(){
        this.isActive = false;
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
        playerAnimator.render(g2d,position);
    }
}
