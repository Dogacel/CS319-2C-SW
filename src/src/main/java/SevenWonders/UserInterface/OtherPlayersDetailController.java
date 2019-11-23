package SevenWonders.UserInterface;

import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OtherPlayersDetailController implements Initializable {

    private PlayerModel playerModel;

    @FXML
    Label moneyLabel, warLabel, godLabel, playerNameLabel;

    @FXML
    Button seeDetailButton;

    public OtherPlayersDetailController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPlayer( PlayerModel player) {
        this.playerModel = player;
    }

    private void updateLabels() {
        moneyLabel.setText(playerModel.getGold() + "");
        warLabel.setText( playerModel.getShields() + "");
        godLabel.setText( ""); //TODO
        playerNameLabel.setText( playerModel.getName());
    }
}
