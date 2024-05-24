package machine;

import java.util.*;

class CoffeeMachine {

    State status = State.MENU;  // set the initial state to be menu
    int water;
    int milk;
    int beans;
    int cups;
    int money;
    String[] ingredients_s = {"water", "milk", "beans,", "cups"};
    int[] inventory = new int[5]; // list for coffee maker states
    int[] ingredients = new int[5]; // will input coffee machine values here
    int q; // variable used to assign as inventory_ask questions
    int loop = 0; // variable used to loop through fill commands

    // drink options and a drink list with all three options
    int[] espresso = new int[] {250, 0, 16, 1, -4};
    int[] latte = new int[] {350, 75, 20, 1, -7};
    int[] cappuccino = new int[] {200, 100, 12, 1, -6};
    Object[] drink_list = {espresso, latte, cappuccino};

    // this string array is used in the loop to fill the machine
    String[] inventory_ask = {"Write how many ml of water you want to add:",
        "Write how many ml of milk you want to add:",
        "Write how many grams of coffee beans you want to add:",
        "Write how many disposable cups you want to add:"};

    // constructor
    CoffeeMachine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
    }
    // used in buy option; pull specific drink based on user input
    Object getDrink(int j) {
        return drink_list[j-1];  // -1 since user options are 1-3
    }
    // moves selected drink into an array with can be iterated upon
    void convertDrink(int j) {
        for (int i = 0; i < 5; i++) {
            int[] drink = (int[]) getDrink(j);
            ingredients[i] = drink[i];
        }
    }
    // changes status, called in each menu option method
    void setStatus(State status) {
        this.status = status;
    }

    // will keep the loop going as long as status is not set to exit
    boolean on() {
        return status != State.EXIT;
    }

    // prints machine stats on the screen
    void machineStats() {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("""
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money\n""", inventory[0], inventory[1], inventory[2], inventory[3], inventory[4]);
        setStatus(State.MENU);
        menu();
    }
    // main switchboard for the menu options, loops through here several times for each method.
    void run(String input) {
        if (on()) {
            if (status == State.MENU) {
                status = State.getState(input);
            }
        }
        switch (status) {
            case BUY -> buyCoffee(input);
            case FILL -> fillMachine(input, inventory_ask);
            case TAKE -> takeMoney();
            case REMAINING -> machineStats();
        }
    }
    // prints menu options on the screen
    void menu() {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
    }

    // fills machine with desired ingredients
    void fillMachine(String input, String[] inventory_ask) {
        try {
            if (loop > 0) { // don't want to try and parse 'fill'
                int total = Integer.parseInt(input);
                inventory[q-1] = total + inventory[q-1]; // -1 since starting on second loop through
            }
            if (q == 4) {  // after asking all 3 questions, loop ends.
                q = 0; // resets fill method for next usage
                setStatus(State.MENU);
                menu();
                loop = 0;
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;  // return statement so q doesn't increment on invalid input
        }
        // placed at the end so it doesn't ask the first question twice
        System.out.println(inventory_ask[q]);
        q++;
        loop++;
    }

    // sets money to zero
    void takeMoney() {
        System.out.printf("I gave you $%d\n", inventory[4]);
        inventory[4] = 0;  // sets money total to zero
        setStatus(State.MENU); // returns user to menu
        menu();
    }
    // lets the user select and buy the desired coffee drink
    void buyCoffee(String input) {
        int end = 0;  // variable to count up to indicating you have enough resources

        if (status == State.BUY) {

            // ensures that this line isn't duplicated when it loops back through again with drink
            if (Objects.equals(input, "buy")) {
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, " +
                        "back - to main menu:");
            }

            int j; // variable to pull the required drink option

            // I'm sure there's a better way, but I forgot
            if (Objects.equals(input, "1") || Objects.equals(input, "2") || Objects.equals(input, "3")) {
                // converts 1, 2, or 3 to int to use in for loop
                j = Integer.parseInt(input);
            } else if (Objects.equals(input, "back")) {
                // if user enters back, status is changed back to menu and exit
                setStatus(State.MENU);
                return;
            } else {
                return;
            }

            // dumps selected drink into ingredients array
            convertDrink(j);

            // for loop checks every required ingredient against available inventory
            for (int i = 0; i < 5; i++) {

                // prints message if not enough ingredients
                if (inventory[i] - ingredients[i] < 0) {
                    System.out.printf("Sorry, not enough %s!\n", ingredients_s[i]);
                    setStatus(State.MENU);
                    menu();
                    return;
                } else {
                    end++; // we inch closer to ending the loop and returning to menu
                }
            }
            // comparing cups possible with cups needed
            if (end > 3) {
                System.out.println("I have enough resources, making you coffee!");
                setStatus(State.MENU);

                // loops through the inventory and subtracts what's needed for drink
                for (int z = 0; z < 5; z++) {
                    inventory[z] = inventory[z] - ingredients[z];
                }
            }
        }
        // brings up menu before prompting for user input
        menu();
    }
}

/*
I initially used this subclass to create a drink object for each drink.
However, for some reason it was creating a stack overflow error.
There was some sort of issue between the shared variables.

class drinkOption extends CoffeeMachine {
    public drinkOption(int water, int milk, int beans, int cups, int money) {

        super(water, milk, beans, cups, money);

    }
}
 */