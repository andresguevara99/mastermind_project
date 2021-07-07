/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Andres Guevara-Flores
 * Section: 11:30 AM
 * Date: 11/11/2020
 * Time: 12:21 PM
 *
 * Project: csci205FinalProject
 * Package: MasterMind
 * Class: MasterMindController
 *
 * Description:
 *
 * ****************************************
 */
package MasterMind;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.time.LocalTime;

public class MasterMindController {
    private MasterMindModel theModel; // the Model
    private MasterMindView theView; // the View
    private guessCircleColors theColors; // the colors of the guess circles
    private codeMaker theCodeMaker; // the code maker
    private Host theHost; // the host
    private Join theJoin; // the join

    private int[] Guess = {0, 0, 0, 0}; // the list form of a guess code
    private final int positions; // the total number of positions
    private final int pegs; // the total number of pegs
    private int[] totalClickCount; // the total number of click count
    private int[] guessNumbers; // the guess number
    private Color[] guessColor;  // a list of guess colors
    private int secretCode; // the int form of secret code
    private Circle c; // the circle
    private int[] guessButtonCount; // the number of time that hte guess BUttom clicked
    private static int startTime; // the starting time of the game
    private static int endTime; // the ending time of the game
    private static int totalTime; // the elapsed time

    /**
     * This method initialize the fields
     * */
    public MasterMindController(MasterMindModel theModel, MasterMindView theView, guessCircleColors theColors) {
        this.theModel = theModel;
        this.theView = theView;
        this.theColors = theColors;

        pegs = 6; // initialize the number of pegs as 6
        positions = 4; // initialize the number of positions as 4
        totalClickCount = new int[]{0, 0, 0, 0}; // a list of total click count
        guessNumbers = new int[]{0, 0, 0, 0}; // the current guess code in the form of a list
        guessColor = new Color[4]; // the guess colors

        modeRadioButton(theModel, theView); // add the radio button for mode

        roleRadioButton(theModel, theView); // add the radio button for role

        startButton(theModel, theView); // add the start button

        colorsButtons(theView, theColors); // add the color buttons

        guessButton(theModel, theView); // add the guess buttons
    }

    /**
     *  This method is for GUESS BUTTON
     * @param  theModel the model for mastermind GUI
     * @param theView the view for MasterMindView GUI
     */
    private void guessButton(MasterMindModel theModel, MasterMindView theView) {

        // Initiate the guess button count as 0.
        guessButtonCount = new int[]{0};
        // set action on the click of guess button
        theView.getGuessButton().setOnMouseClicked(event -> {
            try {
                //Keep track of the Guesses in number
                for (int i = 0; i < positions; i++) {
                    // if any circle is not clicked, throw Invalid Input error
                    if (totalClickCount[i] == 0) {
                        Guess[i] = 0;
                        throw new InvalidInputException("No Input");
                    } else {
                        // increase one on every click
                        Guess[i] = guessNumbers[i] + 1;
                    }
                }

                //Paste it on the History Board
                for (int j = 0; j < positions; j++) {
                    if (guessButtonCount[0] < 12) {
                        Circle historyCircle = theView.getHistoryCircle(guessButtonCount[0] % 12, j);
                        // set the color of the circle according to the total click number
                        historyCircle.setFill(guessColor[j]);
                    }
                }
                guessButtonCount[0]++; // everytime increment the guess button count by one.

                //Set it up to connect to the original code
                if (theModel.getGuessesLeft() > 0) {
                    theModel.setGuessesLeft(theModel.getGuessesLeft() - 1);
                }

                theModel.setGuessCode(getGuess()); // update the guess code to the model
                theModel.setScore(theCodeMaker.evaluateGuess(secretCode, getGuess(), theModel.getMode())); // update score to the model
                theView.setGuessesLeftNum(theModel.getGuessesLeft()); // pass in count guess left
                theView.setScoreResult(theModel.getScore()); // pass in the score

                System.out.println(theModel.getScore());
                // if the player guess the code, the message " You won!" displays
                if (theModel.getScore().equals("****") == true) {
                    theView.setWinLose("You won!");

                    // end the timer and display how long they played
                    endTime = LocalTime.now().getMinute();
                    totalTime = endTime - startTime;
                    theView.setTime("It took " + totalTime + " minutes to guess the code");
                }
                // the the player doesn't guess the code and there's no guess left, the message "You lost!" displays
                else if (theModel.getGuessesLeft() == 0) {
                    theView.setWinLose("You lost!");

                    // end the timer and display how long they played
                    endTime = LocalTime.now().getMinute();
                    totalTime = endTime - startTime;
                    theView.setTime("You played for " + totalTime + " minutes");
                }

                if (theModel.getRole().equals("H")){
                    System.out.println("aaajjj");
                    System.out.println(theHost.receiveResponse(theHost.clientSocket));
                }

                if (theModel.getRole().equals("J")){
                    System.out.println("aaaaa");
                    theJoin.transmitMessage(theJoin.hostSocket,"hello");
                }
            } catch (InvalidInputException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error!");
                alert.setHeaderText("No input specified!");
                alert.setContentText("Zero guesses were made");
                alert.show();
            }
        });
    }

    /**
     * This method is for the COLOR BUTTONS
     * @param theView the view for MasterMindView GUI
     */
    private void colorsButtons(MasterMindView theView, guessCircleColors theColors) {
        for (int i = 0; i < positions; i++) {
            c = theView.getGuessCircles().get(i);
            Circle finalC = c;
            int finalI = i;
            // set action on every mouse click on the color circles
            c.setOnMouseClicked(event -> {
                guessNumbers[finalI] = totalClickCount[finalI] % pegs;
                finalC.setFill(theColors.getColor(guessNumbers[finalI]));
                totalClickCount[finalI]++; // increment by one on every click
                guessColor[finalI] = theColors.getColor(guessNumbers[finalI]); // set guess color according to the click count
            });
            totalClickCount[i] = 0; // set the click count back to zero
        }
    }

    /**
     *  This method is for the START BUTTON
     *  @param theModel the model for mastermind GUI
     *  @param theView the view for MasterMindView GUI
     */
    private void startButton(MasterMindModel theModel, MasterMindView theView) {
        theView.getStartButton().setOnMouseClicked(event -> {
            resetView(theView); //reset the game board
            //If Multi + Host, create a Host
            if (theModel.getMode() == "M" && theModel.getRole() == "H") {
                theHost = new Host();
                theHost.createHost();
            }
            //Check if the Host existed
            if (theModel.getMode() == "M" && theModel.getRole() == "J") {
                try {
                    theJoin = new Join();
                    theJoin.connectSocket();
                    theJoin.receiveResponse(theJoin.hostSocket);
                    //theJoin.hostSocket.close();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Role Error!");
                    alert.setHeaderText("No Host yet");
                    alert.setContentText("There isn't a HOST yet. Single Player Mode now!");
                    alert.show();
                }
            }

            //Create a secret code for both mode
            if (theModel.getMode() != "") {
                theCodeMaker = new codeMaker();
                secretCode = theCodeMaker.generateSecretCode(pegs, positions);
                System.out.println(secretCode);
            }
        });

        // start the timer
        startTime = LocalTime.now().getMinute();
    }

    /**
     * This method is to reset the vew after the player click the start button
     * @param theView the view for MasterMindView GUI
     * */
    private void resetView(MasterMindView theView) {
        totalClickCount = new int[]{0, 0, 0, 0}; // set total click for all four circles to 0
        guessNumbers = new int[]{0, 0, 0, 0}; // set the guess number to 0
        guessColor = new Color[4]; // reset the guess colors
        guessButtonCount = new int[]{0}; // reset the guess button count to 0

        theModel.setGuessesLeft(12); // set count to 12
        theView.setGuessesLeftNum(12); // set current guess left to 12
        theView.setScoreResult(""); // set current score to empty

        // set the color buttons to gray
        for (int i = 0; i < positions; i++) {
            c = theView.getGuessCircles().get(i);
            Circle finalC = c;
            finalC.setFill(Color.GRAY);
        }

        // set all circles in the history board to transparent
        Circle historyCircle;
        for (int j = 0; j < positions; j++) {
            for (int i = 0; i < 12; i++) {
                historyCircle = theView.getHistoryCircle(i, j);
                historyCircle.setFill(Color.TRANSPARENT);
            }
        }
    }

    /**
     * This method is for ROLE RADIO BUTTONS
     * @param  theModel the model for mastermind GUI
     * @param theView the view for MasterMindView GUI
     */
    private void roleRadioButton(MasterMindModel theModel, MasterMindView theView) {
        // if the radio button is selected, get user data of the role and set the role
        theView.getRoleGroup().selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (theView.getModeGroup().getSelectedToggle() != null) {
                theModel.setRole(theView.getRoleGroup().getSelectedToggle().getUserData().toString());
            }
        });
    }

    /**
     * This method is for MODE RADIO BUTTONS
     * @param  theModel the model for mastermind GUI
     * @param theView the view for MasterMindView GUI
     */
    private void modeRadioButton(MasterMindModel theModel, MasterMindView theView) {
        // if the radio button is selected, get user data of the mode and set the mode
        theView.getModeGroup().selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (theView.getModeGroup().getSelectedToggle() != null) {
                theModel.setMode(theView.getModeGroup().getSelectedToggle().getUserData().toString());
            }
        });
    }

    /**
     * This method is a getter for the Code Breaker as a String
     * @return guessCode - CodeBreaker
     */
    public int getGuess() {
        String guessCode = "";
        //add each guess number to the guess code string
        for (int a : Guess) {
            guessCode += a;
        }
        // return the integer form of the guess code
        return Integer.parseInt(guessCode);
    }
}
