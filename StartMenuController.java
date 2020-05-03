/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenuController {

    @FXML
    private GridPane root;
    @FXML
    private Button startGameButton;

    private static final String startGameSndPath = "asset/snd/start_game.wav";
    private final AudioClip startGameSnd;

    @FXML
    public void initialize() {
//        set colors
        root.setBackground(new Background(new BackgroundFill(Color.rgb(20, 158, 66), CornerRadii.EMPTY, Insets.EMPTY)));
        startGameButton.setBackground(new Background(new BackgroundFill(Color.rgb(226, 193, 29), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public StartMenuController() {
        startGameSnd = new AudioClip(getClass().getResource(startGameSndPath).toString());
    }

    @FXML
    public void onStartGamePressed(ActionEvent actionEvent) throws IOException {
//        play sound
        startGameSnd.play();
//        switch scene
        Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScreenView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(mainScreen));
    }
}
