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

    }

    /** Generates a blank 9x9 puzzle filled with (81) PuzzlePieces */
    private void initPuzzle() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                puzzle[row][col] = new PuzzlePiece();
            }
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
     * Checks value with the row, column, and 3x3 sub-grid to ensure value is valid
     * @param row (int): row that the value is on
     * @param col (int): column that the value is on
     * @param value (int): the value inputted
     * @return (boolean): True if value is valid, false if value is invalid
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
