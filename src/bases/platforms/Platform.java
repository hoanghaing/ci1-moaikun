package bases.platforms;


import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
import moaimoai.enemies.Enemy;
import moaimoai.players.Player;
import moaimoai.players.PlayerDeath;



public class Platform extends GameObject implements PhysicsBody{

    private BoxCollider boxCollider;
    private Constraints constraints;
    private Vector2D velocity;
    private final float GRAVITY = 0.2f;
    private boolean hasGravity;
    private boolean breakable;
    private boolean moveable;
    private boolean killPlayer;
    private int type;


    public Platform() {
        super();
        this.boxCollider = new BoxCollider(38, 32);
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
                break;
            case 3: // ĐÁ CỨNG DA CAM
                platform.renderer = ImageRenderer.create("assets/images/rocks/unbreakrock/orange.png");
                break;
            case 4: // ĐÁ LĂN vàng cam
                platform.renderer = ImageRenderer.create("assets/images/rocks/rollingrock/yellow.png");
                platform.velocity = new Vector2D();
                platform.hasGravity = true;
                platform.moveable = true;
                break;

            case 5: // ĐÁ MỀM VÀNG
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/orange.png");
                break;
            case 6: //CỌC SẮT
                platform.boxCollider = new BoxCollider(25,32);
                platform.renderer = ImageRenderer.create("assets/images/deadgrounds/cocsat/coc2.png");
                platform.velocity = new Vector2D();
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
                break;
            case 11: // ĐÁ MỀM, XÁM TRẮNG
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/gray.png");
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
                break;
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
        if(constraints != null){
            constraints.make(position);
        }
    }


    private void updateHorizontalPhysics() {
        Vector2D checkPositon = screenPosition.add(velocity.x, 0);
        Platform platform = Physics.collideWith(screenPosition,checkPositon, boxCollider.getWidth(), boxCollider.getHeight() / 2, Platform.class);
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
        velocity.x = 0;
    }

    private void updateVericalPhysics() {
        Vector2D checkPosition = screenPosition.add(0,velocity.y);
        Platform platform =  Physics.collideWith(screenPosition,checkPosition,this.boxCollider.getWidth() / 2,this.boxCollider.getHeight(),Platform.class);
        if(platform != null){
            while (Physics.collideWith(screenPosition,this.screenPosition.add(0,1),this.boxCollider.getWidth(),this.boxCollider.getHeight(),Platform.class) == null){
                position.addUp(0,1);
                screenPosition.addUp(0,1);
            }
            velocity.y = 0;
        }
        this.position.y += velocity.y;
        this.screenPosition.y += velocity.y;
    }


    private void hitPlayerAndEnemy(int dx, int dy) {
        Vector2D checkPosition = screenPosition.add(dx, dy);
        Player player = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Player.class);
        if (player != null) {
            player.getHit();
        }
        Enemy enemy = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Enemy.class);
        if (enemy != null){
            enemy.getHit();
        }
    }


    public void setConstraints(Constraints constraints){
        this.constraints = constraints;
    }

    public void getHit() {
        this.isActive = false;
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

}