package bases.platforms;


import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
import bases.renderers.Renderer;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/3/17.
 */
public class Platform extends GameObject implements PhysicsBody{

    private BoxCollider boxCollider;
    private Vector2D velocity;
    private int type;
    private final float GRAVITY = 0.9f;
    private boolean yes;

    public Platform(int type){
        this.type = type;
        switch (type){
            case 1:
                this.renderer = ImageRenderer.create("assets/images/standinggrounds/green/biggrass.png");
                this.boxCollider = new BoxCollider(608, 32);
                this.children.add(boxCollider);

                break;
            case 2:
                this.renderer = ImageRenderer.create("assets/images/rocks/weakrock/greensky.png");
                this.boxCollider = new BoxCollider(40, 32);
                this.children.add(boxCollider);
                this.velocity = new Vector2D();
                yes = true;
                break;
        }
    }


    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if (isYes()) {
            updatePhysics();
        }
    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public boolean isYes() {
        return yes;
    }

    private void updatePhysics(){
        velocity.y += GRAVITY;

        updateVerticalPhysics();
        // Check future colision
    }



    private void updateVerticalPhysics() {
        // TODO: hit celling
        Vector2D checkPosition = screenPosition.add(0, velocity.y);
        Platform platform = Physics.collideWith(screenPosition, checkPosition, boxCollider.getWidth(), boxCollider.getHeight(), Platform.class);
        if (platform != null) {
//            while (Physics.collideWith(screenPosition, screenPosition.add(0,Math.signum(velocity.y)), boxCollider.getWidth(),boxCollider.getHeight(), Platform.class) == null){
//                position.addUp(0,Math.signum(velocity.y));
//                screenPosition.addUp(0,Math.signum(velocity.y));
//            }
            velocity.y = 0;
        }
        this.position.y += velocity.y;
        this.screenPosition.y += velocity.y;
    }

}