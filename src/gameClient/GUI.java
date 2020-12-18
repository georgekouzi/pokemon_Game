package gameClient;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

/**
 * This class represents a very simple GUI class to present a
 * pokemon game on a graph .
 *  @author George kouzy and Dolev Saadia.
 *
 */

public class GUI extends JFrame{


	private Arena _ar;
	private gameClient.util.Range2Range _w2f;

	private BufferedImage map;
	private BufferedImage balbazurimg;
	private BufferedImage charmanderimg;
	private BufferedImage squiltelimg;
	private BufferedImage hash;
	private static File folderInput = new File("src\\images\\pokemonBattleFieldTesting.png");
	private static File balbazur = new File("src\\images\\bulbasaurFront.png");
	private static File charmander = new File("src\\images\\charmanderFront.png");
	private static File squiltel = new File("src\\images\\squirtleFront.png");
	private static File has = new File("src\\images\\pokeballImage.png");
	protected JPanel mainWindow;
	private static game_service game;
	
	/**
	 *constactur crate the  game panel init all images pokemon and egent  from file include the img  and  paint the garph by drawGraph fiction
	 *
	
	 */
	public GUI(game_service game)
	{
		try {
			map = ImageIO.read(folderInput);
			balbazurimg= ImageIO.read(balbazur);
			charmanderimg= ImageIO.read(charmander);
			squiltelimg= ImageIO.read(squiltel);
			hash= ImageIO.read(has);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		this.game=game;
		_ar=new Arena();
		
		mainWindow=new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(map, 0, 0, null);
				drawGraph(g);
			}
		};
		
		add(mainWindow);
		setTitle("Pokemon");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application

	}

	/**
	 *update the arena and update all new location on the graph;
	 *
	 */
	
	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}

	/**
	 * update all new location on the graph;
	 *
	 */
	
	public void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g =_ar.getGraph(); 
		_w2f = Arena.w2f(g,frame);
	}
	
	/**
	 *paint every frame  the pokemon and anants by new location on the graph;
	 *
	 */
	
	public void paint(Graphics g) {
		super.paint(g); 
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0));

		drawPokemons(g2);
		drawTimer(g2);
		drawAgants(g2);
		drawScore(g2);
		g.dispose();
		g2.dispose();
	}


	/**
	 *paint every frame  the score all the agants do every frame in the game;
	 *
	 */
	private void drawScore(Graphics g){
		List<CL_Agent> agents = _ar.getAgents();
		double score = 0;
		if(agents!=null) {
		for(CL_Agent agent : agents){
			score += agent.getValue();
		}
		g.drawString("Score: "+String.valueOf(score), this.getWidth()-200, 70);
		}
		}

	/**
	 *put pokemon image by location on the graph;
	 *
	 */

	private  void drawPokemons(Graphics g) {
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
			Iterator<CL_Pokemon> itr = fs.iterator();

			while(itr.hasNext()) {
				CL_Pokemon f = itr.next();
				Point3D c = f.getLocation();
				geo_location fp = this._w2f.world2frame(c);

				if(fp!=null) {
					if (f.getType()==1) {
						g.drawImage(charmanderimg,(int)fp.x()-11, (int)fp.y()-121,120, 120, null);
					}
					else if (f.getType()==2) {
						g.drawImage(squiltelimg,(int)fp.x()-11, (int)fp.y()-121,120, 120, null);
					}
					else {
						g.drawImage(balbazurimg,(int)fp.x()-11, (int)fp.y()-121,120, 120, null);
					}
					
				}
			}
		}
	}
	

	/**
	 *put agant image by location on the graph;
	 *
	 */
	
	private void drawAgants(Graphics g) {
		List<CL_Agent> rs = _ar.getAgents();
		Graphics2D g2d = (Graphics2D) g.create();
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
//			String[] strs = rs.get(i).toString().split("(?<=\\})(?=\\{)");
//			g2d.drawString(""+strs[i],(int)c.x(), (int)c.y()+50);
			i++;
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);
				g2d.drawImage(hash,(int)fp.x()-11, (int)fp.y()-101,  40, 40, null);
			}
		}

	}
	
	/**
	 *paint the edge between 2 verticles and pain the wighet
	 *
	 */
	
	private void drawEdge(edge_data e, Graphics g) {
		 
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);

		g.setColor(Color.BLACK);
		g.drawLine((int)s0.x()-10, (int)s0.y()-100, (int)d0.x()-10, (int)d0.y()-100);
		g.drawLine((int)s0.x()-11, (int)s0.y()-101, (int)d0.x()-11, (int)d0.y()-101);
		g.drawLine((int)s0.x()-12, (int)s0.y()-102, (int)d0.x()-12, (int)d0.y()-102);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));


	}

	/**
	 *paint the graph the verticles and the edges
	 *
	 */
	
	private void drawGraph(Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.blue);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
		}
	}
	
	/**
	 *paint every frame  the time you have for end game;
	 *
	 */

	private void drawTimer(Graphics g){
		g.setFont(new Font("Arial",Font.CENTER_BASELINE,36));
		int sec = (int) (game.timeToEnd()/1000);
		int min = (int) (game.timeToEnd()/60000);
		String time = min+":"+sec;
		g.drawString("Time to end: "+time,20,70);
	}

	/** 
	 * paint the verticles when he paint the graph and paint the key of each verticles
	 *
	 */
	
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.setColor(Color.RED);
		g.fillOval((int)fp.x()-10, (int)fp.y()-110, r+10, r+10);
		g.setColor(Color.BLACK);
		g.drawString(" "+n.getKey(), (int)fp.x(), (int)fp.y()-105);
	}

}
