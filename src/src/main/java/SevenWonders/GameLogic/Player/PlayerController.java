package SevenWonders.GameLogic.Player;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.*;
import SevenWonders.GameLogic.Game.GameController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Wonder.Wonder;
import SevenWonders.Network.Client;
import SevenWonders.SoundManager;
import javafx.scene.media.MediaPlayer;

import java.util.Vector;

public class PlayerController {
    private final int NO_OF_PLAYERS = 7;
    private final int DISCARD_REWARD = 3;

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

                if(playedCard.getColor() == CARD_COLOR_TYPE.RED && player.getConstructionZone().getRedHero() == 3){
                    player.addHero(AssetManager.getInstance().getRandomHeroByEffect(HERO_EFFECT_TYPE.GRANT_ONE_SHIELD));
                    player.getConstructionZone().resetRedHero();
                }
                else if(playedCard.getColor() == CARD_COLOR_TYPE.GREEN && player.getConstructionZone().getGreenHero() == 3){
                    player.addHero(AssetManager.getInstance().getRandomHeroByEffect(HERO_EFFECT_TYPE.GRANT_RANDOM_SCIENCE));
                    player.getConstructionZone().resetGreenHero();
                }
                else if(playedCard.getColor() == CARD_COLOR_TYPE.BLUE && player.getConstructionZone().getBlueHero() == 3){
                    player.addHero(AssetManager.getInstance().getRandomHeroByEffect(HERO_EFFECT_TYPE.GRANT_THREE_VP));
                    player.getConstructionZone().resetGreenHero();
                }

                System.out.println("Heroes of player with id " + player.getId() + ": " + player.getHeroes());

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
                //TODO Add God Power

                GOD_POWER_TYPE powerType = player.getWonder().getGod().getGodPower();

                switch(powerType){
                    case EXTRA_WAR_TOKENS:

                        break;

                    case TRADE_WITH_ANY:


                        break;

                    case VP_EACH_TURN:


                        break;

                    case EARTHQUAKE:

                        break;

                    case BLOCK_AND_DESTROY_CARD:

                        break;

                    case FORESIGHT:

                        break;

                    case FAMINE:

                        break;
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
