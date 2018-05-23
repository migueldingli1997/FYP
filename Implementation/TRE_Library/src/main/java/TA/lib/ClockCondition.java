package TA.lib;

import java.time.Duration;
import java.util.function.Function;

public class ClockCondition {

    static final Duration INFINITY = Duration.ofSeconds(Long.MAX_VALUE); // Represents infinity

    final Clock clock;
    final Duration min;
    final Duration max;

    final String minString;
    final String maxString;

    public ClockCondition(final Clock clock, final Duration min, final Duration max) {
        this.clock = clock;
        this.min = min;
        this.max = max;
        this.minString = min.equals(INFINITY) ? "∞" :
                min.getNano() == 0 ?
                        Long.toString(min.toMillis() / 1000) :
                        Double.toString(min.toMillis() / 1000.0);
        this.maxString = max.equals(INFINITY) ? "∞" :
                max.getNano() == 0 ?
                        Long.toString(max.toMillis() / 1000) :
                        Double.toString(max.toMillis() / 1000.0);
        if (!max.equals(INFINITY)) {
            this.clock.setTimeout(max);
        }
    }

    public ClockCondition(final Clock clock, final Duration value) {
        this(clock, value, value);
    }

    public Clock getClock() {
        return clock;
    }

    public String toJavaCondition(final Function<Clock, String> clockToString) {
        if (min.equals(max)) {
            return "";
        } else if (min.equals(Duration.ZERO)) {
            return String.format("%s.current() < %s", clockToString.apply(clock), maxString);
        } else if (max.equals(INFINITY)) {
            return String.format("%s.current() >= %s", clockToString.apply(clock), minString);
        } else {
            return String.format("%s.current() >= %s && %s.current() < %s",
                    clockToString.apply(clock), minString, clockToString.apply(clock), maxString);
        }
    }

    @Override
    public String toString() {
        if (min.equals(max)) {
            return clock + "=" + min;
        } else if (min.equals(Duration.ZERO)) {
            return clock + "<" + max;
        } else if (max.equals(INFINITY)) {
            return clock + ">=" + min;
        } else {
            return min + "<=" + clock + "<" + max;
        }
    }
}
