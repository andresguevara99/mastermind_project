/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Andres Guevara-Flores
 * Section: 11:30 AM
 * Date: 11/8/2020
 * Time: 12:50 PM
 *
 * Project: csci205FinalProject
 * Package: MasterMind
 * Class: mastermindView
 *
 * Description:
 *
 * ****************************************
 */
package MasterMind;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class MasterMindView {
    private final GridPane root; // the root pane
    private Button startButton; // "start game" button
    private Button guessButton; // "guess" button
    private Label guessesLeftNum; // the number of guess left
    private Label scoreResult; // the score result
    private ToggleGroup modeGroup; // the toggle group for mode
    private ToggleGroup roleGroup; // the toggle group for role
    private Label winLose; // the win/lose label for the winningBoard
    private Label time; // the time label for the winningBoard

    private ArrayList<Circle> guessCircles; // array list of guess circle
    private ArrayList<ArrayList> historyGuesses; // array list of guess history

    private final int positions; // total number of positions
    private final int guessCount; // number of guess count

    public MasterMindView() {
        positions = 4; // initialize positions as four
        guessCount = 12; // initialize guess count as 12

        //A grid pane of the entire GUI
        root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(5);
        root.setHgap(5);

        //Create the components for the left board of the grid pane
        VBox settingsBoard = settingsBoard();
        VBox mainBoard = mainBoard();
        VBox resultBoard = resultBoard();
        VBox winningBoard = winningBoard();
        VBox history = historyBoard();

        //Add all components to the left board
        VBox leftBoard = new VBox();
        leftBoard.setSpacing(10);
        leftBoard.setPadding(new Insets(10));
        leftBoard.getChildren().addAll(settingsBoard, mainBoard, resultBoard, winningBoard);

        //Add main, settings, and history board to root
        root.setConstraints(leftBoard, 10, 0);
        root.setConstraints(history, 30, 0);
        root.getChildren().addAll(leftBoard, history);
    }

    private VBox winningBoard() {
        VBox winningBoard = new VBox();
        winningBoard.setPadding(new Insets(10));
        winLose = new Label("");
        time = new Label("");
        winningBoard.getChildren().addAll(winLose, time);
        return winningBoard;
    }

    /**
     * This method is for the history board
     * */
    private VBox historyBoard() {
        Circle temporaryCircle;
        VBox history = new VBox();
        HBox rowHistory;

        //HISTORY LABEL
        Label historyLabel = new Label("History Board");
        historyLabel.setFont(new Font("Arial",30));
        historyLabel.setTextFill(Color.web("#0076a3"));
        historyLabel.setUnderline(true);
        history.getChildren().add(historyLabel);

        //INVISIBLE CIRCLES
        //Invisible board to save the previous guesses
        //12 rows, each row has 4 invisible circles
        ArrayList row;
        historyGuesses = new ArrayList<>(12);

        for (int j = 0; j < guessCount; j++) {
            row = new ArrayList();
            rowHistory = new HBox();
            rowHistory.setPadding(new Insets(5,20,5,20));
            for (int k = 0; k < positions; k++) {
                temporaryCircle = new Circle(20, Color.TRANSPARENT);
                DropShadow s = new DropShadow(5,5,5,Color.BLACK);
                temporaryCircle.setEffect(s);
                row.add(temporaryCircle);
                rowHistory.getChildren().add(temporaryCircle);
                rowHistory.setSpacing(10);
            }
            history.setSpacing(10);
            history.getChildren().add(rowHistory); // add row history to the history board
            historyGuesses.add(row);
        }

        //Add all features to the History Board
        history.setBorder(new Border(new BorderStroke(Color.web("#0076a3"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3,3,3,3))));
        history.setAlignment(Pos.CENTER);
        return history;
    }

    /**
     * This method is for the result board
     * */
    private VBox resultBoard() {
        VBox resultBoard = new VBox();
        resultBoard.setPadding(new Insets(10));

        //GUESS BUTTONS
        Label guessesLeftLabel = new Label("GUESSES LEFT:");
        guessesLeftLabel.setFont(new Font("Arial",20));

        guessesLeftNum = new Label("12");
        guessesLeftNum.setFont(new Font("Arial",20));

        //SCORE DISPLAY
        Label scoreLabel = new Label("SCORE:");
        scoreLabel.setFont(new Font("Arial",20));
        scoreLabel.setTextFill(Color.RED);

        scoreResult = new Label("");
        scoreResult.setFont(new Font("Arial", 20));

        //Add all features to resultBoard
        resultBoard.getChildren().addAll(guessesLeftLabel, guessesLeftNum, new Separator(), scoreLabel, scoreResult);
        resultBoard.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3,3,3,3))));
        resultBoard.setAlignment(Pos.CENTER);

        return resultBoard;
    }

    /**
     * This method is for the main board
     * */
    private VBox mainBoard() {
        VBox mainBoard = new VBox();
        mainBoard.setSpacing(20);
        HBox guessBoard = new HBox();
        guessBoard.setSpacing(10);

        //MAIN BOARD LABEL
        Label mainBoardLabel = new Label("Main Board");
        mainBoardLabel.setFont(new Font("Arial",30));
        mainBoardLabel.setTextFill(Color.web("#0076a3"));

        //GUESS BUTTON
        guessButton = new Button("GUESS");

        //GUESS CIRCLES
        // 1 row with 4 Gray Circle as default
        Circle temporaryCircle;
        guessCircles = new ArrayList<>();

        // set guess circles as gray
        for (int i = 0; i < positions; i++){
            temporaryCircle = new Circle(25, Color.GRAY);
            guessCircles.add(temporaryCircle);
            DropShadow s = new DropShadow(5,5,5,Color.BLACK);
            temporaryCircle.setEffect(s);
            guessBoard.getChildren().add(temporaryCircle);
        }

        //Adding all the components to the Main Board
        mainBoard.getChildren().addAll(mainBoardLabel, guessBoard, guessButton);
        mainBoard.setAlignment(Pos.CENTER);

        return mainBoard;
    }

    /**
     * This method is for the setting board
     * This board allows the player to set the mode and the role
     * @return settingBoard return the seeting board
     * */
    private VBox settingsBoard() {
        VBox settingsBoard = new VBox();
        settingsBoard.setPadding(new Insets(10));
        settingsBoard.setSpacing(10);

        //MODE RADIO BUTTONS
        Label modeLabel = new Label("CHOOSE MODE");
        modeLabel.setFont(new Font("Arial",20));

        modeGroup = new ToggleGroup();
        RadioButton rbSingle = new RadioButton("Single player");
        RadioButton rbMulti = new RadioButton("Multiplayer");

        rbSingle.setToggleGroup(modeGroup); // set toggle group for the radio button for single player mode
        rbSingle.setUserData("S"); // set user data as "S", which represents single player

        rbMulti.setToggleGroup(modeGroup); // set toggle group for the radio button for multiple player mode
        rbMulti.setUserData("M"); // set user data as "M", which represents multiple player

        //ROLE RADIO BUTTONS
        Label roleLabel = new Label("CHOOSE ROLE"); // set label choose role
        Label instruction = new Label("(If Multiplayer MODE)");
        roleLabel.setFont(new Font("Arial",20));

        roleGroup = new ToggleGroup();
        RadioButton rbHost = new RadioButton("HOST");
        rbHost.setToggleGroup(roleGroup); // set toggle group for the radio button for host
        rbHost.setUserData("H"); // set user data as "H", which represents host

        RadioButton rbJoin = new RadioButton("JOIN");
        rbJoin.setToggleGroup(roleGroup); // set toggle group for the radio button for join
        rbJoin.setUserData("J"); // set user data as "J", which represents join

        //START BUTTON
        startButton = new Button("Start Game!"); // create start button

        //Add all features to settingsBoard
        settingsBoard.getChildren().addAll(modeLabel, rbSingle, rbMulti, roleLabel, instruction, rbHost, rbJoin, startButton);
        settingsBoard.setAlignment(Pos.CENTER);

        return settingsBoard; //return the setting board
    }

    /**
     * This method set the number of guesses left
     * @para guessCountLeft number fo guess left
     * */
    public void setGuessesLeftNum(int guessCountLeft) {
        String count = "";
        count += guessCountLeft;
        guessesLeftNum.setText(count); // update the number of guesses left  the board
    }

    /**
     * This method set the score
     * @para score the score for the current guess
     * */
    public void setScoreResult(String score) {
        scoreResult.setText(score); // update the score to the board
    }

    public void setWinLose(String status) {
        winLose.setText(status);
    } // set the winning status

    public void setTime(String timePassed) {
        time.setText(timePassed);
    } // set the total time used

    public Circle getHistoryCircle(int row, int col) { return (Circle) historyGuesses.get(row).get(col); } // return the circle in the history guess

    public ArrayList<Circle> getGuessCircles() { return guessCircles; } // return the array list of guess circles

    public GridPane getRoot() { return root; } // return the root pane

    public ToggleGroup getModeGroup() { return modeGroup; } // return the mode group

    public ToggleGroup getRoleGroup() { return roleGroup; } // return the ole group

    public Button getStartButton() { return startButton; } // return the start button

    public Button getGuessButton(){ return guessButton; } // return the guess button
}