package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.GameLogic.ScoreController;
import SevenWonders.Network.Requests.LobbyUpdateRequest;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreViewController implements Initializable {

   GameplayController gameplayController;

    @FXML
    private VBox scoreBox;

    @FXML
    public Button returnMenuButton, exitButton;

    public ScoreViewController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater( () -> {
            if(gameplayController == null){
                    System.out.println("controller null");
            }
            if(gameplayController.gameModel == null){
                System.out.println("model is nulll");
            }
            PlayerModel[] allPlayers = gameplayController.gameModel.getPlayerList();

            for ( PlayerModel player: allPlayers) {
                Parent root = AssetManager.getInstance().getSceneByNameForce("ScorePlayerGrid.fxml");
                scoreBox.getChildren().add(root);

                Label militaryPoints = (Label) root.lookup("#militaryPoints");

                militaryPoints.setText(ScoreController.calculateMilitaryConflicts(player) + "");
            }
        });
    }

    public void exitButtonClicked(javafx.scene.input.MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void returnMenuButtonClicked(javafx.scene.input.MouseEvent mouseEvent) {
        SceneManager.getInstance().changeScene("MainMenu.fxml");
    }
}
