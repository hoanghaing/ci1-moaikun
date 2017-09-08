package moaimoai.scenes;

import bases.GameObject;
import bases.renderers.ImageRenderer;
import moaimoai.settings.Settings;

public class StartingMenu extends Scene{
    private ImageRenderer imageRenderer;

    @Override
    public void init() {
        imageRenderer = ImageRenderer.create("assets/images/scene images/startingimage.png");
        GameObject background = new GameObject().setRenderer(imageRenderer);
        background.getPosition().set(Settings.instance.getGamePlayWidth() / 2, Settings.instance.getGamePlayHeight() / 2);
        GameObject.add(background);
    }
}
