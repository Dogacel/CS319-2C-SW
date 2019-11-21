package SevenWonders;

import SevenWonders.Network.Client;
import SevenWonders.Network.Server;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApplication extends Application {

    @Override
    public void start(Stage stage){
        SceneManager.initialize(stage);
        stage.setOnCloseRequest(event -> {
            try {
                stop();
                Server.stopServerInstance();
                Client.getInstance().disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        SceneManager.getInstance().changeScene("MainMenu.fxml");
        SceneManager.getInstance().show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
