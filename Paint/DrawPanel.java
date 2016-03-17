package part3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel{

	private MyShape[] shapes;
	private int shapeCount;
	private int shapeType;
	private MyShape currentShape;
	private Color currentColor;
	private boolean filledShape;
	private JLabel statusLabel;
	private MouseHandler mouseHandler;
		
	class MouseHandler extends MouseAdapter implements MouseMotionListener{
		
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			System.out.println(shapeType);
			currentShape = (shapeType == 0 ? new MyShape("line") : (shapeType == 1 ? new MyShape("oval") : new MyShape("rectangle")));
			currentShape.setStartPosition(e.getX(), e.getY());
			currentShape.shape_color = currentColor;
			currentShape.is_filled = filledShape;
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			super.mouseReleased(e);
			currentShape.setEndPosition(e.getX(), e.getY());
			shapes[shapeCount] = currentShape;
			currentShape = null;
			shapeCount++;
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e){
			super.mouseMoved(e);
			statusLabel.setText("("+e.getX()+","+e.getY()+")");
		}
		
		@Override
		public void mouseDragged(MouseEvent e){
			super.mouseDragged(e);
			currentShape.setEndPosition(e.getX(), e.getY());
			statusLabel.setText("("+e.getX()+","+e.getY()+")");
			repaint();
		}
		
	}
	
	private void init(){
		shapes = new MyShape[100];
		shapeCount = 0;
		shapeType = 0;
		currentShape = null;
		currentColor = Color.BLACK;
		setBackground(Color.WHITE);
		statusLabel = new JLabel();
		mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}
	
	public DrawPanel(JLabel tempLabel){
		init();
		statusLabel = tempLabel;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < shapeCount; ++i){
			MyShape temp = shapes[i];
			g.setColor(temp.shape_color);
			if(temp.getType().equals("line")){
				g.drawLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
			} else if (temp.getType().equals("oval")){
				if (temp.is_filled)
					g.fillOval(temp.startX, temp.startY, temp.endX-temp.startX, temp.endY-temp.startY);
				else
					g.drawOval(temp.startX, temp.startY, temp.endX-temp.startX, temp.endY-temp.startY);
			} else if (temp.getType().equals("rectangle")){
				if(temp.is_filled)
					g.fillRect(temp.startX, temp.startY, temp.endX-temp.startX, temp.endY-temp.startY);
				else
					g.drawRect(temp.startX, temp.startY, temp.endX-temp.startX, temp.endY-temp.startY);
			}
		}
		if(currentShape != null){
			g.setColor(currentShape.shape_color);
			if(currentShape.getType().equals("line")){
				g.drawLine(currentShape.getStartX(), currentShape.getStartY(), currentShape.getEndX(), currentShape.getEndY());
			} else if (currentShape.getType().equals("oval")){
				if (currentShape.is_filled)
					g.fillOval(currentShape.startX, currentShape.startY, currentShape.endX-currentShape.startX, currentShape.endY-currentShape.startY);
				else
					g.drawOval(currentShape.startX, currentShape.startY, currentShape.endX-currentShape.startX, currentShape.endY-currentShape.startY);
			} else if (currentShape.getType().equals("rectangle")){
				if(currentShape.is_filled)
					g.fillRect(currentShape.startX, currentShape.startY, currentShape.endX-currentShape.startX, currentShape.endY-currentShape.startY);
				else
					g.drawRect(currentShape.startX, currentShape.startY, currentShape.endX-currentShape.startX, currentShape.endY-currentShape.startY);
			}
		}
	}

	public void setShapeType(int type){
		this.shapeType = type;
	}

	public void setCurrentColor(Color c){
		this.currentColor = c;
	}
	
	public void setFilledShape(boolean b){
		this.filledShape = b;
	}
	
	public void clearLastShape(){
		if(shapeCount == 0)
			System.out.println("shapeCount is 0, cannot decrement.");
		else 
			--this.shapeCount;
		repaint();
	}
	
	public void clearDrawing(){
		shapeCount = 0;
		repaint();
	}

}
