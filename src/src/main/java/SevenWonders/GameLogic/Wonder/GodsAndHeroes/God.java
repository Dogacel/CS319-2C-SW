package SevenWonders.GameLogic.Wonder.GodsAndHeroes;

import SevenWonders.GameLogic.Enums.GOD_POWER_TYPE;
import SevenWonders.GameLogic.Enums.GOD_TYPE;

public class God {
    private GOD_TYPE       godType;
    private GOD_POWER_TYPE godPower;
    private boolean        used;
    private int            vpEachTurn;

    public God(GOD_TYPE godType, GOD_POWER_TYPE godPower){
        this.godType = godType;
        this.godPower = godPower;
        this.used = false;
        this.vpEachTurn = 0;
    }

    public GOD_TYPE getGodType() { return  godType; }

    public GOD_POWER_TYPE getGodPower() { return godPower; }

    public boolean isUsed() { return used; }

    public void setUsed() { used = true; }

    public void incrementVpEachTurn(){ vpEachTurn++; }

    public int getVpEachTurn() { return vpEachTurn; }
}
