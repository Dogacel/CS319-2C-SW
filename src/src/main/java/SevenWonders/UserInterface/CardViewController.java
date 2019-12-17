package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class CardViewController implements Initializable {

    GameplayController gameplayController;

    private Card selectedCard;
    private ImageView focusedView;

    @FXML
    HBox cardBox;

    public CardViewController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void updateScene(Vector<Card> hand) {
        Platform.runLater(() -> {
            hbox1.getChildren().clear();
            hbox2.getChildren().clear();
            for (int i = 0 ; i < hand.size() ; i++) {
                if (hand.get(i) != null) {
                    ImageView imageView = new ImageView(
                        AssetManager.getInstance().getImage(hand.get(i).getName().replaceAll(" ", "").toLowerCase() + ".png")
                    );
                    imageView.setScaleX(0.95);
                    imageView.setScaleY(0.95);
                    Card c = hand.get(i);
                    imageView.setOnMouseClicked((e) ->  {
                        if (focusedView != null) {
                            focusedView.setScaleX(0.95);
                            focusedView.setScaleY(0.95);
                        }
                        if (focusedView != imageView) {
                            selectedCard = c;
                            focusedView = imageView;
                            imageView.setScaleX(1);
                            imageView.setScaleY(1);
                        }
                    });
                    cardBox.getChildren().add(imageView);
                }
            }
        });
    }

    public Card getSelectedCard(){
        return selectedCard;
    }
}
