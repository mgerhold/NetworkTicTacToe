import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

class Client extends Peer {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            throw new RuntimeException("unable to connect to server");
        }
        System.out.println("Connected to server!");
    }

    @Override
    void send(Serializable message) {
        try {
            outputStream.write(message.serialize().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("unable to send network message");
        }
    }

    @Override
    <T extends Deserializable> void receive(T target) {
        String data;
        try {
            data = new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (Exception e) {
            throw new RuntimeException("unable to read network message");
        }
        target.deserialize(data);
    }

}
