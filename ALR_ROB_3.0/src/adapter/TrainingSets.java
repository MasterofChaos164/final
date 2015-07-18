package adapter;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;

import adapter.ImageAdapter;

public class TrainingSets {
	
	public static final String line01FileName = "black_white.png";
	public BufferedImage line01Image;
	public int[][] line01RGBSet;
	public int[][] line01Set;
	
	public TrainingSets() {
		initialize();
		generate();
	}
	
	public void initialize() {
		ImageAdapter.initializeAdapter();
		TextFileAdapter.initializeAdapter();
	}
	
	public void generate() {
		generateSetLine01();
//		generateSetExOr();
		
//		System.out.println("\nresult1 in RGB\n");
//		System.out.println("Length: "+twoBoxesRGBSet.length+", Length[0]: "+twoBoxesRGBSet[0].length);
//		Color myColor;
//		for(int i=0;i<twoBoxesRGBSet.length;i++) {
//			for(int j=0;j<twoBoxesRGBSet[0].length;j++) {
//				myColor = new Color(twoBoxesRGBSet[i][j]);
//				System.out.print("("+myColor.getRed()+","+myColor.getGreen()+","+myColor.getBlue()+") ");
//				
//			}
//			System.out.println();
//		}
	}
	
	public void generateSetLine01() {
		line01Image = ImageAdapter.readImage(line01FileName);
		line01RGBSet = ImageAdapter.getPixelsOf(line01Image);
		
//		String set = "";
//		int numSets = line01RGBSet.length*line01RGBSet[0].length;
//		
//		for(int row=0;row<line01RGBSet.length;row++) {
//			for(int col=0;col<line01RGBSet[0].length;col++) {
//				set += row+" "+col+" "+(line01RGBSet[row][col] == -1? 0 : 1);
//				set += System.lineSeparator();
//			}
//			System.out.println("row: "+row);
//		}
//
//		line01Set = TextFileAdapter.convertToIntArray(set,line01RGBSet.length,line01RGBSet[0].length);
	}
	
	public int generateRandomValue(int von, int bis) {
		return (int)(Math.random()*(bis-von+1)+von);
	}
}
