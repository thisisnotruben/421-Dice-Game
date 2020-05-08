/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import java.util.Random;


public class MainScreenController {

    private final static String imgPath = "asset/img/%s.png";
    private final static Image[] dieImages = {
            new Image(String.format(imgPath, 1)),
            new Image(String.format(imgPath, 2)),
            new Image(String.format(imgPath, 3)),
            new Image(String.format(imgPath, 4)),
            new Image(String.format(imgPath, 5)),
            new Image(String.format(imgPath, 6)),
            new Image(String.format(imgPath, 7))
    };
    private final static String[] sndPaths = {
            "asset/snd/roll_die.wav",
            "asset/snd/switch_die.wav"
    };

    private final AudioClip rollDieSnd;
    private final AudioClip switchDieSnd;
    private final MainScreenModel model;
    private boolean resetted;

    @FXML
    private GridPane root;
    @FXML
    private Label dieStatusLabel;
    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player1RollLabel;
    @FXML
    private Label player2ScoreLabel;
    @FXML
    private Label player2RollLabel;
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
    private TextArea feedbackTextArea;
    @FXML
    private Label playerTurnLabel;
    @FXML
    private Label currentRoundLabel;
    @FXML
    private CheckBox keepDie1Chk;
    @FXML
    private CheckBox keepDie2Chk;
    @FXML
    private CheckBox keepDie3Chk;

    public MainScreenController() {
        model = new MainScreenModel();
        rollDieSnd = new AudioClip(getClass().getResource(sndPaths[0]).toString());
        switchDieSnd = new AudioClip(getClass().getResource(sndPaths[1]).toString());
    }

    @FXML
    public void initialize() {
        newTurn();
//        set colors
        root.setBackground(new Background(new BackgroundFill(Color.rgb(20, 158, 66), CornerRadii.EMPTY, Insets.EMPTY)));
        rollButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 193, 29), CornerRadii.EMPTY, Insets.EMPTY)));
        doneButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 29, 62), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    public void onRollPressed() {
        resetted = false;
//        play sound
        rollDieSnd.play();

//        set roll counter text
        int playerTurn = model.getPlayerTurn();
        String rollText = String.format("Roll: %d", model.getPlayerRollCount(playerTurn) + 1);
        String defaultRollText = "Roll: 0";
        if (playerTurn == 0) {
            player1RollLabel.setText(rollText);
            player2RollLabel.setText(defaultRollText);
        } else {
            player2RollLabel.setText(rollText);
            player1RollLabel.setText(defaultRollText);
        }

//        roll die
        int playerRollCount = model.getPlayerRollCount(playerTurn) + 1;
        model.setDie(rollDie(), rollDie(), rollDie());

//        update feedback label
        String feedbackText = String.format("Player %d %s roll rolled: %d-%d-%d!",
                playerTurn + 1, ordinal(playerRollCount),
                model.getDie(playerTurn, 0), model.getDie(playerTurn, 1), model.getDie(playerTurn, 2));
        updateFeedbackLabel(feedbackText);

//        update main view
        System.out.println(model.getDie(0));
        if (playerTurn == model.getPlayerTurn()) {
//          set die label text
            dieStatusLabel.setText(String.format("%d-%d-%d", model.getDie(0), model.getDie(1), model.getDie(2)));
//          set die images
            die1Image.setImage(dieImages[model.getDie(0) - 1]);
            die2Image.setImage(dieImages[model.getDie(1) - 1]);
            die3Image.setImage(dieImages[model.getDie(2) - 1]);
        } else {
            newTurn();
            updateFeedbackLabel(String.format("Player %d turn now!", model.getPlayerTurn() + 1));
        }
    }

    @FXML
    public void onDonePressed() {
        if (!resetted) {
//            update feedback label
            int playerTurn = model.getPlayerTurn() + 1;
            updateFeedbackLabel(String.format("Player %d switched turns! Player %d now has to roll %dx or less!",
                    playerTurn, (playerTurn == 1) ? 2 : 1, model.getPlayerRollCount(playerTurn - 1)));

            switchDieSnd.play();
            model.switchPlayers();
            newTurn();
        } else {
            updateFeedbackLabel("Must first roll the die!");
        }
    }

    @FXML
    public void onKeepDie(ActionEvent actionEvent) {

        CheckBox checkBox = (CheckBox) actionEvent.getSource();
        String feedbackText;

        if (resetted) {
            feedbackText = "Must first roll the die!";
            checkBox.setSelected(false);
        } else {

            boolean selected = checkBox.isSelected();

            int index = -1;
            if (checkBox == keepDie1Chk) {
                index = 0;
            } else if (checkBox == keepDie2Chk) {
                index = 1;
            } else if (checkBox == keepDie3Chk) {
                index = 2;
            }

            if (!model.setSelectedDie(index, selected)) {
                checkBox.setSelected(false);
                feedbackText = "Can only keep two die at the most!";
            } else {
                feedbackText = String.format("Player %d decided to %s die %d!",
                        model.getPlayerTurn() + 1, (selected) ? "keep" : "not keep", index + 1);
            }
        }
        updateFeedbackLabel(feedbackText);
    }

    private int rollDie() {
//        return a random number from 1-6
        return new Random().nextInt(6) + 1;
    }

    private void newTurn() {
        dieStatusLabel.setText("Roll the die!");
        playerTurnLabel.setText(String.format("Player turn: %d", model.getPlayerTurn() + 1));
        die1Image.setImage(dieImages[6]);
        die2Image.setImage(dieImages[6]);
        die3Image.setImage(dieImages[6]);
        keepDie1Chk.setSelected(false);
        keepDie2Chk.setSelected(false);
        keepDie3Chk.setSelected(false);
        resetted = true;
    }

    public void updateFeedbackLabel(String text) {
        feedbackTextArea.setText(feedbackTextArea.getText() + text + "\n");
//        scroll to bottom
        feedbackTextArea.setScrollTop(Double.MAX_VALUE);
    }

    public static String ordinal(int i) {
//        https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

}
