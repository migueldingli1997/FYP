package RE.parser.util;

public class CommentRemover {

    /**
     * Removes java-style multi-line comments from the string.
     */
    public static String removeComments(final String toRemoveCommentsFrom) {
        String temp = toRemoveCommentsFrom;
        int start = 0, end;

        do {
            start = temp.indexOf("/*", start); // start search from previous start
            if (start != -1) {
                end = temp.indexOf("*/", start + 2) + 2; // start search from after /*
                if ((end - 2) != -1) {
                    temp = temp.substring(0, start) + temp.substring(end, temp.length());
                } else break;
            } else break;
        } while (true);

        return temp;
    }
}
