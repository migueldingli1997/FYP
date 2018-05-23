package TA.lib;

import java.time.Duration;

public class Clock {

    public int id = -1;
    Duration timeout = null;

    Clock() {
    }

    void setTimeout(final Duration timeout) {
        if (hasTimeout()) {
            System.err.println("WARNING: Attempted to set timeout twice.");
        } else {
            this.timeout = timeout;
        }
    }

    public boolean hasTimeout() {
        return timeout != null;
    }

    public long getTimeoutInMillis() throws NullPointerException {
        return timeout.toMillis();
    }

    @Override
    public String toString() {
        if (id == -1) {
            return super.toString();
        } else {
            return "clock" + id;
        }
    }
}
