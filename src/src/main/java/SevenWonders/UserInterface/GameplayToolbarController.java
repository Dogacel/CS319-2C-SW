package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class GameplayToolbarController {

    PlayerModel playerModel;
    GameplayController gameplayController;
    CardViewController cardViewController;

    ImageView wonder1, wonder2, wonder3;

    @FXML
    Button buildCardButton, buildWonderButton, readyButton, discardCardButton, useGodPowerButton;

    @FXML
    BorderPane wonder1Pane, wonder2Pane, wonder3Pane;

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
            gameplayController.getClient().sendMakeMoveRequest( playerModel.getCurrentMove());
            gameplayController.getClient().sendPlayerReadyRequest(true);
        }
    }

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            this.playerModel = playerModel;
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
        });
    }
}
