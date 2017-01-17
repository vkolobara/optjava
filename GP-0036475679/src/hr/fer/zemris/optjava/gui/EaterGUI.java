package hr.fer.zemris.optjava.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private Point current;
	private EaterComponent[][] comps;
	private AntMap map;
	private int step;
	
	public EaterGUI(AntMap map, Eater e) {
		setSize(600,600);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(map.getHeight(), map.getWidth()));
		setLayout(new BorderLayout());
		comps = new EaterComponent[map.getHeight()][map.getWidth()];
		comps[0][0] = new EaterComponent();
		comps[0][0].setDirection(Eater.RIGHT);
		panel.add(comps[0][0]);

		current = new Point(0,0);
		int size = e.getVisited().size();
		JLabel label = new JLabel("Koraci: 0/" + size);
		JButton but = new JButton(new AbstractAction("Next") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				PointDirection pd = eater.getVisited().get(EaterGUI.this.step);
				comps[current.y][current.x].clear();
				comps[pd.getPoint().y][pd.getPoint().x].setDirection(pd.getDirection());
				current = pd.getPoint();
				EaterGUI.this.step++;
				label.setText("Koraci: "+step+"/" + size);
			}
		});
		
		for (int i=0; i<map.getHeight(); i++) {
			for (int j=0; j<map.getWidth(); j++) {
				if (i==0 && j==0) continue;
				comps[i][j] = new EaterComponent();
 				if (map.getFoodMap()[i][j]) comps[i][j].setFood();
				panel.add(comps[i][j]);
			}
			
		}
		add(panel, BorderLayout.CENTER);
		panel = new JPanel();
		panel.add(but);
		panel.add(label);
		add(panel, BorderLayout.SOUTH);
		
		this.eater = e;
		this.map = map;
		this.map.restore();
		step = 0;
	}
	
	

}
