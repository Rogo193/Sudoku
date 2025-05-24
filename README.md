# Sudoku – A Simple Java Sudoku Game

This is a self-contained, GUI-based Sudoku game written in **Java** using **Swing**.  
It allows users to generate puzzles, solve them automatically, and validate their own solutions.

---

## Features

- Sudoku puzzle generation with different difficulty levels
- Sudoku solving via backtracking
- Input validation to detect rule violations
- Fully interactive GUI with keyboard navigation
- Simple design with clean, testable components

---

## Background

> This project started as a **university exercise** that focused solely on implementing Sudoku logic in Java.  
> I extended it further by adding a full **graphical user interface (GUI)** using Swing, as well as features like:
>
> - Puzzle generation with difficulty levels
> - Automatic solving (with a helpful backtracking template)
> - Keyboard navigation
> - Input validation
>
> Now, I’m publishing it to share the result and help others learn from it.

---

## Getting Started

### Requirements
- Java 17 or higher
- IntelliJ IDEA or another Java IDE (optional, but recommended)

### Run the application
1. Clone or download the repository
2. Open the project in your IDE
3. Run the `main` method in `Main.java`:
   ```java
   public static void main(String[] args) {
       new SudokuGui().run();
   }
   ```

---

## Project Structure

```
src/
└── sudoku/
      ├── core/        # Core Sudoku logic: Sudoku, Field, Value, Position
      ├── generator/   # Puzzle generator and difficulty settings
      ├── solver/      # Backtracking solver and solution snapshot
      └── ui/          # GUI: JTextField grid, navigation, buttons
```

---

## Design Decision: No MVC/MVVM

This project **does not strictly follow MVC or MVVM architecture** — intentionally.

While these patterns are beneficial for large-scale software, I decided against adding architectural complexity for the following reasons:

- The goal was clarity and fast development during and after the university exercise.
- The current structure already separates core logic and GUI well enough.
- Logic classes (like `Sudoku`, `Solver`, `Generator`) serve as a natural foundation for a model.
- The project remains easily extensible — MVC/MVVM refactoring can be done later if needed.

---

## Future Improvements

- Add timer and statistics
- Implement undo/redo functionality
- Refactor GUI toward MVC or MVVM pattern
- Add difficulty detection or puzzle scoring
- Port to JavaFX or a web-based version

---

## License

This project is licensed under the MIT License – feel free to use, extend, and contribute.

---

## Credits

Developed by _R0G0.  
Originally created for an academic assignment and expanded for educational and public use.
