package moaimoai.inputs;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class InputManager {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean xPressed;
    public boolean cPressed;

    public static final InputManager instance = new InputManager();
    public boolean enterPressed;
    public boolean shiftPressed;


    private InputManager() {

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_RIGHT:
                rightPressed = true;
                break;
            case VK_LEFT:
                leftPressed = true;
                break;
            case VK_UP:
                upPressed = true;
                break;
            case VK_DOWN:
                downPressed = true;
                break;
            case VK_X:
                xPressed = true;
                break;
            case VK_C:
                cPressed = true;
                break;
            case VK_ENTER:
                enterPressed = true;
                break;
            case VK_SHIFT:
                shiftPressed = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_RIGHT:
                rightPressed = false;
                break;
            case VK_LEFT:
                leftPressed = false;
                break;
            case VK_UP:
                upPressed = false;
                break;
            case VK_DOWN:
                downPressed = false;
                break;
            case VK_X:
                xPressed = false;
                break;
            case VK_C:
                cPressed = false;
                break;
            case VK_ENTER:
                enterPressed = false;
                break;
            case VK_SHIFT:
                shiftPressed = false;
                break;
        }
    }
}
