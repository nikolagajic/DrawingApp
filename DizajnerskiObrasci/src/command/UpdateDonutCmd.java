package command;

import java.io.Serializable;

import drawing.Donut;

public class UpdateDonutCmd implements Command, Serializable{

	private Donut donut;
	private Donut newDonut;
	private Donut temp = new Donut();
	
	public UpdateDonutCmd(Donut donut, Donut newDonut) {
		this.donut = donut;
		this.newDonut = newDonut;
	}
	
	@Override
	public void execute() {
		temp.setCenter(donut.getCenter());
		temp.setRadius(donut.getRadius());
		temp.setInnerRadius(donut.getInnerRadius());
		temp.setColor(donut.getColor());
		temp.setInnerColor(donut.getInnerColor());
		
		donut.setCenter(newDonut.getCenter());
		donut.setRadius(newDonut.getRadius());
		donut.setInnerRadius(newDonut.getInnerRadius());
		donut.setColor(newDonut.getColor());
		donut.setInnerColor(newDonut.getInnerColor());
	}

	@Override
	public void unexecute() {
		donut.setCenter(temp.getCenter());
		donut.setRadius(temp.getRadius());
		donut.setInnerRadius(temp.getInnerRadius());
		donut.setColor(temp.getColor());
		donut.setInnerColor(temp.getInnerColor());
	}
	
	public String toString() {
		return "Updated:"+ temp.toString()+" --> "+newDonut.toString();
	}

}
