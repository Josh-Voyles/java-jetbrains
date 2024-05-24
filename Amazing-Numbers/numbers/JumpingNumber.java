package numbers;

// a jumping number is one that the adjacent digits inside the number differ by 1.
public class JumpingNumber {

    boolean isJumping(long input) {
        // map digits into an array to analyze each one individually
        int[] number = Long.toString(input).chars().map(c -> c - '0').toArray();

        // will only return false if the next digits is not plus or minus 1
        for (int i = 0; i < String.valueOf(input).length() - 1; i++) {
            if (!(Math.abs(number[i] - number[i + 1]) == 1)) {
                return false;
            }
        }
        return true;
    }
}
