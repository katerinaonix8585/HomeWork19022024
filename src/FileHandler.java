import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Handler;
import java.util.stream.Collectors;
import java.util.Scanner;

public class FileHandler {
    private String path;

    public static void createFiels(ArrayList<String> list) {
        for (String path: list) {
            File file = new File(path);
            try{
                file.createNewFile();
            }catch (IOException e){
                System.out.println("Файл создать не удалось: " + e.getMessage());
            }
        }
    }

    public FileHandler(String path) {
        this.path = path;
        createIfNotExists();
    }
    public String read() {
        try (Scanner scanner = new Scanner(new File(this.path))){
           String result = "";
            while (scanner.hasNextLine()){
                String newLine = scanner.nextLine();
                result += newLine + '\n';
            }
            scanner.close();
            return result;
        }catch (FileNotFoundException e){
            System.out.println("Файл не найден: " + e.getMessage());
        }
        return "";
    }

    private void createIfNotExists(){
        File file = new File(this.path);
        boolean result = create(this.path);
        if (result){
            System.out.println("Файл успешно создан.");
        }else{
            System.out.printf("Файл [%s] уже существует.%n", this.path);
        }
    }

    private boolean create(String path) {
        File file = new File(path);
        try{
            return file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String getLength(){
        int coef = 1000;
        File file = new File(this.path);
        long length = file.length();
        if (length < coef){
            return length + " bytes";
        }else if (length < Math.pow(coef, 2)){
            return String.format("%.2f KB", (double) length / coef);
        }else if (length < Math.pow(coef, 3)){
            return String.format("%.2f MB", (double) length / Math.pow(coef, 2));
        }else if (length < Math.pow(coef, 4)){
            return String.format("%.2f GB", (double) length / Math.pow(coef, 3));
        }else{
            return "ХВАТИТ!!";
        }
    }

    private String getLength2(){
        File file = new File(this.path);
        long length = file.length();

        HashMap<String, Double> sizePrefs = new HashMap<>();
        sizePrefs.put("bytes", 1.0);
        sizePrefs.put("KB", 1000.0);
        sizePrefs.put("MB", Math.pow(1000, 2));
        sizePrefs.put("GB", Math.pow(1000, 3));

        List<String> keyList = sizePrefs
                .keySet()
                .stream()
                .sorted(Comparator.comparing(x -> sizePrefs.get(x)))
                .collect(Collectors.toList());

        for (String pref : keyList){
            double resultSize = length / sizePrefs.get(pref);
            if (resultSize < 1000){
                return  String.format("%.2f %s", resultSize, pref);
            }
        }
        return "ХВАТИТ!!";
    }

    public boolean rename(String newName){
        File newFile = new File(newName);
        File file = new File(this.path);
        if (!newFile.exists()){
            boolean result = file.renameTo(newFile);
            if (result){
                this.path = newName;
            }
            return result;
        }
        return false;
    }

    @Override
    public String toString() {
        File file = new File(this.path);
        return String.format(
                "%s (%s) (path: %s)  %n",
                file.getName(),
                getLength2(),
                file.getAbsolutePath()
        );
    }

    public int search(String searchString){
        int numberStr = 0;
        try (Scanner scanner = new Scanner(new File(this.path))){
            while (scanner.hasNextLine()){
                ++numberStr;
                String newLine = scanner.nextLine();
                if (newLine.contains(searchString))
                return numberStr;
            }
            scanner.close();
            return -1;
        }catch (FileNotFoundException e){
            System.out.println("Файл не найден: " + e.getMessage());

        }
        return -1;

}

    public void write(String string){
        try (FileWriter writer = new FileWriter(this.path, true)) {
            writer.append(string + "\n");
            System.out.println("Добавлена строка: " + string);
        }catch (IOException e){
            System.out.println("Ошибка" + e.getMessage());
        }

    }

    public void writeFromTerminal(){
        Scanner scanner = new Scanner(System.in);
            while (true){
                String newString = scanner.nextLine();
                if (newString.equals("END")){
                    return;
                }else {
                    write(newString);
                }
            }
    }

    
    //ЗАДАЧА 1 - ВАРИАНТ 1
    public boolean deleteFile(String string){
        File file = new File(string);
        if (file.exists()){
           return file.delete();
        } else {
            System.out.println("Файл не существует");
            return false;
        }
    }
    
    // ЗАДАЧА 2


    public void copyFileToFile(String newPath){
        File newFile = new File(newPath);
        ArrayList<String> array = new ArrayList<>();
        // создаем массив из строк старого файла
        try (Scanner scanner = new Scanner(new File(this.path))){
            while (scanner.hasNextLine()){
                String newLine = scanner.nextLine();
                array.add(newLine + '\n');
            }
            scanner.close();
        } catch (FileNotFoundException e){
        System.out.println("Файл не найден: " + e.getMessage());
    }
        //переписываем из массива строки в новый файл
        try (FileWriter writer = new FileWriter(newFile, true)) {
            for (int i = 0; i < array.size(); i++) {
                writer.write(array.get(i));
            }
        }catch (IOException e){
            System.out.println("Ошибка" + e.getMessage());
        }
    }

    // ЗАДАЧА 3

    public void copyToFile (List<String> list){
        try (FileWriter writer = new FileWriter(this.path);) {
            for (String line: list) {
                writer.append(line + '\n');
        }
            writer.close();
        }catch (IOException e){
            System.out.println("Ошибка" + e.getMessage());
        }
    }

    // ЗАДАЧА 4

    public void readLines (int numberLine, int quantityLines)
    {
        try (Scanner scanner = new Scanner(new File(this.path));){
            int i = 1;
            while (scanner.hasNextLine()) {
                String newLine = scanner.nextLine();
                if (i >= numberLine && i < (numberLine + quantityLines))
                {
                    System.out.println(newLine);
                }
                  else if (i >= numberLine + quantityLines) {
                     break;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        }
    }





//    {
//        try (Scanner scanner = new Scanner(new File(this.path))){
//            int numberCurrentLine = 0;
//            while (scanner.hasNextLine()){
//                if ((numberCurrentLine >= numberLine) && (numberCurrentLine <= numberLine + quantityLines)){
//                String newLine = scanner.nextLine();
//                System.out.println(newLine);
//            }else if (numberCurrentLine > quantityLines + numberLine) {break;}
//            numberCurrentLine++;
//
//        }
//            scanner.close();
//        }catch (FileNotFoundException e){
//            System.out.println("Файл не найден: " + e.getMessage());
//        }
//      }
    }







