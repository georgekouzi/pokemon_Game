package gameClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;



public class BackgroundImageJFrame extends JFrame
{
	// instance variables
	// instance variables should really be private to keep code in other classes from messing them up.
	private JButton b1;
	private JLabel l1;
	static File folderInput = new File("src\\images\\winningImage.png");
	private static int _scenario = 0;
	private static int _id = -1;
	private static JComboBox _sceneNum;
	private static JButton Quit;
	private static JButton _loginButton;
	private static JTextField _userTxt;
	private static JLabel background;
	private static JLabel user;
	private static JLabel scene;
	private static JPanel panel;
	private static JFrame login;
	private final static AtomicBoolean run = new AtomicBoolean(false);


	private static File folderInpu2t = new File("src\\images\\bulbasaurFront.png");


	public BackgroundImageJFrame(File imageFile)
	{
		
		background=new JLabel(new ImageIcon(imageFile.getAbsolutePath()));
		add(background);
		background.setLayout(new FlowLayout());
		l1=new JLabel("start a new game");
		b1=new JButton("start game");
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginPanel();				
			}
		});
		b1.setSize(300, 300);
		background.add(l1);
		background.add(b1);

		setTitle("Pokemon");
		pack(); // automatically size the window to fit its components
		setLocationRelativeTo(null); // center this window on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application
		setVisible(true); // call setVisible(true) last of all (best if done by method that created this JFrame
	}


	public static void loginPanel(){

		panel = new JPanel();
		login = new JFrame();
		login.setSize(330,160);
		panel.setLayout(null);
		// login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.add(panel);
		user = new JLabel("User:");
		user.setBounds(10,20,80,25);

		JLabel scene = new JLabel("Scenario:");
		scene.setBounds(10,50,80,25);

		_userTxt = new JTextField(20);
		_userTxt.setBounds(100,20,165,25);

		String scenes[] = new String[24];
		for(int i = 0; i < 24; i++){
			scenes[i] = String.valueOf(i);
		}

		_sceneNum.setBounds(100,50,165,25);
		_sceneNum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int scenario = _sceneNum.getSelectedIndex();
				_scenario = scenario;
				
			}


		});



		Quit = new JButton("quit");
		Quit.setBounds(10,80,120,30);
		Quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);


			}

		});


		_loginButton = new JButton("Login");
		_loginButton.setBounds(170,80,120,30);
//		_loginButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				long id = Integer.parseInt(_userTxt.getText());
//				if(id > 0){
//					
//					new Ex2(_scenario,id);
//				}
//				Thread client = new Thread(new Pokemon_Game());
//				Thread Music = new Thread(new MyMusic("Pokemon.mp3"));
//				Music.start();
//				client.start();
//				
//				if(!client.isAlive()) 
//					run.set(false);	
//						 
//			}
//
//		});

		panel.add(user);
		panel.add(scene);
		panel.add(_userTxt);
		panel.add(_sceneNum);
		panel.add(Quit);
		panel.add(_loginButton);
		login.setVisible(true);
		login.setTitle("login");
		login.setLocationRelativeTo(null); // center this window on the screen
		login.setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application

	}
}



