package SevenWonders.UserInterface;

import SevenWonders.AI.AIMoveGenerator;
import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameplayToolbarController {

    PlayerModel playerModel;
    GameplayController gameplayController;
    CardViewController cardViewController;
    MoveModel currentMove;

    ImageView wonder1, wonder2, wonder3;

    @FXML
    Label coinLabel;

    @FXML
    Button buildCardButton, buildWonderButton, readyButton, discardCardButton, useGodPowerButton;

    @FXML
    BorderPane wonder1Pane, wonder2Pane, wonder3Pane;

    @FXML
    private void buildCardButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD);
        playerModel.setCurrentMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void buildWonderButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.UPGRADE_WONDER);
        playerModel.setCurrentMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void discardCardButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.DISCARD_CARD);
        playerModel.setCurrentMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void useGodPowerButtonClicked(MouseEvent event) {
        MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.USE_GOD_POWER);
        playerModel.setCurrentMove(move);
        System.out.println("Score: " + AIMoveGenerator.evaluateMove(move, playerModel, gameplayController.gameModel));
    }

    @FXML
    private void readyButtonClicked(MouseEvent event) {
        if (playerModel.getCurrentMove()!= null) {
            if (MoveController.getInstance().playerCanMakeMove(playerModel.getCurrentMove(), playerModel, new Pair<PlayerModel,PlayerModel>(gameplayController.getLeftPlayer(),gameplayController.getRightPlayer()), false).getKey()){
                currentMove = playerModel.getCurrentMove();
            } else {
                currentMove = new MoveModel(0,0,ACTION_TYPE.DISCARD_CARD);
            }
            gameplayController.getClient().sendMakeMoveRequest( playerModel.getCurrentMove());
            gameplayController.getClient().sendPlayerReadyRequest(true);
        }
    }

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            String type = "";
            if ( currentMove != null)
            {
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
            this.playerModel = playerModel;
            wonder1 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_1.png"));
            wonder2 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_2.png"));
            wonder3 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_3.png"));
            wonder1Pane.setCenter(wonder1);
            wonder2Pane.setCenter(wonder2);
            wonder3Pane.setCenter(wonder3);

            coinLabel.setText(playerModel.getGold() + "");

            if(playerModel.getWonder().getCurrentStageIndex() == 1) {
                wonder1Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
            }
            if(playerModel.getWonder().getCurrentStageIndex() == 2) {
                wonder2Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
            }
            if(playerModel.getWonder().getCurrentStageIndex() == 3) {
                wonder3Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
            }
        });
    }
}
