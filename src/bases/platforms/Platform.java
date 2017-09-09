package bases.platforms;


import bases.Constraints;
import bases.FrameCounter;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
import moaimoai.allies.BombObject;
import moaimoai.allies.FriendlyObject;
import moaimoai.enemies.EnemyMouse;
import moaimoai.enemies.EnemyRabit;
import moaimoai.players.Player;
import moaimoai.settings.Settings;
import tklibs.AudioUtils;

import javax.sound.sampled.Clip;


public class Platform extends GameObject implements PhysicsBody{

    private BoxCollider boxCollider;
    private Constraints constraints;
    private Vector2D velocity;
    private final float GRAVITY = 0.6f;
    private boolean hasGravity;
    private boolean breakable;
    private boolean moveable;
    private boolean killPlayer;
    private int type;

    private FrameCounter runingTime;
    private FrameCounter standOnTrap;
    private FrameCounter waitTime;

    private boolean moving;
    private boolean stopable;
    private boolean trap;
    private boolean blowing;

    private Clip explosionMp3;


    public Platform() {
        super();
        this.explosionMp3 = AudioUtils.loadSound("assets/music/sfx/Concrete-break (mp3cut.net).wav");
        this.boxCollider = new BoxCollider(36, 32);
        this.children.add(boxCollider);
    }
    public static Platform create(int platformType) {

        Platform platform = new Platform();
        switch (platformType){
            case 1: //CỎ XANH
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/green/grass1.png");
                break;
            case 2: // ĐÁ RƠI XANH
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/greensky.png");
                platform.velocity = new Vector2D();
                platform.hasGravity = true;
                platform.breakable = true;
                platform.waitTime = new FrameCounter(60);
                break;
            case 3: // ĐÁ CỨNG DA CAM
                platform.renderer = ImageRenderer.create("assets/images/rocks/unbreakrock/orange.png");
                break;
            case 4: // ĐÁ LĂN vàng cam
                platform.renderer = ImageRenderer.create("assets/images/rocks/rollingrock/yellow.png");
                platform.velocity = new Vector2D();
                platform.hasGravity = true;
                platform.moveable = true;
                platform.boxCollider = new BoxCollider(38,30);
                platform.children.add(platform.boxCollider);
                platform.runingTime = new FrameCounter(18);
                platform.setConstraints(new Constraints(
                        Settings.instance.getWindowInsets().top,
                        Settings.instance.getGamePlayHeight(),
                        Settings.instance.getWindowInsets().left,
                        Settings.instance.getGamePlayWidth())
                );
                break;

            case 5: // ĐÁ MỀM VÀNG
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/orange.png");
                break;
            case 6: //CỌC SẮT
                platform.renderer = ImageRenderer.create("assets/images/deadgrounds/cocsat/coc2.png");
                platform.velocity = new Vector2D();
                platform.boxCollider = new BoxCollider(34,32);
                platform.children.add(platform.boxCollider);
                platform.killPlayer = true;
                break;
            case 7: //MÂY HỒNG 1
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/pink/grass111.png");
                break;

            case 8: // MÂY HỒNG 2
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/pink/grass11.png");
                break;
            case 9: // MÂY HỒNG 3
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/pink/grass1.png");
                break;
            case 10: //ĐINH GỈ
                platform.renderer = ImageRenderer.create("assets/images/deadgrounds/dinhgi/white.png");
                platform.trap = true;
                platform.velocity = new Vector2D();
                platform.standOnTrap = new FrameCounter(25);
                break;
            case 11: // ĐÁ MỀM, XÁM TRẮNG
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/gray.png");
                platform.waitTime = new FrameCounter(60);
                platform.breakable = true;
                break;
            case 12: //ĐÁ RƠI VÀNG CAM
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/orangesky.png");
                break;
            case 13: // ĐÁ ĐỠ NÂU
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/brown/1.png");
                break;
            case 14: // ĐÁ CỨNG HỒNG
                platform.renderer = ImageRenderer.create("assets/images/rocks/unbreakrock/pink.png");
                break;
            case 15: //ĐÁ LĂN ĐỎ
                platform.renderer = ImageRenderer.create("assets/images/rocks/rollingrock/pink.png");
                break;
            case 16: // ĐÁ CỨNG XANH
                platform.renderer = ImageRenderer.create("assets/images/rocks/unbreakrock/blue.png");
                break;
            case 17: //ĐÁ ĐỠ TRẮNG
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/white/1.png");
                platform.boxCollider = new BoxCollider(36,30);
                platform.children.add(platform.boxCollider);
                break;
//            case 18:
//                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/white/1.png");
//                break;
            default:
                break;
        }
        return platform;
    }


    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if(hasGravity) {
            velocity.y += GRAVITY;
                updateVericalPhysics();
                updateHorizontalPhysics();
            if(!moveable) {
                hitPlayerAndEnemy(0,2);
            }
        }
        if(killPlayer){
            hitPlayerAndEnemy(0,-2);
        }
        if(blowing){
                if(waitTime.run()){
                    this.getHit();
//                    AudioUtils.play();
                }
        }
        if(constraints != null){
            constraints.make(position);
        }
    }


    private void updateHorizontalPhysics() {
        Vector2D checkPositon = screenPosition.add(velocity.x, 0);
        hitPlatform(checkPositon);
        hitAllyHorizontal(checkPositon);
        hitBomb();
        if(moving) {
            this.position.x += velocity.x;
            this.screenPosition.x += velocity.x;
            if(this.stopable && runingTime.run()){
                moving = false;
                runingTime.reset();
            }
        }
    }


    private void hitPlatform(Vector2D checkPositon) {
        Platform platform = Physics.collideWith(screenPosition,checkPositon, boxCollider.getWidth(), boxCollider.getHeight() - 2 , Platform.class);
        if (platform != null ){
            while (Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(),
                    boxCollider.getHeight() - 2 , Platform.class) == null){
                position.addUp(Math.signum(velocity.x), 0);
                screenPosition.addUp(Math.signum(velocity.x), 0);
            }
            velocity.x = 0;
            this.killPlayer = false;
        }
    }


    private void hitAllyHorizontal(Vector2D checkPositon) {
        FriendlyObject friendlyObject = Physics.collideWith(checkPositon, boxCollider.getWidth(), boxCollider.getHeight() - 2 , FriendlyObject.class);
        if (friendlyObject != null ){
            while (Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(),
                    boxCollider.getHeight() - 2 , Platform.class) == null){
                position.addUp(Math.signum(velocity.x), 0);
                screenPosition.addUp(Math.signum(velocity.x), 0);
            }
            velocity.x = 0;
            this.killPlayer = false;
        }
    }

    private void updateVericalPhysics() {
        if(!isTrap()){
            Vector2D checkPosition = screenPosition.add(0,velocity.y);
            Platform platform =  Physics.collideWith(screenPosition,checkPosition,this.boxCollider.getWidth() - 4 ,this.boxCollider.getHeight(),Platform.class);
            if(platform != null ){
                while (Physics.collideWith(screenPosition,this.screenPosition.add(0,1),this.boxCollider.getWidth() - 4 ,this.boxCollider.getHeight(),Platform.class) == null){
                    position.addUp(0,1);
                    screenPosition.addUp(0,1);
                }
                velocity.y = 0;
            }
        }
        this.position.y += velocity.y;
        this.screenPosition.y += velocity.y;
    }

    private void hitBomb(){
       BombObject bombObject = Physics.collideWith(boxCollider,BombObject.class);
       if(bombObject != null){
           bombObject.setActive(false);
       }
    }




    private void hitPlayerAndEnemy(int dx, int dy) {
        Vector2D checkPosition = screenPosition.add(dx, dy);
        Player player = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Player.class);
        if (player != null) {
            player.getHit();
        }
        EnemyRabit enemy = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), EnemyRabit.class);
        if (enemy != null){
            enemy.getHit();
        }
        EnemyMouse enemyMouse = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), EnemyMouse.class);
        if (enemyMouse != null){
            enemyMouse.getHit();
        }
    }


    public void setConstraints(Constraints constraints){
        this.constraints = constraints;
    }

    public void getHit() {
        this.isActive = false;
        BrokenPlatform brokenPlatform = new BrokenPlatform();
        brokenPlatform.getPosition().set(this.getPosition());
        AudioUtils.play(explosionMp3);
        GameObject.add(brokenPlatform);
    }

    public void explosion() {
        BrokenPlatform brokenPlatform = new BrokenPlatform();
        brokenPlatform.getPosition().set(this.getPosition());
        GameObject.add(brokenPlatform);

    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }


    public boolean isBreakable() {
        return breakable;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public Constraints getConstraints() {
        return constraints;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isStopable() {
        return stopable;
    }

    public void setStopable(boolean stopable) {
        this.stopable = stopable;
    }

    public boolean isKillPlayer() {
        return killPlayer;
    }

    public void setKillPlayer(boolean killPlayer) {
        this.killPlayer = killPlayer;
    }

    public void setHasGravity(boolean hasGravity) {
        this.hasGravity = hasGravity;
    }

    public boolean isTrap() {
        return trap;
    }

    public FrameCounter getStandOnTrap() {
        return standOnTrap;
    }

    public boolean isBlowing() {
        return blowing;
    }

    public void setBlowing(boolean blowing) {
        this.blowing = blowing;
    }
}