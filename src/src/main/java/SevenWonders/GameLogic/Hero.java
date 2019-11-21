package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.HERO_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.HERO_TYPE;

public class Hero {
    private HERO_TYPE heroType;
    private HERO_EFFECT_TYPE heroEffect;

    public Hero(HERO_TYPE heroType, HERO_EFFECT_TYPE effect)
    {
        this.heroType = heroType;
        this.heroEffect = effect;
    }

    public HERO_TYPE getHeroType() { return heroType; }

    public HERO_EFFECT_TYPE getHeroEffect() {
        return heroEffect;
    }

}
