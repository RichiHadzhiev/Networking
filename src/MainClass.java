import java.io.IOException;
import java.net.UnknownHostException;

public class MainClass {
	public static void main(String args[]) {
		Thread th1 = new Thread() {
			public void run() {
				Server.launchServer();
			}
		};
		Thread th2 = new Thread() {
			public void run() {
				try {
					Client.launchClient();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		th1.start();
		th2.start();
	}
}
