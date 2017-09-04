package moaimoai.scenes;

import bases.Constraints;
import bases.GameObject;
import bases.maps.Map;
import moaimoai.allies.Ally;
import moaimoai.door.Door;
import moaimoai.enemies.Enemy;
import moaimoai.players.Player;
import moaimoai.settings.Settings;


public class testScene extends Scene {
    Player player = new Player();
    Ally ally1 = new Ally();
    Door door = new Door(1);
    Settings settings = Settings.instance;
    @Override
    public void init() {
        addBackground();
        addDoor();
        addPlatform();
        addPlayer();
        addAlly();
        addEnemy();
    }
    private void addDoor() {
        door.getPosition().set(513,176);
        GameObject.add(door);
    }

    private void addEnemy() {
        Enemy enemy = new Enemy();
        enemy.getPosition().set(250 , 200);
        enemy.setConstraints(new Constraints(
                settings.getWindowInsets().top,
                settings.getGamePlayHeight(),
                settings.getWindowInsets().left ,
                settings.getGamePlayWidth()));
        GameObject.add(enemy);
    }

    private void addAlly() {
        Ally ally2 = new Ally();
        Ally ally3 = new Ally();
        Ally ally4 = new Ally();
        Ally ally5 = new Ally();
        ally1.setAllynumber(5);
        ally1.getPosition().set(
                418, 192
        );
        ally2.getPosition().set(342,224);
        ally3.getPosition().set(266,256);
        ally4.getPosition().set(190,288);
        ally5.getPosition().set(114,320);
        GameObject.add(ally1);
        GameObject.add(ally2);
        GameObject.add(ally3);
        GameObject.add(ally4);
        GameObject.add(ally5);
    }
    private void addPlayer() {
        player.setConstraints(new Constraints(
                settings.getWindowInsets().top,
                settings.getGamePlayHeight(),
                settings.getWindowInsets().left,
                settings.getGamePlayWidth())
        );
        player.getPosition().set(
                50,350
        );
        GameObject.add(player);
    }
    private void addBackground() {
        GameObject.add(new Background());
    }
    private void addPlatform() {
        Map map = Map.load("assets/maps/jsonfile/stage1.json");
        map.generate();
    }
}
