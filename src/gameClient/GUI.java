package gameClient;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;

import java.awt.Toolkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

public class GUI extends JFrame{

	private static JPanel background;
	private int _ind;
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
	public static GraphicsDevice device = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getScreenDevices()[0];


	public GUI()
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
		_ind = 0;
		_ar=new Arena();
		setLayout(new BorderLayout());
		mainWindow=new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				
				g.drawImage(map, 0, 0, null);
//				drawInfo(g);
				drawGraph(g);
				

				g.drawImage(map, 0, 0, null);
				drawGraph(g);

			}
		};
		add(mainWindow);
		mainWindow.setLayout(new FlowLayout());
		mainWindow.setVisible(true);
		setVisible(true); // call setVisible(true) last of all (best if done by method that created this JFrame
		device.setFullScreenWindow(this);
		setTitle("Pokemon");

		pack(); // automatically size the window to fit its components
		setLocationRelativeTo(null); // center this window on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // when this window is closed, exit this application

	}

	GUI(String a) {
		super(a);
	}
	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}

	public Dimension getPreferredSize() {
		return map == null ? new Dimension(200, 200) : new Dimension(map.getWidth(), map.getHeight());
	}

	public void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g =_ar.getGraph(); 
		_w2f = Arena.w2f(g,frame);
	}
	
	public void paint(Graphics g) {
	
	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0));
	    super.paint(g2); 

		drawPokemons(g);


		drawAgants(g);
	
	    g.dispose();
	    g2.dispose();
	}


//	private void drawInfo(Graphics g) {
//		List<String> str = _ar.get_info();
//		String dt = "none";
//		for(int i=0;i<str.size();i++) {
//			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
//		}
//	}

//		drawGraph(g);

//		drawInfo(g);
	

	private void drawInfo(Graphics g) {
		List<String> str = _ar.get_info();
		String dt = "none";
		for(int i=0;i<str.size();i++) {
			g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
		}
	}

//
//	private void drawPokemons(Graphics g) {
//		List<CL_Pokemon> fs = _ar.getPokemons();
//		if(fs!=null) {
//			Iterator<CL_Pokemon> itr = fs.iterator();
//
//			while(itr.hasNext()) {
//
//				CL_Pokemon f = itr.next();
//				Point3D c = f.getLocation();
//				super.paintComponents(g);
//				Graphics2D g2d = (Graphics2D) g.create();
//				if(c!=null) {
//					geo_location fp = this._w2f.world2frame(c);
//					super.paintComponents(g);
//					if (balbazurimg != null) {
//
//						g2d.drawImage(balbazurimg,(int)fp.x(), (int)fp.y(), this);
//
//						g2d.dispose();
//
//					}
//					else {
//						g2d.drawImage(charmanderimg,(int)c.x(), (int)c.y(), this);
//					}
//
//				}
//			}
//		}
//	}
	 private void drawPokemons(Graphics g) {
	        List<CL_Pokemon> fs = _ar.getPokemons();
	        if(fs!=null) {
	            Iterator<CL_Pokemon> itr = fs.iterator();
	            while(itr.hasNext()) {
	                CL_Pokemon f = itr.next();
	                Point3D c = f.getLocation();
	                int r=10;
	                g.setColor(Color.green);
	                if(f.getType()<0) {g.setColor(Color.orange);}
	                if(c!=null) {
	                    geo_location fp = this._w2f.world2frame(c);
	                    g.drawImage(charmanderimg,(int)fp.x()-r, (int)fp.y()-r,100, 100, null);
	                }
	            }
	        }
	    }
	private void drawAgants(Graphics g) {
		List<CL_Agent> rs = _ar.getAgents();
		//	Iterator<OOP_Point3D> itr = rs.iterator();
		Graphics2D g2d = (Graphics2D) g.create();

		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			i++;
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);
				g2d.drawImage(hash,(int)fp.x(), (int)fp.y(),  80, 80, null);

				//				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
			}
		}
	
	}
	//	private void drawNode(node_data n, int r, Graphics g) {
	//		geo_location pos = n.getLocation();
	//		geo_location fp = this._w2f.world2frame(pos);
	//		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
	//		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	//	}
	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);


//		((Graphics2D)g).drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	

	g.setColor(Color.BLUE);

//	g.drawLine((int)s0.x()-100, (int)s0.y()-100, (int)d0.x()-100, (int)d0.y()-100);

	g.drawLine((int)s0.x(), (int)s0.y()-100, (int)d0.x(), (int)d0.y()-100);

	}

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
    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-100);
    }
//    private void drawTimer(Graphics g){
//        g.setFont(new Font("Arial",Font.BOLD,36));
//        int sec = (int) (_game.timeToEnd()/1000);
//        int min = (int) (_game.timeToEnd()/60000);
//        String time = min+":"+sec;
//        g.drawString(time,20,70);
//    }

	public static void main(String args[])
	{

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				if (folderInput != null) {
					GUI frame = new GUI();
					frame.setVisible(true); // call setVisible(true) last of all

				}
			}
		});
	}

}