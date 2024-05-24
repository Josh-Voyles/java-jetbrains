package battleship;

public class Main {

    public static void main(String[] args) {
        final int PLAYER_ONE = 0;
        final int PLAYER_TWO = 1;
        final int WIN_COND = 5;

        int player = PLAYER_ONE;

        GameEngine gameEngine = new GameEngine();

        System.out.println("Player 1, place your ships on the game field");

        gameEngine.placePlayerShips(PLAYER_ONE);

        gameEngine.passPlayer();

        System.out.println("Player 2, place your ships on the game field");

        gameEngine.placePlayerShips(PLAYER_TWO);

        gameEngine.passPlayer();

        while(gameEngine.playerSinkCounters[player] != WIN_COND) {

            gameEngine.printPlayField(player);

            System.out.printf("Player %d, it's your turn:\n", player + 1);

            gameEngine.playGame(player);

            gameEngine.passPlayer();

            // this code alternates players
            if (player == PLAYER_ONE) {
                player = PLAYER_TWO;
            } else {
                player = PLAYER_ONE;
            }
        }
    }

}


