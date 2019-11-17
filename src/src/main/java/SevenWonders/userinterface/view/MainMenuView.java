package SevenWonders.userinterface.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import SevenWonders.userinterface.model.MainMenuModel;
import javafx.scene.image.ImageView;

public class MainMenuView {
    private Button playGameButton;
    private Button settingsButton;
    private Button tutorialButton;
    private Button exitButton;
    private Image backgroundImage;
    private ImageView barImage;
    private MainMenuModel model;

    public MainMenuView()
    {
        
    }

    public Button getPlayGameButton() {
        return playGameButton;
    }

    public void setPlayGameButton(Button playGameButton) {
        this.playGameButton = playGameButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(Button settingsButton) {
        this.settingsButton = settingsButton;
    }

    public Button getTutorialButton() {
        return tutorialButton;
    }

    public void setTutorialButton(Button tutorialButton) {
        this.tutorialButton = tutorialButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public void setExitButton(Button exitButton) {
        this.exitButton = exitButton;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public ImageView getBarImage() {
        return barImage;
    }

    public void setBarImage(ImageView barImage) {
        this.barImage = barImage;
    }

    public MainMenuModel getModel() {
        return model;
    }

    public void setModel(MainMenuModel model) {
        this.model = model;
    }
}
