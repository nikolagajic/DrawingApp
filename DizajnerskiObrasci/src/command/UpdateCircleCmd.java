package command;

import java.io.Serializable;

import drawing.Circle;

public class UpdateCircleCmd implements Command, Serializable{

	private Circle circle;
	private Circle newCircle;
	private Circle temp = new Circle();
	
	public UpdateCircleCmd(Circle circle, Circle newCircle) {
		this.circle = circle;
		this.newCircle = newCircle;
	}
	
	@Override
	public void execute() {
		temp.setRadius(circle.getRadius());
		temp.setCenter(circle.getCenter());
		temp.setColor(circle.getColor());
		temp.setInnerColor(circle.getInnerColor());
		
		circle.setRadius(newCircle.getRadius());
		circle.setCenter(newCircle.getCenter());
		circle.setColor(newCircle.getColor());
		circle.setInnerColor(newCircle.getInnerColor());
	}

	@Override
	public void unexecute() {
		circle.setRadius(temp.getRadius());
		circle.setCenter(temp.getCenter());
		circle.setColor(temp.getColor());
		circle.setInnerColor(temp.getInnerColor());	
	}
	
	public String toString() {
		return "Updated:"+ temp.toString()+" --> "+newCircle.toString();
	}


}
