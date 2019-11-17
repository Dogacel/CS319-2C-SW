package SevenWonders.GameLogic;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;

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
        for(int i = 0; i < playerControllers.length; i++ ) {
            playerControllers[i].makeMove();
        }

        for(int i = 0; i < playerControllers.length; i++){

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
                    //TODO
                    break;
                case GET_MONEY_AND_VP_PER_YELLOW:
                    //TODO
                    break;
            }

            playerControllers[i].updateCurrentMove(null); //Clear the players move
        }

    }

    public void discardCard(Card card) {
        discardPileController.discardCard(card);
    }

    public boolean shiftCardsRight()
    {
        //TODO Might not need this method, instead perform shift in GameController?
        return model.getCurrentAge() == 2;
    }
}