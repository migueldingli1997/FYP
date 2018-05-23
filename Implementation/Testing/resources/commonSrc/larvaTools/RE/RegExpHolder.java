package larvaTools.RE;

import RE.lib.RegExp;
import RE.lib.basic.Symbol;
import RE.lib.operators.TimeInterval;
import RE.parser.Parser;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Given a regular expression, the holder immediately computes the minimum
 * time value by which the regular expression is expecting a symbol and
 * sets a timer with this value as the timeout. If the time runs out before
 * a derivative is applied to the regular expression, a derivative with
 * respect to time elapsed will automatically be applied to the expression.
 */
public class RegExpHolder {

    public static boolean loggingEnabled = false;
    private static int generalId = 0;

    private static List<RegExp> expressions = new ArrayList<>();

    private final int id = generalId++;
    private RegExp regExp; // the held regular expression
    private Instant lastChange; // time of last change to expression
    private Thread timerThread; // thread for timeout timer

    /**
     * This should be called right at the start of a script so that the expression
     * is parsed only once and is stored in the static list provided by this class.
     *
     * @param exprInput The original input expression.
     * @return The index of the latest parsed expression.
     */
    public static int parseAndStore(final String exprInput) {
        expressions.add(new Parser(false, true).parse(exprInput).get(0).getRegExp());
        return expressions.size() - 1;
    }

    /**
     * Sets the regular expression to be held and sets a timeout.
     *
     * @param index The expression index returned when parseAndStore was called.
     */
    public RegExpHolder(final int index) {
        this(expressions.get(index));
    }

    /**
     * Sets the regular expression to be held and sets a timeout.
     *
     * @param regExp Regular expression to be held.
     */
    public RegExpHolder(final RegExp regExp) {

        setRegExp(regExp);
        if (loggingEnabled) {
            System.out.println("RegExp " + id + " update: \"" + regExp + "\"");
        }
        setTimeout();
    }

    /**
     * Replaces the current regular expression with its derivative (w.r.t the time that
     * elapsed since latest derivative and the symbol argument). It also resets the timer.
     *
     * @param sym Symbol with respect to which derivative will be done.
     * @return Derivative with respect to the time elapsed and passed symbol.
     */
    public synchronized RegExp toDerivative(final Symbol sym) {

        if (timerThread != null) {
            timerThread.interrupt();
        }

        final Duration timeElapsed = Duration.between(lastChange, Instant.now());
        if (loggingEnabled) {
            System.out.println("RegExp " + id + " update: event " + sym + " occurred after " + timeElapsed + ".");
        }

        setRegExp(regExp.getDerivative(sym, timeElapsed));
        if (loggingEnabled) {
            System.out.println("RegExp " + id + " update: \"" + regExp + "\"");
        }

        setTimeout();
        return regExp;
    }

    /**
     * Replaces the current regular expression with its derivative (w.r.t the
     * time that elapsed since latest derivative). It also resets the timer.
     * <p>
     * Additionally, if the residual satisfies isEmpty or hasEmptyString, the
     * appropriate trigger is invoked, so that it is captured by the monitor.
     */
    private void timeout() {

        final Duration timeElapsed = Duration.between(lastChange, Instant.now());
        if (loggingEnabled) {
            System.out.println("RegExp " + id + " update: timeout occurred after " + timeElapsed + ".");
        }

        setRegExp(regExp.getDerivative(Duration.between(lastChange, Instant.now())));
        if (loggingEnabled) {
            System.out.println("RegExp " + id + " update: \"" + regExp + "\"");
        }

        if (regExp.isEmpty()) {
            triggerTimeout_isEmpty();
        } else if (regExp.hasEmptyString()) {
            triggerTimeout_hasEmptyString();
        }
    }

    /**
     * Interrupts the thread and sets it to null to stop it.
     */
    public void stop() {

        if (timerThread != null) {
            timerThread.interrupt();
        }
        timerThread = null;
    }

    /**
     * Sets a timeout timer based on the minimum timeout value obtained from the current
     * expression. The method loops until the timer is interrupted by toDerivative(...)
     * or stop(). Once the timer runs out, a derivative is applied based on time elapsed.
     * If the minimum timeout value is infinite, no timeout timer is started.
     */
    private void setTimeout() {

        final Duration timeoutValue = regExp.minTimeoutValue();
        if (!timeoutValue.equals(TimeInterval.INFINITY)) {
            timerThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        final Duration timeoutValue = regExp.minTimeoutValue();
                        if (!timeoutValue.equals(TimeInterval.INFINITY)) {
                            try {
                                // Sleeping until interrupted or time runs out
                                sleep(timeoutValue.toMillis());

                                // Perform derivative with respect to time elapsed
                                synchronized (RegExpHolder.this) {
                                    timeout(); // derivative
                                }
                            } catch (InterruptedException e) {
                                break; // Timeout interrupted by another derivative
                            }
                        } else {
                            break;
                        }
                    }
                }
            };
            timerThread.start();
        }
    }

    /**
     * Sets regular expression and updates the time of the last change.
     *
     * @param regExp The new regular expression.
     */
    private void setRegExp(final RegExp regExp) {

        this.regExp = regExp;
        lastChange = Instant.now();
    }

    /**
     * This method is meant to be captured by the monitor to indicate that the
     * RE has satisfied isEmpty(), so that the appropriate action to be taken.
     */
    public void triggerTimeout_isEmpty() {
    }

    /**
     * This method is meant to be captured by the monitor to indicate that the RE
     * has satisfied hasEmptyString(), so that the appropriate action to be taken.
     */
    public void triggerTimeout_hasEmptyString() {
    }

    public int getId() {
        return id;
    }

    public RegExp getRegExp() {
        return regExp;
    }

    @Override
    public String toString() {
        return regExp.toString();
    }
}
