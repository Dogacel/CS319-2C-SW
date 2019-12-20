package SevenWonders.GameLogic.Player;

import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;

import java.util.Vector;

public class ConstructionZone {

    private Vector<Card> constructedCards;
    private int redHero;
    private int greenHero;
    private int blueHero;

    public ConstructionZone()
    {
        constructedCards = new Vector<>();
    }

    public boolean canBuildCard(Card card)
    {
        if (card.getColor() == CARD_COLOR_TYPE.BROWN || card.getColor() == CARD_COLOR_TYPE.GRAY)
            return true;  //If the card is brown or gray, duplicate cards are allowed.
        else
        {
            for (Card alreadyBuilt: constructedCards)
            {
                if (alreadyBuilt.getName() == card.getName())
                    return false;  //If a card with the same name is already built, and it is not brown and gray
            }                      //it cannot be built.
        }

        return true; //If no complication is found, card can be built.
    }
    public void buildCard(Card card)
    {
        if(card.getColor() == CARD_COLOR_TYPE.RED)
            redHero++;
        else if(card.getColor() == CARD_COLOR_TYPE.GREEN)
            greenHero++;
        else if(card.getColor() == CARD_COLOR_TYPE.BLUE)
            blueHero++;

        constructedCards.add(card);

    }

    public Vector<Card> getConstructedCards(){ return constructedCards; }

    public void resetRedHero() { this.redHero = 0; }

    public void resetGreenHero() { this.greenHero = 0; }

    public void resetBlueHero() { this.blueHero = 0; }

    public int getRedHero() { return this.redHero; }

    public int getGreenHero() { return this.greenHero; }

    public int getBlueHero() { return this.blueHero; }
}
