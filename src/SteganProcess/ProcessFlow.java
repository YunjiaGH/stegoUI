package SteganProcess;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import FileProcess.ReadFile;
import FileProcess.WriteFile;

public class ProcessFlow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ReadFile readFile = new ReadFile();
	ArrangeData arrange = new ArrangeData();		
	WriteFile print = new WriteFile();
	Exchange exchange = new Exchange();
	GenerateImage generateImage = new GenerateImage();
	
	ArrayList<String> cover_image_list = new ArrayList<String>(); //step 1
	ArrayList<String> image_binary_list = new ArrayList<String>(); //step 2
	ArrayList<String> data_list = new ArrayList<String>(); //step 3
	ArrayList<String> data_binary_list = new ArrayList<String>(); //step 4
	ArrayList<Character> single_data_binary_list = new ArrayList<Character>(); //step 5
	ArrayList<String> changed_list = new ArrayList<String>(); //step 6
	
	
	int KeyOfLength = 0;
	int KeyOfIndex = 0;
	
	public void cover_image(String path){
		
		System.out.println("Step 1: Load cover image"); // read image, collect image to arraylist
		System.out.println("--------------------------");	
		
		cover_image_list = readFile.readImage(path);
		System.out.println("Picture was opened, the path: " + path);
		System.out.println("The size of cover_image_list: " + cover_image_list.size());
		System.out.println("-----Step 1 DONE-----\n");
/*
 * --------------------------------------------------------------------------------------------------------------		
 */
		
		System.out.println("\nStep 2:Convert above data to 8 bits binary");
		System.out.println("----------------------------------------------");
		
		String OutPath2 = "OutFile/image_binary_out.txt";
		image_binary_list = arrange.arrangeImage(cover_image_list);
		
		print.WriteData(image_binary_list, OutPath2);

		System.out.println("The size of image_binary_list: " + image_binary_list.size());	
		System.out.println("-----Step 2 DONE-----\n");
		
	}
	
	
	public ArrayList<String> hide_data(String path){
		
		System.out.println("\nStep 3: Load confidential data "); // read data, collect data to arraylist
		System.out.println("---------------------------------");	
		
		data_list = readFile.readData(path);
		System.out.println("The size of data_list: "+ data_list.size());
		System.out.println("-----Step 3 DONE-----\n");
				
/*
 * --------------------------------------------------------------------------------------------------------------		
 */			
		
		System.out.println("\nStep 4: Convert above data to 8 bits binary");
		System.out.println("--------------------------------------------------");
		
		String OutPath4 = "OutFile/data_binary_out.txt";
		data_binary_list = arrange.arrangeData(data_list);
		
		print.WriteData(data_binary_list, OutPath4);
		
		System.out.println("The size of data_binary_list: " + data_binary_list.size());	
		System.out.println("-----Step 4 DONE-----\n");				
		
/*
* --------------------------------------------------------------------------------------------------------------		
*/	
		
		System.out.println("\nStep 5: Split above binary in single value. For calculate the bit length of data  ");
		System.out.println("------------------------------------------------------------------------------------");
		
		single_data_binary_list = arrange.SplitToSingle(data_binary_list);
		
		KeyOfLength = single_data_binary_list.size();
		System.out.println("The size of single_data_binary_list: " +  KeyOfLength);
		System.out.println("-----Step 5 DONE------\n");	
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(Integer.toString(data_list.size()));
		list.add(Integer.toString(data_binary_list.size()));
		list.add(Integer.toString(KeyOfLength));
		
		return list;
		
	}
	
	public void start_stegan(){
		System.out.println("\nStep 6: Start hide data into image ");
		System.out.println("-------------------------------------\nStart Chechk Data Size ---");

		String image_type = null;	
		String bits_exchange = null;
		int imageSize = image_binary_list.size(); 
		int insertSize = KeyOfLength; 	
		
		boolean isTrue_first = true;
		boolean isTrue_second = true;
		boolean isTrue_third = true;
		
		if(insertSize>imageSize*8){
			JOptionPane.showMessageDialog(null, "Oops, \nThe length of insert data is over the size of image. Please change a large image !!", "WARNING", JOptionPane.ERROR_MESSAGE);
			
		}else{
			JOptionPane.showMessageDialog(null, "Prepare to start stegan process!", "Notice", JOptionPane.INFORMATION_MESSAGE);
		
			while(isTrue_first){
				Object[] typesObj = {".jpg", ".png", ".bmp"};
				image_type = (String)JOptionPane.showInputDialog(this, "Choose the type of generate image:\n","Stegan Image Type", JOptionPane.PLAIN_MESSAGE, null, typesObj,".png");
			
				if(image_type !=null){
					
					while(isTrue_second){
						Object[] bitsObj = {"1","2","3","4","5","6","7","8"};
						bits_exchange = (String)JOptionPane.showInputDialog(this, "How many bits want to exchange:\n","Last Bits Choice", JOptionPane.PLAIN_MESSAGE, null, bitsObj,"1");
						
						if(bits_exchange != null){
								
							KeyOfIndex = Integer.valueOf(bits_exchange);
												
							int total_taken_bits = KeyOfIndex * imageSize; 
							
							if(total_taken_bits < insertSize){ //  total_take_bits_from_image < data_total_length
								System.err.println("refresh Key");
								if(insertSize % imageSize == 0){
									KeyOfIndex = insertSize / imageSize;
								}else{
									KeyOfIndex = insertSize / imageSize + 1;
								}
								
								String notice = "Totally Exchange Bits not Enough to Cover Original Data, \nThe Bit at least: \n" +KeyOfIndex;
								JOptionPane.showMessageDialog(null, notice, "Warning", JOptionPane.ERROR_MESSAGE);
								
							}else{
								
								while(isTrue_third){
									Object[] hideObj = {"From Horizontal direction", "In Random"};
									String hide_method = (String) JOptionPane.showInputDialog(this,"Choose the hiding method: ", "Hide Method", JOptionPane.PLAIN_MESSAGE,null,hideObj,"From Horizontal direction");
									
								
									if(hide_method!=null){
										
										if(hide_method.equals("From Horizontal direction")){
											changed_list = exchange.changeXY(image_binary_list, single_data_binary_list, KeyOfIndex);
											isTrue_second = false;
											isTrue_first = false;
											break;
											
										}else if(hide_method.equals("In Random")){
											changed_list =exchange.changeRandom(image_binary_list, single_data_binary_list, KeyOfIndex);									
											isTrue_second = false;
											isTrue_first = false;
											break;											
										}
																				
									}else{
										JOptionPane.showMessageDialog(null, "Please Choose a Option !", "Notice", JOptionPane.INFORMATION_MESSAGE);
										isTrue_second = false;
										isTrue_first= false;
										break;
									}
								}
								
								
//								changed_list = exchange.change(image_binary_list, single_data_binary_list, KeyOfIndex);
//								isTrue_first = false;
//								break;								
								
							}				
							
						}else{
							JOptionPane.showMessageDialog(null, "Please Choose a Option !", "Notice", JOptionPane.INFORMATION_MESSAGE);
							isTrue_first= false;
							break;
						}
					}
				
				}else{
					JOptionPane.showMessageDialog(null, "Please Choose a Type !", "Notice", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
							
			}
		}
		
		String ex_path = "OutFile/exchang_list.txt";
		print.WriteData(changed_list, ex_path);
		
		System.out.println("The size of changed_list: " + changed_list.size());
		System.out.println("-----Step 6 DONE-----\n");
		
/*
 * --------------------------------------------------------------------------------------------------------------		
 */			
		
		System.out.println("\nStep 7: System will generate a new image according the above changed_list ");
		System.out.println("----------------------------------------------------------------------------");
		
		String OutImage = "OutFile/ImageNew" + image_type;
		BufferedImage img = readFile.getBufferedImage();
		generateImage.generate(img, changed_list, OutImage);
		
		JOptionPane.showMessageDialog(null, "Done! \nThe Stegan Image Will be Display in a new Windows", 
				"Successful!", JOptionPane.INFORMATION_MESSAGE);
		
		System.out.println("Done !!! Data was hidded in Image. the path of new image is: ----->  " + OutImage);
		System.out.println("-----Step 7 DONE-----\n");
		
		
/*
 * --------------------------------------------------------------------------------------------------------------		
 */
		System.out.println("\nStep 8: Key will be released ");
		System.out.println("-------------------------------");
		
		String KeyPath = "OutFile/key.txt";
		print.WriteKey(KeyOfIndex, KeyOfLength, KeyPath);
		
		initNewImage(OutImage, KeyOfIndex, KeyOfLength);
		
		System.out.println("-----Stego Process Done -----\n");
		
		System.out.println("=============================");
		System.out.println("|   Welcome to use again    |");
		System.out.println("=============================");
			
	
	}	 		
		
	
	private void initNewImage(String path, int bits, int sizes){
		JFrame frame = new JFrame();
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		try {
			BufferedImage img = ImageIO.read(new File(path));
			ImageIcon icon = new ImageIcon(img);
			
//			String key_infor [] = {Integer.toString(bits), Integer.toString(sizes)};
			ArrayList<String> key_infor = new ArrayList<String>();
			key_infor.add(Integer.toString(bits));
			key_infor.add(Integer.toString(sizes));
			String keyFeedback = "<html>Bits: " + key_infor.get(0) + 
					"; <br>Size: " + key_infor.get(1)+"; </html>";
			
			
//			if(img.getWidth()<400){
//				this.setSize(400,img.getHeight()+150);	
//			}else{							
//				this.setSize(img.getWidth()+20,img.getHeight()+160);	
//			}
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
			frame.setBounds((screenSize.width-img.getWidth())/2, (screenSize.height-img.getHeight())/2, 
					img.getWidth()+20, img.getHeight()+80);
			
			label1.setIcon(icon);
			label2.setText(keyFeedback);
			
			frame.setLayout(new BorderLayout());
			
			frame.add(label1, BorderLayout.CENTER);
			frame.add(label2, BorderLayout.SOUTH);	

			frame.setVisible(true);			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
