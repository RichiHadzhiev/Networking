import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	public static void main(String args[]) {
		try {
			Socket socket = new Socket("localhost", 3333);
			PrintWriter outToServer = new PrintWriter(socket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
			String fromUserLine = fromUser.readLine();
			outToServer.write(fromUserLine);
			//String lineFromServer = inFromServer.readLine();
			//System.out.println(lineFromServer);
			//socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
