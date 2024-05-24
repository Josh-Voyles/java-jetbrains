package numbers;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // new numbers object and initialize its status to default state
        Numbers amazing_numbers = new Numbers(State.INIT);

        // simple 'splash screen' of program
        amazing_numbers.Welcome();

        // automatically turns on
        while (true) {
            // request user enters a request (Can be 1-4 entries)
            amazing_numbers.SolicitRequest();

            // checks on processes user input
            amazing_numbers.checkInput(in.nextLine());

            // print results on the screen based on user input
            amazing_numbers.Results(amazing_numbers.status);

        }

    }
}
