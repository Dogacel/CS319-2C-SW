package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

public class Wonder {
    private int id;
    private String name;
    private RESOURCE_TYPE resource;
    private WonderStage[] stages;
    private God god;

    public Wonder(int id, String name, RESOURCE_TYPE resource, WonderStage[] stages, God god)
    {
        this.id = id;
        this.name = name;
        this.resource = resource;
        this.stages = stages;
        this.god = god;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RESOURCE_TYPE getResource() {
        return resource;
    }

    public void setResource(RESOURCE_TYPE resource) {
        this.resource = resource;
    }

    public WonderStage[] getStages() {
        return stages;
    }

    public void setStages(WonderStage[] stages) {
        this.stages = stages;
    }

    public God getGod() {
        return god;
    }

    public void setGod(God god) {
        this.god = god;
    }
}
