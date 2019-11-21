package SevenWonders.GameplayUI;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

public class CardViewController implements Initializable {

    public CardViewModel model;

    GameplayController gameplayController;

    Map<Node, Integer> cardMap;

    @FXML
    GridPane gridPane1, gridPane2;

    public CardViewController(){
        this.model = new CardViewModel();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardMap = new HashMap<>();
        try {
            updateHand();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        Node source = (Node)e.getSource();
        int a = cardMap.get(source);
    }

    private void updateHand() throws FileNotFoundException {
        Vector<Card> hand = gameplayController.getPlayer().getHand();
        int index = 0;
        for (Node node : gridPane1.getChildren()) {
            if( hand.get(index) != null) {
                node = AssetManager.getInstance().getImage(hand.get(index).getName().toLowerCase());
                cardMap.put(node, hand.get(index).getId());
                index++;
            }
            else{
                break;
            }
        }

        for (Node node : gridPane2.getChildren()) {
            if( hand.get(index) != null) {
                node = AssetManager.getInstance().getImage(hand.get(index).getName().toLowerCase());
                cardMap.put(node, hand.get(index).getId());
                index++;
            }
            else{
                break;
            }
        }
    }

}
