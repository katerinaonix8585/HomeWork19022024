import javax.imageio.IIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private String path;

    public FileHandler(String path) {
        this.path = path;
        createIfNotExists();
    }

    public String read(){
        try (Scanner scanner = new Scanner(new File(this.path))){
            String result = "";
            while (scanner.hasNextLine()) {
                String newLine = scanner.nextLine();
                result += newLine + '\n';
            }
            scanner.close();
            return result;
        }catch (FileNotFoundException e){
            System.out.println("Файл не найден" + e.getMessage());
        }
        return "";
    }

    private void createIfNotExists(){
        File file = new File(this.path);
        try {
            boolean result = file.createNewFile();
            if (result){
                System.out.println("Файл успешно создан");
            }else {
                System.out.printf("Файл [%s] уже существует.%n", this.path);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public String toString(){
        File file = new File(this.path);
        return String.format("Данные о текущем файле: длина файла составляет %d, наименование файла %s, абсолютный путь к файлу %s.%n", file.length(), file.getName(), file.getAbsoluteFile());
    }

    public String getLength(){
        File file = new File(this.path);
        if (file.length() < 1024){
            return String.format("%d B", file.length());
        } else if (file.length() < 1024 * 1024) {
            return String.format("%d KB", file.length());
        } else if (file.length() < 1024 * 1024 *1024) {
            return String.format("%d MB", file.length());
        } else if (file.length() < 1024 * 1024 * 1024 * 1024) {
            return String.format("%d GB", file.length());
        } else {return String.format("%.2f TB", file.length());}

    }

    public void rename(String newPath){
        File oldName = new File(this.path);
        File newName = new File(newPath);
        oldName.renameTo(newName);
    }


}
