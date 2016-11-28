package tigerzone.TCP;

import java.net.*;
import java.io.*;

public class tcpClient {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting Client");
		
		String hostName = "";
		int portNumber = -1;
		String serverPassword = "";
		String userName = "";
		String userPassword = "";
		
		//If not enough command arguments
		if (args.length < 5 ) {
			System.err.println("Usage: java tcpServer <port number>");
			System.exit(1);
		}
		
		hostName = args[0];
		portNumber = Integer.valueOf(args[1]);
		serverPassword = args[2];
		userName = args[3];
		userPassword = args[4];
		
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
		    clientp.initInfo(serverPassword, userName, userPassword);
		
		    while ((serverMessage = in.readLine()) != null) {
		        
		        if (serverMessage.equals("") || serverMessage.equals(" ")){
		        	//System.out.println("ERROR EMPTY SERVER MESSAGE");
		        	userMessage = null;
		        }
		        else {
			        System.out.println("Server: " + serverMessage);
		        	userMessage = clientp.processMessage(serverMessage);
		        }
		        //if (userMessage != null && userMessage != "") {
		        if (userMessage != null) {
			        if (userMessage.equals("Bye.")) {
			        	
			        	break;
			        }
		            out.println(userMessage + " \r \n");
		            System.out.println("User: " + userMessage);
		        }
		        else {
		        	System.out.println("NULL MESSAGE");
		        }
		    }
			System.out.println("ERROR OUT OF WHILE LOOP CLIENT");
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