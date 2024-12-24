package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeselectShapeCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import drawing.Circle;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import drawing.Donut;
import drawing.Line;
import drawing.Point;
import drawing.Rectangle;
import drawing.Shape;
import hexagon.HexagonAdapter;
import observer.ShapeObserver;
import saving.DrawingSaving;
import saving.LogSaving;
import saving.SavingManager;


public class DrawingController implements Serializable{

	private DrawingModel drawingModel;
	private FrameDrawing frameDrawing;
	
	private Shape lastSelectedShape;
	private List<Shape> selectedShapes = new ArrayList<Shape>();
	private Point startPoint;
	private Stack<Command> undoRedoStack = new Stack<>();
	private int stackPointer = -1;
	private BufferedReader bufferedReader;
	private String nextLine;
	
	public DrawingController (DrawingModel model, FrameDrawing frame) {
		this.drawingModel = model;
		this.frameDrawing = frame;
	}

	public void modify() {
		if (lastSelectedShape != null) {
			if (lastSelectedShape instanceof Point) {
				Point point = (Point) lastSelectedShape;
				DlgPoint dlg = new DlgPoint();
				dlg.modifyPoint(point);
				dlg.setVisible(true);
				Point newPoint = new Point();
				if (dlg.isOK) {
					
					newPoint.moveTo(dlg.getPoint().getX(),dlg.getPoint().getY());
					if (dlg.getCurrentColor() != null) {
						newPoint.setColor(dlg.getCurrentColor());
					} else {
						newPoint.setColor(frameDrawing.getCurrentColor());
					}
					UpdatePointCmd updatePoint = new UpdatePointCmd(point, newPoint);
					addCommand(updatePoint);
				}
				
			} else if (lastSelectedShape instanceof Line) {
				Line line = (Line) lastSelectedShape;
				DlgLine dlg = new DlgLine();
				dlg.modifyLine(line);
				dlg.setVisible(true);
				Line newLine = new Line();
				if (dlg.isOK) {
					newLine.moveTo(dlg.getLine().getStartPoint(), dlg.getLine().getEndPoint());
					if (dlg.getCurrentColor() != null) {
						newLine.setColor(dlg.getCurrentColor());
					} else {
						newLine.setColor(frameDrawing.getCurrentColor());
					}
					UpdateLineCmd updateLine = new UpdateLineCmd(line,newLine);
					addCommand(updateLine);
				}
				
			} else if (lastSelectedShape instanceof Rectangle) {
				Rectangle rec = (Rectangle) lastSelectedShape;
				DlgRectangle dlg = new DlgRectangle();
				dlg.modifyRectangle(rec);
				dlg.setTitle("Modify rectangle");
				dlg.setVisible(true);
				Rectangle newRec = new Rectangle();
				if (dlg.isOK) {
					Color color;
					Color innerColor;
					if (dlg.getCurrentColor() != null) {
						color = dlg.getCurrentColor();
					} else {
						color = frameDrawing.getCurrentColor();
					}
					if (dlg.getCurrentInnerColor() != null) {
						innerColor = dlg.getCurrentInnerColor();
					} else {
						innerColor = frameDrawing.getCurrentInnerColor();
					}
					newRec.setUpperLeftPoint(dlg.getRectangle().getUpperLeftPoint());
					newRec.setHeight(dlg.getRectangle().getHeight());
					newRec.setWidth(dlg.getRectangle().getWidth());
					newRec.setColor(color);
					newRec.setInnerColor(innerColor);
					UpdateRectangleCmd updateRec = new UpdateRectangleCmd(rec, newRec);
					addCommand(updateRec);
				}
				
			} else if (lastSelectedShape instanceof Circle && !(lastSelectedShape instanceof Donut)) {
				
				Circle circle = (Circle) lastSelectedShape;
				DlgCircle dlg = new DlgCircle();
				dlg.modifyCircle(circle);
				dlg.setTitle("Modify circle");
				dlg.setVisible(true);
				Circle newCircle = new Circle();
				if (dlg.isOK) {
					Color color;
					Color innerColor;
					if (dlg.getCurrentColor() != null) {
						color = dlg.getCurrentColor();
					} else {
						color = frameDrawing.getCurrentColor();
					}
					if (dlg.getCurrentInnerColor() != null) {
						innerColor = dlg.getCurrentInnerColor();
					} else {
						innerColor = frameDrawing.getCurrentInnerColor();
					}
					newCircle.setCenter(dlg.getCircle().getCenter());
					newCircle.setRadius(dlg.getCircle().getRadius());
					newCircle.setColor(color);
					newCircle.setInnerColor(innerColor);
					UpdateCircleCmd updateCircle = new UpdateCircleCmd(circle, newCircle);
					addCommand(updateCircle);
				}
				
			} else if (lastSelectedShape instanceof Donut) {
				Donut donut = (Donut) lastSelectedShape;
				DlgDonut dlg = new DlgDonut();
				dlg.modifyDonut(donut);
				dlg.setTitle("Modify donut");
				dlg.setVisible(true);
				Donut newDonut = new Donut();
				if (dlg.isOK) {
					Color color;
					Color innerColor;
					if (dlg.getCurrentColor() != null) {
						color = dlg.getCurrentColor();
					} else {
						color = frameDrawing.getCurrentColor();
					}
					if (dlg.getCurrentInnerColor() != null) {
						innerColor = dlg.getCurrentInnerColor();
					} else {
						innerColor = frameDrawing.getCurrentInnerColor();
					}
					newDonut.setCenter(dlg.getDonut().getCenter());
					newDonut.setRadius(dlg.getDonut().getRadius());
					newDonut.setInnerRadius(dlg.getDonut().getInnerRadius());
					newDonut.setColor(color);
					newDonut.setInnerColor(innerColor);
					UpdateDonutCmd updateDonut = new UpdateDonutCmd(donut, newDonut);
					addCommand(updateDonut);
				}
			} else if (lastSelectedShape instanceof HexagonAdapter) {
				HexagonAdapter hexagon = (HexagonAdapter) lastSelectedShape;
				DlgHexagon dlg = new DlgHexagon();
				dlg.modifyHexagon(hexagon);
				dlg.setTitle("Modify hexagon");
				dlg.setVisible(true);
				HexagonAdapter newHexagon = new HexagonAdapter();
				if (dlg.isOK) {
					Color color;
					Color innerColor;
					if (dlg.getCurrentColor() != null) {
						color = dlg.getCurrentColor();
					} else {
						color = frameDrawing.getCurrentColor();
					}
					if (dlg.getCurrentInnerColor() != null) {
						innerColor = dlg.getCurrentInnerColor();
					} else {
						innerColor = frameDrawing.getCurrentInnerColor();
					}
					newHexagon.setX(dlg.getHexagon().getX());
					newHexagon.setY(dlg.getHexagon().getY());
					newHexagon.setR(dlg.getHexagon().getR());
					newHexagon.setColor(color);
					newHexagon.setInnerColor(innerColor);
					UpdateHexagonCmd updateHexagon = new UpdateHexagonCmd(hexagon, newHexagon);
					addCommand(updateHexagon);
				}
			}
			frameDrawing.getView().repaint();
		} else { 
			JOptionPane.showMessageDialog(null, "You must select a shape to modify!", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	public void delete() {
		if (!selectedShapes.isEmpty()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Iterator<Shape> it = selectedShapes.iterator();
				while (it.hasNext()) {
					Shape nextShape = it.next();
					RemoveShapeCmd removeShape = new RemoveShapeCmd(drawingModel, nextShape);
					addCommand(removeShape);
				}
				selectedShapes.clear();
				frameDrawing.getView().repaint();
			}
		} else
			JOptionPane.showMessageDialog(null, "You must select a shape to delete!", "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	public void drawingMouseClicked(MouseEvent e) {
		Shape newShape = null;
		if(frameDrawing.getTglSelect().isSelected()) {
			lastSelectedShape = null;
			Iterator<Shape> it = drawingModel.getShapes().iterator();
			while (it.hasNext()) {
				Shape shape = it.next();
				if (shape.contains(e.getX(), e.getY())) {
					lastSelectedShape = shape;
				}
			}
			if (lastSelectedShape != null) {
				SelectShapeCmd selectShape = new SelectShapeCmd(lastSelectedShape);
				addCommand(selectShape);
				selectedShapes.add(lastSelectedShape);
			}
			if (lastSelectedShape == null) {
				Iterator<Shape> it2 = selectedShapes.iterator();
				while (it2.hasNext()) {
					Shape shape = it2.next();
					DeselectShapeCmd deselectShape = new DeselectShapeCmd(shape);
					addCommand(deselectShape);
				}
				selectedShapes.clear();
			}

		} else if (frameDrawing.getTglPoint().isSelected()) {
			newShape = new Point(e.getX(), e.getY(), false, frameDrawing.getCurrentColor());
			
		} else if (frameDrawing.getTglLine().isSelected()) {
			if (startPoint == null)
				startPoint = new Point(e.getX(),e.getY());
			else {
				newShape = new Line (startPoint, new Point(e.getX(),e.getY()), false, frameDrawing.getCurrentColor());
				startPoint = null;
			}
			
		} else if (frameDrawing.getTglRectangle().isSelected()) {
			DlgRectangle dlg = new DlgRectangle();
			dlg.setTitle("Add rectangle");
			dlg.addRectangle(new Rectangle(new Point(e.getX(),e.getY()),-1,-1,false,frameDrawing.getCurrentColor(), frameDrawing.getCurrentInnerColor()));
			dlg.setVisible(true);
			if (dlg.isOK) {
				Color color;
				Color innerColor;
				if (dlg.getCurrentColor() != null) {
					color = dlg.getCurrentColor();
				} else {
					color = frameDrawing.getCurrentColor();
				}
				if (dlg.getCurrentInnerColor() != null) {
					innerColor = dlg.getCurrentInnerColor();
				} else {
					innerColor = frameDrawing.getCurrentInnerColor();
				}
				newShape = new Rectangle(dlg.getRectangle().getUpperLeftPoint(), dlg.getRectangle().getHeight(), dlg.getRectangle().getWidth(),false,color,innerColor);
			}
			
		} else if (frameDrawing.getTglCircle().isSelected()) {
			DlgCircle dlg = new DlgCircle();
			dlg.setTitle("Add circle");
			dlg.addCircle(new Circle(new Point(e.getX(),e.getY()),-1,false,frameDrawing.getCurrentColor(), frameDrawing.getCurrentInnerColor()));
			dlg.setVisible(true);
			if (dlg.isOK) {
				Color color;
				Color innerColor;
				if (dlg.getCurrentColor() != null) {
					color = dlg.getCurrentColor();
				} else {
					color = frameDrawing.getCurrentColor();
				}
				if (dlg.getCurrentInnerColor() != null) {
					innerColor = dlg.getCurrentInnerColor();
				} else {
					innerColor = frameDrawing.getCurrentInnerColor();
				}
				newShape = new Circle(dlg.getCircle().getCenter(),dlg.getCircle().getRadius(),false,color,innerColor);
			}
			
		} else if (frameDrawing.getTglDonut().isSelected()) {
			DlgDonut dlg = new DlgDonut();
			dlg.setTitle("Add donut");
			dlg.addDonut(new Donut(new Point(e.getX(),e.getY()),-1,-1, false,frameDrawing.getCurrentColor(), frameDrawing.getCurrentInnerColor()));
			dlg.setVisible(true);
			if (dlg.isOK) {
				Color color;
				Color innerColor;
				if (dlg.getCurrentColor() != null) {
					color = dlg.getCurrentColor();
				} else {
					color = frameDrawing.getCurrentColor();
				}
				if (dlg.getCurrentInnerColor() != null) {
					innerColor = dlg.getCurrentInnerColor();
				} else {
					innerColor = frameDrawing.getCurrentInnerColor();
				}
				newShape = new Donut(dlg.getDonut().getCenter(),dlg.getDonut().getRadius(),dlg.getDonut().getInnerRadius(),false,color,innerColor);
			}
		} else if (frameDrawing.getTglHexagon().isSelected()) {
			DlgHexagon dlg = new DlgHexagon();
			dlg.setTitle("Add hexagon");
			dlg.addHexagon(new HexagonAdapter(e.getX(), e.getY(), -1, false, frameDrawing.getCurrentColor(), frameDrawing.getCurrentInnerColor()));
			dlg.setVisible(true);
			if (dlg.isOK) {
				Color color;
				Color innerColor;
				if (dlg.getCurrentColor() != null) {
					color = dlg.getCurrentColor();
				} else {
					color = frameDrawing.getCurrentColor();
				}
				if (dlg.getCurrentInnerColor() != null) {
					innerColor = dlg.getCurrentInnerColor();
				} else {
					innerColor = frameDrawing.getCurrentInnerColor();
				}
				newShape = new HexagonAdapter(dlg.getHexagon().getX(), dlg.getHexagon().getY(), dlg.getHexagon().getR(), false, color, innerColor);
			}
		}
		if (newShape != null) {
			AddShapeCmd addShape = new AddShapeCmd(drawingModel, newShape);
			addCommand(addShape);
			newShape.addObserver(new ShapeObserver(this.frameDrawing,this.drawingModel));
		}
		frameDrawing.getView().repaint();
		
	}
	
	public void undo() {
		 undoRedoStack.get(stackPointer).unexecute();
		 frameDrawing.addToLogList("Undo:"+ undoRedoStack.get(stackPointer).toString());
		 stackPointer--;
		 frameDrawing.repaint();
		 frameDrawing.getBtnRedo().setEnabled(true);
		 if (stackPointer==-1) frameDrawing.getBtnUndo().setEnabled(false);
	 }
	 
	 public void redo() {
		 stackPointer++;
		 undoRedoStack.get(stackPointer).execute();
		 frameDrawing.addToLogList("Redo:"+ undoRedoStack.get(stackPointer).toString());
		 frameDrawing.repaint();
		 frameDrawing.getBtnUndo().setEnabled(true);
		 
		 if(stackPointer + 1 >= undoRedoStack.size()) {
			 frameDrawing.getBtnRedo().setEnabled(false);
		 }
	 }
	 
	 public void addCommand(Command c) {
		 
		 clearStack(stackPointer);
		 
		 undoRedoStack.push(c);
		 stackPointer++;
		 c.execute();
		 
		 frameDrawing.addToLogList(c.toString());
		 frameDrawing.repaint(); 
		 frameDrawing.getBtnUndo().setEnabled(true);
		 frameDrawing.getBtnRedo().setEnabled(false);
	 }
	 
	 public void clearStack(int pointer) {
		 if (undoRedoStack.size()<1) {
			 return;
		 }
		 
		 for (int i = undoRedoStack.size()-1; i > pointer; i--) {
			 undoRedoStack.remove(i);
		 }
	 }
	 
	 public void toFront() {
		Iterator<Shape> iterator= drawingModel.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.isSelected()) {				
				if (drawingModel.getShapes().indexOf(shape)==drawingModel.getShapes().size()-1) {
					JOptionPane.showMessageDialog(null, "Element is alrady in front!");						
					break;
				} else {
					addCommand(new ToFrontCmd(drawingModel, shape));
					frameDrawing.repaint();
					break;
				}																					
			}
		}
	}
	 
	 public void toBack() {
		 Iterator<Shape> iterator= drawingModel.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.isSelected()) {				
				if (drawingModel.getShapes().indexOf(shape)==0) {
					JOptionPane.showMessageDialog(null, "Element is alrady in the back!");						
					break;
				} else {
					addCommand(new ToBackCmd(drawingModel, shape));
					frameDrawing.repaint();
					break;
				}																					
			}
		}
	 }
	 
	 public void bringToFront() {
		Iterator<Shape> iterator= drawingModel.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.isSelected()) {				
				if (drawingModel.getShapes().indexOf(shape)==drawingModel.getShapes().size()-1) {
					JOptionPane.showMessageDialog(null, "Element is alrady in front!");						
					break;
				} else {
					addCommand(new BringToFrontCmd(drawingModel, shape));
					frameDrawing.repaint();
					break;
				}																					
			}
		}
	}
	 
	 public void bringToBack() {
		 Iterator<Shape> iterator= drawingModel.getShapes().iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.isSelected()) {				
				if (drawingModel.getShapes().indexOf(shape)==0) {
					JOptionPane.showMessageDialog(null, "Element is alrady in the back!");						
					break;
				} else {
					addCommand(new BringToBackCmd(drawingModel, shape));
					frameDrawing.repaint();
					break;
				}																					
			}
		}
	}
	 
	public void save(int option) {
		if(option == JOptionPane.YES_OPTION) {
			SavingManager logManager = new SavingManager(new LogSaving(frameDrawing.getDlm()));
			logManager.save();
		}
		else if(option == JOptionPane.NO_OPTION) {
			SavingManager drawingManager = new SavingManager(new DrawingSaving(drawingModel.getShapes()));
			drawingManager.save();
		}
	}
	
	public void open(int option) {
		if(option == JOptionPane.YES_OPTION) {
			JFileChooser jfc=new JFileChooser("C:\\Users\\Slobodan\\Desktop");
			jfc.setFileFilter(new FileNameExtensionFilter("log file (.log)", "log"));
			jfc.setDialogTitle("Open Log File");
		    int chosen = jfc.showOpenDialog(null);
		    if (chosen == JFileChooser.APPROVE_OPTION) {
		        File logFile=new File(jfc.getSelectedFile().getAbsolutePath());
		        try 
		        {
		        	frameDrawing.getBtnUndo().setEnabled(false); 
		        	undoRedoStack.clear();
					stackPointer=-1;
					drawingModel.getShapes().clear();
					frameDrawing.getDlm().clear();
		        	frameDrawing.getBtnLoadNext().setEnabled(true);
		        	bufferedReader = new BufferedReader(new FileReader(logFile));
		        	frameDrawing.repaint();
		        	JOptionPane.showMessageDialog(null, "Drawing loaded successfully", "Success",JOptionPane.INFORMATION_MESSAGE);
		        } catch (Exception ex) 
		        {
		        	ex.printStackTrace();
		        	JOptionPane.showMessageDialog(null, "Error while opening log file", "Error",JOptionPane.ERROR_MESSAGE);
		        }
		    }
		}
		else if(option == JOptionPane.NO_OPTION)
		{
			JFileChooser jfc=new JFileChooser("C:\\Users\\Slobodan\\Desktop");
			jfc.setFileFilter(new FileNameExtensionFilter("ser file (.ser)", "ser"));
			jfc.setDialogTitle("Open Ser File");
		    int choosen =jfc.showOpenDialog(null);
		    if (choosen == JFileChooser.APPROVE_OPTION) {
		        File serFile=new File(jfc.getSelectedFile().getAbsolutePath());
		        try 
		        {
		        	FileInputStream fileInputStream = new FileInputStream(serFile);
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					frameDrawing.getBtnUndo().setEnabled(false);
					frameDrawing.getBtnLoadNext().setEnabled(false);
					undoRedoStack.clear();
					stackPointer=-1;
					drawingModel.getShapes().clear();
					frameDrawing.getDlm().clear();
					ArrayList<Shape> list = (ArrayList<Shape>)objectInputStream.readObject();
					for (Shape s : list) {
						drawingModel.add(s);
						s.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						if(s.isSelected()) {
							s.setSelected(true);
						}
					}
					frameDrawing.getTglSelect().setEnabled(true);
					objectInputStream.close();
					fileInputStream.close();
		            JOptionPane.showMessageDialog(null, "Drawing loaded successfully", "Success",JOptionPane.INFORMATION_MESSAGE);
		            frameDrawing.repaint();
		        } catch (Exception ex) 
		        {
		        	ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error while opening the file", "Error",JOptionPane.ERROR_MESSAGE);
		        }
		    }
		}
	}
	
	public void loadNext() {
		try {
			if((nextLine = bufferedReader.readLine()) != null) {
				bufferedReader.mark(1);
				checkLine();
				
				String[] unfiltered = nextLine.split("\\W");
				List<String> list = new ArrayList<String>();;
				
				for (String s : unfiltered) {
					list.add(s);
				}
				list.removeAll(Arrays.asList(""));
				
				String[] command = new String[list.size()];
				
				for (int i = 0; i< list.size(); i++) {
					command[i] = list.get(i);
				}
				
				System.out.println(command[0]);
				System.out.println(command[1]);
				System.out.println(command[2]);
				System.out.println(command[3]);
				
				Color color = frameDrawing.getCurrentColor();
				Color innerColor = frameDrawing.getCurrentInnerColor();
				
				if(command[1].contentEquals("Point")) {
					String action = command[0];
					Point point = new Point(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
					point.setColor(color);
					
					switch(action) {
					
					case "Added": {
						addCommand(new AddShapeCmd(drawingModel, point));
						point.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						break;
					}
					
					case "Updated": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Point) {
								if(point.equals((Point)shape)) {
									addCommand(new UpdatePointCmd((Point)shape, point));
								}
							}
						}
						break;	
					}
					
					case "Removed": {
						
						Iterator<Shape> it = selectedShapes.iterator();
						Shape deletedShape = null;
						while (it.hasNext()) {
							Shape nextShape = it.next();
							if(nextShape instanceof Point) {
								if(point.equals((Point)nextShape)) {
									addCommand(new RemoveShapeCmd(drawingModel, nextShape));
									deletedShape = nextShape;
								}
							}
						}
						selectedShapes.remove(deletedShape);
						break;	
					}
					
					case "Deselected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Point) {
								if(point.equals((Point)shape)) {
									addCommand(new DeselectShapeCmd(shape));
									selectedShapes.remove(shape);
								}
							}
						}
						break;	
					}
					
					case "Selected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Point) {
								if(point.equals((Point)shape)) {
									addCommand(new SelectShapeCmd(shape));
									selectedShapes.add(shape);
								}
							}
						}
						break;
					}
					
					default:
						break;
					
					}
				} else if (command[1].contentEquals("Line")) {
					String action = command[0];
					Line line = new Line(new Point(Integer.parseInt(command[2]), Integer.parseInt(command[3])), new Point(Integer.parseInt(command[4]), Integer.parseInt(command[5])));
					line.setColor(color);
					switch (action) {
					
					case "Added": {
						addCommand(new AddShapeCmd(drawingModel, line));
						line.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						break;
					}
					
					case "Updated": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Line) {
								if(line.equals((Line)shape)) {
									addCommand(new UpdateLineCmd((Line)shape, line));
								}
							}
						}
						break;	
					}
					
					case "Removed": {
						Iterator<Shape> it = selectedShapes.iterator();
						Shape deletedShape = null;
						while (it.hasNext()) {
							Shape nextShape = it.next();
							if(nextShape instanceof Line) {
								if(line.equals((Line)nextShape)) {
									addCommand(new RemoveShapeCmd(drawingModel, nextShape));
									deletedShape = nextShape;
								}
							}
						}
						selectedShapes.remove(deletedShape);
						break;		
					}
					
					case "Deselected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Line) {
								if(line.equals((Line)shape)) {
									addCommand(new DeselectShapeCmd(shape));
									selectedShapes.remove(shape);
								}
							}
						}
						break;	
					}
					
					case "Selected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Line) {
								if(line.equals((Line)shape)) {
									addCommand(new SelectShapeCmd(shape));
									selectedShapes.add(shape);
								}
							}
						}
						break;
					}
					
					default:
						break;
					
					}
				} else if (command[1].contentEquals("Rectangle")) {
					String action = command[0];
					Rectangle rec = new Rectangle(new Point(Integer.parseInt(command[6]), Integer.parseInt(command[7])), Integer.parseInt(command[9]), Integer.parseInt(command[11]));
					rec.setColor(color);
					rec.setInnerColor(innerColor);
					switch (action) {
					
					case "Added": {
						addCommand(new AddShapeCmd(drawingModel, rec));
						rec.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						break;
					}
					
					case "Updated": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Rectangle) {
								if(rec.equals((Rectangle)shape)) {
									addCommand(new UpdateRectangleCmd((Rectangle)shape, rec));
								}
							}
						}
						break;	
					}
					
					case "Removed": {
						Iterator<Shape> it = selectedShapes.iterator();
						Shape deletedShape = null;
						while (it.hasNext()) {
							Shape nextShape = it.next();
							if(nextShape instanceof Rectangle) {
								if(rec.equals((Rectangle)nextShape)) {
									addCommand(new RemoveShapeCmd(drawingModel, nextShape));
									deletedShape = nextShape;
								}
							}
						}
						selectedShapes.remove(deletedShape);
						break;		
					}
					
					case "Deselected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Rectangle) {
								if(rec.equals((Rectangle)shape)) {
									addCommand(new DeselectShapeCmd(shape));
									selectedShapes.remove(shape);
								}
							}
						}
						break;	
					}
					
					case "Selected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Rectangle) {
								if(rec.equals((Rectangle)shape)) {
									addCommand(new SelectShapeCmd(shape));
									selectedShapes.add(shape);
								}
							}
						}
						break;
					}
					
					default:
						break;
					
					}
				} else if (command[1].contentEquals("Circle")) {
					String action = command[0];
					Circle circle = new Circle(new Point(Integer.parseInt(command[4]), Integer.parseInt(command[5])), Integer.parseInt(command[7]));
					circle.setColor(color);
					circle.setInnerColor(innerColor);
					switch (action) {
					
					case "Added": {
						addCommand(new AddShapeCmd(drawingModel, circle));
						circle.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						break;
					}
					
					case "Updated": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Circle) {
								if(circle.equals((Circle)shape)) {
									addCommand(new UpdateCircleCmd((Circle)shape, circle));
								}
							}
						}
						break;	
					}
					
					case "Removed": {
						Iterator<Shape> it = selectedShapes.iterator();
						Shape deletedShape = null;
						while (it.hasNext()) {
							Shape nextShape = it.next();
							if(nextShape instanceof Circle) {
								if(circle.equals((Circle)nextShape)) {
									addCommand(new RemoveShapeCmd(drawingModel, nextShape));
									deletedShape = nextShape;
								}
							}
						}
						selectedShapes.remove(deletedShape);
						break;	
					}
					
					case "Deselected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Circle) {
								if(circle.equals((Circle)shape)) {
									addCommand(new DeselectShapeCmd(shape));
									selectedShapes.remove(shape);
								}
							}
						}
						break;	
					}
					
					case "Selected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Circle) {
								if(circle.equals((Circle)shape)) {
									addCommand(new SelectShapeCmd(shape));
									selectedShapes.add(shape);
								}
							}
						}
						break;
					}
					
					default:
						break;
					
					}
					
				} else if (command[1].contentEquals("Donut")) {
					String action = command[0];
					Donut donut = new Donut(new Point(Integer.parseInt(command[4]), Integer.parseInt(command[5])), Integer.parseInt(command[7]), Integer.parseInt(command[10]));
					donut.setColor(color);
					donut.setInnerColor(innerColor);
					switch (action) {
					
					case "Added": {
						addCommand(new AddShapeCmd(drawingModel, donut));
						donut.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						break;
					}
					
					case "Updated": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Donut) {
								if(donut.equals((Donut)shape)) {
									addCommand(new UpdateDonutCmd((Donut)shape, donut));
								}
							}
						}
						break;	
					}
					
					case "Removed": {
						Iterator<Shape> it = selectedShapes.iterator();
						Shape deletedShape = null;
						while (it.hasNext()) {
							Shape nextShape = it.next();
							if(nextShape instanceof Donut) {
								if(donut.equals((Donut)nextShape)) {
									addCommand(new RemoveShapeCmd(drawingModel, nextShape));
									deletedShape = nextShape;
								}
							}
						}
						selectedShapes.remove(deletedShape);
						break;	
					}
					
					case "Deselected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Donut) {
								if(donut.equals((Donut)shape)) {
									addCommand(new DeselectShapeCmd(shape));
									selectedShapes.remove(shape);
								}
							}
						}
						break;	
					}
					
					case "Selected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof Donut) {
								if(donut.equals((Donut)shape)) {
									addCommand(new SelectShapeCmd(shape));
									selectedShapes.add(shape);
								}
							}
						}
						break;
					}
					
					default:
						break;
					
					}
					
				} else if (command[1].contentEquals("Hexagon")) {
					String action = command[0];
					HexagonAdapter hex = new HexagonAdapter(Integer.parseInt(command[3]), Integer.parseInt(command[4]),Integer.parseInt(command[6]));
					hex.setColor(color);
					hex.setInnerColor(innerColor);
					switch (action) {
					
					case "Added": {
						addCommand(new AddShapeCmd(drawingModel, hex));
						hex.addObserver(new ShapeObserver(frameDrawing, drawingModel));
						break;
					}
					
					case "Updated": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof HexagonAdapter) {
								if(hex.equals((HexagonAdapter)shape)) {
									addCommand(new UpdateHexagonCmd((HexagonAdapter)shape, hex));
								}
							}
						}
						break;	
					}
					
					case "Removed": {
						Iterator<Shape> it = selectedShapes.iterator();
						Shape deletedShape = null;
						while (it.hasNext()) {
							Shape nextShape = it.next();
							if(nextShape instanceof HexagonAdapter) {
								if(hex.equals((HexagonAdapter)nextShape)) {
									addCommand(new RemoveShapeCmd(drawingModel, nextShape));
									deletedShape = nextShape;
								}
							}
						}
						selectedShapes.remove(deletedShape);
						break;		
					}
					
					case "Deselected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof HexagonAdapter) {
								if(hex.equals((HexagonAdapter)shape)) {
									addCommand(new DeselectShapeCmd(shape));
									selectedShapes.remove(shape);
								}
							}
						}
						break;	
					}
					
					case "Selected": {
						for(Shape shape : drawingModel.getShapes()) {
							if(shape instanceof HexagonAdapter) {
								if(hex.equals((HexagonAdapter)shape)) {
									addCommand(new SelectShapeCmd(shape));
									selectedShapes.add(shape);
								}
							}
						}
						break;
					}
					
					default:
						break;
					
					}
				}
			
				String action = command[0];
				switch (action) {
				
				case "Undo":
					undo();
					break;
					
				case "Redo":
					redo();
					break;
					
				case "ToFront":
					toFront();
					break;
					
				case "ToBack":
					toBack();
					break;
					
				case "BringToFront":
					bringToFront();
					break;
					
				case "BringToBack":
					bringToBack();
					break;						

				default:
					break;
					
				}	
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error while opening log file", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void checkLine() {	
		try {
			if(bufferedReader.readLine()==null)
			{
				frameDrawing.getBtnLoadNext().setEnabled(false);
				bufferedReader.close();
			}
			else
			{
				bufferedReader.reset();
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error while opening the file", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}
