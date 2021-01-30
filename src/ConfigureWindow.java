/*Kevin Kinney
 *Mrs. Gallatin
 *3/23/18
 */
/**************************CONFIGURE WINDOW ********************************************/
import javax.swing.*;
import javax.swing.event.*;	
import java.awt.event.*;
import java.awt.*;	
import java.util.*;
/**
 * SettingsWindow displays a window for the user to manipulate the simulation. Implements Item, Action, and Change Listener and extends JFrame
 */
public class ConfigureWindow extends JFrame implements ItemListener, ActionListener
{
	public static final int BASE_WIDTH = 300;
	public static final int BASE_HEIGHT = 300;
	public static final int WIDTH = (int)(BASE_WIDTH * GravitySim.SCALE);
	public static final int HEIGHT = (int)(BASE_HEIGHT * GravitySim.SCALE);
	
	private GravityComp sim;
	private GravitySim window;
	private ButtonGroup bodySelectGroup;
	private JPanel optionPanel;
	private LinkedList<JRadioButton> options;
	private JButton save;
	private JComboBox<String> scenarios;
	
	/**
	 * Constructs a settings window with the given reference to the simulation.
	 * @param gc the GravityComp Simulation
	 */
	public ConfigureWindow(GravityComp gc, GravitySim w)
	{
		sim = gc;
		window = w;
		setLocationRelativeTo(null);
		setSize(WIDTH, HEIGHT);
		setTitle("Conditions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(6, 1));
		
		bodySelectGroup = new ButtonGroup();
		optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(1, 4));
		JLabel numBod = new JLabel("NUMBER OF BODIES", JLabel.CENTER);
		numBod.setFont(GravitySim.DIALOG_FONT);
		add(numBod);
		
		options = new LinkedList<>();
		
		options.add(new JRadioButton("1", sim.getNumBodies()==1));
		options.add(new JRadioButton("2", sim.getNumBodies()==2));
		options.add(new JRadioButton("3", sim.getNumBodies()==3));
		options.add(new JRadioButton("4", sim.getNumBodies()==4));
		String s = sim.getScenario();
		for(JRadioButton b:options)
		{
			b.setFont(GravitySim.DIALOG_FONT);
			bodySelectGroup.add(b);
			optionPanel.add(b);
			b.addItemListener(this);
			if(!(s.equals(GravityComp.PLANETS) || s.equals(GravityComp.CUSTOM))) {
				b.setEnabled(false);
			}
		}
		add(optionPanel);
		
		
		String[] cOps = window.getCustomScenarios();
		String[] options = new String[GravityComp.OPTIONS.length + cOps.length];
		for(int i=0;i<GravityComp.OPTIONS.length;i++)
			options[i] = GravityComp.OPTIONS[i];
		for(int i=0;i<cOps.length;i++)
			options[i+GravityComp.OPTIONS.length] = cOps[i];
		scenarios = new JComboBox<String>(options);
		scenarios.setSelectedItem(sim.getScenario());
		scenarios.addActionListener(this);
		scenarios.setFont(GravitySim.DIALOG_FONT);
		add(new JLabel());
		add(scenarios);
		add(new JLabel());
		
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1, 3));
		savePanel.add(new JLabel());
		save = new JButton("Save");
		save.addActionListener(this);
		save.setBackground(Color.gray);
		save.setForeground(Color.white);
		save.setFont(GravitySim.DIALOG_FONT);
		savePanel.add(save);
		savePanel.add(new JLabel());
		add(savePanel);
		
		setVisible(true);
	}
	
	/**
	 * Method for ItemListener, handles ItemEvent.
	 * @param e the ItemEvent to handle.
	 */
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getStateChange() == ItemEvent.SELECTED){
			ItemSelectable i = e.getItemSelectable();
			String text = ((JRadioButton)i).getText();
			switch(text){
				case "1":
					sim.setNumBodies(1);
					break;
				case "2":
					sim.setNumBodies(2);
					break;
				case "3":
					sim.setNumBodies(3);
					break;
				case "4":
					sim.setNumBodies(4);
					break;
			}	
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
		
		else if(e.getSource() == scenarios) {
			JComboBox jc = (JComboBox)e.getSource();
			String s = (String)jc.getSelectedItem();
			sim.setScenario(s);
			
			boolean buttonEdit = window.isCustomScenario(s) || s.equals(GravityComp.PLANETS) || s.equals(GravityComp.CUSTOM);
			
			
			for(JRadioButton b:options)
				b.setEnabled(buttonEdit);
			
			for(JRadioButton b:options) {
				if(b.getText().equals(""+sim.getNumBodies()))
					b.setSelected(true);
			}
			
		}
	}
	public static void main (String[] args) {
		new GravitySim();
	}
}