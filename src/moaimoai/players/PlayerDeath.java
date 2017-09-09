package moaimoai.players;

import bases.FrameCounter;
import bases.GameObject;
import bases.Vector2D;
import bases.renderers.Animation;
import moaimoai.GameWindow;
import moaimoai.scenes.GameOverScene;
import moaimoai.scenes.SceneManager;
import tklibs.SpriteUtils;

import java.awt.*;

public class PlayerDeath extends GameObject{
    private Animation animation;
    private final int SPEED = 2;
    private FrameCounter waittingTime;

    public PlayerDeath(){
        this.animation = new Animation(
                7,
                false,
                false,
                SpriteUtils.loadImage("assets/images/player/left/dead/1.png"),
                SpriteUtils.loadImage("assets/images/player/left/dead/2.png")
        );
        this.renderer = animation;
        this.waittingTime = new FrameCounter(60);
    }

    @Override
    public void run(Vector2D parentPosition) {
        super.run(parentPosition);
        if(waittingTime.run()){
            this.position.y -= SPEED;
        }
        int HP = GameWindow.getPlayerHP();
        if(HP == 0 && position.y <= 0){
            SceneManager.changeScene(new GameOverScene());
        }
        if(this.position.y <= 0){
            SceneManager.changeScene(SceneManager.getCurrentScene());
        }
    }


    @Override
    public void reset() {
        super.reset();
        animation.reset();
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
        if(animation.isStopped()){
            this.isActive = false;
        }
    }
}
