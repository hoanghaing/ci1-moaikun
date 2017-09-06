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
import moaimoai.GameWindow;
import moaimoai.allies.BombObject;
import moaimoai.allies.FriendlyObject;
import moaimoai.enemies.Enemy;
import moaimoai.enemies.Explosion;
import moaimoai.inputs.InputManager;
import moaimoai.scenes.GameOverScene;
import moaimoai.scenes.Scene;
import moaimoai.scenes.SceneManager;
import moaimoai.settings.Settings;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public class Player extends GameObject implements PhysicsBody {
    Settings settings = Settings.instance;
    private final float SPEED = 2;
    private Vector2D velocity;
    private final float GRAVITY = 0.5f;
    private BoxCollider boxCollider;
    private Constraints constraints;
    private boolean attack;
    private boolean left = true;
    private boolean right;
    private int rangeAttack;
    private int bomb = 0;


    private FrameCounter attackCoolDown;
    private FrameCounter setMineTime;

    private PlayerAnimator playerAminator;


    public Player(){
        super();
        this.velocity = new Vector2D();
        this.boxCollider = new BoxCollider(30, 30);
        this.children.add(boxCollider);
        this.attack = false;
        this.playerAminator = new PlayerAnimator();
        this.renderer = playerAminator;
        this.attackCoolDown = new FrameCounter(20);
        this.setMineTime = new FrameCounter(90);

        this.setConstraints(new Constraints(
                        settings.getWindowInsets().top,
                        settings.getGamePlayHeight(),
                        settings.getWindowInsets().left,
                        settings.getGamePlayWidth())
        );
    }

    public static Player create(){
        Player player = new Player();
        return player;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        updatePhysics();
        playerAminator.update(this);

        if (constraints != null) {
            constraints.make(this.position);
        }
        if (InputManager.instance.xPressed && !InputManager.instance.downPressed) {
            if (!attack) {
                hitEnemy();
                hitRock();
                setAttack(true);
            }
        }else {
            unlockAttack();
        }
        if(InputManager.instance.xPressed && InputManager.instance.downPressed){
            if(setMineTime.run() && bomb != 0){
                bomb --;
                setMine();
                setMineTime.reset();
            }
        }

        hitFriendlyObject();
    }

    private void hitFriendlyObject() {
        FriendlyObject friendlyObject = Physics.collideWith(boxCollider,FriendlyObject.class);
        if(friendlyObject != null){
            if(friendlyObject.isAlly()){
                FriendlyObject.setAllynumber(FriendlyObject.getAllynumber() - 1);
                friendlyObject.setActive(false);
            }

        }
        BombObject bombObject = Physics.collideWith(boxCollider,BombObject.class);
        if(bombObject != null){
            if(bombObject.isBomb()){
                this.bomb ++;
                bombObject.setActive(false);
            }
        }
    }

    private void setMine() {
        Vector2D checkPosition = screenPosition.add(0, 1);
        Platform platform = Physics.collideWith(checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if(platform != null && platform.isBreakable()){
            platform.getHit();
            Explosion explosion = new Explosion();
            explosion.getPosition().set(platform.getPosition());
            GameObject.add(explosion);
        }
    }


    private void hitEnemy() {
        if(right){
            rangeAttack = 10;
        }else
            rangeAttack = -10;
        Vector2D checkPosition = screenPosition.add(rangeAttack, 0);
        Enemy enemy = Physics.collideWith(checkPosition, boxCollider.getWidth(), 0, Enemy.class);
        if (enemy != null) {
            enemy.getHit();
        }
    }

    public void unlockAttack() {
        if (attack){
            if (attackCoolDown.run()){
                setAttack(false);
            }
        }
    }

    private void updatePhysics() {
        velocity.y += GRAVITY;
        velocity.x = 0;
        jump();
        moveHorizontal();
        updateVerticalPhysics();
        updateHorizontalPhysics();
    }

    private void updateHorizontalPhysics() {
        Vector2D checkPositon = screenPosition.add(velocity.x, 0);
        Platform platform = Physics.collideWith(checkPositon, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (platform != null ){
            while (Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(),
                    boxCollider.getHeight(), Platform.class) == null){
                position.addUp(Math.signum(velocity.x), 0);
                screenPosition.addUp(Math.signum(velocity.x), 0);
            }
            velocity.x = 0;
        }
        this.position.x += velocity.x;
        this.screenPosition.x += velocity.x;
    }

    private void moveHorizontal() {
        if (InputManager.instance.leftPressed && !InputManager.instance.xPressed && !InputManager.instance.downPressed){
            velocity.x -= SPEED;
            Platform platform = Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
            if(platform != null && platform.isMoveable()){
//                platform.getScreenPosition().addUp(velocity.x,0);
//                platform.getPosition().addUp(velocity.x, 0);
                platform.getVelocity().x = this.getVelocity().x;
            }
            left = true;
            right = false;
        }
        if (InputManager.instance.rightPressed && !InputManager.instance.xPressed && !InputManager.instance.downPressed){
            velocity.x += SPEED;
            Platform platform = Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
            if(platform != null && platform.isMoveable()){
//                platform.getScreenPosition().addUp(velocity.x,0);
//                platform.getPosition().addUp(velocity.x, 0);
                platform.getVelocity().x = this.getVelocity().x;
            }
            left = false;
            right = true;
        }
    }

    private void jump() {
        if (InputManager.instance.cPressed) {
            if (Physics.collideWith(screenPosition.add(0, 1), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) != null) {
                velocity.y = -6;
            }
        }
    }

    private void updateVerticalPhysics() {
        Vector2D checkPositon = screenPosition.add(0, velocity.y);
        Platform platform = Physics.collideWith(checkPositon, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (platform != null){
            while (Physics.collideWith(screenPosition.add(0, Math.signum(velocity.y)), boxCollider.getWidth(), boxCollider.getHeight(),
                    Platform.class) == null){
                position.addUp(0, Math.signum(velocity.y));
                screenPosition.addUp(0, Math.signum(velocity.y));
            }
            velocity.y = 0;
        }
        this.position.y += velocity.y;
        this.screenPosition.y += velocity.y;
    }

    private void hitRock() {
        if(right){
            rangeAttack = 10;
        }else
            rangeAttack = -10;
        Vector2D checkPosition = screenPosition.add(rangeAttack, 0);
        Platform platform = Physics.collideWith(checkPosition, boxCollider.getWidth(), 0, Platform.class);
        if(platform != null && platform.isBreakable()){
            platform.getHit();
        }
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void getHit(){
        this.isActive = false;

        int HP = GameWindow.getPlayerHP();
        HP--;
        GameWindow.setPlayerHP(HP);

        if(HP == 0)
            SceneManager.changeScene(new GameOverScene());
        else {
            PlayerDeath playerDeath = new PlayerDeath();
            playerDeath.getPosition().set(this.getPosition());
            GameObject.add(playerDeath);
        }

    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isAttack() {
        return attack;
    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
