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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent startMenu = FXMLLoader.load(getClass().getResource("StartMenuView.fxml"));
        primaryStage.setTitle("4-2-1");
        primaryStage.setScene(new Scene(startMenu));
        primaryStage.show();
    }
}
