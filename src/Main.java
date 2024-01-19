import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;

public class Main {
    public static void main(String[] args) {

        FileHandler users = new FileHandler("src/Users4.txt");
        File file = new File("src/users.txt");
        System.out.println(users);
        System.out.println(users.getLength());
        users.rename("src/Users5.txt");
        System.out.println(users);


        task_1();


    }
    public static void task_1(){
        ArrayList<String> listPath = new ArrayList<>(
                List.of("src/Users_test_1.txt", "src/Users_test_2.txt",
                        "src/Users_test_3.txt"));
        for (String file: listPath) {
            new FileHandler(file);
        }
    }
}