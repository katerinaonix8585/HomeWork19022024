import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;

public class Main {

    public static void deleteFile_2(String string){
        File file = new File(string);
        if (file.exists()){
            file.delete();
        } else {
            System.out.println("Файл не существует");
        }
    }
    public static void main(String[] args) {

        // ЗАДАЧА 1 - Вариант 1
        FileHandler file = new FileHandler("src/users_1.txt");
        file.deleteFile("src/users_1.txt");

        // ЗАДАЧА 1 - Вариант 2

        deleteFile_2("src/Test.txt");

        // ЗАДАЧА 2

        FileHandler users = new FileHandler("src/users.txt");
        users.copyFileToFile("src/users_new1.txt");

        // ЗАДАЧА 3

        users.copyToFile(List.of("Элемент 9", "Элемент 10", "Элемент 11"));

        // ЗАДАЧА 4
       FileHandler users_2 = new FileHandler("src/users_new1.txt");
       users_2.readLines(3, 5);


    }

}