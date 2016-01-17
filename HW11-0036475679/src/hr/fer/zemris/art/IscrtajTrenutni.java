package hr.fer.zemris.art;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class IscrtajTrenutni extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;

	public IscrtajTrenutni(String path) {
		this.path = path;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			g.drawImage(ImageIO.read(new File(path)), 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
