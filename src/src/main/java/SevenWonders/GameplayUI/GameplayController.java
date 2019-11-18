package SevenWonders.GameplayUI;

import SevenWonders.AssetManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

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
        Pane playerPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("PlayerView.fxml");
        Pane rightNeighborPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("RightNeighborView.fxml");
        Pane otherPlayersPane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersView.fxml");
        Pane cardPane = (Pane) AssetManager.getInstance().getSceneByNameForce("CardView.fxml");
        Pane leftNeighborPane = (Pane) AssetManager.getInstance().getSceneByNameForce("LeftNeighborView.fxml");
        this.neighborViewRightPane.getChildren().add(playerPane);
        this.neighborViewLeftPane.getChildren().add(rightNeighborPane);
        this.playerViewPane.getChildren().add(otherPlayersPane);
        this.otherPlayersViewPane.getChildren().add(cardPane);
        this.cardViewPane.getChildren().add(leftNeighborPane);
    }

}
