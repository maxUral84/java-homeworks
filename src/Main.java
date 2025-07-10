import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GameInstaller installer = new GameInstaller();
        // Указываем любой путь (можно изменить на нужный вам)
        String basePath = "Games";

        try {
            String log = installer.installGame(basePath);
            System.out.println("Installation log:\n" + log);

            // Более точная проверка на ошибки
            if (log.contains("[ERROR]") || log.contains("ERROR:")) {
                System.err.println("Installation completed with errors!");
            } else {
                System.out.println("Installation completed successfully!");
            }
        } catch (IOException e) {
            System.err.println("Critical installation error: " + e.getMessage());
        }
    }
}
