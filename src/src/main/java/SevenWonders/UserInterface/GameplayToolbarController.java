package SevenWonders.UserInterface;

import SevenWonders.AI.AIMoveGenerator;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GameplayToolbarController {

    PlayerModel playerModel;
    GameplayController gameplayController;
    CardViewController cardViewController;

    @FXML
    Button buildCardButton, buildWonderButton, readyButton, discardCardButton, useGodPowerButton;

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
            gameplayController.getClient().sendMakeMoveRequest( playerModel.getCurrentMove());
            gameplayController.getClient().sendPlayerReadyRequest(true);
        }
    }

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            this.playerModel = playerModel;
        });
    }
}
