package SevenWonders.GameplayUI;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;

public class WonderSelectionViewModel {
    private WONDER_TYPE selectedWonder;
    public WonderSelectionViewModel(){
        selectedWonder = null;
    }

    public void setSelectedWonder(WONDER_TYPE selectedWonder) {
        this.selectedWonder = selectedWonder;
    }

    public WONDER_TYPE getSelectedWonder(){
        return selectedWonder;
    }
}
