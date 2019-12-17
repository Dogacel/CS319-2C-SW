package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.Network.Client;
import SevenWonders.Network.IGameListener;
import SevenWonders.Network.Requests.UpdateGameStateRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class GameplayController implements Initializable, IGameListener {


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
        client.setGameListener(this);
    }

    public void updateGameModel(GameModel gameModel) {
        PlayerModel me = gameModel.getPlayerList()[client.getID()];

        playerController.updateScene(me);
        cardViewController.updateScene(me.getHand());
        constructionZoneController.updateScene(me);
        gameplayToolbarController.updateScene(me);



        //otherPlayersController.updateScene();
        //otherPlayersDetailController.updateScene();

        //leftNeighborController.updateScene(gameModel.getLeftPlayer(client.getID()));
        //rightNeighborController.updateScene(gameModel.getRightPlayer(client.getID()));

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pair = AssetManager.getInstance().getSceneAndController("GameplayToolbarView.fxml");
        Pane gameplayToolbarPane = (Pane) pair.getKey();
        gameplayToolbarController = (GameplayToolbarController) pair.getValue();
        this.gameplayToolbarPane.getChildren().add(gameplayToolbarPane);
        gameplayToolbarController.gameplayController = this;

        pair = AssetManager.getInstance().getSceneAndController("NeighborView.fxml");
        Pane rightNeighborPane = (Pane) pair.getKey();
        rightNeighborController = (NeighborController) pair.getValue();
        this.neighborViewRightPane.getChildren().add(rightNeighborPane);
        //rightNeighborController.gameplayController = this;

        pair = AssetManager.getInstance().getSceneAndController("NeighborView.fxml");
        Pane leftNeighborPane = (Pane) pair.getKey();
        leftNeighborController = (NeighborController) pair.getValue();
        this.neighborViewLeftPane.getChildren().add(leftNeighborPane);
        //leftNeighborController.gameplayController = this;

        pair = AssetManager.getInstance().getSceneAndController("OtherPlayersView.fxml");
        Pane otherPlayersPane = (Pane) pair.getKey();
        otherPlayersController = (OtherPlayersController) pair.getValue();
        this.otherPlayersViewPane.getChildren().add(otherPlayersPane);

        pair = AssetManager.getInstance().getSceneAndController("CardView.fxml");
        Pane cardPane = (Pane)  pair.getKey();
        cardViewController = (CardViewController) pair.getValue();
        this.cardViewPane.getChildren().add(cardPane);
        cardViewController.gameplayController = this;

        pair = AssetManager.getInstance().getSceneAndController("ConstructionZoneView.fxml");
        Pane constructionZonePane = (Pane) pair.getKey();
        constructionZoneController = (ConstructionZoneController) pair.getValue();
        this.constructionZonePane.getChildren().add(constructionZonePane);
        constructionZoneController.gameplayController = this;

        gameplayToolbarController.cardViewController = cardViewController;
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

    @Override
    public void onUpdateGameStateRequest(UpdateGameStateRequest request) {
        this.updateGameModel(request.newGameModel);
    }

    @Override
    public void onEndGameRequest() {

    }

    @Override
    public void onEndAgeRequest() {

    }

    @Override
    public void onEndTurnRequest() {

    }

    @Override
    public void onDisconnect() {

    }
}
