package SevenWonders.GameLogic.Game;

import SevenWonders.GameLogic.Deck.Card.Card;

public class DiscardPileController {
    private DiscardPileModel discardPile;

    public DiscardPileController( DiscardPileModel model)
    {
        this.discardPile = model;
    }

    public void discardCard(Card card)
    {
        discardPile.discardCard(card);
    }

}
