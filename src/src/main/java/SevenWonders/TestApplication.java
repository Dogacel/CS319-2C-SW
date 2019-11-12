package SevenWonders;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class TestApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group g = new Group();
        Scene emptyScene = new Scene(g, 640, 480);
        primaryStage.setScene(emptyScene);
        primaryStage.show();

        AssetManager.getInstance();

    }
}
