package sudoku.ui;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;
import java.awt.Font;

/**
 * A custom implementation of {@link JTextField} designed for restricting input
 * to a single digit between 1 and 9. This class is intended for use in applications
 * where constrained numeric input is required, such as in Sudoku grids.
 * <p>
 * The text field automatically applies the following customizations:
 * - A limit of one visible character is set by default.
 * - Text alignment is centered.
 * - A bold "Arial" font with a size of 20 is used.
 * - A custom {@link DigitFilter} is applied to the text field's document to enforce
 *   input validation, ensuring only a single digit (1-9) can be entered.
 * </p>
 * This text field is particularly useful in grid-based input scenarios where
 * strict control over user input is required.
 */
public class DigitTextField extends JTextField {

    public DigitTextField() {
        super(1);
        setHorizontalAlignment(CENTER);
        setFont(new Font("Arial", Font.BOLD, 20));

        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new DigitFilter());
        setDocument(doc);
    }
}