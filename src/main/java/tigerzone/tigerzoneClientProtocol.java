package tigerzone;

import java.net.*;
import java.io.*;

public class tigerzoneClientProtocol {
    private static final int WAITING = 0;
    private static final int SentJoin = 1;
    private static final int WasWelcomed = 2;
    private static final int ReceivedChallenge = 3;
    private static final int ReceivedRounds = 4;
    private static final int ReceivedOpponent = 5;
    private static final int SentName = 10;

    private int state = WAITING;

	private String serverPassword = "PersiaRocks!";
    private String playerName = "TeamO";
    private String playerPassword = "password";
    
    private int challengeNum = -1;
    private int matchNum = -1;
    private int matchTotal = -1;
    private String opponent = "";

<<<<<<< Upstream, based on origin/tcpconnection
    public String readLine(String theInput) {
    	System.out.println(theInput);
=======
    public String processMessage(String theInput) {
    	System.out.println("Client Protocol Called");
>>>>>>> c084a59 Fix TCP Client
        String theOutput = null;

        if (state == WAITING) {
        	if (theInput.equals("THIS IS SPARTA!")) {
        		System.out.println("Found Sparta.");
        		theOutput = "JOIN " + serverPassword;
                state = SentJoin;
        	}
        	else {
        		System.out.println("Waiting for Sparta.");
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
        }/*
        else if (state == SentName) {
        	String[] split = theInput.split(" ");
            if (!split[0].equals("MAKE")) {
            	//Make a move
                theOutput = 
            } else {
                theOutput = "You're supposed to say \"" + 
			    clues[currentJoke] + 
			    " who?\"" + 
			    "! Try again. Knock! Knock!";
                state = SENTKNOCKKNOCK;
            }
        }*/
        return theOutput;
    }
}