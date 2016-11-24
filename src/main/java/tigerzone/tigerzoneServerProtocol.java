package tigerzone;

import java.net.*;
import java.io.*;

public class tigerzoneServerProtocol {
    private static final int WAITING = 0;
    private static final int SentSparta = 1;
    private static final int SentHello = 2;
    private static final int SentWelcome = 3;
    private static final int SentNewChallenge = 4;
    private static final int SentBeginRound = 5;
    private static final int SentOpponent = 6;
    private static final int SentStartTile = 7;
    private static final int SentRemainingTiles = 8;
    private static final int SentMatchBeginTime = 9;
    private static final int SentMakeAMove = 10;
    private static final int SentMoveMade = 11;
    
    private static final int SentScore = 20;
    private static final int SentEndRound = 21;
    private static final int SentEndChallenges = 22;
    private static final int SentEndConnection = 23;
    
    private int state = WAITING;
    
    //TODO: MAKE non constant
    private int challengeNum = 1;
    private int matchNum = 1;
    private int roundNum = 1;
    private int roundTotal = 1;
    private int moveTime = 1;
    private boolean gameOver = false;
    
    private String tourPassword = "PersiaRocks!";
    private String playPassword = "Obiwan77";
    
    public String processInput(String theInput) {
        String theOutput = null;

        //Send Initial Message after port established
        if (state == WAITING) {
            theOutput = "THIS IS SPARTA!";
            state = SentSparta;
        } 
        //Ask for tournament password
        else if (state == SentSparta) {
        	//First arg must be JOIN, Second is the password
        	String[] split = theInput.split(" ");
        	if (split[0].equals("JOIN") && split[1].equals(tourPassword)) {
        		//Join message correct 
            	theOutput = "HELLO!";
            	state = SentHello;
        	}
        	else {
        		//incorrect message
        		//TODO: Not sure what to do here
        		theOutput = "ERROR Wrong Password";
        		state = WAITING;
        	}
        }
        //Get player id and player password
        else if (state == SentHello) {
        	String[] split = theInput.split(" ");
            if (split[0].equals("I") && split[1].equals("am")) {
            	//TODO: Check if player ID and password are correct
            	if (true) {
                theOutput = "WELCOME " + split[2] + " PLEASE WAIT FOR THE NEXT CHALLENGE";
                state = SentWelcome;
            	}
            } else {
                theOutput = "ERROR: Unknown Reply";
            }
        } 
        //Begin tournament
        //Begin Challenge
        else if (state == SentWelcome) {
        	if (matchNum > 1) {
        		theOutput = "NEW CHALLENGE " + challengeNum + " YOU WILL PLAY " + matchNum + " MATCHES";
        	}
        	else {
        		theOutput = "NEW CHALLENGE " + challengeNum + " YOU WILL PLAY " + matchNum + " MATCH";
        	}
            state = SentNewChallenge;
        }
        //Begin Round
        else if (state == SentNewChallenge) {
            theOutput = "BEGIN ROUND " + roundNum + " OF " + roundTotal;
            state = SentBeginRound;
        }
        //Send opponent name player
        //TODO: Send to both players
        else if (state == SentBeginRound) {
        	String playerName = "Blue";
            theOutput = "YOUR OPPONENT IS PLAYER " + playerName;
            state = SentOpponent;
        }
        //Send starting tile
        else if (state == SentOpponent) {
        	//TODO: Make non-constant
        	String startingTile = "TLTJ";
        	int xPos = 0;
        	int yPos = 0;
        	int rot = 0;
            theOutput = "STARTING TILE IS " + startingTile + "AT" + xPos + " " + yPos + " " + rot;
            state = SentStartTile;
        }
        else if (state == SentStartTile) {
        	//TODO: Make non-constant
        	int tilesLeft = 6;
        	String tileString = "TLTTP LJTJ- JLJL- JJTJX LTTJB TLLT-";
            theOutput = "REMAINING " + tilesLeft + " TILES ARE [ " + tileString + " ]";
            state = SentRemainingTiles;
        }
        //Send time till match starts
        else if (state == SentRemainingTiles) {
        	//TODO: Make non-constant
        	int timeToMatch = 15;
        	if (timeToMatch > 1){
        		theOutput = "MATCH BEGINS IN " + timeToMatch + " SECONDS";
        	}
        	else {
        		theOutput = "MATCH BEGINS IN " + timeToMatch + " SECOND";
        	}
            state = SentMatchBeginTime;
        }
        //Send move request
        //TODO: Set timer for response
        else if (state == SentMatchBeginTime) {
        	//TODO: Make non-constant
        	String gameID = "A";
        	int moveNum = 1;
        	String tile = "TLTTP";
        	if (moveTime > 1){
        		theOutput = "MAKE YOUR MOVE IN GAME " + gameID + " WITHIN " + moveTime + "SECONDS: MOVE " + moveNum + " PLACE " + tile;
        	}
        	else {
        		theOutput = "MAKE YOUR MOVE IN GAME " + gameID + " WITHIN " + moveTime + "SECOND: MOVE " + moveNum + " PLACE " + tile;
        	}
            state = SentMakeAMove;
        }
        //Send Player Made a Move
        else if (state == SentMakeAMove) {
        	//TODO: Make non-constant
        	int tilesLeft = 6;
        	String gameID = "Z";
        	String playerID = "BLARG";
        	int moveNum = 0;
        	theOutput = "GAME " + gameID + " MOVE " + moveNum + " PLAYER " + playerID + " ";
        	if (true) {
            	String[] split = theInput.split(" ");
        		theOutput = theOutput + split[5] + " AT " + split[7] + " " + split[8] + " " + split[9] + " TIGER " + split[11];
        	}
        	//Illegal Tile Placement
        	else if (true) {
        	theOutput = theOutput + "FORFEITED: ILLEGAL TILE PLACEMENT";
        	}
        	//Illegal Meeple Placement
        	else if (true) {
        	theOutput = theOutput + "FORFEITED: ILLEGAL MEEPLE PLACEMENT";
        	}
        	//Invalid Meeple Placement
        	else if (true) {
        	theOutput = theOutput + "FORFEITED: ILLEGAL INVALID MEEPLE PLACEMENT";
        	}
        	//Timeout
        	else if (true) {
        	theOutput = theOutput + "FORFEITED: TIMEOUT";
        	}
        	//Illegal Message Received
        	else if (true) {
        	theOutput = theOutput + "FORFEITED: ILLEGAL MESSAGE RECEIVED";
        	}
        	if (!gameOver){
        		state = SentMatchBeginTime;
        	}
        	else {
        		state = SentMoveMade;
        	}
        }
        else if (state == SentMoveMade) {
        	//TODO: Remove Constants
        	String gameID = "Z";
        	String player1 = "player1";
        	String player2 = "player2";
        	int player1Score = -1;
        	int player2Score = -2;
        	theOutput = "GAME " + gameID + " OVER PLAYER " + player1 + " " + player1Score + " PLAYER " + player2 + " " + player2Score;
            state = SentScore;
        }
        else if (state == SentScore) {
        	//TODO: Remove Constants
        	int round = 1;
        	int roundTotal = 1;
        	theOutput = "END OF ROUND " + round + " OF " + roundTotal;
            state = SentEndRound;
        }
        else if (state == SentEndRound) {
        	theOutput = "END OF CHALLENGES";
        	state = SentEndChallenges;
        }
        else if (state == SentEndChallenges) {
        	theOutput = "THANK YOU FOR PLAYING! GOODBYE";
        	state = WAITING;
        }
        return theOutput;
    }
}