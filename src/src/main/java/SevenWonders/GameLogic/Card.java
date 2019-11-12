package SevenWonders.GameLogic;
import java.util.Map;

public class Card {
    private int id;
    private String name;
    private int goldCost;
    private Map<RESOURCE_TYPE, Integer> resourceCost;
    private int[] preConditionCardIDs;
    private CardEffect cardEffect;
    private CARD_COLOR_TYPE color; //This may change to ENUM

    public Card(){
        this.id = -1;
        this.name = "Undefined";
        this.goldCost = -1;
        this.resourceCost = null;
        this.preConditionCardIDs = null;
        this.cardEffect = null;
        this.color = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoldCost() {
        return goldCost;
    }

    public void setGoldCost(int goldCost) {
        this.goldCost = goldCost;
    }

    public Map<RESOURCE_TYPE, Integer> getResourceCost() {
        return resourceCost;
    }

    public void setResourceCost(Map<RESOURCE_TYPE, Integer> resourceCost) {
        this.resourceCost = resourceCost;
    }

    public int[] getPreConditionCardIDs() {
        return preConditionCardIDs;
    }

    public void setPreConditionCardIDs(int[] preConditionCardIDs) {
        this.preConditionCardIDs = preConditionCardIDs;
    }

    public CardEffect getCardEffect() {
        return cardEffect;
    }

    public void setCardEffect(CardEffect cardEffect) {
        this.cardEffect = cardEffect;
    }

    public CARD_COLOR_TYPE getColor() {
        return color;
    }

    public void setColor(CARD_COLOR_TYPE color) {
        this.color = color;
    }
}
