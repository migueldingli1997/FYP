package examples.ftp;

import java.io.File;
import java.net.UnknownHostException;

public class FTPClientRunner {

    private static final String ARG_FILE_LOCAL = new File("fileStorage", "EmptyFile.txt").toString();
    private static final String ARG_FILE_REMOTE = "EmptyFile.txt";
    private static final String ARG_ANONYMOUS = "-A";
    private static final String ARG_USERNAME = "john";
    private static final String ARG_PASSWORD = "1234";

    private static final boolean ANON = true;
    private static final boolean LOGIN = !ANON;

    private static String HOSTNAME;
    private static String SCENARIO;

    public static void main(String args[]) throws Exception {

        if (args.length == 2) {
            HOSTNAME = "localhost:" + args[0];
            SCENARIO = args[1];
        } else {
            System.out.println("Expected: <PORT> <SCENARIO>");
            System.exit(0);
        }

        switch (SCENARIO) {

            //-----------------------------------------------------------------------

            // Transfer-intensive.
            // These should only be used with AnonServer.
            case "1.1":
                performScenario(1, ANON, 1000);
                break;
            case "1.2":
                performScenario(1, ANON, 2000);
                break;

            // User-intensive.
            // These should only be used with LoginServer.
            case "2.1":
                performScenario(1000, LOGIN, 0);
                break;
            case "2.2":
                performScenario(2000, LOGIN, 0);
                break;

            // Balanced users and transfers.
            // These should only be used with AnonServer.
            case "3.1":
                performScenario(500, ANON, 1);
                break;
            case "3.2":
                performScenario(1000, ANON, 1);
                break;

            // Balanced users and transfers.
            // These should only be used with LoginServer.
            case "4.1":
                performScenario(500, LOGIN, 1);
                break;
            case "4.2":
                performScenario(1000, LOGIN, 1);
                break;

            // Transfer-intensive.
            // These should only be used with AnonServer.
            case "5.1":
                performScenario(1, ANON, 500);
                break;
            case "5.2":
                performScenario(1, ANON, 1000);
                break;

            //-----------------------------------------------------------------------

            // Exactly 5 and 6 transfers per user.
            // These should only be used with AnonServer.
            case "3.3":
                performScenario(3, ANON, 5);
                break;
            case "3.4":
                performScenario(3, ANON, 6);
                break;

            // Exactly 4 and 5 transfers per user.
            // These should only be used with AnonServer.
            case "5.3":
                performScenario(3, ANON, 4);
                break;
            case "5.4":
                performScenario(3, ANON, 5);
                break;

            //-----------------------------------------------------------------------

            // Selected scenario does not exist.
            default:
                System.out.println("The selected scenario does not exist.");
                System.exit(1);
        }
    }

    private static void performScenario(final int users, final boolean anon, final int transfers) throws UnknownHostException {

        // Set number of transfers
        FTPClientExample.NUMBER_OF_FILE_TRANSFERS = transfers;

        // Set arguments for FTPClientExample
        final String arguments[];
        if (anon) {
            arguments = new String[]{ARG_ANONYMOUS, HOSTNAME, ARG_FILE_REMOTE, ARG_FILE_LOCAL};
        } else {
            arguments = new String[]{HOSTNAME, ARG_USERNAME, ARG_PASSWORD, ARG_FILE_REMOTE, ARG_FILE_LOCAL};
        }

        // Run FTPClientExample with arguments
        for (int i = 0; i < users; i++) {
            FTPClientExample.main(arguments);
        }
    }
}
