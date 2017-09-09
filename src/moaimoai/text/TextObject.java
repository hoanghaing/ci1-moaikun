package moaimoai.text;

import bases.GameObject;
import bases.Vector2D;
import bases.renderers.TextRenderer;

public class TextObject extends GameObject{
    public TextObject(){
        this.renderer = new TextRenderer("");
    }
}
