package hunting;

import javax.swing.SwingUtilities;

/**
 * The entry point class for running the Hunting Game application.
 */
public class Main {

    /**
     * The main method that starts the application safely on the Swing Event Dispatch Thread.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HuntingGameGUI());
    }
}