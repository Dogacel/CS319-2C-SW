package SevenWonders.GameplayUI;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OtherPlayersController {
    OtherPlayersModel model;

    @FXML
    ImageView ageIcon;

    public OtherPlayersController(){
        this.model = new OtherPlayersModel();
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
