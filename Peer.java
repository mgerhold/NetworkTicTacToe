abstract class Peer {
    abstract void send(Serializable message);
    abstract <T extends Deserializable> void receive(T target);
}
