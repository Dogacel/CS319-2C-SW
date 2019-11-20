package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.GOD_POWER_TYPE;
import SevenWonders.GameLogic.Enums.GOD_TYPE;

public class God {
    private GOD_TYPE       godType;
    private GOD_POWER_TYPE godPower;

    public God(GOD_TYPE godType, GOD_POWER_TYPE godPower){
        this.godType = godType;
        this.godPower = godPower;
    }

    public GOD_TYPE getGodType() { return  godType; }

    public GOD_POWER_TYPE getGodPower() { return godPower; }
}
