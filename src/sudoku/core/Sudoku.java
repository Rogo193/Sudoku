package sudoku.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Sudoku puzzle board with methods for initialization, manipulation, and retrieval
 * of its fields and their associated values.
 * The board consists of a 9x9 grid of {@code Field} objects, each representing
 * a single cell within the Sudoku puzzle.
 */
public class Sudoku {

    private final Field[][] board;

    /**
     * Constructs a new Sudoku board instance and initializes it with a 9x9 grid of {@code Field} objects.
     * Each {@code Field} is assigned a unique {@code Position} within the board and is associated with this Sudoku instance.
     * By default, all fields on the board are initialized as empty.
     */
    public Sudoku() {
        board = new Field[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                board[y][x] = new Field(new Position(x, y), this);
            }
        }
    }

    /**
     * Retrieves the Field object located at the specified coordinates on the Sudoku board.
     *
     * @param y the row index of the desired Field (0-based)
     * @param x the column index of the desired Field (0-based)
     * @return the Field object located at the specified (y, x) coordinates
     */
    public Field getField(int y, int x) {
        return board[y][x];
    }

    /**
     * Initializes the Sudoku board with the provided 2D array of values.
     * Each value in the array represents the initial state of a field on the board.
     * Valid values (1-9) are set to their corresponding {@code Value} and marked as fixed.
     * Any other value is treated as an empty field.
     * <p>
     * Used for testing
     *
     * @param values a 9x9 integer array where each element represents the initial value
     *               of a corresponding field on the board. Valid values are 1 through 9,
     *               and other values are treated as empty.
     */
    public void initialize(int[][] values) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int val = values[y][x];
                if (val >= 1 && val <= 9) {
                    board[y][x].setValue(Value.of(val), true);
                } else {
                    board[y][x].setValue(Value.EMPTY, false);
                }
            }
        }
    }

    /**
     * Retrieves a list of all empty fields on the Sudoku board.
     * An empty field is defined as a field whose value is set to {@code Value.EMPTY}.
     *
     * @return a list of {@code Field} objects representing all the empty fields on the board
     */
    public List<Field> getEmptyFields() {
        List<Field> emptyFields = new ArrayList<>();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (board[y][x].isEmpty()) {
                    emptyFields.add(board[y][x]);
                }
            }
        }
        return emptyFields;
    }

    /**
     * Provides the string representation of the Sudoku board.
     * Each row of the board is represented on a new line, and rows are separated into blocks with separator lines.
     * '|' Characters separate columns within each block.
     * Each field on the board is displayed as returned by its {@code toString()} method, where empty fields
     * are represented by ". ".
     *
     * @return a string representation of the current state of the Sudoku board, formatted in a human-readable grid layout
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
                sb.append(board[y][x].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves the value assigned to the field located at the specified coordinates on the Sudoku board.
     *
     * @param y the row index of the desired field (0-based)
     * @param x the column index of the desired field (0-based)
     * @return the Value object representing the current state of the field at the specified coordinates
     */
    public Value getValue(int y, int x) {
        return board[y][x].getValue();
    }

    /**
     * Updates the value of a specific field on the Sudoku board and specifies whether the field is fixed.
     *
     * @param y      the row index of the field to update (0-based)
     * @param x      the column index of the field to update (0-based)
     * @param value  the {@code Value} to assign to the specified field
     * @param fixed  a boolean indicating whether the field should be marked as fixed
     */
    public void setValue(int y, int x, Value value, boolean fixed) {
        board[y][x].setValue(value, fixed);
    }


    //----------------------------------- SudokuGenerator ------------------------------------

    /**
     * Creates a deep copy of the current Sudoku board.
     * The method generates a new instance of the Sudoku board, replicating all fields with their
     * respective values and fixed statuses from the original board. All fields are copied independently
     * to ensure modifications to the new board do not affect the original and vice versa.
     *
     * @return a new {@code Sudoku} object representing an identical and independent copy of the original board
     */
    public Sudoku deepCopy() {
        Sudoku copy = new Sudoku();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Field original = this.getField(y, x);
                Value value = original.getValue();
                boolean fixed = original.isFixed();
                copy.getField(y, x).setValue(value, fixed);
            }
        }
        return copy;
    }

    /**
     * Retrieves all fields on the Sudoku board in a row-major order.
     * The board is assumed to be a 9x9 grid, and the method iterates
     * through all rows and columns to collect every field.
     *
     * @return a list of {@code Field} objects representing all fields on the Sudoku board.
     */
    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fields.add(getField(y, x));
            }
        }
        return fields;
    }
}