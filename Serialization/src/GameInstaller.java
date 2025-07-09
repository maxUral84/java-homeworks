import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameInstaller {
    private final String basePath;
    private final StringBuilder log;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public GameInstaller(String basePath) {
        this.basePath = basePath;
        this.log = new StringBuilder();
        log.append("=== Установка начата ===\n");
        log.append("Время: ").append(LocalDateTime.now().format(dtf)).append("\n");
    }

    public boolean createDirectory(String path) {
        File dir = new File(basePath + "/" + path);
        if (dir.mkdirs()) {
            log.append("[OK] Создана папка: ").append(path).append("\n");
            return true;
        } else {
            log.append("[ERROR] Не удалось создать: ").append(path).append("\n");
            return false;
        }
    }

    public void writeLog() {
        try (FileWriter writer = new FileWriter(basePath + "/temp/temp.txt")) {
            log.append("\n=== Установка завершена ===\n");
            log.append("Время: ").append(LocalDateTime.now().format(dtf)).append("\n");
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println("Ошибка записи лога: " + e.getMessage());
        }
    }

}