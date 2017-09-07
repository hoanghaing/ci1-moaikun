package bases.actions;

import bases.FrameCounter;
import bases.GameObject;

/**
 * Created by NguyenGiaThe on 8/26/2017.
 */
public class WaitAction implements Action {
    private FrameCounter frameCounter;

    public WaitAction(int frameDelay) {
        this.frameCounter = new FrameCounter(frameDelay);
    }

    @Override
    public boolean run(GameObject owner) {
        return frameCounter.run();
    }

    @Override
    public void reset() {
        frameCounter.reset();
    }
}
