package SevenWonders.GameLogic.Move;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;

import java.util.Vector;

public class MoveModel {
    private int playerID;
    private int selectedCardID;
    private ACTION_TYPE action;
    private Vector<TradeAction> trades; //ask

    @Override
    public String toString() {
        String result = "";
        Card card = AssetManager.getInstance().getCardByID(selectedCardID);
        result += action.name() + " " + card.getName() + "\n";
        for (TradeAction trade : trades) {
            result += "Trade: " + trade.getSelectedResource().name() + "\n";
        }
        return result;
    }

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
