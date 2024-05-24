package numbers;

import java.util.Objects;

// duck numbers are numbers that have zeros except for the first number (133202 is duck) (011234 is not)
public class DuckNumber {

    boolean isDuck(long input) {
        String number = String.valueOf(input);
        // we only care about zeros unless it's the first one so i = 1
        for (int i = 1; i < number.length(); i++) {
            if (Objects.equals(number.charAt(i), '0')) {
                return true;
            }
        }
        return false;
    }
}
