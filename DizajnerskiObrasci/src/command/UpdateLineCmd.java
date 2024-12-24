package command;

import java.io.Serializable;

import drawing.Line;

public class UpdateLineCmd implements Command, Serializable{
	
	Line line;
	Line newLine;
	Line temp = new Line();
	
	public UpdateLineCmd(Line line, Line newLine) {
		this.line = line;
		this.newLine = newLine;
	}

	@Override
	public void execute() {
		temp.setStartPoint(line.getStartPoint());
		temp.setEndPoint(line.getEndPoint());
		temp.setColor(line.getColor());
		
		line.setStartPoint(newLine.getStartPoint());
		line.setEndPoint(newLine.getEndPoint());
		line.setColor(newLine.getColor());
		
	}

	@Override
	public void unexecute() {
		line.setStartPoint(temp.getStartPoint());
		line.setEndPoint(temp.getEndPoint());
		line.setColor(temp.getColor());
	}
	
	public String toString() {
		return "Updated:"+ temp.toString()+" --> "+newLine.toString();
	}

}
