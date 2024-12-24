package observer;

import java.io.Serializable;

import drawing.Shape;
import mvc.DrawingModel;
import mvc.FrameDrawing;

public class ShapeObserver implements Observer, Serializable {
	
	private FrameDrawing frame;
	private DrawingModel model;
	
	public ShapeObserver(FrameDrawing frame,DrawingModel model) {
		this.frame=frame;
		this.model=model;
	}
	 
	public int getSelectedShapes() {
		int counter=0;
		for(Shape s : model.getShapes()) {
			if(s.isSelected()) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public void update() {
		if (getSelectedShapes() == 0) {
			this.frame.getBtnModify().setEnabled(false);
			this.frame.getBtnDelete().setEnabled(false);
			this.frame.getBtnBringToBack().setEnabled(false);
			this.frame.getBtnBringToFront().setEnabled(false);
			this.frame.getBtnToBack().setEnabled(false);
			this.frame.getBtnToFront().setEnabled(false);
		} else if (getSelectedShapes() == 1) {
			this.frame.getBtnModify().setEnabled(true);
			this.frame.getBtnDelete().setEnabled(true);
			this.frame.getBtnBringToBack().setEnabled(true);
			this.frame.getBtnBringToFront().setEnabled(true);
			this.frame.getBtnToBack().setEnabled(true);
			this.frame.getBtnToFront().setEnabled(true);
		} else {
			this.frame.getBtnModify().setEnabled(false);
			this.frame.getBtnDelete().setEnabled(true);
			this.frame.getBtnBringToBack().setEnabled(false);
			this.frame.getBtnBringToFront().setEnabled(false);
			this.frame.getBtnToBack().setEnabled(false);
			this.frame.getBtnToFront().setEnabled(false);
		}
	}
}
