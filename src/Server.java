import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 3000;
        boolean running = true;

        System.out.println("Запуск сервера на порту: " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (running) {
                try (Socket clientSocket = serverSocket.accept()) {
                    String clientIP = clientSocket.getInetAddress().getHostAddress();
                    int clientPort = clientSocket.getPort();

                    System.out.println("\nНовое соединение принято");
                    System.out.println("IP клиента: " + clientIP);
                    System.out.println("порт клиента: " + clientPort);

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    out.println("Добро пожаловать на сервер! Пожалуйста, введите своё имя:");
                    String name = in.readLine();
                    System.out.println("Имя клиента получено: " + name);

                    String response = String.format("Привет %s, твой порт %d", name, clientPort);
                    out.println(response);
                    System.out.println("Ответ отправлен клиенту: " + response);
                } catch (IOException e) {
                    System.err.println("Ошибка обработки клиентского соединения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }
}