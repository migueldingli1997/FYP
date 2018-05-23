package TA.lib;

import RE.lib.basic.Symbol;

public class EmptySymbol extends Symbol {

    public static final EmptySymbol INSTANCE = new EmptySymbol();

    private EmptySymbol() {
        super("");
    }
}
