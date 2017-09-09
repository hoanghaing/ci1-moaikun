package moaimoai.door;

import bases.GameObject;
import bases.renderers.Animation;
import tklibs.SpriteUtils;

import java.awt.*;

public class DoorOpen extends GameObject {
    private Animation animation;
    private static int type;
    public DoorOpen(int type){
        this.type = type;
        switch (type){
            case 1:{
                this.animation = new Animation(
                        10,true,false,
                        SpriteUtils.loadImage("assets/images/doors/blue/door2.png"),
                        SpriteUtils.loadImage("assets/images/doors/blue/door3.png"),
                        SpriteUtils.loadImage("assets/images/doors/blue/door4.png"),
                        SpriteUtils.loadImage("assets/images/doors/blue/door5.png"),
                        SpriteUtils.loadImage("assets/images/doors/blue/door6.png"),
                        SpriteUtils.loadImage("assets/images/doors/blue/door7.png")
                        );
                this.renderer = animation;
                break;
            }
        }
    }

}
