package gameClient;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MyMusic implements Runnable{

	String pa;
	public MyMusic(String p) {
		pa=p;
	}
	
	@Override
	public void run() 
	{
		play();
	}
		
	   
	
	
	public void play()
	    {
	        try
	        {
	             FileInputStream fis = new FileInputStream(pa);
	             Player playMP3 = new Player(fis);
	             playMP3.play();
	        }  
	        catch(Exception e)
	        {
	        	System.out.println(e);
	        }
	    }

		



}
