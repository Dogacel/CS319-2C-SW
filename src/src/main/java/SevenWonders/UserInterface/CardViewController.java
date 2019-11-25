package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class CardViewController implements Initializable {

    GameplayController gameplayController;

    private Card selectedCard;

    @FXML
    HBox hbox1, hbox2;

    public CardViewController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void mouseClicked(MouseEvent e) {
        Node source = (Node) e.getTarget();;
        // TODO: Add listener
    }

    public void updateScene(Vector<Card> hand) {
        Platform.runLater(() -> {
            for (int i = 0 ; i < 4 ; i++) {
                if (hand.get(i) != null) {
                    ImageView imageView = new ImageView(
                        AssetManager.getInstance().getImage(hand.get(i).getName().replaceAll(" ", "").toLowerCase() + ".png")
                    );
                    Card c = hand.get(i);
                    imageView.setOnMouseClicked((e) ->  {
                        selectedCard = c;
                    });
                    hbox1.getChildren().add(imageView);
                }
            }

            for (int i = 4 ; i < 7 ; i++) {
                if (hand.get(i) != null) {
                    ImageView imageView = new ImageView(
                        AssetManager.getInstance().getImage(hand.get(i).getName().replaceAll(" ", "").toLowerCase() + ".png")
                    );
                    Card c = hand.get(i);
                    imageView.setOnMouseClicked((e) ->  {
                        selectedCard = c;
                    });
                    hbox2.getChildren().add(imageView);
                }
            }
        });
    }

    public Card getSelectedCard(){
        return selectedCard;
    }
}
