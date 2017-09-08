package moaimoai.players;

import bases.Vector2D;
import bases.physics.Physics;
import bases.platforms.Platform;
import moaimoai.allies.BombObject;
import moaimoai.allies.FriendlyObject;

public class PlayerHitFriend {
    public void hitAlly(Player owner){
        FriendlyObject friendlyObject = Physics.collideWith(owner.getBoxCollider(),FriendlyObject.class);
        if(friendlyObject != null){
            FriendlyObject.setAllynumber(FriendlyObject.getAllynumber() - 1);
            friendlyObject.setActive(false);
        }
//            if(friendlyObject.isBomb()){
//                owner.setBomb(owner.getBomb() + 1);
//                friendlyObject.setActive(false);

        BombObject bombObject = Physics.collideWith(owner.getBoxCollider(),BombObject.class);
        if(bombObject != null){
            owner.setBomb(owner.getBomb() + 1);
            bombObject.setActive(false);
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
