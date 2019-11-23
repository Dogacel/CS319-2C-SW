package SevenWonders.GameLogic.Deck.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

import java.util.Map;

public class Card {
    private int id;
    private String name;
    private Map<RESOURCE_TYPE, Integer> requirements;
    private String[] buildingChain;
    private CardEffect cardEffect;
    private CARD_COLOR_TYPE color;

    public Card(int id,
                String name,
                Map<RESOURCE_TYPE, Integer> requirements,
                String[] buildingChain,
                CardEffect cardEffect,
                CARD_COLOR_TYPE color){
        this.id = id;
        this.name = name;
        this.requirements = requirements;
        this.buildingChain = buildingChain;
        this.cardEffect = cardEffect;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<RESOURCE_TYPE, Integer> getRequirements() {
        return requirements;
    }

    public String[] getBuildingChain(){ return buildingChain; }

    public CardEffect getCardEffect() {
        return cardEffect;
    }

    public CARD_COLOR_TYPE getColor() {
        return color;
    }
}
