/*
Ruben Alvarez Reyes
Javier Felix
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
    private Label dieStatusLabel;
    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player2ScoreLabel;
    @FXML
    private ImageView die1Image;
    @FXML
    private ImageView die2Image;
    @FXML
    private ImageView die3Image;
    @FXML
    private Button rollButton;
    @FXML
    private Button doneButton;
    @FXML
    private TextFlow feedbackLabel;
    @FXML
    private Label playerTurnLabel;
    @FXML
    private Label currentRoundLabel;

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
