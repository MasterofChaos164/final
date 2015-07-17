package adapter;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;

public class ImageAdapter {

	public static final String relativOutputPath = "res/";
	private static String absolutProjectPath;
	private static String absolutOutputPath;
	private static final String[] mimeTypes = { ".bmp", ".gif", ".jpg", ".png", ".tif" };
	
	public static void initializeAdapter() {
		absolutProjectPath = new File("").getAbsolutePath() + "/";
		absolutOutputPath = absolutProjectPath + relativOutputPath;
	}
	
	public boolean writeImage(byte[] data, String fileName) {
        File file = new File(absolutOutputPath+fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            return true;
        } catch (FileNotFoundException e) {
            System.err.println(file + " doesn't exist!");
        } catch (IOException e) {
            System.err.println("Problems writing data to " + file);
        } finally {
            try {
                if(fos != null) fos.close();
            }catch(IOException e){}
        }
        return false;
    }
	  
	public static boolean isWantedFile(String fileName) {
	    for (String mimeType: mimeTypes){ 
	    	if (fileName.endsWith(mimeType)) {
	    		return true;
	    	}
	    }
	    return false;
	}

	public static int[][] getPixelsOf(BufferedImage image) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData(); 
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		int[][] result = new int[height][width];
	      
		if (hasAlphaChannel) {
	        final int pixelLength = 4;
	        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
	            int argb = 0;
	            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
	            argb += ((int) pixels[pixel + 1] & 0xff); // blue
	            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
	            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
	            result[row][col] = argb;
	            col++;
	            if (col == width) {
	               col = 0;
	               row++;
	            }
	        }
		} else {
			final int pixelLength = 3;
	        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
	        	int argb = 0;
	            argb += -16777216; // 255 alpha
	            argb += ((int) pixels[pixel] & 0xff); // blue
	            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
	            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
	            result[row][col] = argb;
	            col++;
	            if (col == width) {
	               col = 0;
	               row++;
	            }
	        }
	    }
		return result;
	}
	
	public static BufferedImage setARGBPixelsOf(BufferedImage image, int[][] result) {
		//TODO: Implementierung via:
//		final int pixelLength = 4;
//		byte[] pixels = new byte[result.length*result[0].length*pixelLength];
//	    
//		for (int row=0;row<result.length;row++) {
//			for (int col=0;col<result[0].length;col+=pixelLength) {
//				int argb = 0;
//				pixels[row*result[0].length+col] = (byte)((result[row][col] >>24) & 0xff); // alpha
//				pixels[row*result[0].length+col+1] = (byte)(result[row][col+1] & 0xff); // blue
//				pixels[row*result[0].length+col+2] = (byte)((result[row][col+2] >> 8) & 0xff); // green
//				pixels[row*result[0].length+col+3] = (byte)((result[row][col+3] >> 16) & 0xff);; // red
//			}
//		}
		
		for (int row=0;row<result.length;row++) {
			for (int col=0;col<result[0].length;col++) {
				image.setRGB(col, row, result[row][col]);
			}
		}
		
		return image;
	}
	
	public static BufferedImage setRGBPixelsOf(BufferedImage image, int[][] result) {
		//TODO: Performance Optimierung via:
//		final int pixelLength = 3;
//		byte[] pixels = new byte[result.length*result[0].length*pixelLength];
//	    
//		for (int row=0;row<result.length;row++) {
//			for (int col=0;col<result[0].length;col+=pixelLength) {
//				int argb = 0;
//				pixels[row*result[0].length+col] = (byte)(result[row][col] & 0xff); // blue
//				pixels[row*result[0].length+col+1] = (byte)((result[row][col+1] >> 8) & 0xff); // green
//				pixels[row*result[0].length+col+2] = (byte)((result[row][col+2] >> 16) & 0xff);; // red
//			}
//		}
		
		for (int row=0;row<result.length;row++) {
			for (int col=0;col<result[0].length;col++) {
				image.setRGB(col, row, result[row][col]);
			}
		}
		
		return image;
	}

	public static BufferedImage readImage(String fileName) { 
		if(!isWantedFile(fileName)) {
			System.out.println("Error: File type denied");
			return null;
		} 
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(absolutOutputPath+fileName));
		} catch (IOException e) {
			System.err.print("Image not found: "+absolutOutputPath+fileName);
		}
		return img;
	}
}