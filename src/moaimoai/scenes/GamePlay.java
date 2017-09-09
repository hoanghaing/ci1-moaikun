package moaimoai.scenes;

import bases.GameObject;
import bases.maps.Map;
import moaimoai.door.Door;
import moaimoai.settings.Settings;


public class GamePlay extends Scene {
    Door door = new Door(1);
    Settings settings = Settings.instance;
    private static int stageLevel = 4;
    @Override
    public void init() {
        addBackground(stageLevel);
        addPlatform(stageLevel);
    }

    private void addBackground(int backgroundType)
    {
        GameObject.add(new Background(backgroundType));
    }
//    private void addPlatform(int level) {
//        String stage = "assets/maps/jsonfile/stage"+level+".json";
//        if(stage != null) {
//            Map map = Map.load(stage);
//            map.generate();
//        }
//    }
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
        GamePlay.stageLevel = stageLevel;
    }
}
