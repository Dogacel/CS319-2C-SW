package SevenWonders;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class AssetManager {
    //properties
    private static AssetManager managerInstance = null; //Singleton class, holds one static instance of itself
    Map<String, Image> imageMap;

    //constructor
    private AssetManager() {
        imageMap = new HashMap<>();
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
    public Image getImage(String imageId) {
        if ( !imageMap.containsKey(imageId)) {
            return imageMap.get(imageId);
        }
        else {
            System.out.println("Cannot find image in the map!");
        }
        return null;
    }

    /**
     * Loads all images from a file to the program for efficiency.
     */
    private void loadImages() {
        BufferedReader r;
        try {
            r = new BufferedReader( new FileReader("images.txt")); //filename to be determined?
            String imageLocation  = r.readLine();
            //read every line which contains a image address, push the address and a Image object inside the map
            while ( imageLocation != null) {
                imageMap.put(imageLocation, new Image(imageLocation));
                imageLocation = r.readLine();
            }
            r.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}