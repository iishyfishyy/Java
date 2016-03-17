package part1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TypeTest extends JFrame implements KeyListener, ActionListener{
	
	private JPanel panelB;
	private JTextArea inputArea;
	private JLabel instructionsLabel;
	private JButton start, finish;
	private static final String keyboardStrings = "1 2 3 4 5 6 7 8 9 0 - = Q W E R T Y U I O P [ ] \\ A S D F G H J K L ; ' Z X C V B N M , . / Sp Sh";
	private LinkedHashMap<String, JButton> characters = new LinkedHashMap<String, JButton>();
	
	private ArrayList<String> testStrings = new ArrayList<String>();
	private String currentTestString;
	private String userInputString;
	
	public TypeTest(){
		super("Typing Tester");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(5,1));
		setResizable(true);
		initTop();
		initKeyboard();
		testStrings.add("The quick brown fox jumps over a lazy dog.");
		testStrings.add("The five boxing wizards jump quickly");
		testStrings.add("We promptly judged antique ivory buckles for the next prize.");
		testStrings.add("A mad boxer shot a quick, gloved jab to the jaw of his dizzy opponent.");
		testStrings.add("How razorback-jumping frogs can level six piqued gymnasts.");
		start = new JButton("START");
		finish = new JButton("FINISH");
		start.addActionListener(this);
		finish.addActionListener(this);
		add(start);
		add(finish);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == start){
			Random r = new Random();
			currentTestString = testStrings.get(r.nextInt(5));
			JFrame tempFrame = new JFrame("Please type this sentence!");
			tempFrame.add(new JLabel(currentTestString));
			tempFrame.pack();
			tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tempFrame.setVisible(true);
		} else if (e.getSource() == finish){
			checkUserInput();
		}
	}
	
	public void checkUserInput(){
		userInputString = inputArea.getText().trim();
		char[] test = currentTestString.toCharArray();
		char[] user = userInputString.toCharArray();
		int faultCount = 0;
		for(int i = 0; i < currentTestString.length(); ++i){
			if(i < userInputString.length()){
				if(test[i] != user[i]){
					++faultCount;
				}
			} else {
				++faultCount;
			}
		}
		int correct = currentTestString.length() - faultCount;
		double accuracy;
		if(faultCount == 0){
			accuracy = 1.0;
		} else {
			accuracy = 1.0 - (double)faultCount/currentTestString.length();
		}
		System.out.println(faultCount + "/" + currentTestString.length() + "=" + (double)faultCount/currentTestString.length());
		DecimalFormat df = new DecimalFormat("#.##");
		String reply = "<html>Number of correctly inputted characters is: " + correct + ".<br>Number of faulty inputted characters is: " + faultCount + ".<br>Accuracy: " + df.format(accuracy)+ "</html>"; 
		JFrame tempFrame = new JFrame("Test Score");
		tempFrame.add(new JLabel(reply));
		tempFrame.pack();
		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tempFrame.setVisible(true);
	}
	
	public void initTop(){
		inputArea = new JTextArea(5,50);
		inputArea.setLineWrap(true);
		inputArea.addKeyListener(this);
		instructionsLabel = new JLabel();
		instructionsLabel.setText("<html>Type text in the following box. Each key you press will be highlighted.<br>Note: Clicking buttons will NOT do anything.</html>");
		add(instructionsLabel);
		add(inputArea);
	}
	
	public void initKeyboard(){
		panelB = new JPanel();
		panelB.setLayout(new GridLayout(4, 11));
		for(String c : keyboardStrings.split(" ")){
			characters.put(c, new JButton(c));
		}
		for(Map.Entry<String, JButton> e: characters.entrySet()){
			e.getValue().addKeyListener(this);
			e.getValue().setEnabled(false);
			panelB.add(e.getValue());
		}
		add(panelB, BorderLayout.SOUTH);
	}

	
	public static void main(String... args){
		@SuppressWarnings("unused")
		TypeTest tt = new TypeTest();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for(Map.Entry<String, JButton> ent: characters.entrySet()){
			if(Character.toString(e.getKeyChar()).equals(ent.getKey().toLowerCase()) || Character.toString(e.getKeyChar()).equals(ent.getKey().toUpperCase())){
				ent.getValue().setBackground(Color.YELLOW);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE){
				characters.get("Sp").setBackground(Color.YELLOW);
			} else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
				characters.get("Sh").setBackground(Color.YELLOW);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for(Map.Entry<String, JButton> ent: characters.entrySet()){
			if(Character.toString(e.getKeyChar()).equals(ent.getKey().toLowerCase()) || Character.toString(e.getKeyChar()).equals(ent.getKey().toUpperCase())){
				ent.getValue().setBackground(new JButton().getBackground());
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE){
				characters.get("Sp").setBackground(new JButton().getBackground());
			} else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
				characters.get("Sh").setBackground(new JButton().getBackground());
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//unused
	}
	
}
