package SevenWonders.GameLogic;

import java.util.ArrayList;

public class PlayerModel {

    private final int MAX_CARDS_AT_HAND = 7;

    private int       id;
    private String    name;
    private int       gold;
    private Card[]    hand;
    private Wonder    wonder;
    private int       warPoints;
    private boolean   isReady;
    private MoveModel currentMove;
    private ArrayList<Hero> heroes;  //TODO Might change, did like this because hero number is not limited
    private ConstructionZone constructionZone;

    public PlayerModel(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.gold = 0;

        this.hand = new Card[MAX_CARDS_AT_HAND];
        initializeCards();

        this.wonder = new Wonder(); //TODO, Change according to the wonder constructor
        this.warPoints = 0;
        this.isReady = false;
        this.currentMove = new MoveModel(); //TODO, Change according to the wonder constructor

        this.heroes = new ArrayList<Hero>();

        this.constructionZone = new ConstructionZone(); //TODO, Change according to CZ constructor
    }

    private void initializeCards()
    {
        //TODO Question: Will the players have a hand full of cards when they are constructed? If so, do it here.
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getGold() { return gold; }

    public Wonder getWonder() { return wonder; }

    public int getWarPoints() { return warPoints; }

    public boolean isReady() { return isReady; }

    public MoveModel getCurrentMove() { return currentMove; }

    public Card[] getHand() { return hand; }

    public ConstructionZone getConstructionZone() { return constructionZone; }

    public ArrayList<Hero> getHeroes() { return heroes; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setGold(int gold) { this.gold = gold; }

    public void setWonder(Wonder wonder) { this.wonder = wonder; }

    public void setWarPoints(int warPoints) { this.warPoints = warPoints; }

    public void setReady(boolean ready) { isReady = ready; }

    public void setCurrentMove(MoveModel currentMove) { this.currentMove = currentMove; }

    public void setHand(Card[] hand) { this.hand = hand; }

    public void addHero(String name, HERO_EFFECT_TYPE effect) {

        heroes.add(Hero(name, effect));

    }
}
