package SevenWonders.GameLogic;

import SevenWonders.AssetManager;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class DeckController {
    static final int NUMBER_OF_AGES = 3;
    static final int CARDS_PER_AGE = 49;

    private DeckModel deckModel;

    public DeckController(DeckModel model)
    {
        this.deckModel = model;
        setCards();
    }

    public Card[] getCurrentDeck(int age)
    {
        return deckModel.getCardsOfAge(age);
    }

    private void setCards(){
        deckModel.setCards(AssetManager.getInstance().mapDeck());
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
