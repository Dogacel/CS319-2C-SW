package SevenWonders.UserInterface;

import SevenWonders.GameLogic.PlayerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class NeighborController {

    PlayerModel neıghbor;

    @FXML
    ImageView neighborIcon;

    @FXML
    Label neighborName;

    @FXML
    GridPane resourcesGrid;

    public NeighborController() {

        updateScene();
    }

    public void updateScene(){

    }

}
