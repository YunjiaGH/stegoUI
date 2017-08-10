package SteganProcess;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GenerateImage {
	public void generate(BufferedImage img, ArrayList<String> list, String path){
		
		Color color;
		int width = img.getWidth();
		int height = img.getHeight();
		int red;
		int green;
		int blue;
		
		ArrayList<Integer> decimalList = new ArrayList<Integer>();
		ArrayList<Color> myColor = new ArrayList<Color>();
		
		for(int i=0; i<list.size(); i++){

			String decimalData = Integer.valueOf(list.get(i),2).toString();
			int data = Integer.valueOf(decimalData);
			decimalList.add(data);			
		}
		
		System.out.println("The size of decimalLise: " + decimalList.size() + "\n");
				
		
		int colorSize = decimalList.size()/3;
		
		for(int i=0; i<colorSize; i++){
			int index = i*3;
			red = decimalList.get(index);
			green = decimalList.get(index+1);
			blue = decimalList.get(index+2);
			color = new Color(red, green, blue);
			myColor.add(color);
			
		}
		
		
		//set color for image
		int indexColor = 0;		
		for(int y=0; y<height; y++){
			for(int x =0; x<width; x++){
				int rgb = myColor.get(indexColor).getRGB();
				img.setRGB(x, y, rgb);
				indexColor++;				
			}
		}
		
		
		String[] ss = path.split("\\.");
		String types = ss[1]; 
		
		File out = new File(path);
		
		try {
		
			ImageIO.write(img, types, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
				
	}
}
