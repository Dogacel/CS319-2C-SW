package SevenWonders;

import SevenWonders.GameLogic.Card;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.GameLogic.Wonder;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AssetManager {
    //properties
    private static AssetManager managerInstance = null; //Singleton class, holds one static instance of itself
    private static final int NUMBER_OF_AGES = 3;
    private static final int CARDS_PER_AGE = 49;

    private Gson gson;

    Map<String, ImageView> imageMap;
    Map<String, Parent> sceneMap;
    Map<Integer, Card> cardMap;
    Map<WONDER_TYPE, Wonder> wonderMap;

    public void initialize() {
        gson = new Gson();
        imageMap = new HashMap<>();
        sceneMap = new HashMap<>();
        cardMap = new HashMap<>();
        wonderMap = new HashMap<>();
        loadImages();
        // loadScenes();
        loadCards();
        //loadWonders();
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
    public ImageView getImage(String imageId) throws FileNotFoundException {
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
                imageMap.put(f.getName(), new ImageView("ui-images/" + f.getName()));
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

    private void loadCards() {

        URL cardResourcesURL = getClass().getClassLoader().getResource("cards");
        assert cardResourcesURL != null;
        File dir = new File(cardResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.getName().endsWith(".json")) {
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

    /*
    private void loadWonders(){
        URL wonderResourcesURL = getClass().getClassLoader().getResource("wonders");
        assert wonderResourcesURL != null;
        File dir = new File(wonderResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.getName().endsWith(".json")) {
                try {
                    FileReader fileReader = new FileReader(f.getAbsolutePath());
                    BufferedReader reader = new BufferedReader(fileReader);
                    Wonder myWonder = gson.fromJson(reader, Wonder.class);
                    wonderMap.put(myWonder.getWonderType(), myWonder);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public Card[][] mapDeck() {
        //Creating the deck
        Card[][] cards = new Card[NUMBER_OF_AGES][CARDS_PER_AGE];

        URL ageFileResourceURL = getClass().getClassLoader().getResource("age_mapping.json");
        File ageFile = new File(ageFileResourceURL.getPath());
        try {
            FileReader fileReader = new FileReader(ageFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(fileReader);
            int[][] arr = gson.fromJson(reader, int[][].class);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    cards[i][j] = cardMap.get(arr[i][j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public Card getCardByID(int cardID){
        return cardMap.get(cardID);
    }

    public Wonder getWonderByType(WONDER_TYPE wonderType){ return wonderMap.get(wonderType); }

    public Parent getSceneByName(String sceneName) {
        try {
            return FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml-scenes/" + sceneName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // return sceneMap.get(sceneName);
    }

    public Parent getSceneByNameForce(String sceneName) {
        try {
           return FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml-scenes/" + sceneName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pair<Parent, Object> getSceneAndController(String sceneName){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent parent = fxmlLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml-scenes/" + sceneName)));
            return new Pair<>( parent, fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}