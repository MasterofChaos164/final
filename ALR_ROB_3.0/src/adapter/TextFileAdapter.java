package adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class TextFileAdapter {
	
	public static final String relativOutputPath = "res/";
	private static String absolutProjectPath;
	private static String absolutOutputPath;
	
	public static void initializeAdapter() {
		absolutProjectPath = new File("").getAbsolutePath() + "/";
		absolutOutputPath = absolutProjectPath + relativOutputPath;
	}
	
	public static boolean createTextFile(String fileName) {
		File file = new File(absolutOutputPath+fileName);
		
        if (file != null) {
            try {
                file.createNewFile();
                System.out.println("Created: "+file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error creating " + file.toString());
            }
            if (file.isFile() && file.canWrite() && file.canRead())
                return true;
        }
        return false;
    }
	
	public static void writeTextFile(String fileName, String text) {			
		PrintWriter out = null;
		File file = new File(absolutOutputPath+fileName);
		try {
			out = new PrintWriter(file);
			System.out.println("Written: "+file.getAbsolutePath());
			out.println(text);
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error init print writer\nLocation: "+file.getAbsolutePath());
		}
	}
	
	public static String readTextFile(String fileName) {
		String line;
        String lines = "";

        try {
            FileReader fileReader = new FileReader(relativOutputPath+fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	lines += line;
                System.out.println(line);
            }  
            bufferedReader.close();            
        } catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '"+fileName+"'");
        } catch(IOException ex) {
            System.out.println(
                "Error reading file '"+fileName+"'");
        }
        return lines;
    }
	
	public static void clearTextFile(String fileName) {
		FileWriter writer = null;
		File file = new File(absolutOutputPath+fileName);
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			System.err.println("Error init file writer " + file.toString());
		}
		
		try {
			writer.write("");
		} catch (IOException e) {
			System.err.println("Error clearing " + file.toString());
		}
	}
	
	public static int[][] convertToIntArray(String text, int rows, int cols) {
		System.out.println(text);
		int[][] intArray = new int[rows][cols];
		for(int row=0;row<rows;row++) {
			for(int col=0;col<cols;col++) {
				intArray[row][col] = text.charAt(((row*cols)+col)*2)-48;
			}
		}
		return intArray;
	}
}
