package numbers;

public class SquareNumber {
    // is the number a perfect square
    boolean isSquare(long input) {
        double number = Math.sqrt(input);

        int number2 = (int) number;

        return number % number2 == 0;
    }
}
