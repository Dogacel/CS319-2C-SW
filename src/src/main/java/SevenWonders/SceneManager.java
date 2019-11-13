package SevenWonders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance = new SceneManager();

    private Stage stage;

    private SceneManager() {
        fxmlLoader = new FXMLLoader();
    }

    public static SceneManager getInstance() {
        return instance;
    }

    private void changeScene(String sceneName) throws IOException {

        Parent root = getSceneByName(sceneName);
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
}
