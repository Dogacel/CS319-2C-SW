package SevenWonders.GameLogic;



public class DeckModel {

    private Card[][] cards;
    final int NUMBER_OF_AGES = 3;
    final int CARDS_PER_AGE = 49;

    public DeckModel()
    {
        cards = new Card[NUMBER_OF_AGES][CARDS_PER_AGE];
        initializeDeck();
    }

    private void initializeDeck()
    {
        //TODO, file read and initialization of each card should be done here.
    }

    public Card[][] getCards()
    {
        return cards;
    }

    public void shuffleCards()
    {
        //TODO, card shuffling
    }
}
