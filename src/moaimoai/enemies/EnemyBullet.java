package moaimoai.enemies;

import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.actions.*;
import bases.actions.Action;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import bases.renderers.ImageRenderer;
import moaimoai.players.Player;
import moaimoai.settings.Settings;
import tklibs.SpriteUtils;

/**
 * Created by NguyenGiaThe on 9/4/2017.
 */
public class EnemyBullet extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private Vector2D velocity;
    private Constraints constraints;


    public EnemyBullet(){
        super();
        this.constraints = new Constraints(
                        Settings.instance.getWindowInsets().top,
                        Settings.instance.getGamePlayHeight(),
                        Settings.instance.getWindowInsets().left ,
                        Settings.instance.getGamePlayWidth());
        this.boxCollider = new BoxCollider(5, 5);
        this.children.add(boxCollider);
        this.velocity = new Vector2D();
        this.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/explosion/enemy explosion/expl_01_0002.png"));
    }

    public void configActions(Vector2D velocity){
        Action action = new Action() {
            @Override
            public boolean run(GameObject owner) {
                EnemyBullet enemyAttack = (EnemyBullet) owner;
                enemyAttack.velocity.set(velocity);
                return true;
            }

            @Override
            public void reset() {

            }
        };

        this.addAction(new SequenceAction(new WaitAction(20), action));
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        this.position.addUp(velocity);
        hitPlayer();
        hitRock();
        if (constraints != null){
            constraints.make(position);
        }
    }

    private void hitPlayer() {
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if (player != null){
            player.getHit();
        }
    }

    private void hitRock(){
        Platform platform = Physics.collideWith(this.boxCollider , Platform.class);
        if (platform != null){
            platform.explosion();
            this.isActive = false;
        }
    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
