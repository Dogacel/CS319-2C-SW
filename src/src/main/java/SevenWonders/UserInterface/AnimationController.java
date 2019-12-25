package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Enums.GOD_POWER_TYPE;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Player.PlayerModel;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.God;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.Hero;
import SevenWonders.SceneManager;
import SevenWonders.SoundManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static SevenWonders.GameLogic.Enums.GOD_POWER_TYPE.*;
import static SevenWonders.GameLogic.Enums.HERO_EFFECT_TYPE.GRANT_ONE_SHIELD;
import static SevenWonders.GameLogic.Enums.HERO_EFFECT_TYPE.GRANT_RANDOM_SCIENCE;

public class AnimationController {

    public static void endOfAgeAnimation(GameplayController gameplayController, StackPane stackPane, GameModel gameModel) {
        stackPane.setVisible(true);
        ImageView shieldImage = new ImageView(AssetManager.getInstance().getImage("shield.png"));
        stackPane.getChildren().add(shieldImage);
        StackPane.setAlignment(shieldImage, Pos.CENTER);
        StackPane.setMargin(shieldImage, new Insets(0, 0, 50, 0));
        ImageView leftSword = new ImageView(AssetManager.getInstance().getImage("sword_left.png"));
        ImageView rightSword = new ImageView(AssetManager.getInstance().getImage("sword_right.png"));

        FadeTransition shield = new FadeTransition(Duration.millis(2000), shieldImage);
        shield.setFromValue(0.0);
        shield.setToValue(1.0);
        shield.play();

        TranslateTransition swordLeft = new TranslateTransition(Duration.millis(500), leftSword);
        swordLeft.setFromX(-500);
        swordLeft.setToX(-50);
        swordLeft.setFromY(-500);
        swordLeft.setToY(-100);
        swordLeft.play();

        TranslateTransition swordRight = new TranslateTransition(Duration.millis(500), rightSword);
        swordRight.setFromX(500);
        swordRight.setToX(50);
        swordRight.setFromY(-500);
        swordRight.setToY(-100);
        swordRight.play();

        shield.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                FadeTransition pane = new FadeTransition(Duration.millis(2500), stackPane);
                pane.setFromValue(1.0);
                pane.setToValue(0.0);
                pane.play();
                pane.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        FadeTransition pane = new FadeTransition(Duration.millis(10), stackPane);
                        pane.setFromValue(0.0);
                        pane.setToValue(1.0);
                        pane.play();
                        stackPane.setVisible(false);
                        stackPane.getChildren().clear();
                    }
                });
            }
        });

        stackPane.getChildren().add(leftSword);
        stackPane.getChildren().add(rightSword);

        PlayerModel player = gameModel.getPlayerList()[gameplayController.getPlayer().getId()];
        PlayerModel leftPlayer = gameModel.getLeftPlayer(gameplayController.getPlayer().getId());
        PlayerModel rightPlayer = gameModel.getRightPlayer(gameplayController.getPlayer().getId());

        ImageView shieldRight, shieldLeft;
        if (player.getShields() < rightPlayer.getShields()) {
            shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_minus1.png"));
            shieldRight.setScaleX(1.5);
            shieldRight.setScaleY(1.5);
        } else if (player.getShields() == rightPlayer.getShields()) {
            shieldRight = null;
        } else {
            if (gameplayController.gameModel.getCurrentAge() == 1) {
                shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_1.png"));
            } else if (gameplayController.gameModel.getCurrentAge() == 2) {
                shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_3.png"));
            } else if (gameplayController.gameModel.getCurrentAge() == 3) {
                shieldRight = new ImageView(AssetManager.getInstance().getImage("warpoint_5.png"));
            } else {
                shieldRight = null;
            }

            if (shieldRight != null) {
                shieldRight.setScaleY(0.8);
                shieldRight.setScaleX(0.8);
            }
        }

        if (player.getShields() < leftPlayer.getShields()) {
            shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_minus1.png"));
            shieldLeft.setScaleY(1.5);
            shieldLeft.setScaleX(1.5);
        } else if (player.getShields() == leftPlayer.getShields()) {
            shieldLeft = null;
        } else {
            if (gameplayController.gameModel.getCurrentAge() == 1) {
                shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_1.png"));
            } else if (gameplayController.gameModel.getCurrentAge() == 2) {
                shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_3.png"));
            } else if (gameplayController.gameModel.getCurrentAge() == 3) {
                shieldLeft = new ImageView(AssetManager.getInstance().getImage("warpoint_5.png"));
            } else {

                shieldLeft = null;
            }

            if (shieldLeft != null) {
                shieldLeft.setScaleY(0.8);
                shieldLeft.setScaleX(0.8);
            }
        }

        TranslateTransition leftShield = new TranslateTransition(Duration.millis(1500), shieldLeft);
        leftShield.setFromX(-700);
        leftShield.setToX(-50);
        leftShield.setFromY(-100);
        leftShield.setToY(-100);
        leftShield.play();

        TranslateTransition rightShield = new TranslateTransition(Duration.millis(1500), shieldRight);
        rightShield.setFromX(700);
        rightShield.setToX(50);
        rightShield.setFromY(-100);
        rightShield.setToY(-100);
        rightShield.play();

        if (shieldLeft != null) {
            stackPane.getChildren().add(shieldLeft);
        }
        if (shieldRight != null) {
            stackPane.getChildren().add(shieldRight);
        }
    }

    public static void startOfAgeAnimation(GameplayController gameplayController, StackPane stackPane, GameModel gameModel) {
        Text ageText = new Text();
        if (gameplayController.gameModel.getCurrentAge() == 3) {
            ageText.setText("GAME OVER   ");
        } else {
            ageText.setText("AGE " + (gameplayController.gameModel.getCurrentAge() + 1) + "   ");
        }
        ageText.setStyle("-fx-font-family: 'Assassin$';" + "-fx-font-size: 80px;" + "-fx-fill: white;");

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.RED);
        int depth1 = 120;
        borderGlow.setWidth(depth1);
        borderGlow.setHeight(depth1);
        ageText.setEffect(borderGlow);

        TranslateTransition textAnim = new TranslateTransition(Duration.millis(2500), ageText);
        textAnim.setFromX(50);
        textAnim.setToX(50);
        textAnim.setFromY(-500);
        textAnim.setToY(-200);
        textAnim.play();
        textAnim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FadeTransition ageFade = new FadeTransition(Duration.millis(500), ageText);
                ageFade.setFromValue(1.0);
                ageFade.setToValue(0.0);
                ageFade.play();
                if (gameplayController.gameModel.getGameFinished()) {
                    SoundManager.getInstance().stopAgeThreeMusic();
                    SoundManager.getInstance().playEndMusic();

                    var sceneAndController = AssetManager.getInstance().getSceneAndController("ScoreView.fxml");
                    ScoreViewController scoreViewController = (ScoreViewController) sceneAndController.getValue();
                    scoreViewController.gameplayController = gameplayController;
                    Parent scene = sceneAndController.getKey();

                    SceneManager.getInstance().changeScene(scene);
                }
            }
        });
        stackPane.getChildren().add(ageText);
        StackPane.setAlignment(ageText, Pos.CENTER);
    }

    public static void heroAnimation(PlayerModel playerModel, StackPane stackPane) {
        stackPane.setVisible(true);
        Hero hero = playerModel.getHeroes().lastElement();

        Text heroText = new Text();
        Text effectText = new Text();
        heroText.setText(hero.getHeroType().name().toUpperCase().replaceAll("_", " ") + "\nHAS\nARRIVED!");

        if (hero.getHeroEffect() == GRANT_ONE_SHIELD) {
            effectText.setText("       GAINED 1 SHIELD");
        } else if (hero.getHeroEffect() == GRANT_RANDOM_SCIENCE) {
            effectText.setText("       GAINED ONE SCIENCE ARTIFACT");
        } else {
            effectText.setText("       GAINED THREE VICTORY POINTS");
        }

        heroText.setStyle("-fx-font-family: 'Assassin$';" + "-fx-font-size: 80px;" + "-fx-fill: white;");
        effectText.setStyle("-fx-font-family: 'Assassin$';" + "-fx-font-size: 30px;" + "-fx-fill: red;" + "-fx-stroke: white;" + "-fx-stroke-width: 1px;");
        heroText.setTextAlignment(TextAlignment.CENTER);
        effectText.setTextAlignment(TextAlignment.CENTER);

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.RED);
        int depth1 = 120;
        borderGlow.setWidth(depth1);
        borderGlow.setHeight(depth1);
        heroText.setEffect(borderGlow);

        TranslateTransition effectAnim = new TranslateTransition(Duration.millis(2000), effectText);
        effectAnim.setFromX(-50);
        effectAnim.setToX(-50);
        effectAnim.setFromY(400);
        effectAnim.setToY(120);
        effectAnim.play();

        FadeTransition textAnim = new FadeTransition(Duration.millis(2000), heroText);
        textAnim.setFromValue(0.0);
        textAnim.setToValue(1.0);
        textAnim.play();
        textAnim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FadeTransition heroFade = new FadeTransition(Duration.millis(1000), heroText);
                heroFade.setFromValue(1.0);
                heroFade.setToValue(0.0);
                heroFade.play();
                heroFade.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        stackPane.setVisible(false);
                    }
                });
            }
        });
        stackPane.getChildren().add(heroText);
        stackPane.getChildren().add(effectText);
        StackPane.setAlignment(heroText, Pos.CENTER);
        StackPane.setMargin(heroText, new Insets(0, 0, 50, 0));
    }

    public static void wonderCompletedAnimation() {
        final int[] x = {0};
        final int[] y = {0};

        Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.03), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (x[0] == 0) {
                    SceneManager.getInstance().getStage().setX(SceneManager.getInstance().getStage().getX() + 5);
                    x[0] = 1;
                } else {
                    SceneManager.getInstance().getStage().setX(SceneManager.getInstance().getStage().getX() - 5);
                    x[0] = 0;
                }
            }
        }));

        timelineX.setCycleCount(40);
        timelineX.setAutoReverse(true);
        timelineX.play();

        timelineX.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SceneManager.getInstance().getStage().setX(SceneManager.getInstance().getStage().getX() -5);
            }
        });
    }
    public static void godAnimation(PlayerModel playerModel, StackPane stackPane) {
        stackPane.setVisible(true);
        God god = playerModel.getWonder().getGod();

        Text godText = new Text();
        Text effectText = new Text();
        godText.setText(god.getGodType().name().toUpperCase().replaceAll("_", " ") + "\nIS\nSUMMONED!");

        if (god.getGodPower() == EXTRA_WAR_TOKENS) {
            effectText.setText("       GAINED 5 SHIELDS");
        } else if (god.getGodPower() == SCIENTIFIC_REGRESSION) {
            effectText.setText("       REMOVED 1 SCIENCE TOKEN FROM EVERY PLAYER");
        } else if ( god.getGodPower() == VP_EACH_TURN) {
            effectText.setText("       1 VICTORY POINT EACH TURN ");
        }
        else if (god.getGodPower() == EARTHQUAKE) {
            effectText.setText("       NEIGHBOR'S LAST WONDER STAGE DESTROYED ");
        }
        else if (god.getGodPower() == BLOCK_AND_DESTROY_CARD) {
            effectText.setText("       NEIGHBORS'S CAN'T BUILD");
        }
        else if (god.getGodPower() == FORESIGHT) {
            effectText.setText("       SEE THE HAND YOU WILL RECEIVE ");
        }
        else if (god.getGodPower() == ECONOMIC_DEPRESSION) {
            effectText.setText("      ECONOMIC DEPRESSION");
        }

        godText.setStyle("-fx-font-family: 'Assassin$';" + "-fx-font-size: 80px;" + "-fx-fill: white;");
        effectText.setStyle("-fx-font-family: 'Assassin$';" + "-fx-font-size: 30px;" + "-fx-fill: red;" + "-fx-stroke: white;" + "-fx-stroke-width: 1px;");
        godText.setTextAlignment(TextAlignment.CENTER);
        effectText.setTextAlignment(TextAlignment.CENTER);

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.RED);
        int depth1 = 120;
        borderGlow.setWidth(depth1);
        borderGlow.setHeight(depth1);
        godText.setEffect(borderGlow);

        TranslateTransition effectAnim = new TranslateTransition(Duration.millis(2000), effectText);
        effectAnim.setFromX(-50);
        effectAnim.setToX(-50);
        effectAnim.setFromY(400);
        effectAnim.setToY(120);
        effectAnim.play();

        FadeTransition textAnim = new FadeTransition(Duration.millis(2000), godText);
        textAnim.setFromValue(0.0);
        textAnim.setToValue(1.0);
        textAnim.play();
        textAnim.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FadeTransition heroFade = new FadeTransition(Duration.millis(1000), godText);
                heroFade.setFromValue(1.0);
                heroFade.setToValue(0.0);
                heroFade.play();
                heroFade.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        stackPane.setVisible(false);
                    }
                });
            }
        });
        stackPane.getChildren().add(godText);
        stackPane.getChildren().add(effectText);
        StackPane.setAlignment(godText, Pos.CENTER);
        StackPane.setMargin(godText, new Insets(0, 0, 50, 0));
    }

}