package TA.lib;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class StateSkip {

    public final List<ClockCondition> guard;
    public final LinkedHashSet<Clock> toReset;
    public final State to;

    public StateSkip(final State to) {
        this(to, new ArrayList<>(), new LinkedHashSet<>());
    }

    public StateSkip(final State to, final LinkedHashSet<Clock> toReset) {
        this(to, new ArrayList<>(), toReset);
    }

    public StateSkip(final State to, final List<ClockCondition> guard) {
        this(to, guard, new LinkedHashSet<>());
    }

    public StateSkip(final State to, final List<ClockCondition> guard, final LinkedHashSet<Clock> toReset) {
        this.guard = guard;
        this.toReset = toReset;
        this.to = to;
    }

    /**
     * (Should not be used in place of a Larva transition!)
     */
    public String toEdgeString() {
        final String guardStr = !guard.isEmpty() ? guard.toString() : "";
        final String slash = !toReset.isEmpty() ? "\\" : "";
        final String toResetStr = !toReset.isEmpty() ? toReset.toString() : "";
        return String.format("%s%s%s", guardStr, slash, toResetStr);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final StateSkip that = (StateSkip) o;
        return Objects.equals(guard, that.guard) &&
                Objects.equals(toReset, that.toReset) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guard, toReset, to);
    }
}
