package SevenWonders.UserInterface;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;

public class WonderSelectionViewModel {
    private WONDER_TYPE selectedWonder;
    WonderSelectionViewModel(){
        selectedWonder = null;
    }

    void setSelectedWonder(WONDER_TYPE selectedWonder) {
        this.selectedWonder = selectedWonder;
    }

    WONDER_TYPE getSelectedWonder(){
        return selectedWonder;
    }
}
