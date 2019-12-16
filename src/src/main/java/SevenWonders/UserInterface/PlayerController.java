package SevenWonders.UserInterface;
import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class PlayerController {

    PlayerModel playerModel;

    CardViewController cardController;

    GameplayController gameplayController;

    @FXML
    Button readyButton, buildCardButton, buildWonderButton, discardButton, godPowerButton,settingsButton,tutorialButton,exitButton;

    @FXML
    AnchorPane playerAnchor;

    @FXML
    GridPane brownAndGray, green, blue, purple, red, yellow;

    public PlayerController() {

    }

    // TODO: Remove all set styles inside button presses

    @FXML
    private void buildCardButtonPressed(MouseEvent event) {
        buildCardButton.setStyle("-fx-background-image: url('/images/ui-images/buildCardButtonClicked.png')");
    }

    @FXML
    private void buildCardButtonReleased(MouseEvent event) {
        buildCardButton.setStyle("-fx-background-image: url('/images/ui-images/buildCardButton.png')");
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardController.getSelectedCard().getId(), ACTION_TYPE.BUILD_CARD));
    }

    @FXML
    private void buildWonderButtonPressed(MouseEvent event) {
        buildWonderButton.setStyle("-fx-background-image: url('/images/ui-images/buildWonderButtonClicked.png')");
    }

    @FXML
    private void buildWonderButtonReleased(MouseEvent event) {
        buildWonderButton.setStyle("-fx-background-image: url('/images/ui-images/buildWonderButton.png')");
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardController.getSelectedCard().getId(), ACTION_TYPE.UPGRADE_WONDER));
    }

    @FXML
    private void discardButtonPressed(MouseEvent event) {
        discardButton.setStyle("-fx-background-image: url('/images/ui-images/discardCardButtonClicked.png')");
    }

    @FXML
    private void discardButtonReleased(MouseEvent event) {
        discardButton.setStyle("-fx-background-image: url('/images/ui-images/discardCardButton.png')");
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardController.getSelectedCard().getId(), ACTION_TYPE.DISCARD_CARD));
    }

    @FXML
    private void godPowerButtonPressed(MouseEvent event) {
        godPowerButton.setStyle("-fx-background-image: url('/images/ui-images/godPowerButtonClicked.png')");
    }

    @FXML
    private void godPowerButtonReleased(MouseEvent event) {
        godPowerButton.setStyle("-fx-background-image: url('/images/ui-images/godPowerButton.png')");
        playerModel.setCurrentMove( new MoveModel(playerModel.getId(), cardController.getSelectedCard().getId(), ACTION_TYPE.USE_GOD_POWER));
    }

    @FXML
    private void settingsButtonPressed(MouseEvent event) {
        settingsButton.setStyle("-fx-background-image: url('/images/ui-images/settingsButtonClicked.png')");
    }

    @FXML
    private void settingsButtonReleased(MouseEvent event) {
        settingsButton.setStyle("-fx-background-image: url('/images/ui-images/settingsButton.png')");
    }

    @FXML
    private void tutorialButtonPressed(MouseEvent event) {
        tutorialButton.setStyle("-fx-background-image: url('/images/ui-images/tutorialButtonClicked.png')");
    }

    @FXML
    private void tutorialButtonReleased(MouseEvent event) {
        tutorialButton.setStyle("-fx-background-image: url('/images/ui-images/tutorialButton.png')");
    }

    @FXML
    private void exitButtonPressed(MouseEvent event) {
        exitButton.setStyle("-fx-background-image: url('/images/ui-images/exitButtonClicked.png')");
    }

    @FXML
    private void exitButtonReleased(MouseEvent event) {
        exitButton.setStyle("-fx-background-image: url('/images/ui-images/exitButton.png')");
    }

    @FXML
    private void readyButtonPressed(MouseEvent event) {
        readyButton.setStyle("-fx-background-image: url('images/ui-images/tokens/readyClicked.png')");
    }

    @FXML
    private void readyButtonReleased(MouseEvent event) {
        readyButton.setStyle("-fx-background-image: url('/images/ui-images/tokens/ready.png')");
        gameplayController.getClient().sendMakeMoveRequest( playerModel.getCurrentMove());
        gameplayController.getClient().sendPlayerReadyRequest(true);
    }

    public void updateScene(PlayerModel playerModel) {
        this.playerModel = playerModel;
        String wonder = playerModel.getWonder().getWonderType().toString().toLowerCase();
        this.playerAnchor.setStyle("-fx-background-image: url('/images/ui-images/" + wonder + ".png')");

        int columnIndex = 0;
        int rowIndex = 0;
        for(Card card: playerModel.getConstructionZone().getConstructedCards()){
            CARD_COLOR_TYPE color = card.getColor();

            if(color == CARD_COLOR_TYPE.BROWN || color == CARD_COLOR_TYPE.GRAY)
                brownAndGray.add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")), columnIndex, rowIndex);
            else if(color == CARD_COLOR_TYPE.RED)
                red.add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")), columnIndex, rowIndex);
            else if(color == CARD_COLOR_TYPE.BLUE)
                blue.add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")), columnIndex, rowIndex);
            else if(color == CARD_COLOR_TYPE.GREEN)
                green.add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")), columnIndex, rowIndex);
            else if(color == CARD_COLOR_TYPE.PURPLE)
                purple.add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")), columnIndex, rowIndex);
            else if( color == CARD_COLOR_TYPE.YELLOW)
                yellow.add(new ImageView(AssetManager.getInstance().getImage(card.getName().toLowerCase() + "_mini.png")), columnIndex, rowIndex);

            if (columnIndex == 1) {
                columnIndex = 0;
                rowIndex++;
            }
            columnIndex++;
        }
    }
}
