package util;

import java.time.Duration;

public class DurationComparison {

    /**
     * Computes the maximum of two durations.
     */
    public static Duration max(final Duration d1, final Duration d2) {
        return d1.compareTo(d2) >= 0 ? d1 : d2;
    }

    /**
     * Computes the minimum of two durations.
     */
    public static Duration min(final Duration d1, final Duration d2) {
        return d1.compareTo(d2) >= 0 ? d2 : d1;
    }

    /**
     * True if first duration >= second duration
     */
    public static boolean grtrEq(final Duration d1, final Duration d2) {
        return d1.compareTo(d2) >= 0;
    }

    /**
     * True if first duration <= second duration
     */
    public static boolean lessEq(final Duration d1, final Duration d2) {
        return d1.compareTo(d2) <= 0;
    }

    /**
     * True if first duration > second duration
     */
    public static boolean grtrThan(final Duration d1, final Duration d2) {
        return d1.compareTo(d2) > 0;
    }

    /**
     * True if first duration < second duration
     */
    public static boolean lessThan(final Duration d1, final Duration d2) {
        return d1.compareTo(d2) < 0;
    }
}
