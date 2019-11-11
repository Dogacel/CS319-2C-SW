package SevenWonders;

public class CardModel {
    private String image;

    public CardModel(java.lang.String image) {
        this.image = image;
    }

    public CardModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
