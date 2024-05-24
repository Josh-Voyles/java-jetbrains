package rockpaperscissors;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        GameEngine rps = new GameEngine();

        for (;;) {
            if (rps.getStatus() == GameState.INIT) {
                System.out.print("Enter your name: ");
            }
            rps.playGame(in.nextLine());
            System.out.println();
        }


    }
}
