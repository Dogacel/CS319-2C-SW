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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;


public class ConstructionZoneController {
    GameplayController gameplayController;
    public Vector<TradeAction> trades = new Vector<TradeAction>();

    private Card selectedCard;

    @FXML
    VBox brown, gray, blue, green, red, yellowAndPurple, leftNeighborConstructionPane, rightNeighborConstructionPane;

    @FXML
    GridPane constructionGrid;

    VBox brownNeighbor, grayNeighbor, blueNeighbor, greenNeighbor, yellowNeighbor, purpleNeighbor;

    public ConstructionZoneController() {}

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            constructionGrid.setEffect(new DropShadow(2d, 8d, -10d, Color.rgb(0,0,0,0.6)));

            trades = new Vector<>();
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

            Tooltip tp = new Tooltip();
            tp.setText(card.getName());
            tp.setShowDelay(new Duration(250));
            Tooltip.install(imageView, tp);

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

        playerModel.getConstructionZone().getConstructedCards().sort(Comparator.comparingInt(card -> card.getColor().ordinal()));

        int count = 0;
        HBox hbox = new HBox();
        hbox.setSpacing(2);
        pane.getChildren().add(hbox);
        Card lastCard = playerModel.getConstructionZone().getConstructedCards().size() > 0 ? playerModel.getConstructionZone().getConstructedCards().get(0) : null;

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
            button.setStyle(button.getStyle() + "-fx-background-image: url('/images/cards/" + card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png' );");

            if( (card.getColor() == CARD_COLOR_TYPE.GRAY) || (card.getColor() == CARD_COLOR_TYPE.BROWN) && !SettingsController.autoTrade)
            {
                button.setOnAction((e) -> {
                    gameplayController.cardViewController.refresh();
                    clickCount.getAndIncrement();
                    selectedCard = card;
                    var iter = selectedCard.getCardEffect().getResources().entrySet().iterator();
                    Map.Entry<RESOURCE_TYPE, Integer> entry = iter.next();

                    if(selectedCard.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_RAW_MATERIAL || selectedCard.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_MANUFACTURED_GOODS){
                            if(entry.getValue() == 1){
                                if( clickCount.get() % 2 == 1) {
                                    trades.add(new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey()));
                                    borderGlow.setColor(Color.DARKORANGE);
                                }
                                else{
                                    TradeAction trade2 = new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey());
                                    for (int i = 0 ; i < trades.size() ; i++) {
                                        if(trades.get(i).equals(trade2)) {
                                            trades.remove(i);
                                            break;
                                        }
                                    }
                                    borderGlow.setColor(Color.TRANSPARENT);
                                }
                            }
                            else {
                                borderGlow.setColor(Color.DARKORANGE);
                                if(clickCount.get() % 3 == 1) {
                                    trades.add(new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey()));
                                    borderGlow.setOffsetX(-5);
                                }
                                else if(clickCount.get() % 3 == 2) {
                                    trades.add(new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey()));
                                    borderGlow.setOffsetX(0);
                                }
                                else {
                                    TradeAction trade2 = new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey());
                                    for (int i = 0 ; i < trades.size() ; i++) {
                                        if(trades.get(i).equals(trade2)) {
                                            trades.remove(i);
                                            break;
                                        }
                                    }
                                    for (int i = 0 ; i < trades.size() ; i++) {
                                        if(trades.get(i).equals(trade2)) {
                                            trades.remove(i);
                                            break;
                                        }
                                    }
                                    borderGlow.setColor(Color.TRANSPARENT);
                                    borderGlow.setOffsetX(0);
                                }
                            }

                    }
                    else if( card.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_ONE_OF_TWO) {
                        Map.Entry<RESOURCE_TYPE, Integer> entry2 = iter.next();
                        assert  entry != entry2;
                        borderGlow.setColor(Color.DARKORANGE);
                        if(clickCount.get() % 3 == 1) {
                            trades.add(new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey()));
                            borderGlow.setOffsetX(-5);
                        }
                        else if(clickCount.get() % 3 == 2) {
                            trades.add(new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry2.getKey()));
                            TradeAction trade2 = new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry.getKey());
                            for (int i = 0 ; i < trades.size() ; i++) {
                                if(trades.get(i).equals(trade2)) {
                                    trades.remove(i);
                                    break;
                                }
                            }
                            borderGlow.setOffsetX(5);
                        }
                        else {
                            TradeAction trade2 = new TradeAction(gameplayController.getPlayer().getId(), playerModel.getId(), selectedCard.getId(), entry2.getKey());
                            for (int i = 0 ; i < trades.size() ; i++) {
                                if(trades.get(i).equals(trade2)) {
                                    trades.remove(i);
                                    break;
                                }
                            }
                            borderGlow.setColor(Color.TRANSPARENT);
                            borderGlow.setOffsetX(0);
                        }
                    }
                });
            }

            if (hbox.getChildren().size() == 0) {
                hbox.getChildren().add(button);
            } else {
                if (card.getColor() != lastCard.getColor()) {
                    hbox = new HBox();
                    hbox.getChildren().add(button);
                } else {
                    hbox.getChildren().add(button);
                    hbox = new HBox();
                }
                pane.getChildren().add(hbox);
                hbox.setSpacing(2);
            }
            lastCard = card;
        }
    }
}
