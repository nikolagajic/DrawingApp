package mvc;

import java.awt.Graphics;
import java.util.Iterator;

import drawing.Shape;

import javax.swing.JPanel;

public class PnlDrawing extends JPanel {
	
	private DrawingModel model = new DrawingModel();
	
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}
}
