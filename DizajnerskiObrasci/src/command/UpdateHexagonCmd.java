package command;

import java.io.Serializable;

import hexagon.HexagonAdapter;

public class UpdateHexagonCmd implements Command, Serializable{

	private HexagonAdapter hexagon;
	private HexagonAdapter newHexagon;
	private HexagonAdapter temp = new HexagonAdapter();
	
	public UpdateHexagonCmd(HexagonAdapter hexagon, HexagonAdapter newHexagon) {
		this.hexagon = hexagon;
		this.newHexagon = newHexagon;
	}
	
	@Override
	public void execute() {
		temp.setX(hexagon.getX());
		temp.setY(hexagon.getY());
		temp.setR(hexagon.getR());
		temp.setColor(hexagon.getColor());
		temp.setInnerColor(hexagon.getInnerColor());
		
		hexagon.setX(newHexagon.getX());
		hexagon.setY(newHexagon.getY());
		hexagon.setR(newHexagon.getR());
		hexagon.setColor(newHexagon.getColor());
		hexagon.setInnerColor(newHexagon.getInnerColor());
	}

	@Override
	public void unexecute() {
		hexagon.setX(temp.getX());
		hexagon.setY(temp.getY());
		hexagon.setR(temp.getR());
		hexagon.setColor(temp.getColor());
		hexagon.setInnerColor(temp.getInnerColor());
	}
	
	public String toString() {
		return "Updated:"+ temp.toString()+" --> "+newHexagon.toString();
	}

}
