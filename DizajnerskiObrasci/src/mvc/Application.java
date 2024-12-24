package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		
		DrawingModel model = new DrawingModel();
		FrameDrawing frame = new FrameDrawing();
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		
		frame.setSize(1000,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
