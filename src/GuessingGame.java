import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;

//remember to get input from a file
class GuessingGame
{
    private static final String ESCAPE_INPUT = "x";
    private static final int MIN_GUESS = 1;
    private static final int MAX_GUESS = 5;
    private static final int ANSWER_MIN_BOUND_INCLUSIVE = 1;
    private static final int ANSWER_MAX_BOUND_EXCLUSIVE = 5;

    public static void main(final String[] args)
    {
        final GuessingGame gameInstance;

        gameInstance = new GuessingGame();

        gameInstance.runGame();
    }

    private void runGame()
    {
        final int gameScorePrintThreshold = 0;
        int gameScore; //adding this because it kinda needs to exist
        String userInput;

        gameScore = 0;
        userInput = getInput();

        while(!userInput.equals(ESCAPE_INPUT))
        {
            if(compareInput(Integer.parseInt(userInput)))
            {
                gameScore++;
            }

            userInput = getInput();
        }

        if(gameScore > gameScorePrintThreshold)
        {
            System.out.println(gameScore);
        }
    }

    private String getInput()
    {
        final Scanner inputScanner;
        inputScanner = new Scanner(System.in);

        System.out.print("Give me input: ");

        final String userInput;
        userInput = inputScanner.nextLine();

        validateInput(userInput);

        return userInput;
    }

    private static void validateInput(final String input)
    {
        try {
            if (Integer.parseInt(input) < MIN_GUESS ||
                    Integer.parseInt(input) > MAX_GUESS)
            {
                throw new RuntimeException("Input out of bounds");
            }
        } catch (Exception e) {
            if(!input.equals(ESCAPE_INPUT)) {
                throw new RuntimeException("Input not accepted.");
            }
        }
    }

    private boolean compareInput(final int input) {
        final Random ayn;
        ayn = new Random();

        return input == ayn.nextInt(ANSWER_MIN_BOUND_INCLUSIVE, ANSWER_MAX_BOUND_EXCLUSIVE);
    }
}
