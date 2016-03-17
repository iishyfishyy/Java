package part3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawFrame extends JFrame implements ActionListener{
	
	JLabel start;
	DrawPanel draw;
	JPanel components;
	JButton undo, clear;
	JComboBox colorSelect, shapeType;
	JCheckBox checkFilled;
	private static final String[] colors = {"Black","Green","Blue","Red","White","Cyan","Gray","Magenta","Pink","Yellow","Dark Grey","Orange","Light Grey"};
	private static final Color[] color_array = {Color.BLACK, Color.GREEN, Color.BLUE, Color.RED, Color.WHITE, Color.CYAN, Color.GRAY, Color.MAGENTA, Color.PINK, Color.YELLOW, Color.DARK_GRAY, Color.ORANGE, Color.LIGHT_GRAY};
	private static final String[] shapes = {"Line","Oval","Rectangle"};
	
	public void init(){
		components = new JPanel();
		undo = new JButton("Undo");
		undo.addActionListener(this);
		clear = new JButton("Clear");
		clear.addActionListener(this);
		colorSelect = new JComboBox(colors);
		colorSelect.addActionListener(this);
		shapeType = new JComboBox(shapes);
		shapeType.addActionListener(this);
		checkFilled = new JCheckBox("Filled");
		checkFilled.addActionListener(this);
		components.add(undo);
		components.add(clear);
		components.add(colorSelect);
		components.add(shapeType);
		components.add(checkFilled);
		start = new JLabel("Starting paint");
		draw = new DrawPanel(start);
		add(components, BorderLayout.NORTH);
		add(draw, BorderLayout.CENTER);
		add(start, BorderLayout.SOUTH);
	}
	
	public DrawFrame(){
		super("Paint");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		init();
		setSize(1000,500);
		setVisible(true);
	}
	
	public static void main(String... args){
		DrawFrame frame = new DrawFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == undo){
			draw.clearLastShape();
		} else if (e.getSource() == clear){
			draw.clearDrawing();
		} else if (e.getSource() == colorSelect){
			draw.setCurrentColor(color_array[colorSelect.getSelectedIndex()]);
		} else if (e.getSource() == shapeType){
			draw.setShapeType(shapeType.getSelectedIndex());
		} else if (e.getSource() == checkFilled){
			draw.setFilledShape(checkFilled.isSelected());
		}
	}

}

