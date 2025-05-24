package sudoku.core;

/**
 * Represents a 2D position defined by coordinates x and y.
 * This class is designed as a record, meaning it is a compact and immutable data carrier.
 */
public record Position(int x, int y) {

    public boolean equals(int x, int y) {
        return x == this.x && y == this.y;
    }
}

/*
   Advantages and Disadvantages of Record Classes in Java

   Advantages:
   -----------
   1. Less Boilerplate:
      - Automatically generates constructor, getters, toString(), equals(), and hashCode()

   2. Immutable by Default:
      - All fields are final and cannot be changed after construction
      - Improves code safety and predictability

   3. Clear Semantics:
      - Ideal for pure data containers (Value Objects, DTOs, coordinates, results, etc.)

   4. Better Debugging & Comparison:
      - toString() and equals() produce meaningful and useful outputs by default

   5. Fully Functional Classes:
      - Can implement interfaces
      - Can define custom methods and static methods

   Disadvantages:
   --------------
   1. Immutability:
      - Fields are final – no setX(...) methods possible
      - Not suitable for mutable objects (e.g., game logic)

   2. No Inheritance:
      - Records are implicitly final and cannot be extended
      - Only inherit from Object

   3. No Overloaded Constructors:
      - Only one constructor with all parameters (canonical constructor)
      - Additional constructors require extra effort

   4. Simple Field Declarations Only:
      - No complex initialization or parameter logic allowed in the header
*/