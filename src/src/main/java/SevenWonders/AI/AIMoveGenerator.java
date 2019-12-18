package SevenWonders.AI;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_EFFECT_TYPE;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerController;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.GameLogic.ScoreController;
import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.Random;
import java.util.Vector;

public class AIMoveGenerator {

    private static double scienceScore(MoveModel move, PlayerModel me, GameModel game) {
        double score = 0.0;
        Card card = AssetManager.getInstance().getCardByID(move.getSelectedCardID());

        if (!(move.getAction() == ACTION_TYPE.UPGRADE_WONDER &&
                me.getWonder().getCurrentStage().getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.CHOOSE_ONE_SCIENCE) &&
                !(card.getColor() == CARD_COLOR_TYPE.GREEN && move.getAction() == ACTION_TYPE.BUILD_CARD)) {
            return 0;
        }

        int playedGreens = -1;
        for (Card card1 : me.getHand()) {
            if (card1.getColor() == CARD_COLOR_TYPE.GREEN)
                playedGreens++;
        }

        for (PlayerModel player : game.getPlayerList()) {
            if (player.getId() != me.getId()) {
                for (Card card1 : player.getConstructionZone().getConstructedCards()) {
                    if (card1.getColor() == CARD_COLOR_TYPE.GREEN)
                        playedGreens++;
                }
            }
        }

        score -= playedGreens / 2.0;
        score -= ScoreController.calculateScore(me.getId(), game);

        if (move.getAction() == ACTION_TYPE.UPGRADE_WONDER) {
            game.getPlayerList()[me.getId()].getWonder().upgradeStage();
            score += ScoreController.calculateScore(me.getId(), game);
            game.getPlayerList()[me.getId()].getWonder().downgradeStage();
        } else {
            game.getPlayerList()[me.getId()].getConstructionZone().getConstructedCards().add(card);
            score += ScoreController.calculateScore(me.getId(), game);
            game.getPlayerList()[me.getId()].getConstructionZone().getConstructedCards().remove(card);
        }

        return score;
    }

    public static double evaluateMove(MoveModel move, PlayerModel me, GameModel game) {
        double score = 0.0;

        score += scienceScore(move, me, game);

        return score;
    }

    private static Vector<MoveModel> generateBestMoveListSorted(PlayerModel me, GameModel game) {
        Vector<MoveModel> moves = new Vector<MoveModel>();
        for (Card card : me.getHand()) {
            for (ACTION_TYPE at : ACTION_TYPE.values()) {
                MoveModel move = new MoveModel(me.getId(), card.getId(), at);
                Pair<PlayerModel, PlayerModel> neighbors = new Pair<>(game.getLeftPlayer(me.getId()), game.getRightPlayer(me.getId()));
                if (MoveController.getInstance().playerCanMakeMove(move, me, neighbors)) {
                    moves.add(move);
                }
            }
        }

        moves.sort((moveModel, t1) -> {
            return (int) (evaluateMove(moveModel, me, game) - evaluateMove(t1, me, game));
        });
        return moves;
    }

    private static double map(double value, double begin, double end) {
        return (value - begin) / (end - begin);
    }

    private static double getAccuracyIndex(AI_DIFFICULTY difficulty) {
        double x = Math.random();
        double l = difficulty.ordinal() + 1;
        return map(l * Math.exp(l * x), 0, l * Math.exp(l));
    }

    public static MoveModel generateMove(PlayerModel me, GameModel game, AI_DIFFICULTY difficulty) {
        Vector<MoveModel> moves = generateBestMoveListSorted(me, game);
        return moves.get((int) getAccuracyIndex(difficulty) * moves.size());
    }
}
