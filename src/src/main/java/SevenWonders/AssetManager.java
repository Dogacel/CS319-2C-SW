package SevenWonders;

import SevenWonders.GameLogic.Card;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AssetManager {
    //properties
    private static AssetManager managerInstance = null; //Singleton class, holds one static instance of itself

    private Gson gson;

    Map<String, Image> imageMap;
    Map<String, Parent> sceneMap;
    Map<Integer, Card> cardMap;

    public void initialize() {
        gson = new Gson();
        imageMap = new HashMap<>();
        sceneMap = new HashMap<>();
        cardMap = new HashMap<>();
        loadImages();
        loadScenes();
    }

    /**
     * Static method to create a AssetManager instance
     * @return AssetManager instance
     */
    public static AssetManager getInstance() {
        if ( managerInstance == null) {
            managerInstance = new AssetManager();
            managerInstance.initialize();
        }
        return managerInstance;
    }

    /**
     *
     * @param imageId String ID of an image
     * @return the matching image for that ID
     */
    public Image getImage(String imageId) throws FileNotFoundException {
        if (imageMap.containsKey(imageId)) {
            return imageMap.get(imageId);
        } else {
            throw new FileNotFoundException(imageId);
        }
    }

    /**
     * Loads all images from a file to the program for efficiency.
     */
    private void loadImages() {
        URL imageResourcesURL = getClass().getClassLoader().getResource("ui-images");
        assert imageResourcesURL != null;
        File dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))") && !imageMap.containsKey(f.getName()))
                imageMap.put(f.getName(), new Image("ui-images/" + f.getName()));
        }
    }

    private void loadScenes() {
        URL sceneResourcesURL = getClass().getClassLoader().getResource("fxml-scenes");
        assert sceneResourcesURL != null;
        File dir = new File(sceneResourcesURL.getPath());

        for( File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().endsWith(".fxml") && !sceneMap.containsKey(f.getName()))
            {
                try {
                    sceneMap.put(f.getName(), FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml-scenes/" + f.getName()))));
                }
                catch ( IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadCards(){
        URL cardResourcesURL = getClass().getClassLoader().getResource("cards");
        assert cardResourcesURL != null;
        File dir = new File(cardResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().endsWith(".json") ) {
                try {
                    FileReader fileReader = new FileReader(f.getAbsolutePath());
                    BufferedReader reader = new BufferedReader(fileReader);
                    Card myCard = gson.fromJson(reader, Card.class);
                    cardMap.put(myCard.getId(), myCard);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Card getCardByID(int cardID){
        return cardMap.get(cardID);
    }

    public Parent getSceneByName(String sceneName) {
        return sceneMap.get(sceneName);
    }

    public Parent getSceneByNameForce(String sceneName) {
        try {
           return FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml-scenes/" + sceneName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}