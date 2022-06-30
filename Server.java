import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Peer {
    private ServerSocket socket;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Server(int port) {
        try {
            socket = new ServerSocket(port);
            clientSocket = socket.accept();
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
        } catch (Exception e) {
            throw new RuntimeException("unable to start server");
        }
        System.out.println("Connected to client!");
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
