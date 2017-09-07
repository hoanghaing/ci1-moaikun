package moaimoai.players;

import bases.physics.Physics;
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
    }

    public void hitBomb(Player owner){
        BombObject bombObject = Physics.collideWith(owner.getBoxCollider(),BombObject.class);
        if(bombObject != null){
            owner.setBomb(owner.getBomb() + 1);
            bombObject.setActive(false);
        }
    }
}
