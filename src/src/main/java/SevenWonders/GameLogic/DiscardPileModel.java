package SevenWonders.GameLogic;

import java.util.Vector;

public class DiscardPileModel {

    private Vector<Card> cards;

    public DiscardPileModel()
    {
        cards = new Vector<>();
    }

    public void discardCard(Card card)
    {
        cards.add(card);
    }

    public Vector<Card> getCards()
    {
        return cards;
    }

}
