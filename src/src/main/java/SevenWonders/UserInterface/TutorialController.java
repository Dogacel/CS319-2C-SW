package SevenWonders.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class TutorialController {

    public int currentPageNo;

    public final int totalPageNo = 3;

    @FXML
    Button closeButton;

    @FXML
    Button leftButton;

    @FXML
    Button rightButton;

    @FXML
    GridPane tutorialGrid;

    @FXML
    public void closeButtonClicked( MouseEvent event) {
        tutorialGrid.setVisible(false);
    }

    @FXML
    public void leftButtonClicked( MouseEvent event) {
        currentPageNo--;
        if(currentPageNo == 0){
            leftButton.setVisible(false);
        }
        rightButton.setVisible(true);
    }

    @FXML
    public void rightButtonClicked( MouseEvent event) {
        currentPageNo++;
        if(currentPageNo == totalPageNo){
            rightButton.setVisible(false);
        }
        leftButton.setVisible(true);
    }

    public void updateScene() {
        tutorialGrid.setVisible(true);
        currentPageNo = 0;
        leftButton.setVisible(false);
    }
}
