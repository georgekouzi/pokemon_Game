package gameClient;
import java.util.concurrent.atomic.AtomicBoolean;
public class Ex2  {
	private final static AtomicBoolean run = new AtomicBoolean(false);
	public static void main(String[] args) {
		Thread client = new Thread(new Pokemon_Game());
		Thread Music = new Thread(new MyMusic("Pokemon.mp3"));
		Music.start();
		client.start();
		if(!client.isAlive()) 
			run.set(false);	
	}



}