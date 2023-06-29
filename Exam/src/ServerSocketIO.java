import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class ServerSocketIO implements Runnable{
    private static int port = 1234;
    private ServerSocket serverSocket;
    @Override
    public void run() {
        try {
            // tạo server
            serverSocket = new ServerSocket(port);
            while(true) {
                Socket s = serverSocket.accept(); // chấp nhận kết nối từ client
                System.out.println(s);
                // tạo thread cho client kết nối tới
                ClientSocketIO clientSocket = new ClientSocketIO(s);
                new Thread(clientSocket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reponseClient(Socket socket, Object data, int typeMsg) throws IOException {
        ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());

        dout.writeInt(typeMsg);
        dout.writeObject(data);
        dout.flush();
    }
}
