package SevenWonders.GameplayUI;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class OtherPlayersController {
    OtherPlayersModel model;
    GameplayController gameplayController;

    @FXML
    ImageView ageIcon;

    @FXML
    Button player3Button;

    @FXML
    Button player4Button;

    @FXML
    Button player5Button;

    @FXML
    Button player6Button;

    public OtherPlayersController(){
        this.model = new OtherPlayersModel();
    }
/*
    private void setClickedStyle(Button button, String imageName){
        button.setStyle("-fx-background-image: url('/ui-images/otherPlayer_' + )");
    }*/

    @FXML
    private void player3ButtonClicked(MouseEvent event) {
       // player3Button.setStyle("-fx-background-image: url('/ui-images/other')");
        gameplayController.player3ButtonClicked();
    }

    @FXML
    private void player4ButtonClicked(MouseEvent event) {
        //player4Button.setStyle("-fx-background-image: url('/ui-images/')");
        gameplayController.player4ButtonClicked();
    }

    @FXML
    private void player5ButtonClicked(MouseEvent event) {
        //player5Button.setStyle("-fx-background-image: url('/ui-images/')");
        gameplayController.player5ButtonClicked();
    }

    @FXML
    private void player6ButtonClicked(MouseEvent event) {
        //player6Button.setStyle("-fx-background-image: url('/ui-images/')");
        gameplayController.player6ButtonClicked();
    }

    public void changeAgeIcon( int age){
        if( age == 1){
            ageIcon.setImage( new Image("/ui-images/tokens/age1.png"));
        }
        else if( age == 2){
            ageIcon.setImage( new Image("/ui-images/tokens/age2.png"));
        }
        else{
            ageIcon.setImage( new Image("/ui-images/tokens/age3.png"));
        }
    }
}
