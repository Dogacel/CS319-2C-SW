package SevenWonders.GameplayUI;

import SevenWonders.AssetManager;
import SevenWonders.Network.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameplayController implements Initializable{
    GameplayModel model;

    Client client;

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
        this.playerViewPane.getChildren().add(playerPane);

        Pane rightNeighborPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("RightNeighborView.fxml");
        this.neighborViewRightPane.getChildren().add(rightNeighborPane);

        Pane otherPlayersPane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersView.fxml");
        this.otherPlayersViewPane.getChildren().add(otherPlayersPane);

        Pane cardPane = (Pane) AssetManager.getInstance().getSceneByNameForce("CardView.fxml");
        this.cardViewPane.getChildren().add(cardPane);

        Pane leftNeighborPane = (Pane) AssetManager.getInstance().getSceneByNameForce("LeftNeighborView.fxml");
        this.neighborViewLeftPane.getChildren().add(leftNeighborPane);
    }

    public void setClient(Client client){
        this.client = client;
    }

}
