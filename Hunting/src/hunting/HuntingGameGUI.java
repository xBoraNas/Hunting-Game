package hunting;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The primary View class that sets up the main application frame ({@link JFrame}) and menu bar.
 * It manages window resizing and dynamic swapping of the {@link BoardGUI} panel when the board size changes.
 */
public class HuntingGameGUI {
    private JFrame frame;
    private BoardGUI boardGUI;
    private final int INITIAL_BOARD_SIZE = 5;

    /**
     * Constructs the main GUI window, builds the menu bar, attaches event handlers for
     * board size selection, and makes the window visible.
     */
    public HuntingGameGUI() {
        frame = new JFrame("Hunting Game - Hunter and Fugitive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardGUI.getStatusLabel(), BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenu newMenu = new JMenu("New Game");
        gameMenu.add(newMenu);

        int[] boardSizes = new int[]{3, 5, 7}; 
        for (int size : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(size + "x" + size);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                /**
                 * Handles menu selection events for changing board sizes.
                 * Removes the existing board panel, instantiates a new board of the chosen size,
                 * and automatically resizes the main window frame.
                 *
                 * @param e the triggering {@link ActionEvent}.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    frame.getContentPane().remove(boardGUI.getStatusLabel());

                    boardGUI = new BoardGUI(size);
                    frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
                    frame.getContentPane().add(boardGUI.getStatusLabel(), BorderLayout.SOUTH);

                    frame.pack(); // Adjusts the window to fit the new dimensions perfectly
                }
            });
        }

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(e -> System.exit(0));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}