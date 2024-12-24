package command;

import java.io.Serializable;

import drawing.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command, Serializable{
	
	private DrawingModel model;
	private Shape shape;
	
	public RemoveShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.remove(shape);
		
	}

	@Override
	public void unexecute() {
		model.add(shape);
		
	}
	
	public String toString() {
		return "Removed:" + shape.toString();
	}
}
