package main;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;
import puzzle.Puzzle;
import puzzle.PuzzlePiece;

public class PuzzleDisplay extends Parent {
    // Class variables //
    private final int GRID_SIZE = 9;
    GridPane puzzleDisplayGrid;
    Puzzle puzzle;

    /**
     * Constructor
     *
     *
     */
    public PuzzleDisplay(String difficulty) {
        puzzle = new Puzzle(difficulty);
        puzzleDisplayGrid = new GridPane();
        createPuzzleDisplay();
    }


    /** Creates 9x9 grid pieces and adds to GridPane */
    private void createPuzzleDisplay() {
        for (int row = 0; row < GRID_SIZE; row++) {
            puzzleDisplayGrid.addRow(row);
            for (int col = 0; col < GRID_SIZE; col++) {
                puzzleDisplayGrid.addColumn(col, displayTextArea(puzzle.getPuzzlePiece(row, col)));
            }
        }
        createPuzzleLines();
    }


    /** Creates lines on grid */
    private void createPuzzleLines() {
        for(int row = 0; row < 9; ++row){
            for (int col = 0; col < 9; ++col) {
                TextArea TextAreaCell = (TextArea) getNode(row, col);
                assert TextAreaCell != null;
                TextAreaCell.setBorder(new Border(new BorderStroke(Color.rgb(52, 52, 52), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,1,1))));
                if((row + 1) % 3 == 0 && row != 8)
                    TextAreaCell.setBorder(new Border(new BorderStroke(Color.rgb(52, 52, 52), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,5,1))));
                if(col % 3 == 0 && col != 0)
                    TextAreaCell.setBorder(new Border(new BorderStroke(Color.rgb(52, 52, 52), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,1,5))));
                if((row + 1) % 3 == 0 && row != 8 && col % 3 == 0 && col != 0)
                    TextAreaCell.setBorder(new Border(new BorderStroke(Color.rgb(52, 52, 52), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1,1,5,5))));
            }
        }
    }


    /**
     *  Helper method to get a node based off of location
     * @param row (int): Location of node's row
     * @param col (col): Location of node's column
     * @return (Node): Node if location has one, otherwise null
     */
    private Node getNode(int row, int col) {
        for(Node node : puzzleDisplayGrid.getChildren()){
            if(GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row){
                return node;
            }
        }
        return null;
    }


    /**
     * Initializes a new text field with parameters and handler
     * @param puzzlePiece (PuzzlePiece): puzzle piece to get data from if conditions are met
     */
    private TextArea displayTextArea(PuzzlePiece puzzlePiece) {
        // Text area design //
        boolean isEditable;
        TextArea displayTextArea = new TextArea();
        displayTextArea.setEditable(false);
        displayTextArea.setPrefSize(100, 100);
        displayTextArea.setFont(new Font("Arial", 30));
        displayTextArea.setStyle("-fx-focus-color: rgba(241,80,37,0.2); -fx-faint-focus-color: #F15025;");

        if(puzzlePiece.getShowing()) {
            isEditable = false;
            displayTextArea.appendText("" + puzzlePiece.getValue());
            displayTextArea.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        } else {
            isEditable = true;
        }

        // Handles user value input //
        displayTextArea.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(isValueEnteredValid(keyEvent) && isEditable) {
                displayTextArea.setText(keyEvent.getText());
                checkPuzzleValidity(keyEvent);
            } else if(keyEvent.getCode().getName().equals("Backspace") && isEditable && displayTextArea.getText().length() != 0) {
                displayTextArea.deleteText(0, 1);
                checkPuzzleValidity(keyEvent);
            }
        });

        return displayTextArea;
    }


    /**
     * Finds source input and calls helper function to determine if input is valid
     * @param keyEvent: user event (input value)
     */
    private void checkPuzzleValidity(KeyEvent keyEvent) {
        TextArea source = (TextArea) keyEvent.getSource();
        if(!source.getText().equals("")) {
            int rowSelect = -1, colSelect = -1;
            boolean isFound = false;

            // Find location on grid
            for (int row = 0; row < 9 && !isFound; row++) {
                for (int col = 0; col < 9 && !isFound; col++) {
                    if (getNode(row, col) == source) {
                        rowSelect = row;
                        colSelect = col;
                        isFound = true;
                    }
                }
            }

            // Helper function to determine is the value input valid
            if (isValid(rowSelect, colSelect)) {
                source.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
            } else {
                source.setBackground(new Background(new BackgroundFill(Color.RED, null,null)));
            }
        } else {
            source.setBackground(new Background(new BackgroundFill(Color.WHITE, null,null)));
        }
    }


    /**
     * Uses source input, and compares with it's row, column, and sub grid to verify if
     * input is valid or invalid
     * @param row: Location of input row
     * @param col: Location of input column
     * @return: true if number doesn't collide with values on it row, col, and subgrid
     */
    private boolean isValid(int row, int col) {
        int subPuzzleRowStart =  (row - (row % 3));
        int subPuzzleColStart = (col - (col % 3));
        boolean isValid = true;
        TextArea source = (TextArea) getNode(row, col);
        assert source != null;
        String value = source.getText();

        // Check row
        for(int checkRow = 0; checkRow < 9; checkRow++) {
            TextArea checkDisplayPuzzlePiece = (TextArea) getNode(checkRow, col);
            assert checkDisplayPuzzlePiece != null;
            if(checkDisplayPuzzlePiece.getText().equals(value) && checkRow != row) {
                isValid = false;
                break;
            }
        }

        // Check column
        for(int checkCol = 0; checkCol < 9 && isValid; checkCol++) {
            TextArea checkDisplayPuzzlePiece = (TextArea) getNode(row, checkCol);
            assert checkDisplayPuzzlePiece != null;
            if(checkDisplayPuzzlePiece.getText().equals(value) && checkCol != col) {
                isValid = false;
                break;
            }
        }

        // Check 3x3 sub puzzle
        for(int checkRow = subPuzzleRowStart; checkRow < (subPuzzleRowStart  + 3) && isValid; checkRow++) {
            for(int checkCol = subPuzzleColStart; checkCol < (subPuzzleColStart + 3); checkCol++) {
                TextArea checkDisplayPuzzlePiece = (TextArea) getNode(checkRow, checkCol);
                assert checkDisplayPuzzlePiece != null;
                if(checkDisplayPuzzlePiece.getText().equals(value) && checkRow != row && checkCol != col){
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }


    /** Returns true if user input is valid (1-9) */
    private boolean isValueEnteredValid(KeyEvent keyEvent) {
        return keyEvent.getText().matches(".*[123456789].*");
    }

    @Override
    public Node getStyleableNode() {
        return puzzleDisplayGrid;
    }
}