package tigerzone;

import java.net.*;
import java.io.*;

public class tcpClient {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting Client");
		
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
				hostName = "192.168.1.2";
				portNumber = 10;
		}
		
		System.out.println("Connecting to " + hostName + " at port " + portNumber);
		try (
		    Socket tzSocket = new Socket(hostName, portNumber);
		    PrintWriter out = new PrintWriter(tzSocket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(tzSocket.getInputStream()));
		) {
			System.out.println("Connected");
		    String serverMessage;
		    String userMessage;
		    tigerzoneClientProtocol clientp = new tigerzoneClientProtocol();
		
		    while ((serverMessage = in.readLine()) != null) {
		        System.out.println("Server: " + serverMessage);
		        if (serverMessage.equals("Bye."))
		            break;
		        
		        userMessage = clientp.processMessage(serverMessage);
		        if (userMessage != null) {
		            System.out.println("User: " + userMessage);
		            out.println(userMessage);
		        }
		        else {
		        	System.out.println("NULL MESSAGE");
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