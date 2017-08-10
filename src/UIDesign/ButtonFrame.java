package UIDesign;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonFrame extends JPanel {

	private MyFrame myFrame;

	public ButtonFrame(MyFrame frame) {
		myFrame = frame;
		initButton();
	}

	private void initButton() {
		JButton jb1 = new JButton("CoverImage");
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myFrame.coverImage();
			}

		});

		JButton jb2 = new JButton("HideData");
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myFrame.hideData();
			}

		});

		JButton jb3 = new JButton("StartStegan");
		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				myFrame.startStegan();
			}

		});

		this.setLayout(new GridLayout(1, 3));
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
	}
}
