package TA.lib;

import RE.lib.basic.Symbol;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class TimedAutomaton {

    final LinkedHashSet<State> states;
    final LinkedHashSet<Clock> clocks;
    final LinkedHashSet<Transition> transitions;
    final LinkedHashSet<Symbol> alphabet;
    final State initialState;
    final LinkedHashSet<State> acceptingStates;

    public TimedAutomaton() {
        this(new State());
    }

    public TimedAutomaton(final State initialState) {

        this.states = new LinkedHashSet<>();
        this.clocks = new LinkedHashSet<>();
        this.transitions = new LinkedHashSet<>();
        this.alphabet = new LinkedHashSet<>();
        this.initialState = initialState;
        this.states.add(initialState);
        this.acceptingStates = new LinkedHashSet<>();
    }

    public State addState(final boolean isAccepting) {
        final State newState = new State();
        states.add(newState);
        if (isAccepting) {
            acceptingStates.add(newState);
        }
        return newState;
    }

    public Clock addClock() {
        final Clock newClock = new Clock();
        clocks.add(newClock);
        return newClock;
    }

    public Transition addTransition(final Transition transition) {
        transitions.add(transition);
        alphabet.add(transition.symbol);
        return transition;
    }

    public LinkedHashSet<State> getStates() {
        return states;
    }

    public LinkedHashSet<Clock> getClocks() {
        return clocks;
    }

    public LinkedHashSet<Transition> getTransitions() {
        return transitions;
    }

    public LinkedHashSet<Symbol> getAlphabet() {
        return alphabet;
    }

    public State getInitialState() {
        return initialState;
    }

    public LinkedHashSet<State> getAcceptingStates() {
        return acceptingStates;
    }

    public boolean isAcceptingState(final State toCheck) {
        return acceptingStates.contains(toCheck);
    }

    public LinkedHashSet<Transition> getTransitionsLeadingToAcceptingStates() {

        final LinkedHashSet<Transition> toReturn = new LinkedHashSet<>();
        for (final Transition trans : transitions) {
            if (acceptingStates.contains(trans.to)) {
                toReturn.add(trans);
            }
        }
        return toReturn;
    }

    public TimedAutomaton joinWith(final TimedAutomaton toJoinWith, final boolean keepAcceptingStates) {

        this.states.addAll(toJoinWith.states);
        this.clocks.addAll(toJoinWith.clocks);
        this.transitions.addAll(toJoinWith.transitions);
        this.alphabet.addAll(toJoinWith.alphabet);
        if (keepAcceptingStates) {
            this.acceptingStates.addAll(toJoinWith.acceptingStates);
        } else {
            final LinkedHashSet<State> acc = toJoinWith.acceptingStates;
            this.transitions.removeIf(t -> acc.contains(t.from) || acc.contains(t.to));
            this.states.removeAll(toJoinWith.acceptingStates);
        }
        return this;
    }

    public void eliminateNonDetAndSilentTrans() {

        // Remove emptyString transitions which have equal source and destination
        final Iterator<Transition> iterator = transitions.iterator();
        while (iterator.hasNext()) {
            final Transition t = iterator.next();
            if (t.from.equals(t.to) && t.isEmptySymbolTransition()) {
                iterator.remove();
                if (t.hasNoGuardAndNoResets()) {
                    System.out.println("WARNING: removed silent transition containing a guard or resets.");
                }
            }
        }

        // Create state-transition map
        final Map<State, LinkedHashSet<Transition>> stateTransitionMap = new HashMap<>();
        states.forEach(s -> stateTransitionMap.put(s, new LinkedHashSet<>()));
        transitions.forEach(t -> stateTransitionMap.get(t.from).add(t));

        // Eliminate non-determinism and silent transitions for each state
        for (final State s : new LinkedHashSet<>(states)) { // Note: new hashset to avoid concurrent modification issues

            // Build multiset of outgoing symbols for current state
            final Multiset<Symbol> symbolsMultiset = HashMultiset.create();
            final LinkedHashSet<Transition> outgoing = stateTransitionMap.get(s);
            outgoing.forEach(t -> symbolsMultiset.add(t.symbol));

            // Remove symbols with count equal to one and the empty string
            symbolsMultiset.removeIf(sym -> symbolsMultiset.count(sym) == 1 || sym instanceof EmptySymbol);

            // Create new transitions for each symbol non-determinism
            final Map<Symbol, Transition> newTransitionMap = new HashMap<>();
            for (final Symbol sym : symbolsMultiset.elementSet()) {
                final State newState = addState(false);
                final Transition newTrans = addTransition(new Transition(s, newState, sym));
                newTransitionMap.put(sym, newTrans); // add transition to map
            }

            // Add state-skips to states
            for (final Transition t : outgoing) {
                if (newTransitionMap.containsKey(t.symbol)) { // symbol non-determinism
                    final State theNewState = newTransitionMap.get(t.symbol).to;
                    theNewState.addStateSkip(new StateSkip(t.to, t.guard, t.toReset));
                } else if (t.isEmptySymbolTransition()) { // silent transitions
                    s.addStateSkip(new StateSkip(t.to, t.guard, t.toReset));
                }
            }

            // Remove all empty-string and non-deterministic transitions
            for (final Transition t : outgoing) {
                if (t.isEmptySymbolTransition() || newTransitionMap.containsKey(t.symbol)) {
                    transitions.remove(t);
                }
            }
        }

        // EmptySymbol no longer a part of the alphabet
        alphabet.remove(EmptySymbol.INSTANCE);
    }

    public int setStateNamesAndClockIDs() {

        // Set state names
        int count = 0;
        for (final State s : states) {
            if (s.equals(initialState)) {
                s.name = "start";
            } else {
                s.name = "S" + count++;
            }
        }

        // Set clock IDs
        int count2 = 0;
        for (final Clock c : clocks) {
            c.id = count2++;
        }

        // Return number of states given a name (excluding initial state)
        return count;
    }

    @Override
    public String toString() {

        // Set state names and clock IDs
        setStateNamesAndClockIDs();

        // Build and return output
        final StringBuilder sb = new StringBuilder();
        sb.append("digraph TA {\n");
        sb.append("\t\"\" [shape=none]\n");
        sb.append("\t\"\" -> \"" + initialState + "\"\n");
        for (final State s : acceptingStates) {
            sb.append("\t\"" + s + "\" [shape=doublecircle]\n");
        }
        sb.append("\n");
        for (final Transition t : transitions) {
            sb.append("\t\"" + t.from + "\" -> \"" + t.to + "\"");
            sb.append(" [label = \"" + t.toEdgeString() + "\"];\n");
        }
        for (final State s : states) {
            for (final StateSkip t : s.getStateSkips()) {
                sb.append("\t\"" + s + "\" -> \"" + t.to + "\" [style=\"dotted\"]");
                sb.append(" [label = \"" + t.toEdgeString() + "\"];\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
