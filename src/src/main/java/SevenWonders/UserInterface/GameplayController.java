package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Player.ConstructionZone;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.Network.Client;
import SevenWonders.Network.IGameListener;
import SevenWonders.Network.Requests.UpdateGameStateRequest;
import SevenWonders.SoundManager;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;
import java.util.Timer;

public class GameplayController implements Initializable, IGameListener {

    GameModel gameModel;

    Client client;
    int updateCount;

    CardViewController cardViewController;
    OtherPlayersController otherPlayersController;
    ConstructionZoneController constructionZoneController;
    GameplayToolbarController gameplayToolbarController;

    Pair<Parent, Object> pair;

    @FXML
    Pane constructionZonePane, otherPlayersConstructionViewPane, otherPlayersConstructionPane, cardViewPane, gameplayToolbarPane, otherPlayersViewPane;

    @FXML
    ImageView shieldImage;

    @FXML
    StackPane stackPane;

    public GameplayController() {
        gameModel = null;
        updateCount = 0;
        client = Client.getInstance();
        client.setGameListener(this);
        SoundManager.getInstance().stopMenuMusic();
        SoundManager.getInstance().playAgeOneMusic();
    }

    public void updateGameModel(GameModel gameModel) {
        Platform.runLater(() -> {
            if (this.gameModel != null) {
                if(this.gameModel.getCurrentTurn() == 6) {
                    stackPane.setVisible(true);
                    shieldImage.setImage(AssetManager.getInstance().getImage("shield.png"));
                    ImageView leftSword = new ImageView(AssetManager.getInstance().getImage("sword_left.png"));
                    ImageView rightSword = new ImageView(AssetManager.getInstance().getImage("sword_right.png"));

                    FadeTransition shield = new FadeTransition(Duration.millis(3000), shieldImage);
                    shield.setFromValue(0.0);
                    shield.setToValue(1.0);
                    shield.play();

                    TranslateTransition swordLeft = new TranslateTransition(Duration.millis(1000), leftSword);
                    swordLeft.setFromX(-500);
                    swordLeft.setToX(-50);
                    swordLeft.setFromY(-500);
                    swordLeft.setToY(-100);
                    swordLeft.play();

                    TranslateTransition swordRight = new TranslateTransition(Duration.millis(1000), rightSword);
                    swordRight.setFromX(500);
                    swordRight.setToX(50);
                    swordRight.setFromY(-500);
                    swordRight.setToY(-100);
                    swordRight.play();

                    stackPane.getChildren().add(leftSword);
                    stackPane.getChildren().add(rightSword);

                    ImageView shieldRight, shieldLeft;
                    if(getPlayer().getShields() < getRightPlayer().getShields()){
                        shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_minus1.png"));
                    }
                    else if(getPlayer().getShields() == getRightPlayer().getShields()){
                        shieldRight = null;
                    }
                    else{
                        if(this.gameModel.getCurrentAge() == 1){
                            shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_1.png"));
                        }
                        else if(this.gameModel.getCurrentAge() == 2){
                            shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_3.png"));
                        }
                        else if(this.gameModel.getCurrentAge() == 3){
                            shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_5.png"));
                        }
                        else {
                            shieldRight = null;
                        }
                    }
                    if (shieldRight != null){
                        shieldRight.setScaleY(0.8);
                        shieldRight.setScaleX(0.8);
                    }

                    if(getPlayer().getShields() < getLeftPlayer().getShields()){
                        shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_minus1.png"));
                    }
                    else if(getPlayer().getShields() == getLeftPlayer().getShields()){
                        shieldLeft = null;
                    }
                    else{
                        if(this.gameModel.getCurrentAge() == 1){
                            shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_1.png"));
                        }
                        else if(this.gameModel.getCurrentAge() == 2){
                            shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_3.png"));
                        }
                        else if(this.gameModel.getCurrentAge() == 3){
                            shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_5.png"));
                        }
                        else{

                            shieldLeft = null;
                        }
                    }
                    if (shieldLeft != null){
                        shieldLeft.setScaleY(0.8);
                        shieldLeft.setScaleX(0.8);
                    }

                    TranslateTransition leftShield  = new TranslateTransition(Duration.millis(1000), shieldLeft);
                    leftShield.setFromX(-700);
                    leftShield.setToX(-50);
                    leftShield.setFromY(-100);
                    leftShield.setToY(-100);
                    leftShield.play();

                    TranslateTransition rightShield = new TranslateTransition(Duration.millis(1000), shieldRight);
                    rightShield.setFromX(700);
                    rightShield.setToX(50);
                    rightShield.setFromY(-100);
                    rightShield.setToY(-100);
                    rightShield.play();

                    if(shieldLeft != null){
                        stackPane.getChildren().add(shieldLeft);
                    }
                    if(shieldRight != null){
                        stackPane.getChildren().add(shieldRight);
                    }

                    FadeTransition pane = new FadeTransition(Duration.millis(4000), stackPane);
                    pane.setFromValue(1.0);
                    pane.setToValue(0.0);
                    pane.play();
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
            else if(gameModel.getGameFinished()) {
                SoundManager.getInstance().stopAgeThreeMusic();
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
