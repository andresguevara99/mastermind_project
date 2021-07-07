/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Khanh Pham
 * Pair with: (if applicable)
 * Section: 11:30 AM
 * Date: 11/30/2020
 * Time: 10:20 AM
 *
 * Project: csci205FinalProject
 * Package: MasterMind
 * Class: Join
 *
 * Description:
 *
 * ****************************************
 */
package MasterMind;

import java.io.*;
import java.net.Socket;

public class Join {
    // host's Socket object
    public static Socket hostSocket;
    // port number used for connecting sockets
    private static final int portNum = 17382;

    /**
     * A method connect a socket to the HOST
     * @throws IOException connectSocket might throw this if there is no Host
     */
    public static void connectSocket() throws IOException {
        hostSocket = new Socket("127.0.0.1", portNum);
    }

    /**
     * A method receives a response from the server.
     * In this case, recieve the Secret code from the HOST for the Code Breaker to play
     *
     * @param socket is the socket we're receiving from
     * @return the String that was received
     */
    public static String receiveResponse(Socket socket) throws IOException {
        String sBuffer;
        // Set an infinite time out until we receive some data...
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sBuffer = in.readLine();
        return sBuffer;

    }

    /**
     * Transmit message to the server as a {@link String} encoded into bytes
     *
     * @param socket  is the socket we're using to send to
     * @param message the message to send
     */
    public static void transmitMessage(Socket socket, String message){
        // Send as a sequence of bytes by using the getBytes
        try {
            PrintWriter out = null;
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}