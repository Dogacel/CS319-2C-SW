package SevenWonders.UserInterface;

import SevenWonders.Network.Client;
import SevenWonders.Network.Server;
import SevenWonders.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PlayMenuController {
    @FXML
    TextField ipInputField;
    @FXML
    TextField nameInputField;

    @FXML
    Label idWarningLabel, nameWarningLabel;

    @FXML
    public void createGameButtonClicked(MouseEvent event) {
        if( !nameInputField.getText().equals("")){
            Server.startServerInstance();

            Client.createClientInstance (ipInputField.getText(), 18232, nameInputField.getText());
            Client.getInstance().sendConnectRequest( nameInputField.getText());
            Client.getInstance().makeAdmin();
            SceneManager.getInstance().changeScene("LobbyView.fxml");
            nameWarningLabel.setVisible(false);
        }
        else{
            nameWarningLabel.setText("enter your name first");
            nameWarningLabel.setVisible(true);
            idWarningLabel.setVisible(false);
        }
    }

    @FXML
    public void joinGameButtonClicked(MouseEvent event) {
        if(!ipInputField.getText().equals("") && !nameInputField.getText().equals("")) {
            Client.createClientInstance (ipInputField.getText(), 18232, nameInputField.getText());
            Client.getInstance().sendConnectRequest( nameInputField.getText());
            SceneManager.getInstance().changeScene("LobbyView.fxml");
            idWarningLabel.setVisible(false);
            nameWarningLabel.setVisible(false);
        }
        if(ipInputField.getText().equals("")){
            idWarningLabel.setText("enter game id first");
            idWarningLabel.setVisible(true);
        }
        if(nameInputField.getText().equals("")){
            nameWarningLabel.setText("enter your name first");
            nameWarningLabel.setVisible(true);
        }
    }

    @FXML
    public void backButtonClicked(MouseEvent mouseEvent) {
        SceneManager.getInstance().changeScene("MainMenuView.fxml");
    }
}
