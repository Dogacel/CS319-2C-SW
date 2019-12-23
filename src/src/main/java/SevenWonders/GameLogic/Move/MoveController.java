package SevenWonders.GameLogic.Move;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Deck.Card.CardEffect;
import SevenWonders.GameLogic.Enums.*;
import SevenWonders.GameLogic.Game.GameController;
import SevenWonders.GameLogic.Player.PlayerController;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.util.Pair;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MoveController {
    //variables
    private static MoveController moveControllerInstance = null;

    private Pair<Integer, Vector<TradeAction>> min(Pair<Integer, Vector<TradeAction>> a, Pair<Integer, Vector<TradeAction>> b) {
        return a.getKey() < b.getKey() ? a : b;
    }
    //methods
    public static MoveController getInstance() {
        if (moveControllerInstance == null) {
            moveControllerInstance = new MoveController();
        }
        return moveControllerInstance;
    }


    public Pair<Boolean, Vector<TradeAction>> playerCanMakeMove(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbours, boolean autoTrade) {

        ACTION_TYPE action = moveModel.getAction();
        if ( !playerCanMakeTheTrade( moveModel, currentPlayer, neighbours)) {
            return new Pair<>(false, new Vector<>());
        }
        if (!autoTrade) {
            neighbours = null;
        }
        switch (action) {
            case DISCARD_CARD:
                return playerCanDiscardCard( moveModel, currentPlayer);
            case BUILD_CARD:
                return playerCanPlayBuildCard(moveModel, currentPlayer, neighbours);
            case UPGRADE_WONDER:
                return playerCanBuildWonder(moveModel, currentPlayer, neighbours);
            case USE_GOD_POWER:
                return new Pair<>(!currentPlayer.getWonder().isUpgradeable() && !currentPlayer.getWonder().getGod().isUsed(), new Vector<>());
            default: break;
        }
        return new Pair<>(false, new Vector<>());
    }

    private boolean playerCanMakeTheTrade( MoveModel move, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbours) {
        return tradeCost(move.getTrades(), currentPlayer, neighbours) <= currentPlayer.getGold();
    }

    public int tradeCost(Vector<TradeAction> trades, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbours) {
        int cost = 0;

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

        for (TradeAction trade : trades) {
            if ( trade.getSelectedResource() == RESOURCE_TYPE.BRICK || trade.getSelectedResource() == RESOURCE_TYPE.ORE ||
                    trade.getSelectedResource() == RESOURCE_TYPE.WOOD || trade.getSelectedResource() == RESOURCE_TYPE.STONE ) {
                if ( hasLeftRawDiscount && trade.getTradedPlayerID() == neighbours.getKey().getId()) {
                    cost += 1;
                }
                else if (hasRightRawDiscount && trade.getTradedPlayerID() == neighbours.getValue().getId()) {
                    cost += 1;
                }
                else {
                    cost += 2;
                }
            }
            else if ( trade.getSelectedResource() == RESOURCE_TYPE.LOOM || trade.getSelectedResource() == RESOURCE_TYPE.PAPYRUS || trade.getSelectedResource() == RESOURCE_TYPE.GLASS) {
                if ( hasManifacturedDiscount) {
                    cost += 1;
                }
                else {
                    cost += 2;
                }
            }
        }
        return cost;
    }

    public Pair<Boolean, Vector<TradeAction>> playerHasEnoughResources( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades) {
        var x = playerResourceCost(requiredResources, currentPlayer, trades, null);
        return new Pair<>(x.getKey() == 0, x.getValue());}

    public Pair<Boolean, Vector<TradeAction>> playerHasEnoughResourcesAutoTrade( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades, Pair<PlayerModel, PlayerModel> neighbors) {
        var x = playerResourceCost(requiredResources, currentPlayer, trades, neighbors);
        return new Pair<>(x.getKey() <= currentPlayer.getGold(), x.getValue());
    }

    public Pair<Integer, Vector<TradeAction>> playerResourceCost( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades, Pair<PlayerModel, PlayerModel> neighbors) {
        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        /*to deep clone a map */
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            if ( entry.getKey() != RESOURCE_TYPE.GOLD) {
                clonedResourceMap.put(entry.getKey(), entry.getValue());
            }
        }

        /*First assume all the trades, successful trades are a given and will not be checked*/
        for ( TradeAction trade : trades) {
            RESOURCE_TYPE resource = trade.getSelectedResource();
            if ( clonedResourceMap.containsKey( resource)) {
                if ( clonedResourceMap.get(resource) <= 1) {
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
            return new Pair<>(0, new Vector<>());
        }

        // Check wonder resource
        int wonderResourceRequirement = clonedResourceMap.getOrDefault(currentPlayer.getWonder().getResource(), 0);
        if (wonderResourceRequirement == 1) {
            clonedResourceMap.remove(currentPlayer.getWonder().getResource());
        } else if (wonderResourceRequirement > 1) {
            clonedResourceMap.put(currentPlayer.getWonder().getResource(), wonderResourceRequirement - 1);
        }

        /*check for all the non-choice cards to see if we have enough resources for user action*/
        for (Card builtCard : currentPlayer.getConstructionZone().getConstructedCards()) {
            CardEffect effect = builtCard.getCardEffect();
            CARD_EFFECT_TYPE effect_type =  effect.getEffectType();
            switch (effect_type) {
                case PRODUCE_RAW_MATERIAL:
                case PRODUCE_MANUFACTURED_GOODS:
                    for (Map.Entry<RESOURCE_TYPE, Integer> entry : effect.getResources().entrySet()) {
                        int numberOfResources = clonedResourceMap.getOrDefault(entry.getKey(), 0);
                        if (numberOfResources > 0) {
                            int valueToBePut = clonedResourceMap.get(entry.getKey()) - entry.getValue();
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
            return new Pair<>(0, new Vector<>());
        }
        //If non-choice cards are not enough, must look at choice cards
        Vector<Card> choiceCards = new Vector<>();

        //If players unlocked the wonder stage that allows him to build on of the resources, add that ability as a corresponding card to the list
        if (currentPlayer.getWonder().getCurrentStageIndex() >= 2) {
            if (currentPlayer.getWonder().getStages()[1].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.ONE_OF_EACH_RAW_MATERIAL) {
                choiceCards.add(AssetManager.getInstance().getCardByID(37)); //Adds the card caravensery, which ability is the same with the wonder ability
            }
        }

        /*Traverse every card and collect choice cards*/
        for (Card card : currentPlayer.getConstructionZone().getConstructedCards()) {
            switch (card.getCardEffect().getEffectType()) {
                case ONE_OF_EACH_MANUFACTURED_GOODS:
                case ONE_OF_EACH_RAW_MATERIAL:
                case PRODUCE_ONE_OF_TWO:
                    choiceCards.add(card);
                    break;
            }
        }
        return playerHasEnoughResourcesWitChoiceCards( choiceCards, choiceCards.size() - 1, clonedResourceMap, currentPlayer, neighbors);
    }


    /**
     *  A recursive method for understanding if choice cards provide enough resources.
     * @param choiceCards all the choice cards that the user have in their constructionZone
     * @param begin size of the choiceCard vector
     * @param map resources that user requires to perform given action
     * @return true if choice cards provide necessary resources, false if not.
     */
    private Pair<Integer, Vector<TradeAction>> playerHasEnoughResourcesWitChoiceCards(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map, PlayerModel me, Pair<PlayerModel, PlayerModel> neighbors) {
        if (begin == -1) {
            // gold ?
            if (neighbors == null) {
                return map.isEmpty() ? new Pair<>(0, new Vector<>()) : new Pair<>(999, new Vector<>());
            }
            return map.isEmpty() ? new Pair<>(0, new Vector<>()) : minimumTradeCost(map, me, neighbors);
        }
        Card card = choiceCards.get(begin);
        switch (card.getCardEffect().getEffectType()) {
            case ONE_OF_EACH_MANUFACTURED_GOODS:
                var loomCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.LOOM, me, neighbors);
                var glassCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.GLASS, me, neighbors);
                var papyrusCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.PAPYRUS, me, neighbors);
                return min(loomCost, min(glassCost, papyrusCost));
            case ONE_OF_EACH_RAW_MATERIAL:
                var woodCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.WOOD, me, neighbors);
                var brickCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.BRICK, me, neighbors);
                var stoneCost =  recursive(choiceCards, begin, map, RESOURCE_TYPE.STONE, me, neighbors);
                var oreCost =  recursive(choiceCards, begin, map, RESOURCE_TYPE.ORE, me, neighbors);
                return min(min(woodCost, brickCost), min(stoneCost, oreCost));
            case PRODUCE_ONE_OF_TWO:
                Map<RESOURCE_TYPE, Integer> tempMap = card.getCardEffect().getResources();
                var bestCost = new Pair<Integer, Vector<TradeAction>>(999, new Vector<>());
                for (var k : tempMap.keySet()) {
                   var cost = recursive(choiceCards, begin, map, k, me, neighbors);
                   if (bestCost.getKey() > cost.getKey()) {
                       bestCost = cost;
                   }
                }
                return bestCost;
        }
        return new Pair<>(999, new Vector<>());
    }

    private Pair<Integer, Vector<TradeAction>> recursive(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map, RESOURCE_TYPE resource_type, PlayerModel me, Pair<PlayerModel, PlayerModel> neighbors) {
        int resourceCount = map.getOrDefault(resource_type, 0);
        if (resourceCount > 1) {
            map.put(resource_type, resourceCount-1);
        } else if (resourceCount == 1 || resourceCount == 0){
            map.remove(resource_type);
        }
        Pair<Integer, Vector<TradeAction>> cost  = playerHasEnoughResourcesWitChoiceCards(choiceCards, begin-1, map, me, neighbors);
        if (resourceCount > 0){
            map.put(resource_type, resourceCount);
        }
        return cost;
    }


    public Pair<Integer, Vector<TradeAction>> minimumTradeCost(Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbors) {

        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        /*to deep clone a map */
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            if ( entry.getKey() != RESOURCE_TYPE.GOLD) {
                clonedResourceMap.put(entry.getKey(), entry.getValue());
            }
        }

        boolean leftDiscount = false, rightDiscount = false, goodDiscount = false;
        for (Card card : currentPlayer.getConstructionZone().getConstructedCards()) {
            if (card.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.LEFT_RAW_MATERIAL_TRADE_DISCOUNT) {
                leftDiscount = true;
            } else if (card.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.RIGHT_RAW_MATERIAL_TRADE_DISCOUNT) {
                rightDiscount = true;
            } else if (card.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.MANUFACTURED_GOODS_TRADE_DISCOUNT) {
                goodDiscount = true;
            }
        }

        Vector<Pair<Pair<Card, Integer>, Integer>> possibleTradeAndCosts = new Vector<>();
        for (Card card : neighbors.getKey().getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                if (leftDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getKey().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getKey().getId()));
                }
            } else if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                if (goodDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getKey().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getKey().getId()));
                }
            }
        }

        for (Card card : neighbors.getValue().getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                if (rightDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getValue().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getValue().getId()));
                }
            } else if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                if (goodDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getValue().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getValue().getId()));
                }
            }
        }

        return minimumTradeCostOfCards(currentPlayer, clonedResourceMap, possibleTradeAndCosts, 1);
    }


    private Pair<Integer, Vector<TradeAction>> minimumTradeCostOfCards(PlayerModel me, Map<RESOURCE_TYPE, Integer> requiredResources, Vector<Pair<Pair<Card, Integer>, Integer>> possibleTradeAndCosts, int level) {

        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        /*to deep clone a map */
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            if ( entry.getKey() != RESOURCE_TYPE.GOLD) {
                clonedResourceMap.put(entry.getKey(), entry.getValue());
            }
        }

        if (clonedResourceMap.isEmpty()) {
            return new Pair<>(0, new Vector<>());
        } else if (level == 3) {
            return new Pair<>(999, new Vector<>());
        }
        int cost = 0;

        Vector<TradeAction> trades = new Vector<>();
        for (var trade : possibleTradeAndCosts) {
            if (trade.getKey().getValue() == level) {
                if (trade.getKey().getKey().getCardEffect().getEffectType() == CARD_EFFECT_TYPE.PRODUCE_ONE_OF_TWO) {

                } else {
                    Map.Entry<RESOURCE_TYPE, Integer> resource = trade.getKey().getKey().getCardEffect().getResources().entrySet().iterator().next();
                    int required = clonedResourceMap.getOrDefault(resource.getKey(), 0);
                    if (required > 0) {
                        if (required > resource.getValue()) {
                            for (int i = 0 ; i < resource.getValue() ; i++) {
                                trades.add(new TradeAction(me.getId(), trade.getValue(), trade.getKey().getKey().getId(), resource.getKey()));
                                cost += level;
                            }
                            clonedResourceMap.put(resource.getKey(), required - resource.getValue());
                        } else {
                            for (int i = 0 ; i < required ; i++) {
                                trades.add(new TradeAction(me.getId(), trade.getValue(), trade.getKey().getKey().getId(), resource.getKey()));
                                cost += level;
                            }
                            clonedResourceMap.remove(resource.getKey());
                        }
                    }
                }
            }
        }

        if (clonedResourceMap.isEmpty()) {
            return new Pair<>(cost, trades);
        }

        var mco1 = minimizeChooseOne(me, clonedResourceMap, possibleTradeAndCosts, possibleTradeAndCosts.size() - 1, level);
        cost += mco1.getKey();
        for (var trade : mco1.getValue()) {
            trades.add(new TradeAction(me.getId(), trade.getTradedPlayerID(), trade.getSelectedCardID(), trade.getSelectedResource()));
            RESOURCE_TYPE resource = trade.getSelectedResource();
            int resourceCount = clonedResourceMap.getOrDefault(resource, 0);
            if (resourceCount > 1) {
                clonedResourceMap.put(resource, resourceCount-1);
            } else if (resourceCount == 1 || resourceCount == 0){
                clonedResourceMap.remove(resource);
            }
        }

        if (clonedResourceMap.isEmpty()) {
            return new Pair<>(cost, trades);
        }

        return new Pair<>(999, new Vector<>());
    }

    private Pair<Integer, Vector<TradeAction>> minimizeChooseOne(PlayerModel me, Map<RESOURCE_TYPE, Integer> requiredResources, Vector<Pair<Pair<Card, Integer>, Integer>> possibleTradeAndCosts, int begin, int cost) {
        if (begin == -1 || requiredResources.isEmpty()) {
            return requiredResources.isEmpty() ? new Pair<>(0, new Vector<>()) : minimumTradeCostOfCards(me, requiredResources, possibleTradeAndCosts, cost + 1);
        }

        var x = possibleTradeAndCosts.get(begin);
        Card card = x.getKey().getKey();

        if (card.getCardEffect().getEffectType() != CARD_EFFECT_TYPE.PRODUCE_ONE_OF_TWO || x.getKey().getValue() != cost) {
            return minimizeChooseOne(me, requiredResources, possibleTradeAndCosts, begin-1, cost);
        }

        Map<RESOURCE_TYPE, Integer> tempMap = card.getCardEffect().getResources();
        var bestCost = new Pair<Integer, Vector<TradeAction>>(999, new Vector<>());
        for (var k : tempMap.keySet()) {
            var bcost = recursiveTrade(me, requiredResources, possibleTradeAndCosts, begin, k, cost);
            if (bestCost.getKey() > bcost.getKey()) {
                bestCost = bcost;
            }
        }
        return bestCost;

    }

    private Pair<Integer, Vector<TradeAction>> recursiveTrade(PlayerModel me, Map<RESOURCE_TYPE, Integer> requiredResources,
                                                              Vector<Pair<Pair<Card, Integer>, Integer>> possibleTradeAndCosts, int begin, RESOURCE_TYPE resource, int bcost) {
        int resourceCount = requiredResources.getOrDefault(resource, 0);
        if (resourceCount > 1) {
            requiredResources.put(resource, resourceCount-1);
        } else if (resourceCount == 1){
            requiredResources.remove(resource);
        } else if (resourceCount == 0) {
            return minimizeChooseOne(me, requiredResources, possibleTradeAndCosts, begin-1, bcost);
        }
        Pair<Integer, Vector<TradeAction>> ccost = minimizeChooseOne(me, requiredResources, possibleTradeAndCosts, begin-1, bcost);

        requiredResources.put(resource, resourceCount);

        var y = possibleTradeAndCosts.get(begin);
        ccost.getValue().add(new TradeAction(me.getId(), y.getValue(), y.getKey().getKey().getId(), resource));
        Pair<Integer, Vector<TradeAction>> cost = new Pair<>(ccost.getKey() + bcost, ccost.getValue());
        return cost;

    }


    private boolean checkConstructionZone(MoveModel moveModel, PlayerModel currentPlayer) {
        for ( Card card : currentPlayer.getConstructionZone().getConstructedCards()) {
            if ( card.getId() == moveModel.getSelectedCardID()) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if a player can build a card
     * @param moveModel current move
     * @param currentPlayer current player
     * @return true if player can build a card, false otherwise
     */
    private Pair<Boolean, Vector<TradeAction>> playerCanPlayBuildCard(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbors) {
        boolean has = false;
        for (Card c : currentPlayer.getHand()) {
            if (c.getId() == moveModel.getSelectedCardID()) {
                has = true;
                break;
            }
        }

        if (!has) {
            //This if will check if the user's wonder has the ability to play a card from a discard pile
            if ( currentPlayer.getPlayerCanBuildDiscard() &&
                    currentPlayer.getWonder().getStages()[1].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.BUILD_FROM_DISCARDED && currentPlayer.getWonder().getCurrentStageIndex() >=2) {
                currentPlayer.setPlayerCanBuildDiscard(false);
                return new Pair<>(true, new Vector<>());
            }
            return new Pair<>(false, new Vector<>());
        }

        if (haveBuildingChain(moveModel, currentPlayer)) {
            return new Pair<>(true, new Vector<>());
        }

        var x = playerHasEnoughResourcesAutoTrade(AssetManager.getInstance().getCardByID(moveModel.getSelectedCardID()).getRequirements(), currentPlayer, moveModel.getTrades(), neighbors);

        if (checkConstructionZone(moveModel, currentPlayer) && !x.getKey()) {
            //First if will check if the user's wonder has the ability to play a free card for an age.
            if (currentPlayer.getPlayerCanBuildFree() &&
                    currentPlayer.getWonder().getStages()[1].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.FREE_BUILDING && currentPlayer.getWonder().getCurrentStageIndex() >=2) {
                currentPlayer.setPlayerCanBuildFree(false);
                return new Pair<>(true, new Vector<>());
            }
            return new Pair<>(false, new Vector<>());
        }

        return new Pair<>(checkConstructionZone(moveModel, currentPlayer) && x.getKey(), x.getValue());
    }

    /**
     * Checks if the player can discard their selected cards
     *
     * @param moveModel     current move
     * @param currentPlayer current player
     * @return true if player can discard, false otherwise
     */
    private Pair<Boolean, Vector<TradeAction>> playerCanDiscardCard(MoveModel moveModel, PlayerModel currentPlayer) {
        return new Pair<>(currentPlayer.getHand().contains(AssetManager.getInstance().getCardByID(moveModel.getSelectedCardID())), new Vector<>());
    }

    /**
     * Checks if player can upgrade their wonders
     *
     * @param moveModel     current move
     * @param currentPlayer current player
     * @return true if player can upgrade wonder, false otherwise
     */
    private Pair<Boolean, Vector<TradeAction>> playerCanBuildWonder(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbors) {
        if (!currentPlayer.getWonder().isUpgradeable())
            return new Pair<>(false, new Vector<>());
        var x = playerHasEnoughResourcesAutoTrade(currentPlayer.getWonder().getCurrentStage().getRequiredResources(), currentPlayer, moveModel.getTrades(), neighbors);
        return new Pair<>(currentPlayer.getWonder().isUpgradeable() && x.getKey(), x.getValue());
    }

    private boolean haveBuildingChain(MoveModel moveModel, PlayerModel currentPlayer ){
        for (Card card: currentPlayer.getConstructionZone().getConstructedCards()){
            Card toPlay = AssetManager.getInstance().getCardByID(moveModel.getSelectedCardID());
            for(String name: toPlay.getBuildingChain()){
                if (card.getName().equals(name))
                    return true;
            }
        }
        return false;
    }
}
