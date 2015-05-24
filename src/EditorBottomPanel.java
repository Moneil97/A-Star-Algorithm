import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class EditorBottomPanel extends JPanel {

	public EditorBottomPanel() {
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		JLabel delayLabel = new JLabel("Delay:");
		add(delayLabel, BorderLayout.WEST);
		
		JLabel currentDelayLabel = new JLabel(Runner.currentDelay > 1000 ? Runner.currentDelay/1000.0 + "s" : Runner.currentDelay + "ms");
		currentDelayLabel.setPreferredSize(new Dimension(50,40));
		add(currentDelayLabel, BorderLayout.EAST);
		
		JSlider delaySlider = new JSlider();
		delaySlider.setMinimum(0);
		delaySlider.setMaximum(10000);
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
