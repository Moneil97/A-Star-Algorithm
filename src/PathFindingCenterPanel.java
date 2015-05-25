import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PathFindingCenterPanel extends JPanel{

	Cell endCell;
	
	public PathFindingCenterPanel() {
		
		new Thread(new Runnable() {
			
			List<Cell> open = new ArrayList<Cell>();
			List<Cell> closed = new ArrayList<Cell>();
			
			@Override
			public void run() {

				//List<Cell> open = new ArrayList<Cell>();
				//List<Cell> closed = new ArrayList<Cell>();
				endCell = getEndCell();
				Cell start = getStartCell();
				start.gCost = 0;
				start.hCost = getCostBetween(start, endCell);
				open.add(start);
				if (open.get(0) == null){
					System.err.println("no start");
					return;
				}
				say(open);

				
				
				//start loop
				//for (int i=0; i < 4; i++){
				while (true){
				
					Cell current = getLowestOpenFCost();
					open.remove(current);
					closed.add(current);
					current.setStatus(CellStatus.CLOSED);
					
					if (current.getType() == CellTypes.END){
						say("we done");
						say(closed);
						repaint();
						
						//while (cell.)
						
						Cell cell = endCell.parentCell;
						cell.setStatus(CellStatus.FINAL);
						
						do{
							cell = cell.parentCell;
							cell.setStatus(CellStatus.FINAL);
							say(cell);
							repaint();
						}while(cell.parentCell != null);
						
						
						return;
					}
					
					
					for (int r=Math.max(0, current.getRow()-1); r <= Math.min(Runner.cells.length-1, current.getRow()+1); r++){
						for (int c=Math.max(0, current.getCol()-1); c <= Math.min(Runner.cells[r].length-1 ,current.getCol()+1); c++){
							
							Cell neighbor = Runner.cells[r][c];
							
							if ((neighbor.getType() == CellTypes.EMPTY || neighbor.getType() == CellTypes.END) && !closed.contains(neighbor)){
								
								neighbor.hCost = getCostBetween(neighbor, endCell);
								
								double newGCost = current.gCost + getCostBetween(current, neighbor);
								if (neighbor.gCost > newGCost){
									neighbor.gCost = newGCost;
									neighbor.parentCell = current;
									if (!open.contains(neighbor)){
										open.add(neighbor);
										neighbor.setStatus(CellStatus.OPEN);
									}
								}
								
								neighbor.fCost = neighbor.gCost + neighbor.hCost;
								
							}
							
							
						}
						
						repaint();
						
						try {
							Thread.sleep(Runner.currentDelay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				
				
				}//End loop
				
				
				
				
				
				
				
				
				
				
				/*print2D(Runner.cells);
				updateCostsAround(open.get(0));
				print2D(Runner.cells);
				
				say("");
				say("lowest: " + getLowestFCost());
				
				repaint();
				
				closed.add(start);
				
				say("lowest: " + getLowestFCost());*/
				
				
				
//				while (true){
//					
//					
//
//					try {
//						Thread.sleep(Runner.currentDelay);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				
				
			}
			
			//gCost -dist from start //needs to get gCost of parent and add to it
			//hCost -dist to end //should stay same
			//fCost -total

			private void updateCostsAround(Cell currentCell) {
				
				for (int r=Math.max(0, currentCell.getRow()-1); r <= Math.min(Runner.cells.length, currentCell.getRow()+1); r++)
					for (int c=Math.max(0, currentCell.getCol()-1); c <= Math.min(Runner.cells[r].length ,currentCell.getCol()+1); c++){
						
						if (!closed.contains(Runner.cells[r][c])){
						
							//say("r: " + r + " c: " + c);
							//TODO Need to fix this later, needs to check distance from parent cell and add it to parent's gCost //Done?
							double newGCost = currentCell.gCost + getCostBetween(currentCell, Runner.cells[r][c]);
							if (Runner.cells[r][c].gCost > newGCost){
								Runner.cells[r][c].gCost = newGCost;
								Runner.cells[r][c].parentCell = currentCell;
							}
							
							Runner.cells[r][c].hCost = getCostBetween(Runner.cells[r][c], endCell);
							
							Runner.cells[r][c].fCost = Runner.cells[r][c].hCost + Runner.cells[r][c].gCost;
						}
					}
						
				
			}
			
			private double getCostBetween(Cell a, Cell b){
				int height = Math.abs(a.getRow() - b.getRow());
				//say("height: " + height);
				int length = Math.abs(a.getCol() - b.getCol());
				//say("length: " + length);
				double distance = Math.sqrt(length*length + height*height);
				//say("distance: " + distance);
				return distance;
			}
			
			private Cell getLowestOpenFCost(){
				
				Cell lowest = open.get(0);
				
				for (Cell cell: open)
					if (lowest.fCost > cell.fCost)
						lowest = cell;
				
				return lowest;
					
				
//				Cell lowest = Runner.cells[0][0];
//				
//				for (Cell[] row : Runner.cells)
//					for (Cell cell : row)
//						if (lowest.fCost > cell.fCost)
//							lowest = cell;
//				
//				return lowest;
			}
			
			private Cell getStartCell(){
				for (Cell[] row : Runner.cells)
					for (Cell cell : row)
						if (cell.getType() == CellTypes.START)
							return cell;
				return null;
			}
			
			private Cell getEndCell(){
				for (Cell[] row : Runner.cells)
					for (Cell cell : row)
						if (cell.getType() == CellTypes.END)
							return cell;
				return null;
			}
			
			
		}).start();
		
	}
	
	private void print2D(Object[][] array){
		for (Object[] row : array)
		    System.out.println(Arrays.toString(row));
	}
	
	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		
		for (Cell[] row : Runner.cells)
			for (Cell cell : row)
				cell.draw(g);
		
	}
	
	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}

}
