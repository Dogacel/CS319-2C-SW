package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.HERO_EFFECT_TYPE;

import java.util.ArrayList;
import java.util.Vector;

public class PlayerModel {

    private final int MAX_CARDS_AT_HAND = 7;

    private int          id;
    private String       name;
    private int          gold;
    private Vector<Card> hand;
    private Wonder       wonder;
    private int          shields;
    private int          warPoints;
    private boolean      isReady;
    private MoveModel    currentMove;
    private Vector<Hero> heroes;
    private ConstructionZone constructionZone;
    public PlayerModel(int id, String name, Wonder wonder)
    {
        this.id = id;
        this.name = name;
        this.gold = 5;

        this.wonder = wonder;
        this.warPoints = 0;
        this.isReady = false;

        this.hand = new Vector<>();
        this.heroes = new Vector<>();

        this.constructionZone = new ConstructionZone();

    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getGold() { return gold; }

    public Wonder getWonder() { return wonder; }

    public int getWarPoints() { return warPoints; }

    public boolean isReady() { return isReady; }

    public MoveModel getCurrentMove() { return currentMove; }

    public Vector<Card> getHand() { return hand; }

    public ConstructionZone getConstructionZone() { return constructionZone; }

    public Vector<Hero> getHeroes() { return heroes; }

    public int getShields() { return shields; }

    public void setGold(int gold) { this.gold = gold; }

    public void setWarPoints(int warPoints) { this.warPoints = warPoints; }

    public void setReady(boolean ready) { isReady = ready; }

    public void setCurrentMove(MoveModel currentMove) { this.currentMove = currentMove; }

    public void setHand(Vector<Card> hand) { this.hand = hand; }

    public void setShields(int shields) { this.shields = shields; }

    public void addHero(String name, HERO_EFFECT_TYPE effect) {

        heroes.add(new Hero(name, effect));

    }

}
