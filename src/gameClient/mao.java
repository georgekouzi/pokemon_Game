package gameClient;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class mao {

	public static void main(String[] args) {
		new mao();
	}

	public mao() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}

				MapPane pane = new MapPane();


				JFrame frame = new JFrame("Testing");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.add(pane);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public class MapPane extends JPanel {

		private BufferedImage img;
		private File folderInput = new File("C:\\Users\\user\\eclipse-workspace\\pokemon_Game\\src\\images\\pokemonBattleFieldTesting.png");
		
		public MapPane() {

			try {
				img = ImageIO.read(folderInput);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img != null) {
				Graphics2D g2d = (Graphics2D) g.create();

				int x = (getWidth() - img.getWidth()) / 2;
				int y = (getHeight() - img.getHeight()) / 2;

				g2d.drawImage(img, x, y, this);
				g2d.dispose();
			}
		}

	}

}