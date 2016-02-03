package hr.fer.zemris.optjava.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hr.fer.zemris.optjava.gp.AntMap;
import hr.fer.zemris.optjava.gp.Eater;
import hr.fer.zemris.optjava.gp.Eater.PointDirection;

public class EaterGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Eater eater;
	private AntMap map;
	private int index;
	
	public EaterGUI(AntMap map, Eater e) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(map.getHeight()+1, map.getWidth()));
		JButton but = new JButton(new AbstractAction("Sljedeći korak") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				PointDirection pd = eater.getVisited().get(EaterGUI.this.index);
			}
		});
		
		for (int i=0; i<map.getHeight(); i++) {
			for (int j=0; j<map.getWidth(); j++) 
			add(new Arrow(), i, j);
		}
		
		this.eater = e;
		this.map = map;
		this.map.restore();
		index = 0;
	}
	
	
	
	class Arrow extends JComponent {
		private final int ARR_SIZE = 4;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private int direction;
		
		public Arrow() {
			direction = Eater.RIGHT;
		}
		
		public void setDirection(int direction) {
			this.direction = direction;
		}
		
		public int getDirection() {
			return direction;
		}
		
		void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
            Graphics2D g = (Graphics2D) g1.create();

            double dx = x2 - x1, dy = y2 - y1;
            double angle = Math.atan2(dy, dx);
            int len = (int) Math.sqrt(dx*dx + dy*dy);
            AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
            at.concatenate(AffineTransform.getRotateInstance(angle));
            g.transform(at);

            g.drawLine(0, 0, len, 0);
            g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                          new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        }
		
		@Override
		protected void paintComponent(Graphics g) {
			int x1 = getX();
			int y1 = getY();
			int x2 = x1 + getWidth();
			int y2 = y1 + getHeight();
			drawArrow(g, x1, y1, x2, y2);
			super.paintComponent(g);
		}
	}

}
