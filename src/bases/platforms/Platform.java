package bases.platforms;


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
    private Vector2D velocity;
    private int type;
    private static final float GRAVITY = 0.1f;
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
                break;
            case 3:
                platform.renderer = ImageRenderer.create("assets/images/rocks/unbreakrock/orange.png");
                break;
            case 5:
                platform.renderer = ImageRenderer.create("assets/images/rocks/rollingrock/yellow.png");
                platform.velocity = new Vector2D();
                break;
        }
        return platform;
    }


    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if ( type == 2) {
            velocity.y += GRAVITY;
            updateVerticalPhysics();
            hitPlayer();
        }
        if(type == 3){
            velocity.y += GRAVITY;
            updateVerticalPhysics();
        }
    }
    private void hitPlayer() {
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if (player != null) {
            player.getHit();
            PlayerDeath playerDeath = new PlayerDeath();
            playerDeath.getPosition().set(player.getPosition());
            GameObject.add(playerDeath);
        }
    }
    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void updateVerticalPhysics() {
        Vector2D checkPosition = screenPosition.add(0, velocity.y);
        Platform platform = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (platform != null) {
            while (Physics.collideWith(screenPosition, screenPosition.add(0,Math.signum(velocity.y)), boxCollider.getWidth(),boxCollider.getHeight(), Platform.class) == null){
                position.addUp(0,Math.signum(velocity.y));
                screenPosition.addUp(0,Math.signum(velocity.y));
            }
            velocity.y = 0;
        }
        this.position.y += velocity.y;
        this.screenPosition.y += velocity.y;
    }



    public void getHit() {
        this.isActive = false;
    }


    public int getType() {
        return type;
    }


}