package moaimoai.scenes;

/**
 * Created by NguyenGiaThe on 8/23/2017.
 */
public class SceneManager {

    private static Scene currentScene;
    private static Scene nextScene;
    public static void changeScene(Scene newScene) {

        if (nextScene == null){
            nextScene = newScene;
        }
    }

    public static void changeSceneIfNeeded(){
        if (nextScene != null){
            if (currentScene != null) {
                currentScene.destroy();
            }
            nextScene.init();
            currentScene = nextScene;
            nextScene = null;
        }
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }
}
