package moaimoai.players;

import bases.Constraints;
import bases.FrameCounter;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
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
    private InputManager inputManager;
    private Constraints constraints;


    private Vector2D velocity;

    private static Player instance;

    public static Player getInstance(){
        return instance;
    }

    public Player(){
        super();
        this.velocity = new Vector2D();
        this.boxCollider = new BoxCollider(20, 20);
        this.children.add(boxCollider);
        this.playerAnimator = new PlayerAnimator();
    }

    public void setConstraints(Constraints constraints){
        this.constraints = constraints;
    }

    public void run(Vector2D parentPosition){
        super.run(parentPosition);
        playerAnimator.update(this);
        velocity.set(0,0);

        this.velocity.y += 2;

        if (InputManager.instance.cPressed){

            if (Physics.bodyInRect(position.add(0, 1), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) != null) {
                this.velocity.y = -35;
            }
        }
        if (InputManager.instance.leftPressed)
                velocity.x -= SPEED;

        if (InputManager.instance.rightPressed)
                velocity.x += SPEED;


        if (constraints != null) {
            constraints.make(position);
        }

        moveHorizontal(); // xu li va cham theo chieu ngang
        moveVertical(); // xu li va cham theo chieu doc, trong luc , bla bla
        this.position.addUp(velocity);

    }

    public Vector2D getVelocity() {
        return velocity;
    }
    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    private void moveHorizontal() {
        float deltaX = velocity.x > 0 ? 1: -1;
        PhysicsBody body = Physics.bodyInRect(position.add(velocity.x, 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (body != null) {
            while(Physics.bodyInRect(position.add(deltaX, 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(deltaX, 0);
            }
            this.velocity.x = 0;
        }
    }

    private void moveVertical() {
        float deltaY = velocity.y > 0 ? 1: -1;
        PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.getWidth(), boxCollider.getHeight(),
                Platform.class);
        if (body != null) {
            while(Physics.bodyInRect(position.add(0, deltaY), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(0, deltaY);
            }
            this.velocity.y = 0;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
        playerAnimator.render(g2d,position);
    }


}
