package moaimoai.scenes;

import bases.Constraints;
import bases.GameObject;
import bases.platforms.Platform;
import moaimoai.inputs.InputManager;
import moaimoai.players.Player;
import moaimoai.settings.Settings;

public class Level1Scene extends Scene{
    Player player = new Player();
     // TODO: Viec cua lop: sua thanh game object

    Settings settings = Settings.instance;

    @Override
    public void init() {
        addBackground();
        addPlayer();
        addPlatform();
    }

    private void addBackground() {
        GameObject.add(new Background());
    }

    private void addPlayer() {
        player.setInputManager(InputManager.instance);
        player.setConstraints(new Constraints(
                settings.getWindowInsets().top,
                settings.getGamePlayHeight(),
                settings.getWindowInsets().left,
                settings.getGamePlayWidth())
        );
        player.getPosition().set(
                settings.getGamePlayWidth() / 2,
                settings.getGamePlayHeight() * 3 / 4);

        GameObject.add(player);
    }

    private void addPlatform(){
        Platform platform = new Platform();
        platform.getPosition().set(50 , 405);
        GameObject.add(platform);

        Platform platform1 = new Platform();
        platform1.getPosition().set(400, 405);
        GameObject.add(platform1);

        Platform platform2 = new Platform();
        platform2.getPosition().set(500 , 350);
        GameObject.add(platform2);
    }
}
