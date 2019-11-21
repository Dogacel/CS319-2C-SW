package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.GameModel;
import SevenWonders.GameLogic.PlayerModel;
import SevenWonders.Network.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameplayController implements Initializable{

    private static GameplayController gameplayControllerInstance = null;

    GameModel gameModel;

    Client client;

    PlayerController playerController;
    CardViewController cardViewController;
    OtherPlayersController otherPlayersController;
    OtherPlayersDetailController otherPlayersDetailController;
    NeighborController neighborController;

    @FXML
    Pane playerViewPane, neighborViewRightPane, neighborViewLeftPane, otherPlayersViewPane, cardViewPane, player3ViewPane, player4ViewPane
            ,player5ViewPane, player6ViewPane;

    public GameplayController() {
        gameModel = new GameModel();
        client = Client.getInstance();
    }

    public void updateGameModel(GameModel gameModel) throws FileNotFoundException {
        playerController.updateScene();
        cardViewController.updateScene();
        otherPlayersController.updateScene();
        otherPlayersDetailController.updateScene();
        neighborController.updateScene();
    }

    public static GameplayController getInstance() {
        if ( gameplayControllerInstance == null) {
            gameplayControllerInstance = new GameplayController();
        }
        return gameplayControllerInstance;
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

    public PlayerModel getRightPlayer(){
        return gameModel.getPlayerList()
    }

    public Client getClient(){
        return client;
    }

}
