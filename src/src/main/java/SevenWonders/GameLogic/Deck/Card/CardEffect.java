package SevenWonders.GameLogic.Deck.Card;

import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

import java.util.Map;

public class CardEffect {
    private CARD_EFFECT_TYPE effectType;
    private int victoryPoints;
    private int gold;
    private int shields;
    private Map<RESOURCE_TYPE, Integer> resources;

    public CardEffect(CARD_EFFECT_TYPE effect_type, int victoryPoints, int gold, int shields, Map<RESOURCE_TYPE,Integer> resources)
    {
        this.effectType = effect_type;
        this.victoryPoints = victoryPoints;
        this.gold = gold;
        this.shields = shields;
        this.resources = resources;
    }

    public CARD_EFFECT_TYPE getEffectType() {
        return effectType;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getGold() {
        return gold;
    }

    public int getShields() {
        return shields;
    }

    public Map<RESOURCE_TYPE, Integer> getResources() {
        return resources;
    }
}
