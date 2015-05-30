import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;


@SuppressWarnings("serial")
abstract public class EditorBottomPanel extends JPanel {
	
	JButton startButton;
	public abstract void onStart();
	public abstract void onStop();

	public EditorBottomPanel() {
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		JLabel delayLabel = new JLabel("Delay:");
		add(delayLabel, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel currentDelayLabel = new JLabel(Runner.currentDelay > 1000 ? Runner.currentDelay/1000.0 + "s" : Runner.currentDelay + "ms");
		panel.add(currentDelayLabel, BorderLayout.WEST);
		currentDelayLabel.setPreferredSize(new Dimension(50,0));
		
		startButton = new JButton("Start");
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startButton.getText().equals("Start"))
					onStart();
				else if (startButton.getText().equals("Stop"))
					onStop();
				else
					System.err.println("Could not find: " + startButton.getText());
			}
			
		});
		
		panel.add(startButton, BorderLayout.EAST);
		
		JSlider delaySlider = new JSlider();
		delaySlider.setMinimum(0);
		delaySlider.setMaximum(1000);
		delaySlider.setValue(Runner.currentDelay);
		delaySlider.setMinorTickSpacing(1);
		delaySlider.setMajorTickSpacing(10);
		delaySlider.setPaintTicks(true);
		
		delaySlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Runner.currentDelay = delaySlider.getValue();
				currentDelayLabel.setText(Runner.currentDelay > 1000 ? Runner.currentDelay/1000.0 + "s" : Runner.currentDelay + "ms");
				//SwingUtilities.getRoot(EditorBottomPanel.this).repaint();
			}
			
		});
		
		add(delaySlider, BorderLayout.CENTER);
		
	}

	

}
