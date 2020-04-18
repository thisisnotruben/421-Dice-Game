/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent startMenu = FXMLLoader.load(getClass().getResource("StartMenuView.fxml"));
        primaryStage.setTitle("4-2-1");
        primaryStage.setScene(new Scene(startMenu));
        primaryStage.show();
    }
}
