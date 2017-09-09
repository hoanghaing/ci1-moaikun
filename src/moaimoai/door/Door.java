package moaimoai.door;

import bases.FrameCounter;
import bases.GameObject;
import bases.Vector2D;
import bases.physics.BoxCollider;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.renderers.ImageRenderer;
import moaimoai.GameWindow;
import moaimoai.allies.FriendlyObject;
import moaimoai.players.Player;
import moaimoai.scenes.GamePlay;
import moaimoai.scenes.SceneManager;

public class Door extends GameObject implements PhysicsBody {
    private BoxCollider boxCollider;
    private static int doortype;
    private boolean touchPlayer;

    private FrameCounter counter;
    public Door(int type){
        super();
        this.boxCollider = new BoxCollider(5, 5);
        this.children.add(boxCollider);
        this.counter = new FrameCounter(60);
        this.doortype = type;
        switch (type){
            case 1:{ //BLUE
                this.renderer = ImageRenderer.create("assets/images/doors/blue/door11.png");
//                this.boxCollider = new BoxCollider(75, 64);
//                this.children.add(boxCollider);
                break;
            }
            case 2:{ // ORANGE
                this.renderer = ImageRenderer.create("assets/images/doors/orange/door1.png");
//                this.boxCollider = new BoxCollider(75, 64);
//                this.children.add(boxCollider);
                break;
            }
            case 3:{ // PINK
                this.renderer = ImageRenderer.create("assets/images/doors/pink/door1.png");
//                this.boxCollider = new BoxCollider(75, 64);
//                this.children.add(boxCollider);
                break;
            }
            case 4:{ // WHITE
                this.renderer = ImageRenderer.create("assets/images/doors/white/door1.png");
//                this.boxCollider = new BoxCollider(75, 64);
//                this.children.add(boxCollider);
                break;
            }
        }
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        hitPlayer();
        if(touchPlayer){
            if(counter.run()){
                SceneManager.changeScene(new GamePlay());
            }
        }
    }

    private void hitPlayer() {
        Player player = Physics.collideWith(this.boxCollider, Player.class);
        if(player != null && FriendlyObject.getAllynumber() == 0){
            player.setActive(false);
            DoorOpen doorOpen = new DoorOpen(doortype);
            doorOpen.getPosition().set(this.position);
            GameObject.add(doorOpen);

            // TĂng lên 1 stage
            int stage = GamePlay.getStageLevel();
            stage++;
            GamePlay.setStageLevel(stage);

            // Cho thêm 1 mạng
            int HP = GameWindow.getPlayerHP();
            HP++;
            GameWindow.setPlayerHP(HP);
            touchPlayer = true;

            // Chuyển Scene
        }

    }
    @Override
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public static Object create() {
        Door door = new Door(1);
        return door;
    }
}
