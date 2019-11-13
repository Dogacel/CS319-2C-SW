package SevenWonders;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance = new SceneManager();
    private Stage stage;

    private SceneManager() {
        stage = new Stage();
    }

    public static SceneManager getInstance() {
        return instance;
    }

    private void changeScene(String sceneName) throws IOException {

        Parent root = AssetManager.getInstance().getSceneByName(sceneName);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
    }

    public void actOnExit() {
        stage.close();
    }

    public void show(){
        stage.show();
    }
}
