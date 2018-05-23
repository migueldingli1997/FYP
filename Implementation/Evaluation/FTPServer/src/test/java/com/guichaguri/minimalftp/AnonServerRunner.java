package com.guichaguri.minimalftp;

import com.guichaguri.minimalftp.impl.NativeFileSystem;
import com.guichaguri.minimalftp.impl.NoOpAuthenticator;

import java.io.File;
import java.io.IOException;

/**
 * @author Guilherme Chaguri
 */
public class AnonServerRunner {

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

        // Uses the fileStorage folder in the current directory as the root
        File root = new File(System.getProperty("user.dir"), "fileStorage");

        // Creates a native file system
        NativeFileSystem fs = new NativeFileSystem(root);

        // Creates a noop authenticator
        NoOpAuthenticator auth = new NoOpAuthenticator(fs);

        // Creates the server with the authenticator
        FTPServer server = new FTPServer(auth);

        // Changes the timeout to 10 minutes
        server.setTimeout(10 * 60 * 1000); // 10 minutes

        // Start listening synchronously
        System.out.println("AnonServer will now be started on port " + PORT + ".");
        server.listenSync(PORT);
    }
}
