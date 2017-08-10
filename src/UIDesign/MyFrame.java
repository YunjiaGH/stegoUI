package UIDesign;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import SteganProcess.ProcessFlow;

public class MyFrame extends JFrame {

	// set Windows size
	int width = 800;
	int height = 400;

	int step1 = 0;
	int step2 = 0;
	int step3 = 0;;

	String dataFeedback = "<html>Import and Cover image and Data first, " + "<br>Then Start the Stegan Proces! "
			+ "<br> Thank you !!! </html>";

	// JLabel inforLabel = new JLabel();
	JLabel imageLabel = new JLabel();
	JLabel dataLabel = new JLabel();
	ButtonFrame bf = new ButtonFrame(this);
	ProcessFlow proces = new ProcessFlow();

	public MyFrame() {
		initFrame();
	}

	private void initFrame() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((screenSize.width) / 6, (screenSize.height) / 6, width, height);
		this.setTitle("Steganography");
		this.setLayout(new BorderLayout());

		dataLabel.setText(dataFeedback);

		this.add(imageLabel, BorderLayout.NORTH);
		this.add(dataLabel, BorderLayout.CENTER);
		this.add(bf, BorderLayout.SOUTH);

		this.initMenu();
		this.initExit();
	}

	private void initMenu() {

		JMenu About = new JMenu("About");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> aboutList = new ArrayList<String>();
				aboutList.add("There is no information here! At all!");
				aboutInfor(aboutList);
			}

		});

		JMenu Help = new JMenu("Help");
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> helpList = new ArrayList<String>();
				helpList.add("Any questions just contact: \nyw43@st-andrews.ac.uk ");
				helpInfor(helpList);
			}

		});

		About.add(about);
		Help.add(help);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(About);
		menuBar.add(Help);

		this.setJMenuBar(menuBar);
	}

	private void initExit() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(MyFrame.this, "Are you Sure to Exit?", "Exit Confirm!",
						JOptionPane.OK_CANCEL_OPTION);
				if (JOptionPane.OK_OPTION == option) {
					System.exit(0);
				} else {
					MyFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}

	private void aboutInfor(ArrayList<String> list) {

		JOptionPane.showMessageDialog(this, list.get(0), "About", JOptionPane.INFORMATION_MESSAGE);
	}

	private void helpInfor(ArrayList<String> list) {

		JOptionPane.showMessageDialog(this, list.get(0), "Help", JOptionPane.INFORMATION_MESSAGE);
	}

	public void coverImage() {
		FileDialog fd = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
		fd.setVisible(true);

		if (fd.getDirectory() != null && fd.getFile() != null) {
			String path = fd.getDirectory() + fd.getFile();
			File file = new File(path);
			if (imageType(file)) {

				if (file.exists()) {
					try {
						BufferedImage img = ImageIO.read(file);
						ImageIcon icon = new ImageIcon(img);

						if (img.getWidth() < 400) {
							this.setSize(400, img.getHeight() + 150);
						} else {
							this.setSize(img.getWidth() + 20, img.getHeight() + 160);
						}

						imageLabel.setIcon(icon);
						proces.cover_image(path);
						step1 = 1;

					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "The Selected was not a Picture", "WARNING",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "The Picture is not *.jpg||*.png||*.bmp*", "WARNING",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public void hideData() {
		FileDialog fd = new FileDialog(new Frame(), "Open", FileDialog.LOAD);
		fd.setVisible(true);

		if (fd.getDirectory() != null && fd.getFile() != null) {
			String path = fd.getDirectory() + fd.getFile();
			File file = new File(path);
			if (txtType(file)) {
				ArrayList<String> dataInfor = new ArrayList<String>();
				dataInfor = proces.hide_data(path);

				dataFeedback = "<html>The line of data: " + dataInfor.get(0) + ";  <br>The character size of data: "
						+ dataInfor.get(1) + ";  <br>The bit size of data: " + dataInfor.get(2) + ";  <br>The path is: "
						+ path + " </html>";

				dataLabel.setText(dataFeedback);
				step2 = 1;

			} else {
				JOptionPane.showMessageDialog(null, "The data file is not *.txt*", "WARNING",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public void startStegan() {
		step3 = step1 + step2;
		if (step3 != 2) {
			JOptionPane.showMessageDialog(null,
					"Should Import Cover Image and Embed Data First, \nThen Start Stegan Process.", "WARNING",
					JOptionPane.ERROR_MESSAGE);
		} else {
			proces.start_stegan();
		}
	}

	private boolean imageType(File file) {
		return (isJPG(file) || isBMP(file) || isPNG(file));
	}

	private boolean txtType(File file) {
		return (isTXT(file));
	}

	private boolean isTXT(File file) {
		return (file.getName().endsWith(".txt"));
	}

	private boolean isJPG(File file) {
		return (file.getName().endsWith(".jpg"));
	}

	private boolean isBMP(File file) {
		return (file.getName().endsWith(".bmp"));
	}

	private boolean isPNG(File file) {
		return (file.getName().endsWith(".png"));
	}

}
