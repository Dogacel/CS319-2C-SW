package SevenWonders.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SettingsController {

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
}
