package command;

import java.io.Serializable;

import drawing.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command, Serializable{

	private DrawingModel model;
	private Shape shape;
	private int index;
	
	public BringToFrontCmd(DrawingModel model,Shape shape) {
		this.model=model;
		this.shape=shape;
		this.index=model.getShapes().indexOf(shape);
	}
	
	@Override
	public void execute() {
		model.remove(shape);
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.getShapes().add(index, shape);	
	}
	
	@Override
	public String toString() {
		return "BringToFront:" + shape.toString();
	}

}
