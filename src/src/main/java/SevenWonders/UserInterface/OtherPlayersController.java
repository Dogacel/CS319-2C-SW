package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.Network.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class OtherPlayersController implements Initializable {

    public GameplayController gameplayController;

    @FXML
    private GridPane otherPlayersPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void updateScene( PlayerModel playerModel){
        Platform.runLater(() -> {
            otherPlayersPane.getChildren().clear();

            PlayerModel[] allPlayers = gameplayController.gameModel.getPlayerList();
            int columnIndex = 1;
            for ( PlayerModel player: allPlayers) {

                if ( player.getId() != gameplayController.client.getID()) {

                    Parent root = AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");

                    //modify name label to hold names of players
                    Label playerNameLabel = (Label) root.lookup("#playerNameLabel");
                    playerNameLabel.setText(player.getName());

                    //coin label holds the coins of players
                    Label coinLabel = (Label) root.lookup("#coinLabel");
                    coinLabel.setText(player.getGold() + "");
                    coinLabel.toFront();

                    //image behind the coinlabel
                    ImageView coinImageView = (ImageView) root.lookup("#coinImageView");
                    coinImageView.setImage(AssetManager.getInstance().getImage("coin.png"));
                    coinImageView.toBack();

                    //holds the war points of players
                    Label shieldLabel = (Label) root.lookup("#shieldLabel");
                    shieldLabel.setText(player.getShields() +"");
                    shieldLabel.toFront();

                    //image behind shield label
                    ImageView shieldImageView = (ImageView) root.lookup("#shieldImageView");
                    shieldImageView.setImage(AssetManager.getInstance().getImage("shield.png"));
                    shieldImageView.toBack();

                    //holds the war points of players
                    Label warLabel = (Label) root.lookup("#warLabel");
                    warLabel.setText(player.getWarPoints() +"");
                    warLabel.toFront();

                    //image behind war point label
                    ImageView warImageView = (ImageView) root.lookup("#shieldImageView");
                    warImageView.setImage(AssetManager.getInstance().getImage("military.png"));
                    warImageView.toBack();

                    /*
                    Wonder stage requirements images for each player's wonders
                     */
                    Label wonderStage1 = (Label) root.lookup("#wonderStage1");
                    ImageView wonderStage1Image = new ImageView(AssetManager.getInstance().getImage(player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_1.png"));
                    wonderStage1Image.setFitHeight(34);
                    wonderStage1Image.setFitWidth(64);
                    wonderStage1.setGraphic(wonderStage1Image);

                    Label wonderStage2 = (Label) root.lookup("#wonderStage2");
                    ImageView wonderStage2Image = new ImageView(AssetManager.getInstance().getImage(player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_2.png"));
                    wonderStage2Image.setFitHeight(34);
                    wonderStage2Image.setFitWidth(64);
                    wonderStage2.setGraphic(wonderStage2Image);

                    Label wonderStage3 = (Label) root.lookup("#wonderStage3");
                    ImageView wonderStage3Image = new ImageView(AssetManager.getInstance().getImage(player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_3.png"));
                    wonderStage3Image.setFitHeight(34);
                    wonderStage3Image.setFitWidth(64);
                    wonderStage3.setGraphic(wonderStage3Image);

                    //Update other players wonder stage by highlighting them
                    if (player.getWonder().getCurrentStageIndex() >= 1) {
                        wonderStage1.setStyle("-fx-background-color: rgb(0,144,116)");
                    } else {
                        wonderStage1.setStyle("-fx-background-color: rgba(0,0,0,0.8)");
                    }
                    if (player.getWonder().getCurrentStageIndex() >= 2) {
                        wonderStage2.setStyle("-fx-background-color: rgb(0,144,116)");
                    } else {
                        wonderStage2.setStyle("-fx-background-color: rgba(0,0,0,0.8)");
                    }
                    if (player.getWonder().getCurrentStageIndex() >= 3) {
                        wonderStage3.setStyle("-fx-background-color: rgb(0,144,116);");
                    } else {
                        wonderStage3.setStyle("-fx-background-color: rgba(0,0,0,0.8)");
                    }

                    //add background image according to the user wonder
                    Pane topPane = (Pane) root.lookup("#topPane");
                    BackgroundImage backgroundImage = new BackgroundImage(AssetManager.getInstance().getImage(player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + ".png"),
                            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                    topPane.setBackground(new Background(backgroundImage));

                    GridPane outerGrid = (GridPane) root.lookup("#otherConstruction");
                    Parent constructionRoot = AssetManager.getInstance().getSceneByNameForce("NeighborConstructionView.fxml");
                    Pane outerPane = (Pane) root.lookup("#outerPane");
                    outerPane.setStyle("");
                    //initialize construction zone areas
                    VBox brown = (VBox) constructionRoot.lookup("#brown");
                    VBox gray = (VBox) constructionRoot.lookup("#gray");
                    VBox blue = (VBox) constructionRoot.lookup("#blue");
                    VBox green = (VBox) constructionRoot.lookup("#green");
                    VBox yellow = (VBox) constructionRoot.lookup("#yellow");
                    VBox purple = (VBox) constructionRoot.lookup("#purple");

                    brown.getChildren().clear();
                    gray.getChildren().clear();
                    blue.getChildren().clear();
                    green.getChildren().clear();
                    yellow.getChildren().clear();
                    purple.getChildren().clear();

                    if (player.getId() == gameplayController.gameModel.getLeftPlayer(gameplayController.client.getID()).getId()) {
                        otherPlayersPane.add(root, 0, 0);
                        outerPane.getChildren().removeAll();
                        HBox minusOnes = new HBox();
                        minusOnes.setSpacing(-7.0);
                        outerPane.getChildren().add(minusOnes);
                        outerPane.setMaxHeight(50);
                        outerPane.setVisible(true);
                        for(int i = 0; i <
                                gameplayController.gameModel.getLeftPlayer(gameplayController.client.getID()).getLostWarNumber(); i++)
                        {
                            minusOnes.getChildren().add(new ImageView(AssetManager.getInstance().getImage("warpoints_minus1.png")));
                        }
                        outerPane.setManaged(false);
                        outerPane.setLayoutY(90);
                        outerPane.setLayoutX(0);
                    } else if (player.getId() == gameplayController.gameModel.getRightPlayer(gameplayController.client.getID()).getId()) {
                        otherPlayersPane.add(root, 5, 0);
                        outerPane.getChildren().removeAll();
                        HBox minusOnes = new HBox();
                        minusOnes.setSpacing(-7.0);
                        outerPane.getChildren().add(minusOnes);
                        outerPane.setMaxHeight(50);
                        outerPane.setVisible(true);
                        for(int i = 0; i <
                                gameplayController.gameModel.getLeftPlayer(gameplayController.client.getID()).getLostWarNumber(); i++)
                        {
                            minusOnes.getChildren().add(new ImageView(AssetManager.getInstance().getImage("warpoints_minus1.png")));
                        }
                        outerPane.setManaged(false);
                        outerPane.setLayoutY(90);
                        outerPane.setLayoutX(0);
                    } else {
                        otherPlayersPane.add(root, columnIndex, 0);
                        outerGrid.add(constructionRoot, 0, 0);

                        for (Card card : player.getConstructionZone().getConstructedCards()) {
                            CARD_COLOR_TYPE color = card.getColor();

                            if (color == CARD_COLOR_TYPE.BROWN) {
                                brown.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
                            } else if (color == CARD_COLOR_TYPE.GRAY)
                                gray.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
                            else if (color == CARD_COLOR_TYPE.BLUE)
                                blue.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
                            else if (color == CARD_COLOR_TYPE.GREEN)
                                green.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
                            else if ((color == CARD_COLOR_TYPE.YELLOW))
                                yellow.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
                            else if (color == CARD_COLOR_TYPE.PURPLE)
                                purple.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
                        }
                        topPane.setOnMouseClicked((event) -> {
                            if (outerPane.isVisible()) {
                                outerPane.setVisible(false);
                            } else {
                                outerPane.setVisible(true);
                            }
                        });
                        columnIndex++;
                    }
                }
            }
        });
    }
}
