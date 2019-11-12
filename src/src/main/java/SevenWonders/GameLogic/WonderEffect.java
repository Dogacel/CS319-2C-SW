package SevenWonders.GameLogic;

import java.util.Map;

public class WonderEffect {
    private WONDER_EFFECT_TYPE effectType;
    private int victoryPoints;
    private int gold;
    private int shields;
    private Map<RESOURCE_TYPE, Integer> resources;

    public WONDER_EFFECT_TYPE getEffectType() {
        return effectType;
    }

    public void setEffectType(WONDER_EFFECT_TYPE effectType) {
        this.effectType = effectType;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public Map<RESOURCE_TYPE, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<RESOURCE_TYPE, Integer> resources) {
        this.resources = resources;
    }
}
