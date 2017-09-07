package bases.actions;

import bases.GameObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public class SequenceAction implements Action {
    private List<Action> actions;
    private int currentActionIndex;

    public SequenceAction(Action...actions) {
        this.actions = Arrays.asList(actions);
        this.currentActionIndex = 0;
    }

    @Override
    public boolean run(GameObject owner) {

        if (currentActionIndex >= actions.size()){
            return true;
        }
        else {
            Action currentAction = actions.get(currentActionIndex);
            if (currentAction.run(owner)){
                currentActionIndex++;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        currentActionIndex = 0;
        for (Action action : actions){
            action.reset();
        }
    }
}
