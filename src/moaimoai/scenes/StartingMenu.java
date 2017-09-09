package moaimoai.scenes;

import bases.GameObject;
import bases.renderers.ImageRenderer;
import moaimoai.inputs.InputManager;
import moaimoai.settings.Settings;

public class StartingMenu extends Scene{
    private ImageRenderer imageRenderer;
    InputManager inputManager = InputManager.instance;

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void init() {
        addStarting();
    }

    private void addStarting() {
        imageRenderer = ImageRenderer.create("assets/images/scene images/startingimage.png");
        GameObject background = new GameObject().setRenderer(imageRenderer);
        background.getPosition().set(Settings.instance.getGamePlayWidth() / 2, Settings.instance.getGamePlayHeight() / 2);
        GameObject.add(background);
    }

}
