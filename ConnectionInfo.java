enum PeerType {
    Server,
    Client
}

class ConnectionInfo {
    public PeerType peerType;
    public String address;
    public int port;

    public ConnectionInfo(PeerType peerType, String address, int port) {
        this.peerType = peerType;
        this.address = address;
        this.port = port;
    }
}
