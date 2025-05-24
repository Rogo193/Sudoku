package sudoku.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A custom implementation of DocumentFilter that restricts input in a text component
 * to a single digit between 1 and 9. This filter ensures only valid single-character
 * numeric input is allowed and prevents overwriting behaviors that violate the intended
 * input rules.
 */
public class DigitFilter extends DocumentFilter {

    /**
     * Inserts the specified string into the document, respecting the constraints
     * of valid input defined by the filter. Only allows input that passes the
     * validity check performed by the {@code isValid} method.
     *
     * @param fb    the FilterBypass that can be used to mutate the document
     * @param offset the offset into the document to insert the content
     * @param string the string to insert into the document
     * @param attr  the attributes to associate with the inserted text, or null
     * @throws BadLocationException if the offset is invalid or insertion fails
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (isValid(string, fb.getDocument().getLength())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    /**
     * Replaces a portion of the document with the specified text, while ensuring
     * that the input adheres to the validity constraints defined by the {@code isValid} method.
     *
     * @param fb    the FilterBypass that can be used to directly modify the document
     * @param offset the starting offset in the document to replace content
     * @param length the length in the document to replace
     * @param text  the text to insert as a replacement
     * @param attr  the attributes to associate with the inserted text, or null
     * @throws BadLocationException if the offset or length is invalid, or if the replacement fails
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr)
            throws BadLocationException {
        if (isValid(text, fb.getDocument().getLength() - length)) {
            super.replace(fb, offset, length, text, attr);
        }
    }

    /**
     * Validates whether the given string and current document length conform to the defined input constraints.
     * The method checks if the string is either empty or a single digit in the range 1-9, and ensures that the
     * current length of the document is zero, indicating no prior input.
     *
     * @param s the input string to validate
     * @param currentLength the current length of the document
     * @return true if the string is valid and the document length is zero; false otherwise
     */
    private boolean isValid(String s, int currentLength) {
        return (s.isEmpty() || s.matches("[1-9]")) && currentLength == 0;
    }
}
