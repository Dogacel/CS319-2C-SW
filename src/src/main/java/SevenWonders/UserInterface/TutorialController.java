package SevenWonders.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TutorialController {
    @FXML
    Button closeButton;

    @FXML
    AnchorPane tutorialAnchor;

    @FXML
    public void closeButtonClicked( MouseEvent event) {
        tutorialAnchor.setVisible(false);
    }

    public void updateScene() {
        tutorialAnchor.setVisible(true);
    }
}
