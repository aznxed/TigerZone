package tigerzone;

import java.net.*;
import java.io.*;

public class tigerzoneClientProtocol {
    private static final int WAITING = 0;
    private static final int SentJoin = 1;
    private static final int SentName = 2;
    private static final int WasWelcomed = 3;
    private static final int ReceivedChallenge = 4;
    private static final int ReceivedRounds = 5;
    private static final int ReceivedOpponent = 6;
    private static final int ReceivedStartTile = 7;
    private static final int ReceivedRemainingTiles = 8;
    private static final int MakeAMove = 9;
    private static final int MoveMade = 10;
    private static final int MatchesOver = 11;
    private static final int RoundsOver = 12;
    private static final int ChallengeOver = 13;
    private static final int Finished = 14;

    private int state = WAITING;

	private String serverPassword = "PersiaRocks!";
    private String playerName = "TeamO";
    private String playerPassword = "password";
    
    private int challengeNum = -1;
    private int matchNum = -1;
    private int matchTotal = -1;
    private String opponent = "";
    private int gamesOver = 0;
    private move playerMove;
    
    bot bot = new bot();

    public String processMessage(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
        	if (theInput.equals("THIS IS SPARTA!")) {
        		theOutput = "JOIN " + serverPassword;
                state = SentJoin;
        	}
        }
        else if (state == SentJoin) {
            if (theInput.equals("HELLO!")) {
                theOutput = "I AM " + playerName + " " + playerPassword;
                state = SentName;
            } 
            else {
                theOutput = "Error Invalid Message Hello";
            }
        }
        else if (state == SentName) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("WELCOME")) {
        		matchNum = 1;
                state = WasWelcomed;
        	}
        }
        //Input: NEW CHALLENGE 1 YOU WILL PLAY 1 MATCH
        else if (state == WasWelcomed) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("NEW") && split[1].equals("CHALLENGE")) {
                //TODO: Not sure if challengeNum should be an ID (String)
        		challengeNum = Integer.valueOf(split[2]);
        		matchTotal = Integer.valueOf(split[6]);
        		state = ReceivedChallenge;
        	}
        }
        //Input: BEGIN ROUND 1 OF 1
        else if (state == ReceivedChallenge) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("BEGIN")) {
        		matchNum = Integer.valueOf(split[2]);
                state = ReceivedRounds;
        	}
        }
        //Input: YOUR OPPONENT IS PLAYER Blue
        //Not sure what to do here
        else if (state == ReceivedRounds) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("YOUR")) {
        		opponent = split[4];
                state = ReceivedOpponent;
        	}
        }
        else if (state == ReceivedOpponent) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("STARTING")) {
        		bot.firstTile(split[3], Integer.valueOf(split[5]), Integer.valueOf(split[6]), Integer.valueOf(split[7]));
                state = ReceivedStartTile;
        	}
        }
        else if (state == ReceivedStartTile) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("REMAINING")) {
        		for (int i = 0; i < Integer.valueOf(split[1]); i++) {
        			//Add tile to deck
        			bot.addDeck(split[5 + i]);
        		}
        		state = ReceivedRemainingTiles;
        	}
        }
        else if (state == ReceivedRemainingTiles) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("MATCH")) {
        		bot.botProcess(Integer.valueOf(split[3]));
        		state = MakeAMove;
        	}
        }
        //MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE TLTTP
        else if (state == MakeAMove) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("MAKE")) {
        		//player needs to make a move
            	playerMove = bot.makeMove(split[5], Integer.valueOf(split[7]), split[12]);
        		theOutput = "Game " + split[5] + " MOVE " + split[10] + " PLACE " + split[12]
						+ " AT " + playerMove.xPos + " " + playerMove.yPos + " " + playerMove.rot;
            	if(!(playerMove.meep).equals("")){
            		theOutput = theOutput + " " + playerMove.meep + " " + playerMove.meepPos;
            	}
        		state = MoveMade;
        	}
        	else if (split[0].equals("GAME")) {
        		//One Game is over
        		gamesOver++;
        		if (gamesOver > 1) {
        			state = MatchesOver;
        		}
        	}
        	//Something has gone wrong if you're here
        	else if (split[0].equals("END") && split[2].equals("ROUND")) {
        		if (matchNum == matchTotal) {
        			state = RoundsOver;
        		}
        		else {
        			state = ReceivedChallenge;
        		}
        	}
        }
        //Place the Opponents Tile
        else if (state == MoveMade) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("GAME")) {
        		if (!split[5].equals(playerName)){
        			if (split.length < (12 + 1)) {
        				bot.placeTile(split[1], split[7], Integer.valueOf(split[9]), Integer.valueOf(split[10]), Integer.valueOf(split[11]), "", -1);
        				}
        			else {
        				bot.placeTile(split[1], split[7], Integer.valueOf(split[9]), Integer.valueOf(split[10]), Integer.valueOf(split[11]), split[12], Integer.valueOf(split[13]));
        			}
	        		state = MakeAMove;
        		}
        		else {
        			//Wait for other players move
        		}
        	}
        }
        else if (state == MatchesOver) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("END") && split[2].equals("ROUND")) {
        		if (matchNum == matchTotal) {
        			state = RoundsOver;
        		}
        		else {
        			state = ReceivedChallenge;
        		}
        	}
        }
        else if (state == RoundsOver) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("END") && split[2].equals("CHALLENGES")) {
        		state = ChallengeOver;
        	}
        }
        else if (state == ChallengeOver) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("THANK")) {
        		state = Finished;
        	}
        }
        else if (state == Finished) {
        	theOutput = "Bye.";
        }
        return theOutput;
    }
}