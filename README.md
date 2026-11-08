# Hunting Game (Java Swing & OOP)

A two-player strategy board game implemented in Java Swing following standard Object-Oriented Programming (OOP) and Model-View-Controller (MVC) architecture.

## 🎮 Game Rules
- **Fugitive (F):** Starts at the center of the grid. Objective is to survive until the maximum turn limit or evade capture.
- **Hunters (H):** 4 Hunters starting at the four corners of the grid. Objective is to completely trap the Fugitive within 4n turns so that it has no valid adjacent moves left.
- **Movement:** Players take turns moving 1 step in any direction (horizontal, vertical, or diagonal) into an empty adjacent tile.

## 📏 Selectable Board Sizes
- **3x3 Grid:** 12 turns limit
- **5x5 Grid:** 20 turns limit
- **7x7 Grid:** 28 turns limit

## 🛠️ Architecture & Project Structure
- **Language:** Java 8+
- **GUI Library:** Java Swing & AWT
- **Design Pattern:** Layered MVC / Separation of Concerns
  - `Field.java` - Single square model state
  - `HuntingBoard.java` - Game logic, board state, and movement validation
  - `BoardGUI.java` - Board grid rendering and button listeners
  - `HuntingGameGUI.java` - Main JFrame and dynamic menu management
  - `Main.java` - Application execution entry point

## 🚀 How to Run
1. Clone this repository:
   ```bash
   git clone [https://github.com/xBoraNas/Hunting.git](https://github.com/xBoraNas/Hunting.git)
