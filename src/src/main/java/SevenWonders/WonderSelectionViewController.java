package SevenWonders;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.Network.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class WonderSelectionViewController implements Initializable {
    private WonderSelectionViewModel model;
    private Client client;

    public WonderSelectionViewController( Client cl, WonderSelectionViewModel model){
        this.model = model;
        this.client = cl;
    }

    public void initialize(URL url, ResourceBundle rb){}

    @FXML
    ImageView artemis;

    @FXML
    ImageView colossus;

    @FXML
    ImageView ghiza;

    @FXML
    ImageView babylon;

    @FXML
    ImageView halicarnassus;

    @FXML
    ImageView lighthouse;

    @FXML
    ImageView statueOfZeus;

    public void artemisPressed(){
        model.setSelectedWonder( WONDER_TYPE.TEMPLE_OF_ARTEMIS);
    }

    public void colossusPressed(){
        model.setSelectedWonder( WONDER_TYPE.COLOSSUS_OF_RHODES);
    }

    public void ghizaPressed(){
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

    public void selectButtonPressed(){
        if( model.getSelectedWonder() != null)
            client.sendSelectWonderRequest( model.getSelectedWonder());
    }

}
