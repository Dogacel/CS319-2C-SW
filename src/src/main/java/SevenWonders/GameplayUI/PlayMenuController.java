package SevenWonders.GameplayUI;
import SevenWonders.Network.Client;
import SevenWonders.Network.Server;
import SevenWonders.SceneManager;
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
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        joinGameButtonReleased(event);
    }
    @FXML
    public void joinGameButtonPressed( MouseEvent event) {

    }
    @FXML
    public void joinGameButtonReleased(MouseEvent event) {
        Client client = new Client( ipInputField.getText(), 8080, nameInputField.getText()); //TODO maybe static client!
        client.sendConnectRequest( nameInputField.getText());
        SceneManager.getInstance().changeScene("Lobby.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
