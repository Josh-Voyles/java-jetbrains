package numbers;

import java.util.Arrays;

// palindromic numbers are the same forwards as they are backwards (I.E. 101)
public class Palindromic {

    boolean isPalindromic(long input) {

        String number = String.valueOf(input);

        int x = number.length() - 1;
        // create character array to number string into
        char[] backwards = new char[number.length()];

        for (char i : number.toCharArray()) {
            backwards[x] = i;
            --x;
        }
        return Arrays.equals(number.toCharArray(), backwards);  // if matching return true
    }
}
