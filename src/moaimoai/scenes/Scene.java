package moaimoai.scenes;

import bases.GameObject;
import moaimoai.inputs.InputHandler;
import moaimoai.inputs.InputManager;

/**
 * Created by NguyenGiaThe on 8/23/2017.
 */
public abstract class Scene {
    public void destroy(){
        GameObject.clearAll();
        InputManager.instance.unRegister();
    }
    public abstract void init();
    public InputHandler inputHandler;
}
