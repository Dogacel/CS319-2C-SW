package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;


public class ConstructionZoneController {
    GameplayController gameplayController;
    Map<Card, RESOURCE_TYPE> trades;
    ImageView focusedView;

    private Card selectedCard;

    @FXML
    VBox brown, gray, blue, green, red, yellowAndPurple, leftNeighborConstructionPane, rightNeighborConstructionPane;

    @FXML
    GridPane constructionGrid;

    VBox brownNeighbor, grayNeighbor, blueNeighbor, greenNeighbor, yellowNeighbor, purpleNeighbor;

    public ConstructionZoneController() {}

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            trades = new HashMap<>();
            updatePlayerConstruction(playerModel);
            updateNeighborConstruction(gameplayController.getLeftPlayer(), leftNeighborConstructionPane);
            updateNeighborConstruction(gameplayController.getRightPlayer(), rightNeighborConstructionPane);
        });
    }

    private void updatePlayerConstruction(PlayerModel playerModel){
        brown.getChildren().clear();
        gray.getChildren().clear();
        blue.getChildren().clear();
        green.getChildren().clear();
        red.getChildren().clear();
        yellowAndPurple.getChildren().clear();

        for(Card card: playerModel.getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();
            ImageView imageView = new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png"));
            imageView.setOnMouseClicked((e) -> {
                if (focusedView != imageView) {
                    selectedCard = card;
                    focusedView = imageView;
                    imageView.setScaleX(1.5);
                    imageView.setScaleY(1.5);
                }
            });

            if(color == CARD_COLOR_TYPE.BROWN)
                brown.getChildren().add(imageView);
            else if(color == CARD_COLOR_TYPE.GRAY)
                gray.getChildren().add(imageView);
            else if(color == CARD_COLOR_TYPE.BLUE)
                blue.getChildren().add(imageView);
            else if(color == CARD_COLOR_TYPE.GREEN)
                green.getChildren().add(imageView);
            else if(color == CARD_COLOR_TYPE.RED)
                red.getChildren().add(imageView);
            else if( (color == CARD_COLOR_TYPE.YELLOW) || (color == CARD_COLOR_TYPE.PURPLE))
                yellowAndPurple.getChildren().add(imageView);
        }
    }

    private void updateNeighborConstruction(PlayerModel playerModel, Pane pane){
        pane.getChildren().clear();
        Parent root = AssetManager.getInstance().getSceneByName("NeighborConstructionView.fxml");
        pane.getChildren().add(root);
        brownNeighbor = (VBox) root.lookup("#brown");
        grayNeighbor = (VBox) root.lookup("#gray");
        blueNeighbor = (VBox) root.lookup("#blue");
        greenNeighbor = (VBox) root.lookup("#green");
        yellowNeighbor = (VBox) root.lookup("#yellow");
        purpleNeighbor = (VBox) root.lookup("#purple");

        for(Card card: playerModel.getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();
            ToggleButton button = new ToggleButton();
            AtomicInteger clickCount = new AtomicInteger();
            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(Color.TRANSPARENT);
            borderGlow.setWidth(20);
            borderGlow.setHeight(20);
            button.setEffect(borderGlow);
            borderGlow.setSpread(0.5);

            button.getStylesheets().add(getClass().getResource("/css/ConstructionZone.css").toExternalForm());
            button.setStyle(button.getStyle() + "-fx-background-image: url('/images/cards/" + card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png' );");

            if( (card.getColor() == CARD_COLOR_TYPE.GRAY) || (card.getColor() == CARD_COLOR_TYPE.BROWN))
            {
                button.setOnAction((e) -> {
                    clickCount.getAndIncrement();
                    selectedCard = card;
                    Map.Entry<RESOURCE_TYPE, Integer> entry = selectedCard.getCardEffect().getResources().entrySet().iterator().next();
                   // RESOURCE_TYPE tradeResource = entry.getKey();
                    if(selectedCard.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_RAW_MATERIAL || selectedCard.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_MANUFACTURED_GOODS){
                            if(entry.getValue() == 1){
                                if( clickCount.get() % 2 == 1) {
                                    borderGlow.setColor(Color.DARKORANGE);
                                }
                                else{
                                    borderGlow.setColor(Color.TRANSPARENT);
                                }
                            }
                            else {
                                borderGlow.setColor(Color.DARKORANGE);
                                if(clickCount.get() % 3 == 1) {
                                    borderGlow.setOffsetX(-5);
                                }
                                else if(clickCount.get() % 3 == 2) {
                                    borderGlow.setOffsetX(0);
                                }
                                else {
                                    borderGlow.setColor(Color.TRANSPARENT);
                                    borderGlow.setOffsetX(0);
                                }
                            }

                    }
                    else if( card.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_ONE_OF_TWO) {
                        borderGlow.setColor(Color.DARKORANGE);
                        if(clickCount.get() % 3 == 1) {
                            borderGlow.setOffsetX(-5);
                        }
                        else if(clickCount.get() % 3 == 2) {
                            borderGlow.setOffsetX(5);
                        }
                        else {
                            borderGlow.setColor(Color.TRANSPARENT);
                            borderGlow.setOffsetX(0);
                        }
                    }
                });
            }

            if(color == CARD_COLOR_TYPE.BROWN)
                brownNeighbor.getChildren().add(button);
            else if(color == CARD_COLOR_TYPE.GRAY)
                grayNeighbor.getChildren().add(button);
            else if(color == CARD_COLOR_TYPE.BLUE)
                blueNeighbor.getChildren().add(button);
            else if(color == CARD_COLOR_TYPE.GREEN)
                greenNeighbor.getChildren().add(button);
            else if(color == CARD_COLOR_TYPE.PURPLE)
                purpleNeighbor.getChildren().add(button);
            else if( color == CARD_COLOR_TYPE.YELLOW)
                yellowNeighbor.getChildren().add(button);
        }
    }
}
