package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.GameModel;
import SevenWonders.GameLogic.PlayerModel;
import SevenWonders.Network.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

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
    NeighborController leftNeighborController, rightNeighborController;

    Pair<Parent, Object> pair;

    @FXML
    Pane playerViewPane, neighborViewRightPane, neighborViewLeftPane, otherPlayersViewPane, cardViewPane, player3ViewPane, player4ViewPane
            ,player5ViewPane, player6ViewPane;

    public GameplayController() {
        gameModel = null;
        client = Client.getInstance();
    }

    public void updateGameModel(GameModel gameModel) throws FileNotFoundException {
        playerController.updateScene();
        cardViewController.updateScene();
        //otherPlayersController.updateScene();
        //otherPlayersDetailController.updateScene();


        leftNeighborController.setNeighbor( getLeftPlayer());
        rightNeighborController.setNeighbor( getRightPlayer());

        leftNeighborController.updateScene();
        rightNeighborController.updateScene();

    }

    public static GameplayController getInstance() {
        if ( gameplayControllerInstance == null) {
            gameplayControllerInstance = new GameplayController();
        }
        return gameplayControllerInstance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pair = AssetManager.getInstance().getSceneAndController("PlayerView.fxml");
        Pane playerPane =  (Pane) pair.getKey();
        playerController = (PlayerController) pair.getValue();
        this.playerViewPane.getChildren().add(playerPane);

        pair = AssetManager.getInstance().getSceneAndController("NeighborView.fxml");
        Pane rightNeighborPane = (Pane) pair.getKey();
        rightNeighborController = (NeighborController) pair.getValue();
        this.neighborViewRightPane.getChildren().add(rightNeighborPane);

        pair = AssetManager.getInstance().getSceneAndController("NeighborView.fxml");
        Pane leftNeighborPane = (Pane) pair.getKey();
        leftNeighborController = (NeighborController) pair.getValue();
        this.neighborViewLeftPane.getChildren().add(leftNeighborPane);

        pair = AssetManager.getInstance().getSceneAndController("OtherPlayersView.fxml");
        Pane otherPlayersPane = (Pane) pair.getKey();
        otherPlayersController = (OtherPlayersController) pair.getValue();
        this.otherPlayersViewPane.getChildren().add(otherPlayersPane);

        pair = AssetManager.getInstance().getSceneAndController("CardView.fxml");
        Pane cardPane = (Pane)  pair.getKey();
        cardViewController = (CardViewController) pair.getValue();
        this.cardViewPane.getChildren().add(cardPane);
    }

    public PlayerModel getPlayer(){
        if( gameModel != null)
            return gameModel.getPlayerList()[client.getID()];
        return null;
    }

    public PlayerModel getRightPlayer(){

        return gameModel.getRightPlayer( getPlayer().getId());
    }

    public PlayerModel getLeftPlayer(){
        return gameModel.getLeftPlayer( getPlayer().getId());
    }

    public Client getClient(){
        return client;
    }

}
