package com.guichaguri.minimalftp;

import com.guichaguri.minimalftp.custom.UserbaseAuthenticator;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Guilherme Chaguri
 */
public class LoginServerRunner {

    public static void main(String[] args) throws IOException {

        // Set server port
        final int PORT;
        if (args.length == 1) {
            PORT = Integer.parseInt(args[0]);
        } else {
            PORT = -1;
            System.out.println("Expected: <PORT>");
            System.exit(0);
        }

        // Create the FTP server
        FTPServer server = new FTPServer();

        // Create our custom authenticator
        UserbaseAuthenticator auth = new UserbaseAuthenticator();

        // Register a few users
        auth.registerUser("john", "1234");
        auth.registerUser("alex", "abcd123");
        auth.registerUser("hannah", "98765");

        // Set our custom authenticator
        server.setAuthenticator(auth);

        // Changes the timeout to 10 minutes
        server.setTimeout(10 * 60 * 1000); // 10 minutes

        // Start listening synchronously
        System.out.println("LoginServer will now be started on port " + PORT + ".");
        server.listenSync(InetAddress.getByName("localhost"), PORT);
    }
}
