package SevenWonders.GameLogic.Game;

import SevenWonders.GameLogic.Deck.DeckModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import com.google.gson.annotations.Expose;

public class GameModel {

    private final int NUMBER_OF_PLAYERS = 7;

    private int currentAge;
    private int currentTurn;
    private DiscardPileModel discardPile;

    private transient DeckModel deck;

    private PlayerModel[] playerList;
    private int playerCount;

    private boolean ended;

    public GameModel()
    {
        this.currentAge = 1;
        this.currentTurn = 1;
        discardPile = new DiscardPileModel();
        deck = new DeckModel();
        playerList = new PlayerModel[NUMBER_OF_PLAYERS];
        playerCount = 0;
        ended = false;
    }

    public int getCurrentAge() { return currentAge; }

    public int getCurrentTurn() { return currentTurn; }

    public DiscardPileModel getDiscardPile() { return discardPile; }

    public DeckModel getDeck() { return deck; }

    public PlayerModel[] getPlayerList() { return playerList; }

    public void incrementCurrentTurn(){
        currentTurn++;
    }

    public void incrementCurrentAge() {
        if (!isGameEnded())
        {
            currentAge++;
            currentTurn = 1;
        }
    }

    public boolean isGameEnded()
    {
        return currentAge == 3 && currentTurn == 6;  //Might change according to design choices in GameController
    }

    public void addPlayer(PlayerModel model){
        playerList[playerCount] = model;
        playerCount++;
    }

    public void setGameFinished(boolean state){ ended = true; }

    public boolean getGameFinished(){ return ended; }


    public PlayerModel getLeftPlayer(int id){
        return playerList[(id + 6) % 7];
    }

    public PlayerModel getRightPlayer(int id){
        return playerList[(id + 1) % 7];
    }

}
