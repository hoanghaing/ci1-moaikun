package moaimoai.allies;

import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import bases.renderers.ImageRenderer;
import moaimoai.enemies.EnemyRabit;
import moaimoai.enemies.Explosion;
import tklibs.SpriteUtils;

public class BombObject extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private Vector2D velocity;

    public BombObject(){
        super();
        boxCollider = new BoxCollider(30,30);
        this.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/items/bombs/blue.png"));
        this.children.add(boxCollider);
        this.velocity = new Vector2D();
    }

    public static BombObject create (){
        BombObject bombObject = new BombObject();
        return bombObject;
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
        EnemyRabit enemy = Physics.collideWith(this.boxCollider, EnemyRabit.class);
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
