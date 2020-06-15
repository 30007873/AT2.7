package client;

import server.Server;

import java.net.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * @author : Jai Ananda
 * @version : 1.0
 * @description: This class connects to the server socket using localhost and port 8080,
 * reads the file to the buffer from the downloads folder of the project home directory
 * and then writes it out to the server using the established socket connection.
 */
public class Client {

    private static Logger log = Logger.getLogger(Server.class.getName());

    /* This is the file path */
    private static final String FILE_PATH = "downloads\\Movies.csv";
    /* This is the localhost IP */
    private static final String LOCALHOST = "127.0.0.1";
    /* This is the client port */
    private static final int CLIENT_PORT = 8080;

    /**
     * This method accepts nothing and returns socket
     *
     * @return
     * @throws Exception
     */
    public Socket createSocket(String host, int port) throws Exception {
        // create socket using localhost on port 8080
        Socket socket = new Socket(host, port);
        log.info("Connecting...");
        return socket;
    }

    /**
     * This method accepts socket and filePath as arguments and returns boolean
     *
     * @param socket
     * @param filePath
     * @return
     */
    public boolean transferFile(Socket socket, String filePath) {
        try {
            // initialize bytes read to buffer
            int bytesReadToBuffer = 0;
            // initialize bytes to write
            int bytesToWrite = 0;
            // create a client object
            /* save file on server */
            // initialize a byte array with some large value
            byte[] byteArray = new byte[10000000];
            // get the socket input stream
            InputStream inputStream = socket.getInputStream();
            // create a file output file stream using the file from the project home folder's download folder
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            // create buffered output stream using file output stream
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            // read byte array to bytes to write
            bytesToWrite = inputStream.read(byteArray, 0, byteArray.length);
            // loop until bytes read to buffer is -1
            do {
                // read byte array to the buffer
                bytesReadToBuffer = inputStream.read(byteArray, bytesToWrite, (byteArray.length - bytesToWrite));
                // when buffer has data then add buffer content size to bytes to write
                if (bytesReadToBuffer >= 0) bytesToWrite += bytesReadToBuffer;
            } while (bytesReadToBuffer > -1);
            // write to output stream buffer
            bufferedOutputStream.write(byteArray, 0, bytesToWrite);
            // flush from buffer to the file
            bufferedOutputStream.flush();
            // close the output stream buffer
            bufferedOutputStream.close();
            // close the socket
            socket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method accepts String array as arguments and returns nothing
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        // create a client socket
        Socket socket = client.createSocket(LOCALHOST, CLIENT_PORT);
        // transfer file
        boolean status = client.transferFile(socket, FILE_PATH);
        log.info("File transfer status: " + (status? "SUCCESS" : "FAILED"));
    }
}