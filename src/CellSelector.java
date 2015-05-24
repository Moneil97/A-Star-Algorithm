import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CellSelector extends JPanel{

	public CellSelector() {
		
		setPreferredSize(new Dimension(130,120));
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				if (green.contains(e.getPoint()))
					selected = green;
				if (red.contains(e.getPoint()))
					selected = red;
				if (black.contains(e.getPoint()))
					selected = black;
				
				repaint();
				
			}
			
		});
	}
	
	Rectangle green = new Rectangle(20, 20, 20, 20);
	Rectangle red = new Rectangle(20, 50, 20, 20);
	Rectangle black = new Rectangle(20, 80, 20, 20);
	
	Rectangle selected = black;
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setColor(Color.green);
		g.fill(green);
		g.setColor(Color.red);
		g.fill(red);
		g.setColor(Color.black);
		g.fill(black);
		
		g.setColor(selected == green? Color.black: Color.LIGHT_GRAY);
		g.drawRect(15, 14, 100, 29);
		g.setColor(selected == red? Color.black: Color.LIGHT_GRAY);
		g.drawRect(15, 45, 100, 29);
		g.setColor(selected == black? Color.black: Color.LIGHT_GRAY);
		g.drawRect(15, 76, 100, 29);
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		
		g.drawString("Start", 50, 37);
		g.drawString("End", 50, 67);
		g.drawString("Barrier", 50, 97);
		
	}

	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.add(new CellSelector());
//		frame.setSize(130,120);
//		frame.setLocationRelativeTo(null);
//		frame.setUndecorated(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//	}

}
