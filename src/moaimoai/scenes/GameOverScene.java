package moaimoai.scenes;

import bases.GameObject;
import bases.renderers.ImageRenderer;
import moaimoai.GameOver;
import moaimoai.GameWindow;
import moaimoai.inputs.InputHandler;
import moaimoai.inputs.InputManager;
import moaimoai.settings.Settings;

import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;

public class GameOverScene extends Scene {
    Settings settings = Settings.instance;
    @Override
    public void init() {
        GameObject.stop = false;
        ImageRenderer imageRenderer = ImageRenderer.create("assets/images/scene images/GAMEOVER image.png");
        GameObject background = new GameObject().setRenderer(imageRenderer);
        background.getPosition().set(settings.getGamePlayWidth() / 2, settings.getGamePlayHeight() / 2);
        GameObject.add(background);

        this.inputHandler = new InputHandler() {
            @Override
            public void KeyPressed(KeyEvent e) {
            }

            @Override
            public void KeyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    GamePlay.setStageLevel(1);
                    SceneManager.changeScene(new GamePlay());
                }
            }
        };
        InputManager.instance.register(this);
    }

}
