package gameClient;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import api.dw_graph_algorithms;
import okhttp3.internal.ws.RealWebSocket.Close;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;



public class BackgroundImageJFrame extends JFrame
{
	// instance variables
	// instance variables should really be private to keep code in other classes from messing them up.
	private JButton b1;
	private JLabel l1;
	static File folderInput = new File("C:\\Users\\user\\Downloads\\PokeRoll-master\\Pokemon Game Final Summative\\src\\winningImage.png");
	private static int _scenario = 0;
	private static int _id = -1;
	private static JComboBox _sceneNum;
	private static JButton Quit;
	private static JButton _loginButton;
	private static JTextField _userTxt;
	private static JFrame login;
	private static MyFrame _win;
	private static Arena _ar;
	private static dw_graph_algorithms algo;
	private static MyFrame _win2;
	private static Image image;
	


	public BackgroundImageJFrame(File imageFile)
	{
		setLayout(new BorderLayout());
		JLabel background=new JLabel(new ImageIcon(imageFile.getAbsolutePath()));
		add(background);
		background.setLayout(new FlowLayout());
		l1=new JLabel("Here is a button");
		b1=new JButton("start game");
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginPanel();				
			}
		});
		background.add(l1);
		background.add(b1);

		setTitle("Pokemon");
		pack(); // automatically size the window to fit its components
		setLocationRelativeTo(null); // center this window on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application
		setVisible(true); // call setVisible(true) last of all (best if done by method that created this JFrame
	}
	public static void loginPanel(){
		JPanel panel = new JPanel();
		JFrame login = new JFrame();
		login.setSize(330,160);
		panel.setLayout(null);
		// login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.add(panel);
		JLabel user = new JLabel("User:");
		user.setBounds(10,20,80,25);

		JLabel scene = new JLabel("Scenario:");
		scene.setBounds(10,50,80,25);

		_userTxt = new JTextField(20);
		_userTxt.setBounds(100,20,165,25);

		String scenes[] = new String[24];
		for(int i = 0; i < 24; i++){
			scenes[i] = String.valueOf(i);
		}
		_sceneNum = new JComboBox(scenes);
		_sceneNum.setBounds(100,50,165,25);
		_sceneNum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				

			}

		});
	

		Quit = new JButton("quit");
		Quit.setBounds(10,80,120,30);
		Quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				_win2 = new MyFrame(image);
//				_win2.setTitle("Ex2 - OOP: (NONE trivial Solution)");
//				_win.setSize(1000, 700);
//				_win2.update(_ar);
//				_win2.show();
				Ex2.run();
			}

		});


		_loginButton = new JButton("Login");
		_loginButton.setBounds(170,80,120,30);
		_loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
         });


		panel.add(user);
		panel.add(scene);
		panel.add(_userTxt);
		panel.add(_sceneNum);
		panel.add(Quit);
		panel.add(_loginButton);
		login.setVisible(true);
	}




	public static void main(String args[])
	{
		/**
		 * You really need to get in the habit of creating GUI objects in the following way, as recommended by Oracle
		 * @see http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
		 */
		// 
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				//				File imageFile = MyFileChooser.chooseFile("Image Files (png & jpg)", "png", "jpg");
				if (folderInput != null) {


					BackgroundImageJFrame frame = new BackgroundImageJFrame(folderInput);
					frame.setVisible(true); // call setVisible(true) last of all
				}
			}
		});
	}

}



