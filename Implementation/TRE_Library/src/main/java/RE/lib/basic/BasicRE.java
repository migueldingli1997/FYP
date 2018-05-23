package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.operators.TimeInterval;

import java.time.Duration;

/**
 * Serves as the superclass of basic regular expressions
 * where common or default behaviour can be implemented.
 */
public abstract class BasicRE extends RegExp {

    @Override
    public final RegExp getDerivative(final Duration dt) {
        return this;
    }

    @Override
    public final Duration minTimeoutValue() {
        return TimeInterval.INFINITY;
    }

    @Override
    public boolean equals(final Object o) {
        return this == o;
    }
}
