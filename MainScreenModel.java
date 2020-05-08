/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

import java.util.Arrays;

public class MainScreenModel {

    public static final int MAX_DIE = 3;
    public static final int MAX_ROLLS = 3;
    public static final int MAX_PLAYERS = 2;

    private int[][] die;
    private int[] playerRolls;
    private int currentPlayer = 0;
    private boolean[] dieSelection;

    public MainScreenModel() {
        die = new int[MAX_PLAYERS][MAX_DIE];
        playerRolls = new int[MAX_PLAYERS];
        dieSelection = new boolean[MAX_DIE];
        currentPlayer = 0;
    }

    public void setDie(int die1, int die2, int die3) {

        if (!dieSelection[0]) {
            die[currentPlayer][0] = die1;
        }
        if (!dieSelection[1]) {
            die[currentPlayer][1] = die2;
        }
        if (!dieSelection[2]) {
            die[currentPlayer][2] = die3;
        }
        playerRolls[currentPlayer]++;
        if ((currentPlayer == 1 && playerRolls[1] == playerRolls[0]) || MAX_ROLLS == playerRolls[currentPlayer]) {
//            switch player if (it's 2nd player and 2nd player rolls equals 1st player rolls) || equal to max rolls
            switchPlayers();
        }
    }

    public int getDie(int index) {
        return die[currentPlayer][index];
    }

    public int getDie(int playerIndex, int dieIndex) {
        return die[playerIndex][dieIndex];
    }

    public void switchPlayers() {
        if (getPlayerTurn() == 1) {
//              reset roll counter to 0 if player 2, because it's a new round when player 2 finishes
            playerRolls[0] = 0;
            playerRolls[1] = 0;
        }
        currentPlayer = (getPlayerTurn() == 1) ? 0 : 1;
        Arrays.fill(dieSelection, false);
    }

    public int getPlayerTurn() {
        return currentPlayer;
    }

    public int getPlayerRollCount(int playerIndex) {
        return playerRolls[playerIndex];
    }

    public boolean setSelectedDie(int index, boolean dieSelected) {
        if (dieSelected) {
//        only two die max can be selected at any given time
//        if at max capacity, returns false to show unsuccessful
            int selections = 0;
            for (boolean b : dieSelection) {
                if (b && ++selections == 2) {
                    return false;
                }
            }
        }
        dieSelection[index] = dieSelected;
        return true;
    }

    public boolean getSelectedDie(int index) {
        return dieSelection[index];
    }

}
