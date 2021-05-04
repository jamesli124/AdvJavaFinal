package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
    @Override
    public void start(Stage primary) {
        primary.setTitle("Unnamed Game");

        BorderPane bp = new BorderPane();

        primary.setScene(new Scene(bp, 500, 500));

        primary.show();
    }
}
