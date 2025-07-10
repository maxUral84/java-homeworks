import java.io.File;

public class Paths {
    public static final String BASE_DIR = "Games"; // Или ваш путь, например, "D:" + File.separator + "Games"
    public static final String SRC_DIR = BASE_DIR + File.separator + "src";
    public static final String RES_DIR = BASE_DIR + File.separator + "res";
    public static final String SAVEGAMES_DIR = BASE_DIR + File.separator + "savegames";
    public static final String TEMP_DIR = BASE_DIR + File.separator + "temp";

    public static final String SRC_MAIN_DIR = SRC_DIR + File.separator + "main";
    public static final String SRC_TEST_DIR = SRC_DIR + File.separator + "test";

    public static final String MAIN_JAVA = SRC_MAIN_DIR + File.separator + "Main.java";
    public static final String UTILS_JAVA = SRC_MAIN_DIR + File.separator + "Utils.java";

    public static final String RES_DRAWABLES_DIR = RES_DIR + File.separator + "drawables";
    public static final String RES_VECTORS_DIR = RES_DIR + File.separator + "vectors";
    public static final String RES_ICONS_DIR = RES_DIR + File.separator + "icons";

    public static final String TEMP_TXT = TEMP_DIR + File.separator + "temp.txt";

    // Новые константы для сохранения
    public static final String SAVEGAME_FILE_PREFIX = "save";
    public static final String SAVEGAME_FILE_EXTENSION = ".dat";
    public static final String ZIP_FILE_NAME = "savegames.zip";
    public static final String ZIP_FILE_PATH = SAVEGAMES_DIR + File.separator + ZIP_FILE_NAME;

}
