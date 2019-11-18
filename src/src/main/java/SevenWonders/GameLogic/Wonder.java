package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;

public class Wonder {
    //TODO loading of the wonders should be done
    private WONDER_TYPE wonderType;
    private RESOURCE_TYPE resource;
    private WonderStage[] stages;
    private int currentStage;
    private God god;

    public Wonder(WONDER_TYPE wonderType, RESOURCE_TYPE resource, WonderStage[] stages, God god)
    {
        this.wonderType = wonderType;
        this.resource = resource;
        this.stages = stages;
        this.currentStage = 0;
        this.god = god;
    }


    public RESOURCE_TYPE getResource() {
        return resource;
    }

    public WONDER_TYPE getWonderType() { return wonderType; }

    public WonderStage[] getStages() {
        return stages;
    }

    public int getCurrentStageIndex() { return currentStage; }

    public God getGod() { return god; }

    public void upgradeStage() {
        if (isUpgradeable())
            currentStage++;
    }

    public boolean isUpgradeable() {
        return currentStage <= 2;
    }

    public WonderStage getCurrentStage() {
        if (isUpgradeable()) {
            return stages[currentStage];
        }
        return null;
    }
}
