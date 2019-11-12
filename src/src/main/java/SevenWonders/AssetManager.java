package SevenWonders;

import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AssetManager {
    //properties
    private static AssetManager managerInstance = null; //Singleton class, holds one static instance of itself
    Map<String, Image> imageMap;

    //constructor
    private AssetManager() {
        imageMap = new HashMap<>();
        loadImages();
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
        URL imageResourcesURL = getClass().getClassLoader().getResource("images");
        assert imageResourcesURL != null;
        File dir = new File(imageResourcesURL.getPath());

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            System.out.println(f.getName());
            imageMap.put(f.getName(), new Image("images/" + f.getName()));
        }
    }
}