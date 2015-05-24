import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel{

	public MenuPanel() {
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				Runner.readyButton.updatePressed(e);
				repaint();
			}
			
		});
		
		addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				Runner.readyButton.updateHover(e);
				repaint();
			}
			
		});
		
	}
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		
		Graphics2D g = (Graphics2D) g1;
		
		g.setColor(Color.blue);
		
		for (int w=0; w<Runner.screenSize.width; w+=Runner.currentGridSize){
			g.drawLine(w, 0, w, Runner.screenSize.height);
		}
		
		for (int h=0; h<Runner.screenSize.height; h+=Runner.currentGridSize){
			g.drawLine(0, h, Runner.screenSize.width, h);
		}
		
		Runner.readyButton.draw(g);
	}

}
