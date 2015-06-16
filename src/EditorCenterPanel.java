import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class EditorCenterPanel extends JPanel{
	
	Cell lastClicked;

	public EditorCenterPanel() {

		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				press(e);
			}
			
		});
		
		
		addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				press(e);
			}
			
		});
		
	}
	
	Cell currentStart, currentEnd;
	
	private void press(MouseEvent e){
		
		if (Runner.currentCellTypeSelection == CellTypes.START && currentStart != null)
				currentStart.setType(CellTypes.EMPTY);
		else if (Runner.currentCellTypeSelection == CellTypes.END && currentEnd != null)
			currentEnd.setType(CellTypes.EMPTY);
		
		for (Cell[] row : Runner.cells)
			for (Cell cell : row)
				if (cell.contains(e)){
					
					cell.setType(Runner.currentCellTypeSelection);
					
					if (Runner.currentCellTypeSelection == CellTypes.START)
						currentStart = cell;
					else if (Runner.currentCellTypeSelection == CellTypes.END)
						currentEnd = cell;
				}
		
		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		
		for (Cell[] row : Runner.cells)
			for (Cell cell : row)
				cell.draw(g);
		
	}

}
