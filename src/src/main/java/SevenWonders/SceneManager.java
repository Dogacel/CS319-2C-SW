package SevenWonders;

import SevenWonders.Network.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static SceneManager instance = new SceneManager();
    private Stage stage;
    public static Client client = Client.getInstance();

    private SceneManager() {
        stage = new Stage();
    }

    public static SceneManager getInstance() {
        return instance;
    }

    public void changeScene(String sceneName) {
        Parent root = AssetManager.getInstance().getSceneByName(sceneName);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
       Image image = new Image("ui-images/tokens/arrow.png");
       scene.setCursor(new ImageCursor(image));
    }

    public void actOnExit() {
        stage.close();
    }

    public void show(){
        stage.show();
    }
}
