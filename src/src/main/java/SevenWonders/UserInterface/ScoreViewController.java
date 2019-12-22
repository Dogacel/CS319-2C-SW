package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.GameLogic.ScoreController;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreViewController implements Initializable {

   GameplayController gameplayController;

    @FXML
    private Button returnMenuButton, exitButton;

    @FXML
    private BorderPane borderPane;


    public ScoreViewController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater( () -> {
            PlayerModel[] allPlayers = gameplayController.gameModel.getPlayerList();
            GridPane playerGrid = new GridPane();
            int index = 1;
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("militaryPoints.jpg")),1,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("goldPoints.png")),2,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("wonderPoints.png")),3,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("civilPoints.jpg")),4,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("commercialPoints.jpg")),5,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("guildPoints.jpg")),6,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("sciencePoints.jpg")),7,0);
            playerGrid.add(new ImageView(AssetManager.getInstance().getImage("totalPoints.png")),8,0);

            for ( PlayerModel player: allPlayers) {
                FlowPane pane = new FlowPane();
                ImageView wonderImage =  new ImageView(AssetManager.getInstance().getImage(player.getWonder().getWonderType().name().replaceAll("_", "").toLowerCase() + ".png"));
                Text text = new Text(player.getName() + "");
                wonderImage.setFitWidth(150);
                wonderImage.setFitHeight(50);
                pane.getChildren().add(wonderImage);
                pane.getChildren().add(text);

                playerGrid.add(pane, 0, index);
                playerGrid.add(new Text(ScoreController.calculateMilitaryConflicts(player) + ""), 1, index);
                playerGrid.add(new Text(ScoreController.calculateTreasuryContents(player) + ""), 2, index);
                playerGrid.add(new Text(ScoreController.calculateWonders(player) + ""), 3, index);
                playerGrid.add(new Text(ScoreController.calculateCivilianStructures(player) + ""), 4, index);
                playerGrid.add(new Text(ScoreController.calculateCommercialStructures(player) + ""), 5, index);
                playerGrid.add(new Text(ScoreController.calculateGuilds(player, gameplayController.getLeftPlayer(), gameplayController.getRightPlayer()) + ""), 6, index);
                playerGrid.add(new Text(ScoreController.calculateScientificStructures(player) + ""), 7, index);
                playerGrid.add(new Text(ScoreController.calculateScore(player.getId(), gameplayController.gameModel) + ""), 8, index);
                index++;
            }
            borderPane.setCenter(playerGrid);
            playerGrid.setAlignment(Pos.CENTER);
            for(Node node: playerGrid.getChildren()){
                GridPane.setHalignment(node, HPos.CENTER);
                node.setStyle(" -fx-font: Assassin$;" + "-fx-font-size: 20px;" +
                        "-fx-fill: white;" +
                        "-fx-stroke-width: 1px;" +
                        "-fx-stroke: gold;");
                if( node instanceof FlowPane){
                    ((FlowPane) node).getChildren().get(1).setStyle("-fx-font-size: 20px;" +
                            "-fx-fill: white;" +
                            "-fx-stroke-width: 1px;" +
                            "-fx-stroke: gold;");
                }
            }

            playerGrid.setVgap(5);
            int depth1 = 100;
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.BLACK);
            borderGlow.setWidth(depth1);
            borderGlow.setHeight(depth1);
            playerGrid.setEffect(borderGlow);
            playerGrid.setStyle("-fx-background-color: rgba(0,0,0,0.3);");
        });
    }

    public void exitButtonClicked(javafx.scene.input.MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void returnMenuButtonClicked(javafx.scene.input.MouseEvent mouseEvent) {
        SceneManager.getInstance().changeScene("MainMenu.fxml");
    }
}
