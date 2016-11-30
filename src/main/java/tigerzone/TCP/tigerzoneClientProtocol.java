package tigerzone.TCP;

import tigerzone.bot2;
import tigerzone.fakeBot;
import tigerzone.move;
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
    private static final int MatchesOver = 11;
    private static final int RoundsOver = 12;
    private static final int ChallengeOver = 13;
    private static final int Finished = 14;

    private int state = WAITING;

	private String serverPassword = "";
    private String playerName;
    private String playerPassword;
    
    private int challengeNum = -1;
    private int matchNum = -1;
    private int matchTotal = -1;
    private String opponent = "";
    private int gamesOver = 0;
    private move playerMove;
	
    private int gamesPlayed = 0;
	private int myForfeit = 0;
	private int enemyForfeit = 0;
	
	private int boardWin = 0;
	private int boardLoss = 0;
	private int boardTie = 0;
	
	private int matchWin = 0;
	private int matchTie = 0;
	private int matchLoss = 0;
	
	private int boardWinTotal = 0;
	private int boardTieTotal = 0;
	private int boardLossTotal = 0;
	
	private int tigerMeep = 7;
    
    bot2 bot = new bot2();
    
    public void initInfo(String servPass, String playName, String playPass) {
    	serverPassword = servPass;
    	playerName = playName;
    	playerPassword = playPass;
    }
    
    public void outputTournamentRecord() {
    	if (gamesPlayed > 0) {
        	System.out.println("Games Played:     " + gamesPlayed);
        	System.out.println("Match Record:     " + matchWin + " - " + matchTie + " - " + matchLoss);
        	System.out.println("Game Record:      " + boardWinTotal + " - " + boardTieTotal + " - " + boardLossTotal);
        	System.out.println("Forfeited:        " + myForfeit);
        	System.out.println("Enemy Forfeited:  " + enemyForfeit);
        	bot.printBoard();
    	}
    	else {
    		System.out.println("No games played");
    	}
    }

    public String processMessage(String theInput) {
    	//Default to something
    	if (serverPassword.equals("")){
    		serverPassword = "PersiaRocks!";
    	    playerName = "TeamO";
    	    playerPassword = "password";
    	}
        if (theInput == null){
        	return null;
        }
        String theOutput = null;
        if (state == WAITING) {
        	if (theInput.equals("THIS IS SPARTA!")) {
        		theOutput = "JOIN " + serverPassword;
                state = SentJoin;
        	}
        }
        else if (state == SentJoin) {
        	String[] split = theInput.split(" ");
            if (split[0].equals("HELLO!")) {
                theOutput = "I AM " + playerName + " " + playerPassword;
                state = SentName;
            } 
            else {
                System.out.println("Error Invalid Message Hello");
            }
        }
        else if (state == SentName) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("WELCOME")) {
                state = WasWelcomed;
        	}
        }
        //Input: NEW CHALLENGE 1 YOU WILL PLAY 1 MATCH
        else if (state == WasWelcomed) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("NEW") && split[1].equals("CHALLENGE")) {
                //TODO: Not sure if challengeNum should be an ID (String)
        		matchNum = 1;
        		challengeNum = Integer.valueOf(split[2]);
        		matchTotal = Integer.valueOf(split[6]);
        	}
        	//Wait for next challenge
        	//If game over 
        	else if (split[0].equals("THANK")) {
            	theOutput = "Bye.";
            	outputTournamentRecord();
        	}
        	//Begin Round
        	else if (split[0].equals("BEGIN")) {
            	gamesOver = 0;
        		matchNum = Integer.valueOf(split[2]);
        	}
        	else if (split[0].equals("YOUR")) {
        		opponent = split[4];
        	}
        	else if (split[0].equals("STARTING")) {
        		//bot.placeFirstTile(split[3], Integer.valueOf(split[5]), Integer.valueOf(split[6]), Integer.valueOf(split[7]));
        		bot.initBoards();
        		bot.botProcess(3);
                bot.firstTile(split[3], Integer.valueOf(split[6]), Integer.valueOf(split[5]), Integer.valueOf(split[7]));
                tigerMeep = 7;
        	}
        	else if (split[0].equals("THE")) {
        		for (int i = 0; i < Integer.valueOf(split[2]); i++) {
        			//Add tile to deck
        			//bot.addDeck(split[6 + i]);
        		}
        	}
        	else if (split[0].equals("MATCH")) {
        	}
        	else if (split[0].equals("MAKE")) {
        		//player needs to make a move
            	playerMove = bot.makeMove(split[5], Integer.valueOf(split[7]), split[12]);
        		theOutput = "GAME " + split[5] + " MOVE " + split[10];
        		if ((playerMove.meepPos) != -1){
            		theOutput = theOutput + " PLACE " + split[12]+ " AT "
            					+ playerMove.xPos + " " + playerMove.yPos + " " + playerMove.rot;
                	if((playerMove.meep).equals("TIGER") && tigerMeep > 0) {
                		theOutput = theOutput + " " + playerMove.meep + " " + playerMove.meepPos;
                		tigerMeep--;
                	}
                	else if((playerMove.meep).equals("CROCODILE")){
                		theOutput = theOutput + " " + playerMove.meep;
                	}
                	else {
                		theOutput = theOutput + " NONE";
                	}
        		}
        		else if ((playerMove.meep).equals("PASS")){
        			theOutput = theOutput + " TILE " + split[12] + " UNPLACEABLE PASS";
        		}
        		else if ((playerMove.meep).equals("RETRIEVE")){
        			theOutput = theOutput + " TILE " + split[12] + " UNPLACEABLE RETRIEVE TIGER AT "
        						+ playerMove.xPos + " " + playerMove.yPos;
        		}
        		else if ((playerMove.meep).equals("ADD")){
        			theOutput = theOutput + " TILE " + split[12] + " UNPLACEABLE ADD ANOTHER TIGER AT "
        						+ playerMove.xPos + " " + playerMove.yPos;
        		}
        		else {
        			System.out.println("ERROR Player Move");
        		}
        	}
        	else if (split[0].equals("GAME")) {
            	if (split[2].equals("OVER")) {
            		//One Game is over
            		gamesOver++;
            		gamesPlayed++;
            		System.out.println("Games over: " + gamesOver);
            		//Compare score
            		if (split[4].equals(playerName)) {
            			if (split[5].equals("WIN")) {
            				boardWin++; //YAY!
            				enemyForfeit++;
            			}
            			else if (split[5].equals("FORFEITED")) {
            				boardLoss++; //Aww
            				myForfeit++;
            			}
            			else if (Integer.valueOf(split[5]) > Integer.valueOf(split[8])){
                			boardWin++; //YAY!
                		}
                		else if (Integer.valueOf(split[5]) == Integer.valueOf(split[8])){
                			boardTie++;
                		}
                		else {
                			boardLoss++; //Aww
                		}
            		}
            		else {
            			if (split[5].equals("WIN")) {
            				boardLoss++; //Aww
            				myForfeit++;
            			}
            			else if (split[5].equals("FORFEITED")) {
            				boardWin++; //YAY!
            				enemyForfeit++;
            			}
            			else if (Integer.valueOf(split[5]) > Integer.valueOf(split[8])){
                			boardLoss++; //Aww
                		}
                		else if (Integer.valueOf(split[5]) == Integer.valueOf(split[8])){
                			boardTie++;
                		}
                		else {
                			boardWin++; //YAY!
                		}
            		}
            		if (gamesOver > 1) {
            			if (boardWin > boardLoss){
            				matchWin++;
            			}
            			else if (boardLoss > boardWin) {
            				matchLoss++;
            			}
            			else {
            				matchTie++;
            			}
            			boardWinTotal += boardWin;
            			boardTieTotal += boardTie;
            			boardLossTotal += boardLoss;
            			boardWin = 0;
            			boardLoss = 0;
            			boardTie = 0;
            			
            		}
            	}
            	else if (!split[5].equals(playerName)){
        			//Opponent placed a tile
        			//GAME <gid> MOVE <#> PLAYER <pid> TILE <tile> UNPLACEABLE RETRIEVE TIGER AT <x> <y>
        			if (split[6].equals("PLACED")){
	        			if (true) {
	        				bot.placeTile(split[1], split[7], Integer.valueOf(split[9]), Integer.valueOf(split[10]), Integer.valueOf(split[11]), "", -1);
	        				}
	        			else {
	        				bot.placeTile(split[1], split[7], Integer.valueOf(split[9]), Integer.valueOf(split[10]), Integer.valueOf(split[11]), split[12], Integer.valueOf(split[13]));
	        			}
        			}
        			//Tile is Unplaceable
        			//TODO: Make sure this is correct format
        			else if(split[6].equals("TILE")){
        				//Pass
        				if (split[9].equals("PASSED")){
        					//Do Nothing
        				}
        				//Retrieve Tiger
        				else if (split[9].equals("RETRIEVED")){
        					//bot.removeMeep(Integer.valueOf(split[12]), Integer.valueOf(split[13]));
        				}
        				//Add Tiger
        				else if (split[9].equals("ADDED")){
        					//bot.placeMeep(Integer.valueOf(split[13]), Integer.valueOf(split[14]));
        				}
        			}
        			else {
        				System.out.println("ERROR Opponents Message: " + theInput);
        			}
        		}
        		else {
        			//Wait for other players move
        		}
        	}
        	else if (split[0].equals("END") && split[2].equals("ROUND")) {
        		if (matchNum < matchTotal) {
        		}
        		else {
        		}
        	}
        	//Challenge is over Wait for new challenge
        	else if (split[0].equals("END") && (split[2].equals("CHALLENGES") || split[2].equals("CHALLENGE"))) {
        	}
        	else if (split[0].equals("THANK")) {
            	theOutput = "Bye.";
            	System.out.println("Games Played:      " + gamesPlayed);
            	System.out.println("Tournament Record: " + boardWin + " - " + boardTie + " - " + boardLoss);
            	System.out.println("Forfeited:         " + myForfeit);
        	}
        	else if (split[4].equals("FORFEITED")){
        		System.out.println("ERROR TIMEOUT");
        	}
        }
        else if (state == Finished) {
        }
        return theOutput;
    }
}