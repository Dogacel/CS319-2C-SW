package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.ACTION_TYPE;

public class MoveModel {
    private int playerID;
    private int selectedCardID;
    private ACTION_TYPE action;
    //private TradeAction[] trades;  TODO

    public MoveModel(int playerID, int selectedCardID, ACTION_TYPE action)
    {
        this.playerID = playerID;
        this.selectedCardID = selectedCardID;
        this.action = action;
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
}
