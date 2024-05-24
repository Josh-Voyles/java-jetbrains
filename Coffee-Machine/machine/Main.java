/*
25 Dec 22
I'm putting this project on Github to open it up to feedback and provide assistance for anyone trying this themselves.
This project is from the Jet Brains Academy Coffee Machine Project.
When I finished this project, I'd only completed two coding classes at UMGC; one of those being Introductory Java.
Being my 3rd project on JBA, I was familiar with many concepts and even some OOP.
However, this project presented many challenges. Using a single point to accept user input was not easy.
I'm normally not accustomed to exiting a method without finishing its full function.
However, the assignment guidelines made it seem like that's the way it needed to be.
If you've taken the time to look at my code, I welcome feedback on my journey to better programming.
You might see many errors, but this is my current understanding at this point.
 */

package machine;

import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        final Scanner in = new Scanner(System.in);

        // Initialize new coffee machine with default values
        CoffeeMachine coffeemaker = new CoffeeMachine(400, 540, 120, 9, 550);

        // Initialize coffee maker default values into inventory array for use later
        coffeemaker.inventory = new int[] {coffeemaker.water, coffeemaker.milk,
                coffeemaker.beans, coffeemaker.cups, coffeemaker.money};

        // initial call to the menu
        coffeemaker.menu();

        // will be false when user selects "exit"
        while(coffeemaker.on()) {
            coffeemaker.run(in.nextLine());  // the only source of user input in the entire program

        }
    }
}
