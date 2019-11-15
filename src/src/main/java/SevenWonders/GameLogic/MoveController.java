package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    //  TODO add trade action here!
    private boolean playerHasEnoughResources(MoveModel moveModel, PlayerModel currentPlayer) {
        Map<RESOURCE_TYPE,Integer> requiredResources = fromIDToCard(moveModel.getSelectedCardID()).getResourceCost();
        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        //to deep clone a map
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            clonedResourceMap.put(entry.getKey(),entry.getValue());
        }
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
                case PRODUCE_ONE_OF_TWO:
                    //TODO implement here
                    break;
            }
        }
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
