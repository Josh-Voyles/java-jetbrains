/*
Jet Brains Number Base Program Assignment

Code by: Josh Voyles

This program converts a number chosen by the user from a base to a base and then prints the result in the console.
 */

package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

public class BaseConverter {

    private final HashMap<Integer, String> conversionFromDecimalTable = new HashMap<>(36);
    private final HashMap<String, String> conversionToDecimalTable = new HashMap<>(36);

    // build table when we create the object
    public BaseConverter() {
        String baseNChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < baseNChars.length(); i++) {
            conversionFromDecimalTable.put(i, String.valueOf(baseNChars.charAt(i)));
            conversionToDecimalTable.put(String.valueOf(baseNChars.charAt(i)), String.valueOf(i));
        }
    }

    // coverts 1-9 and A-Z to their respective numerical representation
    protected String[] getSourceInput(String number) {

        String[] stringValues = new String[number.length()];

        int i = 0;

        while (true) {
            try {
                String[] values = number.split("");
                for (String value : values) {
                    if (Objects.equals(value, ".")) {
                        stringValues[i] = value;
                    } else {
                        stringValues[i] = conversionToDecimalTable.get(value.toUpperCase());
                    }
                    i++;
                }
                return stringValues;
            // catches if user enters something like "!"
            } catch (Exception e) {
                System.out.println("You did not enter a valid entry.");
            }
        }
    }

    // gets user base, displays error if not int
    protected int getBases(String base) {
        while (true) {
            try {
                if (Objects.equals(base, "/exit")) {
                    System.exit(0);
                }
                return Integer.parseInt(base);
            } catch (Exception e) {
                System.out.println("You did not enter a valid number");
            }
        }
    }

    // converting to decimal number with a formula
    protected BigInteger convertToDecimalNumber(String[] number, int base) {
        BigInteger baseTenDecimal = new BigInteger("0");
        BigInteger b = new BigInteger(String.valueOf(base));
        for (int i = 0; i < number.length; i++) {
            if (Objects.equals(number[0], "")) {
                i++;
            }
            baseTenDecimal = baseTenDecimal.add(BigInteger.valueOf(
                    Long.parseLong(number[i])).multiply(b.pow(number.length - 1 - i)));
        }
        return baseTenDecimal;
    }

    // converts decimal number to specified radix
    protected String covertByChosenBase(BigInteger baseTenDecimal, int base) {
        // use stack to store remainder values (converted number)
        Stack<String> remainders = new Stack<>();

        // starting point
        BigInteger workingNumber = baseTenDecimal;

        while (!workingNumber.equals(BigInteger.valueOf(0))) {
            // remainders are what we want to display, over 9 uses alphabet

            remainders.push(conversionFromDecimalTable.get(
                    Integer.parseInt((workingNumber.divideAndRemainder(BigInteger.valueOf(base)))[1].toString())));
            // we divide the answer over and over till left with nothing
            workingNumber = workingNumber.divide(BigInteger.valueOf(base));
        }
        // since we push to stack, we need to reverse the input
        Collections.reverse(remainders);

        if (remainders.isEmpty()) {
            return "0";

        } else {
            StringBuilder resultBuilder = new StringBuilder();
            remainders.forEach(resultBuilder::append);

            return resultBuilder.toString();
        }
    }

    // uses algorithm to convert converted numbers to a number past the decimal point
    protected BigDecimal convertToDecimalAfterDP(String[] source, int base) {
        BigDecimal decimal = new BigDecimal("0");
        for (int i = 0; i < source.length ;i++) {
            decimal = decimal.add(BigDecimal.valueOf(Double.parseDouble(source[i]) * Math.pow(base, -i - 1)));
        }
        return decimal;
    }

    // converts decimal number to new conversion using selected base
    protected String covertByBasePostDecimal(BigDecimal baseTenDecimal, int base) {
        BigDecimal decimal = baseTenDecimal;
        Stack<String> remainders = new Stack<>();
        BigInteger integer;

        for (int i = 0; i < 5; i++) {
            decimal = decimal.multiply(BigDecimal.valueOf(base));
            integer = decimal.toBigInteger();
            String s = conversionFromDecimalTable.get(integer.intValue());
            decimal = decimal.subtract(decimal.setScale(0, RoundingMode.FLOOR));
            remainders.push(s);
        }
        StringBuilder resultBuilder = new StringBuilder();
        remainders.forEach(resultBuilder::append);

        return resultBuilder.toString();
    }
}
