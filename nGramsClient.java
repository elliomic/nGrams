import java.io.*;
import java.net.*;

class nGramsClient {
	private static final int port = 1033;

	private static String request(String cmd, Socket clientSocket) throws IOException {
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes(cmd + "\n");
		String sentence = inFromServer.readLine();
		while (true) {
			if (inFromServer.ready()) {
				sentence = sentence.concat("\n" + inFromServer.readLine());
			} else {
				break;
			}
		}
		clientSocket.close();
		return sentence;
	}
	
	private static void addDefaultFiles() throws IOException{
		String[] filenames = {"obama.txt", "sherlock_holmes.txt", 
							  "huckleberry_finn.txt", "odyssey.txt", 
							  "shakespeare.txt", "bible.txt", 
							  "Harry-Potter.txt", "state-of-the-union-1790-2006.txt", 
							  "The_Lord_of_The_Rings.txt", "starwars-complete.txt"};
		
		for (int x = 0; x <= 9; x++) {
			request("add " + filenames[x], new Socket("localhost", port));
		}
	}
	
	public static void main(String[] args) throws IOException{
		if (args.length > 0) {
			String req = args[0];
			for (int i = 1; i < args.length; i++) {
				req = req.concat(" " + args[i]);
			}
			System.out.println(request(req, new Socket("localhost", port)));
		} else {
			System.out.println("adding default files");
			addDefaultFiles();
		}
	}
}