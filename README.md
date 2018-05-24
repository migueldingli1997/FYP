# Runtime Verification of Timed Regular Expressions in Larva

## Table of Contents  
1 - Prerequisites  
2 - Cloning and Building  
3 - Executables

## 1 - Prerequisites
To compile and run the project, the two main necessary components are AspectJ and Maven. Although the profiling tool Java VisualVM that was used in the case study is packaged with any recent Java Development Kit (JDK) installation, instructions for installing the alternative standalone VisualVM will also be provided.

### 1.1 - Assumptions
It is assumed that the system has an appropriate JDK and Git installed. For Windows system, it will be assumed that a bash terminal is installed, such as Git Bash which comes packaged with the Windows Git installation. Note that although instructions are provided for Windows systems, the whole process is much more straightforward using a Linux system.

### 1.2 - Installations
On Windows:
* __AspectJ__ can be downloaded from [here](http://www.eclipse.org/aspectj/downloads.php) (Version 1.8.13) and installation instructions can be found [here](http://www.eclipse.org/aspectj/downloads.php#install).
* __Maven__ can be downloaded from [here](https://maven.apache.org/download.cgi) (Binary zip archive) and installation instructions can be found [here](http://maven.apache.org/install.html).
* __Java VisualVM__ startup and usage instructions can be found [here](https://docs.oracle.com/javase/8/docs/technotes/guides/visualvm/intro.html).
* __Standalone VisualVM__ can be downloaded from [here](https://visualvm.github.io/download.html).

On Linux:
```sh
sudo apt-get update             # Update the package lists
sudo apt-get install aspectj    # AspectJ
sudo apt-get install maven      # Maven
sudo apt-get install visualvm   # Standalone VisualVM
```

In any case, the VisualVM Tracer plugin, used throughout the evaluation to measure memory overheads, can be installed by following the instructions [here](https://visualvm.github.io/plugins.html).

### 1.3 - Required Commands
The following is a list of commands which should be recognized by the bash terminal after the above installations:
1. ```mvn```: Maven
2. ```ajc```: AspectJ compiler 
3. ```aj5```: AspectJ runner

## 2 - Cloning and Building
To download the project, unless this was already obtained from the CD, the following command can be entered in the command line (Windows) or terminal (Linux):

```sh
git clone https://github.com/migueldingli1997/FYP.git
```

Once the cloning (or copying from the CD) is done, the result is an FYP folder with three sub-folders:
* __Copy of Larva Compiler__: contains the Larva compiler used throughout this project. A fork of the original Larva repository containing the same version of the Larva compiler can be found [here](https://github.com/migueldingli1997/larva-rv-tool).
* __Copy of Larva Tools__: contains the collection of tools that extend the functionality of Larva DATEs, as presented in the report. Copies of these tools are found in both the testing module and FTP modules, presented below.
* __FYP__: contains the remainder of the FYP implementation consisting of four modules:
  * __Evaluation__: contains the source code of the [MinimalFTP](https://github.com/Guichaguri/MinimalFTP) FTP server use case and the evaluation experiments which use the [Apache Commons Net](https://commons.apache.org/proper/commons-net/) library.
  * __Larva Generators__: contains the source code of the Larva script generators.
  * __Testing__: contains the source code of the [FiTS](https://drive.google.com/open?id=150xMnws6ehzU-lU097fVv8xgto0ckX2B) system, which was used as a platform for monitoring tests, and the actual runnable test classes.
  * __TRE Library__: contains the source code and runnable unit tests of the TREs, TAs, TRE lexer and parser, and any other utilities.

## 3 - Executables
The execution of bash scripts may require execution permission to be granted for the bash script file. To provide this permission for a bash script 'script.sh', the ``chmod +x script.sh`` command is entered in a terminal.

### 3.1 - Building the Project
To build the project, navigate to the ``Implementation/`` folder and execute the ``build.sh`` bash script (``./build.sh``) from a bash terminal. The script will use Maven to build all of the four modules. This step may take a few minutes to finish, especially the first time round, but is __very important__ given that it generates _.jar_ files that are used as dependencies throughout the project.

### 3.2 - Optional: Importing the Project in an IDE
The use of Maven to structure the project means that it can be easily imported into an any Java IDE that supports maven, such as IntelliJ IDEA. However, no IDE is necessary to run the implemented tests, the evaluation experiments, and the implemented Larva script generator.

### 3.3 - Running the Tests
To run the TRE Library test:
1. Navigate to the ``Implementation/FTP_Library/`` folder.
2. Execute ``runAllTests.sh``.

To run the monitoring tests:
1. Navigate to the ``Implementation/Testing/executables`` folder.
2. Execute ``runAllTests_x.sh``, according to the OS (``x``).

Note that the monitoring tests use a collection of precompiled Larva scripts, found in ``resources/precompiledScripts/generatedLarvaFiles/``, which are based on the Larva scripts found in ``resources/precompiledScripts/generatedScripts/``. These scripts can be [generated automatically](https://github.com/migueldingli1997/FYP#35-generating-and-compiling-larva-scripts), but without the changes that were necessary to make them usable, i.e. setting up the parametrised events correctly in the 'Params' tests.

### 3.4 - Running the Evaluation Experiments
Running the evaluation experiments is a two-part job, which involves first starting the FTP server, and then running one of the FTP client scenario methods. In both the server and client runners, the bash scripts ask for a port. The tried and tested port is '2021'. This must be common for both the server and client for an experiment to work.

To run the FTP server, navigate to the ``Evaluation/FTPServer/executables/x/`` folder, according to the OS (``x``). This folder contains six FTP server launchers, of the form ``launchTYPE_APPROACH.sh``.
*  The TYPE indicates the FTP server runner, whether the AnonServerRunner or the LoginServerRunner (as discussed in the FYP) that will be used.
*  The APPROACH indicates whether the monitorless (noMonitors), timed regular expression derivatives (RE), or timed automata (TA) approach will be used.  The monitorless approach simply starts the server, whereas the RE and TA approaches ask for the FTP server property that is being verified (1-5), so that they weave the appropriate monitors into the FTP server.

To run an FTP client scenario method, navigate to the ``Evaluation/FTPClient/executables/`` folder and execute the ``runScenaro.sh`` script, which asks for the scenario method (1.1 to 5.2) that will be run.

At this point, the VisualVM profiling tool can be used to analyse the CPU and memory usages of the FTP server.

### 3.5 - Extra Scenario Methods
The ``runScenario.sh`` script in ``Evaluation/FTPClient/executables`` also recognizes the scenarios 3.3, 3.4, 5.3, and 5.4. These scenarios are meant to show that the properties 3 and 5 specified for the FTP server are violated when enough file transfers are performed. Scenarios 3.3 and 5.3 perform a low enough number of file transfers such that they do not violate the properties, whilst scenarios 3.4 and 5.4 perform just enough file transfers to violate the properties.

The output from Larva indicating that a property has been violated can generally found in a ``larvaRuntimeOutput`` folder. In this case, this folder is found at ``Evaluation/FTPServer/larvaRuntimeOutput/``.

### 3.5 - Generating and Compiling Larva Scripts
To generate Larva scripts, navigate to the ``Larva_Generators/executables/`` folder. Four options are available from this point:
1. To generate a Larva script based on a custom input, execute ``generateAnyScript.sh``.
2. To generate the Larva scripts used in the evaluation experiments:
   1. Navigate to the ``For Case Study/`` folder.
   2. Execute ``generateScripts_x.sh``, according to the OS (``x``).
3. To generate the Larva scripts used in the monitoring tests:
   1. Navigate to the ``For Testing/`` folder. 
   2. Execute ``generateScripts_x.sh``, according to the OS (``x``). 
4. To generate Larva scripts based on some included example inputs:
   1. Navigate to the ``For Examples/`` folder. 
   2. Execute ``generateScripts_x.sh``, according to the OS (``x``).

For points 2-4, the resultant ``generatedScripts/`` folder can be moved to ``Copy of Larva Compiler/`` to be compiled automatically by executing the respective bash script. This creates a ``generatedLarvaFiles/`` folder containing the compiled scripts.

A bash script for compiling any general Larva script (``compileAnyScript.sh``) is also included. This script asks for the script name and output directory, after which it compiles the script and places the resultant folders (``aspects/`` and ``larva/``) in the specified output directory. It is not recommended to use ``out/`` as the output directory, given that this holds the compiler's _.class_ files. 
