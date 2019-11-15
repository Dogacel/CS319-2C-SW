package SevenWonders.GameplayUI;

import SevenWonders.AssetManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameplayController implements Initializable{
    GameplayModel model;

    @FXML
    Pane playerViewPane;

    public GameplayController() {
        this.model = new GameplayModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane newLoadedPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("PlayerView.fxml");
        this.playerViewPane.getChildren().add(newLoadedPane);
    }
}
