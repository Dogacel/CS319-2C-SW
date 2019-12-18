package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ConstructionZoneController {
    
    GameplayController gameplayController;

    @FXML
    VBox rightNeighborConstructionPane, leftNeighborConstructionPane, brown, gray, blue, green, red, yellowAndPurple;

    @FXML
    GridPane constructionGrid;

    VBox brownNeighbor, grayNeighbor, blueNeighbor, greenNeighbor, yellowNeighbor, purpleNeighbor;

    public ConstructionZoneController() {}

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            updatePlayerConstruction(playerModel);
            updateLeftNeighborConstruction();
            updateRightNeighborConstruction();
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

            if(color == CARD_COLOR_TYPE.BROWN)
                brown.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.GRAY)
                gray.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.BLUE)
                blue.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.GREEN)
                green.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.RED)
                red.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png")));
            else if( (color == CARD_COLOR_TYPE.YELLOW) || (color == CARD_COLOR_TYPE.PURPLE))
                yellowAndPurple.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini.png")));
        }
    }

    private void updateLeftNeighborConstruction(){
        leftNeighborConstructionPane.getChildren().clear();
        Parent root = AssetManager.getInstance().getSceneByName("NeighborConstructionView.fxml");
        leftNeighborConstructionPane.getChildren().add(root);
        brownNeighbor = (VBox) root.lookup("#brown");
        grayNeighbor = (VBox) root.lookup("#gray");
        blueNeighbor = (VBox) root.lookup("#blue");
        greenNeighbor = (VBox) root.lookup("#green");
        yellowNeighbor = (VBox) root.lookup("#yellow");
        purpleNeighbor = (VBox) root.lookup("#purple");

        for(Card card: gameplayController.getLeftPlayer().getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();

            if(color == CARD_COLOR_TYPE.BROWN)
                brownNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GRAY)
                grayNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.BLUE)
                blueNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GREEN)
                greenNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.PURPLE)
                purpleNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if( color == CARD_COLOR_TYPE.YELLOW)
                yellowNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
        }
    }

    private void updateRightNeighborConstruction(){
        rightNeighborConstructionPane.getChildren().clear();
        Parent root = AssetManager.getInstance().getSceneByName("NeighborConstructionView.fxml");
        rightNeighborConstructionPane.getChildren().add(root);
        brownNeighbor = (VBox) root.lookup("#brown");
        grayNeighbor = (VBox) root.lookup("#gray");
        blueNeighbor = (VBox) root.lookup("#blue");
        greenNeighbor = (VBox) root.lookup("#green");
        yellowNeighbor = (VBox) root.lookup("#yellow");
        purpleNeighbor = (VBox) root.lookup("#purple");

        for(Card card: gameplayController.getRightPlayer().getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();

            if(color == CARD_COLOR_TYPE.BROWN)
                brownNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GRAY)
                grayNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.BLUE)
                blueNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GREEN)
                greenNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.PURPLE)
                purpleNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
            else if( color == CARD_COLOR_TYPE.YELLOW)
                yellowNeighbor.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().replaceAll(" ", "").toLowerCase() + "_mini_neighbor.png")));
        }
    }
}
