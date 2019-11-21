package SevenWonders.UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class OtherPlayersController {
    GameplayController gameplayController;

    @FXML
    ImageView ageIcon;

    @FXML
    Button player3Button, player4Button, player5Button, player6Button;

    public OtherPlayersController(){
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
