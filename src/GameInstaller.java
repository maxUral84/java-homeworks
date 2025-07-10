import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameInstaller {
    // Константы для директорий
    private static final String SRC_DIR = "src";
    private static final String MAIN_DIR = "main";
    private static final String TEST_DIR = "test";
    private static final String RES_DIR = "res";
    private static final String DRAWABLES_DIR = "drawables";
    private static final String VECTORS_DIR = "vectors";
    private static final String ICONS_DIR = "icons";
    private static final String SAVEGAMES_DIR = "savegames";
    private static final String TEMP_DIR = "temp";

    // Константы для файлов
    private static final String MAIN_JAVA = "Main.java";
    private static final String UTILS_JAVA = "Utils.java";
    private static final String TEMP_TXT = "temp.txt";
    private static final String LOG_FILE = "temp.txt";

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
                SRC_DIR,
                SRC_DIR + File.separator + MAIN_DIR,
                SRC_DIR + File.separator + TEST_DIR,
                RES_DIR,
                RES_DIR + File.separator + DRAWABLES_DIR,
                RES_DIR + File.separator + VECTORS_DIR,
                RES_DIR + File.separator + ICONS_DIR,
                SAVEGAMES_DIR,
                TEMP_DIR
        };

        for (String dir : directories) {
            File newDir = new File(baseDir, dir);
            createDirectoryWithCheck(newDir);
        }
    }

    private void createFiles(File baseDir) throws IOException {
        String[] files = {
                SRC_DIR + File.separator + MAIN_DIR + File.separator + MAIN_JAVA,
                SRC_DIR + File.separator + MAIN_DIR + File.separator + UTILS_JAVA,
                TEMP_DIR + File.separator + TEMP_TXT
        };

        for (String file : files) {
            File newFile = new File(baseDir, file);
            createFileWithCheck(newFile);
        }
    }

    private void writeLogFile(File baseDir) throws IOException {
        File tempFile = new File(baseDir, TEMP_DIR + File.separator + LOG_FILE);
        if (tempFile.exists()) {
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(log.toString());
                log.append("\nLog written to: ").append(tempFile.getAbsolutePath());
            }
        }
    }

    private void createDirectoryWithCheck(File dir) {
        if (dir.exists()) {
            log.append("[OK] Directory already exists: ").append(dir.getAbsolutePath()).append("\n");
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
            log.append("[OK] File already exists: ").append(file.getAbsolutePath()).append("\n");
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