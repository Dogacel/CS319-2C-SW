package SevenWonders;

import SevenWonders.Network.Client;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;
    public static Client client = Client.getInstance();

    private SceneManager(Stage firstStage) {
        stage = firstStage;
    }

    public static void initialize(Stage firstStage) {
        instance = new SceneManager(firstStage);
        Font.loadFont(SceneManager.getInstance().getClass().getClassLoader().getResource("fonts/Assassin$.ttf").toExternalForm(), 60);
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
       Image image = new Image("images/tokens/arrow.png");
       scene.setCursor(new ImageCursor(image));
    }

    public void actOnExit() {
        stage.close();
    }

    public void show(){
        stage.show();
    }
}
