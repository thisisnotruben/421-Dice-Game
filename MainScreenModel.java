/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1
 */

package pkg421dicegame;

import java.util.Arrays;

public class MainScreenModel {

    public static final int MAX_DIE = 3;
    public static final int MAX_ROLLS = 3;
    public static final int MAX_PLAYERS = 2;

    private int[][] die;
    private int[] playerRolls;
    private int currentPlayer = 0;
    public int player1points = 0;
    public int player2points = 0;

    public MainScreenModel() {
       
        die = new int[MAX_PLAYERS][MAX_DIE];
        playerRolls = new int[MAX_PLAYERS];
        currentPlayer = 0;
    }

    public void setDie(int die1, int die2, int die3) {
        if (die1 > 0)
            die[currentPlayer][0] = die1;
        
        if (die2 > 0)
            die[currentPlayer][1] = die2;
        
        if (die3 > 0)
            die[currentPlayer][2] = die3;
        
        playerRolls[currentPlayer]++;
        /*if ((currentPlayer == 1 && playerRolls[1] == playerRolls[0]) || MAX_ROLLS == playerRolls[currentPlayer]) {
//            switch player if (it's 2nd player and 2nd player rolls equals 1st player rolls) || equal to max rolls
            switchPlayers();
        }*/
    }

    public int getDie(int index) {
       
        return die[currentPlayer][index];
    }

    public boolean switchPlayers() {
        //if ((currentPlayer == 1 && playerRolls[1] == playerRolls[0]) || MAX_ROLLS == playerRolls[currentPlayer]) {
//          switch player if (it's 2nd player and 2nd player rolls equals 1st player rolls) || equal to max rolls
            if (getPlayerTurn() == 1) {
                points();
    //              reset roll counter to 0 if player 2, because it's a new round when player 2 finishes
                playerRolls[0] = 0;
                playerRolls[1] = 0;
                currentPlayer = (getPlayerTurn() == 1) ? 0 : 1;
                return true;
            }
            currentPlayer = (getPlayerTurn() == 1) ? 0 : 1;
            return false;
        //}
    }

    public int getPlayerTurn() {
        return currentPlayer;
    }

    public int getCurrentPlayerRollCount() {
        return playerRolls[currentPlayer];
    }
    
    public void points()
    {
        if (is421(die[0][0], die[0][1], die[0][2]))
        {
            player1points++;
        }
        else if (is421(die[1][0], die[1][1], die[1][2]))
        {
            player2points++;
        }
        else if (isThreeOfAKind(die[0][0], die[0][1], die[0][2]))
        {
            if (!isThreeOfAKind(die[1][0], die[1][1], die[1][2]))
            {    
                player1points++;
            }
            else
            {
                if (die[0][0] >= die[1][0])
                    player1points++;
                else
                    player2points++;
            }
        }
        else if (isTwoOfAKind(die[0][0], die[0][1], die[0][2]))
        {
            if (isThreeOfAKind(die[1][0], die[1][1], die[1][2]))
            {
                player2points++;
            }
            else if (isTwoOfAKind(die[1][0], die[1][1], die[1][2]))
            {
                int n1 = duplicateNumber(die[0][0], die[0][1], die[0][2]);
                        
                int n2 = duplicateNumber(die[1][0], die[1][1], die[1][2]);
                if (n1 >= n2)
                {
                    player1points++;
                }
                else
                {
                    player2points++;
                }
            }
            else
            {
                player1points++;
            }
        }
        else
        {
            if (isThreeOfAKind(die[1][0], die[1][1], die[1][2]) || isTwoOfAKind(die[1][0], die[1][1], die[1][2]))
            {
                player2points++;
            }
            else
            {
                if (sortNumbers(die[0][0], die[0][1], die[0][2]) >= sortNumbers(die[1][0], die[1][1], die[1][2]))
                {
                    player1points++;
                }
                else
                {
                    player2points++;
                }
            }
        }
        
    }
    
    public boolean is421(int n1, int n2, int n3)
    {
        int value = (n1 * 100) + (n2 * 10) + n3;
        return value == 421 || value == 124 || value == 142 || value == 412 || value == 241 || value == 214;
    }
    
    public boolean isThreeOfAKind(int n1, int n2, int n3)
    {
        return n1 == n2 && n1 == n3;
    }

    public boolean isTwoOfAKind(int n1, int n2, int n3)
    {
        return (!isThreeOfAKind(n1, n2, n3) && (n1 == n2 || n1 == n3 || n2 == n3));
    }
    
    public int duplicateNumber(int n1, int n2, int n3)
    {
        int value = 0;
        if (n1 == n2)
        {
            value = (n1*100) + (n2*10) + n3;
        }
        else if(n1 == n3)
        {
            value = (n1*100) + (n3*10) + n2;
        }
        else
        {
            value = (n2*100) + (n3*10) + n1;
        }
        return value;
    }
    
    public int sortNumbers(int n1, int n2, int n3)
    {
        int numbers[] = new int[3];
        numbers[0] = n1;
        numbers[1] = n2;
        numbers[2] = n3;
        Arrays.sort(numbers);
        return (numbers[0]*100) + (numbers[1] * 10) + numbers[2];
    }
}