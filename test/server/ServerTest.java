package server;

import client.Client;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.net.*;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jai Ananda
 * @version 1.0
 * @description This class consists of all the test cases for the type Server
 */
public class ServerTest {

    private static Logger log = Logger.getLogger(Server.class.getName());

    /* This is the localhost IP */
    private static final String LOCALHOST = "127.0.0.1";
    /* This is the client port */
    private static final int CLIENT_PORT = 8080;
    /* This is the server port */
    private static final int SERVER_PORT = 8080;
    /* This is the file path */
    private static final String SERVER_FILE_PATH = "uploads\\test.csv";
    /* This is the file path */
    private static final String CLIENT_FILE_PATH = "downloads\\test.csv";
    /* This is the rule set for the test suite */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * This method is used to include setup method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    @Before
    public void setup() {
        log.info("Setting up...");
    }

    /**
     * This method is used to include tear down method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    @After
    public void tearDown() {
        log.info("Tearing down...");
    }

    /**
     * This method is used to include before class method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    @BeforeClass
    public static void before() {
        log.info("Running JUnit test cases from class: " + ServerTest.class);
    }

    /**
     * This method is used to include after class method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    @AfterClass
    public static void after() {
        log.info("Testing class " + ServerTest.class + " has completed.");
    }

    /**
     * This method is the reset method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    public void reset() {
        tearDown();
        setup();
    }

    /**
     * This method is used to test create server socket, accepts nothing, returns nothing and throws an exception
     *
     * @throws Exception
     */
    @Test
    public void createServerSocketTest() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        if(serverSocket != null){
            assertEquals(SERVER_PORT, serverSocket.getLocalPort());
        }
        reset();
    }

    /**
     * This method is used to test acceptAll, accepts nothing, returns nothing and throws an exception
     *
     * @throws Exception
     */
    @Test
    public void acceptAll() throws Exception {
        // create server
        Server server = new Server();
        // create server socket
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        // create client
        Client client = new Client();
        // create a client socket
        Socket socket = client.createSocket(LOCALHOST, CLIENT_PORT);
        // accept connections, files and store the file
        boolean serverAcceptStatus = server.acceptAll(serverSocket, SERVER_FILE_PATH);
        // asserts
        assertTrue(serverAcceptStatus);
        // transfer file
        boolean clientTransferStatus = client.transferFile(socket, CLIENT_FILE_PATH);
        log.info("File transfer status: " + (clientTransferStatus? "SUCCESS" : "FAILED"));
        reset();
    }
}
