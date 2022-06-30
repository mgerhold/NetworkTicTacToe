class PlayingField {
    public static final int DIMENSIONS = 3;

    private Symbol[][] fields = new Symbol[DIMENSIONS][DIMENSIONS];

    public PlayingField() {
        for (int row = 0; row < DIMENSIONS; ++row) {
            for (int column = 0; column < DIMENSIONS; ++column) {
                setField(new Position(row, column), Symbol.EMPTY);
            }
        }
    }

    public Symbol getField(Position position) {
        return fields[position.row][position.column];
    }

    public void setField(Position position, Symbol symbol) {
        fields[position.row][position.column] = symbol;
    }

    private boolean rowFilledWith(int row, Symbol symbol) {
        for (int i = 0; i < DIMENSIONS; ++i) {
            if (getField(new Position(row, i)) != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean columnFilledWith(int column, Symbol symbol) {
        for (int i = 0; i < DIMENSIONS; ++i) {
            if (getField(new Position(i, column)) != symbol) {
                return false;
            }
        }
        return true;
    }

    public boolean allFieldsOccupied() {
        for (int row = 0; row < DIMENSIONS; ++row) {
            for (int column = 0; column < DIMENSIONS; ++column) {
                if (!isOccupied(new Position(row, column))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasPlayerWon(Symbol symbol) {
        for (int row = 0; row < DIMENSIONS; ++row) {
            if (rowFilledWith(row, symbol)) {
                return true;
            }
        }
        for (int column = 0; column < DIMENSIONS; ++column) {
            if (columnFilledWith(column, symbol)) {
                return true;
            }
        }
        boolean diagonalFilled = true;
        for (int i = 0; i < DIMENSIONS; ++i) {
            if (getField(new Position(i, i)) != symbol) {
                diagonalFilled = false;
                break;
            }
        }
        if (diagonalFilled) {
            return true;
        }
        diagonalFilled = true;
        for (int i = 0; i < DIMENSIONS; ++i) {
            if (getField(new Position(i, DIMENSIONS - 1 - i)) != symbol) {
                diagonalFilled = false;
                break;
            }
        }
        return diagonalFilled;
    }

    public boolean isOccupied(Position position) {
        return getField(position) != Symbol.EMPTY;
    }

    @Override
    public String toString() {
        String result = "  ";
        for (int i = 1; i <= DIMENSIONS; ++i) {
            result += i;
        }
        result += "\n  ";
        for (int i = 1; i <= DIMENSIONS; ++i) {
            result += "-";
        }
        result += "\n";
        for (int row = 0; row < DIMENSIONS; ++row) {
            result += (row + 1) + "|";
            for (int column = 0; column < DIMENSIONS; ++column) {
                result += getField(new Position(row, column)).character;
            }
            result += "\n";
        }
        return result;
    }

}
