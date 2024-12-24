package command;

import java.io.Serializable;

import drawing.Shape;

public class SelectShapeCmd implements Command, Serializable{
	
	private  Shape s;
	
	public SelectShapeCmd(Shape s) {
		this.s=s;
	}

	@Override
	public void execute() {
		s.setSelected(true);
		
	}

	@Override
	public void unexecute() {
		s.setSelected(false);
		
	}
	
	public String toString() {
		return "Selected:" + s.toString();
	}

}
