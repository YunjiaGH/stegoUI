package SteganProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import FileProcess.ReadFile;
import FileProcess.WriteFile;

public class Exchange {
	
	ReadFile readFile = new ReadFile();
	WriteFile print = new WriteFile();

	public ArrayList<String> changeXY(ArrayList<String> imageList, ArrayList<Character> insertList, int key){
		
		int rows; // how many rows will be exchanged from image list;
		int index; // this is the number of last line which will be took 
		
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Character> takenList = new ArrayList<Character>();
		ArrayList<Character> charList = new ArrayList<Character>();
		
	
		index = insertList.size() % key;
		
		if(index == 0){
			rows = insertList.size() / key;
		}else{
			rows = insertList.size() / key + 1;
		}
		
		System.out.println("How many rows will be took from imageList?  -----  " + rows);
		
		for(int i= 0; i<rows; i++){
//			System.out.println(i + " out of " + rows);
			String data = imageList.get(i);
			char[] charData = data.toCharArray();
			
			//put those char into charList
			if(i==rows-1 && index!=0){
				for(int j=0; j<charData.length-index; j++){
					charList.add(charData[j]);
				}
				
			}else{
				for(int j=0; j<charData.length-key; j++){
					charList.add(charData[j]);
				}
			}			
			
			//exchange the new bits 
			if(i==rows-1 && index!=0){
				for(int j=0; j<index;j++){
					charList.add(insertList.get(j));
				}
			
			}else{
				for(int j=0; j<key; j++){
					charList.add(insertList.get(j));
				}
			}			
			
			//put the changed bits into takenlist, and flush insertlist
			if(i==rows-1 && index!=0){
				for(int j =index; j>0;j--){
					takenList.add(charData[charData.length-j]);
					insertList.remove(0);
				}
				
			}else{
				for(int j=key; j>0; j--){
					takenList.add(charData[charData.length-j]);
					insertList.remove(0);
				}
			}	
			
			
		}
		
		
		//load the rest data whichi not be changed. 
		for(int i=0; i<imageList.size()-rows; i++){
	
			String data = imageList.get(rows+i);
			char[] charData = data.toCharArray();
			
			for(int j =0; j<charData.length; j++){
				charList.add(charData[j]);
			}
		}
		
				
		//Put those char to a string arraylist
		for(int i=0; i<(charList.size())/8;i++){
			int indexChar =i*8;
			char charNum1 = charList.get(indexChar);
			char charNum2 = charList.get(indexChar+1);
			char charNum3 = charList.get(indexChar+2);
			char charNum4 = charList.get(indexChar+3);
			char charNum5 = charList.get(indexChar+4);
			char charNum6 = charList.get(indexChar+5);
			char charNum7 = charList.get(indexChar+6);
			char charNum8 = charList.get(indexChar+7);
		
			
			char[] ss = {charNum1,charNum2, charNum3, charNum4, charNum5, charNum6, charNum7, charNum8};
			
			String data = String.valueOf(ss);
			
			list.add(data);
		}
	
		
		return list;
	}

	public ArrayList<String> changeRandom(ArrayList<String> img_list, ArrayList<Character> insert_list, int key){
		
		int rows; // how many rows will be took from image list;
		int index_lastline_bit; // this is the number of last line which will be took 
		
		ArrayList<String> list = new ArrayList<String>();
//		ArrayList<Character> takenList = new ArrayList<Character>();
		ArrayList<Character> charList = new ArrayList<Character>();
		
		ArrayList<Integer> random_index = new ArrayList<Integer>(); //important for key 
		ArrayList<String> random_list = new ArrayList<String>();
		ArrayList<String> random_exchange_list = new ArrayList<String>();
		
		
		index_lastline_bit = insert_list.size() % key;
		
		if(index_lastline_bit == 0){
			rows = insert_list.size() / key;
		}else{
			rows = insert_list.size() / key + 1;
		}
		
		System.out.println("How many rows will be took from imageList?  -----  " + rows);
		
		// make sure the rows, then make sure the data of those rows , get random
		
		int num; 
		Random ran = new Random();
		boolean[] bool = new boolean[img_list.size()];
		for(int i = 0; i<rows; i++){
			do{
				num = ran.nextInt(img_list.size());
			}while(bool[num]);
			bool[num] = true;
			random_index.add(num);
			
		}
		
		Collections.sort(random_index);
		
		String ran_key_path = "OutFile/random_index.txt";
		
		print.WriteData(random_index, ran_key_path);
		
//		System.out.println(random_index);
		
		for(int i = 0; i < random_index.size(); i++){
			random_list.add(img_list.get(random_index.get(i)));
		}
		
//		System.out.println(random_list);
		
		for(int i = 0; i < random_list.size(); i++){
			String data = random_list.get(i);
			char[] charData = data.toCharArray();
			
			if(i ==random_index.size()-1 && index_lastline_bit!=0){
				for(int j = 0; j<charData.length - index_lastline_bit; j++){
					charList.add(charData[j]);
				}
			}else{
				for(int j =0; j<charData.length-key; j++){
					charList.add(charData[j]);
				}
			}
			
			if(i ==random_index.size()-1 && index_lastline_bit!=0){
				for(int j =0; j<index_lastline_bit; j++){
					charList.add(insert_list.get(0));
					insert_list.remove(0);
				}
			}else{
				for(int j=0; j<key; j++){
					charList.add(insert_list.get(0));
					insert_list.remove(0);					
				}
			}
					
		}
		
		
		for(int i=0; i<(charList.size())/8;i++){
			int indexChar =i*8;
			char charNum1 = charList.get(indexChar);
			char charNum2 = charList.get(indexChar+1);
			char charNum3 = charList.get(indexChar+2);
			char charNum4 = charList.get(indexChar+3);
			char charNum5 = charList.get(indexChar+4);
			char charNum6 = charList.get(indexChar+5);
			char charNum7 = charList.get(indexChar+6);
			char charNum8 = charList.get(indexChar+7);
		
			
			char[] ss = {charNum1,charNum2, charNum3, charNum4, charNum5, charNum6, charNum7, charNum8};
			
			String data = String.valueOf(ss);
			
			random_exchange_list.add(data);
		}
		
//		System.out.println(random_exchange_list);
		
		int index =0;
		
		for(int i=0; i<img_list.size();i++){
			
			if(i == Integer.valueOf(random_index.get(index))){
				list.add(random_exchange_list.get(index));
				if(index<random_index.size()-1){
					index++;
				}else{
					index=0;
				}
				
			}else{
				list.add(img_list.get(i));
			}
		}
			
		
		
		
//		System.out.println(list);
		return list;
				
		
	}
}
