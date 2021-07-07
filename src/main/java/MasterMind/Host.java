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
 * Class: Host
 *
 * Description:
 *
 * ****************************************
 */
package MasterMind;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
    // port number used for connecting sockets
    private static final int portNum = 17382;

    // ServerSocket variable used to open the initial ServerSocket object
    public static ServerSocket serverSocket;

    // client's socket object
    public static Socket clientSocket;

    // Win/Lost status
    public String status = "";

    public void createHost() {
        try {
            serverSocket = new ServerSocket(portNum);
            clientSocket = serverSocket.accept();
            // Send it to the clientSocket for the Code Breaker to play

            transmitMessage(clientSocket, status);

            // Close it, so the HOST can do something else
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Transmit message to the server as a {@link String} encoded into bytes
     *
     * @param socket  is the socket we're using to send to
     * @param message the message to send
     */
    public static void transmitMessage(Socket socket, String message) throws IOException {
        // Send as a sequence of bytes by using the getBytes
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        out.println(message);
    }

    /**
     * A method receives a response from the server.
     * In this case, recieve the Secret code from the HOST for the Code Breaker to play
     *
     * @param socket is the socket we're receiving from
     * @return the String that was received
     */
    public static String receiveResponse(Socket socket) {
        String sBuffer = "";
        try {
        // Set an infinite time out until we receive some data...
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sBuffer = in.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sBuffer;
    }
}