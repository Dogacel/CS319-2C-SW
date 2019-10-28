package SevenWonders.model;

public class Card {

	private String name;
	private int cost;
	private Resource[] resourceCost;
	private Card[] preconditionCards;
	private Object[] rewardParameters;
	private CardType type;
	private CardColor color;

}