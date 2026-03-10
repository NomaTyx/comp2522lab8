import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

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
 * @author Julia Ziebart
 * @author Mayvee Tan
 * @version 1.0
 */
class NumberGuessingGame implements Game
{
    /*
     * Escape value that ends the game loop.
     */
    private static final String ESCAPE_INPUT = "x";

    /*
     * Minimum allowed value for a user guess.
     */
    private static final int MIN_GUESS = 1;

    /*
     * Maximum allowed value for a user guess.
     */
    private static final int MAX_GUESS = 5;

    /*
     * Inclusive lower bound for generated random answers.
     */
    private static final int ANSWER_MIN_BOUND_INCLUSIVE = 1;

    /*
     * Exclusive upper bound for generated random answers.
     */
    private static final int ANSWER_MAX_BOUND_EXCLUSIVE = 5;

    /*
     * How many points a correct guess is worth.
     */
    private static final int CORRECT_GUESS_POINT_VALUE = 1;

    /*
     * A buffer that stores a log of the current session
     */
    private final List<String> logBuffer;

    /*
     * The path of the game's log file.
     */
    private static final Path LOG_FILE_PATH = Paths.get("game_log.txt");

    /**
     * Constructor.
     */
    public NumberGuessingGame()
    {
        logBuffer = new ArrayList<>();
    }

    /**
     * Runs the core logic of the game.
     *
     * The method repeatedly requests input from the user until the escape
     * value is entered. Each valid guess is compared with a randomly
     * generated number. If the guess is correct, the score increases.
     *
     * When the user exits the loop, the score is printed if it is greater
     * than zero.
     */
    @Override
    public void runGame()
    {
        final Random random;
        int score;
        String userInput;
        int userGuess;
        int computerNumber;

        score = 0;
        random = new Random();

        System.out.println("Welcome to the guessing game!");
        System.out.print("In this game, you will guess a random number from ");
        System.out.println(MIN_GUESS + "-" + MAX_GUESS);
        System.out.println("Or, enter " + ESCAPE_INPUT + " to quit the game.");

        // Get the first input before entering the loop.
        userInput = getInput();

        // Continue processing guesses until the escape value is entered.
        while (!userInput.equals(ESCAPE_INPUT))
        {
            final boolean playerWon;

            userGuess = Integer.parseInt(userInput);
            computerNumber = random.nextInt(MIN_GUESS, MAX_GUESS + 1);

            if (userGuess == computerNumber)
            {
                score += CORRECT_GUESS_POINT_VALUE;
                playerWon = true;
                System.out.println("You got a correct answer! Score: " + score);
            }
            else
            {
                playerWon = false;
                System.out.println("You did not get the correct answer. Unfortunate. Your score is unchanged.");
            }

            addRoundToLogBuffer(userGuess, computerNumber, playerWon, score);

            userInput = getInput();
        }
        writeBufferToFile();
    }

    /*
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
        boolean validInput;
        String userInput;

        inputScanner = new Scanner(System.in);

        do
        {
            System.out.print("Guess: ");
            userInput = inputScanner.nextLine();

            try
            {
                validateInput(userInput);
                validInput = true;
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
                validInput = false;
            }

        } while (!validInput);

        return userInput;
    }

    /*
     * Validates the user's input.
     *
     * The input must either be a number within the allowed guess range
     * or the escape value used to end the game. If the input is numeric
     * but outside the allowed range, or if it is not numeric and not the
     * escape value, the program throws a runtime exception.
     *
     * @param input the user input to validate
     *
     * @throws InputMismatchException if the provided input is invalid.
     */
    private static void validateInput(final String input) throws InputMismatchException
    {
        try
        {
            if (Integer.parseInt(input) < MIN_GUESS || Integer.parseInt(input) > MAX_GUESS)
            {
                throw new InputMismatchException("Input out of bounds");
            }
        }
        catch (NumberFormatException e)
        {
            if (!input.equals(ESCAPE_INPUT))
            {
                throw new InputMismatchException("Input not a number.");
            }
        }
    }

    /*
     * Interprets a round of the game played into text and adds it to a buffer.
     *
     * @param userGuess The number the user guessed
     * @param chosenNumber the number that the computer generated
     * @param score The user's current total score
     */
    private void addRoundToLogBuffer(int userGuess, int chosenNumber, boolean roundWon, int score)
    {
        final StringBuilder logString;
        logString = new StringBuilder();

        logString.append("Player guess: ");
        logString.append(userGuess);
        logString.append(" | Generated number: ");
        logString.append(chosenNumber);
        logString.append(" | ");

        if(roundWon)
        {
            logString.append("Player won");
        }
        else
        {
            logString.append("Player lost");
        }

        logString.append(" | Score: ");
        logString.append(score);

        logBuffer.add(logString.toString());
    }

    /*
     * Writes the contents of the buffer to the file.
     * Buffer is used because system storage is monstrously slow so generally
     * we'd prefer to call it as few times as possible.
     */
    private void writeBufferToFile()
    {
        if(!logBuffer.isEmpty())
        {
            try
            {
                Files.write(LOG_FILE_PATH, logBuffer, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                System.out.println("Data logged");
                logBuffer.clear();
            }
            catch (Exception e)
            {
                System.out.println("Failed to log data.");
            }
        }
    }

    /*
     * Loads the game from the log file.
     *
     * @return the score from the player's previous session.
     */
    private int loadScoreFromLog()
    {
        
    }
}