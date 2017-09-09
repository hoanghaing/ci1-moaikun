package moaimoai.scenes;

import bases.GameObject;
import bases.renderers.ImageRenderer;
import moaimoai.settings.Settings;

import javax.sound.sampled.Clip;

public class GameOverScene extends Scene {
    Settings settings = Settings.instance;
    @Override
    public void init() {
        ImageRenderer imageRenderer = ImageRenderer.create("assets/images/scene images/GAMEOVER image.png");
        GameObject background = new GameObject().setRenderer(imageRenderer);
        background.getPosition().set(settings.getGamePlayWidth() / 2, settings.getGamePlayHeight() / 2);
        GameObject.add(background);
    }
}
