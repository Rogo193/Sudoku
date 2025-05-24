package sudoku.ui;

import sudoku.core.Field;
import sudoku.core.Position;
import sudoku.core.Sudoku;
import sudoku.core.Value;
import sudoku.generator.Difficulty;
import sudoku.generator.SudokuGenerator;
import sudoku.solver.Solution;
import sudoku.solver.Solver;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * An implementation of the ActionListener interface designed to handle button
 * actions in a Sudoku graphical user interface (GUI). This listener processes
 * actions associated with validating, solving, generating, and exiting the Sudoku game.
 */
public class ButtonListener implements ActionListener {

    private final SudokuGui gui;

    /**
     * Creates a ButtonListener instance associated with the specified SudokuGui.
     *
     * @param gui the SudokuGui instance to associate with this ButtonListener
     */
    public ButtonListener(SudokuGui gui) {
        this.gui = gui;
    }

    /**
     * Responds to button click events in the GUI by dispatching logic
     * depending on the associated action command.
     * <p>
     * Supported commands:
     * - "Check": Validates current puzzle entries for conflicts
     * - "Solve": Attempts to solve the current puzzle
     * - "New" or "New Puzzle": Lets the user select a difficulty and loads a new puzzle
     * - "Close": Closes the application
     *
     * @param e the action event triggered by the GUI
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {

            //----------------------------------- "Check" Button ------------------------------------
            case "Check":
                gui.clearHighlighting();    // Clears previous error highlights
                
                Sudoku checkPuzzle = gui.readCurrentSudoku();
                List<Field> allFields = checkPuzzle.getAllFields();
                boolean valid = true;

                // Check for duplicate values in the field's dependents
                for (Field f : allFields) {
                    if (f.isEmpty()) continue;

                    Value v = f.getValue();
                    Position pos = f.getPosition();

                    for (Field dep : f.getDependents()) {
                        if (v == dep.getValue()) {
                            valid = false;
                            gui.markInvalid(pos);
                            gui.markInvalid(dep.getPosition());
                        }
                    }
                }

                // Show a result to the user
                if (valid) {
                    JOptionPane.showMessageDialog(gui.getFrame(), "Eingabe ist gültig!");
                } else {
                    JOptionPane.showMessageDialog(gui.getFrame(), "Fehlerhafte Eingabe vorhanden!");
                }
                break;

            //----------------------------------- "Solve" Button ------------------------------------
            case "Solve":
                Sudoku current = gui.readCurrentSudoku();
                Solver solver = new Solver();
                List<Solution> results = solver.findSolutions(current, 10); // Nur 1 Lösung

                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(gui.getFrame(), "Keine Lösung gefunden!");
                } else {
                    gui.displaySolution(results.getFirst()); // Neue Methode in GUI
                }
                break;

            //----------------------------------- "New Puzzle" Button ------------------------------------
            case "New Puzzle":  // Intentionally fall through
            case "New":
                Object[] options = {"Easy", "Medium", "Hard"};
                int choice = JOptionPane.showOptionDialog(
                        gui.getFrame(),
                        "Please select a difficulty level for the new Sudoku puzzle: ",
                        "New Sudoku",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]
                );

                // Only continue if not canceled
                if (choice >= 0) {
                    Difficulty selected;
                    switch (choice) {
                        case 0 -> selected = Difficulty.EASY;
                        case 1 -> selected = Difficulty.MEDIUM;
                        case 2 -> selected = Difficulty.HARD;
                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                    SudokuGenerator gen = new SudokuGenerator();
                    Sudoku newPuzzle = gen.generate(selected);
                    gui.loadPuzzle(newPuzzle);
                }
                break;

            //----------------------------------- "Close" Button ------------------------------------
            case "Close":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}