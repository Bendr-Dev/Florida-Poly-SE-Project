package puzzle;

import java.util.Random;

public class Puzzle {
    // Class variables //
    private final int GRID_SIZE = 9;
    private PuzzlePiece[][] puzzle = new PuzzlePiece[9][9];


    /**
     * Constructor
     *
     * Args:
     *  difficulty (String): Amount of hints to be generated, determined by user
     */
    public Puzzle(String difficulty) {
        // Initialze 2D array and insert values //
        initPuzzle();
        insertRandomValues();

        // Check solubility of puzzle and insert new values until solvable //
        while(!isSolvable()) {
            initPuzzle();
            insertRandomValues();
        }
        selectHintValues(difficulty);
    }

    /** Generates a blank 9x9 puzzle filled with (81) PuzzlePieces */
    private void initPuzzle() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                puzzle[row][col] = new PuzzlePiece();
            }
        }
    }

    /**
     * Selects random points on array to show based off of user-selected difficulty
     * @param difficulty (String): 'EASY', 'MEDIUM', 'HARD'
     */
    private void selectHintValues(String difficulty) {
        Random random = new Random();
        int hintValueAmount = 0;

        // Decides how many hints to give (randomized lee-way)
        switch (difficulty) {
            case "EASY":
                hintValueAmount = random.nextInt(5) + 28;
                break;
            case "MEDIUM":
                hintValueAmount = random.nextInt(5) + 23;
                break;
            case "HARD":
                hintValueAmount = random.nextInt(5) + 17;
        }

        // Select hints randomly on grid after determining amount of hint //
        for(int hints = 0; hints < hintValueAmount; hints++) {
            int randomRow = random.nextInt(9);
            int randomCol = random.nextInt(9);

            // Check validity of showing until valid //
            while(puzzle[randomRow][randomCol].getShowing()) {
                randomRow = random.nextInt(9);
                randomCol = random.nextInt(9);
            }
            puzzle[randomRow][randomCol].setShowing(true);
        }
    }

    /** Inserts 17 random values (1 through 9) into the puzzle at random locations on the grid */
    private void insertRandomValues() {
        Random randomValue = new Random();

        // Minimum amount of values to get 1 unique solution = 17 //
        for (int puzzlePiece = 0; puzzlePiece < 17; puzzlePiece++) {
            int nextValue = randomValue.nextInt(9) + 1;
            int nextRow = randomValue.nextInt(9);
            int nextCol = randomValue.nextInt(9);

            // Check validity of value and location until valid //
            while(!isValueValid(nextRow, nextCol, nextValue)) {
                nextValue = randomValue.nextInt(9) + 1;
                nextRow = randomValue.nextInt(9);
                nextCol = randomValue.nextInt(9);
            }
            puzzle[nextRow][nextCol].setValue(nextValue);
        }
    }

    /**
     * Locates a blank PuzzlePiece (value of 0) and assigns a value starting from 1 to it.
     * Checks if that value is valid, then recursively calls the method again and
     * keeps inserting random values until puzzle is solved. If no values can be possible on
     * a puzzle piece, method goes back and changes previous value
     * @return (boolean): true if puzzle is solvable, false otherwise
     */
    private boolean isSolvable() {
        boolean isEmpty = true;
        int row = -1;
        int col = -1;

        // Traverse through array to find blank PuzzlePiece //
        for(int findRow = 0; findRow < GRID_SIZE; findRow++) {
            for(int findCol = 0; findCol < GRID_SIZE; findCol++) {
                if(puzzle[findRow][findCol].getValue() == 0){
                    row = findRow;
                    col = findCol;
                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty)
                break;
        }
        if(isEmpty)
            return true; // No more blank pieces, puzzle is solved //

        // Assigns a value to the PuzzlePiece,  //
        // and checks if it's valid by making the recursive call (goes to next value) //
        for(int value = 1; value <= 9; value++) {
            if(isValueValid(row, col, value)) {
                puzzle[row][col].setValue(value);
                if(isSolvable()) {
                    return true;
                } else {
                    puzzle[row][col].setValue(0);
                }
            }
        }
        return false;
    }

    /**
     * Checks value with the row, column, and 3x3 sub-grid to ensure value is valid
     * @param row (int): row that the value is on
     * @param col (int): column that the value is on
     * @param value (int): the value inputted
     * @return (boolean): true if value is valid, false if value is invalid
     */
    private boolean isValueValid(int row, int col, int value) {
        int subPuzzleRowStart = row - (row % 3);
        int subPuzzleColStart = col - (col % 3);

        // Check row //
        for (int checkRow = 0; checkRow < GRID_SIZE; checkRow++) {
            if(puzzle[checkRow][col].getValue() == value && checkRow != row)
                return false;
        }

        // Check column //
        for (int checkCol = 0; checkCol < GRID_SIZE; checkCol++) {
            if(puzzle[row][checkCol].getValue() == value && checkCol != col)
                return false;
        }

        // Check 3x3 sub-grid //
        for(int checkRow = subPuzzleRowStart; checkRow < (subPuzzleRowStart  + 3); checkRow++) {
            for(int checkCol = subPuzzleColStart; checkCol < (subPuzzleColStart + 3); checkCol++) {
                if(puzzle[checkRow][checkCol].getValue() == value && checkRow != row && checkCol != col)
                    return false;
            }
        }
        return true; // True if no value conflicted with the value being checked //
    }
}
