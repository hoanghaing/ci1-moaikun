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

    public BombObject(){
        super();
        boxCollider = new BoxCollider(30,30);
        this.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/items/bombs/blue.png"));
        this.children.add(boxCollider);
    }

    public static BombObject create (){
        BombObject bombObject = new BombObject();
        return bombObject;
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
