package SevenWonders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class TestApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("PlayerView.fxml"));
        Scene emptyScene = new Scene(root, 1024,200);
        // root.setStyle("-fx-background-image: url('ui-images/wonderbabylon.png')");
        primaryStage.setScene(emptyScene);
        primaryStage.show();

        AssetManager.getInstance();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
