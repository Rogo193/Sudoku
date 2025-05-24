package sudoku.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a single field in a Sudoku grid with a specific position, value, and state.
 * A Field is associated with its Sudoku grid and keeps track of its dependents.
 */
public class Field {

    private final Position position;    
    private final Sudoku sudoku;        
    private Value value;                
    private boolean isFixed;            
    private List<Field> dependents;

    public Field(Position position, Sudoku sudoku) {
        this.position = position;
        this.sudoku = sudoku;
        this.value = Value.EMPTY;
    }

    /**
     * Retrieves the position of the current Field.
     *
     * @return the Position object indicating the coordinates (x, y) of this Field.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Retrieves the current value assigned to the Field.
     *
     * @return the Value object representing the current state of the Field.
     */
    public Value getValue() {
        return value;
    }

    /**
     * Sets the value of the current field and specifies whether the field is fixed.
     *
     * @param value the Value to be assigned to the field
     * @param fixed a boolean indicating whether the field is fixed (immutable)
     */
    public void setValue(Value value, boolean fixed) {
        this.value = value;
        this.isFixed = fixed;
    }

    /**
     * Determines whether the current Field is empty.
     *
     * @return true if the field's value is set to Value.EMPTY, false otherwise
     */
    public boolean isEmpty() {
        return value == Value.EMPTY;
    }

    /**
     * Determines if the current Field is fixed, used to fix the values on the Grid
     * e.g.; values of a given Puzzle are fixed.
     *
     * @return true if the Field is fixed, false otherwise
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * Converts the Field instance to its string representation.
     * If the field contains the value {@code Value.EMPTY}, it returns a string representation of an empty field.
     * Otherwise, it delegates to the {@code toString} method of the contained {@code Value}.
     *
     * @return a string representing the field's value:
     *         ". " for an empty field or the string representation of the {@code Value} contained in the field
     */
    @Override
    public String toString() {
        return (value == Value.EMPTY) ? " . " : value.toString();
    }

    /**
     * Retrieves a list of dependent fields for the current field in a Sudoku puzzle.
     * Dependencies are defined as all fields in the same row, column, or 3x3 block,
     * excluding the current field itself. If the dependents list has not yet been populated,
     * it will be calculated and stored.
     *
     * @return a list of dependent fields (row, column, and block neighbors) for the current field
     */
    public List<Field> getDependents() {
        if (dependents == null) {
            dependents = new ArrayList<>();

            int row = position.y();
            int col = position.x();

            // Row
            for (int x = 0; x < 9; x++) {
                if (x != col) {
                    Field f = sudoku.getField(row, x);
                    if (!dependents.contains(f)) {
                        dependents.add(f);
                    }
                }
            }

            // Col
            for (int y = 0; y < 9; y++) {
                if (y != row) {
                    Field f = sudoku.getField(y, col);
                    if (!dependents.contains(f)) {
                        dependents.add(f);
                    }
                }
            }

            // Block
            int blockStartRow = (row / 3) * 3;
            int blockStartCol = (col / 3) * 3;

            for (int dy = 0; dy < 3; dy++) {
                for (int dx = 0; dx < 3; dx++) {
                    int y = blockStartRow + dy;
                    int x = blockStartCol + dx;
                    if (y == row && x == col) continue;

                    Field f = sudoku.getField(y, x);
                    if (!dependents.contains(f)) {
                        dependents.add(f);
                    }
                }
            }
        }
        return dependents;
    }

    /**
     * Calculates and retrieves the set of possible values (domain) for the current Field.
     * The domain consists of all valid Value entries except Value.EMPTY.
     * Any values that are already used by dependent fields (same row, column, or block)
     * are excluded from the domain.
     *
     * @return a set of possible Value entries that can be assigned to the current Field
     */
    public Set<Value> getDomain() {
        if (!isEmpty()) {
            return Collections.emptySet();
        }

        Set<Value> domain = new HashSet<>();
        for (Value v : Value.values()) {
            if (v != Value.EMPTY) {
                domain.add(v);
            }
        }

        for (Field neighbor : getDependents()) {
            if (!neighbor.isEmpty()) {
                domain.remove(neighbor.getValue());
            }
        }
        return domain;
    }
    
}