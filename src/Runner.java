import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings({ "serial", "unused" })
public class Runner extends JFrame{

	static Screen currentScreen = Screen.MENU;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int currentGridSize = 30;
	static Cell[][] cells;
	JPanel centerPanel, bottomPanel;
	static SimpleAbstractButton readyButton;
	static int currentDelay = 200;
	static CellTypes currentCellTypeSelection = CellTypes.BARRIER;
	
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
						cells[r][c] = new Cell(c*currentGridSize, r*currentGridSize, r, c, currentGridSize);
				
				getContentPane().remove(centerPanel);
				centerPanel = new EditorCenterPanel();
				getContentPane().add(centerPanel, BorderLayout.CENTER);
				
				getContentPane().remove(bottomPanel);
				bottomPanel = new EditorBottomPanel(){

					@Override
					public void onStart() {
						
						currentScreen = Screen.RUN;
						
						getContentPane().remove(centerPanel);
						centerPanel = new PathFindingCenterPanel();
						getContentPane().add(centerPanel, BorderLayout.CENTER);
						
						revalidate();
						Runner.this.repaint();
						startButton.setText("Stop");
					}

					@Override
					public void onStop() {
						
					}
					
				};
				
				getContentPane().add(bottomPanel, BorderLayout.SOUTH);
				
				currentScreen = Screen.EDITOR;
				setResizable(false);
				
				revalidate();
				repaint();
			}
		};
		
		centerPanel = new MenuPanel();
		
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		bottomPanel = new MenuBottomPanel();
	
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		
		JPanel sidePanel = new CellSelector();
		
		getContentPane().add(sidePanel, BorderLayout.EAST);
		
		
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
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Could not set look and feel!");
		}
		
		new Runner();
	}

}
