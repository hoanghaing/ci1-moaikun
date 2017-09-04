package bases.platforms;


import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
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
    private int type;


    public Platform() {
        super();
        this.boxCollider = new BoxCollider(38, 32);
        this.children.add(boxCollider);
    }
    public static Platform create(int platformType) {

        Platform platform = new Platform();
        switch (platformType){
            case 1:
                platform.renderer = ImageRenderer.create("assets/images/standinggrounds/green/grass1.png");
                break;
            case 2:
                platform.renderer = ImageRenderer.create("assets/images/rocks/weakrock/greensky.png");
                platform.velocity = new Vector2D();
                platform.hasGravity = true;
                platform.breakable = true;
                platform.getPlatformType(platformType);
                break;
            case 3:
                platform.renderer = ImageRenderer.create("assets/images/rocks/unbreakrock/orange.png");
                break;
            case 5:
                platform.renderer = ImageRenderer.create("assets/images/rocks/rollingrock/yellow.png");
                platform.velocity = new Vector2D();
                platform.hasGravity = true;
                platform.moveable = true;
                break;
        }
        return platform;
    }

    private void getPlatformType(int platformType) {
        this.type = platformType;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if(hasGravity) {
            velocity.y += GRAVITY;

            updateVericalPhysics();
            updateHorizontalPhysics();
            if(!moveable) {
                hitPlayer();
            }
        }
        if(constraints != null){
            constraints.make(position);
        }
    }


    private void updateHorizontalPhysics() {
        Vector2D checkPositon = screenPosition.add(velocity.x, 0);
        Platform platform = Physics.collideWith(screenPosition,checkPositon, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
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
        Platform platform =  Physics.collideWith(screenPosition,checkPosition,this.boxCollider.getWidth(),this.boxCollider.getHeight(),Platform.class);
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


    private void hitPlayer() {
        Vector2D checkPosition = screenPosition.add(0, 2);
        Player player = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Player.class);
        if (player != null) {
            player.getHit();
            PlayerDeath playerDeath = new PlayerDeath();
            playerDeath.getPosition().set(player.getPosition());
            GameObject.add(playerDeath);
        }
    }


    public void setConstraints(Constraints constraints){
        this.constraints = constraints;
    }

    public void getHit() {
        this.isActive = false;
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