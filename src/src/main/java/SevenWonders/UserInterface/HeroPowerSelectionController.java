package SevenWonders.UserInterface;

import SevenWonders.AssetManager;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Wonder.GodsAndHeroes.Hero;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HeroPowerSelectionController implements Initializable {

    GameplayController gameplayController;

    @FXML
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;

    public HeroPowerSelectionController() {
    }

    public void updateScene(GameModel gameModel) {
        for(Hero hero: gameModel.getPlayerList()[gameplayController.getPlayer().getId()].getHeroes()){
            String name = hero.getHeroType().name().toLowerCase();
            BackgroundImage backgroundImage = new BackgroundImage(AssetManager.getInstance().getImage(name + ".png"),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

            if(name.equals("achilles")){
                button1.setStyle("-fx-background-image: url('/images/hero-images/achilles.png');");
            }
            else if(name.equals("aristotales")){
                button2.setStyle("-fx-background-image: url('/images/hero-images/aristotales.png');");
            }
            else if(name.equals("da_vinci")){
                button3.setStyle("-fx-background-image: url('/images/hero-images/da_vinci.png');");
            }
            else if(name.equals("donatello")){
                button4.setStyle("-fx-background-image: url('/images/hero-images/donatello.png');");
            }
            else if(name.equals("gaudi")){
                button12.setStyle("-fx-background-image: url('/images/hero-images/gaudi.png');");
            }
            else if(name.equals("hector")){
                button5.setStyle("-fx-background-image: url('/images/hero-images/hector.png');");
            }
            else if(name.equals("ibni_sina")){
                button6.setStyle("-fx-background-image: url('/images/hero-images/ibni_sina.png');");
            }
            else if(name.equals("leonidas")){
                button7.setStyle("-fx-background-image: url('/images/hero-images/leonidas.png');");
            }
            else if(name.equals("michelangelo")){
                button8.setStyle("-fx-background-image: url('/images/hero-images/michelangelo.png');");
            }
            else if(name.equals("pisagor")){
                button9.setStyle("-fx-background-image: url('/images/hero-images/pisagor.png');");
            }
            else if(name.equals("spartacus")){
                button10.setStyle("-fx-background-image: url('/images/hero-images/spartacus.png');");
            }
            else if(name.equals("thales")){
                button11.setStyle("-fx-background-image: url('/images/hero-images/thales.png');");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button1.setTooltip(new Tooltip("Achilles"));
        button2.setTooltip(new Tooltip("Aristotales"));
        button3.setTooltip(new Tooltip("Da Vinci"));
        button4.setTooltip(new Tooltip("Donatello"));
        button5.setTooltip(new Tooltip("Gaudi"));
        button6.setTooltip(new Tooltip("Hector"));
        button7.setTooltip(new Tooltip("Ibni Sina"));
        button8.setTooltip(new Tooltip("Leonidas"));
        button9.setTooltip(new Tooltip("Michelangelo"));
        button10.setTooltip(new Tooltip("Pisagor"));
        button11.setTooltip(new Tooltip("Spartacus"));
        button12.setTooltip(new Tooltip("Thales"));
    }
}
