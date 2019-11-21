package SevenWonders.UserInterface;

import SevenWonders.GameLogic.PlayerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class NeighborController {

    PlayerModel neighbor;

    @FXML
    ImageView neighborIcon;

    @FXML
    Label neighborName;

    @FXML
    GridPane resourcesGrid;

    public NeighborController() {
    }

    public void updateScene(){

    }

    public void setNeighbor( PlayerModel neighbor){
        this.neighbor = neighbor;
    }
}
