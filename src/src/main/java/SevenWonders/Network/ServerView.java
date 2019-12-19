package SevenWonders.Network;

import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Vector;

public class ServerView {
    @FXML
    VBox clientsVBox;

    public void update(GameModel game) {
        Platform.runLater(() -> {
            clientsVBox.getChildren().clear();
            for (PlayerModel player : game.getPlayerList()) {
                clientsVBox.getChildren().add(new Label(player.getName() + ":" + player.getGold() + " ==> Move: " + (player.getCurrentMove() != null ? player.getCurrentMove().toString() : "NO MOVE")));
            }
        });
    }
}
