import java.io.*;
import java.net.*;

public class nGramsServer {
	private static int port = 0;
	
	private static void errorHandling(DataOutputStream outToClient, Exception e) throws IOException {
		outToClient.writeBytes("error processing request");
		e.printStackTrace();
	}

	public static void main(String args[]) throws IOException{
		nGrams server;
		if (args.length > 1) {
			server = new nGrams(Integer.parseInt(args[1]));
		} else {
			server = new nGrams();
		}
		
		port = Integer.parseInt(args[0]);
		System.out.println("running server on port " + port);
		
		ServerSocket welcomeSocket = new ServerSocket(port);

		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			String clientSentence = inFromClient.readLine();
			String[] cmd = clientSentence.split("\\s");

			if (cmd[0].equals("words")) {
				System.out.println("displaying first " + cmd[1] + " words");
				try {
					outToClient.writeBytes(server.myTable.displayFirstWords(Integer.parseInt(cmd[1]), 0));
				} catch (Exception e) {
					errorHandling(outToClient, e);
				}
			} else if (cmd[0].equals("write")) {
				System.out.println("writing " + cmd[1] + " words");
				try {
					outToClient.writeBytes(server.write(Integer
							.parseInt(cmd[1])));
				} catch (Exception e) {
					errorHandling(outToClient, e);
				}
			} else if (cmd[0].equals("add")) {
				System.out.println("adding " + cmd[1] + " to the database");
				try {
					server.addFile("/texts/" + cmd[1]);
				} catch (Exception e) {
					errorHandling(outToClient, e);
				}
				outToClient.writeBytes("done");
			} else if (cmd[0].equals("process")) {
				System.out.println("processing entries");
				try {
					server.myTable.processEntries();
				} catch (Exception e) {
					errorHandling(outToClient, e);
				}
				outToClient.writeBytes("done");
			} else {
				String str = "";
				for (int i = 0; i < cmd.length; i++) {
					str = str + cmd[i];
				}
				outToClient.writeBytes("unrecognized command: " + str);
			}

			System.out.println("done");
			outToClient.close();
		}
	}
}