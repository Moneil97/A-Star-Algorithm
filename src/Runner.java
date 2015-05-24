import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	int currentGridSize = 20;
	
	public Runner() {
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen.width*3/4, screen.height*3/4);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		final int buttonWidth = 100, buttonHeight = 40;
		SimpleAbstractButton readyButton = new SimpleAbstractButton("Ready", this.getWidth()/2-buttonWidth/2,this.getHeight()/2-buttonHeight/2,buttonWidth,buttonHeight,20,20,30) {
			
			@Override
			void onPress() {
				say("go");
			}
		};
		
		JPanel centerPanel = new JPanel(){
			
			@Override
			protected void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				
				Graphics2D g = (Graphics2D) g1;
				
				g.setColor(Color.blue);
				
				for (int w=0; w<screen.width; w+=currentGridSize){
					g.drawLine(w, 0, w, screen.height);
				}
				
				for (int h=0; h<screen.height; h+=currentGridSize){
					g.drawLine(0, h, screen.width, h);
				}
				
				readyButton.draw(g);
			}
			
		};
		
		centerPanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				readyButton.updatePressed(e);
				Runner.this.repaint();
			}
			
		});
		
		centerPanel.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				readyButton.updateHover(e);
				Runner.this.repaint();
			}
			
		});
		
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
