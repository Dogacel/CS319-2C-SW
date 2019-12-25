package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.GOD_TYPE;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.Hero;
import SevenWonders.Network.Client;
import SevenWonders.Network.IGameListener;
import SevenWonders.Network.Requests.UpdateGameStateRequest;
import SevenWonders.SoundManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class GameplayController implements Initializable, IGameListener {

    GameModel gameModel;

    Client client;

    CardViewController cardViewController;
    OtherPlayersController otherPlayersController;
    ConstructionZoneController constructionZoneController;
    GameplayToolbarController gameplayToolbarController;
    HeroPowerSelectionController heroPowerSelectionController;

    Pair<Parent, Object> pair;

    @FXML
    Pane constructionZonePane, otherPlayersConstructionViewPane, otherPlayersConstructionPane, cardViewPane, gameplayToolbarPane, otherPlayersViewPane;

    @FXML
    StackPane stackPane;

    public GameplayController() {
        gameModel = null;
        client = Client.getInstance();
        client.setGameListener(this);
        SoundManager.getInstance().stopMenuMusic();
        SoundManager.getInstance().playAgeOneMusic();
    }

    public void updateGameModel(GameModel gameModel) {
        Platform.runLater(() -> {
            if (this.gameModel != null) {
                System.out.println(this.gameModel.getPlayerList()[getPlayer().getId()]);
                if (this.gameModel.getPlayerList()[getPlayer().getId()].getCurrentMove().getAction() == ACTION_TYPE.USE_GOD_POWER && this.gameModel.getPlayerList()[getPlayer().getId()].getWonder().getCurrentStageIndex() == 3 &&
                        !this.gameModel.getPlayerList()[getPlayer().getId()].getWonder().getGod().isUsed()) {
                         AnimationController.godAnimation(gameModel.getPlayerList()[getPlayer().getId()], stackPane);
                }

                if(gameModel.getPlayerList()[getPlayer().getId()].getHeroes().size() != getPlayer().getHeroes().size()){
                    AnimationController.heroAnimation(gameModel.getPlayerList()[getPlayer().getId()], stackPane);
                }

                if(this.gameModel.getCurrentTurn() == 6) {
                    AnimationController.endOfAgeAnimation(this, stackPane, gameModel);
                    AnimationController.startOfAgeAnimation(this, stackPane, gameModel);
                    SoundManager.getInstance().playCardSound("red");
                }

                if(gameModel.getPlayerList()[getPlayer().getId()].getWonder().getCurrentStageIndex() == 3 && this.gameModel.getPlayerList()[getPlayer().getId()].getWonder().getCurrentStageIndex() != 3){
                    AnimationController.wonderCompletedAnimation();
                    gameplayToolbarController.wonderCompleted();
                }
            }

            this.gameModel = gameModel;
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

            PlayerModel me = gameModel.getPlayerList()[client.getID()];

            constructionZoneController.updateScene(me);
            gameplayToolbarController.updateScene(me);
            otherPlayersController.updateScene(me);
            cardViewController.updateScene(me.getHand());

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

    public StackPane getStackPane() {
        return stackPane;
    }
}
