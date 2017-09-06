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
//    Player player = new Player();
    Door door = new Door(1);
    Settings settings = Settings.instance;
    private static int stageLevel = 1;
    @Override
    public void init() {
        addBackground(stageLevel);
        addPlatform(stageLevel);
//        addDoor();
//        addPlayer();
//        addAlly();
//        addEnemy();
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

//
//    private void addPlayer() {
//        player.setConstraints(new Constraints(
//                settings.getWindowInsets().top,
//                settings.getGamePlayHeight(),
//                settings.getWindowInsets().left,
//                settings.getGamePlayWidth())
//        );
//        player.getPosition().set(
//                50,350
//        );
//        GameObject.add(player);
//    }
    private void addBackground(int backgroundType) {
        GameObject.add(new Background());
    }
    private void addPlatform(int level) {
        String stage = "assets/maps/jsonfile/stage"+level+".json";
        if(stage != null) {
            Map map = Map.load(stage);
            map.generate();
        }
    }
    public static int getStageLevel() {
        return stageLevel;
    }

    public static void setStageLevel(int stageLevel) {
        testScene.stageLevel = stageLevel;
    }
}
