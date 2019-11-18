package SevenWonders.GameplayUI;
import SevenWonders.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MainMenuController implements Initializable{

    @FXML
    Button playButton;
    @FXML
    Button settingsButton;
    @FXML
    Button tutorialButton;
    @FXML
    Button exitButton;

    @FXML
    public void playButtonPressed( MouseEvent event) {
    }
    @FXML
    public void playButtonReleased( MouseEvent event) {
        SceneManager.getInstance().changeScene("PlayMenu.fxml");
    }
    @FXML
    public void settingsButtonPressed( MouseEvent event) {

    }
    @FXML
    public void settingsButtonReleased( MouseEvent event) {
        SceneManager.getInstance().changeScene("Settings.fxml");
    }
    @FXML
    public void tutorialButtonPressed( MouseEvent event) {

    }
    @FXML
    public void tutorialButtonReleased( MouseEvent event) {
        SceneManager.getInstance().changeScene("Settings.fxml"); //TODO add tutorial
    }
    @FXML
    public void exitButtonPressed( MouseEvent event) {

    }
    @FXML
    public void exitButtonReleased( MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }


    @Override
    public void initialize( URL url, ResourceBundle bundle) {

    }

}
