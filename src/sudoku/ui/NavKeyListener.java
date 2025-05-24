package sudoku.ui;


import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A key listener that handles navigation between a grid of JTextField components
 * by responding to arrow key inputs. The listener supports moving focus between
 * editable fields while skipping non-editable fields, highlighting the focused field,
 * and maintaining a consistent grid behavior.
 */
public class NavKeyListener extends KeyAdapter {

    private final JTextField[][] fields;
    private final int row;
    private final int col;

    /**
     * Constructs a NavKeyListener for managing focus navigation within a grid of JTextFields.
     * Enables arrow key navigation across editable fields while skipping non-editable fields.
     *
     * @param fields a 2D array of JTextFields representing a grid of input fields
     * @param row the initial row index of the field where the listener is attached
     * @param col the initial column index of the field where the listener is attached
     */
    public NavKeyListener(JTextField[][] fields, int row, int col) {
        this.fields = fields;
        this.row = row;
        this.col = col;
    }

    /**
     * Handles the key press events for arrow navigation within a grid of {@code JTextField}.
     * Updates the focus to the next editable field based on the arrow key pressed, skipping non-editable fields.
     * Invokes a method to visually highlight the newly focused field.
     *
     * @param e the {@code KeyEvent} that triggered the method, representing the key press action
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int newRow = row;
        int newCol = col;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> newRow = (row + 8) % 9;
            case KeyEvent.VK_DOWN -> newRow = (row + 1) % 9;
            case KeyEvent.VK_LEFT -> newCol = (col + 8) % 9;
            case KeyEvent.VK_RIGHT -> newCol = (col + 1) % 9;
            default -> {
                return;
            }
        }

        while (!fields[newRow][newCol].isEditable()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> newRow = (newRow + 8) % 9;
                case KeyEvent.VK_DOWN -> newRow = (newRow + 1) % 9;
                case KeyEvent.VK_LEFT -> newCol = (newCol + 8) % 9;
                case KeyEvent.VK_RIGHT -> newCol = (newCol + 1) % 9;
            }
            if (newRow == row && newCol == col) return;
        }
        // set Focus
        JTextField nextField = fields[newRow][newCol];
        nextField.requestFocus();
        // Highlight
        highlightFocusedField(nextField);
    }

    /**
     * Finds the row index of the specified JTextField within a 2D grid of JTextFields.
     *
     * @param f the JTextField whose row index is to be determined
     * @return the row index of the specified JTextField, or -1 if the JTextField is not found
     */
    private int findRow(JTextField f) {
        for (int y = 0; y < 9; y++)
            for (int x = 0; x < 9; x++)
                if (fields[y][x] == f) return y;
        return -1;
    }

    /**
     * Finds the column index of the specified JTextField within a 2D grid of JTextFields.
     *
     * @param f the JTextField whose column index is to be determined
     * @return the column index of the specified JTextField, or -1 if the JTextField is not found
     */
    private int findCol(JTextField f) {
        for (int y = 0; y < 9; y++)
            for (int x = 0; x < 9; x++)
                if (fields[y][x] == f) return x;
        return -1;
    }

    /**
     * Highlights the currently focused {@code JTextField} in a grid of text fields by changing
     * its background color to light yellow. Resets the background colors of all other editable
     * fields in the grid to their default based on their position.
     *
     * @param focused the {@code JTextField} that is currently focused and should be highlighted
     */
    private void highlightFocusedField(JTextField focused) {
        for (JTextField[] rowFields : fields) {
            for (JTextField field : rowFields) {
                // Reset only the background if it was changed
                if (field.isEditable()) {
                    // Reset background
                    int y = findRow(field);
                    int x = findCol(field);
                    if (((x / 3) + (y / 3)) % 2 == 0) {
                        field.setBackground(new Color(240, 240, 240));
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
            }
        }

        // Highlight focused field with a light yellow background
        focused.setBackground(new Color(255, 255, 200));
    }
}