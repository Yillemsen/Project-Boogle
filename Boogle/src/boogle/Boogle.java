package boogle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Boogle extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane mainPane = new Pane();
        BoogleMenuBar menuBar = new BoogleMenuBar(mainPane);

        VBox root = new VBox(menuBar, mainPane);
        Scene scene = new Scene(root, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Beroepsproject");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}