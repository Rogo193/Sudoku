package sudoku.ui;

import sudoku.core.Field;
import sudoku.core.Position;
import sudoku.core.Sudoku;
import sudoku.core.Value;
import sudoku.generator.SudokuGenerator;
import sudoku.solver.Solution;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

/**
 * The SudokuGui class represents the graphical user interface for interacting
 * with and solving Sudoku puzzles. It provides a grid-based layout for
 * displaying Sudoku puzzles, along with controls for checking, solving,
 * generating new puzzles, and highlighting invalid entries.
 */
public class SudokuGui {
    private final JFrame frame;
    private final JTextField[][] tf;

    /**
     * Constructs a graphical user interface (GUI) for a Sudoku game.
     * <p>
     * This constructor initializes the primary window (`JFrame`) and its components,
     * including a 9x9 grid of custom `DigitTextField` objects for user input, a set of
     * buttons for performing various Sudoku-related actions, and event listeners to
     * handle user interactions.
     * </p>
     * <p>
     * Key parts and functionalities include:
     * - A 9x9 grid of `DigitTextField` objects, embedded within a `JPanel` set to a
     *   `GridLayout`. The text fields are styled and configured to allow only single-digit
     *   numeric input and to provide visual differentiation between 3x3 blocks by alternating
     *   background colors.
     * - A `NavKeyListener` added to each text field, enabling keyboard navigation using arrow keys.
     * - A `JPanel` containing buttons for Sudoku actions like checking the solution, solving the puzzle,
     *   starting a new puzzle, and closing the application. Buttons are assigned action commands and
     *   listeners to handle user interaction.
     * - The GUI adopts a BorderLayout to manage component placement, with the Sudoku grid in the center
     *   and the action buttons at the bottom.
     * </p>
     * Upon calling this constructor, the GUI is set up but not displayed. The `run` method must be called
     * separately to make the GUI visible to the user.
     */
    public SudokuGui() {
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        JPanel spielfeldPanel = new JPanel();
        spielfeldPanel.setLayout(new GridLayout(9, 9));
        tf = new JTextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tf[i][j] = new DigitTextField();
                tf[i][j].setBorder(new LineBorder(Color.GRAY));
                if (((i / 3) + (j / 3)) % 2 == 0){
                    //3x3 Graufärben
                    tf[i][j].setBackground(new Color(240, 240, 240)); // heller Block
                }

                tf[i][j].addKeyListener(new NavKeyListener(tf, i, j));
                tf[i][j].setForeground(Color.BLACK);
                
                spielfeldPanel.add(tf[i][j]);
            }
        }
        frame.add(spielfeldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        ButtonListener listener = new ButtonListener(this);

        String[] labels = {"Check", "Solve", "New Puzzle", "Close"};

        for (String label : labels) {
            JButton btn = new JButton(label);
            btn.setActionCommand(label);
            btn.addActionListener(listener);
            buttonPanel.add(btn);
        }
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Retrieves the main application window (JFrame) associated with the Sudoku GUI.
     *
     * @return the JFrame instance representing the primary graphical user interface of the Sudoku application
     */
    public JFrame getFrame(){
        return frame;
    }

    /**
     * Displays the main Sudoku GUI window.
     * <p>
     * This method makes the primary JFrame associated with the Sudoku GUI visible
     * to the user. It is intended to be called after the GUI has been constructed
     * and set up, using the `SudokuGui` constructor.
     * </p>
     * This method is typically the final step in initializing and launching the
     * graphical interface of the Sudoku application.
     */
    public void run() {
        frame.setVisible(true);
    }

    /**
     * Loads a Sudoku puzzle into the graphical user interface (GUI) by setting up the grid
     * of text fields based on the provided puzzle's initial state.
     * The method handles styling, resetting, and populating the fields appropriately.
     *
     * @param sudoku the Sudoku puzzle to be loaded, which contains the initial setup of fields,
     *               their values, and fixed status (if any).
     */
    public void loadPuzzle(Sudoku sudoku) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Field field = sudoku.getField(y, x);
                JTextField cell = tf[y][x];

                // Set background color based on 3x3 block pattern
                boolean isLightBlock = ((x / 3) + (y / 3)) % 2 == 0;
                Color backgroundColor = isLightBlock ? new Color(240, 240, 240) : Color.WHITE;
                cell.setBackground(backgroundColor);

                // Reset field state
                cell.setText("");
                cell.setEditable(true);
                cell.setForeground(Color.BLACK);
                cell.setBorder(new LineBorder(Color.GRAY));

                // Apply field value if available
                if (!field.isEmpty()) {
                    cell.setText(String.valueOf(field.getValue().getDigit()));

                    if (field.isFixed()) {
                        cell.setEditable(false);           // Lock the field
                        cell.setForeground(Color.BLUE);    // Predefined values in blue
                    }
                }
            }
        }
    }

    /**
     * Updates the graphical Sudoku grid to display the solution provided by the given {@code Solution} object.
     * This method iterates through the 9x9 grid and sets the displayed values in the corresponding
     * GUI text fields based on the solution. Editable fields are highlighted to indicate the change.
     *
     * @param solution the {@code Solution} object containing the solved Sudoku board values;
     *                 expected to provide a 9x9 grid of values representing the solution
     */
    public void displaySolution(Solution solution) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Value v = solution.getValue(y, x);
                JTextField field = tf[y][x];

                // Set the displayed value from the solution
                field.setText(String.valueOf(v.getDigit()));

                // Highlight only the newly solved (editable) fields
                if (field.isEditable()) {
                    field.setForeground(Color.MAGENTA); // User-solved values
                }
            }
        }
    }

    /**
     * Reads the current state of the Sudoku puzzle from the GUI text fields and converts it into
     * a Sudoku object. This involves iterating over a 9x9 grid of text fields, retrieving the user-entered
     * values, and constructing a corresponding Sudoku object. Empty fields are recorded as empty values
     * in the Sudoku object.
     *
     * @return a Sudoku object representing the current state of the puzzle entered the GUI
     */
    public Sudoku readCurrentSudoku() {
        Sudoku sudoku = new Sudoku();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                JTextField cell = tf[y][x];
                if (cell.getText().isEmpty()) {
                    sudoku.setValue(y, x, Value.EMPTY, false);
                } else {
                    sudoku.setValue(y, x, Value.of(Integer.parseInt(cell.getText())), false);
                }
            }
        }
        return sudoku;
    }

    /**
     * Marks the specified position in the Sudoku grid as invalid by changing its visual appearance.
     * This method updates the corresponding text field's foreground color and border
     * to indicate an invalid entry.
     *
     * @param pos the position in the grid (x, y coordinates) to be marked as invalid
     */
    public void markInvalid(Position pos) {
        JTextField field = tf[pos.y()][pos.x()];
        field.setForeground(new Color(255, 88, 88)); // rötlich markieren
        field.setBorder(new LineBorder(new Color(255, 88, 88)));
    }

    /**
     * Clears any visual highlights or custom styles applied to the Sudoku grid's text fields.
     * <p>
     * This method iterates over the 9x9 grid of text fields and resets their visual properties:
     * - Editable fields have their text color set to black.
     * - Non-editable fields have their text color set to blue.
     * - All fields have their borders reset to a standard gray border.
     * </p>
     * This is typically used to remove visual cues such as errors, validations, or other highlights,
     * restoring the grid to its default appearance.
     */
    public void clearHighlighting() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                JTextField field = tf[y][x];
                if (field.isEditable()) {
                    field.setForeground(Color.BLACK);
                }
                else {
                    field.setForeground(Color.BLUE);
                }
                field.setBorder(new LineBorder(Color.GRAY));
            }
        }
    }
}