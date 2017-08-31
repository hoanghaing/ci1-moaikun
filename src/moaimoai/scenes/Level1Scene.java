package moaimoai.scenes;

import bases.Constraints;
import bases.GameObject;
import bases.platforms.Platform;
import moaimoai.allies.Ally;
import moaimoai.inputs.InputManager;
import moaimoai.players.Player;
import moaimoai.settings.Settings;

public class Level1Scene extends Scene{
    Player player = new Player();
    Ally ally = new Ally();

    Settings settings = Settings.instance;

    @Override
    public void init() {
        addBackground();
        addPlayer();
        addPlatform();
        addAlly();
    }

    private void addAlly() {
        ally.getPosition().set(
                400, 150
        );
        GameObject.add(ally);
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
            500,250
        );

        GameObject.add(player);
    }

    private void addPlatform(){
        Platform platform = new Platform(1);
        platform.getPosition().set(304 , 405);
        GameObject.add(platform);

       for(int i = 0, platX = 100; i < 4 ; i++, platX += 100){
           for(int j = 0, platY = 350; j < i +1 ; j++, platY -= 50){
               Platform platform1 = new Platform(2);
               platform1.getPosition().set(platX,platY);
               GameObject.add(platform1);
           }
       }

//        Platform platform1 = new Platform(1);
//        platform1.getPosition().set(400, 405);
//        GameObject.add(platform1);
//
//        Platform platform2 = new Platform();
//        platform2.getPosition().set(500 , 375);
//        GameObject.add(platform2);
    }
}
