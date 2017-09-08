package moaimoai.players;

import bases.GameObject;
import bases.Vector2D;
import bases.physics.Physics;
import bases.platforms.Platform;
import moaimoai.enemies.Explosion;

public class PlayerSetMine {
    public void setMine(Player owner) {
        Vector2D checkPosition = owner.getScreenPosition().add(0, 1);
        Platform platform = Physics.collideWith(checkPosition, owner.getBoxCollider().getWidth(), owner.getBoxCollider().getHeight(), Platform.class);
        if (platform != null && platform.isBreakable()) {
            if(owner.getSetMineTime().run()){
                owner.setBomb(owner.getBomb() - 1);
                Explosion explosion = new Explosion();
                explosion.getPosition().set(platform.getPosition());
                GameObject.add(explosion);
                platform.setBlowing(true);
                owner.getSetMineTime().reset();
            }
        }
    }
}
