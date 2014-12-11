package org.ggp.base.apps.benchmark;

/**
 * PlayerTesterCases is a suite of test cases for the PlayerTester.
 *
 * @author Sam Schreiber
 */
public class PlayerTesterCases {
	public static final PlayerTester.TestCase[] TEST_CASES = new PlayerTester.TestCase[] {
		// Cases from the EndgameCaseGenerator
		new PlayerTester.TestCase("ticTacToe", 0, 15, 5, "( ( cell 2 1 b ) ( control xplayer ) ( cell 1 2 x ) ( cell 1 1 o ) ( cell 3 2 b ) ( cell 2 3 x ) ( cell 3 3 b ) ( cell 3 1 b ) ( cell 1 3 b ) ( cell 2 2 o ) )", new String[] {"( mark 3 3 )", }),
		new PlayerTester.TestCase("ticTacToe", 0, 15, 5, "( ( control xplayer ) ( cell 1 1 x ) ( cell 1 3 b ) ( cell 3 2 x ) ( cell 3 3 o ) ( cell 1 2 b ) ( cell 2 2 b ) ( cell 2 3 o ) ( cell 3 1 o ) ( cell 2 1 x ) )", new String[] {"( mark 1 3 )", }),
		new PlayerTester.TestCase("ticTacToe", 0, 15, 5, "( ( cell 2 1 b ) ( cell 3 1 b ) ( cell 2 2 o ) ( cell 2 3 b ) ( cell 3 3 o ) ( cell 1 2 x ) ( cell 1 3 x ) ( cell 3 2 b ) ( cell 1 1 b ) ( control xplayer ) )", new String[] {"( mark 1 1 )", }),
		new PlayerTester.TestCase("ticTacToe", 0, 15, 5, "( ( cell 2 1 b ) ( cell 2 2 b ) ( cell 1 1 x ) ( cell 2 3 o ) ( cell 3 1 b ) ( cell 1 2 b ) ( cell 3 2 b ) ( control xplayer ) ( cell 1 3 b ) ( cell 3 3 b ) )", new String[] {"( mark 3 1 )", "( mark 1 3 )", "( mark 2 2 )", }),
		new PlayerTester.TestCase("ticTacToe", 0, 15, 5, "( ( cell 3 1 b ) ( cell 3 3 x ) ( cell 3 2 b ) ( cell 2 1 b ) ( cell 1 3 o ) ( cell 2 2 b ) ( cell 1 2 b ) ( cell 2 3 b ) ( control xplayer ) ( cell 1 1 b ) )", new String[] {"( mark 3 1 )", "( mark 1 1 )", "( mark 3 2 )", }),
		new PlayerTester.TestCase("connectFour", 0, 15, 5, "( ( cell 5 2 red ) ( cell 6 2 black ) ( cell 4 1 red ) ( cell 3 4 red ) ( cell 1 2 black ) ( cell 3 3 black ) ( cell 6 4 black ) ( cell 1 3 red ) ( cell 2 1 black ) ( cell 7 1 red ) ( cell 3 2 black ) ( cell 8 1 red ) ( cell 6 5 black ) ( cell 1 1 red ) ( cell 5 1 black ) ( cell 1 4 black ) ( control red ) ( cell 3 1 red ) ( cell 2 2 red ) ( cell 6 1 red ) ( cell 4 2 black ) ( cell 6 6 black ) ( cell 6 3 red ) )", new String[] {"( drop 4 )", "( drop 5 )", "( drop 7 )", "( drop 8 )", }),
		new PlayerTester.TestCase("connectFour", 0, 15, 5, "( ( cell 1 4 black ) ( cell 8 3 red ) ( cell 5 1 black ) ( cell 3 2 black ) ( cell 2 4 red ) ( cell 3 3 black ) ( cell 2 2 black ) ( cell 2 1 black ) ( cell 1 2 red ) ( cell 8 1 black ) ( cell 4 1 red ) ( cell 6 1 red ) ( cell 4 2 red ) ( cell 1 1 red ) ( cell 3 1 red ) ( cell 1 3 black ) ( cell 6 2 black ) ( cell 5 3 red ) ( cell 8 2 red ) ( cell 5 4 black ) ( control red ) ( cell 5 2 red ) ( cell 2 3 black ) )", new String[] {"( drop 4 )", }),
		new PlayerTester.TestCase("qyshinsu", 0, 15, 5, "( ( position 4 1 ) ( legalPlayLoc 2 ) ( control black ) ( position 2 empty ) ( owner 5 black ) ( position 3 5 ) ( position 12 2 ) ( owner 11 black ) ( position 6 empty ) ( position 9 empty ) ( position 8 empty ) ( position 1 empty ) ( position 5 1 ) ( redPlayerRemovedLastTurn 4 6 ) ( blackPlayerRemovedLastTurn 0 7 ) ( step 33 ) ( position 10 0 ) ( owner 4 red ) ( owner 3 black ) ( position 7 empty ) ( owner 10 red ) ( owner 12 black ) ( legalPlayLoc 10 ) ( position 11 3 ) )", new String[] {"( add 2 3 )", "( add 2 4 )", "( add 2 2 )", }),
		new PlayerTester.TestCase("pentago", 0, 15, 5, "( ( rotateControl red ) ( cellHolds 4 3 3 black ) ( cellHolds 3 2 2 black ) ( cellHolds 2 3 1 red ) ( cellHolds 1 3 1 red ) ( cellHolds 2 2 2 black ) ( cellHolds 2 1 2 red ) ( cellHolds 2 2 3 red ) ( cellHolds 2 3 3 black ) ( cellHolds 4 1 2 red ) ( cellHolds 4 1 3 red ) ( cellHolds 3 3 2 red ) ( cellHolds 1 2 1 red ) ( cellHolds 4 3 2 red ) ( cellHolds 4 2 3 red ) ( cellHolds 2 3 2 red ) ( cellHolds 1 2 3 black ) ( cellHolds 4 2 2 black ) ( cellHolds 2 2 1 black ) ( cellHolds 1 1 3 black ) ( cellHolds 1 3 2 red ) ( cellHolds 4 3 1 black ) ( cellHolds 3 2 3 red ) ( cellHolds 3 2 1 black ) ( cellHolds 3 3 1 black ) ( cellHolds 1 2 2 black ) ( cellHolds 2 1 1 red ) ( cellHolds 3 3 3 black ) ( cellHolds 3 1 3 black ) ( cellHolds 2 1 3 red ) ( cellHolds 1 3 3 red ) ( cellHolds 1 1 2 black ) )", new String[] {"( rotate 3 clockwise )", "( rotate 1 clockwise )", "( rotate 4 clockwise )", }),
		new PlayerTester.TestCase("cittaceot", 0, 15, 5, "( ( cell 3 1 x ) ( cell 2 3 b ) ( cell 5 5 x ) ( cell 4 5 b ) ( cell 3 4 o ) ( cell 2 5 b ) ( cell 5 3 b ) ( control xplayer ) ( cell 3 3 x ) ( cell 1 3 o ) ( cell 4 4 o ) ( cell 3 5 b ) ( cell 2 1 o ) ( cell 5 2 b ) ( cell 2 4 x ) ( cell 4 2 o ) ( cell 2 2 o ) ( cell 4 3 x ) ( cell 5 1 x ) ( cell 4 1 b ) ( cell 1 4 x ) ( cell 3 2 o ) ( cell 1 5 x ) ( cell 5 4 o ) ( cell 1 1 o ) ( cell 1 2 x ) )", new String[] {"( mark 2 3 )", }),
		new PlayerTester.TestCase("englishDraughts", 0, 15, 5, "( ( cell h 3 black king ) ( cell g 4 black king ) ( cell g 6 white king ) ( step 18 ) ( lastToMove black ) ( cell b 3 black king ) ( cell f 7 white king ) )", new String[] {"( move g 6 h 5 )", "( move f 7 e 8 )", "( move f 7 e 6 )", "( move f 7 g 8 )", "( move g 6 h 7 )", }),
		new PlayerTester.TestCase("nineBoardTicTacToe", 0, 15, 5, "( ( mark 3 3 2 3 x ) ( mark 2 3 1 1 o ) ( mark 1 1 3 2 o ) ( mark 2 2 2 1 o ) ( mark 3 3 1 3 o ) ( mark 2 3 1 2 o ) ( mark 1 2 1 1 o ) ( mark 1 3 2 2 x ) ( mark 1 3 3 3 o ) ( mark 1 1 1 3 x ) ( mark 2 2 2 3 o ) ( mark 3 1 3 2 x ) ( mark 2 1 3 3 x ) ( mark 3 1 1 1 x ) ( mark 3 2 2 2 x ) ( mark 1 1 2 3 x ) ( mark 1 3 1 2 x ) ( mark 2 1 3 2 x ) ( control xPlayer ) ( mark 2 3 2 1 o ) ( mark 2 3 2 3 x ) ( mark 1 2 2 2 x ) ( mark 2 2 3 1 o ) ( mark 3 2 3 1 o ) ( currentBoard 1 1 ) ( mark 3 2 1 1 o ) )", new String[] {"( play 1 1 3 3 x )", "( play 1 1 1 1 x )", "( play 1 1 1 2 x )", "( play 1 1 2 1 x )", "( play 1 1 3 1 x )", }),
		new PlayerTester.TestCase("2pttc", 0, 15, 5, "( ( cell 4 6 blue ) ( control red ) ( cell 3 4 red ) ( cell 7 4 blue ) ( cell 6 2 red ) ( step 27 ) ( cell 4 3 blue ) ( cell 5 4 blue ) ( cell 3 3 red ) ( cell 5 3 red ) ( cell 4 5 blue ) )", new String[] {"( move 1 4 3 5 )", "( move 3 4 4 3 )", }),
		new PlayerTester.TestCase("breakthroughSmall", 0, 15, 5, "( ( cell 5 4 black ) ( control white ) ( cell 4 1 white ) ( cell 2 4 white ) ( cell 5 3 white ) ( cell 3 2 black ) ( cell 2 1 white ) ( cell 3 5 black ) ( cell 6 3 white ) ( cell 1 5 white ) ( cell 2 2 black ) ( cell 6 5 white ) ( cell 1 4 black ) ( cell 1 6 black ) ( cell 2 5 black ) ( cell 4 2 white ) ( cell 4 6 black ) ( cell 1 1 white ) ( cell 4 4 white ) )", new String[] {"( move 1 5 2 6 )", "( move 6 5 5 6 )", "( move 6 5 6 6 )", }),
		new PlayerTester.TestCase("dotsAndBoxes", 0, 15, 5, "( ( line 4 3 5 3 ) ( line 2 4 2 5 ) ( line 3 1 3 2 ) ( line 1 2 1 3 ) ( line 3 4 3 5 ) ( line 1 1 2 1 ) ( line 1 5 1 6 ) ( line 5 1 5 2 ) ( line 4 1 5 1 ) ( line 1 4 2 4 ) ( line 5 2 5 3 ) ( control xplayer ) ( box_count xplayer 11 ) ( line 5 5 6 5 ) ( line 6 4 6 5 ) ( line 5 2 6 2 ) ( line 6 1 6 2 ) ( line 3 2 3 3 ) ( line 5 3 5 4 ) ( line 1 6 2 6 ) ( line 2 1 2 2 ) ( line 5 4 6 4 ) ( line 1 2 2 2 ) ( line 6 3 6 4 ) ( line 4 1 4 2 ) ( line 3 2 4 2 ) ( line 3 5 4 5 ) ( line 1 3 2 3 ) ( line 6 2 6 3 ) ( line 5 3 6 3 ) ( line 4 4 4 5 ) ( line 2 2 3 2 ) ( line 2 4 3 4 ) ( line 1 5 2 5 ) ( line 3 3 4 3 ) ( line 3 3 3 4 ) ( line 4 2 5 2 ) ( line 2 3 2 4 ) ( line 2 2 2 3 ) ( line 5 4 5 5 ) ( box_count oplayer 10 ) ( line 5 1 6 1 ) ( line 5 6 6 6 ) ( line 3 1 4 1 ) ( line 4 3 4 4 ) ( line 5 5 5 6 ) ( line 2 5 3 5 ) ( line 2 3 3 3 ) ( line 3 6 4 6 ) ( line 4 2 4 3 ) ( line 3 5 3 6 ) ( line 1 3 1 4 ) ( line 2 5 2 6 ) ( line 4 4 5 4 ) ( line 4 5 5 5 ) ( line 2 6 3 6 ) ( line 6 5 6 6 ) ( line 2 1 3 1 ) ( line 1 4 1 5 ) ( line 1 1 1 2 ) )", new String[] {"( draw 4 5 4 6 )", "( draw 3 4 4 4 )", }),
		new PlayerTester.TestCase("dotsAndBoxes", 0, 15, 5, "( ( line 3 4 4 4 ) ( line 4 4 5 4 ) ( line 5 4 5 5 ) ( line 2 4 2 5 ) ( line 5 3 6 3 ) ( line 2 3 3 3 ) ( line 1 1 1 2 ) ( line 3 2 3 3 ) ( line 3 5 3 6 ) ( line 2 1 2 2 ) ( line 6 1 6 2 ) ( control xplayer ) ( line 1 6 2 6 ) ( line 1 3 2 3 ) ( line 4 1 5 1 ) ( line 6 5 6 6 ) ( line 1 2 1 3 ) ( line 5 1 5 2 ) ( line 3 5 4 5 ) ( line 5 2 6 2 ) ( line 5 6 6 6 ) ( line 3 4 3 5 ) ( line 4 2 5 2 ) ( line 3 6 4 6 ) ( box_count oplayer 9 ) ( line 5 5 6 5 ) ( line 1 5 1 6 ) ( line 4 2 4 3 ) ( line 5 2 5 3 ) ( line 1 2 2 2 ) ( line 6 3 6 4 ) ( line 4 4 4 5 ) ( line 2 2 3 2 ) ( line 3 3 4 3 ) ( line 3 2 4 2 ) ( line 5 3 5 4 ) ( line 4 6 5 6 ) ( line 4 3 4 4 ) ( box_count xplayer 11 ) ( line 2 5 2 6 ) ( line 4 5 4 6 ) ( line 2 6 3 6 ) ( line 2 4 3 4 ) ( line 2 5 3 5 ) ( line 2 3 2 4 ) ( line 1 3 1 4 ) ( line 5 4 6 4 ) ( line 4 3 5 3 ) ( line 1 5 2 5 ) ( line 3 3 3 4 ) ( line 2 1 3 1 ) ( line 3 1 3 2 ) ( line 2 2 2 3 ) ( line 5 1 6 1 ) ( line 5 5 5 6 ) ( line 4 5 5 5 ) ( line 3 1 4 1 ) ( line 1 1 2 1 ) ( line 6 2 6 3 ) )", new String[] {"( draw 1 4 2 4 )", "( draw 6 4 6 5 )", "( draw 4 1 4 2 )", }),
	};
}