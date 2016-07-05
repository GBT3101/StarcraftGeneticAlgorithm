package ResultsAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reader {

	public static Results readFile(String path) throws IOException{
		Results gameResult = new Results();
		File resultsFile = new File(path);
		String results = "";
		byte[] data = null;
		try {
			FileInputStream fis = new FileInputStream(resultsFile);
			data = new byte[(int) resultsFile.length()];
			fis.read(data);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(data!=null){
			results = new String(data, "UTF-8");
			System.out.println(results);
		} else {
			System.out.println("reading file failed.");
			return null;
		}
		String[] games = results.split("\n");
		
		for(int i=1; i<games.length; i+=2){
			games[i] = games[i].trim().replaceAll(" +", " ");
			String[] components = games[i].split(" ");
			String host = components[2];
			String guest = components[3];
			String win = components[5];
			String hostScore = components[9];
			String guestScore = components[10];
			String gameTime = components[11];
			if(Integer.parseInt(gameTime)<50000){
				if(win.equals("true")){
					gameResult.addWinTo(host);
				} else if(components[6].equals("false")&&components[7].equals("false")&&components[8].equals("false")) {
					gameResult.addWinTo(guest);
				} 
			}
			gameResult.addScoreTo(Integer.parseInt(hostScore), host);
			gameResult.addScoreTo(Integer.parseInt(guestScore), guest);
			gameResult.addTimeTo(Integer.parseInt(gameTime), host);
			gameResult.addTimeTo(Integer.parseInt(gameTime), guest);
			
		}
		return gameResult;
	}
}
