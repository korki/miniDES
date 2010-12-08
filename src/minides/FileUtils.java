package minides;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * @author Orest Hrycyna
 */
public class FileUtils {

    public static ArrayList<String> readFile(String file) {
	ArrayList<String> fileContent = new ArrayList<String>();
	try {
	    BufferedReader in = new BufferedReader(new FileReader(file));
	    String strLine;
	    while ((strLine = in.readLine()) != null) {
		fileContent.add(strLine);
	    }
	    in.close();

	} catch (Exception e) {//Catch exception if any
	    System.err.println("Error: " + e.getMessage());
	}
	return fileContent;
    }

    public static void writeFile(String file, String content, Boolean append) {
	try {
	    BufferedWriter out = new BufferedWriter(new FileWriter(file, append));
	    out.write(content);
	    out.close();
	} catch (Exception e) {//Catch exception if any
	    System.err.println("Error: " + e.getMessage());
	}
    }

    public static void clearFile(String file) {
	writeFile(file, new String(), false);
    }
}
