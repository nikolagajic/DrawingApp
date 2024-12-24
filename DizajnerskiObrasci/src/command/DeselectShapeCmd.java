package command;

import java.io.Serializable;

import drawing.Shape;

public class DeselectShapeCmd implements Command, Serializable{
	
private  Shape s;
	
	public DeselectShapeCmd(Shape s) {
		this.s=s;
	}

	@Override
	public void execute() {
		s.setSelected(false);
		
	}

	@Override
	public void unexecute() {
		s.setSelected(true);
		
	}
	
	public String toString() {
		return "Deselected:" + s.toString();
	}

}
