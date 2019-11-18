package SevenWonders.GameplayUI;
import SevenWonders.GameLogic.GameController;
import SevenWonders.GameLogic.PlayerModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    PlayerModel playerModel;

    GameplayController gameplayController;

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

    @FXML
    Button settingsButton;

    @FXML
    Button tutorialButton;

    @FXML
    Button exitButton;

    @FXML
    AnchorPane playerAnchor;

    @FXML
    GridPane brownAndGray;

    @FXML
    GridPane green;

    @FXML
    GridPane blue;

    @FXML
    GridPane purple;

    @FXML
    GridPane red;

    @FXML
    GridPane yellow;

    public PlayerController(){
        this.playerModel = gameplayController.getPlayer();
    }

    @FXML
    private void buildCardButtonPressed(MouseEvent event) {
        buildCardButton.setStyle("-fx-background-image: url('/ui-images/buildCardButtonClicked.png')");
    }

    @FXML
    private void buildCardButtonReleased(MouseEvent event) {
        buildCardButton.setStyle("-fx-background-image: url('/ui-images/buildCardButton.png')");
    }

    @FXML
    private void buildWonderButtonPressed(MouseEvent event) {
        buildWonderButton.setStyle("-fx-background-image: url('/ui-images/buildWonderButtonClicked.png')");
    }

    @FXML
    private void buildWonderButtonReleased(MouseEvent event) {
        buildWonderButton.setStyle("-fx-background-image: url('/ui-images/buildWonderButton.png')");
    }

    @FXML
    private void discardButtonPressed(MouseEvent event) {
        discardButton.setStyle("-fx-background-image: url('/ui-images/discardCardButtonClicked.png')");
    }

    @FXML
    private void discardButtonReleased(MouseEvent event) {
        discardButton.setStyle("-fx-background-image: url('/ui-images/discardCardButton.png')");
    }

    @FXML
    private void godPowerButtonPressed(MouseEvent event) {
        godPowerButton.setStyle("-fx-background-image: url('/ui-images/godPowerButtonClicked.png')");
    }

    @FXML
    private void godPowerButtonReleased(MouseEvent event) {
        godPowerButton.setStyle("-fx-background-image: url('/ui-images/godPowerButton.png')");
    }

    @FXML
    private void settingsButtonPressed(MouseEvent event) {
        settingsButton.setStyle("-fx-background-image: url('/ui-images/settingsButtonClicked.png')");
    }

    @FXML
    private void settingsButtonReleased(MouseEvent event) {
        settingsButton.setStyle("-fx-background-image: url('/ui-images/settingsButton.png')");
    }

    @FXML
    private void tutorialButtonPressed(MouseEvent event) {
        tutorialButton.setStyle("-fx-background-image: url('/ui-images/tutorialButtonClicked.png')");
    }

    @FXML
    private void tutorialButtonReleased(MouseEvent event) {
        tutorialButton.setStyle("-fx-background-image: url('/ui-images/tutorialButton.png')");
    }

    @FXML
    private void exitButtonPressed(MouseEvent event) {
        exitButton.setStyle("-fx-background-image: url('/ui-images/exitButtonClicked.png')");
    }

    @FXML
    private void exitButtonReleased(MouseEvent event) {
        exitButton.setStyle("-fx-background-image: url('/ui-images/exitButton.png')");
    }

    @FXML
    private void readyButtonPressed(MouseEvent event) {
        readyButton.setStyle("-fx-background-image: url('/ui-images/tokens/readyClicked.png')");
    }

    @FXML
    private void readyButtonReleased(MouseEvent event) {
        readyButton.setStyle("-fx-background-image: url('/ui-images/tokens/ready.png')");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createScene();
    }

    private void createScene(){
        String wonder = playerModel.getWonder().toString().toLowerCase();

        this.playerAnchor.setStyle("-fx-background-image: url('/ui-images/'" + wonder + "'.png')");
        
    }
}
