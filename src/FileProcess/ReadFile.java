package FileProcess;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ReadFile {
	
		private FileReader fr;
		private BufferedReader br;
		private String data;
		
		private File imageFile; 
		private BufferedImage img;
		
	public ArrayList<String> readData(String path){	
		ArrayList<String> fileData = new ArrayList<String>();
			
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr); 
				
			while((data=br.readLine())!=null){
				fileData.add(data);			
				fileData.add("\r\n");
			}		
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println(e);		
		} catch (IOException e) {
				// TODO Auto-generated catch block
			System.err.println(e);		
		}			
		return fileData;
	}
	
	public ArrayList<String> readImage(String path){
		ArrayList<String> fileData = new ArrayList<String>();
		
		try {
			
			imageFile = new File(path);
			img= ImageIO.read(imageFile);
			
			int width =img.getWidth();
			int height =img.getHeight();
			
	
			for(int y=0; y<height; y++){
				for(int x=0; x<width; x++){
					
					int pixel =img.getRGB(x, y);
					int red = (pixel & 0xff0000) >>16;
					int green = (pixel & 0xff00) >>8;
					int blue = (pixel & 0xff);
					
					fileData.add(String.valueOf(red));
					fileData.add(String.valueOf(green));
					fileData.add(String.valueOf(blue));				
				}
			}			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}	
		return fileData;
	}
	
	public BufferedImage getBufferedImage(){
		return img;
	}
}
