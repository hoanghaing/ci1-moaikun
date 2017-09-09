package bases;

import bases.actions.Action;
import bases.physics.Physics;
import bases.physics.PhysicsBody;
import bases.platforms.Platform;
import bases.pools.GameObjectPool;
import bases.renderers.Renderer;
import moaimoai.allies.BombObject;
import moaimoai.allies.FriendlyObject;
import moaimoai.door.Door;
import moaimoai.enemies.EnemyMouse;
import moaimoai.enemies.EnemyRabit;
import moaimoai.players.Player;
import moaimoai.players.PlayerDeath;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by huynq on 8/9/17.
 */
public class GameObject {
    protected Vector2D position;
    protected Vector2D screenPosition;

    protected Renderer renderer;

    protected ArrayList<GameObject> children;
    protected ArrayList<Action> actions;
    protected ArrayList<Action> newActions;
    protected boolean isActive;
    protected boolean isRenewing;

    private static Vector<GameObject> gameObjects = new Vector<>();
    private static Vector<GameObject> newGameObjects = new Vector<>();

    public static void runAll() {
        for (GameObject gameObject : gameObjects) {
                if (gameObject.isActive)
                    gameObject.run(new Vector2D(0, 0));
            }

        for (GameObject newGameObject : newGameObjects) {
            if (newGameObject instanceof PhysicsBody) {
                Physics.add((PhysicsBody) newGameObject);
            }
        }
        gameObjects.addAll(newGameObjects);
        newGameObjects.clear();
    }

    public static void renderAll(Graphics2D g2d) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.isActive && !gameObject.isRenewing)
                gameObject.render(g2d);
        }
    }

    public static void clearAll() {
        gameObjects.clear();
        newGameObjects.clear();
        Physics.clearAll();
        GameObjectPool.clearAll();
    }

    public static void add(GameObject gameObject) {
        newGameObjects.add(gameObject);
    }

    public GameObject() {
        actions = new ArrayList<>();
        newActions = new ArrayList<>();

        children = new ArrayList<>();
        position = new Vector2D();
        screenPosition = new Vector2D();
        isActive = true;
    }

    public static GameObject create(int gameObjectType) {
        /**
         * group 1: player (1xx)
         * group 2: enemies (2xx)
         * group 3: platform (3xx)
         */
        switch (gameObjectType) {
            case 1 :
                return (GameObject) Platform.create(gameObjectType);
            case 2 :
                return (GameObject) Platform.create(gameObjectType);
            case 3 :
                return (GameObject) Platform.create(gameObjectType);
            case 4 :
                return (GameObject) Platform.create(gameObjectType);
            case 5:
                return (GameObject) Platform.create(gameObjectType);
            case 6:
                return (GameObject) Platform.create(gameObjectType);
            case 7:
                return (GameObject) Platform.create(gameObjectType);
            case 8:
                return (GameObject) Platform.create(gameObjectType);
            case 9:
                return (GameObject) Platform.create(gameObjectType);
            case 10:
                return (GameObject) Platform.create(gameObjectType);
            case 11:
                return (GameObject) Platform.create(gameObjectType);
            case 12:
                return (GameObject) Platform.create(gameObjectType);
            case 13:
                return (GameObject) Platform.create(gameObjectType);
            case 14:
                return (GameObject) Platform.create(gameObjectType);
            case 15:
                return (GameObject) Platform.create(gameObjectType);
            case 16:
                return (GameObject) Platform.create(gameObjectType);
            case 17 : {
                //return platform
                return (GameObject) Platform.create(gameObjectType);
            }
            case 18 :// Player
                return (GameObject) Player.create();
            case 19 ://Ally
                int allynumber = FriendlyObject.getAllynumber();
                allynumber++;
                FriendlyObject.setAllynumber(allynumber);
                return (GameObject) FriendlyObject.create();
            case 20 ://Door
                return (GameObject) Door.create();
            case 21 :
//                return (GameObject) Door.create();
                return null;
            case 22:
                return null;
//                return (GameObject) Door.create();
            case 23:
                return null;
//                return (GameObject) Door.create();
            case 24:
                return (GameObject) BombObject.create();
            case 25:
                return (GameObject) EnemyMouse.create();

            case 27:
                return (GameObject) EnemyRabit.create();
            case 28:
                return (GameObject) EnemyRabit.create();
            case 30 : {
                //return player
                break;
            }
        }
        return null;
    }

    public void run(Vector2D parentPosition) {
        screenPosition = parentPosition.add(position);
        isRenewing = false;
        for (GameObject child: children) {
            if (child.isActive)
                child.run(screenPosition);
        }
    }

    public void render(Graphics2D g2d) {
        if (renderer != null) {
            renderer.render(g2d, screenPosition);
        }

        for (GameObject child: children) {
            if (child.isActive)
                child.render(g2d);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getScreenPosition() {
        return screenPosition;
    }

    public void setPosition(Vector2D position) {
        if (position != null)
            this.position = position;
    }

    public void reset() {
        this.isActive = true;
        this.isRenewing = true;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public GameObject setRenderer(Renderer renderer) {
        if (renderer != null)
            this.renderer = renderer;
        return this;
    }

    public static void runAllActions() {
        for (GameObject gameObject : gameObjects){
            if (gameObject.isActive) {
                gameObject.runActions();
            }
        }
    }

    private void runActions() {

        actions.removeIf(action -> action.run(this));

        actions.addAll(newActions);
        newActions.clear();
    }

    public void addAction(Action action){
        newActions.add(action);
    }
}
