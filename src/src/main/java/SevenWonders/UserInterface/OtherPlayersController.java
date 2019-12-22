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
import java.util.Comparator;
import java.util.ResourceBundle;
public class OtherPlayersController implements Initializable {
    public GameplayController gameplayController;
    @FXML
    private GridPane otherPlayersConstructionPane, otherPlayersPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void updateScene( PlayerModel playerModel){
        Platform.runLater(() -> {
            otherPlayersConstructionPane.getChildren().clear();
            otherPlayersPane.getChildren().clear();
            PlayerModel[] allPlayers = gameplayController.gameModel.getPlayerList();
            int columnIndex = 4;
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
                    //holds the shields of players
                    Label shieldLabel = (Label) root.lookup("#shieldLabel");
                    shieldLabel.setText(player.getShields() + "");
                    shieldLabel.toFront();
                    //image behind shield label
                    ImageView shieldImageView = (ImageView) root.lookup("#shieldImageView");
                    shieldImageView.setImage(AssetManager.getInstance().getImage("military.png"));
                    shieldImageView.toBack();
                    //holds minus ones of players
                    Label minusOneLabel = (Label) root.lookup("#minusOneLabel");
                    if(player.getLostWarNumber() == 0)
                        minusOneLabel.setText("0");
                    else
                        minusOneLabel.setText("-" + player.getLostWarNumber());
                    minusOneLabel.toFront();
                    //image behind minus one label
                    ImageView minusOneImage = (ImageView) root.lookup("#minusOneImage");
                    minusOneImage.setImage(AssetManager.getInstance().getImage("warpoint.png"));
                    minusOneImage.toBack();
                    /*
                    Wonder stage requirements images for each player's wonders
                     */
                    Label wonderStage1 = (Label) root.lookup("#wonderStage1");
                    ImageView wonderStage1Image = new ImageView(AssetManager.getInstance().getImage(
                            player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_1.png"));
                    wonderStage1Image.setFitHeight(34);
                    wonderStage1Image.setFitWidth(64);
                    wonderStage1.setGraphic(wonderStage1Image);
                    Label wonderStage2 = (Label) root.lookup("#wonderStage2");
                    ImageView wonderStage2Image = new ImageView(AssetManager.getInstance().getImage(
                            player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_2.png"));
                    wonderStage2Image.setFitHeight(34);
                    wonderStage2Image.setFitWidth(64);
                    wonderStage2.setGraphic(wonderStage2Image);
                    Label wonderStage3 = (Label) root.lookup("#wonderStage3");
                    ImageView wonderStage3Image = new ImageView(AssetManager.getInstance().getImage(
                            player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + "_3.png"));
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
                    topPane.setLayoutY(0);
                    BackgroundImage backgroundImage = new BackgroundImage(AssetManager.getInstance().getImage(player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + ".png"),
                            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                    topPane.setBackground(new Background(backgroundImage));
                    GridPane outerGrid = (GridPane) root.lookup("#otherConstruction");

                    VBox constructionRoot = new VBox();

                    Pane outerPane = (Pane) root.lookup("#outerPane");
                    BorderPane border = (BorderPane) root.lookup("#BorderPane");
                    outerPane.setStyle("");
                    otherPlayersPane.add(root, 5 - ((6 + player.getId() - playerModel.getId()) % 7), 1);
                    if (player.getId() == gameplayController.gameModel.getRightPlayer(gameplayController.client.getID()).getId()) {
                        outerPane.setVisible(false);
                    } else if (player.getId() == gameplayController.gameModel.getLeftPlayer(gameplayController.client.getID()).getId()) {
                        outerPane.setVisible(false);
                    } else {
                        topPane.setOnMouseClicked((event) -> {
                            if (outerPane.isVisible()) {
                                outerPane.setVisible(false);
                            } else {
                                outerPane.setVisible(true);
                            }
                        });
                        otherPlayersConstructionPane.add(root.lookup("#outerPane"), 5 - ((6 + player.getId() - playerModel.getId()) % 7), 0);
                        columnIndex--;
                    }


                    player.getConstructionZone().getConstructedCards().sort(Comparator.comparingInt(card -> card.getColor().ordinal()));
                    int count = 0;
                    HBox hbox = new HBox();
                    constructionRoot.getChildren().add(hbox);
                    Card lastCard = playerModel.getConstructionZone().getConstructedCards().size() > 0 ? playerModel.getConstructionZone().getConstructedCards().get(0) : null;



                    for(Card card: player.getConstructionZone().getConstructedCards()){
                        ImageView view = new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png"));

                        if (hbox.getChildren().size() == 0) {
                            hbox.getChildren().add(view);
                        } else {
                            if (card.getColor() != lastCard.getColor()) {
                                hbox = new HBox();
                                hbox.getChildren().add(view);
                            } else {
                                hbox.getChildren().add(view);
                                hbox = new HBox();
                            }
                            constructionRoot.getChildren().add(hbox);
                        }
                        lastCard = card;

                    }
                }
            }
        });
    }
}
