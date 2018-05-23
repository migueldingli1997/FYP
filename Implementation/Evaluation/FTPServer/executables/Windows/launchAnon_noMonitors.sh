echo "Please enter the FTP server's port (e.g. 2021):"
read port

d1="target/FTPServer-1.0-jar-with-dependencies.jar"
d2="target/FTPServer-1.0-tests.jar"

cd ../../
java -cp "$d1;$d2" com.guichaguri.minimalftp.AnonServerRunner $port