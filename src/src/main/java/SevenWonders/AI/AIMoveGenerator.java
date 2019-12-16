package SevenWonders.AI;

import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.ACTION_TYPE;
import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Move.MoveController;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Move.TradeAction;
import SevenWonders.GameLogic.Player.PlayerModel;

public class AIMoveGenerator {
    public static MoveModel generateMove(PlayerModel player, AI_DIFFICULTY difficulty) {
        return new MoveModel(player.getId(), player.getHand().firstElement().getId(), ACTION_TYPE.DISCARD_CARD);
    }
}
