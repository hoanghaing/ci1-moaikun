package bases.platforms;


import bases.GameObject;
import bases.physics.BoxCollider;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
import bases.renderers.Renderer;
import tklibs.SpriteUtils;

/**
 * Created by huynq on 8/3/17.
 */
public class Platform extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;

    public Platform() {
        super();
        this.renderer = new ImageRenderer(SpriteUtils.loadImage
                ("assets/images/standinggrounds/grass/green/grass8.png"));
        this.boxCollider = new BoxCollider(304, 32);
        this.children.add(boxCollider);
    }

    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
}
