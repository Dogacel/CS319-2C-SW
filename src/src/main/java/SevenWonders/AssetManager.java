package SevenWonders;

import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.HERO_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.Hero;
import SevenWonders.GameLogic.Wonder.Wonder;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.*;


public class AssetManager {
    //properties
    private static AssetManager managerInstance = null; //Singleton class, holds one static instance of itself
    private static final int NUMBER_OF_AGES = 3;
    private static final int CARDS_PER_AGE = 49;

    private Gson gson;

    Map<String, Image> imageMap;
    Map<String, Parent> sceneMap;
    Map<Integer, Card> cardMap;
    Map<WONDER_TYPE, Wonder> wonderMap;
    Map<HERO_EFFECT_TYPE, Vector<Hero>> heroMap;

    public void initialize() {
        gson = new Gson();
        imageMap = new HashMap<>();
        sceneMap = new HashMap<>();
        cardMap = new HashMap<>();
        wonderMap = new HashMap<>();
        heroMap = new HashMap<>();
        loadImages();
        // loadScenes();
        loadCards();
        loadWonders();
        loadHeroes();
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
    public Image getImage(String imageId) {
        if (imageMap.containsKey(imageId)) {
            return imageMap.get(imageId);
        } else {
            return null;
        }
    }

    /**
     * Loads all images from a file to the program for efficiency.
     */
    private void loadImages() {
        URL imageResourcesURL = getClass().getClassLoader().getResource("images/ui-images");
        assert imageResourcesURL != null;
        File dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))") && !imageMap.containsKey(f.getName()))
                imageMap.put(f.getName(), new Image("/images/ui-images/" + f.getName()));
        }

        imageResourcesURL = getClass().getClassLoader().getResource("images/cards");
        assert imageResourcesURL != null;
        dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))") && !imageMap.containsKey(f.getName()))
                imageMap.put(f.getName(), new Image("/images/cards/" + f.getName()));
        }

        imageResourcesURL = getClass().getClassLoader().getResource("images/tokens");
        assert imageResourcesURL != null;
        dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))") && !imageMap.containsKey(f.getName()))
                imageMap.put(f.getName(), new Image("/images/tokens/" + f.getName()));
        }

        imageResourcesURL = getClass().getClassLoader().getResource("images/wonders");
        assert imageResourcesURL != null;
        dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))") && !imageMap.containsKey(f.getName()))
                imageMap.put(f.getName(), new Image("/images/wonders/" + f.getName()));
        }
        imageResourcesURL = getClass().getClassLoader().getResource("images/tutorialImages");
        assert imageResourcesURL != null;
        dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))") && !imageMap.containsKey(f.getName()))
                imageMap.put(f.getName(), new Image("/images/tutorialImages/" + f.getName()));
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

        URL cardResourcesURL = getClass().getClassLoader().getResource("game-objects/cards");
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
  
    private void loadWonders(){
        URL wonderResourcesURL = getClass().getClassLoader().getResource("game-objects/wonders");
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
    }

    private void loadHeroes(){
        URL heroResourcesURL = getClass().getClassLoader().getResource("game-objects/heroes");
        assert heroResourcesURL != null;
        File dir = new File(heroResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.getName().endsWith(".json")) {
                try {
                    FileReader fileReader = new FileReader(f.getAbsolutePath());
                    BufferedReader reader = new BufferedReader(fileReader);
                    Hero myHero = gson.fromJson(reader, Hero.class);
                    if (heroMap.get(myHero.getHeroEffect()) == null){
                        Vector<Hero> vectorToAdd = new Vector<>();
                        vectorToAdd.add(myHero);
                        heroMap.put(myHero.getHeroEffect(), vectorToAdd);
                    }
                    else{
                        heroMap.get(myHero.getHeroEffect()).add(myHero);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Card[][] mapDeck() {
        //Creating the deck
        Card[][] cards = new Card[NUMBER_OF_AGES][CARDS_PER_AGE];

        URL ageFileResourceURL = getClass().getClassLoader().getResource("game-objects/age_mapping.json");
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

    public Hero getRandomHeroByEffect(HERO_EFFECT_TYPE effect, Vector<Hero> alreadyHave){
        Random rand = new Random();
        Vector<Hero> heroes = heroMap.get(effect);

        heroes.removeAll(alreadyHave);

        return heroes.get(rand.nextInt(heroes.size()));
    }

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml-scenes/" + sceneName));
            Parent parent = fxmlLoader.load();
            return new Pair<>( parent, fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}