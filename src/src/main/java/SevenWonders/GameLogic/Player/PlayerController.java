package SevenWonders.GameLogic.Player;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.*;
import SevenWonders.GameLogic.Game.GameController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Wonder.Wonder;

import java.util.Vector;

public class PlayerController {
    public final int DISCARD_REWARD = 3;
    public final int ARES_SHIELD_BONUS = 5;

    private PlayerModel player;
    private GameController gameController;

    public PlayerController(PlayerModel player, GameController gameController){

        this.player = player;
        this.gameController = gameController;
    }

    public void updateCurrentMove(MoveModel move){
        player.setCurrentMove(move);
    }

    public void makeMove(){
        MoveModel move = player.getCurrentMove();

        if (move == null) {
            move = new MoveModel(player.getId(), player.getHand().firstElement().getId(), ACTION_TYPE.DISCARD_CARD);
            player.setCurrentMove(move);
        }

        ACTION_TYPE action = move.getAction();

        switch (action){
            case DISCARD_CARD:
                gameController.discardCard(AssetManager.getInstance().getCardByID(move.getSelectedCardID()));

                player.setGold(player.getGold() + DISCARD_REWARD);

                for (Card card : player.getHand())
                {
                    if (card.getId() == move.getSelectedCardID()) {
                        //Played card is found, remove it from players hand
                        player.getHand().remove(card);
                        break; //Remove only one card
                    }
                }
                break;
            case BUILD_CARD:
                Card playedCard = AssetManager.getInstance().getCardByID(move.getSelectedCardID());
                player.getConstructionZone().buildCard(playedCard);

                int goldCost = playedCard.getRequirements().getOrDefault(RESOURCE_TYPE.GOLD, 0);
                player.setGold(player.getGold() - goldCost);


                if(playedCard.getColor() == CARD_COLOR_TYPE.RED && player.getConstructionZone().getRedHero() == 3){

                    player.addHero(AssetManager.getInstance().getRandomHeroByEffect(HERO_EFFECT_TYPE.GRANT_ONE_SHIELD, player.getHeroes()));
                    player.getConstructionZone().resetRedHero();
                }
                else if(playedCard.getColor() == CARD_COLOR_TYPE.GREEN && player.getConstructionZone().getGreenHero() == 3){
                    player.addHero(AssetManager.getInstance().getRandomHeroByEffect(HERO_EFFECT_TYPE.GRANT_RANDOM_SCIENCE, player.getHeroes()));
                    player.getConstructionZone().resetGreenHero();
                }
                else if(playedCard.getColor() == CARD_COLOR_TYPE.BLUE && player.getConstructionZone().getBlueHero() == 3){
                    player.addHero(AssetManager.getInstance().getRandomHeroByEffect(HERO_EFFECT_TYPE.GRANT_THREE_VP, player.getHeroes()));
                    player.getConstructionZone().resetGreenHero();
                }

                for (Card card : player.getHand())
                {
                    if (card.getId() == move.getSelectedCardID()) {
                        //Played card is found, remove it from players hand
                        player.getHand().remove(card);
                        break; //Remove only one card
                    }
                }
                break;
            case UPGRADE_WONDER:

                player.getWonder().upgradeStage();
                for (Card card : player.getHand())
                {
                    if (card.getId() == move.getSelectedCardID()) {
                        //Played card is found, remove it from players hand
                        player.getHand().remove(card);
                        break; //Remove only one card
                    }
                }
                break;
            case USE_GOD_POWER:
                gameController.discardCard(AssetManager.getInstance().getCardByID(move.getSelectedCardID()));

                GOD_POWER_TYPE powerType = player.getWonder().getGod().getGodPower();

                if (powerType == GOD_POWER_TYPE.EXTRA_WAR_TOKENS) {
                    player.setShields(player.getShields() + ARES_SHIELD_BONUS);
                }

                break;

        }


    }

    public MoveModel getCurrentMove(){ return player.getCurrentMove(); }

    public void setShields(int shields){ player.setShields(shields); }

    public void setGold(int gold){ player.setGold(gold); }

    public void setHand(Vector<Card> hand) { player.setHand(hand);}

    public void setReady(boolean ready) { player.setReady(ready);}

    public void setWarPoints(int warPoints) { player.setWarPoints(warPoints); }

    public void setLostWarNumber(int lostWarNumber) { player.setLostWarNumber(lostWarNumber); }

    public ConstructionZone getConstructionZone() { return player.getConstructionZone(); }

    public Wonder getWonder() { return player.getWonder(); }

    public Vector<Card> getHand() { return player.getHand(); }

    public boolean isReady() { return player.isReady(); }

    public int getGold(){ return player.getGold(); }

    public int getShields() { return player.getShields(); }

    public int getWarPoints() { return player.getWarPoints(); }

    public int getLostWarNumber() { return player.getLostWarNumber(); }

    public int getId() {
        return player.getId();
    }

    public PlayerModel getPlayer() { return player; }
}
