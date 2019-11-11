package SevenWonders;

public class HandModel {
    private Card activeCard;
    private Card[] cards;

    public HandModel(Card activeCard, Card[] cards) {
        this.activeCard = activeCard;
        this.cards = cards;
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Card activeCard) {
        this.activeCard = activeCard;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
