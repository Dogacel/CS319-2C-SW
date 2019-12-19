package SevenWonders.UserInterface;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.Network.Client;
import SevenWonders.Network.ILobbyListener;
import SevenWonders.Network.Requests.LobbyUpdateRequest;
import SevenWonders.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;


public class WonderSelectionViewController implements Initializable, ILobbyListener {
    private WonderSelectionViewModel model;
    private Client client;

    public WonderSelectionViewController(){
        this.model = new WonderSelectionViewModel();
    }

    public void initialize(URL url, ResourceBundle rb){
        client = Client.getInstance();
    }

    @FXML
    ImageView artemis, colossus, ghiza, babylon, halicarnassus, lighthouse, statueOfZeus;

    @FXML
    Button button;

    public void artemisPressed(){
        model.setSelectedWonder( WONDER_TYPE.TEMPLE_OF_ARTEMIS);
    }

    public void colossusPressed(){
        model.setSelectedWonder( WONDER_TYPE.COLOSSUS_OF_RHODES);
    }

    public void gizaPressed(){
        model.setSelectedWonder( WONDER_TYPE.GREAT_PYRAMID_OF_GIZA);
    }

    public void babylonPressed(){
        model.setSelectedWonder( WONDER_TYPE.HANGING_GARDENS);
    }

    public void halicarnassusPressed(){
        model.setSelectedWonder( WONDER_TYPE.MAUSOLEUM_OF_HALICARNASSUS);
    }

    public void lighthousePressed(){
        model.setSelectedWonder( WONDER_TYPE.LIGHTHOUSE_OF_ALEXANDRIA);
    }

    public void statueOfZeusPressed(){
        model.setSelectedWonder(WONDER_TYPE.STATUE_OF_ZEUS );
    }

    public void startButtonPressed(){
        if( model.getSelectedWonder() != null) {
            client.sendSelectWonderRequest(model.getSelectedWonder());
        }
        System.out.println( model.getSelectedWonder());
    }

    @Override
    public void onUpdateLobbyRequest(LobbyUpdateRequest request) {
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
