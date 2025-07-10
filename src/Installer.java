import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Installer {
    private StringBuilder log = new StringBuilder();
    private List<String> createdPaths = new ArrayList<>(); // Для отката

    public boolean createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                log.append("Директория создана: ").append(path).append("\n");
                createdPaths.add(path); // Добавляем в список для отката
                return true;
            } else {
                log.append("Не удалось создать директорию: ").append(path).append("\n");
                return false;
            }
        } else {
            log.append("Директория уже существует: ").append(path).append("\n");
            return true; // Считаем успешным, так как уже существует
        }
    }

    public boolean createFile(String path) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                log.append("Файл создан: ").append(path).append("\n");
                createdPaths.add(path); // Добавляем в список для отката
                return true;
            } else {
                log.append("Файл уже существует: ").append(path).append("\n");
                return true; // Считаем успешным, так как уже существует
            }
        } catch (IOException e) {
            log.append("Не удалось создать файл: ").append(path).append(". Исключение: ").append(e.getMessage()).append("\n");
            return false;
        }
    }

    public void writeLogToFile(String logFilePath) {
        try (FileWriter writer = new FileWriter(logFilePath)) {
            writer.write(log.toString());
        } catch (IOException e) {
            System.err.println("Ошибка при записи лога в файл " + logFilePath + ": " + e.getMessage());
        }
    }

    public String getLog() {
        return log.toString();
    }
}