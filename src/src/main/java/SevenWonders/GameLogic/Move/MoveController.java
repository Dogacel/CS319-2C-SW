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

    //methods
    public static MoveController getInstance() {
        if (moveControllerInstance == null) {
            moveControllerInstance = new MoveController();
        }
        return moveControllerInstance;
    }

    public static Vector<TradeAction> autoTrades = null;


    public boolean playerCanMakeMove(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbours, boolean autoTrade) {

        ACTION_TYPE action = moveModel.getAction();
        if ( !playerCanMakeTheTrade( moveModel, currentPlayer, neighbours)) {
            return false;
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
                //TODO add god power here
                break;
            case BUILD_FREE:
                if ( GameController.playerCanBuildFree) {
                    GameController.playerCanBuildFree = false;
                    return true;
                }
                return false;
            default: break;
        }
        return false;
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
        //TODO check neighbour cards
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

    public boolean playerHasEnoughResources( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades) {
        return playerResourceCost(requiredResources, currentPlayer, trades, null) == 0;
    }

    public boolean playerHasEnoughResourcesAutoTrade( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades, Pair<PlayerModel, PlayerModel> neighbors) {
        return playerResourceCost(requiredResources, currentPlayer, trades, neighbors) <= currentPlayer.getGold();
    }

    public int playerResourceCost( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades, Pair<PlayerModel, PlayerModel> neighbors) {
        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        /*to deep clone a map */
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            if ( entry.getKey() != RESOURCE_TYPE.GOLD) {
                clonedResourceMap.put(entry.getKey(),entry.getValue().intValue());
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
            return 0;
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
            return 0;
        }
        //If non-choice cards are not enough, must look at choice cards
        Vector<Card> choiceCards = new Vector<>();

        //If players unlocked the wonder stage that allows him to build on of the resources, add that ability as a corresponding card to the list
        if ( currentPlayer.getWonder().getCurrentStageIndex() >= 2) {
            if ( currentPlayer.getWonder().getStages()[1].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.ONE_OF_EACH_RAW_MATERIAL) {
                choiceCards.add( AssetManager.getInstance().getCardByID( 37)); //Adds the card caravensery, which ability is the same with the wonder ability
            }
        }

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
        return playerHasEnoughResourcesWitChoiceCards( choiceCards, choiceCards.size() - 1, clonedResourceMap, currentPlayer, neighbors);
    }

    public int minimumTradeCost(Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbors) {
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
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getKey().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getKey().getId()));
                }
            } else if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                if (goodDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getKey().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getKey().getId()));
                }
            }
        }

        for (Card card : neighbors.getValue().getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.BROWN) {
                if (rightDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getValue().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getValue().getId()));
                }
            } else if (card.getColor() == CARD_COLOR_TYPE.GRAY) {
                if (goodDiscount) {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 2), neighbors.getValue().getId()));
                } else {
                    possibleTradeAndCosts.add(new Pair<>(new Pair<>(card, 1), neighbors.getValue().getId()));
                }
            }
        }

        return minimumTradeCostOfCards(currentPlayer, requiredResources, possibleTradeAndCosts);
    }


    private int minimumTradeCostOfCards(PlayerModel me, Map<RESOURCE_TYPE, Integer> requiredResources, Vector<Pair<Pair<Card, Integer>, Integer>> possibleTradeAndCosts) {
        int cost = 0;

        if (requiredResources.isEmpty()) {
            return 0;
        }
        autoTrades = new Vector<>();
        for (var trade : possibleTradeAndCosts) {
            if (trade.getKey().getValue() == 1) {
                for (Map.Entry<RESOURCE_TYPE, Integer> resource : trade.getKey().getKey().getCardEffect().getResources().entrySet()) {
                    int required = requiredResources.getOrDefault(resource.getKey(), 0);
                    if (required > 0) {
                        if (required > resource.getValue()) {
                            cost += resource.getValue();
                            for (int i = 0 ; i < resource.getValue() ; i++) {
                                autoTrades.add(new TradeAction(me.getId(), trade.getValue(), trade.getKey().getKey().getId(), resource.getKey()));
                            }
                            requiredResources.put(resource.getKey(), required - resource.getValue());
                        } else {
                            cost += required;
                            for (int i = 0 ; i < required ; i++) {
                                autoTrades.add(new TradeAction(me.getId(), trade.getValue(), trade.getKey().getKey().getId(), resource.getKey()));
                            }
                            requiredResources.remove(resource.getKey());
                        }
                    }
                }
            }
        }

        for (var trade : possibleTradeAndCosts) {
            if (trade.getKey().getValue() == 2) {
                for (Map.Entry<RESOURCE_TYPE, Integer> resource : trade.getKey().getKey().getCardEffect().getResources().entrySet()) {
                    int required = requiredResources.getOrDefault(resource.getKey(), 0);
                    if (required > 0) {
                        if (required > resource.getValue()) {
                            cost += 2 * resource.getValue();
                            for (int i = 0 ; i < resource.getValue() ; i++) {
                                autoTrades.add(new TradeAction(me.getId(), trade.getValue(), trade.getKey().getKey().getId(), resource.getKey()));
                            }
                            requiredResources.put(resource.getKey(), required - resource.getValue());
                        } else {
                            cost += 2 * required;
                            for (int i = 0 ; i < required ; i++) {
                                autoTrades.add(new TradeAction(me.getId(), trade.getValue(), trade.getKey().getKey().getId(), resource.getKey()));
                            }
                            requiredResources.remove(resource.getKey());
                        }
                    }
                }
            }
        }

        if (requiredResources.isEmpty()) {
            return cost;
        }

        return 999;
    }

    /**
     *  A recursive method for understanding if choice cards provide enough resources.
     * @param choiceCards all the choice cards that the user have in their constructionZone
     * @param begin size of the choiceCard vector
     * @param map resources that user requires to perform given action
     * @return true if choice cards provide necessary resources, false if not.
     */
    private int playerHasEnoughResourcesWitChoiceCards(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map, PlayerModel me, Pair<PlayerModel, PlayerModel> neighbors) {
        if (begin == -1) {
            // gold ?
            if (neighbors == null) {
                return map.isEmpty() ? 0 : 999;
            }
            return map.isEmpty() ? 0 : minimumTradeCost(map, me, neighbors);
        }
        Card card = choiceCards.get(begin);
        switch (card.getCardEffect().getEffectType()) {
            case ONE_OF_EACH_MANUFACTURED_GOODS:
                int loomCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.LOOM, me, neighbors);
                int glassCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.GLASS, me, neighbors);
                int papyrusCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.PAPYRUS, me, neighbors);
                return Math.min(loomCost, Math.min(glassCost, papyrusCost));
            case ONE_OF_EACH_RAW_MATERIAL:
                int woodCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.WOOD, me, neighbors);
                int brickCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.BRICK, me, neighbors);
                int stoneCost =  recursive(choiceCards, begin, map, RESOURCE_TYPE.STONE, me, neighbors);
                int oreCost =  recursive(choiceCards, begin, map, RESOURCE_TYPE.ORE, me, neighbors);
                return Math.min(Math.min(woodCost, brickCost), Math.min(stoneCost, oreCost));
            case PRODUCE_ONE_OF_TWO:
                Map<RESOURCE_TYPE, Integer> tempMap = card.getCardEffect().getResources();
                int bestCost = 999;
                for (var k : tempMap.keySet()) {
                   int cost = recursive(choiceCards, begin, map, k, me, neighbors);
                   if (bestCost > cost) {
                       bestCost = cost;
                   }
                }
                return bestCost;
        }
        return 999;
    }

    /**
     * A helper method that contains the body of the recursion
     * @param choiceCards
     * @param begin
     * @param map
     * @param resource_type
     * @return
     */
    private int recursive(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map, RESOURCE_TYPE resource_type, PlayerModel me, Pair<PlayerModel, PlayerModel> neighbors) {
        int resourceCount = map.getOrDefault(resource_type, 0);
        if (resourceCount > 1) {
            map.put(resource_type, resourceCount-1);
        } else if (resourceCount == 1 || resourceCount == 0){
            map.remove(resource_type);
        }
        int cost  = playerHasEnoughResourcesWitChoiceCards(choiceCards, begin-1, map, me, neighbors);
        if (resourceCount > 0){
            map.put(resource_type, resourceCount);
        }
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
     * @param currentPlayer curren player
     * @return true if player can build a card, false otherwise
     */
    private boolean playerCanPlayBuildCard(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbors) {
         return checkConstructionZone( moveModel, currentPlayer) && playerHasEnoughResourcesAutoTrade( AssetManager.getInstance().getCardByID(moveModel.getSelectedCardID()).getRequirements(), currentPlayer, moveModel.getTrades(), neighbors);
    }

    /**
     * Checks if the player can discard their selected cards
     * @param moveModel current move
     * @param currentPlayer current player
     * @return true if player can discard, false otherwise
     */
    private boolean playerCanDiscardCard(MoveModel moveModel, PlayerModel currentPlayer) {
        return currentPlayer.getHand().contains(AssetManager.getInstance().getCardByID(moveModel.getSelectedCardID()));
    }

    /**
     * Checks if player can upgrade their wonders
     * @param moveModel current move
     * @param currentPlayer current player
     * @return true if player can upgrade wonder, false otherwise
     */
    private boolean playerCanBuildWonder(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbors) {
        return currentPlayer.getWonder().isUpgradeable() && playerHasEnoughResourcesAutoTrade(currentPlayer.getWonder().getCurrentStage().getRequiredResources(), currentPlayer, moveModel.getTrades(), neighbors);
    }

    private boolean haveBuildingChain(MoveModel moveModel, PlayerModel currentPlayer ){
        if (!checkConstructionZone(moveModel,currentPlayer))
            return false;

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
