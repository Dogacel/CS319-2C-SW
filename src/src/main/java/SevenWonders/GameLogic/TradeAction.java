package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

public class TradeAction {
    private int playerID;
    private int tradedPlayerID;
    private int selectedCardID;
    private RESOURCE_TYPE selectedResource;


    public TradeAction(int playerID, int tradedPlayerID, int selectedCardID, RESOURCE_TYPE selectedResource) {
        this.playerID = playerID;
        this.tradedPlayerID = tradedPlayerID;
        this.selectedCardID = selectedCardID;
        this.selectedResource = selectedResource;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getTradedPlayerID() {
        return tradedPlayerID;
    }

    public int getSelectedCardID() {
        return selectedCardID;
    }

    public RESOURCE_TYPE getSelectedResource() {
        return selectedResource;
    }
}
