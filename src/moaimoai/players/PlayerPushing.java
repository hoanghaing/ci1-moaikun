package moaimoai.players;

import bases.physics.Physics;
import bases.platforms.Platform;
import moaimoai.inputs.InputManager;
import tklibs.AudioUtils;

public class PlayerPushing {
    public void pushLeft(Player owner){
        Platform platform = Physics.collideWith(owner.getScreenPosition().add(Math.signum(owner.getVelocity().x), 0), owner.getBoxCollider().getWidth(), owner.getBoxCollider().getHeight(), Platform.class);
        if(platform != null && platform.isMoveable()){
            if(owner.getPushingTime().run()) {
                platform.getVelocity().x = owner.getVelocity().x;
                platform.setMoving(true);
                platform.setStopable(true);
                owner.getPushingTime().reset();
                AudioUtils.play(owner.getPush());
            }
        }
        if(platform == null || !InputManager.instance.leftPressed){
            owner.getPushingTime().reset();
        }
    }

    public void pushRight(Player owner) {
        Platform platform = Physics.collideWith(owner.getScreenPosition().add(Math.signum(owner.getVelocity().x), 0), owner.getBoxCollider().getWidth(), owner.getBoxCollider().getHeight(), Platform.class);
        if(platform != null && platform.isMoveable()){
            if(owner.getPushingTime().run()) {
                platform.getVelocity().x = owner.getVelocity().x;
                platform.setMoving(true);
                platform.setStopable(true);
                owner.getPushingTime().reset();
                AudioUtils.play(owner.getPush());
            }
        }
        if(platform == null || !InputManager.instance.rightPressed){
            owner.getPushingTime().reset();
        }
    }
}
