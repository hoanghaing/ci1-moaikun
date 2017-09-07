package bases.actions;

import bases.GameObject;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public interface Action {

    boolean run(GameObject owner);      //false : action still runnig - true: action ended
    void reset();
}
