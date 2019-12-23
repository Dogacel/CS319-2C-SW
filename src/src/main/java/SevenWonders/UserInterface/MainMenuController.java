package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.Network.Client;
import SevenWonders.SceneManager;
import SevenWonders.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    SettingsController settingsController;
    TutorialController tutorialController;

    Pair<Parent, Object> pair;

    @FXML
    Button playButton, settingsButton, tutorialButton, exitButton;

    @FXML
    Pane settingsPane, tutorialPane;

    public MainMenuController() {
        SoundManager.getInstance().playMenuMusic();
    }


    @FXML
    public void playButtonClicked( MouseEvent event) {
        SceneManager.getInstance().changeScene("PlayMenuView.fxml");
    }

    @FXML
    public void settingsButtonClicked( MouseEvent event) {
        tutorialPane.setVisible(false);
        settingsPane.setVisible(true);
        settingsController.updateScene();
    }


    @FXML
    public void tutorialButtonClicked( MouseEvent event) {
        settingsPane.setVisible(false);
        tutorialController.updateScene();
        tutorialPane.setVisible(true);
    }

    @FXML
    public void exitButtonClicked( MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pair = AssetManager.getInstance().getSceneAndController("SettingsView.fxml");
        Parent settings =  pair.getKey();
        settingsController = (SettingsController) pair.getValue();
        this.settingsPane.getChildren().add(settings);

        pair = AssetManager.getInstance().getSceneAndController("TutorialView.fxml");
        Parent tutorial =  pair.getKey();
        tutorialController = (TutorialController) pair.getValue();
        this.tutorialPane.getChildren().add(tutorial);
    }
}
