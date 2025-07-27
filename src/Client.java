import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String serverIP = "localhost";
        final int SERVER_PORT = 3000;

        try (Socket socket = new Socket(serverIP, SERVER_PORT)) {
            System.out.println("Подключен к серверу по адресу " + serverIP + ":" + SERVER_PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String greeting = in.readLine();
            System.out.println("Сервер: " + greeting);

            String name = "Студент Максим";
            out.println(name);
            System.out.println("Отправлено на сервер: " + name);

            String response = in.readLine();
            System.out.println("Сервер: " + response);
        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }
}