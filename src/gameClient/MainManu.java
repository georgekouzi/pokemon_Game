package gameClient;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainManu extends JFrame{
	static File folderInput;
	private static JButton _QuitButton;
	private static JButton _StartButton;
	private static JLabel background1;
	public LoginPanel log;
	public boolean start_game;

	public MainManu() {
		start_game=false;
		folderInput	 = new File("src\\images\\winningImage.png");
		_StartButton = new JButton("Start game");
		_QuitButton = new JButton("Quit",new ImageIcon("src\\images\\sed.jpg"));
		_QuitButton.setMargin(new Insets(0, 0, 0, 0));
		_StartButton.addActionListener(new action()); 
		_QuitButton.addActionListener(new action());
		putPicter();
		addButton();
		makeWindow();

	}
	
	public boolean get_start() {
		return start_game;
	}

public LoginPanel getLoginPanel() {
	return log;
}

	private void makeWindow() {
		pack();
		setTitle("Pokemon Game");
		setLocationRelativeTo(null); // center this window on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application
		setVisible(true); // ca
	}

	private void putPicter() {
		background1 =new JLabel(new ImageIcon(folderInput.getAbsolutePath()));
		background1.setLayout(new FlowLayout());
		add(background1);
//		_QuitButton.setIcon(new ImageIcon("src\\images\\sed.jpg"));
//		_QuitButton.setSize(1, 1);
	}


	private void addButton() {
		background1.add(_StartButton);
//		_QuitButton.setBounds(20, 20, 20, 20);
		background1.add(_QuitButton);


	}

	public class action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent button) {
			if(button.getSource()==_QuitButton) {
				System.exit(0);
			}
			if(button.getSource()==_StartButton) {
				log = new LoginPanel();
				start_game=true;
				
			}

		}
	
	}
	public static void main(String[] args) {
		MainManu a= new MainManu();

	}
}
