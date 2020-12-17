package gameClient;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * This class represents a very simple  gui class to present a
 * mainmanu befor the game start.
 *
 */

public class MainManu extends JFrame{
	static File folderInput;
	private static JButton _QuitButton;
	private static JButton _StartButton;
	private static JLabel background1;
	public LoginPanel log;
	public boolean start_game;
	/**
	 * consractor crate the mainmanu window and init image beckground the jframes and the buttons for mainmanue panel 
	 *
	 */
	public MainManu() {
		start_game=false;
		folderInput	 = new File("src\\images\\winningImage.png");
		_StartButton = new JButton("Start game");
		_QuitButton = new JButton("Quit");
		_StartButton.addActionListener(new action()); 
		_QuitButton.addActionListener(new action());
		putPicter();
		addButton();
		makeWindow();

	}
	/**
	 * this function return true when we put on loginbutton and the id is currect
	 * for staring game
	 *
	 */
	public boolean get_start() {
		return start_game;
	}
	/**
	 * this function return the Loginpanel for the game
	 *
	 */
public LoginPanel getLoginPanel() {
	return log;
}

/**
 * this function make the mainmanu window for the game
 *
 */
	private void makeWindow() {
		pack();
		setTitle("Pokemon Game");
		setLocationRelativeTo(null); // center this window on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application
		setVisible(true); 
	}
	/**
	 * this function mput the img to beckground
	 *
	 */

	private void putPicter() {
		background1 =new JLabel(new ImageIcon(folderInput.getAbsolutePath()));
		background1.setLayout(new FlowLayout());
		add(background1);
	}
	/**
	 * this function add the buttons to mainmanue window
	 *
	 */

	private void addButton() {
		background1.add(_StartButton);
		background1.add(_QuitButton);
	}

	/**
	 * this inner class pressent  all action on the when click buttons 
	 *
	 */
	
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
}