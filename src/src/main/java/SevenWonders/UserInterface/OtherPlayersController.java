package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
        PlayerModel[] allPlayers = gameplayController.gameModel.getPlayerList();
        int columnIndex = 1;
        for ( PlayerModel player: allPlayers) {

            if ( player.getId() != gameplayController.client.getID()) {

                Parent root = AssetManager.getInstance().getSceneByNameForce("OtherPlayersDetailView.fxml");
                Label moneyLabel = (Label) root.lookup("#moneyLabel");
                moneyLabel.setText("" + player.getGold());
                Label warLabel = (Label) root.lookup("#warLabel");
                warLabel.setText("" + player.getShields());
                Label godLabel = (Label) root.lookup("#godLabel");
                godLabel.setText("" + player.getWonder().getGod().getGodType());    //Player's god status should be accessed
                Label playerNameLabel = (Label) root.lookup("#playerNameLabel");
                playerNameLabel.setText(player.getName());
                Label coinLabel = (Label) root.lookup("#coinLabel");
                coinLabel.setText("7");
                coinLabel.toFront();
                Label shieldLabel = (Label) root.lookup("#shieldLabel");
                shieldLabel.setText("4");
                shieldLabel.toFront();
                ImageView coinImageView = (ImageView) root.lookup("#coinImageView");
                coinImageView.setImage(AssetManager.getInstance().getImage("coin.png"));
                coinImageView.toBack();
                ImageView shieldImageView = (ImageView) root.lookup("#shieldImageView");
                shieldImageView.setImage(AssetManager.getInstance().getImage("shield.png"));
                shieldImageView.toBack();
                Label wonderStage1 = (Label) root.lookup("#wonderStage1");
                ImageView wonderStage1Image = new ImageView(AssetManager.getInstance().getImage("babylon3.png"));
                wonderStage1Image.setFitHeight(37);
                wonderStage1Image.setFitWidth(37);
                wonderStage1.setGraphic(wonderStage1Image);
                Label wonderStage2 = (Label) root.lookup("#wonderStage2");
                ImageView wonderStage2Image = new ImageView(AssetManager.getInstance().getImage("babylon3.png"));
                wonderStage2Image.setFitHeight(37);
                wonderStage2Image.setFitWidth(37);
                wonderStage2.setGraphic(wonderStage2Image);
                Label wonderStage3 = (Label) root.lookup("#wonderStage3");
                ImageView wonderStage3Image = new ImageView(AssetManager.getInstance().getImage("babylon3.png"));
                wonderStage3Image.setFitHeight(37);
                wonderStage3Image.setFitWidth(37);
                wonderStage3.setGraphic(wonderStage3Image);
                wonderStage3.setStyle("-fx-background-color: white;");
                wonderStage2.setStyle("-fx-background-color: grey;");








                if ( player.getId() == gameplayController.gameModel.getLeftPlayer(gameplayController.client.getID()).getId()) {
                    otherPlayersPane.add(root, 0, 0);
                }
                else if ( player.getId() == gameplayController.gameModel.getRightPlayer(gameplayController.client.getID()).getId()) {
                    otherPlayersPane.add(root, 5, 0);
                }
                else{
                    otherPlayersPane.add(root, columnIndex ,0);
                    columnIndex++;
                }
            }
        }
    }
}
