//Version 2 with scaling

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
/**
 * 
 * Class to display gravity sim window.
 *
 */
public class GravitySim extends JFrame implements ActionListener
{
	public static final int BASE_HEIGHT = 700;
	public static final int BASE_WIDTH = 900;
	public static final int S_HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.7);
	public static final int FRAME_HEIGHT = (int)((double)S_HEIGHT * Toolkit.getDefaultToolkit().getScreenResolution()/100);
	public static final double SCALE = (double)FRAME_HEIGHT / BASE_HEIGHT;
	public static final int FRAME_WIDTH = (int)(BASE_WIDTH * SCALE);

	public static String title = "Gravity Sim";
	public static String[] colors = {"RED", "BLUE", "YELLOW", "GREEN", "ORANGE"};
	public static final Font DIALOG_FONT = new Font("Dialog", Font.PLAIN, (int)(12*SCALE));

	private GravityComp sim;
	private ArrayList<JButton> buttons;
	private ArrayList<JTextField> fields;
	private JPanel controlPanel;
	private JPanel customPanel;
	private JButton apply, save;
	private ArrayList<JComboBox<String>> colorChoosers;
	private HashMap<String, ArrayList<Body>> saveData;
	
	/**
	 * Constructs and shows a Gravity Simulation window.
	 */
	public GravitySim()
	{
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setTitle(title);
		
		try {
			FileInputStream in = new FileInputStream("data.dat");
			ObjectInputStream obIn = new ObjectInputStream(in);
			HashMap<String, ArrayList<Body>> readObject = (HashMap<String, ArrayList<Body>>)obIn.readObject();
			saveData = readObject;
			obIn.close();
		}catch(Exception e) {
			saveData = new HashMap<>();
		}
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 3));
		
		buttons = new ArrayList<>();
		buttons.add(new JButton("STOP"));
		buttons.add(new JButton("START"));
		buttons.add(new JButton("RESET"));
		buttons.add(new JButton("SETTINGS"));
		buttons.add(new JButton("CONDITIONS"));
		
		for(JButton b:buttons){
			controlPanel.add(b);
		}
		
		customPanel = new JPanel();
		customPanel.setLayout(new GridLayout(5, 8));
		ArrayList<JLabel> cLabels = new ArrayList<>();
		apply = new JButton("Apply");
		apply.setFont(DIALOG_FONT);
		buttons.add(apply);
		customPanel.add(apply);
		
		cLabels.add(new JLabel("X"));
		cLabels.add(new JLabel("Y"));
		cLabels.add(new JLabel("VelX"));
		cLabels.add(new JLabel("VelY"));
		cLabels.add(new JLabel("Mass"));
		cLabels.add(new JLabel("Radius"));
		
		for(JLabel jl:cLabels) {
			customPanel.add(jl);
		}
		
		save = new JButton("Save Custom");
		buttons.add(save);
		customPanel.add(save);
		
		fields = new ArrayList<>();
		colorChoosers = new ArrayList<>();
		
		cLabels.add(new JLabel("Body1"));
		customPanel.add(cLabels.get(cLabels.size()-1));
		for(int i=0;i<6;i++) {
			fields.add(new JTextField());
			customPanel.add(fields.get(fields.size()-1));
		}
		colorChoosers.add(new JComboBox<String>(colors));
		customPanel.add(colorChoosers.get(colorChoosers.size()-1));
		
		cLabels.add(new JLabel("Body2"));
		customPanel.add(cLabels.get(cLabels.size()-1));
		for(int i=0;i<6;i++) {
			fields.add(new JTextField());
			customPanel.add(fields.get(fields.size()-1));
		}
		colorChoosers.add(new JComboBox<String>(colors));
		customPanel.add(colorChoosers.get(colorChoosers.size()-1));
		
		cLabels.add(new JLabel("Body3"));
		customPanel.add(cLabels.get(cLabels.size()-1));
		for(int i=0;i<6;i++) {
			fields.add(new JTextField());
			customPanel.add(fields.get(fields.size()-1));
		}
		colorChoosers.add(new JComboBox<String>(colors));
		customPanel.add(colorChoosers.get(colorChoosers.size()-1));
		
		cLabels.add(new JLabel("Body4"));
		customPanel.add(cLabels.get(cLabels.size()-1));
		for(int i=0;i<6;i++) {
			fields.add(new JTextField());
			customPanel.add(fields.get(fields.size()-1));
		}
		colorChoosers.add(new JComboBox<String>(colors));
		customPanel.add(colorChoosers.get(colorChoosers.size()-1));
		
		for(JLabel jl:cLabels) {
			jl.setHorizontalAlignment(JLabel.CENTER);
			jl.setFont(DIALOG_FONT);
		}
		for(JButton b:buttons){
			b.addActionListener(this);
			b.setBackground(Color.gray);
			b.setForeground(Color.white);
			b.setFont(DIALOG_FONT);
		}
		for(JTextField f:fields){
			f.addActionListener(this);
			f.setFont(DIALOG_FONT);
		}
		for(JComboBox<String> b:colorChoosers){
			b.addActionListener(this);
			b.setFont(DIALOG_FONT);
		}
		add(controlPanel, BorderLayout.NORTH);
		sim = new GravityComp(this);
		add(sim, BorderLayout.CENTER);
		add(customPanel, BorderLayout.SOUTH);
		showCustom(false);
		setVisible(true);
		getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		pack();
	}
	/**
	 * Shows a custom input panel if show is true
	 * @param show whether to show custom input panel.
	 */
	public void showCustom(boolean show)
	{
		customPanel.setVisible(show);
		if(show){
			ArrayList<Body> data = sim.getBodies();
			for(JTextField f:fields){
				f.setEnabled(false);
				f.setText("0");
			}
			for(JComboBox<String> cb:colorChoosers) {
				cb.setEnabled(false);
			}
			for(int i=0;i<sim.getNumBodies();i++) {
				colorChoosers.get(i).setEnabled(true);
				Body b = data.get(i);
				String c = "RED";
				Color cl = b.getColor();
				if(cl.equals(Color.RED)) {
					c = "RED";
				}
				else if(cl.equals(Color.BLUE)) {
					c = "BLUE";
				}
				else if(cl.equals(Color.YELLOW)) {
					c = "YELLOW";
				}
				else if(cl.equals(Color.GREEN)) {
					c = "GREEN";
				}
				else if(cl.equals(Color.ORANGE)) {
					c = "ORANGE";
				}
				
				colorChoosers.get(i).setSelectedItem(c);
				int j = i*6;
				fields.get(j).setEnabled(true);
				fields.get(j).setText(""+(int)(b.getX()*Math.pow(10, 4) +.5)/Math.pow(10,4));
				
				fields.get(j+1).setEnabled(true);
				fields.get(j+1).setText(""+(int)(b.getY()*Math.pow(10, 4) +.5)/Math.pow(10,4));
				
				fields.get(j+2).setEnabled(true);
				fields.get(j+2).setText(""+(int)(b.getVX()*Math.pow(10, 4) +.5)/Math.pow(10,4));
				
				fields.get(j+3).setEnabled(true);
				fields.get(j+3).setText(""+(int)(b.getVY()*Math.pow(10, 4) +.5)/Math.pow(10,4));
				
				fields.get(j+4).setEnabled(true);
				fields.get(j+4).setText(""+(int)(b.getMass()*Math.pow(10, 4) +.5)/Math.pow(10,4));
				
				fields.get(j+5).setEnabled(true);
				fields.get(j+5).setText(""+ (int)(b.getRadius()*Math.pow(10, 4) +.5)/Math.pow(10,4));
				
			}

		}
	}
	/**
	 * Returns data in custom panel
	 * @return data in custom panel
	 */
	public ArrayList<Body> getCustomData()
	{
		ArrayList<Body> ret = new ArrayList<>();
		for(int i=0;i<sim.getNumBodies();i++)
		{
			int j = i*6;
			double x = Double.parseDouble(fields.get(j).getText());
			double y = Double.parseDouble(fields.get(j+1).getText());
			double vx = Double.parseDouble(fields.get(j+2).getText());
			double vy = Double.parseDouble(fields.get(j+3).getText());
			double m = Double.parseDouble(fields.get(j+4).getText());
			double r = Double.parseDouble(fields.get(j+5).getText());
			Color c;
			switch((String)colorChoosers.get(i).getSelectedItem()){
				case "RED":
					c = Color.red;
					break;
				case "BLUE":
					c = Color.blue;
					break;
				case "GREEN":
					c = Color.green;
					break;
				case "YELLOW":
					c = Color.yellow;
					break;
				case "ORANGE":
					c = Color.orange;
					break;
				default:
					c = Color.white;
					break;
			}
			Body add = new Body(x, y, r, m, c);
			add.setInitialVelocity(vx, vy);
			ret.add(add);
		}
		return ret;
	}
	public void saveData()
	{
		sim.stop();
		String sc;
		if(saveData.containsKey(sim.getScenario())) {
			saveData.put(sim.getScenario(), getCustomData());
			sc = sim.getScenario();
		}
		else {
			String name = JOptionPane.showInputDialog("Name your custom scenario");
			saveData.put(name, getCustomData());
			sc = name;
		}
		try {
			FileOutputStream write = new FileOutputStream("data.dat");
			ObjectOutputStream outPut = new ObjectOutputStream(write);
			outPut.writeObject(saveData);
			outPut.close();
			write.close();
		}catch(Exception e) {
			
		}
		JOptionPane.showMessageDialog(null, "Saved");
		sim.setScenario(sc);
	}
	public String[] getCustomScenarios()
	{
		String[] ret = new String[saveData.size()];
		ArrayList<String> names = new ArrayList<>();
		names.addAll(saveData.keySet());
		for(int i=0;i<ret.length;i++)
			ret[i] = names.get(i);
		return ret;
	}
	/**
	 * runs program
	 * @param args
	 */
	public static void main (String[] args) 
	{
		new GravitySim();
	}
	/**
	 * Applies custom data to simulation
	 */
	public void apply()
	{
		sim.applyCustom(getCustomData());
	}
	public boolean isCustomScenario(String s)
	{
		return saveData.containsKey(s);
	}
	public ArrayList<Body> getCustomScenarioData(String s)
	{
		return saveData.get(s);
	}
	/**
	 * Handles action events
	 * @param e action event to handle
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof JButton){
			switch(((JButton)e.getSource()).getText()){
				case "STOP":
					sim.stop();
					break;
				case "RESET":
					sim.reset();
					break;
				case "START":
					sim.start();
					break;
				case "SETTINGS":
					sim.settings();
					break;
				case "CONDITIONS":
					sim.configure();
					break;
				case "Apply":
					apply();
					break;
				case "Save Custom":
					saveData();
					break;
			}
		}
		if(e.getSource() instanceof JTextField ) {
			apply();
		}
	}
}