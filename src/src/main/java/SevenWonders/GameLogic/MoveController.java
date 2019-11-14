package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
import javafx.util.Pair;

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

    private boolean playerCanMakeTheTrade( MoveModel move, PlayerModel currentPlayer) {
        int goldOfCurrentPlayer = currentPlayer.getGold();

        /* boolean for understanding if there is a discount */
        boolean hasRightRawDiscount = false;
        boolean hasLeftRawDiscount = false;
        boolean hasManifacturedDiscount = false;

        RESOURCE_TYPE[] rawResources = { RESOURCE_TYPE.WOOD, RESOURCE_TYPE.STONE, RESOURCE_TYPE.ORE, RESOURCE_TYPE.BRICK};
        RESOURCE_TYPE[] manifacturedResources = { RESOURCE_TYPE.GLASS, RESOURCE_TYPE.PAPYRUS, RESOURCE_TYPE.LOOM};

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

        for (TradeAction trade : move.getTrades()) {
            if ( trade.getSelectedResource())
        }

    }

    private boolean playerHasEnoughCoins(MoveModel moveModel, PlayerModel currentPlayer) {
        ACTION_TYPE action = moveModel.getAction();
        TradeAction[] trades = moveModel.getTrades();
        int goldOfCurrentPlayer = currentPlayer.getGold();
        int goldCostForCurrentCard = fromIDToCard(moveModel.getSelectedCardID()).getRequirements().get(RESOURCE_TYPE.GOLD);

        for ()

        switch ( action) {
            case DISCARD_CARD:
            case UPGRADE_WONDER:
            case USE_GOD_POWER:
                return true;
            case BUILD_CARD:
                return goldOfCurrentPlayer >= goldCostForCurrentCard;
            default: return false;
        }
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
}
