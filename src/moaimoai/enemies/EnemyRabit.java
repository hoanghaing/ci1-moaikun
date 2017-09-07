package moaimoai.enemies;

import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import moaimoai.players.Player;
import moaimoai.settings.Settings;

/**
 * Created by NguyenGiaThe on 9/1/2017.
 */
public class EnemyRabit extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private Vector2D velocity;
    private EnemyRabitAminator enemyAminator;
    private Constraints constraints;
    private final float SPEED = 0.7f;
    private boolean right = true;
    private boolean left;
    Settings settings = Settings.instance;

    public EnemyRabit() {
        super();
        this.enemyAminator = new EnemyRabitAminator();
        this.renderer = enemyAminator;
        this.boxCollider = new BoxCollider(32, 33);
        this.children.add(boxCollider);
        this.velocity = new Vector2D();
        this.constraints = new Constraints(
                settings.getWindowInsets().top,
                settings.getGamePlayHeight(),
                settings.getWindowInsets().left ,
                settings.getGamePlayWidth());
    }

    public static EnemyRabit create(){
        EnemyRabit enemy = new EnemyRabit();
        return enemy;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        updatePhysics();
        hitPlayer();

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
//        Ally ally = Physics.collideWith(this.boxCollider, Ally.class);
//        if (ally != null){
//            ally.getHit();
//        }
//    }

    public Vector2D getVelocity() {
        return velocity;
    }
}
