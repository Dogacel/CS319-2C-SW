package SevenWonders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AssetManager {
    //properties
    private static AssetManager managerInstance = null; //Singleton class, holds one static instance of itself
    Map<String, Image> imageMap;
    Map<String, Parent> sceneMap;

    //constructor
    private AssetManager() {
        imageMap = new HashMap<>();
        sceneMap = new HashMap<>();
        loadImages();
        loadScenes();
    }

    //methods

    /**
     * Static method to create a AssetManager instance
     * @return AssetManager instance
     */
    public static AssetManager getInstance() {
        if ( managerInstance == null) {
            managerInstance = new AssetManager();
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
            if( f.getName().matches(".*(\\.(png|jpg|jpeg))"))
                imageMap.put(f.getName(), new Image("ui-images/" + f.getName()));
        }
    }

    private void loadScenes() {
        URL sceneResourcesURL = getClass().getClassLoader().getResource("fxml-scenes");
        assert sceneResourcesURL != null;
        File dir = new File(sceneResourcesURL.getPath());

        for( File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().endsWith(".fxml"))
            {
                try {
                    sceneMap.put(f.getName(), FXMLLoader.load(getClass().getClassLoader().getResource("fxml-scenes/" + f.getName())));
                    if(sceneMap.isEmpty()){
                        System.out.println("aaaaaaxxxxxxxxxxaaaa");
                    }
                }
                catch ( IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Parent getSceneByName(String sceneName) {
        return sceneMap.get(sceneName);
    }
}