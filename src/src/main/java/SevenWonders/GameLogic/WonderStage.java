package SevenWonders.GameLogic;

import java.util.Map;

public class WonderStage {
    private Map<RESOURCE_TYPE, Integer> requiredResources;
    private WonderEffect wonderEffect;

    public Map<RESOURCE_TYPE, Integer> getRequiredResources() {
        return requiredResources;
    }

    public void setRequiredResources(Map<RESOURCE_TYPE, Integer> requiredResources) {
        this.requiredResources = requiredResources;
    }

    public WonderEffect getWonderEffect() {
        return wonderEffect;
    }

    public void setWonderEffect(WonderEffect wonderEffect) {
        this.wonderEffect = wonderEffect;
    }
}
