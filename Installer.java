import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Installer {
    private StringBuilder log = new StringBuilder();

    public void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                log.append("Directory created: " + path + "\n");
            } else {
                log.append("Failed to create directory: " + path + "\n");
            }
        } else {
            log.append("Directory already exists: " + path + "\n");
        }
    }

    public void createFile(String path) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                log.append("File created: " + path + "\n");
            } else {
                log.append("File already exists: " + path + "\n");
            }
        } catch (IOException e) {
            log.append("Failed to create file: " + path + ". Exception: " + e.getMessage() + "\n");
        }
    }

    public void writeLog() {
        try (FileWriter writer = new FileWriter(Paths.TEMP_TXT)) {
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println("Failed to write log file: " + e.getMessage());
        }
    }

    public String getLog() {
        return log.toString();
    }
}
