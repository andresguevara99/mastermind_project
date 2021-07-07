package MasterMind;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MasterMindMain extends Application {
    private MasterMindView theView;  // the GUI View
    private MasterMindModel theModel;  // the GUI Model
    private guessCircleColors theColors; // colors of the circles
    private MasterMindController theController;  // the GUI controller

    /**
     * The init method to initialize and throws Exception
     * */
    @Override
    public void init() throws Exception {
        super.init();
        theModel = new MasterMindModel();  // set MasterMindModel object
        theColors = new guessCircleColors(); // guessCircleColors object
        theView = new MasterMindView();  // set MasterMindView object with theModel, theColors parameters
        theController = new MasterMindController(theModel, theView, theColors); // set MasterMindController with theModel, theView, theColors as parameters
    }

    /**
     * The main method that launch the argument
     * @param args the String[] format argument
     * */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *  This method overrides the start method with IOException
     *
     * */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(theView.getRoot(), 700, 800); // creat the GUI scene
        primaryStage.setTitle("MasterMind Game"); // set the tile of the game board
        primaryStage.setScene(scene); // set the scene
        primaryStage.sizeToScene(); // set the size of hte scene
        primaryStage.show(); // display the scene
    }
}
