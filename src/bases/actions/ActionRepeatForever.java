package bases.actions;

import bases.GameObject;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public class ActionRepeatForever implements Action {
    private Action action;

    public ActionRepeatForever(Action action) {
        this.action = action;
    }

    @Override
    public boolean run(GameObject owner) {
        if (action.run(owner)) {
            action.reset();
        }
        return false;
    }

    @Override
    public void reset() {
        action.reset();
    }
}
