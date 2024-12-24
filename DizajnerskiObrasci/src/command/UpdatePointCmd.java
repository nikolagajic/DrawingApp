package command;

import java.io.Serializable;

import drawing.Point;

public class UpdatePointCmd implements Command, Serializable{

	Point point;
	Point newPoint;
	Point temp = new Point();
	
	public UpdatePointCmd(Point point, Point newPoint) {
		this.point = point;
		this.newPoint = newPoint;
	}
	
	@Override
	public void execute() {
		temp.setX(point.getX());
		temp.setY(point.getY());
		temp.setColor(point.getColor());
		
		point.setX(newPoint.getX());
		point.setY(newPoint.getY());
		point.setColor(newPoint.getColor());
		
	}

	@Override
	public void unexecute() {
		point.setX(temp.getX());
		point.setY(temp.getY());
		point.setColor(temp.getColor());
	}
	
	public String toString() {
		return "Updated:"+ temp.toString()+" --> "+newPoint.toString();
	}

}
