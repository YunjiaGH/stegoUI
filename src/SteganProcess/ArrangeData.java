package SteganProcess;

import java.util.ArrayList;

public class ArrangeData {
	public ArrayList<String> arrangeImage(ArrayList<String> list){
		
		ArrayList<String> arrangelist = new ArrayList<String>();
		
		
		for(int i=0; i<list.size(); i++){
			String data = Integer.toBinaryString(Integer.parseInt(list.get(i)));
			StringBuffer sb = new StringBuffer(data);
			
			for(int j=0; j<8-data.length(); j++){
				sb.insert(0, "0");
			}

			arrangelist.add(sb.toString());
		}
		
		return arrangelist;
	}
	
	
	public ArrayList<String> arrangeData(ArrayList<String> list){
		
		ArrayList<String> arrangelist = new ArrayList<String>();
		
		for(int i=0; i<list.size(); i++){
			String data = list.get(i);
			char[] strChar = data.toCharArray();
			
			for(int j=0; j<strChar.length; j++){
				
				String binary = Integer.toBinaryString(strChar[j]);
				StringBuffer sb = new StringBuffer(binary);
				
				for(int k=0; k<8-binary.length(); k++){
					sb.insert(0, "0");
				}	
				arrangelist.add(sb.toString());
			}			
		}
		
		return arrangelist;
	}
	
	
	public ArrayList<Character> SplitToSingle (ArrayList<String> list){
		
		ArrayList<Character> singlelist = new ArrayList<Character>();
		
		for(int i=0; i<list.size(); i++){
			String data = list.get(i);
			
			char[] strChar = data.toCharArray();
			
			for(int j=0; j<strChar.length; j++){
				singlelist.add(strChar[j]);
			}			
		}
		
		return singlelist; 
	}
	
}
