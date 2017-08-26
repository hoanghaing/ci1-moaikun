package moaimoai.scenes;

import bases.GameObject;

/**
 * Created by NguyenGiaThe on 8/23/2017.
 */
public abstract class Scene {
    public void destroy(){
        GameObject.clearAll();
    }

    public abstract void init();
}
