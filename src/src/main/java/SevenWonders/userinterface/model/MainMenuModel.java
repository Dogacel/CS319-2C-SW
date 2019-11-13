package SevenWonders.userinterface.model;

public class MainMenuModel {
    private String backgroundImage;
    private String barImage;

    public MainMenuModel(String backgroundImage, String barImage)
    {
        this.backgroundImage = backgroundImage;
        this.barImage = barImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBarImage() {
        return barImage;
    }

    public void setBarImage(String barImage) {
        this.barImage = barImage;
    }
}


/*
MainMenuModel:
Attributes:
public String backgroundImage:​ Holds the path to the background image as a string.
35public String barImage:​ Holds the path to the bar image as a string.
Constructor:
MainMenuModel():​ Constructs the main menu model object.

 */