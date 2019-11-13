package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_EFFECT_TYPE;

import java.util.Map;

public class WonderEffect {
    private WONDER_EFFECT_TYPE effectType;
    private int victoryPoints;
    private int gold;
    private int shields;
    private Map<RESOURCE_TYPE, Integer> resources;

    public WonderEffect(WONDER_EFFECT_TYPE effect_type,
                        int victoryPoints,
                        int gold,
                        int shields,
                        Map<RESOURCE_TYPE,Integer> resources)
    {
        this.effectType = effect_type;
        this.victoryPoints = victoryPoints;
        this.gold = gold;
        this.shields = shields;
        this.resources = resources;
    }


    public WONDER_EFFECT_TYPE getEffectType() {
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
