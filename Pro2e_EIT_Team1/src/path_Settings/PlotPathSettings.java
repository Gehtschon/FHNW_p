package path_Settings;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import logger.Logger_Singleton;

public class PlotPathSettings extends JPanel implements I_Plot_Path_Settings {
	
	private JLabel lblConfiguration = new JLabel("Configuration:");
	private JLabel lblLog = new JLabel("Log:");
	private JLabel lblLogIntervall = new JLabel("Logintervall:");
	private JLabel lblEventExport = new JLabel("Eventexport:");
	private JLabel lblLogUnit = new JLabel("[s]");
	private JLabel lblEventUnit = new JLabel("[s]");
	private JTextField txtConfigPath = new JTextField();
	private JTextField txtLogPath = new JTextField();
	SpinnerNumberModel spinnerModelLog = new SpinnerNumberModel(60, 1,Integer.MAX_VALUE , 1); // from 1 to Integer.Max, in 1 steps start value 60
	SpinnerNumberModel spinnerModelEvent = new SpinnerNumberModel(60, 1,Integer.MAX_VALUE , 1); // from 1 to Integer.Max, in 1 steps start value 60
	private JSpinner spinnerLog = new JSpinner(spinnerModelLog);
	private JSpinner spinnerEvent = new JSpinner(spinnerModelEvent);
	private JButton btnLogPath = new JButton("...");
	private JButton btnConfigPath = new JButton("...");	
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");	

	private final JFileChooser fileChooser = new JFileChooser();
	private final Insets insets = new Insets(5, 5, 5, 5);
	private String logPath;
	private String configPath;
	private I_Plot_Path_Settings_Controller controller;
	private Frame pathframe;

	public PlotPathSettings(I_Plot_Path_Settings_Controller controller, Frame pathframe) {
		this.controller = controller;
		this.pathframe = pathframe;
		setPreferredSize(new Dimension(250,200));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Let the user only choose a direcotry
		initPlotPath();
	}
	
	private void initPlotPath() {
		
		txtLogPath.setPreferredSize(new Dimension(100, 20));
		txtConfigPath.setPreferredSize(new Dimension(100, 20));
		spinnerEvent.setMinimumSize(new Dimension(60,20));
		spinnerLog.setMinimumSize(new Dimension(60,20));
		
		setLayout(new GridBagLayout());
		add(lblConfiguration, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(txtConfigPath, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(btnConfigPath, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
		add(lblLog, new GridBagConstraints(0, 2, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(txtLogPath, new GridBagConstraints(0, 3, 2, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(btnLogPath, new GridBagConstraints(2, 3, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
		add(lblLogIntervall, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(spinnerLog, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
		add(lblLogUnit, new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(lblEventExport, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(spinnerEvent, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
		add(lblEventUnit, new GridBagConstraints(2, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(btnOk, new GridBagConstraints(0, 6, 2, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
		add(btnCancel, new GridBagConstraints(1, 6, 2, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));
		
		btnConfigPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Open FileChooser an let the user choose a directory for the Configs
				String path = selectPath();
				if(path!= null) {
					txtConfigPath.setText(path);
					configPath = path;
				}
			}
		});
		
		btnLogPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Open FileChooser an let the user choose a directory for the logs
				String path = selectPath();
				if(path!= null) {
					txtLogPath.setText(path);
					logPath = path;
				}
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//OK_Button clicked -> Give all informations to the PathController
				
				//Get the Path form the Textfields in case the user changed the path manually
				configPath = txtConfigPath.getText(); 
				logPath = txtLogPath.getText();
				
				//Check the validity of the paths and set them
				try {
					Paths.get(configPath);
					Paths.get(logPath);
					controller.setConfigPath(configPath);
					controller.setLogPath(logPath);
					controller.setlogInterval((int)spinnerLog.getValue());
					controller.setEventInterval((int)spinnerEvent.getValue());
					closeWindow();
				} catch (InvalidPathException ex) {
					ErrorMessageBox("Invalid Path", "Invalid Path insert: " + ex.getInput());
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Close window without giving information to the controller (hold the old information)
				closeWindow(); // No access to the frame 
			}
		});
		
	}
	
	/**
	 * Opens a fileChooser to let the user select a path
	 * @return String with the Path in it
	 */
	String selectPath() {
		try {
			if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				return(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}catch(Exception e){
			//Unhandled Exceptions
		}
		return(null);
	}
	
	/**
	 * Opens a ErrorMessageBox for the User
	 * @param boxtitle string with the title ot the box
	 * @param message message that should written in the box
	 */
	void ErrorMessageBox(String boxtitle ,String message) {
		JOptionPane.showMessageDialog(this,message,boxtitle,JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Close this window
	 */
	void closeWindow() {
		pathframe.dispose();
		System.out.println("Pathsettings:"+logPath);
		Logger_Singleton tmp = Logger_Singleton.getInstance();
		tmp.freereport("close window");
	}

	@Override
	public void setConfigPath(String path) {
		configPath = path;
		txtConfigPath.setText(path);
	}


	@Override
	public void setLogPath(String path) {
		logPath = path;
		txtLogPath.setText(path);
	}


	@Override
	public void setlogInterval(int value) {
		spinnerLog.setValue(value);
	}


	@Override
	public void setEventInterval(int value) {
		spinnerEvent.setValue(value);
	}
}
