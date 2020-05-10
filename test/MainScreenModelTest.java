/*
Ruben Alvarez Reyes
Javier Felix
CSCV-335 Spring 2020
Capstone: 4-2-1

Owner: Ruben Alvarez Reyes
Ruben worked on all of this

tests:

    test 07: switchPlayerAfter3Rolls
    test 15: switchPlayerOnCommand
    test 14: player2CanRollAsMuchAsPlayer1Did
    test 16: pointIsGivenAfterRoundIsComplete
    test 08: score421BeatsAllOtherScores
    test 19: scoreTiePointsGoToPlayer1
    test 13: winnerIsNotDecidedUntilEndOfGame
    test 18: winnerIsSwitchedToPlayer1
    pointGetsAwardedToGreatestSameCategoryPointBracketIn3OfAKind
    pointGetsAwardedToGreatestSameCategoryPointBracketIn2OfAKind

 */

package test;

import capstone.MainScreenModel;
import org.junit.Assert;
import org.junit.Test;

public class MainScreenModelTest {

    @Test
    public void switchPlayerAfter3Rolls() {
//        players are '0' based

        MainScreenModel model = new MainScreenModel();

//        before any roll, default is player 0
        Assert.assertEquals(model.getPlayerTurn(), 0);

//        roll 1
        model.setDie(1, 1, 1);
        Assert.assertEquals(model.getPlayerTurn(), 0);
//        roll 2
        model.setDie(1, 1, 1);
        Assert.assertEquals(model.getPlayerTurn(), 0);
//        roll 3; turn should now be player 2
        model.setDie(1, 1, 1);
        Assert.assertEquals(model.getPlayerTurn(), 1);
    }

    @Test
    public void switchPlayerOnCommand() {
//        players are '0' based

        MainScreenModel model = new MainScreenModel();

//        default is player 1
        Assert.assertEquals(model.getPlayerTurn(), 0);

//        switch players
        model.switchPlayers();

//        player should now be 2
        Assert.assertEquals(model.getPlayerTurn(), 1);

//        lets switch players again
        model.switchPlayers();

//        player should now be 1
        Assert.assertEquals(model.getPlayerTurn(), 0);
    }

    @Test
    public void player2CanRollAsMuchAsPlayer1Did() {
//        players are '0' based
//        we'll have player 1 roll 1x and player 2 should roll max 1x
//        then switch players automatically

        MainScreenModel model = new MainScreenModel();

//        player 1 roll then switch

//        default is player 1
        Assert.assertEquals(model.getPlayerTurn(), 0);

        model.setDie(1, 1, 1);
//        player 1 roll count should be 1
        Assert.assertEquals(model.getPlayerRollCount(0), 1);

        model.switchPlayers();

//        player 2
        Assert.assertEquals(model.getPlayerTurn(), 1);
        model.setDie(1, 1, 1);

//        should be player 1 now
        Assert.assertEquals(model.getPlayerTurn(), 0);
    }

    @Test
    public void pointIsGivenAfterRoundIsComplete() {

        MainScreenModel model = new MainScreenModel();

//        game starts off on round 1 (getter is not 0 based)
        Assert.assertEquals(model.getCurrentRound(), 1);

//        player 1/2 points should == 0
        Assert.assertEquals(model.getPlayerScore(0), 0);
        Assert.assertEquals(model.getPlayerScore(1), 0);

//        roll 6x (3 per player)
        for (int i = 0; i < 5; i++) {
            model.setDie(1, 1, 1);
        }

//        check round points and round before last roll switching to round 2
        Assert.assertEquals(model.getPlayerScore(0), 0);
        Assert.assertEquals(model.getPlayerScore(1), 0);
        Assert.assertEquals(model.getCurrentRound(), 1);

        model.setDie(1, 1, 1);

//        should be round 2, and only 1 player must have points
        Assert.assertEquals(model.getCurrentRound(), 2);
        Assert.assertEquals(model.getPlayerScore(0) + model.getPlayerScore(1), 1);
    }

    @Test
    public void score421BeatsAllOtherScores() {

        MainScreenModel model = new MainScreenModel();

//        first round
        Assert.assertEquals(model.getCurrentRound(), 1);

//        player 1 gets 421 on first roll
        Assert.assertEquals(model.getPlayerTurn(), 0);
        model.setDie(4, 2, 1);
        model.setDie(1, 1, 1);
        model.setDie(1, 1, 1);

//    player 2 does:
//        3 of a kind
//        2 of a kind
//        no matches
        Assert.assertEquals(model.getPlayerTurn(), 1);
        model.setDie(6, 6, 6);
        model.setDie(6, 6, 5);
        model.setDie(6, 5, 4);

//        should be 2nd Round now with points available to see
        Assert.assertEquals(model.getCurrentRound(), 2);

//        player 1 should have the points, and player2: "0 points"
        Assert.assertEquals(model.getPlayerScore(0), 1);
        Assert.assertEquals(model.getPlayerScore(1), 0);
    }

    @Test
    public void scoreTiePointsGoToPlayer1() {

        MainScreenModel model = new MainScreenModel();

//        first round
        Assert.assertEquals(model.getCurrentRound(), 1);

//        player 1 scores 421
        Assert.assertEquals(model.getPlayerTurn(), 0);
        model.setDie(4, 2, 1);
        model.setDie(4, 2, 1);
        model.setDie(4, 2, 1);

//        player 2 scores 421
        Assert.assertEquals(model.getPlayerTurn(), 1);
        model.setDie(4, 2, 1);
        model.setDie(4, 2, 1);
        model.setDie(4, 2, 1);

//        round over
        Assert.assertEquals(model.getCurrentRound(), 2);

//        player 1 gets points due to tie
        Assert.assertEquals(model.getPlayerScore(0), 1);
        Assert.assertEquals(model.getPlayerScore(1), 0);
    }

    @Test
    public void winnerIsNotDecidedUntilEndOfGame() {

        MainScreenModel model = new MainScreenModel();

//        roll to the end of game
        int amountToRoll = MainScreenModel.MAX_ROUNDS * MainScreenModel.MAX_ROLLS * MainScreenModel.MAX_PLAYERS;
        for (int i = 0; i < amountToRoll - 1; i++) {
            model.setDie(1, 1, 1);
//            we should not have a winner until end of game
            Assert.assertEquals(model.getWinner(), -1);
        }
//        the last roll
        model.setDie(1, 1, 1);
//        we should have a winner. Either player 1 or 2
        Assert.assertTrue(model.getWinner() == 0 || model.getWinner() == 1);
    }

    @Test
    public void winnerIsSwitchedToPlayer1() {

        MainScreenModel model = new MainScreenModel();

//        we're going to have player 2 win
//        then we're going to test if player 2
//        data is transferred to player 1

//        first round
        Assert.assertEquals(model.getCurrentRound(), 1);

//        player 1
        Assert.assertEquals(model.getPlayerTurn(), 0);
        model.setDie(1, 1, 1);
        model.setDie(1, 1, 1);
        model.setDie(1, 1, 1);

//        player 2 wins by 421
        Assert.assertEquals(model.getPlayerTurn(), 1);
        model.setDie(4, 2, 1);
        model.setDie(4, 2, 1);
        model.setDie(4, 2, 1);

//        second round
        Assert.assertEquals(model.getCurrentRound(), 2);

//        player 2 won, but should now be player 1
//        so lets get the new player 1 points
        Assert.assertEquals(model.getPlayerScore(0), 1);
        Assert.assertEquals(model.getPlayerScore(1), 0);
    }

    @Test
    public void pointGetsAwardedToGreatestSameCategoryPointBracketIn3OfAKind() {

        MainScreenModel model = new MainScreenModel();

//        first round
        Assert.assertEquals(model.getCurrentRound(), 1);

//        player 1
        Assert.assertEquals(model.getPlayerTurn(), 0);
        model.setDie(5, 5, 5);
        model.setDie(3, 3, 3);
        model.setDie(2, 2, 2);

//        player 2
        Assert.assertEquals(model.getPlayerTurn(), 1);
        model.setDie(6, 6, 6);
        model.setDie(1, 1, 1);
        model.setDie(1, 1, 1);

//        round over
        Assert.assertEquals(model.getCurrentRound(), 2);

//        player 2 should get the points due to rolling a : 6, 6, 6
//        remember that player 2 is now player 1
        Assert.assertEquals(model.getPlayerScore(0), 1);
        Assert.assertEquals(model.getPlayerScore(1), 0);
    }

    @Test
    public void pointGetsAwardedToGreatestSameCategoryPointBracketIn2OfAKind() {

        MainScreenModel model = new MainScreenModel();

//        first round
        Assert.assertEquals(model.getCurrentRound(), 1);

//        player 1
        Assert.assertEquals(model.getPlayerTurn(), 0);
        model.setDie(5, 1, 5);
        model.setDie(3, 1, 3);
        model.setDie(2, 1, 2);

//        player 2
        Assert.assertEquals(model.getPlayerTurn(), 1);
        model.setDie(6, 1, 6);
        model.setDie(1, 1, 1);
        model.setDie(1, 1, 1);

//        round over
        Assert.assertEquals(model.getCurrentRound(), 2);

//        player 2 should get the points due to rolling a : 6, 1, 6
//        remember that player 2 is now player 1
        Assert.assertEquals(model.getPlayerScore(0), 1);
        Assert.assertEquals(model.getPlayerScore(1), 0);
    }

}
