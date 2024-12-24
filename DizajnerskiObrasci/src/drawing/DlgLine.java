package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DlgLine extends JDialog {
	
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnColor;
	
	private Color currentColor;
	
	private Line line;
	
	public boolean isOK;
	
	public DlgLine() {
		
		setModal(true);
		setResizable(false);
		setTitle("Modification of line");
		setBounds(150, 150, 350, 250);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0,0));
		setContentPane(contentPane);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		btnOK = new JButton("  OK  ");
		btnOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancel = new JButton("Cancel");
		btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		southPanel.add(btnOK);
		southPanel.add(btnCancel);
		
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		JLabel labelStartX = new JLabel("Start coordinate x:");
		txtStartX = new JTextField(10);
		JLabel labelStartY = new JLabel("Start coordinate y:");
		txtStartY = new JTextField(10);
		JLabel labelEndX = new JLabel("End coordinate x:");
		txtEndX = new JTextField(10);
		JLabel labelEndY = new JLabel("End coordinate y:");
		txtEndY = new JTextField(10);
		
		btnColor = new JButton("Select color");
		btnColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		gc.weightx = 1;
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.gridx = 0;
		centerPanel.add(labelStartX, gc);
		
		gc.gridy = 1;
		centerPanel.add(labelStartY, gc);
		
		gc.gridy = 2;
		centerPanel.add(labelEndX, gc);
		
		gc.gridy = 3;
		centerPanel.add(labelEndY, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		centerPanel.add(txtStartX, gc);
		
		gc.gridy = 1;
		centerPanel.add(txtStartY, gc);
		
		gc.gridy = 2;
		centerPanel.add(txtEndX, gc);
		
		gc.gridy = 3;
		centerPanel.add(txtEndY, gc);
		
		gc.anchor = GridBagConstraints.NORTH;
		gc.weighty = 7;
		gc.gridx = 0;
		gc.gridy = 4;
		centerPanel.add(btnColor,gc);
		
		btnOK.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (txtStartX.getText().isEmpty() || txtStartY.getText().isEmpty() || 
						txtEndX.getText().isEmpty() || txtEndY.getText().isEmpty()) {
					JOptionPane.showMessageDialog(getParent(), "Coordinates can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						line = new Line();
						isOK= true;
						int x;
						int y;
						x = Integer.parseInt(txtStartX.getText());
						y = Integer.parseInt(txtStartY.getText());
						line.setStartPoint(new Point(x,y));
						x = Integer.parseInt(txtEndX.getText());
						y = Integer.parseInt(txtEndY.getText());
						line.setEndPoint(new Point(x,y));
						dispose();
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(getParent(),"Coordinates must be integers", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentColor = JColorChooser.showDialog(null, "Choose line color", currentColor);
			}
		});
	}
	
	public void modifyLine(Line l) {
		txtStartX.setText(String.valueOf(l.getStartPoint().getX()));
		txtStartY.setText(String.valueOf(l.getStartPoint().getY()));
		txtEndX.setText(String.valueOf(l.getEndPoint().getX()));
		txtEndY.setText(String.valueOf(l.getEndPoint().getY()));
		currentColor = l.getColor();
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}
