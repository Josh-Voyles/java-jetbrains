package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static StringBuilder textString = new StringBuilder();

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        File file = new File(args[0]);

        // this is our program that analyzes text
        TextAssessor ta = new TextAssessor();

        // temp for testing predicted ingested file
        readFile(file);

        // we must trim the white space off the end to get accurate sentence count
        ta.buildModel(textString.toString().trim());

        // prints string stats
        System.out.println(ta);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        // prints scores based on users input
        ta.scores(in.nextLine());

    }
    // reads the argument the computer passes into the main method
    private static void readFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            for (;;) {
                if (scanner.hasNext()) {
                    textString.append(scanner.next()).append(" ");
                } else {
                    scanner.close();
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }
    }
}
