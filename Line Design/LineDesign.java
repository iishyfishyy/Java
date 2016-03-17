	package part2a;
	
	import java.awt.Graphics;
	
	import javax.swing.JFrame;
	
	public class LineDesign extends JFrame{
	
		public LineDesign(){
			super("Line Design");
			setSize(200,200);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
		
		public void paint(Graphics g){
			int x1, y1, x2, y2;
			int width = getWidth();
			
			x1 = 0;
			y1 = 0;
			
			x2 = 15;
			y2 = width;
			while(y1 < width){
				g.drawLine(x1, y1, x2, y2);
				y1=y1+15;
				x2=x2+15;
			}
			x1 = 0;
			y1 = width;
			
			x2 = width;
			y2 = width-15;
			while(y2 > 0){
				g.drawLine(x1, y1, x2, y2);
				y2=y2-15;
				x1=x1+15;
			}
			x1 = width;
			y1 = width;
			
			x2 = width-15;
			y2 = 0;
			while(x2 > 0){
				g.drawLine(x1, y1, x2, y2);
				y1=y1-15;
				x2=x2-15;
			}
			x1 = width;
			y1 = 0;
			
			x2 = 0;
			y2 = 15;
			while(x1 > 0){
				g.drawLine(x1, y1, x2, y2);
				y2=y2+15;
				x1=x1-15;
			}
		}
		
		public static void main(String... args){
			LineDesign ld = new LineDesign();
		}
		
	}
