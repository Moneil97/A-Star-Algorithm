import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class MenuBottomPanel extends JPanel{

	public MenuBottomPanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		
		JLabel gridSizeLabel = new JLabel("Grid Size:");
		add(gridSizeLabel, BorderLayout.WEST);
		
		JLabel currentgridSizeLabel = new JLabel(String.valueOf(Runner.currentGridSize));
		add(currentgridSizeLabel, BorderLayout.EAST);
		
		JSlider gridSizeSlider = new JSlider();
		gridSizeSlider.setMinimum(5);
		gridSizeSlider.setMaximum(100);
		gridSizeSlider.setValue(Runner.currentGridSize);
		gridSizeSlider.setMinorTickSpacing(1);
		gridSizeSlider.setMajorTickSpacing(10);
		gridSizeSlider.setPaintTicks(true);
		
		gridSizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Runner.currentGridSize = gridSizeSlider.getValue();
				currentgridSizeLabel.setText(String.valueOf(Runner.currentGridSize));
				SwingUtilities.getRoot(MenuBottomPanel.this).repaint();
			}
			
		});
		
		add(gridSizeSlider, BorderLayout.CENTER);
	}

}
