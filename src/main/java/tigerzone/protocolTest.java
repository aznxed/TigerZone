package tigerzone;

import java.io.IOException;

public class protocolTest {
	public static void main(String[] args) throws IOException {
		boolean stop = false;
		String sIn = " ";
		String sOut = " ";
		String aIn = " ";
		String aOut = " ";
		String bIn = " ";
		String bOut = " ";
		
		//Create server and two clients
		tigerzoneServerProtocol serverP = new tigerzoneServerProtocol();
		tigerzoneClientProtocol playerA = new tigerzoneClientProtocol();
		tigerzoneClientProtocol playerB = new tigerzoneClientProtocol();
		
		while (!stop) {
			sOut = serverP.processInput(bOut);
			System.out.println(sOut + "\n");
			aOut = playerA.processInput(sOut);
			System.out.println(aOut + "\n");
			sOut = serverP.processInput(aOut);
			System.out.println(sOut + "\n");
			bOut = playerB.processInput(sOut);
			System.out.println(bOut + "\n");
			if (sOut.equals("Bye.")) {
				break;
			}
		}
	}
}