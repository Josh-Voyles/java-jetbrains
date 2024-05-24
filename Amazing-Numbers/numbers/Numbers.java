package numbers;

import java.util.Objects;

public class Numbers {

    // all possible objects in the 'game'
    // Is there a better way than placing these here?
    DuckNumber duck = new DuckNumber();
    Palindromic palindromic = new Palindromic();
    Gapful gapful = new Gapful();
    BuzzNumber buzz = new BuzzNumber();
    Spy spy = new Spy();
    SquareNumber square = new SquareNumber();
    JumpingNumber jumping = new JumpingNumber();
    Happy happy = new Happy();

    // this is the array we are going to hold different input values
    long first_number;
    long second_number;

    // these variables are used for checking if user inputs an incorrect option
    StringBuilder shit_list = new StringBuilder();
    boolean is_shit = false;

    // counts number of shit items
    int count = 0;

    // referenced when checking if user selected category is true
    String[] parameters;
    // also referenced if true for negative categories
    String[] non_parameters;

    // variable for the status
    State status;

    // list of possible properties
    String[] options = {"even", "odd", "buzz", "duck", "palindromic", "gapful", "spy", "square", "sunny", "jumping",
            "happy", "sad"};

    // constructor
    public Numbers(State status) {
        this.status = status;
    }

    // getters and setters
    void setFirst_number(long first_number) {
        this.first_number = first_number;
    }

    long getFirst_number() {
        return first_number;
    }

    long getSecond_number() {
        return second_number;
    }

    void setSecond_number(long second_number) {
        this.second_number = second_number;
    }

    void setStatus(State status) {
        this.status = status;
    }

    boolean matchOptions(String input) {
        for (String i : options) {

            // for checking, we will remove the minus symbol
            input = input.replace('-', ' ');

            // trim the white space after we remove the minus symbol
            input = input.trim();

            // check the input against our list
            if (input.equalsIgnoreCase(i)) {
                return true;
            }
        }
        return false;
    }

    // this entire section validates the input meets our requirements
    void checkInput(String input) {
        // split our entries into an array for keying
       String[] numbers = input.split(" ");

        // check all the numbers (running backwards allows us to build upon our inputs)
        switch (numbers.length) {
            // this section validates both or one category and assigns it to category parameter if it's correct
            default:
                // this code block verifies user inputs one or more of the available options
                if (numbers.length > 2) {
                    for (int i = 2; i < numbers.length; i++) {
                        if (!matchOptions(numbers[i])) {
                            shit_list.append(numbers[i].toUpperCase()).append(" ");
                            count++;

                            if (!is_shit) {
                                is_shit = true;
                            }
                        }
                    }

                    // if the options the user picked is bad, we will return to start again
                    if (is_shit) {
                        availableProperties();
                        setStatus(State.INIT);
                        is_shit = false;
                        shit_list.setLength(0);
                        count = 0;
                        return;
                    }
                }

                // we will use a StringBuilder to create our list of positive and negative parameters
                StringBuilder nons = new StringBuilder();
                StringBuilder ones = new StringBuilder();

                if (numbers.length > 2) {
                    for (int i = 2; i < numbers.length; i++) {
                        if (numbers[i].contains("-")) {
                            nons.append(numbers[i].replace('-', ' ').trim()).append(" ");
                        } else {
                            ones.append(numbers[i]).append(" ");
                        }
                    }
                }

                // so we can use the numbers elsewhere in the program
                parameters = ones.toString().split(" ");
                // we have a clean list with no minus sign of parameters we don't want to use
                non_parameters = nons.toString().split(" ");

                // this just checks for opposites of the same type
                for (String parameter : parameters) {
                    for (String nonParameter : non_parameters) {
                        if (parameter.matches(nonParameter)) {
                            System.out.printf("The request contains mutually exclusive properties: [%s, -%s]\n" +
                                            "There are no numbers with these properties.",
                                    parameter.toUpperCase(), parameter.toUpperCase());
                            return;
                        }
                    }
                }

                // this code block checks to verify entries are not mutually exclusive
                if (numbers.length > 3) {
                    if (!mutuallyExclusive(parameters) && !mutuallyExclusive(non_parameters)) {
                        if (status == State.INIT) {
                            setStatus(State.MORE_THAN);
                        }
                    } else {
                        return;
                    }
                }
                // if not set by the code block above
                if (status == State.INIT) {
                    setStatus(State.THREE);
                }

            // validates and assigns second number for iteration
            case 2:
                try {
                    setSecond_number(Long.parseLong(numbers[1]));
                    if (status == State.INIT) {
                        setStatus(State.TWO);
                    }
                    if (getSecond_number() < 0) {
                        throw new Exception();
                    }

                } catch (Exception y) {
                    PrintError2();
                    break;
                }

                // validates first number and assigns it to first number variable
            case 1:
                try {
                    setFirst_number(Long.parseLong(numbers[0]));

                    if (getFirst_number() < 0) {
                        throw new Exception();
                    }

                    if (status == State.INIT) {
                        setStatus(State.ONE);
                    }
                    if (Objects.equals(numbers[0], "0")) {
                        Goodbye();
                        System.exit(0);
                    }

                } catch (Exception x) {
                    PrintError1();
                }
        }
    }

    // checks if first number is odd or even
    boolean oddEven(long lng) {
        return Objects.equals(lng % 2, (long) 0);
    }

    // the sections below are all dedicated to printing stuff on the screen
    void PrintError1() {
        System.out.println("\nThe first parameter should be a natural number or zero.\n");
    }

    protected void PrintError2() {
        System.out.println("The second parameter should be a natural number.\n");
    }

    protected void Goodbye() {
        System.out.println("\nGoodbye!");
    }

    void Welcome() {
        System.out.println("""
                
                Welcome to Amazing Numbers!
                                
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);

    }

    void SolicitRequest () {
        System.out.print("Enter a request: ");
    }

    void Results(State status) {
        // first calculates if it's a buzz number or not based on first number entered
        buzz.findBuzzNumber(getFirst_number());

        switch (status) {
            // runs when only one number is entered
            case ONE -> System.out.printf("""
                                            
                            Properties of %,d
                                    buzz: %b
                                    duck: %b
                             palindromic: %b
                                  gapful: %b
                                     spy: %b
                                  square: %b
                                   sunny: %b
                                 jumping: %b
                                   happy: %b
                                     sad: %b
                                    even: %b
                                     odd: %b
                                              
                            """, getFirst_number(),
                    Objects.equals(buzz.getBuzz(), "It is a Buzz number."),
                    duck.isDuck(getFirst_number()),
                    palindromic.isPalindromic(getFirst_number()),
                    gapful.isGapful(getFirst_number()),
                    spy.isSpy(getFirst_number()),
                    square.isSquare(getFirst_number()),
                    square.isSquare(getFirst_number() + 1),
                    jumping.isJumping(getFirst_number()),
                    happy.isHappy(getFirst_number()),
                    !happy.isHappy(getFirst_number()),
                    oddEven(getFirst_number()),
                    !oddEven(getFirst_number()));

            // runs when only two numbers are entered
            case TWO -> {
                for (long i = 0; i < getSecond_number(); i++) {
                    runValues();
                }
            }

            // runs when categories are selected, THREE state left over from earlier code
            case THREE, MORE_THAN -> {
                for (int i = 0; i < getSecond_number(); ) {
                    if (checkAllProperties()) {
                        runValues();
                        i++;

                    } else {
                        setFirst_number(getFirst_number() + 1);
                        buzz.findBuzzNumber(getFirst_number());
                    }
                }
            }
        }
        setStatus(State.INIT);
        System.out.println();
    }

    // when the program finds a matching numbers to parameter, it prints that number with all its properties
    void runValues() {
        String[] values = {
                Objects.equals(buzz.getBuzz(), "It is a Buzz number.") ? "buzz, " : "",
                duck.isDuck(getFirst_number()) ? "duck, " : "",
                palindromic.isPalindromic(getFirst_number()) ? "palindromic, " : "",
                gapful.isGapful(getFirst_number()) ? "gapful, " : "",
                spy.isSpy(getFirst_number()) ? "spy, " : "",
                square.isSquare(getFirst_number()) ? "square, " : "",
                square.isSquare(getFirst_number() + 1) ? "sunny, " : "",
                jumping.isJumping(getFirst_number()) ? "jumping, " : "",
                happy.isHappy(getFirst_number()) ? "happy, " : "",
                !happy.isHappy(getFirst_number()) ? "sad, " : "",
                oddEven(getFirst_number()) ? "even" : "odd"};


        // output results for each iteration
        System.out.printf("%,d is ", getFirst_number());
        for (String w : values) {
            if (!w.equals("")) {
                System.out.print(w);
            }
        }
        System.out.println();

        setFirst_number(getFirst_number() + 1);
        buzz.findBuzzNumber(getFirst_number());
    }

    // when user selects correct first parameter, the program matches it to list switch and returns true or false
    boolean isCategoryTrue(String input) {
        switch (input.toLowerCase()) {
            case "even" -> {
                return oddEven(getFirst_number());
            }
            case "odd" -> {
                return !oddEven(getFirst_number());
            }
            case "buzz" -> {
                return Objects.equals(buzz.getBuzz(), "It is a Buzz number.");
            }
            case "duck" -> {
                return duck.isDuck(getFirst_number());
            }
            case "palindromic" -> {
                return palindromic.isPalindromic(getFirst_number());
            }
            case "gapful" -> {
                return gapful.isGapful(getFirst_number());
            }
            case "spy" -> {
                return spy.isSpy(getFirst_number());
            }
            case "sunny" -> {
                return square.isSquare(getFirst_number() + 1);

            }
            case "square" -> {
                return square.isSquare(getFirst_number());

            }
            case "jumping" -> {
                return jumping.isJumping(getFirst_number());
            }
            case "happy" -> {
                return happy.isHappy(getFirst_number());
            }
            case "sad" -> {
                return !happy.isHappy((getFirst_number()));
            }
        }
        return false;
    }

    // compares properties user entered with the current number while iterating, if true runs values
    boolean checkAllProperties() {
        for (int j = 0; j < non_parameters.length; j++) {
            // this will exclude the properties we don't want
            if (isCategoryTrue(non_parameters[j]) && !Objects.equals(non_parameters[j], "")) {
                return false;
            }
        }
        for (int q = 0; q < parameters.length; q++) {
            // this will exclude the properties we don't want
            if (!isCategoryTrue(parameters[q]) && !Objects.equals(parameters[q], "")) {
                return false;
            }
        }
        return true;
    }

    // returns false if mutually exclusive properties are not found
    boolean mutuallyExclusive(String[] p) {
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
               // this switch checks over the input and makes sure selection is not mutually exclusive
                switch (p[i]) {
                    case "odd" -> {
                        if (p[j].matches("even")) {
                           printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "even" -> {
                        if (p[j].matches("odd")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "sunny" -> {
                        if (p[j].matches("square")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "square" -> {
                        if (p[j].matches("sunny")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "duck" -> {
                        if (p[j].matches("spy")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "spy" -> {
                        if (p[j].matches("duck")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "happy" -> {
                        if (p[j].matches("sad")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                    case "sad" -> {
                        if (p[j].matches("happy")) {
                            printExclusive(i, j, p);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    // prints mutually exclusive properties, accounts for double negative
    void printExclusive(int i, int j, String[] p) {
        if (non_parameters.length > 1) {
            System.out.printf("""
                The request contains mutually exclusive properties: [-%s, -%s]
                There are no numbers with these properties.
                """, p[i].toUpperCase(), p[j].toUpperCase());
        } else {
            System.out.printf("""
                The request contains mutually exclusive properties: [%s, %s]
                There are no numbers with these properties.
                """, p[i].toUpperCase(), p[j].toUpperCase());
        }
    }

    // if a mutually exclusive property is found, then this method triggers
    void availableProperties() {
        if (count > 1) {
            System.out.printf("The properties [%s] are wrong.\n", shit_list.toString().trim());
        } else {
            System.out.printf("The property [%s] is wrong.\n", shit_list.toString().trim());
        }
        System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, " +
                "GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n");
    }
}

