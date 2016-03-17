package part2b;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class LineMaze extends JFrame{

	int mazescale = 8;
	Scaler myScale;

	class Scaler extends JFrame implements ChangeListener{
		
		private JSlider slider;
		
		public Scaler(){
			
			super("Set your maze scale - LineMaze");
			slider = new JSlider(JSlider.HORIZONTAL, 5, 20, 8);
			slider.addChangeListener(this);
			add(slider);
			pack();
			setSize(325, getHeight());
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setVisible(true);
			
		}
		
		public void stateChanged(ChangeEvent e) {
			mazescale = slider.getValue();
			LineMaze.this.repaint();			
		}
		
	}
	
	public LineMaze() {
		super("Line Maze");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,300);
		myScale = new Scaler();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		int x1, y1, x2, y2;
		int baselength, length;
		
		x1 = getWidth()/2;
		y1 = getHeight()/2;
		length = baselength = x1/mazescale;
		String state = "down";
		
		for(int i = 0; x1 < getWidth() || y1 < getHeight(); ++i){
			
			if(i != 0 && i % 2 == 0){
				length += baselength;
			} 
			
			switch(state){
			
			case "down":
				state = "left";
				g.drawLine(x1, y1, x1, y1+length);
				y1 += length;
				break;
				
			case "left":
				state = "up";
				g.drawLine(x1, y1, x1-length, y1);
				x1 -= length;
				break;
			
			case "up":
				state = "right";
				g.drawLine(x1, y1, x1, y1-length);
				y1 -= length;
				break;
			
			case "right":
				state = "down";
				g.drawLine(x1, y1, x1+length, y1);
				x1 += length;
				break;
			
			}
			
		}
		
	}

	public static void main(String... args){
		
		LineMaze lm = new LineMaze();
		lm.setVisible(true);
		
	}
	
}
