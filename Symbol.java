enum Symbol {
    X('X'),
    O('O'),
    EMPTY(' ');

    public final char character;

    private Symbol(char character) {
        this.character = character;
    }

    public Symbol getOtherSymbol() {
        switch (this) {
            case O:
                return Symbol.X;
            case X:
                return Symbol.O;
            default:
                throw new RuntimeException("invalid player symbol");
        }
    }
}
