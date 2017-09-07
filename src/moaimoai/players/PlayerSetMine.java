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
            platform.getHit();
            Explosion explosion = new Explosion();
            explosion.getPosition().set(platform.getPosition());
            GameObject.add(explosion);
        }
    }
}
