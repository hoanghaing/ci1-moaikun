package moaimoai.scenes;

import bases.GameObject;
import bases.renderers.ImageRenderer;
import moaimoai.settings.Settings;

public class StartingMenu extends GameObject{
        private ImageRenderer imageRenderer;
    Settings settings = Settings.instance;
    public StartingMenu() {
        super();
        imageRenderer = ImageRenderer.create("assets/images/scene images/startingimage.png");
        GameObject background = new GameObject().setRenderer(imageRenderer);
        background.getPosition().set(settings.getGamePlayWidth() / 2, settings.getGamePlayHeight() / 2);
//        imageRenderer.getAnchor().set(0, 1);
//        this.renderer = imageRenderer;


        GameObject.add(background);
    }
}
