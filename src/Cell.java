import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Cell implements Comparable<Cell>{

	int x, y, size;
	boolean partOfPath = false;
	private CellTypes type = CellTypes.EMPTY;
	private int row;
	private int col;
	double gCost = Double.MAX_VALUE, hCost = Double.MAX_VALUE, fCost = Double.MAX_VALUE;
	Cell parentCell;
	CellStatus status = CellStatus.NONE;
	

//	public Cell(int x, int row, int col, int y) {
//		this(x,y, row, col, 50);
//	}
	
	public Cell(int x, int y, int row, int col, int size) {
		this.x = x;
		this.y = y;
		this.setRow(row);
		this.setCol(col);
		this.size = size;
	}
	
	public void draw(Graphics2D g){
		if (status == CellStatus.NONE)
			g.setColor(type.color);
		else if (status == CellStatus.OPEN)
			g.setColor(Color.cyan);
		else if (status == CellStatus.CLOSED)
			g.setColor(Color.orange);
		else if (status == CellStatus.FINAL)
			g.setColor(Color.magenta);
		g.fillRect(x, y, size, size);
		g.setColor(Color.black);
		g.drawRect(x, y, size, size);
		
//		if (gCost < Double.MAX_VALUE)
//			g.drawString(String.format("%.1f", gCost), x+2, y+12);
//		
//		if (hCost < Double.MAX_VALUE)
//			g.drawString(String.format("%.1f", hCost), x+size-20, y+12);
//		
//		if (fCost < Double.MAX_VALUE)
//			g.drawString(String.format("%.1f", fCost), x+size/2-10, y+size/2);
		
	}
	
	public CellTypes getType() {
		return type;
	}

	public void setType(CellTypes type) {
		this.type = type;
	}
	
	public boolean contains(MouseEvent e) {
		return new Rectangle(x, y, size, size).contains(e.getPoint());
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	@Override
	public String toString() {
		return "Cell: [x: " + x + " y: " + y + " row: " + row + " col: " + col + " type: " + type + " gCost: " + gCost + " hCost: " + hCost + " fCost: " + fCost + "]";
	}

	public void setStatus(CellStatus status) {
		this.status = status;
	}
	
	public void reset(){
		status = CellStatus.NONE;
		gCost = hCost = fCost = Double.MAX_VALUE;
	}

	@Override
	public int compareTo(Cell other) {
		if (other.fCost > this.fCost)
			return -1;
		if (other.fCost < this.fCost)
			return 1;
		
		return 0;
	}

}