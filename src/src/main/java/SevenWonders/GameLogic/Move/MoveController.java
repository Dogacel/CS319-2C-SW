package SevenWonders.GameLogic.Move;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Deck.Card.CardEffect;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.RESOURCE_TYPE;
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


    public boolean playerCanMakeMove(MoveModel moveModel, PlayerModel currentPlayer, Pair<PlayerModel, PlayerModel> neighbours) {
        ACTION_TYPE action = moveModel.getAction();
        if ( !playerCanMakeTheTrade( moveModel, currentPlayer, neighbours)) {
            return false;
        }
        switch (action) {
            case DISCARD_CARD:
                return playerCanDiscardCard( moveModel, currentPlayer);
            case BUILD_CARD:
                return playerCanPlayBuildCard(moveModel, currentPlayer);
            case UPGRADE_WONDER:
                return playerCanBuildWonder(moveModel, currentPlayer);
            case USE_GOD_POWER:
                //TODO add god power here
                break;
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
        return playerResourceCost(requiredResources, currentPlayer, trades) == 0;
    }

    public int playerResourceCost( Map<RESOURCE_TYPE, Integer> requiredResources, PlayerModel currentPlayer, Vector<TradeAction> trades) {
        Map<RESOURCE_TYPE,Integer> clonedResourceMap = new HashMap<>(); //a map to be cloned

        /*to deep clone a map */
        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            if ( entry.getKey() != RESOURCE_TYPE.GOLD) {
                clonedResourceMap.put(entry.getKey(),entry.getValue());
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
            return 0;
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


        for (Map.Entry<RESOURCE_TYPE, Integer> entry : requiredResources.entrySet()) {
            if (entry.getKey() == RESOURCE_TYPE.BRICK ||
                    entry.getKey() == RESOURCE_TYPE.STONE ||
                    entry.getKey() == RESOURCE_TYPE.ORE ||
                    entry.getKey() == RESOURCE_TYPE.WOOD
            ) {
                if (leftDiscount) {
                    int count = 0;
                    if (neighbors.getKey().getWonder().getResource() == entry.getKey()) {
                        count++;
                    }
                    for (Card card : neighbors.getKey().getConstructionZone().getConstructedCards())  {
                        if (card.getColor() == CARD_COLOR_TYPE.BROWN) {

                        }
                    }
                } else if (rightDiscount) {

                }
            } else if (entry.getKey() == RESOURCE_TYPE.LOOM ||
                    entry.getKey() == RESOURCE_TYPE.PAPYRUS ||
                    entry.getKey() == RESOURCE_TYPE.GLASS
            ) {
                if (goodDiscount) {

                }
            }
        }

        return -1;
    }

    /**
     *  A recursive method for understanding if choice cards provide enough resources.
     * @param choiceCards all the choice cards that the user have in their constructionZone
     * @param begin size of the choiceCard vector
     * @param map resources that user requires to perform given action
     * @return true if choice cards provide necessary resources, false if not.
     */
    private int playerHasEnoughResourcesWitChoiceCards(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map) {
        if (begin == -1) {
            // gold ?
            return map.isEmpty() ? 0 : 999; // Implement minimumTradeCost(map);
        }
        Card card = choiceCards.get(begin);
        switch (card.getCardEffect().getEffectType()) {
            case ONE_OF_EACH_MANUFACTURED_GOODS:
                int loomCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.LOOM);
                int glassCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.GLASS);
                int papyrusCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.PAPYRUS);
                return Math.min(loomCost, Math.min(glassCost, papyrusCost));
            case ONE_OF_EACH_RAW_MATERIAL:
                int woodCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.WOOD);
                int brickCost = recursive(choiceCards, begin, map, RESOURCE_TYPE.BRICK);
                int stoneCost =  recursive(choiceCards, begin, map, RESOURCE_TYPE.STONE);
                int oreCost =  recursive(choiceCards, begin, map, RESOURCE_TYPE.ORE);
                return Math.min(Math.min(woodCost, brickCost), Math.min(stoneCost, oreCost));
            case PRODUCE_ONE_OF_TWO:
                Map<RESOURCE_TYPE, Integer> tempMap = card.getCardEffect().getResources();
                int bestCost = 999;
                for (var k : tempMap.keySet()) {
                   int cost = recursive(choiceCards, begin, map, k);
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
    private int recursive(Vector<Card> choiceCards, int begin,  Map<RESOURCE_TYPE,Integer> map, RESOURCE_TYPE resource_type) {
        int resourceCount = map.getOrDefault(resource_type, 0);
        if (resourceCount > 1) {
            map.put(resource_type, resourceCount-1);
        } else if (resourceCount == 1 || resourceCount == 0){
            map.remove(resource_type);
        }
        int cost  = playerHasEnoughResourcesWitChoiceCards(choiceCards, begin-1, map);
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
    private boolean playerCanPlayBuildCard(MoveModel moveModel, PlayerModel currentPlayer) {
         return checkConstructionZone( moveModel, currentPlayer) && playerHasEnoughResources( AssetManager.getInstance().getCardByID(moveModel.getSelectedCardID()).getRequirements(), currentPlayer, moveModel.getTrades());
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
    private boolean playerCanBuildWonder(MoveModel moveModel, PlayerModel currentPlayer) {
        return currentPlayer.getWonder().isUpgradeable() && playerHasEnoughResources(currentPlayer.getWonder().getCurrentStage().getRequiredResources(), currentPlayer, moveModel.getTrades());
    }
}
