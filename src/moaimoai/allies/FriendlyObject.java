package moaimoai.allies;

import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import bases.renderers.Animation;
import bases.renderers.ImageRenderer;
import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;
import moaimoai.enemies.Enemy;
import moaimoai.enemies.Explosion;
import moaimoai.players.Player;
import tklibs.SpriteUtils;


public class FriendlyObject extends GameObject implements PhysicsBody{
    private BoxCollider boxCollider;
    private Vector2D velocity;
    private static int ALLYNUMBER = 0;
    private boolean isBomb;
    private boolean isAlly;

    public FriendlyObject(){
        super();
        boxCollider = new BoxCollider(20,20);
        this.children.add(boxCollider);
        velocity = new Vector2D();
    }

    public static FriendlyObject creat(int friendlyObjectType) {
        FriendlyObject friendlyObject
                = new FriendlyObject();
        switch (friendlyObjectType) {
            case 1:
                friendlyObject.renderer = new Animation(5, false, false,
                        SpriteUtils.loadImage("assets/images/peoples/pink/1.png"),
                        SpriteUtils.loadImage("assets/images/peoples/pink/2.png"),
                        SpriteUtils.loadImage("assets/images/peoples/pink/3.png"),
                        SpriteUtils.loadImage("assets/images/peoples/pink/4.png"),
                        SpriteUtils.loadImage("assets/images/peoples/pink/5.png")
                );
                friendlyObject.isAlly = true;
                break;
            case 2:
                friendlyObject.renderer = ImageRenderer.create("assets/images/items/bombs/blue.png");
                friendlyObject.isBomb = true;
                break;
        }
        return friendlyObject;
    }

    public static int getAllynumber() {
        return ALLYNUMBER;
    }

    public static void setAllynumber(int allynumber) {
        ALLYNUMBER = allynumber;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        velocity.y += 0.5;
        moveVertical();
        position.addUp(velocity);
    }




    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
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

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isAlly() {
        return isAlly;
    }
}
