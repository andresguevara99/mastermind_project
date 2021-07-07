/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Andres Guevara-Flores
 * Section: 11:30 AM
 * Date: 11/11/2020
 * Time: 1:22 PM
 *
 * Project: csci205FinalProject
 * Package: MasterMind
 * Class: guessCircleColors
 *
 * Description:
 *
 * ****************************************
 */
package MasterMind;

import javafx.scene.paint.Color;

public class guessCircleColors {
   Color[] colorList;


    public guessCircleColors() {
        this.colorList = new Color[]{Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN, Color.PINK, Color.PURPLE};
    }

    public Color getColor(int index) {
        return this.colorList[index];
    }
}