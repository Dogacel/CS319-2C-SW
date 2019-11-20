package SevenWonders.GameplayUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CardViewController implements Initializable {

    public CardViewModel model;

    @FXML
    GridPane gridPane1, gridPane2;

    public CardViewController(){
        this.model = new CardViewModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
