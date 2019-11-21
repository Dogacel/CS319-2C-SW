package SevenWonders.GameplayUI;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.Network.Client;
import SevenWonders.Network.ILobbyListener;
import SevenWonders.Network.Requests.LobbyUpdateRequest;
import SevenWonders.Network.Server;
import SevenWonders.Network.User;
import SevenWonders.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class LobbyController implements Initializable, ILobbyListener {

    @FXML
    private VBox playerVBox;

    @FXML
    private Button readyButton;


    @FXML
    private ChoiceBox<String> choice;

    private User[] userList;
    private Vector<Parent> userGridList;


    @FXML
    public void readyButtonPressed(MouseEvent event)
    {
        if (Client.getInstance().getUser().isAdmin()) {
            Client.getInstance().sendStartGameRequest();
        } else {
            Client.getInstance().sendGetReadyRequest(!Client.getInstance().getUser().isReady());
        }
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
            if (Client.getInstance().getUser().isAdmin()) {
                readyButton.setText("Start game");
            }
        });

    }

    public void addAIButtonPressed(MouseEvent event) {
        AI_DIFFICULTY difficulty = AI_DIFFICULTY.valueOf(choice.getValue());
        Client.getInstance().sendAddAIPlayerRequest(difficulty);
    }

    public void fillAIButtonPressed(MouseEvent event) {
        AI_DIFFICULTY difficulty = AI_DIFFICULTY.valueOf(choice.getValue());
        for (int i = 0 ; i < 7 ; i++) {
            Client.getInstance().sendAddAIPlayerRequest(difficulty);
        }
    }

    @Override
    public void onUpdateLobbyRequest(LobbyUpdateRequest request) {
        Platform.runLater(() -> {
            // TODO: Make an array
            userList = request.users;
            playerVBox.getChildren().clear();
            for (User user : userList) {
                if (user == null)
                    continue;
                Parent root = AssetManager.getInstance().getSceneByNameForce("LobbyPlayerGrid.fxml");
                playerVBox.getChildren().add(root);
                Label name = (Label) root.lookup("#name");
                Label ready = (Label) root.lookup("#ready");
                Button kick = (Button) root.lookup("#kick");

                if (!Client.getInstance().getUser().isAdmin() || user.isAdmin()) {
                    kick.setVisible(false);
                }

                name.setText(user.getUsername());
                ready.setText(user.isReady() ? "Ready" : "Not ready");
                kick.setOnMouseClicked((event) -> {
                    Client.getInstance().sendKickRequest(user.getUsername());
                });
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

