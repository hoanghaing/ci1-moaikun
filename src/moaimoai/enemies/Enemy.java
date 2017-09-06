package moaimoai.enemies;

import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import moaimoai.allies.FriendlyObject;
import moaimoai.players.Player;

/**
 * Created by NguyenGiaThe on 9/1/2017.
 */
public class Enemy extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private Vector2D velocity;
    private Enemy1Aminator enemyAminator;
    private Constraints constraints;
    private final float SPEED = 1;
    private boolean right = true;
    private boolean left;

    public Enemy() {
        super();
        this.enemyAminator = new Enemy1Aminator();
        this.renderer = enemyAminator;
        this.boxCollider = new BoxCollider(32, 38);
        this.children.add(boxCollider);
        this.velocity = new Vector2D();
    }
    public static Enemy create(int enemyType) {
        Enemy enemy = new Enemy();
        switch (enemyType){

        }
        return enemy;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        updatePhysics();
        hitPlayer();
//        hitAlly();
        enemyAminator.update(this);
        if (constraints != null){
            constraints.make(position);
        }
    }

    private void updatePhysics() {
        velocity.y += 0.4;
        moveHorizontal();
        updateMoveHorizontal();
        updateVerticalPhysics();

    }

    private void updateMoveHorizontal() {
        Vector2D checkPositon = screenPosition.add(velocity.x, 0);
        Platform platform = Physics.collideWith(checkPositon, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (platform != null && right){
            while (Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(),
                    boxCollider.getHeight(), Platform.class) == null){
                position.addUp(Math.signum(velocity.x), 0);
                screenPosition.addUp(Math.signum(velocity.x), 0);
            }
            velocity.x = -SPEED;
            left = true;
            right = false;
        }

        else if (platform != null && left){
            while (Physics.collideWith(screenPosition.add(Math.signum(velocity.x), 0), boxCollider.getWidth(),
                    boxCollider.getHeight(), Platform.class) == null){
                position.addUp(Math.signum(velocity.x), 0);
                screenPosition.addUp(Math.signum(velocity.x), 0);
            }
            velocity.x = SPEED;
            left = false;
            right = true;
        }
        this.position.x += velocity.x;
        this.screenPosition.x += velocity.x;
    }

    private void updateVerticalPhysics() {
        Vector2D checkPositon = screenPosition.add(0, velocity.y);
        Platform platform = Physics.collideWith(checkPositon, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (platform != null) {
            while (Physics.collideWith(screenPosition.add(0, Math.signum(velocity.y)), boxCollider.getWidth(), boxCollider.getHeight(),
                    Platform.class) == null) {
                position.addUp(0, Math.signum(velocity.y));
                screenPosition.addUp(0, Math.signum(velocity.y));
            }
            velocity.y = 0;
        }
        this.position.y += velocity.y;
        this.screenPosition.y += velocity.y;
    }

    private void moveHorizontal() {
        if(right ) {
            velocity.x = SPEED;
        }
        if (left ){
            velocity.x = -SPEED;
        }
    }


    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void getHit() {
        this.isActive = false;
        Explosion explosion = new Explosion();
        explosion.getPosition().set(this.position);
        GameObject.add(explosion);
    }

    private void hitPlayer(){
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if (player != null){
            player.getHit();
        }
    }

//    private void hitAlly(){
//        FriendlyObject ally = Physics.collideWith(this.boxCollider, FriendlyObject.class);
//        if (ally != null){
//            ally.getHit();
//        }
//    }

    public Vector2D getVelocity() {
        return velocity;
    }
}
