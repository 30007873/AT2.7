package client;

import org.junit.*;
import org.junit.rules.ExpectedException;
import server.Server;

import java.net.*;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

/**
 * @author Jai Ananda
 * @version 1.0
 * @description: This class consists of all the test cases for the type Client
 */
public class ClientTest {

    private static Logger log = Logger.getLogger(Server.class.getName());

    /* This is the file path */
    private static final String SERVER_FILE_PATH = "uploads\\test.csv";
    /* This is the file path */
    private static final String CLIENT_FILE_PATH = "downloads\\test.csv";
    /* This is the server port */
    private static final int SERVER_PORT = 8080;
    /* This is the localhost IP */
    private static final String LOCALHOST = "127.0.0.1";
    /* This is the client port */
    private static final int CLIENT_PORT = 8080;
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
        log.info("Running JUnit test cases from class: " + ClientTest.class);

    }

    /**
     * This method is used to include after class method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    @AfterClass
    public static void after() {
        log.info("Testing class " + ClientTest.class + " has completed.");

    }

    /**
     * This method is the reset method for the test suite, accepts nothing, returns nothing and does not throw any exception
     */
    public void reset() {
        tearDown();
        setup();
    }

    /**
     * This method is used to test creation of socket, accepts nothing, returns nothing and throws exception
     *
     * @throws Exception
     */
    @Test
    public void createSocketTest1() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        Client client = new Client();
        Socket socket = client.createSocket(LOCALHOST, CLIENT_PORT);
        if(socket != null) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
        reset();
    }

    /**
     * This method is used to test creation of socket, accepts nothing, returns nothing and throws BindException
     *
     * @throws Exception
     */
    @Test(expected = BindException.class)
    public void createSocketTest2() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        Client client = new Client();
        Socket socket = client.createSocket("", 0); // BindException is thrown here
        reset();
    }

    /**
     * This method is used to test creation of socket, accepts nothing, returns nothing and throws ConnectException
     *
     * @throws Exception
     */
    @Test(expected = ConnectException.class)
    public void createSocketTest3() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        Client client = new Client();
        Socket socket = client.createSocket(LOCALHOST, 8081); // Connect Exception is thrown here
        reset();
    }

    /**
     * This method is used to test creation of socket, accepts nothing, returns nothing and throws UnknownHostException
     *
     * @throws Exception
     */
    @Test(expected = UnknownHostException.class)
    public void createSocketTest4() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        Client client = new Client();
        Socket socket = client.createSocket("xxx.x.x.x", 8080); // UnknownHost Exception is thrown here
        reset();
    }

    /**
     * This method is used to test creation of socket, accepts nothing, returns nothing and throws an exception
     *
     * @throws Exception
     */
    @Test
    public void createSocketTest5() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(9000);
        Client client = new Client();
        Socket socket = client.createSocket(LOCALHOST, 8080);
        if(socket != null) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
        boolean status = server.acceptAll(serverSocket, SERVER_FILE_PATH);
        assertTrue(status);
        reset();
    }

    /**
     * This method is used to test file transfer, accepts nothing, returns nothing and throws an exception
     *
     * @throws Exception
     */
    @Test
    public void transferFileTest() throws Exception {
        Server server = new Server();
        ServerSocket serverSocket = server.createServerSocket(SERVER_PORT);
        Client client = new Client();
        // create a client socket
        Socket socket = client.createSocket(LOCALHOST, CLIENT_PORT);
        // accept connections, files and store the file
        boolean serverAcceptStatus = server.acceptAll(serverSocket, SERVER_FILE_PATH);
        log.info("Server accept status: " + (serverAcceptStatus? "SUCCESS" : "FAILED"));
        // transfer file
        boolean clientTransferStatus = client.transferFile(socket, CLIENT_FILE_PATH);
        assertTrue(clientTransferStatus);
        reset();
    }
}