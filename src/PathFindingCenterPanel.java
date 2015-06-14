import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PathFindingCenterPanel extends JPanel{

	Cell endCell;
	private Thread thread;
	
	public PathFindingCenterPanel() {
		
		thread = new Thread(new Runnable() {
			
			PriorityQueue<Cell> open = new PriorityQueue<Cell>();
			List<Cell> closed = new ArrayList<Cell>();
			
			@Override
			public void run() {
				
				try{
					
					long startTime = System.nanoTime();

					endCell = getEndCell();
					
					if (endCell == null){
						JOptionPane.showMessageDialog(PathFindingCenterPanel.this, "No Available End Point");
						return;
					}
					
					Cell start = getStartCell();
					
					if (start == null){
						JOptionPane.showMessageDialog(PathFindingCenterPanel.this, "No Available Start Point");
						return;
					}
					
					start.gCost = 0;
					start.hCost = getCostBetween(start, endCell);
					open.add(start);
	
					while (true){
					
						Cell current = open.poll();
						
						if (current == null){
							JOptionPane.showMessageDialog(PathFindingCenterPanel.this, "No Path Found");
							return;
						}
						
						closed.add(current);
						current.setStatus(CellStatus.CLOSED);
						
						if (current.getType() == CellTypes.END){
	
							repaint();
							
							Cell cell = endCell;
							cell.setStatus(CellStatus.FINAL);
							
							do{
								cell = cell.parentCell;
								cell.setStatus(CellStatus.FINAL);
								
								
								//say(cell);
								//repaint();
							}while(cell.parentCell != null);
							
							say("It took: " + ((System.nanoTime() - startTime)/1000000000.0) + " sec");
							
							repaint();
							
							return;
						}
						
						
						for (int r=Math.max(0, current.getRow()-1); r <= Math.min(Runner.cells.length-1, current.getRow()+1); r++){
							for (int c=Math.max(0, current.getCol()-1); c <= Math.min(Runner.cells[r].length-1 ,current.getCol()+1); c++){
								
								Cell neighbor = Runner.cells[r][c];
								
								if (checkIfCorner(current, neighbor))
									continue;
								
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
									
									open.remove(neighbor);
									neighbor.fCost = neighbor.gCost + neighbor.hCost;
									open.add(neighbor);
									
								}
							}
						}
						
						repaint();
						
						if (Runner.currentDelay != 0){
							Thread.sleep(Runner.currentDelay);
						}
						else if (thread.isInterrupted())
							return;
						
					}
					
				}
				catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				
			}
			
			//gCost -dist from start //needs to get gCost of parent and add to it
			//hCost -dist to end //should stay same
			//fCost -total

			private boolean checkIfCorner(Cell current, Cell neighbor) {
				
				List<Cell> currentBarriers = new ArrayList<Cell>();
				
				for (Cell cell : getAdjacent(current)){
					if (cell.getType() == CellTypes.BARRIER)
						currentBarriers.add(cell);
				}
				
				if (currentBarriers.size() < /*2*/1)
					return false;
				
				List<Cell> neighborBarriers = new ArrayList<Cell>();
				
				for (Cell cell : getAdjacent(neighbor)){
					if (cell.getType() == CellTypes.BARRIER)
						neighborBarriers.add(cell);
				}
				
				if (neighborBarriers.size() < /*2*/1)
					return false;
				
				int similar = 0;
				
				for (Cell a : currentBarriers)
					for (Cell b : neighborBarriers)
						if (a == b){
							similar++;
							if (similar >= /*2*/1)
								return true;
							break;
						}
				
				return false;//similar >=2;
				
			}
			
			private List<Cell> getAdjacent(Cell cell){
				List<Cell> cells = new ArrayList<Cell>();
				int row = cell.getRow(), col = cell.getCol();
				
				int r[] = {row,row,row+1,row-1};
				int c[] = {col+1,col-1,col,col};
					
					for (int i=0; i < 4; i++)
						if (r[i] >= 0 && r[i] < Runner.cells.length && c[i] >= 0 && c[i] < Runner.cells[r[i]].length)
							cells.add(Runner.cells[r[i]][c[i]]);
				
				return cells;
			}
			
			private double getCostBetween(Cell a, Cell b){
				int height = Math.abs(a.getRow() - b.getRow());
				int length = Math.abs(a.getCol() - b.getCol());
				double distance = Math.sqrt(length*length + height*height);
				return distance;
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
			
		});
		
		thread.start();
		
	}
	
	public void stop(){

		thread.interrupt();

		for (Cell[] row : Runner.cells)
			for (Cell cell : row)
				cell.reset();

	}
	
//	private void print2D(Object[][] array){
//		for (Object[] row : array)
//		    System.out.println(Arrays.toString(row));
//	}
	
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
