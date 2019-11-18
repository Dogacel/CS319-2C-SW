package SevenWonders.GameplayUI;
import SevenWonders.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
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
    public void playButtonPressed( ActionEvent event) {
    }
    @FXML
    public void playButtonReleased( ActionEvent event) {
        SceneManager.getInstance().changeScene("PlayMenu.fxml");
    }
    @FXML
    public void settingsButtonPressed( ActionEvent event) {

    }
    @FXML
    public void settingsButtonReleased( ActionEvent event) {
        SceneManager.getInstance().changeScene("Settings.fxml");
    }
    @FXML
    public void tutorialButtonPressed( ActionEvent event) {

    }
    @FXML
    public void tutorialButtonReleased( ActionEvent event) {
        SceneManager.getInstance().changeScene("Settings.fxml"); //TODO add tutorial
    }
    @FXML
    public void exitButtonPressed( ActionEvent event) {

    }
    @FXML
    public void exitButtonReleased( ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }


    @Override
    public void initialize( URL url, ResourceBundle bundle) {

    }

}
