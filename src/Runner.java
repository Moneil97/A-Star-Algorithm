import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class Runner extends JFrame{

	Screen currentScreen = Screen.MENU;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int currentGridSize = 30;
	static Cell[][] cells;
	JPanel centerPanel;
	static SimpleAbstractButton readyButton;
	
	
	public Runner() {
		
		
		this.setSize(screenSize.width*3/4, screenSize.height*3/4);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		final int buttonWidth = 100, buttonHeight = 40;
		readyButton = new SimpleAbstractButton("Ready", this.getWidth()/2-buttonWidth/2,this.getHeight()/2-buttonHeight/2,buttonWidth,buttonHeight,20,20,30) {
			
			@Override
			void onPress() {
				
				int rows = centerPanel.getHeight()/currentGridSize;
				int cols = centerPanel.getWidth()/currentGridSize;
				
				cells = new Cell[rows][cols];
				
				for (int r=0; r < rows; r++)
					for (int c=0; c < cols; c++)
						cells[r][c] = new Cell(c*50,r*50,50);
				
				say("go");
			}
		};
		
		centerPanel = new MenuPanel();
		
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		bottomPanel.setLayout(new BorderLayout());
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JLabel gridSizeLabel = new JLabel("Grid Size:");
		bottomPanel.add(gridSizeLabel, BorderLayout.WEST);
		
		JLabel currentgridSizeLabel = new JLabel(String.valueOf(currentGridSize));
		bottomPanel.add(currentgridSizeLabel, BorderLayout.EAST);
		
		JSlider gridSizeSlider = new JSlider();
		gridSizeSlider.setMinimum(5);
		gridSizeSlider.setMaximum(100);
		gridSizeSlider.setValue(currentGridSize);
		gridSizeSlider.setMinorTickSpacing(1);
		gridSizeSlider.setMajorTickSpacing(10);
		gridSizeSlider.setPaintTicks(true);
		
		gridSizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				currentGridSize = gridSizeSlider.getValue();
				currentgridSizeLabel.setText(String.valueOf(currentGridSize));
				Runner.this.repaint();
			}
			
		});
		
		bottomPanel.add(gridSizeSlider, BorderLayout.CENTER);
		
		
		this.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//Fix Button Location once JFrame Loaded
				readyButton.setX(centerPanel.getWidth()/2-readyButton.getWidth()/2);
				readyButton.setY(centerPanel.getHeight()/2-readyButton.getHeight()/2);
			}
		});
		
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				//Re-center button
				readyButton.setX(centerPanel.getWidth()/2-readyButton.getWidth()/2);
				readyButton.setY(centerPanel.getHeight()/2-readyButton.getHeight()/2);
			}
			
		});
	}
	
	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}

	public static void main(String[] args) {
		new Runner();
	}

}
