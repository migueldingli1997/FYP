package larvaGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorRunner {

    private static final String OUTPUT_FOLDER_INPUT = "-out";
    private static final String APPROACH_SELECT_INPUT = "-app";
    private static final String ENABLE_TESTING_INPUT = "-test";
    private static final String ENABLE_DOT_FILES_INPUT = "-dot";
    private static final String ENABLE_PNG_FILES_INPUT = "-png";

    private static final String RE_APPROACH = "1";
    private static final String TA_APPROACH = "2";
    private static final String DEFAULT_APPROACH = RE_APPROACH;

    private static final String INVALID_NUMBER_OF_INPUTS = "" +
            "No inputs were read; at least one input is required.\n" +
            "\tA valid input is either a sequence of valid regular expressions or\n" +
            "\tan input file containing the sequence of valid regular expressions.\n\n" +
            "Optional arguments: \n" +
            "\t\'" + OUTPUT_FOLDER_INPUT + "\' <OUTPUT_FOLDER> to specify the script(s) output folder.\n" +
            "\t\'" + APPROACH_SELECT_INPUT + "\' <APPROACH> to select the approach (1=TRE, 2=TA).\n" +
            "\t\'" + ENABLE_TESTING_INPUT + "\' to include additional transitions for testing purposes.\n" +
            "\t\'" + ENABLE_DOT_FILES_INPUT + "\' to output a .dot file of the automaton, if using approach 2.\n" +
            "\t\'" + ENABLE_PNG_FILES_INPUT + "\' to output a .png file of the automaton, if using approach 2.";

    public static void main(final String args[]) throws IOException {

        // Check number of inputs
        if (args.length == 0) {
            System.out.println(INVALID_NUMBER_OF_INPUTS);
            System.exit(1);
        }

        // Come up with arguments list
        final List<String> argsList = new LinkedList<>(Arrays.asList(args));

        // Set booleans
        final boolean testing = argsList.remove(ENABLE_TESTING_INPUT);
        final boolean outputDOT = argsList.remove(ENABLE_DOT_FILES_INPUT);
        final boolean outputPNG = argsList.remove(ENABLE_PNG_FILES_INPUT);

        // Set output folder
        final int outFolderInputIndex = argsList.indexOf(OUTPUT_FOLDER_INPUT);
        String outputFolder = "";
        if (outFolderInputIndex != -1) {
            outputFolder = argsList.get(outFolderInputIndex + 1);
            argsList.remove(outFolderInputIndex); // removes "-out"
            argsList.remove(outFolderInputIndex); // removes folder
            // The above assumes that "-out" is followed by the output folder
        }

        // Set approach
        final int approachIndex = argsList.indexOf(APPROACH_SELECT_INPUT);
        String approach = DEFAULT_APPROACH;
        if (approachIndex != -1) {
            approach = argsList.get(approachIndex + 1);
            argsList.remove(approachIndex); // removes "-app"
            argsList.remove(approachIndex); // removes approach
            // The above assumes that "-app" is followed by the approach
        }

        // Check if input represents a file; If yes, read from file,
        // otherwise assume that the input is a list of regular exp.
        final String remainderOfInput = argsList.stream().collect(Collectors.joining(" "));
        final File inputFile = new File(remainderOfInput);
        final boolean isValidFile = inputFile.exists() && inputFile.canRead();
        String toParse;
        if (isValidFile) {
            toParse = new String(Files.readAllBytes(inputFile.toPath()));
        } else {
            toParse = remainderOfInput;
        }

        // Some notes for the user
        System.out.println("Note: " +
                "\n\t- Testing mode is " + (testing ? "enabled" : "disabled") +
                (approach.equals(TA_APPROACH) ? "" +
                        "\n\t- .DOT outputs are " + (outputDOT ? "enabled" : "disabled") +
                        "\n\t- .PNG outputs are " + (outputPNG ? "enabled" : "disabled") : "") +
                "\n\t- Script(s) will be output to \'" + outputFolder + "\' (if it exists)" +
                (isValidFile ?
                        "\n\t- Input file " + remainderOfInput + " successfully identified" :
                        "\n\t- Input is not a valid/existing file (will assume that input is a list of regular expressions)")
        );

        // Start up the Larva Generator
        final BooleanArguments boolArgs = new BooleanArguments(testing, outputDOT, outputPNG);
        switch (approach) {
            case RE_APPROACH:
                if (outputFolder.equals("")) {
                    new Generator_RE(toParse, boolArgs);
                } else {
                    new Generator_RE(toParse, outputFolder, boolArgs);
                }
                break;
            case TA_APPROACH:
                if (outputFolder.equals("")) {
                    new Generator_TA(toParse, boolArgs);
                } else {
                    new Generator_TA(toParse, outputFolder, boolArgs);
                }
                break;
            default:
                System.out.println("Approach \"" + approach + "\" is not a valid approach.\n" +
                        "A valid approach is either '1' or '2'.");
                break;
        }
    }
}
