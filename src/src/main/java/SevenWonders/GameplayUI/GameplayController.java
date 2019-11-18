package SevenWonders.GameplayUI;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.GameModel;
import SevenWonders.GameLogic.PlayerModel;
import SevenWonders.Network.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameplayController implements Initializable{
    GameplayModel model;

    GameModel gameModel;

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

    @FXML
    Pane player3ViewPane;

    @FXML
    Pane player4ViewPane;

    @FXML
    Pane player5ViewPane;

    @FXML
    Pane player6ViewPane;

    public GameplayController() {
        this.model = new GameplayModel();
        this.client = new Client("serverAddress", 8080, "userName");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addPlayerPane();

        Pane rightNeighborPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("RightNeighborView.fxml");
        this.neighborViewRightPane.getChildren().add(rightNeighborPane);

        Pane otherPlayersPane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersView.fxml");
        this.otherPlayersViewPane.getChildren().add(otherPlayersPane);

        Pane cardPane = (Pane) AssetManager.getInstance().getSceneByNameForce("CardView.fxml");
        this.cardViewPane.getChildren().add(cardPane);

        Pane leftNeighborPane = (Pane) AssetManager.getInstance().getSceneByNameForce("LeftNeighborView.fxml");
        this.neighborViewLeftPane.getChildren().add(leftNeighborPane);
    }

    private void addPlayerPane(){
        Pane playerPane =  (Pane) AssetManager.getInstance().getSceneByNameForce("PlayerView.fxml");
        this.playerViewPane.getChildren().add(playerPane);
    }

    public void player3ButtonClicked(){
        Pane player3Pane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");
        this.player3ViewPane.getChildren().add(player3Pane);
    }

    public void player4ButtonClicked(){
        Pane player4Pane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");
        this.player4ViewPane.getChildren().add(player4Pane);
    }

    public void player5ButtonClicked(){
        Pane player5Pane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");
        this.player5ViewPane.getChildren().add(player5Pane);
    }

    public void player6ButtonClicked(){
        Pane player6Pane = (Pane) AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");
        this.player6ViewPane.getChildren().add(player6Pane);
    }

    public PlayerModel getPlayer(){
        return gameModel.getPlayerList()[client.getID()];
    }

}
