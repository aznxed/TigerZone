package tigerzone.TCP;

import java.io.IOException;

public class protocolTest {
	public static void main(String[] args) throws IOException {
		boolean stop = false;
		String sIn = " ";
		String sOut = " ";
		String aIn = " ";
		String aOut = " ";
		
		//Create server and two clients
		tigerzoneServerProtocol serverP = new tigerzoneServerProtocol();
		tigerzoneClientProtocol playerA = new tigerzoneClientProtocol();
		
		while (!stop) {
			aOut = playerA.processMessage(sOut);
			System.out.println("PlayerA: " + aOut);
			sOut = serverP.processInput(aOut);
			System.out.println("ServerA: " + sOut);
			try {
				if (aOut.equals("Bye.")) {
					break;
				}
			} catch (Exception ex){}
		}
	}
}