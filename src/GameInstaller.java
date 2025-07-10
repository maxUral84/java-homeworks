import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameInstaller {
    private final StringBuilder log = new StringBuilder();

    public String installGame(String basePath) throws IOException {
        // Нормализуем путь для разных ОС
        Path normalizedPath = Paths.get(basePath).normalize();
        File baseDir = normalizedPath.toFile();

        log.append("Starting installation in: ").append(baseDir.getAbsolutePath()).append("\n\n");

        // Проверяем/создаем базовую директорию
        if (handleBaseDirectory(baseDir) == false) {
            return log.toString();
        }

        // Создаем структуру каталогов
        createDirectoryStructure(baseDir);

        // Создаем файлы
        createFiles(baseDir);

        // Записываем лог
        writeLogFile(baseDir);

        return log.toString();
    }

    private boolean handleBaseDirectory(File baseDir) throws IOException {
        if (!baseDir.exists()) {
            log.append("INFO: Base directory does not exist. Attempting to create...\n");
            if (baseDir.mkdirs()) {
                log.append("INFO: Successfully created base directory\n");
                return true;
            } else {
                log.append("ERROR: Failed to create base directory. Check permissions.\n");
                return false;
            }
        }

        if (!baseDir.canWrite()) {
            log.append("ERROR: No write permissions for base directory\n");
            return false;
        }

        log.append("INFO: Base directory exists and is writable\n");
        return true;
    }

    private void createDirectoryStructure(File baseDir) {
        String[] directories = {
                "src",
                "src/main",
                "src/test",
                "res",
                "res/drawables",
                "res/vectors",
                "res/icons",
                "savegames",
                "temp"
        };

        for (String dir : directories) {
            File newDir = new File(baseDir, dir.replace("/", File.separator));
            createDirectoryWithCheck(newDir);
        }
    }

    private void createFiles(File baseDir) throws IOException {
        String[] files = {
                "src/main/Main.java",
                "src/main/Utils.java",
                "temp/temp.txt"
        };

        for (String file : files) {
            File newFile = new File(baseDir, file.replace("/", File.separator));
            createFileWithCheck(newFile);
        }
    }

    private void writeLogFile(File baseDir) throws IOException {
        File tempFile = new File(baseDir, "temp/temp.txt");
        if (tempFile.exists()) {
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(log.toString());
                log.append("\nLog written to: ").append(tempFile.getAbsolutePath());
            }
        }
    }

    private void createDirectoryWithCheck(File dir) {
        if (dir.exists()) {
            log.append("[SKIP] Directory already exists: ").append(dir.getAbsolutePath()).append("\n");
            return;
        }

        if (dir.mkdirs()) {
            log.append("[OK] Created directory: ").append(dir.getAbsolutePath()).append("\n");
        } else {
            log.append("[ERROR] Failed to create directory: ").append(dir.getAbsolutePath()).append("\n");
        }
    }

    private void createFileWithCheck(File file) throws IOException {
        if (file.exists()) {
            log.append("[SKIP] File already exists: ").append(file.getAbsolutePath()).append("\n");
            return;
        }

        // Создаем родительские директории, если нужно
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (file.createNewFile()) {
            log.append("[OK] Created file: ").append(file.getAbsolutePath()).append("\n");
        } else {
            log.append("[ERROR] Failed to create file: ").append(file.getAbsolutePath()).append("\n");
        }
    }
}