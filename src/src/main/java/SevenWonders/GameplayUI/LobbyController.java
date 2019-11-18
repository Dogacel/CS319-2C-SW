package SevenWonders.GameplayUI;

import SevenWonders.Network.Client;
import SevenWonders.Network.User;
import SevenWonders.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class LobbyController {

    private User[] userList;
    private LobbyModel model;
    private Client client;
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
        client.sendGetReadyRequest(isReady);
    }

    public void startGameAndChangeToGameView()
    {
        SceneManager.getInstance().changeScene("GameplayView.fxml");
    }

    @FXML
    public void kickPlayer1(MouseEvent event)
    {
        client.sendKickRequest(userList[0].getUsername());
    }
    @FXML
    public void kickPlayer2(MouseEvent event)
    {
        client.sendKickRequest(userList[1].getUsername());
    }
    @FXML
    public void kickPlayer3(MouseEvent event)
    {
        client.sendKickRequest(userList[2].getUsername());
    }
    @FXML
    public void kickPlayer4(MouseEvent event)
    {
        client.sendKickRequest(userList[3].getUsername());
    }
    @FXML
    public void kickPlayer5(MouseEvent event)
    {
        client.sendKickRequest(userList[4].getUsername());
    }
    @FXML
    public void kickPlayer6(MouseEvent event)
    {
        client.sendKickRequest(userList[5].getUsername());
    }
    @FXML
    public void kickPlayer7(MouseEvent event)
    {
        client.sendKickRequest(userList[6].getUsername());
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

    public void setClient(Client client)
    {
        this.client = client;
        if(false) // if not isAdmin
        {
            // Set kick buttons visible
            kickButton1.setVisible(false);
            kickButton2.setVisible(false);
            kickButton3.setVisible(false);
            kickButton4.setVisible(false);
            kickButton5.setVisible(false);
            kickButton6.setVisible(false);
            kickButton7.setVisible(false);

        }
    }


}
