package sudoku.solver;

import sudoku.core.Field;
import sudoku.core.Sudoku;
import sudoku.core.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Solver class is responsible for solving Sudoku puzzles, generating Sudoku puzzles,
 * and finding solutions based on different criteria. It uses backtracking to explore
 * possible configurations of the Sudoku grid.
 */
public class Solver {

    public Solver() {
    }

    //----------------------------------- Find 1 Solution ------------------------------------

    /**
     * Finds a solution for the given Sudoku puzzle.
     *
     * @param sudoku the Sudoku puzzle represented as a 9x9 grid. The input object is expected
     *               to have its fields initialized, with empty fields indicating unfilled cells
     *               to be solved. This method modifies the input Sudoku directly to contain
     *               a valid solution.
     */
    public void findSolution(Sudoku sudoku) {
        List<Field> empty = sudoku.getEmptyFields();
        backtrack(empty, 0);
    }

    /**
     * Recursive backtracking algorithm for solving constraints on a list of fields in a Sudoku puzzle.
     * It tries to assign valid values to fields in the given order, backtracking when conflicts arise.
     *
     * @param fields the list of fields that represent the Sudoku grid. Each field must have its dependencies
     *               and domain correctly defined.
     * @param index  the zero-based index indicating the current field in the list to be processed.
     * @return true if the algorithm successfully assigns valid values to all fields, false otherwise.
     */
    private boolean backtrack(List<Field> fields, int index) {
        if (index >= fields.size()) return true;

        Field current = fields.get(index);
        for (Value v : current.getDomain()) {
            current.setValue(v, false);
            if (backtrack(fields, index + 1)) return true;
            current.setValue(Value.EMPTY, false);
        }

        return false;
    }

    //----------------------------------- Find all Solutions ------------------------------------

    /**
     * Finds all possible solutions for the given Sudoku puzzle using backtracking.
     * This method generates a list of all valid solutions by exploring all possible
     * configurations of the puzzle. Each solution is represented as a {@code Solution}
     * object, which captures the state of the Sudoku board at the time of completion.
     * <p>
     * Warning: A very long process! Use findSolutions(Sudoku sudoku, int amount)
     *
     * @param sudoku the Sudoku puzzle to solve, containing a 9x9 grid of fields. The
     *               input object is expected to have unfilled cells represented as
     *               empty fields.
     * @return a list of {@code Solution} objects representing all valid solutions for
     * the Sudoku puzzle. If no solutions exist, an empty list is returned.
     */
    public List<Solution> findAllSolutions(Sudoku sudoku) {
        List<Solution> solutions = new ArrayList<>();
        List<Field> fields = sudoku.getEmptyFields();
        backtrackAll(sudoku, fields, 0, solutions);
        return solutions;
    }

    /**
     * A recursive method for generating all possible solutions for a Sudoku puzzle using backtracking.
     * This method explores every potential value assignment for fields in the Sudoku grid and accumulates
     * the complete valid solutions.
     *
     * @param sudoku    the Sudoku puzzle represented by a grid of fields. The input object is expected
     *                  to have its fields initialized, with empty fields indicating unfilled cells.
     * @param fields    the list of fields to process, representing the grid's cells to be assigned values.
     * @param index     the zero-based index indicating the current field in the list being processed.
     * @param solutions the list that stores all valid solutions generated during the backtracking process.
     *                  Each valid solution is added as a {@code Solution} object to this list.
     */
    private void backtrackAll(Sudoku sudoku, List<Field> fields, int index, List<Solution> solutions) {
        if (index >= fields.size()) {
            solutions.add(Solution.from(sudoku));
            return;
        }

        Field current = fields.get(index);
        for (Value v : current.getDomain()) {
            current.setValue(v, false);
            backtrackAll(sudoku, fields, index + 1, solutions);
            current.setValue(Value.EMPTY, false);
        }
    }

    //----------------------------------- Find x amount Solutions ------------------------------------

    /**
     * Finds a specified number of possible solutions for the given Sudoku puzzle.
     * The method uses a backtracking algorithm to search for valid solutions and
     * collects up to the specified number of solutions. Each solution is represented
     * as a {@code Solution} object, capturing the state of the Sudoku board at the
     * time of completion.
     *
     * @param sudoku the {@code Sudoku} puzzle to solve, containing a 9x9 grid structure
     *               with fields representing the state of the board. Empty fields
     *               indicate unfilled cells requiring solutions.
     * @param amount the maximum number of solutions to find. Once the specified number
     *               of solutions is located, the search terminates.
     * @return a list of {@code Solution} objects representing the valid solutions
     * found for the given Sudoku puzzle. The size of the list will not exceed
     * the specified amount. If no solutions are found, an empty list is returned.
     */
    public List<Solution> findSolutions(Sudoku sudoku, int amount) {
        List<Solution> solutions = new ArrayList<>();
        List<Field> fields = sudoku.getEmptyFields();
        backtrackAmount(sudoku, amount, fields, 0, solutions);
        return solutions;
    }

    /**
     * A recursive method to find a specified number of solutions for a given Sudoku puzzle using backtracking.
     * This process explores all potential configurations by assigning values to fields in the grid until
     * either a specified number of solutions is found or no further possibilities exist.
     *
     * @param sudoku    the {@code Sudoku} puzzle represented as a grid of fields. The input object is expected
     *                  to have its fields initialized, with empty fields indicating unfilled cells.
     * @param amount    the maximum number of solutions to find. Once the specified number of solutions
     *                  is located, the search terminates.
     * @param fields    the list of fields in the Sudoku grid. These represent the cells that need values
     *                  assigned as part of the solution process.
     * @param index     the current position in the list of fields from which the recursive backtracking is
     *                  initiated. This parameter tracks progress as fields are processed.
     * @param solutions a list for storing the valid solutions found during the backtracking process.
     *                  Solutions are represented as {@code Solution} objects and added to this list.
     */
    private void backtrackAmount(Sudoku sudoku, int amount, List<Field> fields, int index, List<Solution> solutions) {
        if (solutions.size() >= amount) {
            return;
        }

        if (index >= fields.size()) {
            solutions.add(Solution.from(sudoku));
            return;
        }

        Field current = fields.get(index);
        for (Value v : current.getDomain()) {
            current.setValue(v, false);
            backtrackAmount(sudoku, amount, fields, index + 1, solutions);
            current.setValue(Value.EMPTY, false);
        }
    }

    //----------------------------------- Generate a full Sudoku ------------------------------------

    /**
     * Generates a Sudoku puzzle by preparing the grid with a defined set of empty
     * fields and then applying a generation algorithm to produce a valid puzzle.
     * This method modifies the given {@code Sudoku} object directly to create the puzzle.
     *
     * @param sudoku the {@code Sudoku} puzzle to be generated, represented as a grid.
     *               The input object must be initialized with its structure and field
     *               dependencies. This method will define empty fields and
     *               configure the puzzle state.
     */
    public void genPuzzle(Sudoku sudoku) {
        List<Field> empty = sudoku.getEmptyFields();
        backtrackGen(empty, 0);
    }

    /**
     * A recursive method for generating solutions to a Sudoku puzzle using backtracking.
     * This method attempts to assign values to fields in a randomized order, potentially
     * producing different solutions for multiple invocations.
     *
     * @param fields the list of fields representing the cells of the Sudoku puzzle. Each field
     *               should have its domain (set of possible values) and dependencies defined.
     * @param index  the zero-based index indicating the current position in the list of fields
     *               to be processed. The recursion progresses until all fields are assigned
     *               valid values or backtracking is required.
     */
    private void backtrackGen(List<Field> fields, int index) {
        if (index >= fields.size()) return;

        Field current = fields.get(index);

        List<Value> shuffled = new ArrayList<>(current.getDomain());
        Collections.shuffle(shuffled);

        for (Value v : shuffled) {
            current.setValue(v, false);
            if (backtrack(fields, index + 1)) return;
            current.setValue(Value.EMPTY, false);
        }
    }

    /*
    =====================================================================
    BACKTRACKING ALGORITHM – A SIMPLE BLUEPRINT FOR BEGINNERS
    =====================================================================

    Backtracking is a problem-solving technique that builds a solution
    step by step and undoes choices (i.e., backtracks) when a dead-end
    is reached. It’s like solving a puzzle by trying every possibility
    and going back if something doesn’t fit.

    ---------------------------------------------------------------------
    When Should You Use Backtracking?
    ---------------------------------------------------------------------
    Use backtracking when:
    - You need to try different combinations or configurations.
    - A decision at one step affects the next steps.
    - You must find one / all / a limited number of valid solutions.

    Common use cases:
    - Sudoku solving
    - N-Queens problem
    - Maze solving
    - Generating permutations or combinations

    ---------------------------------------------------------------------
    How Does Backtracking Work?
    ---------------------------------------------------------------------
    1. Start with an empty or partial solution.
    2. Make a choice (e.g., place a number, move to a cell).
    3. If the current state is valid, continue recursively.
    4. If invalid or no further progress: undo the choice (backtrack).
    5. Try the next possible choice.
    6. Repeat until:
       - A complete solution is found (✅), or
       - All options are exhausted (❌).

    ---------------------------------------------------------------------
    Pseudocode Template (with real code mapping)
    ---------------------------------------------------------------------
     // Abstract idea:
    function solve(state):
        if isComplete(state):
            recordSolution(state)
            return

        for each choice in possibleChoices(state):
            if isValid(choice, state):
                make(choice)
                solve(updatedState)
                undo(choice)


    // Concrete Java example from Sudoku Solver:

    private boolean backtrack(List<Field> fields, int index) {
        if (index >= fields.size()) return true;                    // Step 1: check for completion

        Field current = fields.get(index);                          // Step 2: select the current field
        for (Value v : current.getDomain()) {                       // Step 3: iterate through possible values
            current.setValue(v, false);                             // Step 4: make a choice
            if (backtrack(fields, index + 1)) return true;          // Step 5: recursive call to go deeper
            current.setValue(Value.EMPTY, false);                   // Step 6: undo (backtrack)
        }
        return false;                                               // No valid solution in this path
    }

    ---------------------------------------------------------------------
    Key Concepts
    ---------------------------------------------------------------------
    - Systematic trial-and-error using recursion
    - Recursive depth-first search through the solution space
    - Backtrack = “undo the last step and try something else”
    - Prune early = skip obviously invalid paths to improve performance

    ---------------------------------------------------------------------
    Summary
    ---------------------------------------------------------------------
    Backtracking is not magic – it's just trying every option one at
    a time, going deeper if it works, and backing up if it doesn't.

    It’s powerful for problems that require exploring combinations
    or configurations while obeying certain rules.

    Once you understand this pattern, you can adapt it to many problems!
    =====================================================================
*/
}