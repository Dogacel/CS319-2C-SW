package SevenWonders.GameLogic;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;

import java.util.Vector;

public class GameController {
    private final int MAX_NO_OF_PLAYERS = 7;

    private PlayerController[] playerControllers;
    private DiscardPileController discardPileController;
    private DeckController deckController;
    private GameModel model;

    public GameController(){

        model = new GameModel();

        this.playerControllers = new PlayerController[MAX_NO_OF_PLAYERS];
        PlayerModel[] players = model.getPlayerList();

        for ( int index = 0; index < MAX_NO_OF_PLAYERS; index++)
        {
            playerControllers[index] = new PlayerController( players[index], this);
        }

        this.discardPileController = new DiscardPileController( model.getDiscardPile());
        this.deckController = new DeckController(( model.getDeck()));
    }

    public boolean checkMoveIsValid(MoveModel move){
        int id = move.getPlayerID();


        //TODO May change return MoveController.getInstance().playerCanMakeMove( move, playerControllers[id]);

        return true; //TODO Placeholder
    }

    public void updateCurrentMove(int playerID, MoveModel move){

        playerControllers[playerID].updateCurrentMove(move);
    }

    public void makeMoves()
    {
        for (PlayerController playerController : playerControllers) {
            playerController.makeMove();
        }

        for(int i = 0; i < playerControllers.length; i++){
            //Check for possible updates in gold and shields of the player because of the played card
            //Done using the move of the player, therefore the move is not deleted until the updates are performed
            int cardCount = 0;
            PlayerController myPlayerController = playerControllers[i];
            PlayerController leftPlayerController = playerControllers[(i + 6) % 7];
            PlayerController rightPlayerController = playerControllers[(i + 1) % 7];

            CardEffect effect = AssetManager.getInstance().getCardByID(
                    playerControllers[i].getCurrentMove().getSelectedCardID()).getCardEffect();

            switch (effect.getEffectType()){
                case GRANT_SHIELDS:
                    playerControllers[i].setShields(effect.getShields() + playerControllers[i].getShields());
                    break;
                case GET_MONEY:
                    playerControllers[i].setGold(effect.getGold() + playerControllers[i].getGold());
                    break;
                case GET_MONEY_FOR_BROWN_CARD:
                    cardCount = 0;
                    for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                            cardCount++;
                        }
                    }
                    for (Card card : leftPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                            cardCount++;
                        }
                    }
                    for (Card card : rightPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                            cardCount++;
                        }
                    }
                    myPlayerController.setGold(cardCount + myPlayerController.getGold());
                    break;
                case GET_MONEY_FOR_GRAY_CARD:
                    cardCount = 0;

                    for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                            cardCount++;
                        }
                    }
                    for (Card card : leftPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                            cardCount++;
                        }
                    }
                    for (Card card : rightPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                            cardCount++;
                        }
                    }
                    myPlayerController.setGold( cardCount * 2 + myPlayerController.getGold());
                    break;
                case GET_MONEY_AND_VP_PER_BROWN:
                    cardCount = 0;

                    for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                            cardCount++;
                        }
                    }

                    myPlayerController.setGold(cardCount + myPlayerController.getGold());
                case GET_MONEY_AND_VP_PER_GRAY:
                    cardCount = 0;

                    for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                            cardCount++;
                        }
                    }
                    myPlayerController.setGold( cardCount * 2 + myPlayerController.getGold());
                    break;
                case GET_MONEY_AND_VP_PER_WONDER:
                    int goldToAdd = (playerControllers[i].getWonder().getCurrentStage() + 1) * 3;

                    myPlayerController.setGold( goldToAdd + myPlayerController.getGold() );
                    break;
                case GET_MONEY_AND_VP_PER_YELLOW:
                    cardCount = 0;

                    for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                        if (card.getColor() == CARD_COLOR_TYPE.YELLOW) {
                            cardCount++;
                        }
                    }
                    myPlayerController.setGold( cardCount + myPlayerController.getGold());
                    break;
            }

            playerControllers[i].updateCurrentMove(null); //Clear the players move
        }
        //TODO if age is over with this turn, do not shift cards and discard the last card
        shiftCards();

    }

    public void discardCard(Card card) {
        discardPileController.discardCard(card);
    }

    private void shiftCards()
    {
        Vector<Card> tmp, tmp1; //Used for shifting purposes
        tmp = playerControllers[0].getHand();

        if (model.getCurrentAge() == 2 ) //If Age is 2, shift cards right
        {
            for( int i = 0; i < playerControllers.length; i++){
                PlayerController rightPlayerController = playerControllers[(i + 1) % 7];

                tmp1 = rightPlayerController.getHand();
                rightPlayerController.setHand(tmp);
                tmp = tmp1;
            }
        }
        else{ //If Age is not 2, shift cards left
            for( int i = 0; i < playerControllers.length; i++){
                PlayerController myPlayerController = playerControllers[i];
                PlayerController rightPlayerController = playerControllers[(i + 1) % 7];

                myPlayerController.setHand(rightPlayerController.getHand());
            }
            playerControllers[0].setHand(tmp);
        }
    }
}
