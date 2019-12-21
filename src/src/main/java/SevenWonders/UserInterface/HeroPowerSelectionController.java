package SevenWonders.UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HeroPowerSelectionController implements Initializable {

    @FXML
    Button button1;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Button button4;
    @FXML
    Button button5;
    @FXML
    Button button6;
    @FXML
    Button button7;
    @FXML
    Button button8;
    @FXML
    Button button9;
    @FXML
    Button button10;
    @FXML
    Button button11;
    @FXML
    Button button12;

    public HeroPowerSelectionController() {
    }
    @FXML
    public void button1Clicked(MouseEvent mouseEvent) {

    }

    @FXML
    public void button2Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button3Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button4Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button5Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button6Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button7Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button8Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button9Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button10Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button11Clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void button12Clicked(MouseEvent mouseEvent) {
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
