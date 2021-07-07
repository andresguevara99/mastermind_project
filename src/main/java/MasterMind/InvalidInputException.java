/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Khanh Pham & Jessica Zhao
 *
 * Section: 11:30 AM
 * Date: 11/7/2020
 * Time: 8:46 PM
 *
 * Project: csci205FinalProject
 * Package: MasterMind
 * Class: InvalidInputException
 *
 * Description:
 * Create a extended Exception
 * ****************************************
 */
package MasterMind;

/**
 * A method for any invalid inputs
 */
class InvalidInputException extends Exception{
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }
}