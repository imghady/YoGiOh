package view;

import java.util.Scanner;

public class ScanInput {
    private static Scanner scanner=new Scanner(System.in);
    public static String getInput(){
        return scanner.nextLine();
    }
}
