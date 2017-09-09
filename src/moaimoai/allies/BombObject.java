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
import tklibs.AudioUtils;
import tklibs.SpriteUtils;

import javax.sound.sampled.Clip;

public class BombObject extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private Clip bomb;

    public BombObject(){
        super();
        boxCollider = new BoxCollider(30,30);
        this.renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/items/bombs/blue.png"));
        this.children.add(boxCollider);
        this.bomb = AudioUtils.loadSound("assets/music/sfx/Explosion.wav");
    }

    public static BombObject create (){
        BombObject bombObject = new BombObject();
        return bombObject;
    }

    public Clip getBomb() {
        return bomb;
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
