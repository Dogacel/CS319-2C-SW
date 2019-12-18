package SevenWonders.GameLogic;

import SevenWonders.GameLogic.Enums.CARD_COLOR_TYPE;
import SevenWonders.GameLogic.Enums.CARD_EFFECT_TYPE;
import SevenWonders.GameLogic.Enums.WONDER_EFFECT_TYPE;

public class ScoreController {
    
    // TODO: Optimize
    public int calculateScore(int playerID, GameModel model) {
        PlayerModel myPlayer = model.getPlayerList()[playerID];
        PlayerModel left = model.getPlayerList()[(playerID + 6) % 7];
        PlayerModel right = model.getPlayerList()[(playerID + 1) % 7];

        int score = 0;

        score += calculateMilitaryConflicts(myPlayer);
        score += calculateTreasuryContents(myPlayer);
        score += calculateWonders(myPlayer);
        score += calculateCivilianStructures(myPlayer);
        score += calculateCommercialStructures(myPlayer);
        score += calculateScientificStructures(myPlayer);
        score += calculateGuilds(myPlayer, left, right);

        return score;
    }

    private int calculateMilitaryConflicts(PlayerModel playerModel) {
        return playerModel.getWarPoints();
    }

    private int calculateTreasuryContents(PlayerModel playerModel) {
        return playerModel.getGold() / 3;
    }

    private int calculateWonders(PlayerModel playerModel) {
        Wonder wonder = playerModel.getWonder();
        int score = 0;
        for (int i = 0 ; i < wonder.getCurrentStage() ; i++) {
            if (wonder.getStages()[i].getWonderEffect().getEffectType() == WONDER_EFFECT_TYPE.GIVE_VICTORY_POINTS) {
                score += wonder.getStages()[i].getWonderEffect().getVictoryPoints();
            }
        }
        return score;
    }

    private int calculateCivilianStructures(PlayerModel playerModel) {
        int score = 0;
        for (Card card : playerModel.getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.BLUE) {
                score += card.getCardEffect().getVictoryPoints();
            }
        }
        return score;
    }

    private int calculateScientificStructures(PlayerModel playerModel) {
        int drawings = 0, mechanics = 0, writings = 0;
        for (Card card : playerModel.getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.GREEN) {
                switch (card.getCardEffect().getEffectType()) {
                    case SCIENCE_DRAWINGS:
                        drawings++;
                        break;
                    case SCIENCE_MECHANICS:
                        mechanics++;
                        break;
                    case SCIENCE_WRITINGS:
                        writings++;
                        break;
                }
            } else if (card.getCardEffect().getEffectType() == CARD_EFFECT_TYPE.SCIENTISTS_GUILD) {
                // TODO: Implement choose one
            }
        }
        return drawings*drawings + mechanics*mechanics + writings*writings + Math.min(drawings, Math.min(mechanics, writings))*7;
    }

    private int calculateCommercialStructures(PlayerModel playerModel) {
        int score = 0;
        for (Card card : playerModel.getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.YELLOW) {
                switch (card.getCardEffect().getEffectType()) {
                    case GET_MONEY_AND_VP_PER_BROWN:
                        score += countColor(playerModel, CARD_COLOR_TYPE.BROWN) * card.getCardEffect().getVictoryPoints();
                        break;
                    case GET_MONEY_AND_VP_PER_YELLOW:
                        score += countColor(playerModel, CARD_COLOR_TYPE.YELLOW) * card.getCardEffect().getVictoryPoints();
                        break;
                    case GET_MONEY_AND_VP_PER_GRAY:
                        score += countColor(playerModel, CARD_COLOR_TYPE.GRAY) * card.getCardEffect().getVictoryPoints();
                        break;
                    case GET_MONEY_AND_VP_PER_WONDER:
                        score += playerModel.getWonder().getCurrentStage() * card.getCardEffect().getVictoryPoints();
                        break;
                }
            }
        }
        return score;
    }

    private int calculateGuilds(PlayerModel playerModel, PlayerModel left, PlayerModel right) {
        int score = 0;
        for (Card card : playerModel.getConstructionZone().getConstructedCards()) {
            if (card.getColor() == CARD_COLOR_TYPE.PURPLE) {
                switch (card.getCardEffect().getEffectType()) {
                    case WORKERS_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(left, CARD_COLOR_TYPE.BROWN) +
                                countColor(right, CARD_COLOR_TYPE.BROWN));
                        break;
                    case CRAFTSMENS_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(left, CARD_COLOR_TYPE.GRAY) +
                                countColor(right, CARD_COLOR_TYPE.GRAY));
                        break;
                    case TRADERS_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(left, CARD_COLOR_TYPE.YELLOW) +
                                 countColor(right, CARD_COLOR_TYPE.YELLOW));
                        break;
                    case PHILOSOPHERS_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(left, CARD_COLOR_TYPE.GREEN) +
                                countColor(right, CARD_COLOR_TYPE.GREEN));
                        break;
                    case SPIES_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(left, CARD_COLOR_TYPE.RED) +
                                countColor(right, CARD_COLOR_TYPE.RED));
                        break;
                    case STRATEGISTS_GUILD:
                        // TODO: Change implementation of war points
                        break;
                    case SHIPOWNERS_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(playerModel, CARD_COLOR_TYPE.BROWN) +
                                        countColor(playerModel, CARD_COLOR_TYPE.GRAY) +
                                        countColor(playerModel, CARD_COLOR_TYPE.PURPLE));
                        break;
                    case MAGISTRATES_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (countColor(left, CARD_COLOR_TYPE.BLUE) +
                                 countColor(right, CARD_COLOR_TYPE.BLUE));
                        break;
                    case BUILDERS_GUILD:
                        score += card.getCardEffect().getVictoryPoints() *
                                (playerModel.getWonder().getCurrentStage()+
                                left.getWonder().getCurrentStage()+
                                right.getWonder().getCurrentStage());
                        break;
                }
            }
        }
        return score;
    }

    private int countColor(PlayerModel playerModel, CARD_COLOR_TYPE color) {
        int count = 0;
        for (Card card : playerModel.getConstructionZone().getConstructedCards()) {
            if (card.getColor() == color) {
                count++;
            }
        }
        return count;
    }
}
