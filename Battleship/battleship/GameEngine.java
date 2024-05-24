package battleship;

import java.util.*;

public class GameEngine {
    final Scanner in = new Scanner(System.in);

    final int PLAYER_ONE = 0;
    final int PLAYER_TWO = 1;
    final int WIN_COND = 5;
    final int NUM_OF_SHIPS = 5;
    final int NUM_OF_PLAYERS = 2;
    final int ZERO = 0;

    /*
    We need three grids for each player, we can store two per array.
    The utility grid is scanned and modded for calculating wins
    The fogged field is shown to opposing player
    The grid view is shown to own player without mods for win checking
     */

    // accounts for both players stored in this array
    String[][][] utilityGrids;
    String[][][] foggedGrids;
    String[][][] playerGrids;


    // count the number of ships sunk for each player
    int playerOneSinkCounter = ZERO;
    int playerTwoSinkCounter = ZERO;

    // store both sink counters in single array, so we can alternate
    int[] playerSinkCounters = {playerOneSinkCounter, playerTwoSinkCounter};

    // We can store valid coordinates here to check if ship is sunk
    String[][] playerShipCoordinates1 = new String[NUM_OF_PLAYERS][NUM_OF_SHIPS];
    String[][] playerShipCoordinates2 = new String[NUM_OF_PLAYERS][NUM_OF_SHIPS];

    // key is used for validating valid coordinates input
    HashMap<String, Integer> gridKey = new HashMap<>();

    Ship ac = new Ship("Aircraft Carrier", 5);
    Ship bs = new Ship("Battleship", 4);
    Ship sub = new Ship("Submarine", 3);
    Ship crs = new Ship("Cruiser", 3);
    Ship des = new Ship("Destroyer", 2);

    Ship[] ships = {ac, bs, sub, crs, des};

    // constructor that also builds the play field
    public GameEngine() {
        // load key upon object creation
        gridKey.put("A", 1);
        gridKey.put("B", 2);
        gridKey.put("C", 3);
        gridKey.put("D", 4);
        gridKey.put("E", 5);
        gridKey.put("F", 6);
        gridKey.put("G", 7);
        gridKey.put("H", 8);
        gridKey.put("I", 9);
        gridKey.put("J", 10);

        // building all the grids we will display and modify
        utilityGrids = buildGrid();
        foggedGrids = buildGrid();
        playerGrids = buildGrid();
    }

    void playGame(int p) {

        // this code makes sure each player doesn't attack their own field
        if (p == PLAYER_ONE) {
            p = PLAYER_TWO;
        } else {
            p = PLAYER_ONE;
        }

        String[] input = splitCoordinates(getUserInput());

        takeShot(input, p);

        sunkCheck(p);

        if (playerSinkCounters[p] == WIN_COND) {
            System.out.println("You sank the last ship. You won. Congratulations!");
            System.exit(0);
        }
    }

    // Here, we update all 3 grids at the same time since we use them for different function and display
    void takeShot(String[] input, int p) {

        if (Objects.equals(utilityGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])], "S")) {
            System.out.println("\nYou hit a ship!\n");
        }
        else if (Objects.equals(utilityGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])], "O") ||
                Objects.equals(foggedGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])], "X")) {
            utilityGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])] = "X";
            foggedGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])] = "X";
            playerGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])] = "X";
            System.out.println("\nYou hit a ship!\n");
        } else {
            utilityGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])] = "M";
            foggedGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])] = "M";
            playerGrids[p][gridKey.get(input[0])][Integer.parseInt(input[1])] = "M";
            System.out.println("\nYou missed!\n");

        }
    }
    // we can the utility grid to see if we sank a ship
    void sunkCheck(int p) {
        for (int i = 0; i < NUM_OF_SHIPS; i++) {
            int count = 0;

            String[] coord1 = splitCoordinates(playerShipCoordinates1[p][i]);
            String[] coord2 = splitCoordinates(playerShipCoordinates2[p][i]);

            if (Objects.equals(coord1[0], coord2[0])) {
                for (int j = 0; j < ships[i].getSIZE(); j++) {
                    if (Objects.equals(utilityGrids[p][returnValue(coord1[0])][Integer.parseInt(coord1[1]) + j], "X")) {
                        count++;
                    }
                }
            } else {
                for (int x = 0; x < ships[i].getSIZE(); x++) {
                    if (Objects.equals(utilityGrids[p][returnValue(coord1[0]) + x][Integer.parseInt(coord1[1])], "X")) {
                        count++;
                    }
                }
            }
            if (count == ships[i].getSIZE()) {
                playerSinkCounters[p]++;

                if (playerSinkCounters[p] != WIN_COND) {
                    System.out.println("You sank a ship! Specify a new target:");
                }

                if (Objects.equals(coord1[0], coord2[0])) {
                    for (int y = 0; y < ships[i].getSIZE(); y++) {
                        utilityGrids[p][returnValue(coord1[0])][Integer.parseInt(coord1[1]) + y] = "S";
                    }
                } else {
                    for (int q = 0; q < ships[i].getSIZE(); q++) {
                        utilityGrids[p][returnValue(coord1[0]) + q][Integer.parseInt(coord1[1])] = "S";
                    }
                }
            }
        }
    }

    // code used to build all 6 grids
    String[][][] buildGrid() {
        String[][][] playerGrid = new String[NUM_OF_PLAYERS][11][11];
        for (int z = 0; z < NUM_OF_PLAYERS; z++) {
            //place empty spot at top left corner
            playerGrid[z][0][0] = " ";

            // adds 1-10 top of grid
            for (int x = 1; x < 11; x++) {
                playerGrid[z][0][x] = String.valueOf(x);
            }

            // adds A-J left side of grid
            for (int y = 1; y < 11; y++) {
                playerGrid[z][y][0] = new String(Character.toChars((64 + y)));
            }

            // creates fog of war in grid
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    playerGrid[z][i][j] = "~";
                }
            }
        }
        return playerGrid;
    }

    void placePlayerShips(int p) {
        printPlayerGrid(p);

        for (int i = 0; i < NUM_OF_SHIPS; i++) {

            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n",
                    ships[i].getNAME(), ships[i].getSIZE());

            while (true) {
                String[] input = getUserInput().split(" ");

                // the splitCoordinates function is used to concatenate arrays over size 3
                String[] coordinates_1 = splitCoordinates(input[0]);
                String[] coordinates_2 = splitCoordinates(input[1]);

                // this if statement swaps coordinates to easily work with place ship function if horizontal
                if (Integer.parseInt(coordinates_1[1]) > Integer.parseInt(coordinates_2[1])) {
                    String[] c1 = coordinates_2;
                    String[] c2 = coordinates_1;
                    coordinates_1 = c1;
                    coordinates_2 = c2;
                }

                // checks alphabetical order and swaps if needed
                if (!checkAlphabetOrder(coordinates_1, coordinates_2)) {
                    String[] c1 = coordinates_2;
                    String[] c2 = coordinates_1;
                    coordinates_1 = c1;
                    coordinates_2 = c2;
                }

                // validates user input corresponds to actual coordinates; prompts and returns to start if false
                if (!validateUserInput(coordinates_1) || !validateUserInput(coordinates_2)) {
                    System.out.println("Invalid coordinates! Please reenter.");
                    continue;
                }

                // makes sure coordinates matches ship size
                if (!countCoordinates(coordinates_1, coordinates_2, ships[i].getSIZE())) {
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", ships[i].getNAME());
                    continue;
                }

                // ensures that user selects coordinates that are sequential
                if (!Objects.equals(coordinates_1[0], coordinates_2[0])) {
                    if (!Objects.equals(coordinates_1[1], coordinates_2[1])) {
                        System.out.println("Error! Wrong ship location! Try again:");
                        continue;
                    }
                }
                // makes sure placement isn't too close to another ship. If so, continue. Out of bounds passes
                try {
                    if (!checkPlacement(coordinates_1, coordinates_2, ships[i].getSIZE(), p)) {
                        System.out.println("Error! You place it too close to another one. Try again:");
                        continue;
                    }
                } catch (IndexOutOfBoundsException ignored){}

                //after validating input, we can place the ship
                placeShip(coordinates_1, coordinates_2, ships[i].getSIZE(), p);

                //store coordinates for sunk check
                playerShipCoordinates1[p][i] = coordinates_1[0] + coordinates_1[1];
                playerShipCoordinates2[p][i] = coordinates_2[0] + coordinates_2[1];

                printPlayerGrid(p);
                break;
            }

        }

    }

    void printPlayField(int p) {
        // this code makes sure the fields print in the right order and swaps players
        if (p == PLAYER_ONE) {
            p = PLAYER_TWO;
        } else {
            p = PLAYER_ONE;
        }
        //prints opponent on top
        printPlayerFogged(p);

        System.out.println("---------------------");

        // this code makes sure the fields print in the right order and swaps players
        if (p == PLAYER_ONE) {
            p = PLAYER_TWO;
        } else {
            p = PLAYER_ONE;
        }
        //print own grid on the bottom
        printPlayerGrid(p);
    }

    void printPlayerGrid(int p){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(playerGrids[p][i][j] + " ");
            }
            System.out.println();
        }
    }

    void printPlayerFogged(int p){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(foggedGrids[p][i][j] + " ");
            }
            System.out.println();
        }
    }
    void placeShip(String[] coord1, String[] coord2, int size, int player) {

        if (Objects.equals(coord1[0], coord2[0])) {
            for (int i = 0; i < size; i++) {
                utilityGrids[player][returnValue(coord1[0])][Integer.parseInt(coord1[1]) + i] = "O";
                playerGrids[player][returnValue(coord1[0])][Integer.parseInt(coord1[1]) + i] = "O";
            }
        } else {
            for (int i = 0; i < size; i++) {
                utilityGrids[player][returnValue(coord1[0]) + i][Integer.parseInt(coord1[1])] = "O";
                playerGrids[player][returnValue(coord1[0]) + i][Integer.parseInt(coord1[1])] = "O";
            }
        }
    }

    // when we check placement, we need to make sure the boats don't touch hence the -1 and + 2 values
    boolean checkPlacement(String[] coord1, String[] coord2, int size, int p) {

        if (Integer.parseInt(coord1[1] ) == 1) {
            return true;
        }

        if (Objects.equals(coord1[0], coord2[0])) {
            for (int i = 0; i < size + 2; i++) {
                if (Objects.equals(utilityGrids[p][returnValue(coord1[0]) - 1][Integer.parseInt(coord1[1]) + i], "O")) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size + 2; i++) {
                if (Objects.equals(utilityGrids[p][returnValue(coord1[0]) + i - 1][Integer.parseInt(coord1[1])], "O")) {
                    return false;
                }
            }
        } return true;
    }

    Boolean countCoordinates(String[] coord1, String[] coord2, int size) {
        if (Objects.equals(coord1[0], coord2[0])) {
            return Math.abs((Integer.parseInt(coord2[1]) - Integer.parseInt(coord1[1])) + 1) == size;
            } else {
            String hex1 = String.format("%h", coord1[0].charAt(0));
            int uni1 = Integer.parseInt(hex1, 16);

            String hex2 = String.format("%h",coord2[0].charAt(0));
            int uni2 = Integer.parseInt(hex2, 16);

            return uni2 - uni1 + 1 == size;
        }
    }

    int returnValue(String key) {
        return gridKey.get(key);
    }

    String getUserInput() {
        return in.nextLine();
    }

    Boolean validateUserInput(String[] input) {
        return gridKey.containsKey(input[0].toUpperCase()) &&
                gridKey.containsValue(Integer.parseInt(input[1]));
    }

    String[] splitCoordinates(String input) {
        String[] coordinates = input.split("");

        if (coordinates.length > 2) {
            String[] in = new String[2];
            in[0] = coordinates[0];
            in[1] = coordinates[1] + coordinates[2];
            return in;
        }
        return coordinates;
    }

    Boolean checkAlphabetOrder(String[] coord1, String[] coord2) {
        String hex1 = String.format("%h", coord1[0].charAt(0));
        int uni1 = Integer.parseInt(hex1, 16);

        String hex2 = String.format("%h",coord2[0].charAt(0));
        int uni2 = Integer.parseInt(hex2, 16);

        return uni1 <= uni2;
    }

    void passPlayer() {
        System.out.println("Press Enter and pass the move to another player");

        while (true) {
            String input = in.nextLine();

            if (!input.trim().isEmpty()) {
                System.out.println("Error! Please press enter to pass the other player:");

            } else {
                for (int i = 0; i < 50; i++) System.out.println();
                break;
            }
        }
    }
}
