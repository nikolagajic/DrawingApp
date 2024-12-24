package mvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import drawing.Shape;

public class DrawingModel implements Serializable{
	
	private ArrayList <Shape> shapes = new ArrayList<Shape>();
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void add(Shape p) {
		shapes.add(p);
	}
	
	public void remove(Shape p) {
		p.setSelected(false);
		shapes.remove(p);
	}
	
	public Shape get(int index) {
		return shapes.get(index);
	}
}
