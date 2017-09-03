package moaimoai;

import bases.GameObject;
import moaimoai.inputs.InputManager;
import moaimoai.scenes.Level1Scene;
import moaimoai.scenes.SceneManager;
import moaimoai.settings.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class GameWindow extends Frame {

    private long lastTimeUpdate;
    private long currentTime;

    private BufferedImage blackBackground;
    private BufferedImage backbufferImage;
    private Graphics2D backbufferGraphics;

    InputManager inputManager = InputManager.instance;

    public GameWindow() {
        setupGameLoop();
        setupWindow();
        setupLevel();
        setupInput();
        setupBackbuffetImage();
    }

    private void setupBackbuffetImage() {
        this.backbufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.backbufferGraphics = (Graphics2D) this.backbufferImage.getGraphics();

        this.blackBackground = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D backgroundGraphics = (Graphics2D) this.blackBackground.getGraphics();
        backgroundGraphics.setColor(Color.BLACK);
        backgroundGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        Settings.instance.setWindowInsets(this.getInsets());
    }

    private void setupInput() {

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                inputManager.keyReleased(e);
            }
        });
    }

    private void setupLevel() {
        SceneManager.changeScene(new Level1Scene());
    }

    private void setupGameLoop() {
        lastTimeUpdate = -1;
    }

    private void setupWindow() {
        this.setSize(610, 460);
        this.setVisible(true);
        this.setTitle("CI1-Moaikun");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


    }

    public void loop() {
        while(true) {
            if (lastTimeUpdate == -1) {
                lastTimeUpdate = System.nanoTime();
            }
            currentTime = System.nanoTime();
            if (currentTime - lastTimeUpdate > 17000000) {
                run();
                render();
                SceneManager.changeSceneIfNeeded();
                lastTimeUpdate = currentTime;
            }
        }
    }

    private void run() {
        GameObject.runAll();
    }

    private void render() {
        backbufferGraphics.drawImage(blackBackground, 0, 0, null);
        GameObject.renderAll(backbufferGraphics);
        getGraphics().drawImage(backbufferImage, 0, 0, null);
    }
}
