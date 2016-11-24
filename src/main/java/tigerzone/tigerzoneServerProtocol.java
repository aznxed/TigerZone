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
            theOutput = "NEW CHALLENGE " + challengeNum + " YOU WILL PLAY " + matchNum + " MATCH";
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
            state = SentOpponent;
        }
        return theOutput;
    }
}