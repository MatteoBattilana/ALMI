import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class ClientMain {
    private Socket socket;
    private Scanner scanner;
    private ClientMain(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }
    private void start() throws IOException {
        String input;
        while (true) {
            input = scanner.nextLine();
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(input);
            out.flush();
        }
    }

    public static void main(String[] args) throws Exception {
        ClientMain client = new ClientMain(
          InetAddress.getByName("localhost"),
          11111);

        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();
    }
}
