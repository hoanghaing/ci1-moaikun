package moaimoai.scenes;

import bases.GameObject;
import bases.maps.Map;
import moaimoai.door.Door;
import moaimoai.inputs.InputManager;
import moaimoai.settings.Settings;


public class GamePlay extends Scene {
    Door door = new Door(1);
    private static int stageLevel = -5;
    @Override
    public void init() {
        GameObject.stop = false;
        addBackground(stageLevel);
        addPlatform(stageLevel);

        if (stageLevel < 1){
            Tutorial tut = new Tutorial(stageLevel);
            GameObject.add(tut);
        }

    }
    private void addBackground(int backgroundType)
    {
        GameObject.add(new Background(backgroundType));
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
        GamePlay.stageLevel = stageLevel;
    }
}
