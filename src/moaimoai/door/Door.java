package moaimoai.door;

import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
import moaimoai.allies.Ally;
import moaimoai.players.Player;

public class Door extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private static int doortype;
    public Door(int type){
        super();
        this.doortype = type;
        switch (type){
            case 1:{ //BLUE
                this.renderer = ImageRenderer.create("assets/images/doors/blue/door1.png");
                this.boxCollider = new BoxCollider(75, 64);
                this.children.add(boxCollider);
                break;
            }
            case 2:{ // ORANGE
                this.renderer = ImageRenderer.create("assets/images/doors/orange/door1.png");
                this.boxCollider = new BoxCollider(75, 64);
                this.children.add(boxCollider);
                break;
            }
            case 3:{ // PINK
                this.renderer = ImageRenderer.create("assets/images/doors/pink/door1.png");
                this.boxCollider = new BoxCollider(75, 64);
                this.children.add(boxCollider);
                break;
            }
            case 4:{ // WHITE
                this.renderer = ImageRenderer.create("assets/images/doors/white/door1.png");
                this.boxCollider = new BoxCollider(75, 64);
                this.children.add(boxCollider);
                break;
            }
        }
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        hitPlayer();
    }

    private void hitPlayer() {
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if(player != null && Ally.getAllynumber() == 0){
            player.getHit();
            DoorOpen doorOpen = new DoorOpen(doortype);
            doorOpen.getPosition().set(this.position);
            GameObject.add(doorOpen);
        }
    }
    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public static int getDoortype() {
        return doortype;
    }
}
