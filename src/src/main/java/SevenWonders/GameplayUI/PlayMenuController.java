package SevenWonders.GameplayUI;
import SevenWonders.Network.Client;
import SevenWonders.Network.Server;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayMenuController implements  Initializable{
    @FXML
    TextField ipInputField;
    @FXML
    TextField nameInputField;

    @FXML
    public void createGameButtonPressed(MouseEvent event) {

    }
    @FXML
    public void createGameButtonReleased(MouseEvent event) {
        Server.startServerInstance();

        Client.createClientInstance (ipInputField.getText(), 8080, nameInputField.getText());
        Client.getInstance().sendConnectRequest( nameInputField.getText());
        Client.getInstance().makeAdmin();
        SceneManager.getInstance().changeScene("NewLobby.fxml");
    }
    @FXML
    public void joinGameButtonPressed( MouseEvent event) {

    }
    @FXML
    public void joinGameButtonReleased(MouseEvent event) {
        Client.createClientInstance (ipInputField.getText(), 8080, nameInputField.getText());
        Client.getInstance().sendConnectRequest( nameInputField.getText());
        SceneManager.getInstance().changeScene("NewLobby.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
