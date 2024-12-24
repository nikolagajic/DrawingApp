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

import hexagon.HexagonAdapter;

public class DlgHexagon extends JDialog{

	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtR;

	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnColor;
	private JButton btnInnerColor;
	
	private Color currentColor;
	private Color currentInnerColor;

	private HexagonAdapter hexagon;
	
	public boolean isOK;
	
	public DlgHexagon() {
		
		setModal(true);
		setResizable(false);
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
		JLabel labelX = new JLabel("Center coordinate x:");
		txtX = new JTextField(10);
		JLabel labelY = new JLabel("Center coordinate y:");
		txtY = new JTextField(10);
		JLabel labelRadius = new JLabel("Radius:");
		txtR = new JTextField(10);
		
		btnColor = new JButton("      Select color     ");
		btnColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInnerColor = new JButton("Select inner color");
		btnInnerColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		gc.weightx = 1;
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.gridx = 0;
		centerPanel.add(labelX, gc);
		
		gc.gridy = 1;
		centerPanel.add(labelY, gc);
		
		gc.gridy = 2;
		centerPanel.add(labelRadius, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		centerPanel.add(txtX, gc);
		
		gc.gridy = 1;
		centerPanel.add(txtY, gc);
		
		gc.gridy = 2;
		centerPanel.add(txtR, gc);
		
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridx = 0;
		gc.gridy = 3;
		centerPanel.add(btnColor,gc);
		gc.weighty = 6;
		gc.gridy = 4;
		centerPanel.add(btnInnerColor,gc);
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtR.getText().isEmpty()) {
					JOptionPane.showMessageDialog(getParent(), "Values can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						hexagon = new HexagonAdapter();
						isOK= true;
						int x = Integer.parseInt(txtX.getText());
						int y = Integer.parseInt(txtY.getText());
						hexagon.setX(x);
						hexagon.setY(y);
						int r = Integer.parseInt(txtR.getText());
						if (r < 0) {
							JOptionPane.showMessageDialog(getParent(), "Radius can't be negative!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						hexagon.setR(r);
						dispose();
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(getParent(),"Values must be integers", "Error", JOptionPane.ERROR_MESSAGE);
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
				currentColor = JColorChooser.showDialog(null, "Choose hexagon color", currentColor);
			}
		});
		
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentInnerColor = JColorChooser.showDialog(null, "Choose inner color", currentInnerColor);
			}
		});
	}
	
	public void addHexagon(HexagonAdapter h) {
		txtX.setText(String.valueOf(h.getX()));
		txtY.setText(String.valueOf(h.getY()));
		txtX.setEditable(false);
		txtY.setEditable(false);
		currentColor = h.getColor();
		currentInnerColor = h.getInnerColor();
	}
	
	public void modifyHexagon(HexagonAdapter h) {
		txtX.setText(String.valueOf(h.getX()));
		txtY.setText(String.valueOf(h.getY()));
		txtR.setText(String.valueOf(h.getR()));
		currentColor = h.getColor();
		currentInnerColor = h.getInnerColor();
	}
	
	public HexagonAdapter getHexagon() {
		return hexagon;
	}

	public void setHexagon(HexagonAdapter hexagon) {
		this.hexagon = hexagon;
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
}
