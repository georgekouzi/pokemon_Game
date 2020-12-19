package gameClient;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameClient.MainManu.action;


/**
 * 
 * @author georg
 *
 */
public class LoginPanel {
	private Component background;
	private static JComboBox  level_game;
	private static int _scenario = 0;
	private static long _id = -1;
	private static JPanel panel;
	private static JFrame login;
	private static JFrame Error;
	private static JButton _loginButton;
	private static JButton _loginn;
	private static JLabel user;
	private static JTextField _userTxt;
	private static JButton Quit;
	private static JLabel scene;
	public boolean b;
	static File folderInput = new File("src\\images\\tieImage.jpg");

	public LoginPanel(){
		b=false;
		panel = new JPanel();
		login = new JFrame();
		Error = new JFrame();
		panel.setLayout(null);
		login.add(panel);
				
		user = new JLabel("User:");
		scene = new JLabel("Scenario:");
		_userTxt = new JTextField(20);
		level_game = new JComboBox(getscenes());
		Quit = new JButton("quit");
		_loginButton = new JButton("Login");
		_loginn = new JButton("Try Again");
		addpanel();
		addlogin();
		addbounds();

	}

	public boolean get_start() {
		return b;
	}
	public long get_id() {
		return _id;
	}
	public int get_scenario() {
		return _scenario;
	}


	private void addbounds() {
		login.setSize(330,160);
		Error.setSize(330,160);
		level_game.setBounds(100,50,165,25);
		_loginButton.setBounds(170,80,120,30);
		_loginn.setBounds(170,80,120,30);
		Quit.setBounds(10,80,120,30);
		_userTxt.setBounds(100,20,165,25);
		scene.setBounds(10,50,80,25);
		user.setBounds(10,20,80,25);

	}


	private void addpanel() {
		background=new JLabel(new ImageIcon(folderInput.getAbsolutePath()));
		panel.add(background);
		panel.add(user);
		panel.add(scene);
		panel.add(_userTxt);
		panel.add(level_game);
		panel.add(Quit);
		panel.add(_loginButton);
		
		_loginButton.addActionListener(new action()); 
		Quit.addActionListener(new action()); 
		level_game.addActionListener(new action());
		_loginn.addActionListener(new action());
		

		// when this window is closed, exit this application
	}

	public void addlogin() {

		login.setVisible(true);
		login.setTitle("login");
		login.setLocationRelativeTo(null); // center this window on the screen
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		login.setVisible(true); 

	}

	private String [] getscenes() {
		String scenes[] = new String[24];
		for(int i = 0; i < 24; i++){
			scenes[i] = String.valueOf(i);
		}
		return scenes;
	}


	public class action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent button) {
			if(button.getSource()==Quit) {
				System.exit(0);
			}

			if(button.getSource()== level_game) {
				int scenario =level_game.getSelectedIndex();
				_scenario = scenario;
			}

			if(button.getSource()==_loginn) {
				Error.dispose();
				addlogin();
			}

			if(button.getSource()==_loginButton) {
				long id=0;
				try { id = Integer.parseInt(_userTxt.getText());
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if(_userTxt.getText().length()==9) {
					_id=id; 
					b=true;
					login.dispose();
				}
				else {
					
					login.dispose();
					Error.setTitle("Error");
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					Error.setLocation(dim.width/2-Error.getSize().width/2, dim.height/2-Error.getSize().height/2);
//					Error.add(Quit, BorderLayout.AFTER_LINE_ENDS);
					Error.add(_loginn, BorderLayout.AFTER_LAST_LINE);
					JLabel title;
					
					Error.add(title = new JLabel(" Error  your id is not much: The ID must contain 9 numbers"), BorderLayout.CENTER);
					Error.setVisible(true);
				
				}

			}

		}
	}
//
//	public static void main(String args[])
//	{
//		LoginPanel l =new LoginPanel();
//	}

}