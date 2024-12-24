package command;

import java.io.Serializable;

import drawing.Rectangle;

public class UpdateRectangleCmd implements Command, Serializable{

	private Rectangle rectangle;
	private Rectangle newRectangle;
	private Rectangle temp = new Rectangle();
	
	public UpdateRectangleCmd(Rectangle rectangle, Rectangle newRectangle) {
		this.rectangle = rectangle;
		this.newRectangle = newRectangle;
	}
	
	
	@Override
	public void execute() {
		temp.setUpperLeftPoint(rectangle.getUpperLeftPoint());
		temp.setHeight(rectangle.getHeight());
		temp.setWidth(rectangle.getWidth());
		temp.setColor(rectangle.getColor());
		temp.setInnerColor(rectangle.getInnerColor());
		
		rectangle.setUpperLeftPoint(newRectangle.getUpperLeftPoint());
		rectangle.setHeight(newRectangle.getHeight());
		rectangle.setWidth(newRectangle.getWidth());
		rectangle.setColor(newRectangle.getColor());
		rectangle.setInnerColor(newRectangle.getInnerColor());
	}

	@Override
	public void unexecute() {
		rectangle.setUpperLeftPoint(temp.getUpperLeftPoint());
		rectangle.setHeight(temp.getHeight());
		rectangle.setWidth(temp.getWidth());
		rectangle.setColor(temp.getColor());
		rectangle.setInnerColor(temp.getInnerColor());
	}
	
	public String toString() {
		return "Updated:"+ temp.toString()+" --> "+newRectangle.toString();
	}

}
