package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class FrameDrawing extends JFrame {
	
	private JToggleButton tglSelect;
	private JButton btnModify;
	private JButton btnDelete;
	
	private JButton btnColor;
	private JButton btnInnerColor;
	
	private JButton btnUndo;
	private JButton btnRedo;
	
	private JButton btnToBack;
	private JButton btnToFront;
	private JButton btnBringToBack;
	private JButton btnBringToFront;
	
	private JButton btnSave;
	private JButton btnOpen;
	private JButton btnLoadNext;
	
	private JToggleButton tglPoint;
	private JToggleButton tglLine;
	private JToggleButton tglRectangle;
	private JToggleButton tglCircle;
	private JToggleButton tglDonut;
	private JToggleButton tglHexagon;
	
	private PnlDrawing drawingPanel = new PnlDrawing();
	private DrawingController controller;
	

	private Color currentColor = Color.BLACK;
	private Color currentInnerColor = Color.WHITE;
	
	private JPanel logPanel;
	private JPanel savePanel;
	private JScrollPane scrollPane;
	private JList<String> logList;
	private DefaultListModel<String> dlm = new DefaultListModel<String>(); 

	public FrameDrawing() {
		
		super("Gajic Nikola IT27/2018");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,800);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0,0));
		setContentPane(contentPane);
		
		drawingPanel.setBackground(Color.WHITE);
		contentPane.add(drawingPanel, BorderLayout.CENTER);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.LIGHT_GRAY);
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		tglSelect = new JToggleButton("Selection");
		tglSelect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModify = new JButton("Modify");
		btnModify.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete = new JButton("Delete");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnColor = new JButton("Color");
		btnColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUndo = new JButton("Undo");
		btnUndo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRedo = new JButton("Redo");
		btnRedo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		btnModify.setEnabled(false);
		btnDelete.setEnabled(false);
		
		btnToFront = new JButton("To Front");
		btnToFront.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnToBack = new JButton("To Back");
		btnToBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnToFront.setEnabled(false);
		btnToBack.setEnabled(false);
		btnBringToFront.setEnabled(false);
		btnBringToBack.setEnabled(false);
		
		btnSave = new JButton("Save");
		btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOpen = new JButton("Open");
		btnOpen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLoadNext = new JButton("Load Next");
		btnLoadNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnLoadNext.setEnabled(false);
		
		tglPoint = new JToggleButton("Point");
		tglPoint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglLine = new JToggleButton("Line");
		tglLine.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglRectangle = new JToggleButton("Rectangle");
		tglRectangle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglCircle = new JToggleButton("Circle");
		tglCircle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglDonut = new JToggleButton("Donut");
		tglDonut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tglHexagon = new JToggleButton("Hexagon");
		tglHexagon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		ButtonGroup group = new ButtonGroup();
		group.add(tglSelect);
		group.add(tglPoint);
		group.add(tglLine);
		group.add(tglRectangle);
		group.add(tglCircle);
		group.add(tglDonut);
		group.add(tglHexagon);
		
		
		JButton labelColor = new JButton(" ");
		JButton labelInnerColor = new JButton(" ");
		labelColor.setEnabled(false);
		labelInnerColor.setEnabled(false);
		labelColor.setBackground(currentColor);
		labelInnerColor.setBackground(currentInnerColor);
		
		northPanel.add(tglSelect);
		northPanel.add(btnModify);
		northPanel.add(btnDelete);
		northPanel.add(btnColor);
		northPanel.add(labelColor);
		northPanel.add(btnInnerColor);
		northPanel.add(labelInnerColor);
		northPanel.add(btnUndo);
		northPanel.add(btnRedo);
		
		southPanel.add(tglPoint);
		southPanel.add(tglLine);
		southPanel.add(tglRectangle);
		southPanel.add(tglCircle);
		southPanel.add(tglDonut);
		southPanel.add(tglHexagon);
		southPanel.add(btnToBack);
		southPanel.add(btnToFront);
		southPanel.add(btnBringToBack);
		southPanel.add(btnBringToFront);
		
		
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
			}
		});
		
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = JColorChooser.showDialog(null, "Choose color", currentColor);
				labelColor.setBackground(currentColor);
			}
		});
		
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentInnerColor = JColorChooser.showDialog(null, "Choose inner color", currentInnerColor);
				labelInnerColor.setBackground(currentInnerColor);
			}
		});
		
		drawingPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.drawingMouseClicked(arg0);
			}
		});
		
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		
		btnLoadNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadNext();
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] choice = { "Save Log", "Save Drawing"};
				int optionChosen= JOptionPane.showOptionDialog(null, "Choose What To Save", "Save File",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, choice, choice[0]);
				controller.save(optionChosen);
			}
		});
		
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] choice = { "Open Log", "Open Drawing"};
				int optionChosen= JOptionPane.showOptionDialog(null, "Choose What To Open", "Open File",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, choice, choice[0]);
				controller.open(optionChosen);
			}
		});
		
		logPanel = new JPanel();
		logPanel.setBorder(new TitledBorder("Log"));
		logPanel.setLayout(new BorderLayout(0,0));
		
		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		
		logPanel.add(scrollPane, BorderLayout.CENTER);
		
		logList = new JList<String>();
		logList.setBackground(SystemColor.controlLtHighlight);
		
		scrollPane.setViewportView(logList);
		logList.setModel(dlm);
		
		savePanel = new JPanel();		
		savePanel.add(btnSave);
		savePanel.add(btnOpen);
		savePanel.add(btnLoadNext);
		
		logPanel.add(savePanel, BorderLayout.SOUTH);
		
		contentPane.add(logPanel, BorderLayout.WEST);
		
	
	}
	
	public void addToLogList(String string)
	{
		this.dlm.addElement(string);
	}

	public JToggleButton getTglSelect() {
		return tglSelect;
	}

	public void setTglSelect(JToggleButton tglSelect) {
		this.tglSelect = tglSelect;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JButton btnModify) {
		this.btnModify = btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JToggleButton getTglPoint() {
		return tglPoint;
	}

	public void setTglPoint(JToggleButton tglPoint) {
		this.tglPoint = tglPoint;
	}

	public JToggleButton getTglLine() {
		return tglLine;
	}

	public void setTglLine(JToggleButton tglLine) {
		this.tglLine = tglLine;
	}

	public JToggleButton getTglRectangle() {
		return tglRectangle;
	}

	public void setTglRectangle(JToggleButton tglRectangle) {
		this.tglRectangle = tglRectangle;
	}

	public JToggleButton getTglCircle() {
		return tglCircle;
	}

	public void setTglCircle(JToggleButton tglCircle) {
		this.tglCircle = tglCircle;
	}

	public JToggleButton getTglDonut() {
		return tglDonut;
	}

	public void setTglDonut(JToggleButton tglDonut) {
		this.tglDonut = tglDonut;
	}
	
	public JToggleButton getTglHexagon() {
		return tglHexagon;
	}

	public void setTglHexagon(JToggleButton tglHexagon) {
		this.tglHexagon = tglHexagon;
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}
	
	public Color getCurrentInnerColor() {
		return currentInnerColor;
	}
	
	public void setCurrentInnerColor(Color currentInnerColor) {
		this.currentInnerColor = currentInnerColor;
	}
	
	public PnlDrawing getView() {
		return this.drawingPanel;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public JButton getBtnUndo() {
		return this.btnUndo;
	}
	
	public JButton getBtnRedo() {
		return this.btnRedo;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public void setBtnToBack(JButton btnToBack) {
		this.btnToBack = btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public void setBtnToFront(JButton btnToFront) {
		this.btnToFront = btnToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}
	
	public DefaultListModel<String> getDlm() {
		return this.dlm;
	}
	
	public JButton getBtnLoadNext() {
		return this.btnLoadNext;
	}

}
