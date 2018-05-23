#!/usr/bin/env bash

echo "Cleaning existing target folders..."
rm -r */target/
rm -r */*/target/
rm -r */target2/
rm -r */*/target2/
echo "DONE."

echo
echo "The following builds may take a few minutes the first time round,"
echo "given that all necessary libraries need to be downloaded by Maven."
echo

echo -n "Building TRE_Library module..."
cd TRE_Library
mvn -q clean compile assembly:single
echo "DONE."

echo -n "Building Larva_Generators module..."
cd ../Larva_Generators
mvn -q clean compile assembly:single
echo "DONE."

echo -n "Building Testing module..."
cd ../Testing
mvn -q clean compile assembly:single
echo "DONE."

echo -n "Building Evaluation/FTPServer module..."
cd ../Evaluation/FTPServer
mvn -q clean compile package assembly:single
echo "DONE."

echo -n "Building Evaluation/FTPClient module..."
cd ../FTPClient
mvn -q clean compile assembly:single
echo "DONE."