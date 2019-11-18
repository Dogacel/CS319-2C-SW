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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable, ILobbyListener {

    private User[] userList;
    private LobbyModel model;
    // public LobbyView view;
    @FXML
    public Button readyButton;
    @FXML
    public Button backButton;
    @FXML
    public Button kickButton1;
    @FXML
    public Button kickButton2;
    @FXML
    public Button kickButton3;
    @FXML
    public Button kickButton4;
    @FXML
    public Button kickButton5;
    @FXML
    public Button kickButton6;
    @FXML
    public Button kickButton7;

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
        SceneManager.getInstance().changeScene("MainMenu.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            Client.getInstance().setLobbyListener(this);
        });
//        if (!Client.getInstance().getInstance().getUser().isAdmin()) {
//            // Set kick buttons visible
//            kickButton1.setVisible(false);
//            kickButton2.setVisible(false);
//            kickButton3.setVisible(false);
//            kickButton4.setVisible(false);
//            kickButton5.setVisible(false);
//            kickButton6.setVisible(false);
//            kickButton7.setVisible(false);
//        }
    }

    @Override
    public void onUpdateLobbyRequest(LobbyUpdateRequest request) {
        userList = request.users;
    }

    @Override
    public void onStartGameRequest() {
        SceneManager.getInstance().changeScene("GameplayView.fxml");
    }

    @Override
    public void onDisconnect() {
        backButtonPressed(null);
    }
}
