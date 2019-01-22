import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Client {
	public static void launchClient() throws UnknownHostException, IOException {
		System.out.println("Client started.");
		String separator = ",";
		String inFile = "src/logs.csv";
		String decodeFile = "src/out.txt";

		List<String> inFileList = new ArrayList<String>();
		int[] keyArray = new int[65000];
		
		addToList(separator, inFile, inFileList);

		encode(inFileList, keyArray);
		
		Socket socket = new Socket("localhost", 2019);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		for(int i=0; i<inFileList.size(); i++) {
			outToServer.write(keyArray[i]);
		}

		decode(decodeFile, inFileList, keyArray);
		
		//System.out.println(inFromServer.readLine());
		
		inFromServer.close();
		outToServer.close();
		socket.close();
	}
	
	//encode method
	public static void encode(List<String> _inFileList, int[] _keyArray) {
		Iterator<String> iter = _inFileList.iterator();
		int index = 0;
		int key = 0;
		List<String> eventContext = new ArrayList<String>();
		while(iter.hasNext()) {
			String curr = iter.next();
			if(eventContext.contains(curr)) {
				int firstCurr = 0;
				Iterator<String> eventContextIter = eventContext.iterator();
				String otherCurr = eventContextIter.next();
				while(eventContextIter.hasNext() && !curr.equals(otherCurr)) {
					firstCurr++;
					otherCurr = eventContextIter.next();
				}
				_keyArray[index] = _keyArray[firstCurr];
			}
			else {
				_keyArray[index] = key;
				key++;
			}
			eventContext.add(curr);
			index++;
		}
	}
	
	//add data from log file to list
	public static void addToList(String _separator, String _inFile, List<String> _inFileList) {
		String line = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(_inFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while((line = br.readLine()) != null){
				String[] lineSplit = line.split(_separator);
				byte bytes[] = lineSplit[2].getBytes("UTF-8"); 
				String value = new String(bytes, "UTF-8"); 
				_inFileList.add(value);
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//decode method
	public static void decode(String _decodeFile, List<String> _inFileList, int[] _keyArray) throws IOException {
		BufferedReader readOutput = new BufferedReader(new FileReader(_decodeFile));
		String outLine = "";
		try {
			while((outLine = readOutput.readLine()) != null) {
				String[] outLineSplit = outLine.split(":");
				int outKey = Integer.parseInt(outLineSplit[0]);
				Iterator<String> iter1 = _inFileList.iterator();
				int i = 0;
				String outputSentence = iter1.next();
				while(iter1.hasNext() && _keyArray[i]!=outKey) {
					i++;
					outputSentence = iter1.next();
				}
				System.out.println(outputSentence + " : " + outLineSplit[1] + " times");
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readOutput.close();
	}
}
