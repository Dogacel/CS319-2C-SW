package SevenWonders.UserInterface;

import SevenWonders.Network.Client;
import SevenWonders.Network.Server;
import SevenWonders.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PlayMenuController {
    @FXML
    TextField ipInputField;
    @FXML
    TextField nameInputField;

    @FXML
    public void createGameButtonClicked(MouseEvent event) {
        Server.startServerInstance();

        Client.createClientInstance (ipInputField.getText(), 18232, nameInputField.getText());
        Client.getInstance().sendConnectRequest( nameInputField.getText());
        Client.getInstance().makeAdmin();
        SceneManager.getInstance().changeScene("Lobby.fxml");
    }

    @FXML
    public void joinGameButtonClicked(MouseEvent event) {
        Client.createClientInstance (ipInputField.getText(), 18232, nameInputField.getText());
        Client.getInstance().sendConnectRequest( nameInputField.getText());
        SceneManager.getInstance().changeScene("Lobby.fxml");
    }

    @FXML
    public void backButtonClicked(MouseEvent mouseEvent) {
        SceneManager.getInstance().changeScene("MainMenu.fxml");
    }


}
