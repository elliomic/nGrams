import java.io.*;
import java.net.*;

class TCPClient {
	private static String toString(Object[] array) {
		String str = "";
		int i = 0;
		while (i <= array.length - 2) {
			str = str + array[i] + " ";
			i++;
		}
		return str + array[array.length - 1];
	}
	
	private static String request(String cmd, Socket clientSocket) throws Exception {
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes(cmd + "\n");
		String sentence = inFromServer.readLine();
		clientSocket.close();
		return sentence;
	}
	
	public static String tcpSort(Object[] array, Socket socket) throws Exception {
		return request("sort " + toString(array), socket);
	}
	
	public static void main(String[] args) throws Exception {
		Object[] arr = new Object[10];
		for (int i = 0; i < 10; i++) {
			arr[(int)i] = 10 - i;
		}
		System.out.println(tcpSort(arr, new Socket("localhost", 5555)));
	}
}