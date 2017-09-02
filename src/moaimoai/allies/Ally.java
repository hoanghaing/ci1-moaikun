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
import moaimoai.players.Player;
import tklibs.SpriteUtils;


public class Ally extends GameObject implements PhysicsBody{
    private BoxCollider boxCollider;
    private Vector2D velocity;
    private static int ALLYNUMBER = 0;
        public Ally(){
        super();
        boxCollider = new BoxCollider(20,20);
        this.children.add(boxCollider);
        renderer = new Animation(5,false,false,
                SpriteUtils.loadImage("assets/images/peoples/pink/1.png"),
                SpriteUtils.loadImage("assets/images/peoples/pink/2.png"),
                SpriteUtils.loadImage("assets/images/peoples/pink/3.png"),
                SpriteUtils.loadImage("assets/images/peoples/pink/4.png"),
                SpriteUtils.loadImage("assets/images/peoples/pink/5.png")
        );
        velocity = new Vector2D();

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
        TouchPlayer();
        position.addUp(velocity);
    }

    private void TouchPlayer() {
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if(player != null){
            this.isActive = false;
            ALLYNUMBER --;
        }
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
}
