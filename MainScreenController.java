/*
Ruben Alvarez Reyes
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;


public class MainScreenController {

    private final static String imgPath = "asset\\img\\%s.png";
    private final static Image dieImages[] = {
            new Image(String.format(imgPath, 1)),
            new Image(String.format(imgPath, 2)),
            new Image(String.format(imgPath, 3)),
            new Image(String.format(imgPath, 4)),
            new Image(String.format(imgPath, 5)),
            new Image(String.format(imgPath, 6))
    };

    private MainScreenModel model;

    @FXML
    Label dieStatusLabel;
    @FXML
    Label player1ScoreLabel;
    @FXML
    Label player2ScoreLabel;
    @FXML
    ImageView die1Image;
    @FXML
    ImageView die2Image;
    @FXML
    ImageView die3Image;
    @FXML
    Button rollButton;
    @FXML
    Button doneButton;
    @FXML
    TextFlow feedbackLabel;
    @FXML
    Label playerTurnLabel;
    @FXML
    Label currentRoundLabel;

    public MainScreenController() {
        model = new MainScreenModel();
    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void onRollPressed() {
        System.out.println("Roll pressed");
    }

    @FXML
    public void onDonePressed() {
        System.out.println("Done pressed");
    }

}
