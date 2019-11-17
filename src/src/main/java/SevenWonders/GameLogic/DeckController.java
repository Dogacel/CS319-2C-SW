package SevenWonders.GameLogic;

import java.util.Random;

public class DeckController {
    private DeckModel deckModel;

    public DeckController(DeckModel model)
    {
        this.deckModel = model;
    }

    public Card[] getCurrentDeck(int age)
    {
        return deckModel.getCardsOfAge(age);
    }

    public void shuffleDecks()
    {
        for ( int i = 0; i < 3; i++){

            Card[] cardsOfAge = deckModel.getCardsOfAge(i);
            for (int j = cardsOfAge.length - 1; j > 0; j--)
            {
                Random rnd = new Random();
                int index = rnd.nextInt(j + 1);

                Card tmp = cardsOfAge[index];
                cardsOfAge[index] = cardsOfAge[j];
                cardsOfAge[j] = tmp;
            }
        }
    }
}
