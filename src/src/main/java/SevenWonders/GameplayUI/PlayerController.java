package SevenWonders.GameplayUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {
    public PlayerModel model;

    @FXML
    Button readyButton;

    @FXML
    Button buildCardButton;

    @FXML
    Button buildWonderButton;

    @FXML
    Button discardButton;

    @FXML
    Button godPowerButton;


    public PlayerController(){
        this.model = new PlayerModel();
    }

    @FXML
    private void readyButtonClicked(ActionEvent event) {
        readyButton.setStyle("-fx-background-image: url('/ui-images/tokens/readyClicked.png')");
    }

    @FXML
    private void buildCardButtonClicked(ActionEvent event) {
        //buildCardButton.setStyle("");
    }

    @FXML
    private void buildWonderButtonClicked(ActionEvent event) {
        //buildWonderButton.setStyle("");
    }

    @FXML
    private void discardButtonClicked(ActionEvent event) {
        //discardButton.setStyle("");
    }

    @FXML
    private void godPowerButtonClicked(ActionEvent event) {
        //godPowerButton.setStyle("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readyButton.setOnAction(this::readyButtonClicked);
    }
}
