package sudoku.solver;

import sudoku.core.Sudoku;
import sudoku.core.Value;

import java.util.Arrays;

/**
 * The Solution class represents a snapshot of a solved or unsolved Sudoku board.
 * It encapsulates a 9x9 grid of values representing the current state of the Sudoku puzzle.
 */
public class Solution {

    private final Value[][] values;

    /**
     * Constructs a new Solution object representing a snapshot of a Sudoku board.
     * This constructor takes a 9x9 grid of {@code Value} objects, copies the input,
     * and stores it as the internal state of the Solution instance.
     *
     * @param boardSnapshot a two-dimensional array of {@code Value} objects representing
     *                       the initial state of the Sudoku board; must be a 9x9 grid
     */
    public Solution(Value[][] boardSnapshot) {
        this.values = new Value[9][9];
        for (int y = 0; y < 9; y++) {
            this.values[y] = Arrays.copyOf(boardSnapshot[y], 9);
        }
    }

    /**
     * Creates a new {@code Solution} instance from the given {@code Sudoku} object.
     * This method extracts the current state of the Sudoku board, represented by the
     * values of each field in the board, and uses it to initialize a {@code Solution}.
     *
     * @param sudoku the {@code Sudoku} object representing the board to derive the solution from;
     *               must contain a 9x9 grid structure with valid {@code Value} objects.
     *
     * @return a {@code Solution} instance encapsulating the state of the provided {@code Sudoku} board.
     */
    public static Solution from(Sudoku sudoku) {
        Value[][] copy = new Value[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                copy[y][x] = sudoku.getField(y, x).getValue();
            }
        }
        return new Solution(copy);
    }

    /**
     * Retrieves the value located at the specified row and column in the Sudoku board.
     *
     * @param y the row index of the value to retrieve, ranging from 0 to 8
     * @param x the column index of the value to retrieve, ranging from 0 to 8
     * @return the {@code Value} located at the specified position
     */
    public Value getValue(int y, int x) {
        return values[y][x];
    }

    /**
     * Generates a string representation of the Sudoku board in a human-readable format.
     * Each row is represented on a new line, with vertical and horizontal dividers
     * marking the 3x3 subgrids. Empty cells are represented by a dot (".").
     *
     * @return a string representation of the Sudoku board, formatted to display its 9x9 grid structure
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 9; y++) {
            if (y % 3 == 0 && y != 0) {
                sb.append("---------+---------+---------\n");
            }
            for (int x = 0; x < 9; x++) {
                if (x % 3 == 0 && x != 0) {
                    sb.append("|");
                }
                sb.append(values[y][x] == Value.EMPTY ? "." : values[y][x].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}