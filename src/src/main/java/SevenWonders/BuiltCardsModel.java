package SevenWonders;

public class BuiltCardsModel {
    private Card[] cards;
    private TradeAction[] activeTrades;

    public BuiltCardsModel(Card[] cards, TradeAction[] activeTrades) {
        this.cards = cards;
        this.activeTrades = activeTrades;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public TradeAction[] getActiveTrades() {
        return activeTrades;
    }

    public void setActiveTrades(TradeAction[] activeTrades) {
        this.activeTrades = activeTrades;
    }
}
