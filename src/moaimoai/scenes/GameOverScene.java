package moaimoai.scenes;

import bases.GameObject;
import bases.renderers.ImageRenderer;

public class GameOverScene extends Scene {
    @Override
    public void init() {
        GameObject.add(new GameObject().setRenderer(ImageRenderer.create("assets/images/scenes/game-over-background.jpg")));
    }
}
