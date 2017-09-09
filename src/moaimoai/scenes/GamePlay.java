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
    private static int stageLevel = -5;
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
        addTitle();
    }

    private void addTitle() {
        TextObject bomb = GameObjectPool.recycle(TextObject.class);
        TextRenderer textRenderer = ((TextRenderer)bomb.getRenderer());
        textRenderer.setText("Bomb: "+ Player.bomb);
        bomb.getPosition().set(Settings.instance.getGamePlayWidth() - 100,75);
        TextObject hp = GameObjectPool.recycle(TextObject.class);
        TextRenderer  textRenderer1 = ((TextRenderer)hp.getRenderer());
        textRenderer1.setText("HP: " + String.valueOf(GameWindow.getPlayerHP()));
        hp.getPosition().set(Settings.instance.getGamePlayWidth() - 100,100);
    }
    private void addMusic(int level){
        if (level < 10){
            AudioManager.register(AudioUtils.playMedia("assets/music/bgm/Stage1.wav"));
        }
    }

    private void addBackground(int backgroundType){
        GameObject.add(new Background(backgroundType));
    }

    private void addPlatform(int level) {
//        level = 1;
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
