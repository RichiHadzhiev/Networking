import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;

public class Server {
	public static void launchServer() {
		System.out.println("Server started.");
		try(ServerSocket serverSocket = new ServerSocket(2019)) {
			Socket connectionSocket = serverSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());
			String tempFile = "src/temp.txt";
			String outFile = "src/out.txt";
			PrintWriter writeToTempFile = new PrintWriter(tempFile);
			String s = Integer.toString(inFromClient.read());

			while(s != null && !s.isEmpty() && Integer.parseInt(s) >= 0) {
				writeToTempFile.write(s + System.lineSeparator());
				s = Integer.toString(inFromClient.read());
			}
			double minsup = 0.02;
			AlgoApriori alg = new AlgoApriori();
			alg.runAlgorithm(minsup, tempFile, outFile);
			inFromClient.close();
			writeToTempFile.close();
			
			//outToClient.println("Random sentence");
			
			outToClient.close();
			connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
