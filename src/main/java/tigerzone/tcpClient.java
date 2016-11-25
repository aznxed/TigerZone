package tigerzone;

import java.net.*;
import java.io.*;

public class tcpClient {
	public static void main(String[] args) throws IOException {
		
		String hostName;
		int portNumber;
		
		if (false) {
			if (args.length != 2) {
			    System.err.println(
			        "Usage: java EchoClient <host name> <port number>");
			    System.exit(1);
			}
			
			hostName = args[0];
			portNumber = Integer.parseInt(args[1]);
		}
		else {
				//TODO: Host name is unknown
				hostName = "tigerzoneServer";
				portNumber = 10;
		}
		
		try (
		    Socket tzSocket = new Socket(hostName, portNumber);
		    PrintWriter out = new PrintWriter(tzSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(tzSocket.getInputStream()));
		) {
		    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		    String serverMessage;
		    String userMessage;
		
		    while ((serverMessage = in.readLine()) != null) {
		        System.out.println("Server: " + serverMessage);
		        if (serverMessage.equals("Bye."))
		            break;
		        
		        userMessage = stdIn.readLine();
		        if (userMessage != null) {
		            System.out.println("User: " + userMessage);
		            out.println(userMessage);
		        }
		    }
		//Unknown host
		} catch (UnknownHostException e) {
		    System.err.println("Don't know about host " + hostName);
		    System.exit(1);
		} catch (IOException e) {
		    System.err.println("Couldn't get I/O for the connection to " +
		        hostName);
		    System.exit(1);
		}
	}
}
