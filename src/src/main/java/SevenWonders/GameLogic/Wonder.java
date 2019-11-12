package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

public class Wonder {
    private int id;
    private String name;
    private RESOURCE_TYPE resource;
    private WonderStage[] stages;
    private int currentStage;
    private God god;
    private int wonderStageIndex;

    public Wonder(int id, String name, RESOURCE_TYPE resource, WonderStage[] stages, God god)
    {
        this.id = id;
        this.name = name;
        this.resource = resource;
        this.stages = stages;
        this.currentStage = 0;
        this.god = god;
        this.wonderStageIndex = -1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RESOURCE_TYPE getResource() {
        return resource;
    }

    public WonderStage[] getStages() {
        return stages;
    }

    public int getCurrentStage() { return currentStage; }

    public God getGod() { return god; }

    public void upgradeStage() {
        if (isUpgradeable())
            currentStage++;
    }

    public boolean isUpgradeable() {
        return currentStage <= 2;
    }

    public WonderStage getCurrentStage() {
        if (wonderStageIndex == -1) {
            return null;
        }
        return stages[wonderStageIndex];
    }

    public void setWonderStageIndex( int index) {
        this.wonderStageIndex = index;
    }
}
