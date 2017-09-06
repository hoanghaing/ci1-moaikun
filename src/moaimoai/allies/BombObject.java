package moaimoai.allies;

import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import moaimoai.enemies.Enemy;
import moaimoai.enemies.Explosion;

public class BombObject extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private Vector2D velocity;
    private static int BOMBNUMBER = 0;
    private boolean isBomb;

    public BombObject(){
        super();
        boxCollider = new BoxCollider(30,30);
//        this.allyAnimator = new AllyAnimator();
//        this.renderer = allyAnimator;
        this.children.add(boxCollider);
        this.velocity = new Vector2D();
        this.isBomb = true;
    }

    public static BombObject create (){
        BombObject bombObject = new BombObject();
        return bombObject;
    }


    public boolean isBomb() {
        return isBomb;
    }

    public static int getBOMBNUMBER() {

        return BOMBNUMBER;
    }

    public static void setBOMBNUMBER(int BOMBNUMBER) {
        BombObject.BOMBNUMBER = BOMBNUMBER;
    }


    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        velocity.y += 0.5;
        moveVertical();
        position.addUp(velocity);
    }

    private void moveVertical() {
        float deltaY = velocity.y > 0 ? 1: -1;
        PhysicsBody body = Physics.collideWith(screenPosition,position.add(0, velocity.y), boxCollider.getWidth(), boxCollider.getHeight(),
                Platform.class);
        if (body != null) {
            while(Physics.collideWith(screenPosition,position.add(0, deltaY), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(0, deltaY);
            }
            this.velocity.y = 0;
        }
    }

    public void getHit(){
        Enemy enemy = Physics.collideWith(this.boxCollider, Enemy.class);
        if (enemy != null){
            this.isActive = false;
            Explosion explosion = new Explosion();
            explosion.setPosition(this.position);
            GameObject.add(explosion);
        }
    }
    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
