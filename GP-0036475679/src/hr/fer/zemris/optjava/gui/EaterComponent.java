package hr.fer.zemris.optjava.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.optjava.gp.Eater;

public class EaterComponent extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label;

	public EaterComponent() {
		label = new JLabel("");
		label.setEnabled(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(label);

	}

	public void setDirection(int direction) {
		switch (direction) {
		case Eater.UP:
			label.setText("^");
			break;
		case Eater.DOWN:
			label.setText("v");
			break;
		case Eater.LEFT:
			label.setText("<");
			break;
		case Eater.RIGHT:
			label.setText(">");
			break;
		default:
			break;
		}
		setBackground(Color.RED);
	}
	
	public void clear() {
		label.setText("");
		setBackground(Color.gray);
	}
	
	public void setFood() {
		label.setText("¤");
	}

}
