/*
Jet Brains Number Base Program Assignment

Code by: Josh Voyles

This program converts a number chosen by the user from a base to a base and then prints the result in the console.
Contains "some" error checking but it was not an assignment requirement
 */

package converter;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BaseConverter bc = new BaseConverter();

        // get user preference to convert to or from decimal to base or vice versa
        for (;;) {
            String numberToConvert = "";

            // User inputs which bases to convert to/from
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");

            // gets to/from bases with error checking
            int fromBase = bc.getBases(in.next());
            int toBase = bc.getBases(in.next());

            // loop allows user to keep converting numbers with the chosen bases unless /back
            while (!Objects.equals(numberToConvert, "/back")) {
                System.out.printf("\nEnter number in base %d to convert to base %d (To go back type /back) ",
                        fromBase, toBase);

                numberToConvert = in.next();

                if (Objects.equals(numberToConvert, "/back")) {
                    break;
                }
                String[] beforeDecimal;
                String[] afterDecimal;

                // if it's a decimal number, we split into two parts via this path
                if (numberToConvert.contains(".")) {
                    String[] numbers = numberToConvert.split("\\.");
                    beforeDecimal = bc.getSourceInput(numbers[0]);
                    afterDecimal = bc.getSourceInput(numbers[1]);

                    System.out.print("Conversion result: ");

                    String preDecimalConverted = bc.covertByChosenBase(
                            bc.convertToDecimalNumber(beforeDecimal, fromBase), toBase);

                    // prints pre decimal conversion
                    System.out.print(preDecimalConverted);

                    // prints decimal
                    System.out.print(".");

                    String postDecimalConverted = bc.covertByBasePostDecimal(
                            bc.convertToDecimalAfterDP(afterDecimal, fromBase), toBase);

                    // prints post decimal conversion
                    System.out.println(postDecimalConverted);
                } else {

                    String[] nTC = bc.getSourceInput(numberToConvert);

                    System.out.print("Conversion result: ");

                    String convertedNumber = bc.covertByChosenBase(
                            bc.convertToDecimalNumber(nTC, fromBase), toBase);

                    System.out.print(convertedNumber);
                }
            }
        }
    }
}
