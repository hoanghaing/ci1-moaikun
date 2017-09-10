package moaimoai.scenes;

import bases.GameObject;
import bases.maps.Map;
import bases.pools.GameObjectPool;
import bases.renderers.TextRenderer;
import moaimoai.GameWindow;
import moaimoai.audio.AudioManager;
import moaimoai.door.Door;
import moaimoai.inputs.InputManager;
import moaimoai.players.Player;
import moaimoai.settings.Settings;
import moaimoai.text.TextObject;
import tklibs.AudioUtils;


public class GamePlay extends Scene {
    Door door = new Door(1);
    private static int stageLevel = -3;
    @Override
    public void init() {
        GameObject.stop = false;
        addBackground(stageLevel);
        addPlatform(stageLevel);
        addMusic(stageLevel);
        if (stageLevel < 1){
            Tutorial tut = new Tutorial(stageLevel);
            GameObject.add(tut);
        }
        Player.bomb = 0;
    }


    private void addMusic(int level){
        if (level <= 13){
            AudioManager.register(AudioUtils.playMedia("assets/music/bgm/Stage1.wav"));
        }
    }

    private void addBackground(int backgroundType){
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
