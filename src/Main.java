/**
 * Driver class for NumberGuessingGame.java
 *
 * @author Julia Ziebart
 * @author Mayvee Tan
 * @version 1.0
 */
public class Main
{
    /**
     * Entry point of the program.
     *
     * Creates an instance of the game and starts execution.
     *
     * @param args command line arguments passed to the program
     */
    public static void main(final String[] args)
    {
        final Game gameInstance;
        gameInstance = new NumberGuessingGame();

        gameInstance.runGame();
    }
}
