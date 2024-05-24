package rockpaperscissors;

import java.util.*;
import java.io.*;

public class GameEngine {
    /*
    This file path has been obscured for posting.
    The assignment has you create a text file on your computer and assign a few first names with scores.
    Then, you copy the path to that file into the code.
     */
    String pathToFile = ("C:\\Users\\<yourusername>\\Rock-Paper-Scissors\\rating.txt");

    File file = new File(pathToFile);
/*
(This is the list of available game options for reference)
"rock", "fire", "scissors", "snake", "human", "tree", "wolf", "sponge",
"paper", "air", "water", "dragon", "devil", "lightning", "gun"
 */
    // will contain default options if user selects nothing
    private String[] userPicks;

    // default options
    private final String[] defOptions = {"rock", "paper", "scissors"};

    // initial state of program to start the game
    private GameState status = GameState.INIT;

    // if user enters nothing, at least it's not null
    private String playerName = "";

    // default score is zero unless program pulls score from text file based on name
    private int playerScore = 0;

    // when player wins or draws, points are added to users score
    public void addToPlayerScore(int score) {
        this.playerScore += score;
    }

    // getters and setters below
    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public GameState getStatus() {
        return status;
    }

    public void setStatus(GameState status) {
        this.status = status;
    }


    // step one makes it so computer auto wins
    void playGame(String input) {
        // start of the game where we get players name and reference text file for previous player and score
        if (getStatus() == GameState.INIT) {
            setStatus(GameState.OPTIONS);
            setPlayerName(input);
            System.out.printf("Hello, %s", getPlayerName());
            readFile(input);
            return;
        }
        // user picks 'battle options' seperated by a comma, will default to rock, paper, scissors if nothing entered
        // these options selected are what the computer can choose (built to passed assignment test)
        if (getStatus() == GameState.OPTIONS) {
            if (!Objects.equals(input, "")) {
                userPicks = input.split(",");
            } else {
                userPicks = defOptions;
            }
            System.out.print("Okay, let's start");
            setStatus(GameState.RUNNING);
            return;
        }
        // main switchboard of game; compares user pick against computer, shows rating, or exits
        switch (input) {
            case "rock", "fire", "scissors", "snake", "human", "tree", "wolf", "sponge",
                    "paper", "air", "water", "dragon", "devil", "lightning", "gun" -> {
                compareResults(userPicks[randomIndex()], input);
            }
            case "!rating" -> System.out.printf("Your rating: %d", getPlayerScore());
            case "!exit" -> {
                System.out.println("Bye!");
                System.exit(0);
            }
            default -> System.out.print("Invalid input");
        }
    }

    // this method randomizes what the computer picks based on available options.
    private int randomIndex() {
        // creates random object
        Random random = new Random();

        // returns random number based on length of options
        return random.nextInt(userPicks.length);
    }

    // compares what you picked compared to computer, triggers win condition print method
    // options all move in the circular patter
    private void compareResults(String computerPick, String userPick) {
        switch (computerPick) {
            case "rock" -> {
                switch (userPick) {
                    case "rock" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                    case "paper", "air", "water", "dragon", "devil", "lightning", "gun" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case  "fire", "scissors", "snake", "human", "tree", "wolf", "sponge" -> loose(computerPick);
                }
            }
            case "fire" -> {
                switch (userPick) {
                    case "fire" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                    case "air", "water", "dragon", "devil", "lightning", "gun", "rock" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "scissors", "snake", "human", "tree", "wolf", "sponge", "paper" -> loose(computerPick);
                }
            }
            case "scissors" -> {
                switch (userPick) {
                    case "water", "dragon", "devil", "lightning", "gun", "rock", "fire" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "snake", "human", "tree", "wolf", "sponge", "paper", "air" -> loose(computerPick);
                    case "scissors" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "snake" -> {
                switch (userPick) {
                    case  "dragon", "devil", "lightning", "gun", "rock", "fire", "scissors" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "human", "tree", "wolf", "sponge", "paper", "air", "water" -> loose(computerPick);
                    case "snake" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "human" -> {
                switch (userPick) {
                    case "devil", "lightning", "gun", "rock", "fire", "scissors", "snake" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "tree", "wolf", "sponge", "paper", "air", "water", "dragon" -> loose(computerPick);
                    case "human" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "tree" -> {
                switch (userPick) {
                    case "lightning", "gun", "rock", "fire", "scissors", "snake", "human" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "wolf", "sponge", "paper", "air", "water", "dragon", "devil" -> loose(computerPick);
                    case "tree" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "wolf" -> {
                switch (userPick) {
                    case "gun", "rock", "fire", "scissors", "snake", "human", "tree" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "sponge", "paper", "air", "water", "dragon", "devil", "lightning" -> loose(computerPick);
                    case "wolf" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "sponge" -> {
                switch (userPick) {
                    case "rock", "fire", "scissors", "snake", "human", "tree", "wolf" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "paper", "air", "water", "dragon", "devil", "lightning", "gun" -> loose(computerPick);
                    case "sponge" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "paper" -> {
                switch (userPick) {
                    case "air", "water", "dragon", "devil", "lightning", "gun", "rock" -> loose(computerPick);
                    case "paper" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                    case "fire", "scissors", "snake", "human", "tree", "wolf", "sponge" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }

                }
            }
            case "air" -> {
                switch (userPick) {
                    case "scissors", "snake", "human", "tree", "wolf", "sponge", "paper" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "water", "dragon", "devil", "lightning", "gun", "rock", "fire" -> loose(computerPick);
                    case "air" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "water" -> {
                switch (userPick) {
                    case "snake", "human", "tree", "wolf", "sponge", "paper", "air" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "dragon", "devil", "lightning", "gun", "rock", "fire", "scissors" -> loose(computerPick);
                    case "water" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "dragon" -> {
                switch (userPick) {
                    case "human", "tree", "wolf", "sponge", "paper", "air", "water" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "devil", "lightning", "gun", "rock", "fire", "scissors", "snake" -> loose(computerPick);
                    case "dragon" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "devil" -> {
                switch (userPick) {
                    case "tree", "wolf", "sponge", "paper", "air", "water", "dragon" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "lightning", "gun", "rock", "fire", "scissors", "snake", "human" -> loose(computerPick);
                    case "devil" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "lightning" -> {
                switch (userPick) {
                    case "wolf", "sponge", "paper", "air", "water", "dragon", "devil" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "gun", "rock", "fire", "scissors", "snake", "human", "tree" -> loose(computerPick);
                    case "lightning" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }
            case "gun" -> {
                switch (userPick) {
                    case "sponge", "paper", "air", "water", "dragon", "devil", "lightning" -> {
                        win(computerPick);
                        addToPlayerScore(100);
                    }
                    case "rock", "fire", "scissors", "snake", "human", "tree", "wolf" -> loose(computerPick);
                    case "gun" -> {
                        tie(computerPick);
                        addToPlayerScore(50);
                    }
                }
            }

        }
    }

    // prints the bad news
    private void loose(String computerPick) {
        System.out.printf("Sorry, but the computer chose %s", computerPick);
    }

    // still not the news you want
    private void tie(String computerPick) {
        System.out.printf("There is a draw (%s)", computerPick);
    }

    // prints the good news
    private void win(String computerPick) {
        System.out.printf("Well done. The computer chose %s and failed", computerPick);
    }

    // looks for matching player name, if found, applies score to starting score
    // room for bugs, but meets assignment requirements
    private void readFile(String input) {
        try (Scanner scanner = new Scanner(file)) {
            for (;;) {
                if (scanner.hasNextLine()) {
                    if (input.matches(scanner.next())) {
                        setPlayerScore(Integer.parseInt(scanner.next()));
                        scanner.close();
                        break;
                    }
                    else {
                        scanner.nextLine();
                    }
                } else {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + pathToFile);
        }
    }
}
