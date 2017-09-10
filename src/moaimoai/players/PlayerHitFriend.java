package moaimoai.players;

import bases.Vector2D;
import bases.physics.Physics;
import bases.platforms.Platform;
import bases.pools.GameObjectPool;
import bases.renderers.TextRenderer;
import moaimoai.allies.BombObject;
import moaimoai.allies.FriendlyObject;
import moaimoai.text.TextObject;
import tklibs.AudioUtils;

public class PlayerHitFriend {
    public void hitAlly(Player owner){
        FriendlyObject friendlyObject = Physics.collideWith(owner.getBoxCollider(),FriendlyObject.class);
        if(friendlyObject != null){
            FriendlyObject.setAllynumber(FriendlyObject.getAllynumber() - 1);
            friendlyObject.setActive(false);
            AudioUtils.play(owner.getHitAlly());
        }

        BombObject bombObject = Physics.collideWith(owner.getBoxCollider(),BombObject.class);
        if(bombObject != null){
            owner.setBomb(owner.getBomb()+1);
            bombObject.setActive(false);
            AudioUtils.play(owner.getHitBomb());
        }

        Vector2D checkPos = owner.getScreenPosition().add(0,3);
        Platform platform = Physics.collideWith(checkPos,owner.getBoxCollider().getWidth(),owner.getBoxCollider().getHeight(),Platform.class);
        if(platform != null && platform.isTrap()){
            if(platform.getStandOnTrap().run()){
                platform.setHasGravity(true);
                platform.getStandOnTrap().reset();
            }
        }
    }
}
