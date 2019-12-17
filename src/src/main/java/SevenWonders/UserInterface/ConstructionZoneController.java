package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ConstructionZoneController {
    
    GameplayController gameplayController;

    @FXML
    Pane rightNeighborConstructionPane, leftNeighborConstructionPane;

    @FXML
    VBox brown, gray, blue, green, red, yellowAndPurple;

    public ConstructionZoneController() {}

    public void updateScene(PlayerModel playerModel) {
        Platform.runLater(() -> {
            updatePlayerConstruction(playerModel);
            //updateLeftNeighborConstruction();
            //updateRightNeighborConstruction();
        });
    }

    private void updatePlayerConstruction(PlayerModel playerModel){
        for(Card card: playerModel.getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();

            if(color == CARD_COLOR_TYPE.BROWN)
                brown.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.GRAY)
                gray.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.BLUE)
                blue.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.GREEN)
                green.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")));
            else if(color == CARD_COLOR_TYPE.RED)
                red.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")));
            else if( (color == CARD_COLOR_TYPE.YELLOW) || (color == CARD_COLOR_TYPE.PURPLE))
                yellowAndPurple.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")));
        }
    }

    private void updateLeftNeighborConstruction(){
        Parent root = AssetManager.getInstance().getSceneByName("NeighborConstructionView.fxml");
        leftNeighborConstructionPane = (Pane) root;
        VBox brown = (VBox) root.lookup("#brown");
        VBox gray = (VBox) root.lookup("#gray");
        VBox blue = (VBox) root.lookup("#blue");
        VBox green = (VBox) root.lookup("#green");
        VBox yellow = (VBox) root.lookup("#yellow");
        VBox purple = (VBox) root.lookup("#purple");

        for(Card card: gameplayController.getLeftPlayer().getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();

            if(color == CARD_COLOR_TYPE.BROWN)
                brown.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GRAY)
                gray.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.BLUE)
                blue.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GREEN)
                green.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.PURPLE)
                purple.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if( color == CARD_COLOR_TYPE.YELLOW)
                yellow.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
        }
    }

    private void updateRightNeighborConstruction(){
        Parent root = AssetManager.getInstance().getSceneByName("NeighborConstructionView.fxml");
        rightNeighborConstructionPane = (Pane) root;
        VBox brown = (VBox) root.lookup("#brown");
        VBox gray = (VBox) root.lookup("#gray");
        VBox blue = (VBox) root.lookup("#blue");
        VBox green = (VBox) root.lookup("#green");
        VBox yellow = (VBox) root.lookup("#yellow");
        VBox purple = (VBox) root.lookup("#purple");

        for(Card card: gameplayController.getRightPlayer().getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();

            if(color == CARD_COLOR_TYPE.BROWN)
                brown.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GRAY)
                gray.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.BLUE)
                blue.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.GREEN)
                green.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if(color == CARD_COLOR_TYPE.PURPLE)
                purple.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
            else if( color == CARD_COLOR_TYPE.YELLOW)
                yellow.getChildren().add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini_neighbor.png")));
        }
    }
}
