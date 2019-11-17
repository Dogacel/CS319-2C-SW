package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.ACTION_TYPE;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Vector;

public class MoveModel {
    private int playerID;
    private int selectedCardID;
    private ACTION_TYPE action;
    private Vector<TradeAction> trades; //ask

    public MoveModel(int playerID, int selectedCardID, ACTION_TYPE action)
    {
        this.playerID = playerID;
        this.selectedCardID = selectedCardID;
        this.action = action;
        this.trades = new Vector<TradeAction>();
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

    public Vector<TradeAction> getTrades() {
        return trades;
    }

    public boolean addTrade( TradeAction trade) {
        return trades.add(trade);
    }
}
