import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PathFindingCenterPanel extends JPanel{

	public PathFindingCenterPanel() {
		
		
		
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
