public class Main {
    public static void main(String[] args) {
        Installer installer = new Installer();

        // Создание директорий
        installer.createDirectory(Paths.BASE_DIR);
        installer.createDirectory(Paths.SRC_DIR);
        installer.createDirectory(Paths.RES_DIR);
        installer.createDirectory(Paths.SAVEGAMES_DIR);
        installer.createDirectory(Paths.TEMP_DIR);

        installer.createDirectory(Paths.SRC_MAIN_DIR);
        installer.createDirectory(Paths.SRC_TEST_DIR);

        installer.createDirectory(Paths.RES_DRAWABLES_DIR);
        installer.createDirectory(Paths.RES_VECTORS_DIR);
        installer.createDirectory(Paths.RES_ICONS_DIR);

        // Создание файлов
        installer.createFile(Paths.MAIN_JAVA);
        installer.createFile(Paths.UTILS_JAVA);
        installer.createFile(Paths.TEMP_TXT);

        // Запись лога в temp.txt
        installer.writeLog();

        // Вывод лога в консоль
        System.out.println(installer.getLog());
    }
}
