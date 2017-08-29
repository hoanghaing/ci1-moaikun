package moaimoai.allies;

import bases.Constraints;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import bases.renderers.ImageRenderer;
import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;
import moaimoai.players.Player;
import tklibs.SpriteUtils;


public class Ally extends GameObject implements PhysicsBody{
    private BoxCollider boxCollider;
    private static final int SPEED = 3;
    private Constraints constraints;
    private Vector2D velocity;


    public Ally(){
        super();
        boxCollider = new BoxCollider(20,20);
        this.children.add(boxCollider);
        renderer = new ImageRenderer(SpriteUtils.loadImage("assets/images/peoples/pink/1.png"));
        velocity = new Vector2D();
    }

    public void setConstraints(Constraints constraints){
        this.constraints = constraints;
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        velocity.set(0,0);
        moveHorizontal();
        moveVertical();
        TouchPlayer();
    }

    private void TouchPlayer() {
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if(player != null){

            this.isActive = false;
        }
    }


    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
    private void moveHorizontal() {
        float deltaX = velocity.x > 0 ? 1: -1;
        PhysicsBody body = Physics.bodyInRect(position.add(velocity.x, 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (body != null) {
            while(Physics.bodyInRect(position.add(deltaX, 0), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(deltaX, 0);
            }
            this.velocity.x = 0;
        }
    }

    private void moveVertical() {
        float deltaY = velocity.y > 0 ? 1: -1;
        PhysicsBody body = Physics.bodyInRect(position.add(0, velocity.y), boxCollider.getWidth(), boxCollider.getHeight(),
                Platform.class);
        if (body != null) {
            while(Physics.bodyInRect(position.add(0, deltaY), boxCollider.getWidth(), boxCollider.getHeight(), Platform.class) == null) {
                position.addUp(0, deltaY);
            }
            this.velocity.y = 0;
        }
    }
}
