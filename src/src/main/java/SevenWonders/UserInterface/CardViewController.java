package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

public class CardViewController implements Initializable {

    GameplayController gameplayController;

    Map<Node, Integer> cardMap;

    private int selectedCardID;

    @FXML
    GridPane gridPane1, gridPane2;

    public CardViewController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardMap = new HashMap<>();
        try {
            updateScene();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        Node source = (Node)e.getSource();
        selectedCardID = cardMap.get(source);
    }

    private void updateScene() throws FileNotFoundException {
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
                node = AssetManager.getInstance().getImage(hand.get(index).getName().toLowerCase() + ".png");
                cardMap.put(node, hand.get(index).getId());
                index++;
            }
            else{
                break;
            }
        }
    }

    public int getSelectedCardID(){
        return selectedCardID;
    }
}
