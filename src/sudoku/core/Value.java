package sudoku.core;

/**
 * The Value enum represents predefined values for numbers that could be used in a Sudoku puzzle.
 * Each constant is associated with its corresponding integer representation, ranging from 0 (EMPTY) to 9.
 * The enum also provides utility methods for value manipulation and conversion.
 */
public enum Value {
    EMPTY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    private final int number;

    /**
     * Constructs an instance of the Value enum with the specified integer representation.
     *
     * @param i the integer value associated with the specific Value constant
     */
    Value(int i) {
        number = i;
    }

    /**
     * Returns a string representation of the current enum constant.
     * The representation includes a space, followed by the integer value associated with the constant, and another space.
     *
     * @return the string representation of the enum constant's integer value, enclosed with leading and trailing spaces
     */
    @Override
    public String toString() {
        return " "+Integer.toString(number)+" ";
    }

    /**
     * Retrieves the integer representation of the current enumeration constant.
     *
     * @return the integer associated with this enumeration constant
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retrieves the corresponding Value enum constant for a given integer.
     * The method searches through the available constants in the Value enum
     * for a match with the provided integer. If a match is found, the corresponding
     * Value constant is returned; otherwise, null is returned.
     *
     * @param i the integer value to find the corresponding Value enum constant for
     * @return the matching Value constant if found, or null if no match exists
     */
    public static Value of(int i) {
        for (Value v : values()) {
            if (v.getNumber() == i) {
                return v;
            }
        }
        return null;
    }

    /**
     * Converts the integer value associated with the enum constant into an array of characters.
     * Each digit of the integer value is represented as a separate character in the array.
     *
     * @return a character array representing the digits of the integer value
     */
    public char[] getDigit() {
        return String.valueOf(number).toCharArray();
    }
    
    /*
    Pros and Cons of Using Enums Instead of Integers

    Pros:
    -----
    - Type Safety:
      Enums restrict the possible values to a defined set (e.g., Value.ONE to Value.NINE).
      This prevents invalid or unintended numbers from being used.

    - Clearer Semantics:
      Code like Value.TWO is more meaningful and self-explanatory than using plain numbers like 2.

    - Extendability:
      Enums can contain methods, fields, and logic.
      For example, a method like getNumber() can convert an enum to its corresponding int.

    - Better Maintainability and Readability:
      When reading or debugging, enums make the code more expressive and easier to follow.

    Cons:
    -----
    - Slightly More Verbose:
      Requires more code and structure compared to simple int usage.

    - Minor Performance Overhead:
      Accessing enum values can be marginally slower than using raw integers—
      although this is negligible in most real-world applications.

    - Conversion Overhead:
      Enums may need to be converted to and from int, especially when interacting with user input,
      files, databases, or other external data sources.
    */
}
