package FileProcess;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteFile {
	
	private FileWriter fw;
	private PrintWriter pw;
	
	public void WriteData(ArrayList list, String path){
		
		try {
			
			fw = new FileWriter(path);
			pw = new PrintWriter(fw);
			
			pw.flush();
			
			for(int i=0; i<list.size(); i++){
				pw.write(list.get(i) + "\r\n");
				
			}
			pw.close();
			
			System.out.println("The File was printed out, the path is: ->" + path +"\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		
	}
	

	public void WriteKey(int bits, int size, String path){
		
		
		try {
			
			fw = new FileWriter(path);
			pw = new PrintWriter(fw);
	
			pw.flush();
			pw.write("Bits:" + bits + "\r\n");
			pw.write("Size:" + size + "\r\n");
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
	
	
}
