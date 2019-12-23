package SevenWonders.GameLogic.Game;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Deck.Card.CardEffect;
import SevenWonders.GameLogic.Deck.DeckController;
import SevenWonders.GameLogic.Enums.*;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerController;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Vector;

public class GameController {
    private final int MAX_NO_OF_PLAYERS = 7;

    private PlayerController[] playerControllers;
    private DiscardPileController discardPileController;
    private DeckController deckController;
    private GameModel model;
    private int playerCount;
    public GameController(GameModel model){

        this.model = model;

        this.playerControllers = new PlayerController[MAX_NO_OF_PLAYERS];
        playerCount = 0;

        this.discardPileController = new DiscardPileController( model.getDiscardPile());
        this.deckController = new DeckController(( model.getDeck()));

        shuffleDecks();
    }

    public boolean checkMoveIsValid(MoveModel move){
        int id = move.getPlayerID();

        Pair<PlayerModel, PlayerModel> neighbors = new Pair<>(model.getLeftPlayer(move.getPlayerID()), model.getRightPlayer(move.getPlayerID()));

        var m = MoveController.getInstance().playerCanMakeMove(move, playerControllers[id].getPlayer(), neighbors, false);
        for (TradeAction t : m.getValue()) {
            move.addTrade(t);
        }
        return m.getKey();
    }

    public void updateCurrentMove(int playerID, MoveModel move){
        playerControllers[playerID].updateCurrentMove(move);
    }

    public void playTurn(){
        makeMoves();
        if( model.getCurrentTurn() < 6){

            shiftCards();

            model.incrementCurrentTurn();

            updateGodPowers();
        }
        else{
            discardLastCards();
            playEndOfAge();
            if (!model.isGameEnded()) {
                model.incrementCurrentAge();

                if (model.getCurrentAge() <= 3) {
                    dealCards();
                }
            }
        }
    }

    public void dealCards(){
        Card[] cards = deckController.getCurrentDeck(model.getCurrentAge());

        int start = 0;
        int end = 7;
        for( PlayerController playerController : playerControllers){
            Card[] playerHand = new Card[7];
            for (int i = start ; i < end ; i++) {
                playerHand[i-start] = cards[i];
            }
            playerController.setHand(new Vector<Card>(Arrays.asList(playerHand))); //start inclusive, end exclusive
            start += 7;
            end += 7;
        }

        for(Card card: playerControllers[0].getHand()){
            System.out.println("Card Name: " + card.getName());
        }
    }

    private void playEndOfAge(){
        for(int i = 0; i < playerControllers.length; i++){
            int winPoint = 0;
            int totalPoints = 0;
            int totalLosses = 0;
            PlayerController myPlayerController = playerControllers[i];
            PlayerController leftPlayerController = playerControllers[(i + 6) % 7];
            PlayerController rightPlayerController = playerControllers[(i + 1) % 7];

            switch(model.getCurrentAge()){
                case 1:
                    winPoint = 1;
                    break;
                case 2:
                    winPoint = 3;
                    break;
                case 3:
                    winPoint = 5;
                    break;
            }

            if( myPlayerController.getShields() > leftPlayerController.getShields()){ //Player defeats left
                totalPoints += winPoint;
            }
            if( myPlayerController.getShields() > rightPlayerController.getShields()){ //Player defeats right
                totalPoints += winPoint;
            }
            if( myPlayerController.getShields() < leftPlayerController.getShields()){ //Left defeats player
                totalPoints -= 1;
                totalLosses += 1;
            }
            if( myPlayerController.getShields() < rightPlayerController.getShields()){ //Right defeats player
                totalPoints -= 1;
                totalLosses += 1;
            }

            myPlayerController.setWarPoints(myPlayerController.getWarPoints() + totalPoints);
            myPlayerController.setLostWarNumber(myPlayerController.getLostWarNumber() + totalLosses);
        }
    }

    private void updateGodPowers(){
        for(PlayerController playerController: playerControllers){
            GOD_POWER_TYPE godType = playerController.getWonder().getGod().getGodPower();

            if( godType == GOD_POWER_TYPE.VP_EACH_TURN && playerController.getPlayer().getWonder().getGod().isUsed())
                playerController.getPlayer().getWonder().getGod().incrementVpEachTurn();
        }
    }

    private void makeMoves()
    {
        for (PlayerController playerController : playerControllers) {
            if (playerController.getCurrentMove() == null) {
                playerController.updateCurrentMove(new MoveModel(playerController.getId(), playerController.getHand().firstElement().getId(), ACTION_TYPE.DISCARD_CARD));
            }
        }

        for (PlayerController playerController : playerControllers) {
            if (playerController.getCurrentMove().getAction() == ACTION_TYPE.USE_GOD_POWER) {
                switch (playerController.getWonder().getGod().getGodPower()) {
                    case EARTHQUAKE:
                        if (getLeftPlayer(playerController.getId()).getCurrentMove().getAction() == ACTION_TYPE.UPGRADE_WONDER) {
                            getLeftPlayer(playerController.getId()).getCurrentMove().setDiscard();
                        } else {
                            getLeftPlayer(playerController.getId()).getWonder().downgradeStage();
                        }
                        if (getRightPlayer(playerController.getId()).getCurrentMove().getAction() == ACTION_TYPE.UPGRADE_WONDER) {
                            getRightPlayer(playerController.getId()).getCurrentMove().setDiscard();
                        } else {
                            getRightPlayer(playerController.getId()).getWonder().downgradeStage();
                        }
                        break;
                    case BLOCK_AND_DESTROY_CARD:
                        getLeftPlayer(playerController.getId()).getCurrentMove().setDiscard();
                        getRightPlayer(playerController.getId()).getCurrentMove().setDiscard();
                        break;
                    case ECONOMIC_DEPRESSION:
                        for (PlayerController pc : playerControllers) {
                            if (pc.getCurrentMove().getTrades().size() > 0) {
                                pc.getCurrentMove().setDiscard();
                                pc.setGold(pc.getGold() - pc.DISCARD_REWARD);
                            }
                        }
                        break;
                    case SCIENTIFIC_REGRESSION:
                        for (PlayerController pc : playerControllers) {
                            if (pc.getCurrentMove().getAction() == ACTION_TYPE.BUILD_CARD
                            && AssetManager.getInstance().getCardByID(pc.getCurrentMove().getSelectedCardID()).getColor() == CARD_COLOR_TYPE.GREEN) {
                                pc.getCurrentMove().setDiscard();
                                pc.setGold(pc.getGold() - pc.DISCARD_REWARD);
                            } else {
                                for (Card c : pc.getConstructionZone().getConstructedCards()) {
                                    if (c.getColor() == CARD_COLOR_TYPE.GREEN) {
                                        pc.getConstructionZone().getConstructedCards().remove(c);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }

                playerController.getWonder().getGod().setUsed();
            }
        }

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
                    myPlayerController.getCurrentMove().getSelectedCardID()).getCardEffect();

            if (myPlayerController.getCurrentMove().getAction() == ACTION_TYPE.BUILD_CARD) {
                switch (effect.getEffectType()) {
                    case GRANT_SHIELDS:
                        myPlayerController.setShields(effect.getShields() + myPlayerController.getShields());
                        break;
                    case GET_MONEY:
                        myPlayerController.setGold(effect.getGold() + myPlayerController.getGold());
                        break;
                    case GET_MONEY_FOR_BROWN_CARD:
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
                        myPlayerController.setGold(cardCount * 2 + myPlayerController.getGold());
                        break;
                    case GET_MONEY_AND_VP_PER_BROWN:
                        for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                            if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                                cardCount++;
                            }
                        }

                        myPlayerController.setGold(cardCount + myPlayerController.getGold());
                        break;
                    case GET_MONEY_AND_VP_PER_GRAY:
                        for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                            if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                                cardCount++;
                            }
                        }
                        myPlayerController.setGold(cardCount * 2 + myPlayerController.getGold());
                        break;
                    case GET_MONEY_AND_VP_PER_WONDER:
                        int goldToAdd = (myPlayerController.getWonder().getCurrentStageIndex() + 1) * 3;

                        myPlayerController.setGold(goldToAdd + myPlayerController.getGold());
                        break;
                    case GET_MONEY_AND_VP_PER_YELLOW:
                        for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                            if (card.getColor() == CARD_COLOR_TYPE.YELLOW) {
                                cardCount++;
                            }
                        }
                        myPlayerController.setGold(cardCount + myPlayerController.getGold());
                        break;
                }
            }

            Vector<TradeAction> trades = myPlayerController.getCurrentMove().getTrades();

            boolean hasRightRawDiscount = false;
            boolean hasLeftRawDiscount = false;
            boolean hasManifacturedDiscount = false;
        /*
        Check if the user has a discount by looking at their constructionZone
         */
            for (Card card : myPlayerController.getConstructionZone().getConstructedCards()) {
                CARD_EFFECT_TYPE cardEffect = card.getCardEffect().getEffectType();

                switch (cardEffect) {
                    case RIGHT_RAW_MATERIAL_TRADE_DISCOUNT:
                        hasRightRawDiscount = true;
                        break;
                    case LEFT_RAW_MATERIAL_TRADE_DISCOUNT:
                        hasLeftRawDiscount = true;
                        break;
                    case MANUFACTURED_GOODS_TRADE_DISCOUNT:
                        hasManifacturedDiscount = true;
                        break;
                    default: break;
                }
            }

            for ( TradeAction trade : trades)
            {
                PlayerController tradedPlayer = playerControllers[trade.getTradedPlayerID()];

                 if((hasRightRawDiscount && tradedPlayer.getId() == getRightPlayer(trade.getPlayerID()).getId() )&& (trade.getSelectedResource() == RESOURCE_TYPE.WOOD ||
                                            trade.getSelectedResource() == RESOURCE_TYPE.ORE ||
                                            trade.getSelectedResource() == RESOURCE_TYPE.STONE ||
                                            trade.getSelectedResource() == RESOURCE_TYPE.BRICK) )
                 {
                     myPlayerController.setGold(myPlayerController.getGold() - 1);
                     tradedPlayer.setGold( tradedPlayer.getGold() + 1);
                 } else if((hasLeftRawDiscount && tradedPlayer.getId() == getLeftPlayer(trade.getPlayerID()).getId() )&& (trade.getSelectedResource() == RESOURCE_TYPE.WOOD ||
                         trade.getSelectedResource() == RESOURCE_TYPE.ORE ||
                         trade.getSelectedResource() == RESOURCE_TYPE.STONE ||
                         trade.getSelectedResource() == RESOURCE_TYPE.BRICK) )
                 {
                     myPlayerController.setGold(myPlayerController.getGold() - 1);
                     tradedPlayer.setGold( tradedPlayer.getGold() + 1);
                 }
                 else if(hasManifacturedDiscount && (trade.getSelectedResource() == RESOURCE_TYPE.GLASS ||
                                                     trade.getSelectedResource() == RESOURCE_TYPE.PAPYRUS ||
                                                     trade.getSelectedResource() == RESOURCE_TYPE.LOOM))
                 {
                     myPlayerController.setGold(myPlayerController.getGold() - 1);
                     tradedPlayer.setGold( tradedPlayer.getGold() + 1);
                 }
                 else{
                     myPlayerController.setGold(myPlayerController.getGold() - 2);
                     tradedPlayer.setGold(tradedPlayer.getGold() + 2);
                 }
            }

            myPlayerController.setReady(false);
            myPlayerController.updateCurrentMove(null); //Clear the players move
        }
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
            playerControllers[playerControllers.length-1].setHand(tmp);
        }

        for(Card card: playerControllers[0].getHand()){
            System.out.println("Card Name: " + card.getName());
        }
    }

    private void discardLastCards(){
        for (PlayerController playerController: playerControllers){
            discardCard(playerController.getHand().get(0));
            playerController.getHand().clear();
        }
    }

    public void discardCard(Card card) {
        discardPileController.discardCard(card);
    }

    public boolean isEveryoneReady(){
        for(PlayerController controller: playerControllers){
            if (!controller.isReady())
                return false;
        }
        return true;
    }

    public void makePlayerReady(int id){ playerControllers[id].setReady(true); }

    public int addPlayer(String name, WONDER_TYPE wonderType){
        //playerCount corresponds to the id
        PlayerModel playerModel = new PlayerModel(playerCount, name, AssetManager.getInstance().getWonderByType(wonderType));

        model.addPlayer(playerModel);
        playerControllers[playerCount] = new PlayerController(playerModel, this);

        playerCount++;
        return playerCount - 1; //Return the id of the added player
    }

    private void shuffleDecks(){ deckController.shuffleDecks(); }

    public PlayerModel getPlayer(int id){
        return playerControllers[id].getPlayer();
    }

    public PlayerModel getLeftPlayer(int id){
        return playerControllers[(id + 6) % 7].getPlayer();
    }

    public PlayerModel getRightPlayer(int id){
        return playerControllers[(id + 1) % 7].getPlayer();
    }
}
