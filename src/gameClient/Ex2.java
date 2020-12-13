package gameClient;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * This class allows you to run the Pokemon game by running 
 * several classes at the same time as a Pokemon_Game class,MyMusic class and more.
 * @author George kouzy and Dolev Saadia.
 */
public class Ex2 {
	//When the game is over we want all the actions
	//That facts in parallel will also over.
	private final static AtomicBoolean run = new AtomicBoolean(false);
	static File folderInput = new File("C:\\Users\\user\\eclipse-workspace\\pokemon_Game\\src\\images\\winningImage.png");

	public Ex2(){
		BackgroundImageJFrame j = new BackgroundImageJFrame(folderInput);
		Thread client = new Thread(new Pokemon_Game());
		Thread Music = new Thread(new MyMusic("Pokemon.mp3"));
		Music.start();
	
		client.start();
	
		
		if(!client.isAlive()) 
			run.set(false);	
		
	}
	public Ex2(int scenario){
		
		Thread client = new Thread(new Pokemon_Game());
		Thread Music = new Thread(new MyMusic("Pokemon.mp3"));
		Music.start();
		client.start();
		
		if(!client.isAlive()) 
			run.set(false);	
	}
	

	public static void main(String args[])
	{
		Ex2 a = new Ex2();
	}
}