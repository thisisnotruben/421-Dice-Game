/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1

Owner: Ruben Alvarez Reyes
Ruben worked on all of this

contains:
    
    fields:
        private final static String imgPath
        private final static Image[] dieImages
        private final static String sndPath
        private final static String[] sndPaths
        private final AudioClip rollDieSnd
        private final AudioClip switchDieSnd
        private final AudioClip gameOverSnd
        private final AudioClip winSnd
        private final AudioClip diceKeepSnd
        private final MainScreenModel model
        private boolean resetted;
        private GridPane root
        private Label dieStatusLabel
        private Label player1ScoreLabel
        private Label player1RollLabel
        private Label player2ScoreLabel
        private Label player2RollLabel
        private ImageView die1Image
        private ImageView die2Image
        private ImageView die3Image
        private Button rollButton
        private Button doneButton
        private Button die1Button
        private Button die2Button
        private Button die3Button
        private TextArea feedbackTextArea
        private Label playerTurnLabel
        private Label currentRoundLabel
        
    methods:
        public void initialize()
        public void onRollPressed()
        public void onDonePressed()
        public void onKeepDie(ActionEvent)
        public private int rollDie()
        public private void newTurn()
        public void updateFeedbackLabel(String)
        public static String ordinal(int)
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
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
    private final static String sndPath = "asset/snd/%s.wav";
    private final static String[] sndPaths = {
            String.format(sndPath, "roll_die"),
            String.format(sndPath, "switch_die"),
            String.format(sndPath, "game_over"),
            String.format(sndPath, "win"),
            String.format(sndPath, "die_keep")
    };

    private final AudioClip rollDieSnd;
    private final AudioClip switchDieSnd;
    private final AudioClip gameOverSnd;
    private final AudioClip winSnd;
    private final AudioClip diceKeepSnd;
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
    private Button die1Button;
    @FXML
    private Button die2Button;
    @FXML
    private Button die3Button;
    @FXML
    private TextArea feedbackTextArea;
    @FXML
    private Label playerTurnLabel;
    @FXML
    private Label currentRoundLabel;

    public MainScreenController() {
        model = new MainScreenModel();
        rollDieSnd = new AudioClip(getClass().getResource(sndPaths[0]).toString());
        switchDieSnd = new AudioClip(getClass().getResource(sndPaths[1]).toString());
        gameOverSnd = new AudioClip(getClass().getResource(sndPaths[2]).toString());
        winSnd = new AudioClip(getClass().getResource(sndPaths[3]).toString());
        diceKeepSnd = new AudioClip(getClass().getResource(sndPaths[4]).toString());
    }

    @FXML
    public void initialize() {
        newTurn();
//        set colors
        root.setBackground(new Background(new BackgroundFill(Color.rgb(20, 158, 66), CornerRadii.EMPTY, Insets.EMPTY)));
        rollButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 193, 29), CornerRadii.EMPTY, Insets.EMPTY)));
        doneButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 29, 62), CornerRadii.EMPTY, Insets.EMPTY)));
        for (Button button : new Button[]{die1Button, die2Button, die3Button}) {
            button.setBackground(new Background(new BackgroundFill(Color.rgb(29, 161, 226), CornerRadii.EMPTY, Insets.EMPTY)));
        }
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

//        update score labels
        player1ScoreLabel.setText(String.format("Points: %d", model.getPlayerScore(0)));
        player2ScoreLabel.setText(String.format("Points: %d", model.getPlayerScore(1)));

//        update feedback label
        String feedbackText = String.format("Player %d %s roll rolled: %d-%d-%d!",
                playerTurn + 1, ordinal(playerRollCount),
                model.getDie(playerTurn, 0), model.getDie(playerTurn, 1), model.getDie(playerTurn, 2));
        updateFeedbackLabel(feedbackText);

//        update main view
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
//        game over
        if (model.getCurrentRound() - 1 == MainScreenModel.MAX_ROUNDS) {
            winSnd.play();
            gameOverSnd.play();
//            user cannot interact anymore
            for (ButtonBase buttonBase : new ButtonBase[]{rollButton, doneButton, die1Button, die2Button, die3Button}) {
                buttonBase.setDisable(true);
            }
//            update feedback label and header
            String winText = String.format("Player %d won!!!", model.getWinner() + 1);
            dieStatusLabel.setText(winText);
            updateFeedbackLabel(winText);
            playerTurnLabel.setText("");
        } else {
//            update current round label
            currentRoundLabel.setText(String.format("Current round: %d", model.getCurrentRound()));
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
            updateFeedbackLabel("You must first roll the die!");
        }
    }

    @FXML
    public void onKeepDie(ActionEvent actionEvent) {

        String feedbackText;

        if (resetted) {
            feedbackText = "You must first roll the die!";
        } else {

            diceKeepSnd.play();
            Button button = (Button) actionEvent.getSource();

//            effect to give visual feedback on which die was selected
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(0.67);
            ImageView dieImage;

            int index = -1;
            if (button == die1Button) {
                dieImage = die1Image;
                index = 0;
            } else if (button == die2Button) {
                dieImage = die2Image;
                index = 1;
            } else {
                dieImage = die3Image;
                index = 2;
            }

            boolean selected = dieImage.getEffect() == null;

//            set visual effect on die
            dieImage.setEffect((selected) ? colorAdjust : null);

            model.setSelectedDie(index, selected);

            feedbackText = String.format("Player %d decided to %s die %d!",
                    model.getPlayerTurn() + 1, (selected) ? "keep" : "not keep", index + 1);
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
        die1Image.setEffect(null);
        die2Image.setImage(dieImages[6]);
        die2Image.setEffect(null);
        die3Image.setImage(dieImages[6]);
        die3Image.setEffect(null);
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
