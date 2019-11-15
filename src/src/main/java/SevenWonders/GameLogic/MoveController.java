package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MoveController {
    //variables
    private static MoveController moveControllerInstance = null;

    //methods
    public static MoveController getInstance() {
        if (moveControllerInstance == null) {
            moveControllerInstance = new MoveController();
        }
        return moveControllerInstance;
    }


    public boolean playerCanMakeMove(MoveModel moveModel, PlayerModel currentPlayer) {
        Card selectedCard = fromIDToCard(moveModel.getSelectedCardID());
    }

    private boolean playerCanMakeTheTrade( MoveModel move, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbours) {
        int goldOfCurrentPlayer = currentPlayer.getGold();

        /* boolean for understanding if there is a discount */
        boolean hasRightRawDiscount = false;
        boolean hasLeftRawDiscount = false;
        boolean hasManifacturedDiscount = false;
        /*
        Check if the user has a discount by looking at their constructionZone
         */
        for (Card card : currentPlayer.getConstructionZone().getConstructedCards()) {
            CARD_EFFECT_TYPE effect = card.getCardEffect().getEffectType();

            switch (effect) {
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
        /* Check the trade array, check if valid and if valid, reduce gold */
        for (TradeAction trade : move.getTrades()) {
            if ( trade.getSelectedResource() == RESOURCE_TYPE.BRICK || trade.getSelectedResource() == RESOURCE_TYPE.ORE ||
                    trade.getSelectedResource() == RESOURCE_TYPE.WOOD || trade.getSelectedResource() == RESOURCE_TYPE.STONE ) {
                if ( hasLeftRawDiscount) {
                    if (trade.getPlayerID() == neighbours.getKey().getId()) {
                        goldOfCurrentPlayer -= 1;
                    }
                }
                else if (hasRightRawDiscount) {
                    if (trade.getPlayerID() == neighbours.getValue().getId()) {
                        goldOfCurrentPlayer -= 1;
                    }
                }
                else {
                    goldOfCurrentPlayer -= 2;
                }
            }
            else if ( trade.getSelectedResource() == RESOURCE_TYPE.LOOM || trade.getSelectedResource() == RESOURCE_TYPE.PAPYRUS || trade.getSelectedResource() == RESOURCE_TYPE.GLASS) {
                if ( hasManifacturedDiscount) {
                    goldOfCurrentPlayer -= 1;
                }
                else {
                    goldOfCurrentPlayer -= 2;
                }
            }
            //at the end of each iteration, check if the user has ran out of money, return false if so.
            if (goldOfCurrentPlayer < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean playerHasEnoughResources( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, ArrayList<TradeAction> trades) {
        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        /*to deep clone a map */
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            clonedResourceMap.put(entry.getKey(),entry.getValue());
        }

        /*First assume all the trades, successful trades are a given and will not be checked*/
        for ( TradeAction trade : trades) {
            RESOURCE_TYPE resource = trade.getSelectedResource();
            if ( clonedResourceMap.containsKey( resource)) {
                if ( clonedResourceMap.get( resource) <= 0) {
                    clonedResourceMap.remove( resource);
                }
                else {
                    int numberOfResources = clonedResourceMap.get(resource);
                    clonedResourceMap.put(resource, numberOfResources - 1);
                }
            }
        }

        // If the trades were enough, just return true
        if ( clonedResourceMap.isEmpty()) {
            return true;
        }

        /*check for all the non-choice cards to see if we have enough resources for user action*/
        for (Card builtCard : currentPlayer.getConstructionZone().getConstructedCards()) {
            CardEffect effect = builtCard.getCardEffect();
            CARD_EFFECT_TYPE effect_type =  effect.getEffectType();
            switch (effect_type) {
                case PRODUCE_RAW_MATERIAL:
                case PRODUCE_MANUFACTURED_GOODS:
                    for (Map.Entry<RESOURCE_TYPE, Integer> entry : effect.getResources().entrySet()) {
                        int numberOfResources = clonedResourceMap.getOrDefault( entry.getKey(), 0);
                        if (numberOfResources > 0) {
                            int valueToBePut = clonedResourceMap.get(entry.getKey()) - numberOfResources;
                            if (valueToBePut <= 0) {
                                clonedResourceMap.remove(entry.getKey());
                            } else {
                                clonedResourceMap.put( entry.getKey(), valueToBePut);
                            }
                        }
                    }
                    break;
                default: break;
            }
        }
        /*If the non-choice cards provide enough resources, return true*/
        if ( clonedResourceMap.isEmpty()) {
            return true;
        }
        //If non-choice cards are not enough, must look at choice cards
        Vector<Card> choiceCards = new Vector<>();

        /*Traverse every card and collect choice cards*/
        for ( Card card : currentPlayer.getConstructionZone().getConstructedCards() ) {
            switch (card.getCardEffect().getEffectType()) {
                case ONE_OF_EACH_MANUFACTURED_GOODS:
                case ONE_OF_EACH_RAW_MATERIAL:
                case PRODUCE_ONE_OF_TWO:
                    choiceCards.add(card);
                    break;
            }
        }
        return playerHasEnoughResourcesWitChoiceCards( choiceCards, choiceCards.size() - 1, clonedResourceMap);
    }

    /**
     *  A recursive method for understanding if choice cards provide enough resources.
     * @param choiceCards all the choice cards that the user have in their constructionZone
     * @param begin size of the choiceCard vector
     * @param map resources that user requires to perform given action
     * @return true if choice cards provide necessary resources, false if not.
     */
    private boolean playerHasEnoughResourcesWitChoiceCards(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map) {
        if (begin == -1) {
            // gold ?
            return map.isEmpty();
        }
        Card card = choiceCards.get(begin);
        switch (card.getCardEffect().getEffectType()) {
            case ONE_OF_EACH_MANUFACTURED_GOODS:
                if (recursive(choiceCards, begin, map, RESOURCE_TYPE.LOOM)) {
                    return true;
                } else if (recursive(choiceCards, begin, map, RESOURCE_TYPE.GLASS)) {
                    return true;
                } else if (recursive(choiceCards, begin, map, RESOURCE_TYPE.PAPYRUS)) {
                    return true;
                }
                break;
            case ONE_OF_EACH_RAW_MATERIAL:
                if (recursive(choiceCards, begin, map, RESOURCE_TYPE.WOOD)) {
                    return true;
                } else if (recursive(choiceCards, begin, map, RESOURCE_TYPE.BRICK)) {
                    return true;
                } else if (recursive(choiceCards, begin, map, RESOURCE_TYPE.STONE)) {
                    return true;
                }else if (recursive(choiceCards, begin, map, RESOURCE_TYPE.ORE)) {
                    return true;
                }
                break;
            case PRODUCE_ONE_OF_TWO:
                Map<RESOURCE_TYPE, Integer> tempMap = card.getCardEffect().getResources();
               for (var k : tempMap.keySet()) {
                   if (recursive(choiceCards, begin, map, k)) {
                       return true;
                   }
               }
                break;
        }
    return true;
    }

    /**
     * A helper method that contains the body of the recursion
     * @param choiceCards
     * @param begin
     * @param map
     * @param resource_type
     * @return
     */
    private boolean recursive(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map, RESOURCE_TYPE resource_type) {
        int resourceCount = map.get(resource_type);
        if (resourceCount > 1) {
            map.put(resource_type, resourceCount-1);
        } else if (resourceCount == 1 || resourceCount == 0){
            map.remove(resource_type);
        }
        boolean isPossible  = playerHasEnoughResourcesWitChoiceCards(choiceCards, begin-1, map);
        if (resourceCount > 0){
            map.put(resource_type, resourceCount);
        }
        if (isPossible) {
            return true;
        }
        return false;
    }

    private boolean playerCanBuild(MoveModel moveModel, PlayerModel currentPlayer) {

    }

    private boolean playerCanPlayBuildCard(MoveModel moveModel, PlayerModel currentPlayer) {

    }

    private boolean playerCanDiscardCard(MoveModel moveModel, PlayerModel currentPlayer) {

    }

    private boolean playerCanBuildWonder(MoveModel moveModel, PlayerModel currentPlayer,Pair<PlayerModel, PlayerModel> neighbours) {
        WonderStage currentStage = currentPlayer.getWonder().getCurrentStage(); //current stage of the wonder
        Card selectedCard = fromIDToCard(moveModel.getSelectedCardID());
        ConstructionZone currentCZ = currentPlayer.getConstructionZone();
        Vector<Card> constructedCards = currentCZ.getConstructedCards();

    }

    /* TODO
    This method is for taking an ID and returning a Card object, maybe initialized somewhere else later.
     */
    public Card fromIDToCard(int cardID) {
        return null;
    }

    //TODO implement this method, meybe somewhere else
    public PlayerModel fromIDToPlayer(int playerID) {
        return null;
    }
}
