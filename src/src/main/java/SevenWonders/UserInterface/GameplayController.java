package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Player.ConstructionZone;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.Network.Client;
import SevenWonders.Network.IGameListener;
import SevenWonders.Network.Requests.EndGameRequest;
import SevenWonders.Network.Requests.UpdateGameStateRequest;
import SevenWonders.SceneManager;
import SevenWonders.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class GameplayController implements Initializable, IGameListener {

    GameModel gameModel;

    Client client;

    CardViewController cardViewController;
    OtherPlayersController otherPlayersController;
    ConstructionZoneController constructionZoneController;
    GameplayToolbarController gameplayToolbarController;
    ScoreViewController scoreViewController;

    Pair<Parent, Object> pair;

    @FXML
    Pane constructionZonePane, otherPlayersConstructionViewPane, otherPlayersConstructionPane, cardViewPane, gameplayToolbarPane, otherPlayersViewPane;

    public GameplayController() {
        gameModel = null;
        client = Client.getInstance();
        client.setGameListener(this);
        SoundManager.getInstance().stopMenuMusic();
        SoundManager.getInstance().playAgeOneMusic();
    }

    public void updateGameModel(GameModel gameModel) {
        Platform.runLater(() -> {
            this.gameModel = gameModel;
            System.out.println("AGE IS "  + gameModel.getCurrentAge() + " turn is " + gameModel.getCurrentTurn());
            if (gameModel.getCurrentAge() == 2 && gameModel.getCurrentTurn() == 1)
            {
                SoundManager.getInstance().stopAgeOneMusic();
                SoundManager.getInstance().playBattleSound();
                SoundManager.getInstance().playAgeTwoMusic();
            }
            else if (gameModel.getCurrentAge() == 3 && gameModel.getCurrentTurn() == 1)
            {
                SoundManager.getInstance().stopAgeTwoMusic();
                SoundManager.getInstance().playBattleSound();
                SoundManager.getInstance().playAgeThreeMusic();
            }
            else if(gameModel.getCurrentAge() == 4 && gameModel.getCurrentTurn() == 1) {
                SoundManager.getInstance().stopAgeThreeMusic();

                scoreViewController = (ScoreViewController) AssetManager.getInstance().getSceneAndController("ScoreView.fxml").getValue();
                scoreViewController.gameplayController = this;

                SceneManager.getInstance().changeScene("ScoreView.fxml");
            }
            PlayerModel me = gameModel.getPlayerList()[client.getID()];

            cardViewController.updateScene(me.getHand());
            constructionZoneController.updateScene(me);
            gameplayToolbarController.updateScene(me);
            otherPlayersController.updateScene(me);

            otherPlayersViewPane.setOnMouseClicked((event) -> {
                if (otherPlayersConstructionViewPane.isVisible()) {
                    int visibleCount = 0;
                    for (var child : otherPlayersConstructionPane.getChildren()) {
                        if (child.isVisible()) {
                            visibleCount++;
                        }
                    }
                    if (visibleCount == 0) {
                        otherPlayersConstructionViewPane.setVisible(false);
                    }
                }
                else {
                    otherPlayersConstructionViewPane.setVisible(true);
                }
            });
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pair = AssetManager.getInstance().getSceneAndController("GameplayToolbarView.fxml");
        Pane gameplayToolbarPane = (Pane) pair.getKey();
        gameplayToolbarController = (GameplayToolbarController) pair.getValue();
        this.gameplayToolbarPane.getChildren().add(gameplayToolbarPane);

        pair = AssetManager.getInstance().getSceneAndController("OtherPlayersView.fxml");
        Pane otherPlayersViewPane = (Pane) pair.getKey().lookup("#otherPlayersPane");
        otherPlayersConstructionPane = (Pane) pair.getKey().lookup("#otherPlayersConstructionPane");
        otherPlayersController = (OtherPlayersController) pair.getValue();
        this.otherPlayersConstructionViewPane.getChildren().add(otherPlayersConstructionPane);
        this.otherPlayersViewPane.getChildren().add(otherPlayersViewPane);
        otherPlayersController.gameplayController = this;

        pair = AssetManager.getInstance().getSceneAndController("CardView.fxml");
        Pane cardPane = (Pane)  pair.getKey();
        cardViewController = (CardViewController) pair.getValue();
        this.cardViewPane.getChildren().add(cardPane);

        pair = AssetManager.getInstance().getSceneAndController("ConstructionZoneView.fxml");
        Pane constructionZonePane = (Pane) pair.getKey();
        constructionZoneController = (ConstructionZoneController) pair.getValue();
        this.constructionZonePane.getChildren().add(constructionZonePane);

        constructionZoneController.gameplayController = this;
        cardViewController.gameplayController = this;
        gameplayToolbarController.gameplayController = this;
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

    public Pair<PlayerModel, PlayerModel> getNeighbors() {
        return new Pair<>(getLeftPlayer(), getRightPlayer());
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
