/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Andres Guevara-Flores & Bryan Birrueta
 * Updated by: Khanh Pham and Jessica Zhao
 *
 * Section: 11:30 AM
 * Date: 10/10/2020
 * Time: 7:19 PM
 *
 * Project: csci205_hw
 * Package: MasterMind
 * Class: codeMaker
 *
 * Description:
 * Create a code Maker
 * ****************************************
 */
package MasterMind;

public class codeMaker {

    /**
     * If it's single player, a random code is generated from 1 to however many pegs there are (6 default)
     * If it's multiplayer, we open up a socket and get their input for the secret code
     *
     * @param pegs      # of pegs
     * @param positions # of position
     * @return secretCode the secretCode
     */
    public static int generateSecretCode(int pegs, int positions){
        // Single player case for both "S" and "s"
        // secretly generated or host-created code value
        int secretCode = 0;

        // Create a randomized secret code
        while (positions > 0) {
            int guess = (int) (Math.random() * 10);
            if (guess <= pegs && guess >= 1) {
                secretCode += guess * (int) Math.pow(10, positions - 1);
                positions--;
            }
        }
        return secretCode;
    }

    /**
     * This method first checks to see if any of the guesses are the right pegs and in the right position
     * then it checks if any of the guesses are the right pegs but the wrong position.
     * finally, it checks for how many of the guesses are wrong altogether.
     *
     * @param guess     the guess from the code breaker
     * @param players   single or multiplayer
     * @return the String representation of the score
     */
    public static String evaluateGuess(int secretCode, int guess, String players) {
        // the score for the current guess
        // Guess-based score string
        String score = "";

        // Create a temporary StringBuilder for the secret code
        StringBuilder secretCodeString = new StringBuilder();
        // Single Player case
        if (players.equalsIgnoreCase("S")) {
            secretCodeString.append(secretCode);
        }
        // Multiplayer Case
        else if (players.equalsIgnoreCase("M")) {
            secretCodeString.append(secretCode);
        }

        // Create a temporary StringBuilder for the guess code
        StringBuilder guessString = new StringBuilder();
        guessString.append(guess);

        // This will sort the score in the order of "*", "+", "-"
        // Check each peg in the guess code
        // If the secret code contains it and also in the correct position
        for (int g = 0; g < secretCodeString.length(); g++) {
            // If the peg is correct, add "*" in the score and only leave the incorrect ones in the string
            if (guessString.charAt(g) == secretCodeString.charAt(g)) {
                guessString.deleteCharAt(g);
                secretCodeString.deleteCharAt(g);
                score += "*";
                g--;
            }
        }

        // If the secret code contains it but NOT in the correct position
        for (int i = 0; i < secretCodeString.length(); i++) {
            // If the secret code contain this peg, add "+" in the score and only leave the incorrect ones in the string
            if (secretCodeString.toString().contains(guessString.substring(i, i + 1))) {
                int x = secretCodeString.indexOf((guessString.substring(i, i + 1)));
                secretCodeString.deleteCharAt(x);
                guessString.deleteCharAt(i);
                i--;
                score += "+";
            }
        }

        // For all the incorrect pegs in the guess code, we will put "-" into score.
        for (int i = 0; i < secretCodeString.length(); i++) {
            score += "-";
        }

        return score;
    }
}
