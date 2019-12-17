package SevenWonders.UserInterface;

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
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD));
    }

    @FXML
    private void buildWonderButtonClicked(MouseEvent event) {
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.UPGRADE_WONDER));
    }

    @FXML
    private void discardCardButtonClicked(MouseEvent event) {
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.DISCARD_CARD));
    }

    @FXML
    private void useGodPowerButtonClicked(MouseEvent event) {
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.USE_GOD_POWER));
    }

    @FXML
    private void readyButtonClicked(MouseEvent event) {
        if (playerModel.getCurrentMove()!= null) {
            System.out.println(playerModel.getCurrentMove().toString());
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
