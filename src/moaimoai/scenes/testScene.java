package moaimoai.scenes;

import bases.Constraints;
import bases.GameObject;
import bases.maps.Map;
import moaimoai.allies.FriendlyObject;
import moaimoai.door.Door;
import moaimoai.enemies.Enemy;
import moaimoai.players.Player;
import moaimoai.settings.Settings;


public class testScene extends Scene {
    Player player = new Player();
    Door door = new Door(1);
    Settings settings = Settings.instance;
    @Override
    public void init() {
        addBackground();
        addPlatform();
        addDoor();

        addPlayer();
        addAlly();
        addEnemy();
    }
    private void addDoor() {
        door.getPosition().set(532,192);
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
        FriendlyObject ally1 = FriendlyObject.creat(1);
        FriendlyObject ally2 = FriendlyObject.creat(1);
        FriendlyObject ally3 = FriendlyObject.creat(1);
        FriendlyObject ally4 = FriendlyObject.creat(1);
        FriendlyObject ally5 = FriendlyObject.creat(1);
        ally1.setAllynumber(5);
        ally1.getPosition().set(
                437, 208
        );
        ally2.getPosition().set(361,240);
        ally3.getPosition().set(285,272);
        ally4.getPosition().set(209,304);
        ally5.getPosition().set(133,336);
        GameObject.add(ally1);
        GameObject.add(ally2);
        GameObject.add(ally3);
        GameObject.add(ally4);
        GameObject.add(ally5);
        FriendlyObject bomb1 = FriendlyObject.creat(2);
        bomb1.getPosition().set(100, 350);
        GameObject.add(bomb1);
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
