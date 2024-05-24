package numbers;

// gapful numbers are numbers where the first and last digit concatenated have zero remainder with the original num
public class Gapful {

    boolean isGapful(long input) {

        String number = String.valueOf(input);

        if (number.length() > 2) {
            String first = String.valueOf(number.charAt(0));
            String last = String.valueOf(number.charAt(number.length() - 1));
            String first_last = first + last;
            long lng = Long.parseLong(number);
            long fl = Long.parseLong(first_last);
            return lng % fl == 0;
        }
        return false;
    }
}
