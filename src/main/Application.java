package main;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import puzzle.Puzzle;

public class Application extends javafx.application.Application {
    // Class variables //
    String difficulty = "";

    // Start of Application //
    @Override
    public void start(Stage stage) {
        double screenWidth = Screen.getPrimary().getBounds().getWidth() * 0.6;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() * 0.8;

        PuzzleDisplay puzzleDisplay = new PuzzleDisplay("EASY");
        stage.setScene(new Scene((Parent) puzzleDisplay.getStyleableNode(), screenWidth, screenHeight));
        stage.show();
    }
}