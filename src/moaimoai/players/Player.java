package moaimoai.players;

import bases.Constraints;
import bases.FrameCounter;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import moaimoai.GameWindow;
import moaimoai.allies.FriendlyObject;
import moaimoai.inputs.InputManager;
import moaimoai.scenes.GameOverScene;
import moaimoai.scenes.GamePlay;
import moaimoai.scenes.SceneManager;
import moaimoai.settings.Settings;
import tklibs.AudioUtils;

import javax.sound.sampled.Clip;
import java.util.Set;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public class Player extends GameObject implements PhysicsBody {
    private final float SPEED = 2;
    private Vector2D velocity;
    private final float GRAVITY = 0.6f;
    private BoxCollider boxCollider;
    private Constraints constraints;
    private boolean attack;
    private boolean left = true;
    private boolean right;
    private int rangeAttack;
    private int bomb = 0;

    private Clip hitBall;
//    private Clip hitRock;
    private Clip jump;
    private Clip victory;
    private Clip hitAlly;
    private Clip hitBomb;
    private Clip push;

    private PlayerAtack playerAtack;
    private PlayerPushing playerPushing;
    private PlayerSetMine playerSetMine;
    private PlayerHitFriend playerHitFriend;


    private FrameCounter attackCoolDown;
    private FrameCounter setMineTime;
    private FrameCounter pushingTime;


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
        this.setMineTime = new FrameCounter(50);
        this.pushingTime = new FrameCounter(60);

        this.hitBall = AudioUtils.loadSound("assets/music/sfx/Concrete-break (mp3cut.net).wav");
//        this.hitRock = AudioUtils.loadSound("assets/music/sfx/Concrete-break (mp3cut.net).wav");
        this.jump = AudioUtils.loadSound("assets/music/sfx/Jump.wav");
        this.victory = AudioUtils.loadSound("assets/music/bgm/Epic Win Sound Effects (mp3cut.net).wav");
        this.hitAlly = AudioUtils.loadSound("assets/music/sfx/Giải cứu.wav");
        this.hitBomb = AudioUtils.loadSound("assets/music/sfx/powerup.wav");
        this.push = AudioUtils.loadSound("assets/music/sfx/item-collect.wav");


        this.playerAtack = new PlayerAtack();
        this.playerPushing = new PlayerPushing();
        this.playerSetMine = new PlayerSetMine();
        this.playerHitFriend = new PlayerHitFriend();
        this.setConstraints(new Constraints(
                Settings.instance.getWindowInsets().top,
                Settings.instance.getGamePlayHeight(),
                Settings.instance.getWindowInsets().left,
                Settings.instance.getGamePlayWidth())
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
        playerAminator.update(this);
        updatePhysics();
        atack();
        setMine();
        sucide();
        playerHitFriend.hitAlly(this);
        if (constraints != null) {
            constraints.make(this.position);
        }
        if(screenPosition.y > constraints.bottom){
            this.getHit();
        }
//        System.out.println(GameWindow.getPlayerHP());
    }

    private void setMine() {
        if(InputManager.instance.xPressed && InputManager.instance.downPressed){
            if(bomb > 0){
                playerSetMine.setMine(this);
            }
        }
    }

    private void atack() {
        if(Physics.collideWith(boxCollider, Platform.class) == null) {
            if (InputManager.instance.xPressed && !InputManager.instance.downPressed) {
                if (!attack) {
                    playerAtack.doAttack(this);
                    setAttack(true);
                }
            }else {
                unlockAttack();
            }
        }
    }

    private void sucide(){
        if (InputManager.instance.shiftPressed){
            PlayerDeath playerDeath = new PlayerDeath();
            playerDeath.getPosition().set(screenPosition);
            GameObject.add(playerDeath);
            this.isActive = false;
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
        if (InputManager.instance.leftPressed && !InputManager.instance.downPressed){
            velocity.x -= SPEED;
            left = true;
            right = false;
            playerPushing.pushLeft(this);

        }
        if (InputManager.instance.rightPressed  && !InputManager.instance.downPressed){
            velocity.x += SPEED;
            left = false;
            right = true;
            playerPushing.pushRight(this);
        }
    }

    private void jump() {
        if (InputManager.instance.cPressed) {
            if (Physics.collideWith(screenPosition.add(0, 1), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) != null) {
                velocity.y = -6.5f;
                AudioUtils.play(this.jump);
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


    public Vector2D getVelocity() {
        return velocity;
    }

    public void getHit(){
        this.isActive = false;
//        int HP = GameWindow.getPlayerHP();
//        HP--;
//        GameWindow.setPlayerHP(HP);
        PlayerDeath playerDeath = new PlayerDeath();
        playerDeath.getPosition().set(screenPosition);

        GameObject.add(playerDeath);
        FriendlyObject.setAllynumber(0);


    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

//    public boolean isAttack() {
//        return attack;
//    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public int getRangeAttack() {
        return rangeAttack;
    }

    public void setRangeAttack(int rangeAttack) {
        this.rangeAttack = rangeAttack;
    }

    public Clip getHitBall() {
        return hitBall;
    }

    public Clip getVictory() {
        return victory;
    }

    public Clip getHitAlly() {
        return hitAlly;
    }

    public Clip getHitBomb() {
        return hitBomb;
    }

    public Clip getPush() {
        return push;
    }

    public FrameCounter getPushingTime() {
        return pushingTime;
    }


    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        this.bomb = bomb;
    }

    public FrameCounter getSetMineTime() {
        return setMineTime;
    }

}
