package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.Map;
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
            cardBox.getChildren().clear();
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

                    Tooltip tp = new Tooltip();
                    String buildings = "";
                    for (String s : c.getBuildingChain()) {
                        buildings += s + ",";
                    }
                    if (buildings != "")
                        buildings = buildings.substring(0, buildings.length() - 1);
                    String requirements = "";
                    for (var entry : c.getRequirements().entrySet()) {
                        requirements += entry.getKey().name() + ": " + entry.getValue() + "\n";
                    }
                    tp.setText(c.getName() + ((buildings).equals("") ? "" : "\nChain: ") + buildings + ((requirements).equals("") ? "" : "\nRequirements:\n" + requirements));
                    tp.setShowDelay(new Duration(250));
                    Tooltip.install(imageView, tp);
                }
            }
        });
    }

    public Card getSelectedCard(){
        return selectedCard;
    }
}
