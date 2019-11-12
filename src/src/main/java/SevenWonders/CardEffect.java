package SevenWonders;

public class CardEffect {
    private CARD_EFFECT_TYPE effectType;
    private int victoryPoints;
    private int gold;
    private int shields;
    private Resources[] resources;

    public CARD_EFFECT_TYPE getEffectType() {
        return effectType;
    }

    public void setEffectType(CARD_EFFECT_TYPE effectType) {
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

    public Resources[] getResources() {
        return resources;
    }

    public void setResources(Resources[] resources) {
        this.resources = resources;
    }
}
