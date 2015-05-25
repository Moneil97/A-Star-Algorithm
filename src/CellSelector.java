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
				
				if (startOutline.contains(e.getPoint()))
					Runner.currentCellTypeSelection = CellTypes.START;
				if (stopOutline.contains(e.getPoint()))
					Runner.currentCellTypeSelection = CellTypes.STOP;
				if (barrierOutline.contains(e.getPoint()))
					Runner.currentCellTypeSelection = CellTypes.BARRIER;
				if (emptyOutline.contains(e.getPoint()))
					Runner.currentCellTypeSelection = CellTypes.EMPTY;
				
				repaint();
				
			}
			
		});
	}
	
	Rectangle start = new Rectangle(20, 20, 20, 20);
	Rectangle stop = new Rectangle(20, 50, 20, 20);
	Rectangle barrier = new Rectangle(20, 80, 20, 20);
	Rectangle empty = new Rectangle(20, 110, 20, 20);
	
	Rectangle startOutline = new Rectangle(15, 14, 100, 29);
	Rectangle stopOutline = new Rectangle(15, 45, 100, 29);
	Rectangle barrierOutline = new Rectangle(15, 76, 100, 29);
	Rectangle emptyOutline = new Rectangle(15, 107, 100, 29);
	
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		boolean active = Runner.currentScreen == Screen.EDITOR;
		if (!active) g.setColor(Color.LIGHT_GRAY);
		
		//Draw Rectangles
		if (active) g.setColor(CellTypes.START.color);
		g.fill(start);
		if (active) g.setColor(CellTypes.STOP.color);
		g.fill(stop);
		if (active) g.setColor(CellTypes.BARRIER.color);
		g.fill(barrier);
		if (active) g.setColor(CellTypes.EMPTY.color);
		g.fill(empty);
		
		//Draw Outlines
		if (active) g.setColor(Color.black);
		g.draw(start);
		g.draw(stop);
		g.draw(barrier);
		g.draw(empty);
		
		//Draw Labels
		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString("Start", 50, 37);
		g.drawString("End", 50, 67);
		g.drawString("Barrier", 50, 97);
		g.drawString("Empty", 50, 127);
		
		//Draw Borders
		if (active) g.setColor(Runner.currentCellTypeSelection == CellTypes.START? Color.black: Color.LIGHT_GRAY);
		g.draw(startOutline);
		if (active) g.setColor(Runner.currentCellTypeSelection == CellTypes.STOP? Color.black: Color.LIGHT_GRAY);
		g.draw(stopOutline);
		if (active) g.setColor(Runner.currentCellTypeSelection == CellTypes.BARRIER? Color.black: Color.LIGHT_GRAY);
		g.draw(barrierOutline);
		if (active) g.setColor(Runner.currentCellTypeSelection == CellTypes.EMPTY? Color.black: Color.LIGHT_GRAY);
		g.draw(emptyOutline);
		
	}

	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}

}
