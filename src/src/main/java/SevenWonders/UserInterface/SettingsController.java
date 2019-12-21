package SevenWonders.UserInterface;

import SevenWonders.SoundManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SettingsController {

    public static boolean autoTrade = false;

    @FXML
    public Slider gameSlider, musicSlider;

    @FXML
    public CheckBox muteButton, tradeButton;

    @FXML
    Button closeButton;

    @FXML
    AnchorPane settingsAnchor;

    @FXML
    public void closeButtonClicked( MouseEvent event) {
        settingsAnchor.setVisible(false);
    }

    @FXML
    public void updateScene() {
        settingsAnchor.setVisible(true);
    }

    public void onAutoTrade(MouseEvent mouseEvent) {
        autoTrade = tradeButton.isSelected();
        System.out.println(autoTrade);
    }

    public void onMute(MouseEvent mouseEvent) {
        SoundManager.mute = muteButton.isSelected() ? 1 : 0;
        SoundManager.currentMusic.setVolume(1 - SoundManager.mute);
    }

    public void setMusicVolume(MouseEvent dragEvent) {
        SoundManager.MusicSoundLevel = musicSlider.getValue() / musicSlider.getMax();
        SoundManager.currentMusic.setVolume(SoundManager.MusicSoundLevel - SoundManager.mute);
    }

    public void setGameVolume(MouseEvent dragEvent) {
        SoundManager.GameSoundLevel = gameSlider.getValue() / musicSlider.getMax();
    }
}
