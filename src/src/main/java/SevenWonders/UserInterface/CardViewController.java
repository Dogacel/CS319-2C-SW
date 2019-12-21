package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.Network.Server;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.scene.paint.Color;

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

    @FXML
    Button discardPile, exitButton;

    @FXML
    BorderPane leftPane;

    private Vector<Card> hand;

    public CardViewController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void refresh() {
        updateScene(hand);
    }

    public void updateScene(Vector<Card> hand) {
        Platform.runLater(() -> {
            this.hand = hand;
            updateCards(hand);
            updateLeftAndRightPanes();
        });
    }

    private void updateLeftAndRightPanes(){
        leftPane.setStyle("-fx-background-image: url('/images/tokens/age" + gameplayController.gameModel.getCurrentAge() + ".png')");
        ImageView imageView = new ImageView(AssetManager.getInstance().getImage(gameplayController.getPlayer().getWonder().getResource().name().toLowerCase() + ".png"));
        leftPane.setTop(imageView);
    }

    private DropShadow generateCanBuild(Card c) {
        DropShadow canBuild = new DropShadow();
        canBuild.setOffsetY(0f);
        canBuild.setOffsetX(0f);
        canBuild.setWidth(5);
        canBuild.setHeight(5);
        canBuild.setSpread(1);

        MoveModel move = new MoveModel(gameplayController.getPlayer().getId(), c.getId(), ACTION_TYPE.BUILD_CARD);
        for (TradeAction trade : gameplayController.constructionZoneController.trades) {
            move.addTrade(trade);
        }

        if(MoveController.getInstance().playerCanMakeMove(move, gameplayController.getPlayer(), null, false).getKey()){
            canBuild.setColor(Color.LIGHTGREEN);
        }
        else{
            canBuild.setColor(Color.RED);
        }

        return canBuild;
    }

    private void updateCards(Vector<Card> hand){
        cardBox.getChildren().clear();
        int depth1 = 100;
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GOLD);
        borderGlow.setWidth(depth1);
        borderGlow.setHeight(depth1);

        for (int i = 0 ; i < hand.size() ; i++) {
            if (hand.get(i) != null) {
                ImageView imageView = new ImageView(
                        AssetManager.getInstance().getImage(hand.get(i).getName().replaceAll(" ", "").toLowerCase() + ".png")
                );
                imageView.setScaleX(0.95);
                imageView.setScaleY(0.95);
                Card c = hand.get(i);
                imageView.setEffect(generateCanBuild(c));

                imageView.setOnMouseEntered( (e) -> {
                    imageView.setScaleX(1);
                    imageView.setScaleY(1);
                });

                imageView.setOnMouseExited( (e) -> {
                    imageView.setScaleX(0.95);
                    imageView.setScaleY(0.95);
                });

                imageView.setOnMouseClicked((e) ->  {
                    if (focusedView != imageView) {
                        if(focusedView != null) {
                            if (selectedCard != null) {
                                focusedView.setEffect(generateCanBuild(selectedCard));
                            }
                            selectedCard = c;
                            focusedView.setScaleX(0.95);
                            focusedView.setScaleY(0.95);
                        }

                        selectedCard = c;
                        focusedView = imageView;
                        imageView.setScaleX(1);
                        imageView.setScaleY(1);
                        focusedView.setEffect(borderGlow);
                    }
                    else {
                        if(imageView.getScaleX() == 1 && imageView.getScaleY() == 1) {
                            imageView.setScaleX(0.95);
                            imageView.setScaleY(0.95);
                            imageView.setEffect(generateCanBuild(c));
                            selectedCard = null;
                        }
                        else{
                            imageView.setScaleX(1);
                            imageView.setScaleY(1);
                            imageView.setEffect(borderGlow);
                            selectedCard = c;
                        }
                    }});
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
    }

    public Card getSelectedCard(){
        return selectedCard;
    }

    @FXML
    public void exitButtonClicked(MouseEvent e) {
        Server.stopServerInstance();
        gameplayController.getClient().disconnect();
        SceneManager.getInstance().changeScene("MainMenu.fxml");
    }
}