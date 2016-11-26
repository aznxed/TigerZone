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
    
    public void initInfo(String servPass, String playName, String playPass) {
    	serverPassword = servPass;
    	playerName = playName;
    	playerPassword = playPass;
    }
    /*
	private String servPass;
	private String playName;
	private String playPass;
	*/

    public String processMessage(String theInput) {
    	//Default to something
    	if (serverPassword.equals("")){
    		serverPassword = "PersiaRocks!";
    	    playerName = "TeamO";
    	    playerPassword = "password";
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
        		state = ReceivedChallenge;
        	}
        }
        //Input: BEGIN ROUND 1 OF 1
        else if (state == ReceivedChallenge) {
        	String[] split = theInput.split(" ");
        	gamesOver = 0;
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
        	if (split[0].equals("THE")) {
        		for (int i = 0; i < Integer.valueOf(split[2]); i++) {
        			//Add tile to deck
        			bot.addDeck(split[6 + i]);
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
        		theOutput = "GAME " + split[5] + " MOVE " + split[10];
        		if ((playerMove.rot) != -1){
            		theOutput = theOutput + " PLACE " + split[12]+ " AT "
            					+ playerMove.xPos + " " + playerMove.yPos + " " + playerMove.rot;
                	if((playerMove.meep).equals("TIGER")){
                		theOutput = theOutput + " " + playerMove.meep + " " + playerMove.meepPos;
                	}
                	else if((playerMove.meep).equals("CROCODILE")){
                		theOutput = theOutput + " " + playerMove.meep;
                	}
        		}
        		else if ((playerMove.meep).equals("PASS")){
        			theOutput = theOutput + " UNPLACEABLE PASS";
        		}
        		else if ((playerMove.meep).equals("RETRIEVE")){
        			theOutput = theOutput + " UNPLACEABLE RETRIEVE TIGER AT "
        						+ playerMove.xPos + " " + playerMove.yPos;
        		}
        		else if ((playerMove.meep).equals("ADD")){
        			theOutput = theOutput + " UNPLACEABLE ADD ANOTHER TIGER AT "
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
            		System.out.println("Games over: " + gamesOver);
            		if (gamesOver > 1) {
            			state = MatchesOver;
            		}
            	}
            	else if (!split[5].equals(playerName)){
        			//Opponent placed a tile
        			//GAME <gid> MOVE <#> PLAYER <pid> TILE <tile> UNPLACEABLE RETRIEVE TIGER AT <x> <y>
        			if (split[6].equals("PLACED")){
	        			if (split.length < (12 + 1)) {
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
        				if (split[9].equals("PASS")){
        					//Do Nothing
        				}
        				//Retrieve Tiger
        				else if (split[9].equals("RETRIEVE")){
        					bot.removeMeep(Integer.valueOf(split[12]), Integer.valueOf(split[13]));
        				}
        				//Add Tiger
        				else if (split[9].equals("ADD")){
        					bot.placeMeep(Integer.valueOf(split[13]), Integer.valueOf(split[14]));
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
        	//Something has gone wrong if you're here
        	else if (split[0].equals("END") && split[2].equals("ROUND")) {
        		System.out.println("ERROR DIDN'T DETECT MATCH END");
        		if (matchNum < matchTotal) {
        			state = ReceivedChallenge;
        		}
        		else {
        			state = RoundsOver;
        		}
        	}
        }
        //END OF ROUND 1 OF 1
        else if (state == MatchesOver) {
        	String[] split = theInput.split(" ");
        	if (split[0].equals("END") && split[2].equals("ROUND")) {
        		if (matchNum < matchTotal) {
        			state = ReceivedChallenge;
        		}
        		else {
        			state = RoundsOver;
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
            	theOutput = "Bye.";
        		state = Finished;
        	}
        }
        else if (state == Finished) {
        }
        return theOutput;
    }
}