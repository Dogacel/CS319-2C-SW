package SevenWonders.GameLogic;

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
    final int NUMBER_OF_AGES = 3;
    final int CARDS_PER_AGE = 49;

    private DeckModel deckModel;
    private Map<Integer, Card> cardMap;
    private Gson gson;

    public DeckController(DeckModel model)
    {
        this.deckModel = model;
        cardMap = new HashMap<>();
        gson = new Gson();
        setCards();
    }

    public Card[] getCurrentDeck(int age)
    {
        return deckModel.getCardsOfAge(age);
    }

    private void setCards(){
        Card[][] cards = new Card[NUMBER_OF_AGES][CARDS_PER_AGE];

        URL cardResourcesURL = getClass().getClassLoader().getResource("cards");
        assert cardResourcesURL != null;
        File dir = new File(cardResourcesURL.getPath());

        //Mapping each card to its id
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if( f.getName().endsWith(".json") ) {
                try {
                    FileReader fileReader = new FileReader(f.getAbsolutePath());
                    BufferedReader reader = new BufferedReader(fileReader);
                    Card myCard = gson.fromJson(reader, Card.class);
                    cardMap.put(myCard.getId(), myCard);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        //Creating the deck
        File ageFile = new File(cardResourcesURL.getPath() + "/age_mapping.json");
        try {
            FileReader fileReader = new FileReader(ageFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(fileReader);
            for(int i = 0; i < 3; i++){
                int[][] arr = gson.fromJson(reader, int[][].class);
                for(int j = 0; j < arr[i].length; j++){
                    cards[i][j] = cardMap.get(arr[i][j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        deckModel.setCards(cards);
    }

    public Card getCardByID(int id) { return cardMap.get(id); }

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
