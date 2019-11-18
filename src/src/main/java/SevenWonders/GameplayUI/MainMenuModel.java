package SevenWonders.GameplayUI;

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

