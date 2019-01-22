import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(3333);
			while(true) {
				Socket connectionSocket = serverSocket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());
				String lineFromClient = inFromClient.readLine();
				System.out.println(lineFromClient);
				//lineFromClient = lineFromClient.toUpperCase();
				//outToClient.write(lineFromClient);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
