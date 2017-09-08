package moaimoai.enemies;

import bases.GameObject;
import bases.Vector2D;
import bases.actions.Action;
import bases.actions.ActionRepeatForever;
import bases.actions.SequenceAction;
import bases.actions.WaitAction;
import bases.physics.BoxCollider;

/**
 * Created by NguyenGiaThe on 9/6/2017.
 */
public class EnemyMouse extends EnemyRabit {
    private EnemyMouseAnimator enemy2Animator;
    private BoxCollider boxCollider;

    public EnemyMouse() {
        super();
        this.enemy2Animator = new EnemyMouseAnimator();
        this.renderer = enemy2Animator;
        this.boxCollider = new BoxCollider(28, 32);
        this.children.add(boxCollider);
        confixAction(this);
    }

    public static EnemyMouse create(){
        EnemyMouse enemyMouse = new EnemyMouse();
        return enemyMouse;
    }

    private void confixAction(EnemyRabit enemy){
        Action shoot = new Action() {
            @Override
            public boolean run(GameObject owner) {

                EnemyBullet enemyBullet = new EnemyBullet();
                enemyBullet.getPosition().set(owner.getPosition().add(5,0));
                GameObject.add(enemyBullet);
                if (enemy.getVelocity().x > 0) {
                    enemyBullet.getPosition().set(owner.getPosition().add(10,0));
                    enemyBullet.configActions(new Vector2D().add(3, 0));
                }
                if (enemy.getVelocity().x < 0){
                    enemyBullet.getPosition().set(owner.getPosition().add(-10,0));
                    enemyBullet.configActions(new Vector2D().add(-3, 0));
                }
                return true;
            }

            @Override
            public void reset() {

            }
        };

        Action action = new SequenceAction(
                new WaitAction(120),
                shoot
        );
        this.addAction(new ActionRepeatForever(action));
    }


    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        enemy2Animator.update(this);
    }

    public void getHit(){
        this.isActive = false;
        Explosion explosion = new Explosion();
        explosion.getPosition().set(this.position);
        GameObject.add(explosion);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return super.getBoxCollider();
    }
}
