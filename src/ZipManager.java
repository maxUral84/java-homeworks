import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipManager {
    private StringBuilder log = new StringBuilder();

    public boolean zipFiles(String zipFilePath, List<String> filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filesToZip) {
                File fileToZip = new File(filePath);
                if (!fileToZip.exists()) {
                    log.append("Файл не найден для архивации: ").append(filePath).append("\n");
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                    log.append("Файл добавлен в архив: ").append(fileToZip.getName()).append("\n");
                } catch (IOException e) {
                    log.append("Ошибка при добавлении файла ").append(fileToZip.getName()).append(" в архив: ").append(e.getMessage()).append("\n");
                }
            }
            log.append("Архив создан: ").append(zipFilePath).append("\n");
            return true;
        } catch (IOException e) {
            log.append("Ошибка при создании архива ").append(zipFilePath).append(": ").append(e.getMessage()).append("\n");
            return false;
        }
    }

    public void deleteOriginalFiles(List<String> filesToDelete) {
        for (String filePath : filesToDelete) {
            File file = new File(filePath);
            if (file.exists() && file.delete()) {
                log.append("Удален оригинальный файл: ").append(filePath).append("\n");
            } else {
                log.append("Не удалось удалить оригинальный файл: ").append(filePath).append("\n");
            }
        }
    }

    public String getLog() {
        return log.toString();
    }
}
