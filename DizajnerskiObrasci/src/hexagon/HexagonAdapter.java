package hexagon;

import java.awt.Graphics;
import drawing.SurfaceShape;
import java.awt.Color;
import java.awt.Graphics;

public class HexagonAdapter extends SurfaceShape{
	
	private Hexagon hexagon;
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public HexagonAdapter(int x, int y, int r) {
		this.hexagon = new Hexagon(x, y, r);
	}
	
	public HexagonAdapter(int x, int y, int r,boolean selected, Color color,Color innerColor) {
		this.hexagon = new Hexagon(x, y, r);
		this.setSelected(selected);
		this.setInnerColor(innerColor);
		this.setColor(color);
		this.hexagon.setAreaColor(innerColor);
		this.hexagon.setBorderColor(color);
		this.hexagon.setSelected(selected);
	}
	

	public HexagonAdapter() {
		this.hexagon = new Hexagon(-1,-1,-1);
	}



	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			return this.getR() - ((HexagonAdapter)o).getR();
		}
		return 0;
	}

	@Override
	public double area() {
		return 0;
	}

	@Override
	public void fill(Graphics g) {
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
	}

	@Override
	public boolean contains(int x, int y) {		
		return hexagon.doesContain(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof HexagonAdapter) {
			return (this.getX() == ((HexagonAdapter)o).getX() && this.getY() == ((HexagonAdapter)o).getY() && this.getR() == ((HexagonAdapter)o).getR());
		}
		else
		return false;
	}
	
	public int getX() {
		return hexagon.getX();
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public int getR() {
		return hexagon.getR();
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public void setY(int y) {
		hexagon.setY(y);
	}
	
	public void setR(int r) {
		hexagon.setR(r);
	}
	
	@Override
	public Color getColor() {
		return super.getColor();
	}
	
	@Override
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
		super.setColor(color);
	}
	
	@Override
	public Color getInnerColor() {
		return super.getInnerColor();
	}
	
	@Override
	public void setInnerColor(Color innerColor) {
		hexagon.setAreaColor(innerColor);
		super.setInnerColor(innerColor);
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		hexagon.setSelected(selected);
	}
	
	public String toString() {
		return "Hexagon:center:(" + this.getX() + "," + this.getY() + "),radius:" + this.getR();
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

}
