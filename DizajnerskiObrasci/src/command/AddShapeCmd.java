package command;

import java.io.Serializable;

import drawing.Shape;
import mvc.DrawingModel;

public class AddShapeCmd implements Command, Serializable{
	
	
	private DrawingModel model;
	private Shape shape;
	
	public AddShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		
	}
	
	public String toString() {
		return "Added:" + shape.toString();
	}

}
