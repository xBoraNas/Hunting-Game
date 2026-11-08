package hunting;

import java.awt.Point;

/**
 * A model class that manages the overall state of the game board, character positions,
 * movement validation, turn counters, and win conditions.
 */
public class HuntingBoard {
    private final int boardSize;
    private final int maxTurns;
    private int currentTurn;
    private boolean isFugitiveTurn;

    private Field[][] grid;
    private Point fugitive;
    private Point[] hunters;
    private Point selectedHunter;

    /**
     * Constructs a HuntingBoard instance with the specified grid dimension.
     * Initializes the grid array, maximum allowable turns (4 * boardSize), and starting positions.
     *
     * @param boardSize the size of one side of the square board (n x n).
     */
    public HuntingBoard(int boardSize) {
        this.boardSize = boardSize;
        this.maxTurns = 4 * boardSize; // 3x3 -> 12, 5x5 -> 20, 7x7 -> 28
        this.grid = new Field[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j] = new Field();
            }
        }
        initPositions();
    }

    /**
     * Clears the board and sets initial starting positions for all characters.
     * The Fugitive is placed at the center, and the four Hunters are placed at the corners.
     */
    private void initPositions() {
        this.currentTurn = 0;
        this.isFugitiveTurn = true;
        this.selectedHunter = null;

        // Clean Grid
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j].setType(Field.Type.EMPTY);
            }
        }

        // The hunt begins in the middle.
        int center = boardSize / 2;
        fugitive = new Point(center, center);
        grid[center][center].setType(Field.Type.FUGITIVE);

        // Hunters start at the 4 corners
        hunters = new Point[4];
        hunters[0] = new Point(0, 0);
        hunters[1] = new Point(0, boardSize - 1);
        hunters[2] = new Point(boardSize - 1, 0);
        hunters[3] = new Point(boardSize - 1, boardSize - 1);

        for (Point h : hunters) {
            grid[h.x][h.y].setType(Field.Type.HUNTER);
        }
    }

    /**
     * Handles user clicks on a specified grid tile. Performs character selection
     * or movement validation depending on whose turn it currently is.
     *
     * @param r the row index of the clicked tile.
     * @param c the column index of the clicked tile.
     * @return {@code true} if a move or selection was successfully executed; {@code false} otherwise.
     */
    public boolean handleTileClick(int r, int c) {
        Point clicked = new Point(r, c);

        if (isFugitiveTurn) {
            if (isValidMove(fugitive, clicked)) {
                grid[fugitive.x][fugitive.y].setType(Field.Type.EMPTY);
                fugitive = clicked;
                grid[fugitive.x][fugitive.y].setType(Field.Type.FUGITIVE);
                isFugitiveTurn = false;
                currentTurn++;
                return true;
            }
        } else {
            // Select if the clicked location is a hunter.
            for (Point h : hunters) {
                if (h.equals(clicked)) {
                    selectedHunter = h;
                    return true;
                }
            }
            // Play the selected hunter.
            if (selectedHunter != null && isValidMove(selectedHunter, clicked)) {
                grid[selectedHunter.x][selectedHunter.y].setType(Field.Type.EMPTY);
                selectedHunter.setLocation(clicked);
                grid[selectedHunter.x][selectedHunter.y].setType(Field.Type.HUNTER);
                selectedHunter = null;
                isFugitiveTurn = true;
                currentTurn++;
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether a move from one point to another is legal.
     * A valid move must be exactly 1 step (horizontally, vertically, or diagonally) to an empty field.
     *
     * @param from the starting {@link Point}.
     * @param to the target {@link Point}.
     * @return {@code true} if the move is valid; {@code false} otherwise.
     */
    private boolean isValidMove(Point from, Point to) {
        int dr = Math.abs(from.x - to.x);
        int dc = Math.abs(from.y - to.y);

        if (dr > 1 || dc > 1 || (dr == 0 && dc == 0)) {
            return false;
        }

        return grid[to.x][to.y].isEmpty();
    }

    /**
     * Checks if the game has ended.
     * The game ends if the Fugitive has no legal moves left or the turn limit is reached.
     *
     * @return {@code true} if the game is over; {@code false} otherwise.
     */
    public boolean isOver() {
        return !hasLegalMoves(fugitive) || currentTurn >= maxTurns;
    }

    /**
     * Generates a result message announcing the winner of the game.
     *
     * @return a message {@link String} declaring the winner, or an empty string if the game is ongoing.
     */
    public String getWinnerMessage() {
        if (!hasLegalMoves(fugitive)) {
            return "Hunter Won! Hunt has been surrounded.";
        }
        if (currentTurn >= maxTurns) {
            return "Fugitive Won! The maximum move limit has been reached.";
        }
        return "";
    }

    /**
     * Checks if a character at a given position has any valid adjacent fields to move into.
     *
     * @param p the {@link Point} position to check.
     * @return {@code true} if there is at least one legal move available; {@code false} otherwise.
     */
    private boolean hasLegalMoves(Point p) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = p.x + dr;
                int nc = p.y + dc;

                if (nr >= 0 && nr < boardSize && nc >= 0 && nc < boardSize) {
                    if (grid[nr][nc].isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets the dimension of the board.
     *
     * @return the board size.
     */
    public int getBoardSize() { return boardSize; }

    /**
     * Gets the maximum number of turns allowed for the current game size.
     *
     * @return the maximum turn limit.
     */
    public int getMaxTurns() { return maxTurns; }

    /**
     * Gets the count of turns played so far.
     *
     * @return the current turn number.
     */
    public int getCurrentTurn() { return currentTurn; }

    /**
     * Indicates whether it is currently the Fugitive's turn.
     *
     * @return {@code true} if it is the Fugitive's turn; {@code false} if it is the Hunter's turn.
     */
    public boolean isFugitiveTurn() { return isFugitiveTurn; }

    /**
     * Retrieves the Field object at the specified grid location.
     *
     * @param r the row index.
     * @param c the column index.
     * @return the {@link Field} instance at coordinates (r, c).
     */
    public Field getField(int r, int c) { return grid[r][c]; }

    /**
     * Gets the location of the currently selected Hunter.
     *
     * @return the {@link Point} location of the selected Hunter, or {@code null} if none is selected.
     */
    public Point getSelectedHunter() { return selectedHunter; }
}