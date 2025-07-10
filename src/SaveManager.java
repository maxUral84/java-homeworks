import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    private StringBuilder log = new StringBuilder();
    private List<String> savedFiles = new ArrayList<>(); // Список сохраненных файлов для архивации

    public boolean saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            log.append("Игра сохранена в файл: ").append(filePath).append("\n");
            savedFiles.add(filePath); // Добавляем путь к сохраненному файлу
            return true;
        } catch (IOException e) {
            log.append("Ошибка при сохранении игры в файл ").append(filePath).append(": ").append(e.getMessage()).append("\n");
            return false;
        }
    }

    public String getLog() {
        return log.toString();
    }

    public List<String> getSavedFiles() {
        return savedFiles;
    }
}