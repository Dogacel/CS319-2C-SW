package SevenWonders.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class TutorialController {

    public int currentPageNo;

    public final int totalPageNo = 2;

    Image img;
    //img = new Image("images/cards/age1.png");
    @FXML
    Button closeButton;

    @FXML
    Button leftButton;

    @FXML
    Button rightButton;

    @FXML
    GridPane tutorialGrid;

    @FXML
    Label tutorialTextLabel;

    @FXML
    ImageView tutorialImage;

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
        tutorialTextLabel.setText("Tutorial page " + (currentPageNo + 1));
        img = new Image("images/cards/age" + (currentPageNo + 1) + ".png");
        tutorialImage.setImage(img);
    }

    @FXML
    public void rightButtonClicked( MouseEvent event) {
        currentPageNo++;
        if(currentPageNo == totalPageNo){
            rightButton.setVisible(false);
        }
        leftButton.setVisible(true);
        tutorialTextLabel.setText("Tutorial page " + (currentPageNo + 1));
        img = new Image("images/cards/age" + (currentPageNo + 1) + ".png");
        tutorialImage.setImage(img);

    }

    public void updateScene() {
        tutorialGrid.setVisible(true);
        currentPageNo = 0;
        leftButton.setVisible(false);
        tutorialTextLabel.setText("Tutorial page " + (currentPageNo + 1));
        img = new Image("images/cards/age1.png");
        tutorialImage.setImage(img);
    }
}
