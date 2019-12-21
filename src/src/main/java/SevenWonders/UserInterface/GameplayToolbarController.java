package SevenWonders.UserInterface;

import SevenWonders.AI.AIMoveGenerator;
import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.SoundManager;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class GameplayToolbarController {

    PlayerModel playerModel;
    GameplayController gameplayController;
    CardViewController cardViewController;
    MoveModel currentMove;

    ImageView wonder1, wonder2, wonder3;


    @FXML
    Button buildCardButton, buildWonderButton, readyButton, discardCardButton, useGodPowerButton;

    @FXML
    BorderPane wonder1Pane, wonder2Pane, wonder3Pane;

    private void updatePlayerMove(MoveModel move) {
        // TODO: Setting for auto trade
        var x = MoveController.getInstance().playerCanMakeMove(move, playerModel, new Pair<>(gameplayController.getLeftPlayer(),gameplayController.getRightPlayer()), SettingsController.autoTrade);
        for (TradeAction trade : x.getValue()) {
            move.addTrade(trade);
        }
        playerModel.setCurrentMove(move);
    }

    @FXML
    Label coinLabel, warPointLabel;

    @FXML
    HBox victoryPointsHBox;

    @FXML
    private void buildCardButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD);
        updatePlayerMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void buildWonderButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.UPGRADE_WONDER);
        updatePlayerMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void discardCardButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.DISCARD_CARD);
        updatePlayerMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void useGodPowerButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.USE_GOD_POWER);
        updatePlayerMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void readyButtonClicked(MouseEvent event) {
        if (playerModel.getCurrentMove()!= null) {
            boolean canPlay = MoveController.getInstance().playerCanMakeMove(playerModel.getCurrentMove(), playerModel,
                    new Pair<>(gameplayController.getLeftPlayer(),gameplayController.getRightPlayer()), false).getKey();
            if (canPlay) {
                currentMove = playerModel.getCurrentMove();
            } else if (!canPlay && currentMove == null) {
                currentMove = new MoveModel(0, 0, ACTION_TYPE.DISCARD_CARD);
            }
            gameplayController.getClient().sendMakeMoveRequest( playerModel.getCurrentMove());
            gameplayController.getClient().sendPlayerReadyRequest(true);
        }
    }

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            String type = "";

            if (currentMove != null) {
                Card playedCard = AssetManager.getInstance().getCardByID(currentMove.getSelectedCardID());

                if( currentMove.getAction() == ACTION_TYPE.BUILD_CARD) {
                    if (playedCard.getColor() == CARD_COLOR_TYPE.RED)
                        type = "red";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.BLUE)
                        type = "blue";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.GREEN)
                        type = "green";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.YELLOW)
                        type = "yellow";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.BROWN)
                        type = "brown";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.GRAY) {
                        if (playedCard.getCardEffect().getResources().get(RESOURCE_TYPE.PAPYRUS) != null)
                            type = "papyrus";
                        else if (playedCard.getCardEffect().getResources().get(RESOURCE_TYPE.GLASS) != null)
                            type = "glass";
                        else if (playedCard.getCardEffect().getResources().get(RESOURCE_TYPE.LOOM) != null)
                            type = "loom";
                    }
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.PURPLE)
                        type = "purple";

                    SoundManager.getInstance().playCardSound(type);

                }
                else if(currentMove.getAction() == ACTION_TYPE.UPGRADE_WONDER){
                    SoundManager.getInstance().playWonderSound();
                }
                else if(currentMove.getAction() == ACTION_TYPE.DISCARD_CARD){
                    SoundManager.getInstance().playDiscardSound();
                }
            }

            currentMove = null;

            this.playerModel = playerModel;
            updateWonder();
            updateCoinAndWarPoints();
        });
    }

    private void updateWonder(){
        wonder1 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_1.png"));
        wonder2 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_2.png"));
        wonder3 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_3.png"));
        wonder1Pane.setCenter(wonder1);
        wonder2Pane.setCenter(wonder2);
        wonder3Pane.setCenter(wonder3);

        if(playerModel.getWonder().getCurrentStageIndex() == 1) {
            wonder1Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
        }
        if(playerModel.getWonder().getCurrentStageIndex() == 2) {
            wonder2Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
        }
        if(playerModel.getWonder().getCurrentStageIndex() == 3) {
            wonder3Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
        }
    }

    private void updateCoinAndWarPoints() {
        victoryPointsHBox.getChildren().clear();
        coinLabel.setText(playerModel.getGold() + "");
        warPointLabel.setText(playerModel.getWarPoints() + "");

        for(int i = 0; i < playerModel.getLostWarNumber(); i++) {
            ImageView imageView = new ImageView(AssetManager.getInstance().getImage("warpoint_minus1.png"));
            victoryPointsHBox.getChildren().add(imageView);
        }
    }
}
