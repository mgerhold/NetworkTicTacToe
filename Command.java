class Command implements Serializable, Deserializable {
    public Symbol symbol;
    public Position position;

    public Command() {
        symbol = Symbol.EMPTY;
        position = new Position(0, 0);
    }

    public Command(Symbol symbol, Position position) {
        this.symbol = symbol;
        this.position = position;
    }

    @Override
    public void deserialize(String data) {
        String[] parts = data.split(",");
        if (parts.length != 3) {
            throw new RuntimeException("invalid deserialization data");
        }
        symbol = Symbol.valueOf(parts[0]); // may throw
        position = new Position(Integer.parseInt(parts[1]), Integer.parseInt(parts[2].trim())); // may throw
    }

    @Override
    public String serialize() {
        return symbol + "," + position.row + "," + position.column + "\n";
    }
}
