package SevenWonders.GameLogic;

public class Hero {
    private String name;
    private HERO_EFFECT_TYPE heroEffect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HERO_EFFECT_TYPE getHeroEffect() {
        return heroEffect;
    }

    public void setHeroEffect(HERO_EFFECT_TYPE heroEffect) {
        this.heroEffect = heroEffect;
    }
}
