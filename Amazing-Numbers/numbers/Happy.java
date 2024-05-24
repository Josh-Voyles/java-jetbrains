package numbers;

import java.util.Arrays;
import java.util.Objects;
//  a happy number is a number that reaches 1 after
//  a sequence during which the number is replaced by the sum of each digit squares.
public class Happy {
    // there's problem a better way to do this, but I wanted numbers not decimal representations
    boolean isHappy(long input) {

        String number = String.valueOf(input);

        char[] num = number.toCharArray();

        int total = 0;

        for (int i = 0; i < number.length(); i++) {
            int n = Integer.parseInt(Character.valueOf(num[i]).toString());

            total += Math.pow(n, 2);
        }

        // this while loop just repeats the process above with the new total
        while (String.valueOf(total).toCharArray().length > 1) {

            int previous = total;

            char[] n2 = String.valueOf(total).toCharArray();

            for (int j = 0; j < String.valueOf(previous).length(); j++) {
                int y = Integer.parseInt(Character.valueOf(n2[j]).toString());

                if (Objects.equals(total, previous)) {
                    total = 0;
                }
                total += Math.pow(y, 2);
            }
        }

        return Objects.equals(total, 1);
    }
}
