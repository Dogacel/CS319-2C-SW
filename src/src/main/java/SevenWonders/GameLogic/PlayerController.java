package SevenWonders.GameLogic;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import com.sun.javafx.geom.Vec2d;

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
                player.getConstructionZone().buildCard(AssetManager.getInstance().getCardByID(move.getSelectedCardID()));

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
                break;

        }


    }

    public MoveModel getCurrentMove(){ return player.getCurrentMove(); }

    public void setShields(int shields){ player.setShields(shields); }

    public void setGold(int gold){ player.setGold(gold); }

    public void setHand(Vector<Card> hand) { player.setHand(hand);}

    public ConstructionZone getConstructionZone() { return player.getConstructionZone(); }

    public Wonder getWonder() { return player.getWonder(); }

    public Vector<Card> getHand() { return player.getHand(); }

    public boolean isReady() { return player.isReady(); }

    public int getGold(){ return player.getGold(); }

    public int getShields() { return player.getShields(); }
}
