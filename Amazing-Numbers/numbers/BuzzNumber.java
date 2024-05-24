package numbers;

import java.util.Objects;

public class BuzzNumber {


    // message inherits one of the texts below
    String message;
    final String neither = "is neither divisible by 7 nor does it end with 7";
    final String is = "is divisible by 7";
    final String ends = "ends with 7";
    final String both = "is divisible by 7 and ends with 7";

    // a String that indicates whether the number is a buzz number
    String buzz;

    // buzz number is sever
    final long seven = 7;
    long remainder;
    int last_digit;

    // buzz number is used for print statement in numbers
    void setBuzz(String buzz) {

        this.buzz = buzz;
    }

    // returns the buzz number for print statement in Numbers
    String getBuzz() {

        return buzz;
    }

    // used for print statement in Numbers
    void setMessage(String message) {

        this.message = message;
    }

    // used for earlier lesson comparison (saving just in case)
    int getLast_digit() {

        return last_digit;
    }

    // finds last digit in the string for use
    void setLast_digit(long integer) {
        String number = Long.toString(integer);
        String num = Character.toString(number.charAt(number.length() - 1));
        this.last_digit = Integer.parseInt(num);
    }

    // used in earlier lesson (saving just in case)
    void setRemainder(long remainder) {

        this.remainder = remainder;
    }

    // a series of steps for determining if buzz number and it's properties (will simply in last lesson if needed)
    void findBuzzNumber(long lng) {

        // could probably eliminate this and move to method (used with earlier assignment)
        setRemainder(lng % seven);
        setLast_digit(lng);


        // this was a print requirement in earlier assignment, but I use it for comparison now
        if (checkBuzzAndSeven()) {
            setMessage(both);
            setBuzz("It is a Buzz number.");
        } else if (checkBuzzNumber()) {
            setMessage(is);
            setBuzz("It is a Buzz number.");
        } else if (checkEnd()) {
            setMessage(ends);
            setBuzz("It is a Buzz number.");
        } else {
            setMessage(neither);
            setBuzz("It is not a Buzz number.");
        }
    }

    // buzz number is divisible by 7 with no remainder (convert 0 to long for same type)
    private boolean checkBuzzNumber() {
        return Objects.equals(remainder, (long) 0);
    }

    // returns true if ends with 7
    private boolean checkEnd() {
        return Objects.equals(getLast_digit(), 7);
    }

    // returns true if buzz number and ends with seven
    private boolean checkBuzzAndSeven() {
        return checkEnd() && checkBuzzNumber();
    }
}
