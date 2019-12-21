package SevenWonders.GameLogic.Move;

import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;

import java.util.Objects;

public class TradeAction {
    private int playerID;
    private int tradedPlayerID;
    private int selectedCardID;
    private RESOURCE_TYPE selectedResource;


    @Override
    public String toString() {
        return "TradeAction{" +
                "playerID=" + playerID +
                ", tradedPlayerID=" + tradedPlayerID +
                ", selectedCardID=" + selectedCardID +
                ", selectedResource=" + selectedResource +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeAction that = (TradeAction) o;
        return playerID == that.playerID &&
                tradedPlayerID == that.tradedPlayerID &&
                selectedCardID == that.selectedCardID &&
                selectedResource == that.selectedResource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, tradedPlayerID, selectedCardID, selectedResource);
    }

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
