package sudoku.generator;

/**
 * Represents the difficulty levels of a Sudoku puzzle.
 * <p>
 * Each level is associated with a descriptive label to represent the difficulty
 * in a human-readable format.
 * <p>
 * The difficulty levels can be used as parameters, for example, to control the
 * Sudoku generation process, where each level corresponds to a specific number
 * of given fields in the puzzle.
 */
public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String label;

    /**
     * Constructs a Difficulty instance with the specified label.
     *
     * @param label the label representing the difficulty level in a human-readable format
     */
    Difficulty(String label) {
        this.label = label;
    }

    /**
     * Returns the string representation of the object, which is the label
     * associated with the difficulty level.
     *
     * @return the label representing the difficulty level as a human-readable string
     */
    @Override
    public String toString() {
        return label;
    }
}