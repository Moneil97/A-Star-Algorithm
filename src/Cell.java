import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Cell {

	int x, y, size;
	boolean partOfPath = false;

	public Cell(int x, int y) {
		this(x,y,50);
	}
	
	public Cell(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void draw(Graphics2D g){
		g.setColor(partOfPath? Color.blue: !empty? Color.orange:Color.white);
		if (partOfPath || !empty)
			g.fillRect(x, y, size, size);
		else
			g.fillRect(x, y, size, size);
		g.setColor(Color.black);
		g.drawRect(x, y, size, size);
	}

	public boolean isEmpty() {
		return empty;
	}

	boolean empty = true;
	
	public void setPartOfPath(boolean b) {
		partOfPath = b;
	}

	public void setEmpty(boolean b) {
		empty = b;
	}

	public boolean click(MouseEvent e) {
		if (new Rectangle(x, y, size, size).contains(e.getPoint())){
			empty = !empty;
			return true;
		}
		return false;
	}

}