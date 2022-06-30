import java.util.Scanner;

class Main {

    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt + " (" + min + " - " + max + "): ");
                final String input = scanner.nextLine();
                final int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                }
                throw new RuntimeException();
            } catch (Exception e) {
                System.out.println("Ungueltige Eingabe! Bitte wiederholen!");
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                final String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Ungueltige Eingabe! Bitte wiederholen!");
            }
        }
    }

    private static Position readPosition(Scanner scanner, PlayingField playingField) {
        while (true) {
            int row = readInt(scanner, "Zeile", 1, PlayingField.DIMENSIONS) - 1;
            int column = readInt(scanner, "Spalte", 1, PlayingField.DIMENSIONS) - 1;
            Position position = new Position(row, column);
            if (playingField.isOccupied(position)) {
                System.out.println("Bereits belegt! Bitte wiederholen!");
                continue;
            }
            return position;
        }
    }

    private static ConnectionInfo readConnectionInfo(Scanner scanner) {
        System.out.println("Bitte waehlen:");
        System.out.println("1. Server");
        System.out.println("2. Client");
        final int input = readInt(scanner, "Deine Auswahl", 1, 2);
        final PeerType peerType = input == 1 ? PeerType.Server : PeerType.Client;
        String address = null;
        if (peerType == PeerType.Client) {
            System.out.print("Adresse des Hosts (z. B. \"localhost\"): ");
            address = scanner.nextLine();
        }
        final int port = readInt(scanner, "Port");
        return new ConnectionInfo(peerType, address, port);
    }

    private static Peer createPeer(ConnectionInfo connectionInfo) {
        switch (connectionInfo.peerType) {
            case Server:
                return new Server(connectionInfo.port);
            case Client:
                return new Client(connectionInfo.address, connectionInfo.port);
            default:
                throw new RuntimeException("invalid peer type");
        }
    }

    private static Symbol getOwnSymbol(ConnectionInfo connectionInfo) {
        switch (connectionInfo.peerType) {
            case Server:
                return Symbol.X;
            default:
                return Symbol.O;
        }
    }

    private static boolean isGameOver(Symbol currentPlayer, PlayingField playingField) {
        return playingField.hasPlayerWon(currentPlayer) || playingField.allFieldsOccupied();
    }

    private static void handle_opponent_turn(final Peer peer, final PlayingField playingField) {
        System.out.println("Dein Gegner ist am Zug...");
        final Command opponentCommand = new Command();
        peer.receive(opponentCommand);
        playingField.setField(opponentCommand.position, opponentCommand.symbol);
    }

    private static void handle_own_turn(final Scanner scanner, final Peer peer, final Symbol ownSymbol,
            final PlayingField playingField) {
        System.out.println(playingField);
        System.out.println("Bitte mache Deinen Zug!");
        final Position position = readPosition(scanner, playingField);
        peer.send(new Command(ownSymbol, position));
        playingField.setField(position, ownSymbol);
        System.out.println(playingField);
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final PlayingField playingField = new PlayingField();

        final ConnectionInfo connectionInfo = readConnectionInfo(scanner);
        System.out.println("Verbindung wird aufgebaut...");
        final Peer peer = createPeer(connectionInfo);

        final Symbol ownSymbol = getOwnSymbol(connectionInfo);
        Symbol currentPlayer = Symbol.X;

        while (true) {
            final boolean ourTurn = currentPlayer == ownSymbol;
            if (ourTurn) {
                handle_own_turn(scanner, peer, ownSymbol, playingField);
            } else {
                handle_opponent_turn(peer, playingField);
            }
            if (isGameOver(currentPlayer, playingField)) {
                break;
            }
            currentPlayer = currentPlayer.getOtherSymbol();
        }

        if (playingField.allFieldsOccupied()) {
            System.out.println("Unentschieden!");
        } else if (currentPlayer == ownSymbol) {
            System.out.println("Du hast gewonnen!");
        } else {
            System.out.println(playingField);
            System.out.println("Du hast verloren!");
        }
    }

}
