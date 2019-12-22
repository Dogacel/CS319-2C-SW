package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.Network.Client;
import SevenWonders.Network.ILobbyListener;
import SevenWonders.Network.Requests.LobbyUpdateRequest;
import SevenWonders.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;


public class WonderSelectionViewController implements Initializable, ILobbyListener {
    private WonderSelectionViewModel model;
    private Client client;

    @FXML
    ImageView artemisImage, rhodesImage, gizaImage, babylonImage, halicarnassusImage, alexandriaImage, zeusImage;

    ImageView artemis1, artemis2, artemis3;
    ImageView rhodes1, rhodes2, rhodes3;
    ImageView giza1, giza2, giza3;
    ImageView babylon1, babylon2, babylon3;
    ImageView halicarnassus1, halicarnassus2, halicarnassus3;
    ImageView lightHouse1, lightHouse2, lightHouse3;
    ImageView zeus1, zeus2, zeus3;
    @FXML
    Button button;

    ImageView[] wonderImages;

    public WonderSelectionViewController(){
        this.model = new WonderSelectionViewModel();

    }

    public void initialize(URL url, ResourceBundle rb){
        client = Client.getInstance();

        wonderImages = new ImageView[7];
        wonderImages[0] = artemisImage;
        wonderImages[1] = zeusImage;
        wonderImages[2] = gizaImage;
        wonderImages[3] = halicarnassusImage;
        wonderImages[4] = rhodesImage;
        wonderImages[5] = babylonImage;
        wonderImages[6] = alexandriaImage;

        artemis1 = new ImageView(AssetManager.getInstance().getImage("templeofartemis_1.png"));
        artemis2 = new ImageView(AssetManager.getInstance().getImage("templeofartemis_2.png"));
        artemis3 = new ImageView(AssetManager.getInstance().getImage("templeofartemis_3.png"));

        rhodes1 = new ImageView(AssetManager.getInstance().getImage("colossusofrhodes_1.png"));
        rhodes2 = new ImageView(AssetManager.getInstance().getImage("colossusofrhodes_2.png"));
        rhodes3 = new ImageView(AssetManager.getInstance().getImage("colossusofrhodes_3.png"));

        giza1 = new ImageView(AssetManager.getInstance().getImage("greatpyramidofgiza_1.png"));
        giza2 = new ImageView(AssetManager.getInstance().getImage("greatpyramidofgiza_2.png"));
        giza3 = new ImageView(AssetManager.getInstance().getImage("greatpyramidofgiza_3.png"));

        babylon1 = new ImageView(AssetManager.getInstance().getImage("hanginggardens_1.png"));
        babylon2 = new ImageView(AssetManager.getInstance().getImage("hanginggardens_2.png"));
        babylon3 = new ImageView(AssetManager.getInstance().getImage("hanginggardens_3.png"));

        halicarnassus1 = new ImageView(AssetManager.getInstance().getImage("mausoleumofhalicarnassus_1.png"));
        halicarnassus2 = new ImageView(AssetManager.getInstance().getImage("mausoleumofhalicarnassus_2.png"));
        halicarnassus3 = new ImageView(AssetManager.getInstance().getImage("mausoleumofhalicarnassus_3.png"));

        lightHouse1 = new ImageView(AssetManager.getInstance().getImage("lighthouseofalexandria_1.png"));
        lightHouse2 = new ImageView(AssetManager.getInstance().getImage("lighthouseofalexandria_2.png"));
        lightHouse3 = new ImageView(AssetManager.getInstance().getImage("lighthouseofalexandria_3.png"));

        zeus1 = new ImageView(AssetManager.getInstance().getImage("statueofzeus_1.png"));
        zeus2 = new ImageView(AssetManager.getInstance().getImage("statueofzeus_2.png"));
        zeus3 = new ImageView(AssetManager.getInstance().getImage("statueofzeus_3.png"));


    }

    public void artemisPressed(){
        model.setSelectedWonder( WONDER_TYPE.TEMPLE_OF_ARTEMIS);
        setGlowEffect(artemisImage);
    }

    public void colossusPressed(){
        model.setSelectedWonder( WONDER_TYPE.COLOSSUS_OF_RHODES);
        setGlowEffect(rhodesImage);
    }

    public void gizaPressed(){
        model.setSelectedWonder( WONDER_TYPE.GREAT_PYRAMID_OF_GIZA);
        setGlowEffect(gizaImage);
    }

    public void babylonPressed(){
        model.setSelectedWonder( WONDER_TYPE.HANGING_GARDENS);
        setGlowEffect(babylonImage);
    }

    public void halicarnassusPressed(){
        model.setSelectedWonder( WONDER_TYPE.MAUSOLEUM_OF_HALICARNASSUS);
        setGlowEffect(halicarnassusImage);
    }

    public void lighthousePressed(){
        model.setSelectedWonder( WONDER_TYPE.LIGHTHOUSE_OF_ALEXANDRIA);
        setGlowEffect(alexandriaImage);
    }

    public void statueOfZeusPressed(){
        model.setSelectedWonder(WONDER_TYPE.STATUE_OF_ZEUS );
        setGlowEffect(zeusImage);
    }

    private void setGlowEffect(ImageView i){
        removeGlowEffects();
        int depth = 150;//Setting the uniform variable for the glow width and height
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.GOLD);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        i.setEffect(borderGlow);
    }

    private void removeGlowEffects(){
        for (ImageView i : wonderImages){
            if(i.getEffect() != null)
                i.setEffect(null);
        }
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
        SceneManager.getInstance().changeScene("MainMenuView.fxml");
    }
}