import java.util.Random;
import java.util.Scanner;
import java.sql.SQLOutput;

/**
 * A simple number guessing game program.
 *
 * The program repeatedly asks the user to enter a number guess within a
 * defined range. For each guess entered, a random number is generated
 * and compared with the user's guess. If the guess matches the generated
 * number, the user's score increases.
 *
 * The user may exit the game by entering the escape value. When the game
 * ends, the program prints the final score if it is greater than zero.
 *
 * @author Julia and Mayvee
 * @version 1.0
 */
class GuessingGame
{
    /**
     * Escape value that ends the game loop.
     */
    private static final String ESCAPE_INPUT = "x";

    /**
     * Minimum allowed value for a user guess.
     */
    private static final int MIN_GUESS = 1;

    /**
     * Maximum allowed value for a user guess.
     */
    private static final int MAX_GUESS = 5;

    /**
     * Inclusive lower bound for generated random answers.
     */
    private static final int ANSWER_MIN_BOUND_INCLUSIVE = 1;

    /**
     * Exclusive upper bound for generated random answers.
     */
    private static final int ANSWER_MAX_BOUND_EXCLUSIVE = 5;

    /**
     * Entry point of the program.
     *
     * Creates an instance of the game and starts execution.
     *
     * @param args command line arguments passed to the program
     */
    public static void main(final String[] args)
    {
        final GuessingGame gameInstance;

        gameInstance = new GuessingGame();
        gameInstance.runGame();
    }

    /**
     * Runs the main game loop.
     *
     * The method repeatedly requests input from the user until the escape
     * value is entered. Each valid guess is compared with a randomly
     * generated number. If the guess is correct, the score increases.
     *
     * When the user exits the loop, the score is printed if it is greater
     * than zero.
     */
    private void runGame()
    {
        final int gameScorePrintThreshold;
        int gameScore;
        String userInput;

        gameScorePrintThreshold = 0;
        gameScore = 0;

        // Get the first input before entering the loop.
        userInput = getInput();

        // Continue processing guesses until the escape value is entered.
        while (!userInput.equals(ESCAPE_INPUT))
        {
            if (compareInput(Integer.parseInt(userInput)))
            {
                gameScore++;
            }

            userInput = getInput();
        }

        // Print the score only if the user achieved at least one correct guess.
        if (gameScore > gameScorePrintThreshold)
        {
            System.out.println(gameScore);
        }
    }

    /**
     * Prompts the user for input and validates it.
     *
     * The user may enter either a numeric guess within the allowed range
     * or the escape value used to terminate the game. Validation occurs
     * before the input is returned.
     *
     * @return the validated user input
     */
    private String getInput()
    {
        final Scanner inputScanner;
        final String userInput;

        inputScanner = new Scanner(System.in);

        // Prompt the user to enter a guess or the escape value.
        System.out.print("Give me input: ");
        userInput = inputScanner.nextLine();

        // Validate the input before returning it.
        validateInput(userInput);

        return userInput;
    }

    /**
     * Validates the user's input.
     *
     * The input must either be a number within the allowed guess range
     * or the escape value used to end the game. If the input is numeric
     * but outside the allowed range, or if it is not numeric and not the
     * escape value, the program throws a runtime exception.
     *
     * @param input the user input to validate
     */
    private static void validateInput(final String input)
    {
        try
        {
            if (Integer.parseInt(input) < MIN_GUESS || Integer.parseInt(input) > MAX_GUESS)
            {
                throw new RuntimeException("Input out of bounds");
            }
        }
        catch (Exception e)
        {
            if (!input.equals(ESCAPE_INPUT))
            {
                throw new RuntimeException("Input not accepted.");
            }
        }
    }

    /**
     * Compares the user's guess with a randomly generated number.
     *
     * A random number is generated within the configured bounds.
     * The method returns true if the user's guess matches the
     * generated number, otherwise it returns false.
     *
     * @param input the user's numeric guess
     * @return true if the guess matches the generated number,
     *         false otherwise
     */
    private boolean compareInput(final int input)
    {
        final Random ayn;

        ayn = new Random();

        return input == ayn.nextInt(ANSWER_MIN_BOUND_INCLUSIVE, ANSWER_MAX_BOUND_EXCLUSIVE);
    }
}