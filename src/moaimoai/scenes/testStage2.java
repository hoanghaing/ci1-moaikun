package moaimoai.scenes;

import bases.Constraints;
import bases.GameObject;
import bases.maps.Map;
import moaimoai.door.Door;
import moaimoai.players.Player;
import moaimoai.settings.Settings;

public class testStage2 extends Scene{
    Player player = new Player();
    Door door = new Door(1);
    Settings settings = Settings.instance;
    @Override
    public void init() {
        addBackground();
        addPlatform();
        addDoor();
        addPlayer();

    }

    private void addPlayer() {
        player.setConstraints(new Constraints(
                settings.getWindowInsets().top,
                settings.getGamePlayHeight(),
                settings.getWindowInsets().left,
                settings.getGamePlayWidth())
        );
        player.getPosition().set(
                513,368
        );
        GameObject.add(player);
    }

    private void addPlatform() {
        Map map = Map.load("assets/maps/jsonfile/stage2.json");
        map.generate();
    }

    private void addDoor() {
        door.getPosition().set(76,160);
        GameObject.add(door);
    }

    private void addBackground() {
        GameObject.add(new Background());
    }
}
