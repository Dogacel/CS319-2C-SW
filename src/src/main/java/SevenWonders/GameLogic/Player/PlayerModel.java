package SevenWonders.GameLogic.Player;

import SevenWonders.GameLogic.Deck.Card.Card;
import SevenWonders.GameLogic.Enums.HERO_EFFECT_TYPE;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.Hero;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.GameLogic.Wonder.Wonder;

import java.util.Vector;

public class PlayerModel {

    private final int MAX_CARDS_AT_HAND = 7;

    private int              id;
    private String           name;
    private int              gold;
    private Vector<Card>     hand;
    private Wonder           wonder;
    private int              shields;
    private int              warPoints;
    private int              lostWarNumber;
    private boolean          isReady;
    private MoveModel        currentMove;
    private Vector<Hero>     heroes;
    private ConstructionZone constructionZone;


    public PlayerModel(int id, String name, Wonder wonder)
    {
        this.id = id;
        this.name = name;
        this.gold = 3;

        this.wonder = wonder;
        this.warPoints = 0;
        this.lostWarNumber = 0;
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

    public int getLostWarNumber() { return lostWarNumber; }

    public boolean isReady() { return isReady; }

    public MoveModel getCurrentMove() { return currentMove; }

    public Vector<Card> getHand() { return hand; }

    public ConstructionZone getConstructionZone() { return constructionZone; }

    public Vector<Hero> getHeroes() { return heroes; }

    public int getShields() { return shields; }

    public void setGold(int gold) { this.gold = gold; }

    public void setWarPoints(int warPoints) { this.warPoints = warPoints; }

    public void setLostWarNumber(int lostWarNumber) { this.lostWarNumber = lostWarNumber; }

    public void setReady(boolean ready) { isReady = ready; }

    public void setCurrentMove(MoveModel currentMove) { this.currentMove = currentMove; }

    public void setHand(Vector<Card> hand) { this.hand = hand; }

    public void setShields(int shields) { this.shields = shields; }

    public void addHero(Hero hero) {

        if (hero.getHeroEffect() == HERO_EFFECT_TYPE.GRANT_ONE_SHIELD)
            this.shields++;
        heroes.add(hero);

    }

}
