package SevenWonders;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene emptyScene = new Scene(new Group(), 640, 480);
        primaryStage.setScene(emptyScene);
        primaryStage.show();
    }
}
