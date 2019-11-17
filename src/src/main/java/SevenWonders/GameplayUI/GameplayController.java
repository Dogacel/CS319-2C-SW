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

    @FXML
    Pane neighborViewRightPane;

    @FXML
    Pane neighborViewLeftPane;

    @FXML
    Pane otherPlayersViewPane;

    @FXML
    Pane cardViewPane;

    public GameplayController() {
        this.model = new GameplayModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane newLoadedPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("PlayerView.fxml");
        Pane newLoadedPane2 =  (Pane) AssetManager.getInstance().getSceneByNameForce("RightNeighborView.fxml");
        Pane newLoadedPane3 = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersView.fxml");
       // Pane newLoadedPane4 = (Pane) AssetManager.getInstance().getSceneByNameForce("CardView.fxml");
        Pane newLoadedPane5 = (Pane) AssetManager.getInstance().getSceneByNameForce("LeftNeighborView.fxml");
        this.neighborViewRightPane.getChildren().add(newLoadedPane2);
        this.neighborViewLeftPane.getChildren().add(newLoadedPane5);
        this.playerViewPane.getChildren().add(newLoadedPane);
        this.otherPlayersViewPane.getChildren().add( newLoadedPane3);
      //  this.cardViewPane.getChildren().add( newLoadedPane4);
    }
}
