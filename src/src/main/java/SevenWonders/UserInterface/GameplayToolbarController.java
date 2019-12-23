package SevenWonders.UserInterface;

import SevenWonders.AI.AIMoveGenerator;
import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.*;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.God;
import SevenWonders.SceneManager;
import SevenWonders.SoundManager;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Set;

public class GameplayToolbarController {

    PlayerModel playerModel;
    GameplayController gameplayController;
    CardViewController cardViewController;
    MoveModel currentMove;

    ImageView wonder1, wonder2, wonder3;


    Button selectedButton;
    BorderPane waitingPane;

    @FXML
    Button buildCardButton, buildWonderButton, readyButton, discardCardButton, useGodPowerButton;

    @FXML
    BorderPane wonder1Pane, wonder2Pane, wonder3Pane;

    private void updatePlayerMove(MoveModel move) {
        // TODO: Setting for auto trade
        var x = MoveController.getInstance().playerCanMakeMove(move, playerModel, gameplayController.getNeighbors(), SettingsController.autoTrade);
        if (SettingsController.autoTrade) {
            for (TradeAction trade : x.getValue()) {
                move.addTrade(trade);
            }
        } else {
            for (TradeAction tradeAction : gameplayController.constructionZoneController.trades) {
                move.addTrade(tradeAction);
            }
        }
        playerModel.setCurrentMove(move);
    }

    @FXML
    Label coinLabel, warPointLabel;

    @FXML
    HBox victoryPointsHBox;

    @FXML
    private void buildCardButtonClicked(MouseEvent event) {
        if (cardViewController.getSelectedCard() != null) {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("active");
            }
            selectedButton = buildCardButton;
            selectedButton.getStyleClass().add("active");

            MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD);
            updatePlayerMove(move);
        }
    }

    @FXML
    private void buildWonderButtonClicked(MouseEvent event) {
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("active");
        }
        selectedButton = buildWonderButton;
        selectedButton.getStyleClass().add("active");
        if (cardViewController.getSelectedCard() != null) {
            MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.UPGRADE_WONDER);
            updatePlayerMove(move);
        }
    }

    @FXML
    private void discardCardButtonClicked(MouseEvent event) {
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("active");
        }
        selectedButton = discardCardButton;
        selectedButton.getStyleClass().add("active");
        if (cardViewController.getSelectedCard() != null) {
            MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.DISCARD_CARD);
            updatePlayerMove(move);
        }
    }

    @FXML
    private void useGodPowerButtonClicked(MouseEvent event) {
        if (!gameplayController.getPlayer().getWonder().isUpgradeable()
                && gameplayController.getPlayer().getWonder().getGod().getGodPower() == GOD_POWER_TYPE.FORESIGHT) {
            ScrollPane scrollPane = new ScrollPane();
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

            PlayerModel player = gameplayController.gameModel.getCurrentAge() == 2 ? gameplayController.getLeftPlayer() : gameplayController.getRightPlayer();
            for (Card c : player.getHand()) {
                ImageView imageView = new ImageView(AssetManager.getInstance().getImage(c.getName().replaceAll(" ", "").toLowerCase() + ".png")
                );
                cards.getChildren().add(imageView);
            }

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(new Group(scrollPane));
            borderPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
            borderPane.setOnMousePressed(event1 -> {
                System.out.println(event1.getSource());
                System.out.println(event1.getTarget());
                if (event1.getTarget() != scrollPane && ! (event1.getTarget() instanceof ImageView)) {
                    SceneManager.getInstance().popPaneOnScreenNow(borderPane);
                }
            });
            SceneManager.getInstance().showPaneOnScreenNow(borderPane);
        } else if (cardViewController.getSelectedCard() != null) {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("active");
            }
            selectedButton = useGodPowerButton;
            selectedButton.getStyleClass().add("active");
            MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.USE_GOD_POWER);
            updatePlayerMove(move);
        }
    }

    @FXML
    private void readyButtonClicked(MouseEvent event) {
        if (playerModel.getCurrentMove() == null) {
            if (cardViewController.getSelectedCard() != null) {
                playerModel.setCurrentMove(new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD));
            }
        }

        if (playerModel.getCurrentMove()!= null) {
            boolean canPlay = MoveController.getInstance().playerCanMakeMove(playerModel.getCurrentMove(), playerModel,
                    new Pair<>(gameplayController.getLeftPlayer(),gameplayController.getRightPlayer()), false).getKey();
            if (canPlay) {
                currentMove = playerModel.getCurrentMove();
            } else if (currentMove == null) {
                currentMove = new MoveModel(0, 0, ACTION_TYPE.DISCARD_CARD);
            }
            gameplayController.getClient().sendMakeMoveRequest( playerModel.getCurrentMove());
            gameplayController.getClient().sendPlayerReadyRequest(true);

            waitingPane = new BorderPane();
            waitingPane.setCenter(new Group(new ImageView(AssetManager.getInstance().getImage("loading.gif"))));
            waitingPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
            waitingPane.setOnMousePressed(event1 -> {
                gameplayController.getClient().sendPlayerReadyRequest(false);
                SceneManager.getInstance().popPaneOnScreenNow(waitingPane);
            });
            SceneManager.getInstance().showPaneOnScreenNow(waitingPane);
        }
    }

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            String type = "";
            if (waitingPane != null) {
                SceneManager.getInstance().popPaneOnScreenNow(waitingPane);
            }
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("active");
            }
            if (currentMove != null) {
                Card playedCard = AssetManager.getInstance().getCardByID(currentMove.getSelectedCardID());

                if( currentMove.getAction() == ACTION_TYPE.BUILD_CARD) {
                    if (playedCard.getColor() == CARD_COLOR_TYPE.RED)
                        type = "red";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.BLUE)
                        type = "blue";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.GREEN)
                        type = "green";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.YELLOW)
                        type = "yellow";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.BROWN)
                        type = "brown";
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.GRAY) {
                        if (playedCard.getCardEffect().getResources().get(RESOURCE_TYPE.PAPYRUS) != null)
                            type = "papyrus";
                        else if (playedCard.getCardEffect().getResources().get(RESOURCE_TYPE.GLASS) != null)
                            type = "glass";
                        else if (playedCard.getCardEffect().getResources().get(RESOURCE_TYPE.LOOM) != null)
                            type = "loom";
                    }
                    else if (playedCard.getColor() == CARD_COLOR_TYPE.PURPLE)
                        type = "purple";

                    SoundManager.getInstance().playCardSound(type);

                }
                else if(currentMove.getAction() == ACTION_TYPE.UPGRADE_WONDER){
                    SoundManager.getInstance().playWonderSound();
                }
                else if(currentMove.getAction() == ACTION_TYPE.DISCARD_CARD){
                    SoundManager.getInstance().playDiscardSound();
                }
            }

            currentMove = null;

            this.playerModel = playerModel;
            updateWonder();
            updateCoinAndWarPoints();
        });
    }

    private void updateWonder(){
        wonder1 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_1.png"));
        wonder2 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_2.png"));
        wonder3 = new ImageView(AssetManager.getInstance().getImage(playerModel.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_3.png"));
        wonder1Pane.setCenter(wonder1);
        wonder2Pane.setCenter(wonder2);
        wonder3Pane.setCenter(wonder3);

        if(playerModel.getWonder().getCurrentStageIndex() == 1) {
            wonder1Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
        }
        if(playerModel.getWonder().getCurrentStageIndex() == 2) {
            wonder2Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
            if ( playerModel.getWonder().getStages()[1].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.FREE_BUILDING ||
                    playerModel.getWonder().getStages()[1].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.BUILD_FROM_DISCARDED) {
                wonder2Pane.setOnMouseClicked((e) -> {
                    MoveModel move = new MoveModel(playerModel.getId(), cardViewController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD);
                    updatePlayerMove(move);
                });
            }
        }
        if(playerModel.getWonder().getCurrentStageIndex() == 3) {
            wonder3Pane.setStyle("-fx-background-color: linear-gradient(to right top, #604040, #894f33, #996c0d, #849400, #11be18)");
        }
    }

    private void updateCoinAndWarPoints() {
        victoryPointsHBox.getChildren().clear();
        coinLabel.setText(playerModel.getGold() + "");
        warPointLabel.setText(playerModel.getWarPoints() + "");

        for(int i = 0; i < playerModel.getLostWarNumber(); i++) {
            ImageView imageView = new ImageView(AssetManager.getInstance().getImage("warpoint_minus1.png"));
            victoryPointsHBox.getChildren().add(imageView);
        }
    }
}
