import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import utils.Defs;
import utils.Utils;


public class Main {
	
	public static void main(String[] args){
		int[] genome1 = {1,2,3,4,5};
		StringBuilder sb1 = new StringBuilder();
		String Strategy1 = "Protoss_P" + Defs.indevidualCounter++;
		sb1.append("\"" + Strategy1
				+ "\" : { \"Race\" : \"Protoss\", \"OpeningBuildOrder\": ["+Utils.getBuildOrderFromGenome(genome1)+"]},");
		String content;
		try {
			content = new Scanner(new File("test.txt")).useDelimiter("\\Z").next();
			String replacedName = content.replaceAll("REPLACE1", Strategy1);
			String fullyReplaced = replacedName.replaceAll("REPLACE2", sb1.toString());
			File file = new File("test.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(fullyReplaced);
			fileWriter.flush();
			fileWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
