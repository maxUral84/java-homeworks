import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<String> logMessages = new ArrayList<>();

    public static void main(String[] args) {
        String basePath = "src/Games";
        log("[SYSTEM] Начало установки в: " + basePath);

        // 1. Создаем корневую папку Games
        File gamesDir = new File(basePath);
        if (checkOrCreateDirectory(gamesDir)) {
            log("[OK] Основная папка Games готова к работе");
        } else {
            log("[ERROR] Критическая ошибка: невозможно работать с папкой Games");
            saveLogToFile(basePath); // Пытаемся сохранить лог, даже если ошибка
            return;
        }

        // 2. Создаем основные подпапки
        File srcDir = handleDirectory(basePath, "src");
        File resDir = handleDirectory(basePath, "res");
        File savegamesDir = handleDirectory(basePath, "savegames");
        File tempDir = handleDirectory(basePath, "temp");

        // 3. Создаем структуру в src
        if (srcDir != null) {
            File mainDir = handleDirectory(srcDir.getPath(), "main");
            File testDir = handleDirectory(srcDir.getPath(), "test");

            // Создаем файлы в main
            if (mainDir != null) {
                handleFile(mainDir.getPath(), "Main.java");
                handleFile(mainDir.getPath(), "Utils.java");
            }
        }

        // 4. Создаем подпапки в res
        if (resDir != null) {
            handleDirectory(resDir.getPath(), "drawables");
            handleDirectory(resDir.getPath(), "vectors");
            handleDirectory(resDir.getPath(), "icons");
        }

        log("[SYSTEM] Установка завершена");
        saveLogToFile(basePath); // Сохраняем лог в файл
    }

    // Запись сообщения в лог (в память)
    private static void log(String message) {
        System.out.println(message); // Вывод в консоль
        logMessages.add(message);   // Сохранение в список для файла
    }

    // Сохранение лога в файл temp.txt
    private static void saveLogToFile(String basePath) {
        File tempDir = new File(basePath + File.separator + "temp");
        if (!tempDir.exists()) {
            log("[ERROR] Папка temp не существует, невозможно сохранить лог");
            return;
        }

        File logFile = new File(tempDir.getPath() + File.separator + "temp.txt");
        try (FileWriter writer = new FileWriter(logFile)) {
            for (String message : logMessages) {
                writer.write(message + System.lineSeparator());
            }
            log("[OK] Лог успешно записан в: " + logFile.getPath());
        } catch (IOException e) {
            log("[ERROR] Ошибка записи лога в файл: " + e.getMessage());
        }
    }

    // Обработка директории
    private static File handleDirectory(String parentPath, String dirName) {
        File dir = new File(parentPath + File.separator + dirName);
        if (checkOrCreateDirectory(dir)) {
            return dir;
        }
        return null;
    }

    // Проверка и создание директории
    private static boolean checkOrCreateDirectory(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                log("[INFO] Папка уже существует: " + dir.getPath());
                return true;
            } else {
                log("[ERROR] Объект существует, но это не папка: " + dir.getPath());
                return false;
            }
        }

        if (dir.mkdirs()) {
            log("[OK] Папка создана: " + dir.getPath());
            return true;
        } else {
            log("[ERROR] Не удалось создать папку: " + dir.getPath());
            return false;
        }
    }

    // Обработка файла
    private static void handleFile(String parentPath, String fileName) {
        File file = new File(parentPath + File.separator + fileName);

        if (file.exists()) {
            if (file.isFile()) {
                log("[INFO] Файл уже существует: " + file.getPath());
            } else {
                log("[ERROR] Объект существует, но это не файл: " + file.getPath());
            }
            return;
        }

        try {
            if (file.createNewFile()) {
                log("[OK] Файл создан: " + file.getPath());
            } else {
                log("[ERROR] Не удалось создать файл: " + file.getPath());
            }
        } catch (IOException e) {
            log("[ERROR] Ошибка ввода-вывода при создании файла: " + e.getMessage());
        } catch (SecurityException e) {
            log("[ERROR] Нет прав для создания файла: " + e.getMessage());
        }
    }
}