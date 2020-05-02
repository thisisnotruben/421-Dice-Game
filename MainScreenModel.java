/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

public class MainScreenModel {

    public static final int MAX_DIE = 3;
    public static final int MAX_ROLLS = 3;
    public static final int MAX_PLAYERS = 2;

    private int[][] die;
    private int[] playerRolls;
    private int currentPlayer = 0;

    public MainScreenModel() {
        die = new int[MAX_PLAYERS][MAX_DIE];
        playerRolls = new int[MAX_PLAYERS];
        currentPlayer = 0;
    }

    public void setDie(int die1, int die2, int die3) {
        die[currentPlayer][0] = die1;
        die[currentPlayer][1] = die2;
        die[currentPlayer][2] = die3;
        playerRolls[currentPlayer]++;
        if ((currentPlayer == 1 && playerRolls[1] == playerRolls[0]) || MAX_ROLLS == playerRolls[currentPlayer]) {
//            switch player if (it's 2nd player and 2nd player rolls equals 1st player rolls) || equal to max rolls
            switchPlayers();
        }
    }

    public int getDie(int index) {
        return die[currentPlayer][index];
    }

    public void switchPlayers() {
        if (getPlayerTurn() == 1) {
//              reset roll counter to 0 if player 2, because it's a new round when player 2 finishes
            playerRolls[0] = 0;
            playerRolls[1] = 0;
        }
        currentPlayer = (getPlayerTurn() == 1) ? 0 : 1;
    }

    public int getPlayerTurn() {
        return currentPlayer;
    }

    public int getCurrentPlayerRollCount() {
        return playerRolls[currentPlayer];
    }

}
