package hunting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The View/Controller class responsible for rendering the game matrix and button grid.
 * It listens to user interaction events and updates the interface to match the model's state.
 */
public class BoardGUI {
    private JButton[][] buttons;
    private HuntingBoard board;
    private JPanel boardPanel;
    private JLabel statusLabel;

    /**
     * Constructs a BoardGUI component for a specified board size.
     * Initializes the underlying {@link HuntingBoard} model, builds the grid of JButtons,
     * attaches action listeners, and prepares the status label.
     *
     * @param boardSize the size of the board (n x n).
     */
    public BoardGUI(int boardSize) {
        board = new HuntingBoard(boardSize);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        buttons = new JButton[board.getBoardSize()][board.getBoardSize()];

        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(70, 70));
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.addActionListener(new ButtonListener(i, j));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }

        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        refresh();
    }

    /**
     * Re-renders the GUI components by reading the latest state from the {@link HuntingBoard} model.
     * Updates button text labels, background colors, and turn status indicators.
     */
    public void refresh() {
        int size = board.getBoardSize();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Field field = board.getField(i, j);
                JButton button = buttons[i][j];

                if (field.getType() == Field.Type.FUGITIVE) {
                    button.setText("F");
                    button.setBackground(Color.GREEN);
                } else if (field.getType() == Field.Type.HUNTER) {
                    button.setText("H");
                    button.setBackground(Color.RED);
                } else {
                    button.setText("");
                    button.setBackground(Color.LIGHT_GRAY);
                }
            }
        }

        // If there is a selected Hunter, make it orange.
        Point sh = board.getSelectedHunter();
        if (sh != null) {
            buttons[sh.x][sh.y].setBackground(Color.ORANGE);
        }

        String turnStr = board.isFugitiveTurn() ? "Fugitive" : "Hunter";
        statusLabel.setText(String.format("Turn: %s | Move: %d / %d", 
                turnStr, board.getCurrentTurn(), board.getMaxTurns()));
    }

    /**
     * Checks whether the game has concluded.
     * If the game is over, displays a popup dialog showing the winner and automatically starts a new game.
     */
    private void checkGameOver() {
        if (board.isOver()) {
            JOptionPane.showMessageDialog(boardPanel, board.getWinnerMessage(), "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            // Automatically start a new game
            board = new HuntingBoard(board.getBoardSize());
            refresh();
        }
    }

    /**
     * Gets the main panel holding the grid of interactive buttons.
     *
     * @return the {@link JPanel} containing the game board buttons.
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Gets the label displaying turn and move status information.
     *
     * @return the status {@link JLabel}.
     */
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * An inner class implementing {@link ActionListener} to handle button clicks on grid tiles.
     */
    private class ButtonListener implements ActionListener {
        private int x, y;

        /**
         * Constructs a listener associated with specific grid cell coordinates.
         *
         * @param x the row coordinate of the button.
         * @param y the column coordinate of the button.
         */
        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Triggered when a grid cell button is clicked. Delegates the click logic to the model,
         * refreshes the display, and checks for game completion.
         *
         * @param e the triggering {@link ActionEvent}.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (board.handleTileClick(x, y)) {
                refresh();
                checkGameOver();
            }
        }
    }
}