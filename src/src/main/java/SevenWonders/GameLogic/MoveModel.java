package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.ACTION_TYPE;

import java.util.ArrayList;

public class MoveModel {
    private int playerID;
    private int selectedCardID;
    private ACTION_TYPE action;
    private ArrayList<TradeAction> trades; //ask

    public MoveModel(int playerID, int selectedCardID, ACTION_TYPE action)
    {
        this.playerID = playerID;
        this.selectedCardID = selectedCardID;
        this.action = action;
        this.trades = new ArrayList<TradeAction>();
    }

    public int getPlayerID(){
        return playerID;
    }

    public int getSelectedCardID(){
        return selectedCardID;
    }

    public ACTION_TYPE getAction(){
        return action;
    }

    public ArrayList<TradeAction> getTrades() {
        return trades;
    }

    public boolean addTrades( TradeAction trade) {
        if ( trades.contains(trade)) {
            throw new IllegalArgumentException("The value is already in the list."); //discuss
        }
        else {
            trades.add(trade);
            return true;
        }
    }
}
