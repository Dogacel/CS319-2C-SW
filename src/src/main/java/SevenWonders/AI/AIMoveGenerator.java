package SevenWonders.AI;

import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerController;
import SevenWonders.GameLogic.Player.PlayerModel;
import javafx.util.Pair;

import java.util.Random;
import java.util.Vector;

public class AIMoveGenerator {
    private static double evaluateMove(MoveModel move, PlayerModel me, GameModel game) {
        return 0.0;
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
