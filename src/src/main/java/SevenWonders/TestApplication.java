package SevenWonders;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {

    @Override
    public void start(Stage primaryStage){
        SceneManager.getInstance().changeScene("PlayMenu.fxml");
        SceneManager.getInstance().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
