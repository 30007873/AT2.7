package server;

import java.net.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * @author : Jai Ananda
 * @version : 1.0
 * @description: This server accepts the connection from the client using the port 8080,
 * reads the file to the buffer sent from the client socket via the socket and writes out
 * the file to the uploads folder of the project home directory.
 */
public class Server {

    private static Logger log = Logger.getLogger(Server.class.getName());

    /* This is the server port */
    private static final int SERVER_PORT = 8080;
    /* This is the file path */
    private static final String FILE_PATH = "uploads\\Movies.csv";

    /**
     * This method accepts port as arguments and returns server socket
     *
     * @param port
     * @return
     * @throws Exception
     */
    public ServerSocket createServerSocket(int port) throws Exception {
        // return object of server socket
        return new ServerSocket(8080);
    }

    /**
     * This method accepts server socket as arguments and returns boolean
     *
     * @param serverSocket
     * @return
     */
    public boolean acceptAll(ServerSocket serverSocket, String filePath) {
        try {
            // creating a loop to listen to any incoming client connections
            while (true) {
                log.info("Waiting for client connection.");
                // accept incoming client connection
                Socket socket = serverSocket.accept();
                log.info("Client connected.");
                /* transfer file */
                // create a file object using the file from project home folder's uploads directory
                File file = new File(filePath);
                // initialize byte array using file length
                byte[] byteArray = new byte[(int) file.length()];
                // initialize file input stream to read file from socket
                FileInputStream fileInputStream = new FileInputStream(file);
                // create a buffered input stream to read file to the buffer
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                // read file to the buffer
                bufferedInputStream.read(byteArray, 0, byteArray.length);
                // get socket output stream
                OutputStream outputStream = socket.getOutputStream();
                log.info("Transferring file to server.");
                // write byte array to the file
                outputStream.write(byteArray, 0, byteArray.length);
                // flush to write to the file
                outputStream.flush();
                // close the socket after writing
                socket.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method accepts array of String as arguments and returns nothing
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        // create server socket
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        // accept connections, files and store the file
        boolean status = server.acceptAll(serverSocket, FILE_PATH);
        log.info("File receive status: " + (status? "SUCCESS" : "FAILED"));
    }
}
