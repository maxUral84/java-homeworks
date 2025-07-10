import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Installer installationManager = new Installer();
        SaveManager saveManager = new SaveManager();
        ZipManager zipManager = new ZipManager();
        StringBuilder totalLog = new StringBuilder();

        // Часть 1: Установка
        try {
            totalLog.append("--- Запуск установки ---\n");
            // Создание основных директорий
            installationManager.createDirectory(Paths.BASE_DIR);
            installationManager.createDirectory(Paths.SRC_DIR);
            installationManager.createDirectory(Paths.RES_DIR);
            installationManager.createDirectory(Paths.SAVEGAMES_DIR);
            installationManager.createDirectory(Paths.TEMP_DIR);

            // Создание поддиректорий в src
            installationManager.createDirectory(Paths.SRC_MAIN_DIR);
            installationManager.createDirectory(Paths.SRC_TEST_DIR);

            // Создание файлов в main
            installationManager.createFile(Paths.MAIN_JAVA);
            installationManager.createFile(Paths.UTILS_JAVA);

            // Создание поддиректорий в res
            installationManager.createDirectory(Paths.RES_DRAWABLES_DIR);
            installationManager.createDirectory(Paths.RES_VECTORS_DIR);
            installationManager.createDirectory(Paths.RES_ICONS_DIR);

            // Создание файла temp.txt
            installationManager.createFile(Paths.TEMP_TXT);

            totalLog.append(installationManager.getLog());
            totalLog.append("--- Установка завершена ---\n\n");

            // Запись лога установки в temp.txt
            installationManager.writeLogToFile(Paths.TEMP_TXT);


            // Часть 2: Сохранение и архивация
            totalLog.append("--- Запуск сохранения и архивации ---\n");

            // Создание экземпляров GameProgress
            GameProgress game1 = new GameProgress(90, 3, 5, 2500.5);
            GameProgress game2 = new GameProgress(75, 5, 8, 4123.7);
            GameProgress game3 = new GameProgress(100, 2, 1, 100.0);

            // Сохранение игр
            saveManager.saveGame(Paths.SAVEGAMES_DIR + File.separator + Paths.SAVEGAME_FILE_PREFIX + "1" + Paths.SAVEGAME_FILE_EXTENSION, game1);
            saveManager.saveGame(Paths.SAVEGAMES_DIR + File.separator + Paths.SAVEGAME_FILE_PREFIX + "2" + Paths.SAVEGAME_FILE_EXTENSION, game2);
            saveManager.saveGame(Paths.SAVEGAMES_DIR + File.separator + Paths.SAVEGAME_FILE_PREFIX + "3" + Paths.SAVEGAME_FILE_EXTENSION, game3);

            totalLog.append(saveManager.getLog());

            // Архивирование сохраненных файлов
            List<String> filesToZip = saveManager.getSavedFiles();
            zipManager.zipFiles(Paths.ZIP_FILE_PATH, filesToZip);

            totalLog.append(zipManager.getLog());

            // Удаление оригинальных файлов сохранений
            zipManager.deleteOriginalFiles(filesToZip);

            totalLog.append(zipManager.getLog());
            totalLog.append("--- Сохранение и архивация завершены ---\n");

        } catch (Exception e) {
            totalLog.append("Произошла ошибка: ").append(e.getMessage()).append("\n");
            totalLog.append("Выполняется откат установки...\n");
            totalLog.append(installationManager.getLog()); // Добавляем лог отката
        } finally {
            // Вывод общего лога в консоль
            System.out.println(totalLog.toString());
        }
    }
}
