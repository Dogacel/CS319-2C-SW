package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

import java.util.Map;

public class WonderStage {
    private Map<RESOURCE_TYPE, Integer> requiredResources;
    private WonderEffect wonderEffect;

    public WonderStage(Map<RESOURCE_TYPE, Integer> requiredResources, WonderEffect wonderEffect)
    {
        this.requiredResources = requiredResources;
        this.wonderEffect = wonderEffect;
    }

    public Map<RESOURCE_TYPE, Integer> getRequiredResources() {
        return requiredResources;
    }

    public WonderEffect getWonderEffect() {
        return wonderEffect;
    }
}
