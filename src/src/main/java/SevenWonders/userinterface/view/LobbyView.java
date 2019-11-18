package SevenWonders.userinterface.view;

import SevenWonders.userinterface.model.LobbyModel;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javax.swing.text.html.ImageView;

public class LobbyView {
    private ChoiceBox multiSinglePlayerBox;
    private ComboBox playerNumber;
    private ImageView barImage;
    private Label[] playerNames;
    private Label gameId;
    private LobbyModel model;
    private Button backButton;
    private Button readyButton;

    public LobbyView()
    {

    }
}
