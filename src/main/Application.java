package main;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    // Class variables //
    String difficulty = "", currentDifficulty = "";
    PuzzleDisplay puzzleDisplay;

    // Start of Application //
    @Override
    public void start(Stage stage) {
        BorderPane appPane = new BorderPane();
        appPane.setBackground(new Background(new BackgroundFill(Color.rgb(51,51,51), null, null)));
        double screenWidth = Screen.getPrimary().getBounds().getWidth() * 0.6;
        double screenHeight = Screen.getPrimary().getBounds().getHeight() * 0.8;

        // Create side panel //
        VBox userPanel = new VBox();
        userPanel.setPrefSize(screenWidth * 0.2, screenHeight);
        Label title = new Label("Sudoku!");
        title.setStyle("-fx-font: 24px 'Arial'; -fx-text-fill: #FFFFFF; -fx-padding: 2 2 2 2;");
        Label difficultyLabel = new Label("Selected difficulty: " + difficulty);
        difficultyLabel.setStyle("-fx-font: 14px 'Arial'; -fx-text-fill: #FFFFFF; -fx-padding: 2 2 2 2;");
        Label currentDifficultyLabel = new Label("");
        currentDifficultyLabel.setStyle("-fx-font: 14px 'Arial'; -fx-text-fill: #FFFFFF; -fx-padding: 2 2 2 2;");


        // Create radio buttons and toggle group //
        GridPane radioButtonGridPane = new GridPane();
        radioButtonGridPane.addColumn(0);

        ToggleGroup difficultyGroup = new ToggleGroup();

        RadioButton easyRB = new RadioButton("EASY");
        easyRB.setStyle("-fx-font: 20px 'Arial'; -fx-text-fill: #FFFFFF;");
        RadioButton mediumRB = new RadioButton("MEDIUM");
        mediumRB.setStyle("-fx-font: 20px 'Arial'; -fx-text-fill: #FFFFFF;");
        RadioButton hardRB = new RadioButton("HARD");
        hardRB.setStyle("-fx-font: 20px 'Arial'; -fx-text-fill: #FFFFFF;");

        radioButtonGridPane.addRow(0, title);
        radioButtonGridPane.addRow(1, easyRB);
        radioButtonGridPane.addRow(2, mediumRB);
        radioButtonGridPane.addRow(3, hardRB);
        radioButtonGridPane.setVgap(30);
        radioButtonGridPane.setAlignment(Pos.CENTER);

        // Create in toggle group //
        easyRB.setToggleGroup(difficultyGroup);
        mediumRB.setToggleGroup(difficultyGroup);
        hardRB.setToggleGroup(difficultyGroup);

        // Create button to start game with selected difficulty //
        Button startGameButton = new Button("New Game");
        startGameButton.setStyle("-fx-base:#FFFFFF; -fx-font: 20px 'Arial';");
        startGameButton.setDisable(true);

        userPanel.getChildren().add(radioButtonGridPane);
        userPanel.getChildren().add(difficultyLabel);
        userPanel.setSpacing(30);
        userPanel.getChildren().add(startGameButton);
        userPanel.setAlignment(Pos.TOP_CENTER);

        // Handle difficulty selection //
        difficultyGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            try {
                RadioButton selectedRB = (RadioButton) difficultyGroup.getSelectedToggle();
                difficulty = selectedRB.getText();
                difficultyLabel.setText("Selected difficulty: " + difficulty);
                startGameButton.setDisable(false);
            } catch(NullPointerException ignored) { }
        });

        // Handle creating a new puzzle //
        startGameButton.setOnAction((actionEvent) -> {
            try {
                difficultyGroup.getSelectedToggle().setSelected(false);
                startGameButton.setDisable(true);

                puzzleDisplay = new PuzzleDisplay(difficulty);
                currentDifficulty = difficulty;
                currentDifficultyLabel.setText("Current Difficulty: " + currentDifficulty);
                appPane.setCenter((Parent) puzzleDisplay.getStyleableNode());
            } catch(NullPointerException ignored) { }
        });

        userPanel.getChildren().add(currentDifficultyLabel);
        appPane.setRight(userPanel);

        Scene scene = new Scene(appPane, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }
}