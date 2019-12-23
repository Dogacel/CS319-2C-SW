package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.Network.Server;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class CardViewController implements Initializable {

    GameplayController gameplayController;

    private Card selectedCard;
    private ImageView focusedView;

    private DropShadow borderGlow;

    @FXML
    HBox cardBox;

    @FXML
    Button discardPile, exitButton;

    @FXML
    BorderPane leftPane;

    private Vector<Card> hand;

    public CardViewController(){
        borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GOLD);
        int depth1 = 100;
        borderGlow.setWidth(depth1);
        borderGlow.setHeight(depth1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void refresh() {
        focusedView = null;
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
        ImageView imageView = new ImageView(AssetManager.getInstance().getImage(gameplayController.getPlayer().getWonder().getResource().name().toLowerCase() + "_r.png"));
        leftPane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
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

        if (SettingsController.autoTrade) {
            var x = MoveController.getInstance().playerCanMakeMove(move, gameplayController.getPlayer(),  gameplayController.getNeighbors(), true);
            if (x.getKey()) {
                if (x.getValue().size() != 0) {
                    canBuild.setColor(Color.GOLD);
                } else {
                    canBuild.setColor(Color.GREEN);
                }
            }
            else{
                canBuild.setColor(Color.RED);
            }
        } else {
            var x = MoveController.getInstance().playerCanMakeMove(move, gameplayController.getPlayer(),  gameplayController.getNeighbors(), false);
            if(x.getKey()){
                canBuild.setColor(Color.LIGHTGREEN);
            }
            else{
                canBuild.setColor(Color.RED);
            }
        }

        return canBuild;
    }

    private void updateCards(Vector<Card> hand){
        cardBox.getChildren().clear();


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

                StackPane pane = new StackPane();
                pane.getChildren().add(imageView);

                cardBox.getChildren().add(pane);

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

                int tradeCost = 0;
                if (SettingsController.autoTrade) {
                    MoveModel move = new MoveModel(gameplayController.getPlayer().getId(), c.getId(), ACTION_TYPE.BUILD_CARD);
                    var x = MoveController.getInstance().playerCanMakeMove(move, gameplayController.getPlayer(),
                            new Pair<>(gameplayController.getLeftPlayer(), gameplayController.getRightPlayer()), true);
                    if (x.getKey()) {
                        Vector<TradeAction> trades = x.getValue();
                        tradeCost = MoveController.getInstance().tradeCost(trades, gameplayController.getPlayer(), new Pair<>(gameplayController.getLeftPlayer(), gameplayController.getRightPlayer()));
                    }
                }

                if (tradeCost != 0) {
                    StackPane coinstack = new StackPane();
                    coinstack.setAlignment(Pos.CENTER);
                    ImageView coin = new ImageView(AssetManager.getInstance().getImage("coin.png"));
                    coin.setScaleX(0.3);
                    coin.setScaleY(0.3);
                    coinstack.getChildren().add(coin);
                    Label text = new Label(tradeCost + "");
                    text.setStyle("-fx-text-fill: black; -fx-font-family: Assassin$;");
                    coinstack.getChildren().add(text);
                    coinstack.setMaxSize(coin.getFitWidth(), coin.getFitHeight());
                    pane.setAlignment(Pos.BOTTOM_CENTER);
                    pane.getChildren().add(coinstack);
                }

                tp.setText(c.getName() + ((buildings).equals("") ? "" : "\nChain: ") + buildings + ((requirements).equals("") ? "" : "\nRequirements:\n" + requirements + (tradeCost > 0 ? "Trade cost: " + tradeCost : "")));
                tp.setShowDelay(new Duration(250));
                tp.setHideDelay(new Duration(0));
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
        SceneManager.getInstance().changeScene("MainMenuView.fxml");
    }

    public void heroButtonClicked(MouseEvent mouseEvent) {
        var sceneAndController = AssetManager.getInstance().getSceneAndController("HeroPowerSelectionView.fxml");
        HeroPowerSelectionController controller = (HeroPowerSelectionController) sceneAndController.getValue();
    Parent root = sceneAndController.getKey();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);
        borderPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        borderPane.setOnMouseClicked(event -> SceneManager.getInstance().popPaneOnScreenNow(borderPane));
        SceneManager.getInstance().showPaneOnScreenNow(borderPane);
    }

    public void discardButtonClicked(MouseEvent mouseEvent) {
        ScrollPane scrollPane = new ScrollPane();
        if (!gameplayController.getPlayer().getPlayerCanBuildDiscard() ||
                gameplayController.getPlayer().getWonder().getStages()[1].getWonderEffect().getEffectType() != WONDER_EFFECT_TYPE.BUILD_FROM_DISCARDED ||
                gameplayController.getPlayer().getWonder().getCurrentStageIndex() < 2) {
            return;
        }
        scrollPane.setMaxWidth(800);
        scrollPane.setMaxHeight(400);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setStyle("-fx-background-color: transparent;");
        HBox cards = new HBox();
        cards.setStyle("-fx-background-color: black;");
        cards.setSpacing(5.0);
        scrollPane.setContent(cards);

        for (Card c : gameplayController.gameModel.getDiscardPile().getCards()) {
            ImageView imageView = new ImageView(AssetManager.getInstance().getImage(c.getName().replaceAll(" ", "").toLowerCase() + ".png")
            );
            imageView.setOnMouseClicked(event ->
            {
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
                }
            });
            cards.getChildren().add(imageView);
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Group(scrollPane));
        borderPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        borderPane.setOnMousePressed(event -> {
            System.out.println(event.getSource());
            System.out.println(event.getTarget());
            if (event.getTarget() != scrollPane && ! (event.getTarget() instanceof ImageView)) {
                SceneManager.getInstance().popPaneOnScreenNow(borderPane);
            }
        });
        SceneManager.getInstance().showPaneOnScreenNow(borderPane);
    }
}