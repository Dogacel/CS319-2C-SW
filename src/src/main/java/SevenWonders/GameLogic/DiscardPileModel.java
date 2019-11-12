package SevenWonders.GameLogic;

import java.util.ArrayList;

public class DiscardPileModel {

    private ArrayList<Card> cards;

    public DiscardPileModel()
    {
        cards = new ArrayList<Card>();
    }

    public void updateDiscardPile(Card card)
    {
        cards.add(card);
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

}
