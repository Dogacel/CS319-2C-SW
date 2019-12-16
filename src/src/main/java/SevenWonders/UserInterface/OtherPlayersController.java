package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OtherPlayersController implements Initializable {

    public GameplayController gameplayController;

    @FXML
    private GridPane otherPlayersPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void updateView(){
        PlayerModel[] allPlayers = gameplayController.gameModel.getPlayerList();
        for(PlayerModel player: allPlayers){  //Bizi içermesin amin
            Parent root = AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");
            Label moneyLabel = (Label) root.lookup("#moneyLabel");
            Label warLabel = (Label) root.lookup("#warLabel");
            Label godLabel = (Label) root.lookup("#godLabel");
            Label playerNameLabel = (Label) root.lookup("#playerNameLabel");
            Button closeButton = (Button) root.lookup("#closeButton"); //Buna da listener ekleriz

            moneyLabel.setText("" + player.getGold());
            warLabel.setText("" + player.getShields());
            godLabel.setText("" + player.getWonder().getGod().getGodType());    //Player's god status should be accessed
            playerNameLabel.setText(player.getName());
            otherPlayersPane.getChildren().add(root);
        }
    }
}
