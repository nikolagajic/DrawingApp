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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DlgPoint extends JDialog {
	
	private JTextField txtX;
	private JTextField txtY;
	
	private JButton btnOK;
	private JButton btnCancel;
	
	private JButton btnColor;
	
	private Color currentColor;
	
	private Point point;
	
	public boolean isOK;
	
	public DlgPoint() {
		
		setModal(true);
		setResizable(false);
		setTitle("Modification of point");
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
		
		
		// south panel
		btnOK = new JButton("  OK  ");
		btnOK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancel = new JButton("Cancel");
		btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		southPanel.add(btnOK);
		southPanel.add(btnCancel);
		
		// center panel
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		JLabel labelX = new JLabel("Coordinate x:");
		txtX = new JTextField(10);
		JLabel labelY = new JLabel("Coordinate y:");
		txtY = new JTextField(10);
		btnColor = new JButton("Select color");
		btnColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		gc.weightx = 1;
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.gridx = 0;
		centerPanel.add(labelX, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		centerPanel.add(labelY, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		centerPanel.add(txtX, gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		centerPanel.add(txtY, gc);
		
		gc.anchor = GridBagConstraints.NORTH;
		gc.weighty = 7;
		gc.gridx = 0;
		gc.gridy = 2;
		centerPanel.add(btnColor,gc);
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtX.getText().isEmpty() || txtY.getText().isEmpty()) {
					JOptionPane.showMessageDialog(getParent(), "Coordinates can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						point = new Point();
						isOK=true;
						point.setX(Integer.parseInt(txtX.getText()));
						point.setY(Integer.parseInt(txtY.getText()));
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
				currentColor = JColorChooser.showDialog(null, "Choose point color", currentColor);
			}
		});
	}
	
	public void modifyPoint(Point p) {
		txtX.setText(String.valueOf(p.getX()));
		txtY.setText(String.valueOf(p.getY()));
		currentColor = p.getColor();
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color selectedColor) {
		this.currentColor = selectedColor;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
