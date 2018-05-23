echo "Please enter the FTP server's port (e.g. 2021):"
read port
echo "Please enter the scenario (e.g. 2.1):"
read scen

d1="target/FTPClient-1.0-jar-with-dependencies.jar"

cd ../
java -jar "$d1" $port $scen