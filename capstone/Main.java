/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1

Owner: Javier Felix
Javier worked on all of this

contains:

    fields:
        private static final String backgroundMusicPath

    methods:
        public void start(Stage)
 */

package capstone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;


public class Main extends Application {

    private static final String backgroundMusicPath = "asset/snd/background_music.wav";

    public Main() {
        AudioClip backgroundMusicSnd = new AudioClip(getClass().getResource(backgroundMusicPath).toString());
//        have background music loop endlessly
        backgroundMusicSnd.setCycleCount(AudioClip.INDEFINITE);
//        play music
        backgroundMusicSnd.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("4-2-1");
        Parent startMenu = FXMLLoader.load(getClass().getResource("StartMenuView.fxml"));
        primaryStage.setScene(new Scene(startMenu));
        primaryStage.show();
    }
}
