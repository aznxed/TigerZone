package tigerzone;

import java.net.*;
import java.io.*;

public class tcpServer {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting Server");
		
		int portNumber;
		
		if (false) {
			if (args.length != 1) {
				System.err.println("Usage: java tcpServer <port number>");
				System.exit(1);
			}
			
			//portNumber is specified in first command line argument
			portNumber = Integer.parseInt(args[0]);
		}
		else {
				portNumber = 10;
		}
		
		System.out.println("Connecting to Port: " + portNumber);
		//TODO: Do I need to support multiple clients? 
		try (
			//Create ServerSocket object to listen on port portNumber
			ServerSocket serverSocket = new ServerSocket(portNumber);
			//Accept a connection from client and create a socket object on the port
			Socket clientSocket = serverSocket.accept();
			//Get socket's input and output streams and open readers and writers on them
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		) {
			System.out.println("Connected");
			String inputLine, outputLine;
	
			//Initiate conversation with client by writing to the socket
			//Create object that keeps track of the current joke, the current state within the joke
			tigerzoneServerProtocol serverp = new tigerzoneServerProtocol();
			//Get the first message that the server sends to the client
			outputLine = serverp.processInput(null);
			//Write the information to the client socket, therefore sending the message to the client
			out.println(outputLine);
			
			//Communicate with the client by reading from and writing to the socket
			//while the client and server still have something to say to each other
			//read and write to the socket
			//while loop iterates on the read from the input stream until
			//the client responds by writing to its output stream
			while ((inputLine = in.readLine()) != null) {
			//while(true) {
				/*if ((inputLine = in.readLine()) != null) {
					//Do Nothing
				}
				else{
					inputLine = "";
				}*/
				if (inputLine.equals("") || inputLine.equals(" ")){
					System.out.println("Error empty input");
					outputLine = serverp.processInput("");
					out.println(outputLine);
					System.out.println("Sent: " + outputLine);
				}
				else {
					System.out.println("Got:  " + inputLine);
					//ask the KnockKnockProtocol for a suitable reply
					outputLine = serverp.processInput(inputLine);
					//send reply
					out.println(outputLine + " \r \n");
					System.out.println("Sent: " + outputLine);
					//if reply is bye then quit the loop
					//java runtime automatically closes the input and output streams, the
					//client socket and server socket because it was created in the try-with-
					//resources statement
					if (outputLine.equals("Bye."))
						break;
				}
			}
			
		}
	}
}