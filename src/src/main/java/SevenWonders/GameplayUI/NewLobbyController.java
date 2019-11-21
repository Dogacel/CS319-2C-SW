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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewLobbyController implements Initializable, ILobbyListener {

    @FXML
    private VBox playerVBox;

    private User[] userList;

    public void setReady(boolean isReady)
    {
        Client.getInstance().sendGetReadyRequest(isReady);
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
            playerVBox.getChildren().add(new playerGrid());
        });

    }

    @Override
    public void onUpdateLobbyRequest(LobbyUpdateRequest request) {
        Platform.runLater(() -> {
            // TODO: Make an array
            userList = request.users;
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


    private class playerGrid extends GridPane {
        private Label name, ready;
        private Button kickButton;
        private ChoiceBox<String> choiceBox;
        public playerGrid() {
            name = new Label("NAME");
            ready = new Label("READY");
            kickButton = new Button("KICK");
            choiceBox = new ChoiceBox<String>();

            choiceBox.getItems().add("Player");
            choiceBox.getItems().add("EASY");
            choiceBox.getItems().add("MEDIUM");
            choiceBox.getItems().add("HARD");

            this.addColumn(0, name);
            this.addColumn(1, ready);
            this.addColumn(2, choiceBox);
            this.addColumn(3, kickButton);
        }
    }
}

