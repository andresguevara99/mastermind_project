package MasterMind;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class codeMakerTest {



    @Test
    void generateSecretCode() {

        codeMaker maker = new codeMaker();
        int code = maker.generateSecretCode(4,4);


        assertEquals(4, Integer.toString(code).length());

        int count = 0;
        for (int i = 0; i < Integer.toString(code).length() ; i++) {
            // if any are not within range of 1 - positions add count
            if (code/Math.pow(10,i)>0 && code/Math.pow(10,i)<7){
                count+=1;
            }
        }

        assertEquals(4, count);

    }

    @Test
    void evaluateGuess() {
        assertEquals("****",codeMaker.evaluateGuess(4444,4444,"S"));

        assertEquals("**--", codeMaker.evaluateGuess(4444,4423, "S"));

        assertEquals("++--",codeMaker.evaluateGuess(1234, 5621,"S"));


    }
}