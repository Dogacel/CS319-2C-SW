package SevenWonders.GameLogic;

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
