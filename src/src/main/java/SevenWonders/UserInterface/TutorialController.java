package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class TutorialController implements Initializable {

    public int currentPageNo;

    public final int totalPageNo = 4;

    Map<Integer, Image> tutorialImages = new HashMap<>();
    Map<Integer, String> tutorialTexts = new HashMap<>();

    Image img;
    //img = new Image("images/cards/age1.png");
    @FXML
    Button closeButton, leftButton, rightButton;

    @FXML
    Pane tutorialPane, frontPane, backPane;

    @FXML
    TextArea tutorialText;

    @FXML
    ImageView tutorialImage;

    @FXML
    public void closeButtonClicked( MouseEvent event) {
        tutorialImage.setScaleY(1);
        tutorialImage.setScaleX(1);
        tutorialImage.setLayoutY(-5);
        tutorialImage.setLayoutY(0);
        tutorialPane.setVisible(false);
    }

    @FXML
    public void leftButtonClicked( MouseEvent event) {
        currentPageNo--;
        if(currentPageNo == 0){
            leftButton.setVisible(false);
        }
        rightButton.setVisible(true);
        tutorialText.setText(tutorialTexts.get(currentPageNo));
        tutorialImage.setScaleY(1);
        tutorialImage.setScaleX(1);
        tutorialImage.setLayoutX(-5);
        tutorialImage.setLayoutY(0);
        tutorialImage.setImage(tutorialImages.get(currentPageNo));

    }

    @FXML
    public void rightButtonClicked( MouseEvent event) {
        currentPageNo++;
        if(currentPageNo == totalPageNo){
            rightButton.setVisible(false);
        }
        leftButton.setVisible(true);
        tutorialText.setText(tutorialTexts.get(currentPageNo));
        tutorialImage.setScaleY(1);
        tutorialImage.setScaleX(1);
        tutorialImage.setLayoutX(-5);
        tutorialImage.setLayoutY(0);
        tutorialImage.setImage(tutorialImages.get(currentPageNo));
    }

    public void initialize(URL url, ResourceBundle rb){
        for ( int i  = 1; i < 3; i++) {
            tutorialImages.put(i, AssetManager.getInstance().getImage("tutorial" + i + ".png"));
            tutorialTexts.put(i, AssetManager.getInstance().readTextFromFile("build/resources/main/tutorialTexts/tutorial" + i +".txt"));
        }
        //tutorialTexts
    }

    public void updateScene() {
        tutorialPane.setVisible(true);
        currentPageNo = 1;
        leftButton.setVisible(false);
        tutorialText.setText(AssetManager.getInstance().readTextFromFile("build/resources/main/tutorialTexts/tutorial1.txt"));
        tutorialImage.setImage(tutorialImages.get(currentPageNo));
        tutorialImage.setLayoutX(20);
    }

    @FXML
    public void tutorialImageClicked( MouseEvent e) {
        frontPane.toFront();
        backPane.toBack();
        if(tutorialImage.getScaleX() == 1 && tutorialImage.getScaleY() == 1) {
            tutorialImage.setScaleY(600.0/400);
            tutorialImage.setScaleX(600.0/400);
            tutorialImage.setLayoutX(-10);
            tutorialImage.setLayoutY(120);
        }
        else {
            tutorialImage.setScaleY(1);
            tutorialImage.setScaleX(1);
            tutorialImage.setLayoutX(-5);
            tutorialImage.setLayoutY(0);
        }
    }
}
