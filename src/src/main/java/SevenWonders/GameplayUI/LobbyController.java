package SevenWonders.GameplayUI;

import SevenWonders.Network.Client;
import SevenWonders.Network.ILobbyListener;
import SevenWonders.Network.Requests.LobbyUpdateRequest;
import SevenWonders.Network.User;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable, ILobbyListener {

    private User[] userList;
    private LobbyModel model;
    // public LobbyView view;
    @FXML
    public Button readyButton, backButton, kickButton1, kickButton2, kickButton3, kickButton4, kickButton5, kickButton6, kickButton7;
    private Button[] buttons;

    @FXML
    public Label player1, player2, player3, player4, player5, player6, player7;
    private Label[] players;

    public LobbyController()
    {
        this.model = new LobbyModel();
    }

    public void setReady(boolean isReady)
    {
        Client.getInstance().sendGetReadyRequest(isReady);
    }

    @FXML
    public void kickPlayer1(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[0].getUsername());
    }
    @FXML
    public void kickPlayer2(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[1].getUsername());
    }
    @FXML
    public void kickPlayer3(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[2].getUsername());
    }
    @FXML
    public void kickPlayer4(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[3].getUsername());
    }
    @FXML
    public void kickPlayer5(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[4].getUsername());
    }
    @FXML
    public void kickPlayer6(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[5].getUsername());
    }
    @FXML
    public void kickPlayer7(MouseEvent event)
    {
        Client.getInstance().sendKickRequest(userList[6].getUsername());
    }

    @FXML
    public void readyButtonPressed(MouseEvent event)
    {

        setReady(true);
        

    }

    @FXML
    public void backButtonPressed(MouseEvent event)
    {
        SceneManager.getInstance().changeScene("GameplayView.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            Client.getInstance().setLobbyListener(this);
            Client.getInstance().sendGetReadyRequest(false);
            buttons = new Button[]{kickButton1,kickButton2,kickButton3,kickButton4,kickButton5,kickButton6,kickButton7};
            players = new Label[]{player1,player2,player3,player4,player5,player6,player7};
        });

    }

    @Override
    public void onUpdateLobbyRequest(LobbyUpdateRequest request) {
        Platform.runLater(() -> {
            // TODO: Make an array
            userList = request.users;
            for (int i = 0 ; i < 7 ; i++) {
                if (userList[i] != null)
                    players[i].setText(userList[i].getUsername());
                else {
                    players[i].setText("Empty");
                }
            }
             if (!Client.getInstance().getUser().isAdmin()) {
                // Set kick buttons visible
                for (Button b : buttons) {
                    b.setVisible(false);
                }
            }
        });
    }

    @Override
    public void onStartGameRequest() {
        SceneManager.getInstance().changeScene("GameplayView.fxml");
    }

    @Override
    public void onDisconnect() {
        SceneManager.getInstance().changeScene("MainMenu.fxml");
    }
}
