/*Kevin Kinney
 *Mrs. Gallatin
 *3/23/18
 */
/**************************SETTINGS WINDOW ********************************************/
import javax.swing.*;
import javax.swing.event.*;	
import java.awt.event.*;
import java.awt.*;	
import java.util.*;
/**
 * SettingsWindow displays a window for the user to manipulate the simulation. Implements Item, Action, and Change Listener and extends JFrame
 */
public class SettingsWindow extends JFrame implements ItemListener, ActionListener, ChangeListener
{
	public static final int BASE_WIDTH = 300;
	public static final int BASE_HEIGHT = 300;
	public static final int WIDTH = (int)(BASE_WIDTH * GravitySim.SCALE);
	public static final int HEIGHT = (int)(BASE_HEIGHT * GravitySim.SCALE);
	
	private GravityComp sim;
	private JCheckBox sunFixBox, showTrailsBox, showVelBox, showAccBox;
	private JSlider speedSlider;
	private JButton save;
	
	/**
	 * Constructs a settings window with the given reference to the simulation.
	 * @param gc the GravityComp Simulation
	 */
	public SettingsWindow(GravityComp gc)
	{
		sim = gc;
		setLocationRelativeTo(null);
		setSize(WIDTH, HEIGHT);
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5, 1));

		LinkedList<JLabel> labels = new LinkedList<>();
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(1, 3));
		titlePanel.add(new JLabel());
		labels.add(new JLabel("Settings"));
		titlePanel.add(labels.peekLast());
		titlePanel.add(new JLabel());
		add(titlePanel);
		
		JPanel checkBoxes = new JPanel();
		checkBoxes.setLayout(new GridLayout(2, 2));
		ArrayList<JCheckBox> boxes = new ArrayList<>();
		
		sunFixBox = new JCheckBox("Fixed Sun", sim.getFixedSun());
		sunFixBox.addItemListener(this);
		checkBoxes.add(sunFixBox);
		boxes.add(sunFixBox);
		
		showTrailsBox = new JCheckBox("Show Trails", sim.getTrailsOn());
		showTrailsBox.addItemListener(this);
		checkBoxes.add(showTrailsBox);
		boxes.add(showTrailsBox);
		
		showVelBox = new JCheckBox("Show Velocity", sim.getShowVel());
		showVelBox.addItemListener(this);
		checkBoxes.add(showVelBox);
		boxes.add(showVelBox);
		
		showAccBox = new JCheckBox("Show Acceleration", sim.getShowAcc());
		showAccBox.addItemListener(this);
		checkBoxes.add(showAccBox);
		boxes.add(showAccBox);
		
		add(checkBoxes);
		
		for(JCheckBox b:boxes){
			b.setFont(GravitySim.DIALOG_FONT);
		}
		
		
		labels.add(new JLabel("Set Speed", JLabel.CENTER));
		add(labels.peekLast());
		
		speedSlider = new JSlider(JSlider.HORIZONTAL, 5, BodyRunner.MAX_SPEED, (int)(sim.getSpeed()));
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(this);
		add(speedSlider);
		
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1, 3));
		savePanel.add(new JLabel());
		save = new JButton("Save");
		save.addActionListener(this);
		save.setFont(GravitySim.DIALOG_FONT);
		savePanel.add(save);
		savePanel.add(new JLabel());
		add(savePanel);
		save.setBackground(Color.gray);
		save.setForeground(Color.white);

		for(JLabel lb: labels){
			lb.setFont(GravitySim.DIALOG_FONT);
		}

		setVisible(true);
	}
	
	/**
	 * Method for ItemListener, handles ItemEvent.
	 * @param e the ItemEvent to handle.
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getItemSelectable() == showTrailsBox) {
			sim.setTrailsOn(e.getStateChange() == ItemEvent.SELECTED);
		}
		else if(e.getItemSelectable() == sunFixBox) {
			sim.setFixedSun(e.getStateChange() == ItemEvent.SELECTED);
		}
		else if(e.getItemSelectable() == showVelBox) {
			sim.setShowVel(e.getStateChange() == ItemEvent.SELECTED);
		}
		else if(e.getItemSelectable() == showAccBox) {
			sim.setShowAcc(e.getStateChange() == ItemEvent.SELECTED);
		}
	}
	/**
	 * Method for ActionListener, handles ActionEvent.
	 * @param e the Action to handle.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == save){
			this.dispose();
		}
	}
	/**
	 * Method for ChangeListener, handles ChangeEvent.
	 * @param ItemEvent e the ChangeEvent to handle.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		JSlider source = (JSlider)e.getSource();
		if(!source.getValueIsAdjusting()){
			int d = (int)source.getValue();
			sim.setSpeed(d);
		}
	}
	public static void main (String[] args) {
		new GravitySim();
	}
}