/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Andres Guevara-Flores
 * Section: 11:30 AM
 * Date: 11/8/2020
 * Time: 12:57 PM
 *
 * Project: csci205FinalProject
 * Package: MasterMind
 * Class: mastermindModel
 *
 * Description:
 *
 * ****************************************
 */
package MasterMind;

public class MasterMindModel {
    private int guessCode;
    private int guessesLeft; // total count left
    private String score; // score to the current guess code from the user
    private String mode; // user' playing mode (single or multiple)
    private String role; // user's role (host or join)
    private int time; // the total time that the user has used

    /**
     * This method initialize the fields
     * */
    public MasterMindModel(){
        guessCode = 0; // initialize the guess code as 0
        guessesLeft = 12; // initialize the count left as 12
        score = ""; // initialize the score as an empty string
        mode = ""; // initialize the mode as an empty string
        role = ""; // initialize the role as an empty string
        time = 0; // initialize the time as 0
    }

    public void setGuessCode(int guessCode) { this.guessCode = guessCode; } // set the guess code

    public void setGuessesLeft(int count) { this.guessesLeft = count; } // set the count left

    public void setScore(String score) { this.score = score; } // set the current core

    public void setMode(String mode) {
        this.mode = mode;
    } // set the mode ("S" or "M")

    public void setRole(String role) {
        this.role = role;
    } // set the role ("H" or "J")

    public String getMode() {
        return mode;
    } // return the current mode

    public String getRole() {
        return role;
    } // return the current role

    public int getTime() {
        return time;
    } // return the current time used

    public int getGuessesLeft() { return guessesLeft;} // return the current count left

    public String getScore() { return score; } // return the current score

}