/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1

Owner: Ruben Alvarez Reyes
Ruben worked on all of this

contains:

    fields:
        public static final int MAX_DIE
        public static final int MAX_ROLLS
        public static final int MAX_PLAYERS
        public static final int MAX_ROUNDS
        private int[][] die
        private int[][] score
        private int[] playerRolls
        private boolean[] dieSelection
        private int currentPlayer
        private int currentRound

    methods:
        public void setDie(int, int, int)
        public int getDie(int)
        public int getDie(int, int)
        public void switchPlayers()
        public int getPlayerTurn()
        public int getPlayerRollCount(int)
        public void setSelectedDie(int, boolean)
        public boolean getSelectedDie(int)
        public int getPlayerScore(int)
        public int getCurrentRound()
        public int getWinner()
        public int calculateScore(int)
 */

package capstone;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MainScreenModel {

    public static final int MAX_DIE = 3;
    public static final int MAX_ROLLS = 3;
    public static final int MAX_PLAYERS = 2;
    public static final int MAX_ROUNDS = 11;

    private int[][] die;
    private int[][] score;
    private int[] playerRolls;

    private boolean[] dieSelection;

    private int currentPlayer;
    private int currentRound;

    public MainScreenModel() {
        die = new int[MAX_PLAYERS][MAX_DIE];
        score = new int[MAX_PLAYERS][MAX_ROUNDS];
        playerRolls = new int[MAX_PLAYERS];
        dieSelection = new boolean[MAX_DIE];
    }

    public void setDie(int die1, int die2, int die3) {

        if (currentRound == MAX_ROUNDS) {
//            game ended
            return;
        }

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
        calculateScore(currentPlayer);
//      switch player if (it's 2nd player and 2nd player rolls equals 1st player rolls) || equal to max rolls
        if ((currentPlayer == 1 && playerRolls[1] == playerRolls[0]) || MAX_ROLLS == playerRolls[currentPlayer]) {
//            apply points when player 2 is done
            if (currentPlayer == 1) {
                int winner = (calculateScore(0) >= calculateScore(1)) ? 0 : 1;
                score[winner][currentRound++]++;

//                whoever wins becomes player 1
                if (winner == 1) {
//                    store player 2 data into temp
                    int[] tempDie = die[1];
                    int[] tempScore = score[1];
                    int tempPlayerRolls = playerRolls[1];
//                    set player 1 data to player 2 slot
                    die[1] = die[0];
                    score[1] = score[0];
                    playerRolls[1] = playerRolls[0];
//                    set player 2 data to player 1 slot
                    die[0] = tempDie;
                    score[0] = tempScore;
                    playerRolls[0] = tempPlayerRolls;
                }
            }
            switchPlayers();
        }
    }

    public int getDie(int index) {
        return die[currentPlayer][index];
    }

    public int getDie(int player, int dieIndex) {
        return die[player][dieIndex];
    }

    public void switchPlayers() {
        if (currentRound == MAX_ROUNDS) {
//            game ended
            return;
        } else if (getPlayerTurn() == 1) {
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

    public int getPlayerRollCount(int player) {
        return playerRolls[player];
    }

    public void setSelectedDie(int index, boolean dieSelected) {
        if (currentRound == MAX_ROUNDS) {
//            game ended
            return;
        }
        dieSelection[index] = dieSelected;
    }

    public boolean getSelectedDie(int index) {
        return dieSelection[index];
    }

    public int getPlayerScore(int player) {
        return IntStream.of(score[player]).sum();
    }

    public int getCurrentRound() {
        return currentRound + 1;
    }

    public int getWinner() {
//        return player who's sum of points is greater when game is finished
        if (currentRound == MAX_ROUNDS) {
            return (getPlayerScore(0) >= getPlayerScore(1)) ? 0 : 1;
        }
        return -1;
    }

    public int calculateScore(int player) {

        int score;

        int[] check421 = die[player].clone();
        Arrays.sort(check421);
        if (check421[0] == 4 && check421[1] == 2 && check421[2] == 1) {
//            4-2-1 win
            score = Integer.MAX_VALUE;
        } else {
//            find duplicates
            int kind = 0;
            for (int i = 0; i < die[player].length; i++) {
                for (int j = i + 1; j < die[player].length; j++) {
                    if (die[player][i] == die[player][j]) {
                        kind++;
                    }
                }
            }
//            calculate score with weights
            if (kind == 3) {
//            three of a kind
                score = (int) (Integer.MAX_VALUE * 0.75) + IntStream.of(die[player]).sum();
            } else if (kind == 1) {
//            two of a kind
                score = (int) (Integer.MAX_VALUE * 0.5) + IntStream.of(die[player]).sum();
            } else {
//            no matches
                score = (int) (Integer.MAX_VALUE * 0.25) + IntStream.of(die[player]).sum();
            }
        }
        return score;
    }
}
