package command;

import java.io.Serializable;
import java.util.Collections;

import drawing.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command, Serializable{

	private DrawingModel model;
	private Shape shape;
	
	public ToBackCmd(DrawingModel model, Shape shape) {
		this.model=model;
		this.shape=shape;
	}

	@Override
	public void execute() {
		int index = model.getShapes().indexOf(shape);
		Collections.swap(model.getShapes(), index, index-1);
	}

	@Override
	public void unexecute() {
		int index=model.getShapes().indexOf(shape);
		Collections.swap(model.getShapes(), index, index+1);
	}

	@Override
	public String toString() {
		return "ToBack:" + shape.toString();
	}
}
