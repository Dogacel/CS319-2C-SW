package SevenWonders.UserInterface;

import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MainMenuController {

    @FXML
    Button playButton, settingsButton, tutorialButton, exitButton;

    @FXML
    public void playButtonClicked( MouseEvent event) {
        SceneManager.getInstance().changeScene("PlayMenu.fxml");
    }

    public void settingsButtonClicked( MouseEvent event) {
        SceneManager.getInstance().changeScene("Settings.fxml");
    }

    @FXML
    public void tutorialButtonClicked( MouseEvent event) {
        SceneManager.getInstance().changeScene("Settings.fxml"); //TODO add tutorial
    }

    @FXML
    public void exitButtonClicked( MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
