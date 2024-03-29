package tigerzone.TCP;

import tigerzone.bot2;
import tigerzone.fakeBot;
import tigerzone.move;
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
    private static final int SendSecondScore = 12;
    private static final int StartMatch = 13;
    
    private static final int SentScore = 20;
    private static final int SentEndRound = 21;
    private static final int SentEndChallenges = 22;
    
    private int state = WAITING;
    public boolean getReply = true;
    
    private int challengeNum = 1;
    private int challengeTotal = 1;
    private int roundNum = 1;
    private int roundTotal = 1;
    private int moveTime = 1;

	private int timeToMatch = 3;
    private String tourPassword = "PersiaRocks!";
    private String playPassword = "Obiwan77";
	//private String startingTile = "TLTJ-";
	//private String startingTile = "TLLLC";
	private String startingTile = "TLTJ-";
	private String tileString = "JJJJX JLTT- LLLL- LJTJ- JLJL- JJTJX TLLL- TLLT- TTTT- JJJJX LLLL- JJTJX TLTT-";
	//private String tileString = "JJJJ- JJJJX JJTJX TTTT- TJTJ- TJJT- TJTT- LLLL- JLLL- LLJJ- JLJL- LJLJ- LJJJ- JLLJ- TLJT- TLJTP JLTT- JLTTB TLTJ- TLTJD TLLL- TLTT- TLTTP TLLT- TLLTB LJTJ- LJTJD";
	//private String tileString = "JJJJ- JJJJX";
	//private String tileString = "JJJJ- JJJJX";
	private String[] deck = tileString.split(" ");
	private int moveNum = 0;
	private move botMove;
	private move playerMove;
	private String botID = "TEAMFOO";
	private String currentTile = "";
	private String playerName = "";
	private int player1Score = 0;
	private int player2Score = 0;
	private long moveStartTime;
	
	//Used for sending current game
	private boolean gameID = true;
	
	bot2 serverBot = new bot2();
	
	public String moveMess(String gID, int time, int num, String tiles){
		String mess = "";
    	if (moveTime > 1){
    		mess = "MAKE YOUR MOVE IN GAME " + gID + " WITHIN " + time + " SECONDS: MOVE " + num + " PLACE " + tiles;
    	}
    	else {
    		mess = "MAKE YOUR MOVE IN GAME " + gID + " WITHIN " + time + " SECOND: MOVE " + num + " PLACE " + tiles;
    	}
    	return mess;
	}
    
    public String processInput(String theInput) {
        String theOutput = null;

        //Send Initial Message after port established
        if (state == WAITING) {
        	getReply = true;
            theOutput = "THIS IS SPARTA!";
            state = SentSparta;
        }
        
        //Ask for tournament password
        else if (state == SentSparta) {
        	//First arg must be JOIN, Second is the password
        	getReply = true;
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
            if (split[0].equals("I") && split[1].equals("AM")) {
            	//TODO: Check if player ID and password are correct
            	if (true) {
            		playerName = split[2];
            		getReply = false;
            		theOutput = "WELCOME " + playerName + " PLEASE WAIT FOR THE NEXT CHALLENGE";
            		state = SentWelcome;
            	}
            } else {
                System.out.println("ERROR: Unknown Reply");
            }
        } 
        
        //Begin tournament
        //Begin Challenge
        else if (state == SentWelcome) {
        	getReply = false;
        	roundNum = 1;
        	if (roundTotal > 1) {
        		theOutput = "NEW CHALLENGE " + challengeNum + " YOU WILL PLAY " + roundTotal + " MATCHES";
        	}
        	else {
        		theOutput = "NEW CHALLENGE " + challengeNum + " YOU WILL PLAY " + roundTotal + " MATCH";
        	}
            state = SentNewChallenge;
        }
        
        //Begin Round
        else if (state == SentNewChallenge) {
        	getReply = false;
            theOutput = "BEGIN ROUND " + roundNum + " OF " + roundTotal;
            state = SentBeginRound;
        }
        
        //Send opponent name player
        //TODO: Send to both players
        else if (state == SentBeginRound) {
        	getReply = false;
            theOutput = "YOUR OPPONENT IS PLAYER " + botID;
            state = SentOpponent;
        }
        
        //Send starting tile
        else if (state == SentOpponent) {
        	getReply = false;
        	int xPos = 0;
        	int yPos = 0;
        	int rot = 0;
            theOutput = "STARTING TILE IS " + startingTile + " AT " + xPos + " " + yPos + " " + rot;
            //Initialize board and place first for serverBot
            serverBot.initBoards();
    		serverBot.botProcess(3);
            serverBot.firstTile(startingTile, 0, 0, 0);
            //serverBot.placeFirstTile(startingTile, xPos, yPos, rot);
            state = SentStartTile;
        }
        
        else if (state == SentStartTile) {
        	getReply = false;
            theOutput = "THE REMAINING " + deck.length + " TILES ARE [ " + tileString + " ]";
            state = SentRemainingTiles;
        }
        
        //Send time till match starts
        else if (state == SentRemainingTiles) {
        	getReply = false;
        	moveNum = 1;
            gameID = true;
        	if (timeToMatch > 1){
        		theOutput = "MATCH BEGINS IN " + timeToMatch + " SECONDS";
        	}
        	else {
        		theOutput = "MATCH BEGINS IN " + timeToMatch + " SECOND";
        	}
            state = SentMatchBeginTime;
        }
        
        else if (state == SentMatchBeginTime) {
        	getReply = false;
        	//Wait timeToMatch for game to start
			try {Thread.sleep(timeToMatch * 1000);} catch(InterruptedException ex){
				Thread.currentThread().interrupt();
			}
        	state = StartMatch;
        }
        
        //Send move request to player
        else if (state == StartMatch) {
        	getReply = true;
        	currentTile = deck[moveNum - 1];
        	//Send Move to player
        	botMove = serverBot.makeMove((gameID ? "B" : "A"), moveTime, currentTile);
    		//if ((playerMove.meepPos) != -1){
    			theOutput = moveMess((gameID ? "A" : "B"), moveTime, moveNum, currentTile);
    		/*}
    		else {
        		theOutput = "GAME " + (gameID ? "A" : "B") + " MOVE " + moveNum + " UNPLACEABLE PASS";
    		}*/
    		moveStartTime = System.currentTimeMillis();
        	//Send Move to bot
            state = SentMakeAMove;
        }
        
        //Send Move Opponent Made and Process Player Move
        else if (state == SentMakeAMove) {
        	getReply = false;
        	//Sent botmove to player
        	theOutput = "GAME " + (gameID ? "B" : "A") + " MOVE " + moveNum + " PLAYER " + botID + " PLACED "
        					+ currentTile + " AT " + botMove.xPos + " " + botMove.yPos + " " + botMove.rot;
        	if(!(botMove.meep).equals("")){
        		theOutput = theOutput + " " + botMove.meep + " " + botMove.meepPos;
        	}
        	if (deck.length > moveNum) {
        		state = StartMatch;
            	moveNum++;
        	}
        	else {
        		state = SentMoveMade;
        	}
        	//Receive playerMove
        	//Check valid move by player
        	String[] split = theInput.split(" ");
        	//TODO: Remove later
        	if (theInput.equals(null) || theInput.equals("")){
        		System.out.println("FORFEITED: ILLEGAL MESSAGE RECEIVED");
        		theOutput = "GAME " + (gameID ? "A" : "B") + " OVER PLAYER " + playerName + " " + "FORFEITED" + " PLAYER " + botID + " " + "WIN"; 
        		state = SentEndChallenges;
        	}
        	else if (split[0].equals("GAME") && split[2].equals("MOVE")) {
        		if (!((moveTime * 1000) < (System.currentTimeMillis() - moveStartTime))){
        			//Check player move
        			
        		}
        		//Out of time Forfeit
        		else {
                	theOutput = theOutput + "FORFEITED: TIMEOUT";
            		System.out.println("FORFEITED: TIMEOUT");
                	state = SentEndChallenges;
        		}
        	}
        	else {
        		System.out.println("FORFEITED: ILLEGAL TILE PLACEMENT");
        		state = SentEndChallenges;
        	}
        	/*
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
        	//Illegal Message Received
        	else if (true) {
        	theOutput = theOutput + "FORFEITED: ILLEGAL MESSAGE RECEIVED";
        	}*/
        }
        
        else if (state == SentMoveMade) {
        	getReply = false;
        	//Output score for both games
        	theOutput = "GAME " + "A" + " OVER PLAYER " + playerName + " " + player1Score + " PLAYER " + botID + " " + player2Score;
        	state = SendSecondScore;
        }
        
        else if (state == SendSecondScore){
        	getReply = false;
        	theOutput = "GAME " + "B" + " OVER PLAYER " + playerName + " " + player1Score + " PLAYER " + botID + " " + player2Score;
            state = SentScore;
        }
        
        else if (state == SentScore) {
        	getReply = false;
        	theOutput = "END OF ROUND " + roundNum + " OF " + roundTotal;
        	if(roundNum < roundTotal){
        		roundNum++;
        		state = SentNewChallenge;
        	}
        	else {
        		state = SentEndRound;
        	}
        }
        
        else if (state == SentEndRound) {
        	getReply = false;
        	theOutput = "END OF CHALLENGES";
        	if (challengeTotal > challengeNum) {
        		state = SentWelcome;
        		challengeNum++;
        	}
        	else {
        		state = SentEndChallenges;
        	}
        }
        
        else if (state == SentEndChallenges) {
        	getReply = false;
        	theOutput = "THANK YOU FOR PLAYING! GOODBYE";
        }
        
        return theOutput;
    }
}