package numbers;

import java.util.Objects;

// it's a spy number if the sum of digits equals the product of digits
public class Spy {

    boolean isSpy(long input) {

        // initialize starting values for what we are comparing
        long added = 0;
        long multiplied = 1;

        // convert long to String, so we can do weird stuff to it
        String digits = String.valueOf(input);

        // convert each character of the string to its own long number
        // then, we either add or multiply by the previous number
        for (int i = 0; i < digits.length(); i++ ) {
            long number = Long.parseLong(String.valueOf(digits.charAt(i)));

            added += number;
            multiplied *= number;

        }
        return (Objects.equals(added, multiplied));
    }
}
