/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package examples.ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * This is an example program demonstrating how to use the FTPClient class.
 * This program connects to an FTP server and retrieves the specified
 * file.  If the -s flag is used, it stores the local file at the FTP server.
 * Just so you can see what's happening, all reply strings are printed.
 * If the -b flag is used, a binary transfer is assumed (default is ASCII).
 * See below for further options.
 */
public final class FTPClientExample {

    static int NUMBER_OF_FILE_TRANSFERS = 0;
    static int SLEEP_BETWEEN_TRANSFERS = 0;

    private static final String USAGE =
            "Expected Parameters: [options] <hostname> <username> <password> [<remote file> [<local file>]]\n" +
                    "\t-A - anonymous login (omit username and password parameters)\n";

    public static void main(String[] args) throws UnknownHostException {

        int minParams = 5; // listings require 3 params
        String username = null;
        String password = null;

        int base;
        loop:
        for (base = 0; base < args.length; base++) {
            switch (args[base]) {
                case "-A":
                    username = "anonymous";
                    password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
                    break;
                default:
                    break loop;
            }
        }

        final int remain = args.length - base;
        if (username != null) {
            minParams -= 2;
        }
        if (remain < minParams) // server, user, pass, remote, local [protocol]
        {
            if (args.length > 0) {
                System.err.println("Actual Parameters: " + Arrays.toString(args));
            }
            System.err.println(USAGE);
            System.exit(1);
        }

        String server = args[base++];
        int port = 0;
        String parts[] = server.split(":");
        if (parts.length == 2) {
            server = parts[0];
            port = Integer.parseInt(parts[1]);
        }
        if (username == null) {
            username = args[base++];
            password = args[base++];
        }

        final String remote = args[base++];
        final String local = args[base];

        final FTPClient ftp;
        ftp = new FTPClient();
        ftp.setListHiddenFiles(false);

        // suppress login details
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

        final FTPClientConfig config;
        config = new FTPClientConfig();
        config.setUnparseableEntries(false);
        ftp.configure(config);

        try {
            int reply;
            if (port > 0) {
                ftp.connect(server, port);
            } else {
                ftp.connect(server);
            }
            System.out.println("Connected to " + server + " on " + (port > 0 ? port : ftp.getDefaultPort()));

            // After connection attempt, you should check the reply code to verify success.
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
            }
        } catch (IOException e) {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    // do nothing
                }
            }
            System.err.println("Could not connect to server.");
            e.printStackTrace();
        }

        __main:
        try {
            if (!ftp.login(username, password)) {
                ftp.logout();
                break __main;
            }

            ftp.setFileType(FTP.ASCII_FILE_TYPE); // In theory, not necessary as servers should default to ASCII.
            ftp.enterLocalPassiveMode(); // Use passive mode as default because of firewalls.
            ftp.setUseEPSVwithIPv4(false);

            for (int i = 0; i < NUMBER_OF_FILE_TRANSFERS; i++) {
                if (i % 2 == 0) {
                    // Upload
                    final InputStream input = new FileInputStream(local);
                    ftp.storeFile(remote, input);
                    input.close();
                } else {
                    // Download
                    final OutputStream output = new FileOutputStream(local);
                    ftp.retrieveFile(remote, output);
                    output.close();
                }
                try {
                    Thread.sleep(SLEEP_BETWEEN_TRANSFERS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ftp.noop(); // check that control connection is working OK
            ftp.logout();
        } catch (FTPConnectionClosedException e) {
            System.err.println("Server closed connection.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException f) {
                    // do nothing
                }
            }
        }
    } // end main
}
