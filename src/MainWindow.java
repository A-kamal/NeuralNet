import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.JMenu;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;


public class MainWindow implements ItemListener {

	private JFrame frame;
	private static JTextField inputField;
	private static JTextField outputField;
	private static JTextField hiddenField;
	private ItemListener item;
	private JButton insertInputs,getOutputs;
	private int hiddenLayerCount,neuronCount,outputCount,inputCount;
	private NeuralNet net;
	private double[] inputs;
	private int[] neurons;
	private JFileChooser fc;
	private JLabel lblNewLabel_1;
	private JCheckBox fromFile;
	private static MainWindow window;
	private String directory,read,result;
	private String[] inputFromFile;
	private JPanel panel_3;
	private JTabbedPane tabbedPane;
	private JPanel trainingpanel;
	private JLabel lblNewLabel_2;
	private JButton trainInbut;
	private JLabel lblTrainingOutputs;
	private JButton trainOutbut;
	private JLabel lblLearningRate;
	private JTextField learningField;
	private JLabel lblError;
	private JTextField errorField;
	private JLabel lblMaxNumberOf;
	private JTextField iterationField;
	private double[][] trainin,trainout;
	private JButton btnCreateStructure;
	private JButton btnTrain;
	public static JProgressBar progressBar;
	private JComboBox comboBox;
	private JLabel lblActivationFunction;
	private JButton btnInsertNumberOf;
	private JButton btnNewButton;
	private JLabel lblMomentum;
	private JLabel lblTau;
	private JLabel lblC;
	private JLabel lblAdaptation;
	private JLabel lblN;
	private JComboBox comboBox_1;
	private JLabel lblLearningMethod;
	private JTextField momentumField;
	private JTextField tauField;
	private JTextField cField;
	private JComboBox comboBox_2;
	private JTextField n0Field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainWindow();
					window.frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
		
		}
		
	
	public int returnInt(JTextField t){ //Takes a textfield object and returns the integer in it
		int number = Integer.parseInt(t.getText());
		return number;
		
	}
	public double returnDouble(JTextField t){ //Takes a textfield object and returns the integer in it
		double number = Double.parseDouble(t.getText());
		return number;
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 750, 348);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel simulationpanel = new JPanel();
		tabbedPane.addTab("Simulation", null, simulationpanel, null);
		simulationpanel.setBorder(new CompoundBorder());
		GridBagLayout gbl_simulationpanel = new GridBagLayout();
		gbl_simulationpanel.columnWidths = new int[]{126, 108, 112, 167, 0, 0};
		gbl_simulationpanel.rowHeights = new int[]{22, 20, 0, 0, 0, 0};
		gbl_simulationpanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_simulationpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		simulationpanel.setLayout(gbl_simulationpanel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		simulationpanel.add(panel_1, gbc_panel_1);
		
		JLabel lblNewLabel = new JLabel("Inputs: ");
		panel_1.add(lblNewLabel);
		
		inputField = new JTextField();
		inputField.setText("1");
		
		GridBagConstraints gbc_inputField = new GridBagConstraints();
		gbc_inputField.anchor = GridBagConstraints.WEST;
		gbc_inputField.insets = new Insets(0, 0, 5, 5);
		gbc_inputField.gridx = 1;
		gbc_inputField.gridy = 0;
		simulationpanel.add(inputField, gbc_inputField);
		inputField.setColumns(10);
		
		JLabel label1 = new JLabel("Number of Outputs: ");
		GridBagConstraints gbc_label1 = new GridBagConstraints();
		gbc_label1.anchor = GridBagConstraints.EAST;
		gbc_label1.insets = new Insets(0, 0, 5, 5);
		gbc_label1.gridx = 2;
		gbc_label1.gridy = 0;
		simulationpanel.add(label1, gbc_label1);
		
		outputField = new JTextField();
		outputField.setText("1");
		GridBagConstraints gbc_outputField = new GridBagConstraints();
		gbc_outputField.insets = new Insets(0, 0, 5, 5);
		gbc_outputField.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputField.gridx = 3;
		gbc_outputField.gridy = 0;
		simulationpanel.add(outputField, gbc_outputField);
		outputField.setColumns(10);
		
		JLabel label = new JLabel("Hidden Layers: ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		simulationpanel.add(label, gbc_label);
		//field for hidden layers
		hiddenField = new JTextField();
		hiddenField.setText("1");
		GridBagConstraints gbc_hiddenField = new GridBagConstraints();
		gbc_hiddenField.insets = new Insets(0, 0, 5, 5);
		gbc_hiddenField.fill = GridBagConstraints.HORIZONTAL;
		gbc_hiddenField.gridx = 1;
		gbc_hiddenField.gridy = 1;
		simulationpanel.add(hiddenField, gbc_hiddenField);
		hiddenField.setColumns(10);
		//ActionLisener for insert Inputs
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//ImageIcon add=new ImageIcon("src/add.gif");
		insertInputs = new JButton("Insert Inputs");
		//insertInputs.setIcon(add);
		//insertInputs.setOpaque(false);
		//insertInputs.setContentAreaFilled(false);
		//insertInputs.setBorderPainted(false);
		insertInputs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((!hiddenField.getText().isEmpty() && !inputField.getText().isEmpty() && !outputField.getText().isEmpty()) ) //Checking if textfields are empty
				{
				outputCount=returnInt(outputField); //Saving gui textfield to variables
				hiddenLayerCount=returnInt(hiddenField);
				
				if (fromFile.isSelected()){
					window.showOpenFileDialog();
				}
				else{
					startInput(); // Get inputs from dialogue boxes
					
					}
					
				//if (inputs!=null)
				//net=new NeuralNet(hiddenLayerCount, hiddenLayerCount, inputs.length, inputs);
				}
				else JOptionPane.showMessageDialog(frame, "Please make sure all user inputs are entered");
				
			}
		});
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		GridBagConstraints gbc_insertInputs = new GridBagConstraints();
		gbc_insertInputs.insets = new Insets(0, 0, 5, 5);
		gbc_insertInputs.gridx = 3;
		gbc_insertInputs.gridy = 1;
		simulationpanel.add(insertInputs, gbc_insertInputs);
		//ActionLisener for Get outputs
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		getOutputs = new JButton("Get Outputs");
		getOutputs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					double[] result1 = new double[outputCount];
//					result1=net.getOutputs();
//					for (int i=0; i<outputCount; i++){
//						JOptionPane.showMessageDialog(frame, "Output " + (i+1) + " is " + result1[i]);
//					}
//				} catch (HeadlessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				net.updateNeuralNetwork(inputs);
				showSaveFileDialog();
			}}
		);
		
		lblNewLabel_1 = new JLabel("Get input from text file");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		simulationpanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 2;
		simulationpanel.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Creating the checkbox~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		fromFile = new JCheckBox("");
		panel_3.add(fromFile);
		fromFile.addItemListener(this); //Item listener to check if it has been ticked, if it is then do stuff
		
		getOutputs.setToolTipText("Calculates outputs from inputs");
		GridBagConstraints gbc_getOutputs = new GridBagConstraints();
		gbc_getOutputs.insets = new Insets(0, 0, 5, 5);
		gbc_getOutputs.gridx = 3;
		gbc_getOutputs.gridy = 2;
		simulationpanel.add(getOutputs, gbc_getOutputs);
		
		btnCreateStructure = new JButton("Create Structure");
		btnCreateStructure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				net=new NeuralNet(hiddenLayerCount, outputCount, inputs,neurons,(String)comboBox.getSelectedItem());
			}
		});
		
		lblActivationFunction = new JLabel("Activation Function");
		GridBagConstraints gbc_lblActivationFunction = new GridBagConstraints();
		gbc_lblActivationFunction.insets = new Insets(0, 0, 5, 5);
		gbc_lblActivationFunction.gridx = 0;
		gbc_lblActivationFunction.gridy = 3;
		simulationpanel.add(lblActivationFunction, gbc_lblActivationFunction);
		String[] activationfunctions = {"sigmoid","arctangent","hyperbolic tangent",};
		comboBox = new JComboBox(activationfunctions);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		comboBox.setSelectedIndex(0);
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 3;
		simulationpanel.add(comboBox, gbc_comboBox);
		
		btnInsertNumberOf = new JButton("Insert number of neurons");
		btnInsertNumberOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startNeuron();
			}
		});
		GridBagConstraints gbc_btnInsertNumberOf = new GridBagConstraints();
		gbc_btnInsertNumberOf.insets = new Insets(0, 0, 5, 5);
		gbc_btnInsertNumberOf.gridx = 3;
		gbc_btnInsertNumberOf.gridy = 3;
		simulationpanel.add(btnInsertNumberOf, gbc_btnInsertNumberOf);
		GridBagConstraints gbc_btnCreateStructure = new GridBagConstraints();
		gbc_btnCreateStructure.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreateStructure.gridx = 2;
		gbc_btnCreateStructure.gridy = 4;
		simulationpanel.add(btnCreateStructure, gbc_btnCreateStructure);
		
		trainingpanel = new JPanel();
		tabbedPane.addTab("Training", null, trainingpanel, null);
		GridBagLayout gbl_trainingpanel = new GridBagLayout();
		gbl_trainingpanel.columnWidths = new int[]{147, 130, 0, 0, 0, 0};
		gbl_trainingpanel.rowHeights = new int[]{22, 22, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_trainingpanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_trainingpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		trainingpanel.setLayout(gbl_trainingpanel);
		
		lblNewLabel_2 = new JLabel("Training Inputs");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		trainingpanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		trainInbut = new JButton("Enter Inputs");
		trainInbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog f = new FileDialog(frame, "Open File", FileDialog.LOAD);
			    f.setDirectory(directory); // Set the default directory
			    f.show();
			    directory = f.getDirectory(); // Remember new default directory
			    window.setFile(directory, f.getFile(),0); // Calls setfile which chooses a file for reading
			}
		});
		GridBagConstraints gbc_trainInbut = new GridBagConstraints();
		gbc_trainInbut.insets = new Insets(0, 0, 5, 5);
		gbc_trainInbut.gridx = 1;
		gbc_trainInbut.gridy = 0;
		trainingpanel.add(trainInbut, gbc_trainInbut);
		
		lblLearningMethod = new JLabel("Learning Method");
		GridBagConstraints gbc_lblLearningMethod = new GridBagConstraints();
		gbc_lblLearningMethod.insets = new Insets(0, 0, 5, 5);
		gbc_lblLearningMethod.gridx = 3;
		gbc_lblLearningMethod.gridy = 0;
		trainingpanel.add(lblLearningMethod, gbc_lblLearningMethod);
		
		String learningMethod[] = {"online","offline"};
		comboBox_1 = new JComboBox(learningMethod);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 4;
		gbc_comboBox_1.gridy = 0;
		trainingpanel.add(comboBox_1, gbc_comboBox_1);
		
		lblTrainingOutputs = new JLabel("Training Outputs");
		GridBagConstraints gbc_lblTrainingOutputs = new GridBagConstraints();
		gbc_lblTrainingOutputs.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrainingOutputs.gridx = 0;
		gbc_lblTrainingOutputs.gridy = 1;
		trainingpanel.add(lblTrainingOutputs, gbc_lblTrainingOutputs);
		
		trainOutbut = new JButton("Enter Outputs");
		trainOutbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog f = new FileDialog(frame, "Open File", FileDialog.LOAD);
			    f.setDirectory(directory); // Set the default directory
			    f.show();
			    directory = f.getDirectory(); // Remember new default directory
			    window.setFile(directory, f.getFile(),2); // Calls setfile which chooses a file for reading
			}
		});
		GridBagConstraints gbc_trainOutbut = new GridBagConstraints();
		gbc_trainOutbut.insets = new Insets(0, 0, 5, 5);
		gbc_trainOutbut.gridx = 1;
		gbc_trainOutbut.gridy = 1;
		trainingpanel.add(trainOutbut, gbc_trainOutbut);
		
		lblLearningRate = new JLabel("Learning Rate");
		GridBagConstraints gbc_lblLearningRate = new GridBagConstraints();
		gbc_lblLearningRate.insets = new Insets(0, 0, 5, 5);
		gbc_lblLearningRate.gridx = 0;
		gbc_lblLearningRate.gridy = 2;
		trainingpanel.add(lblLearningRate, gbc_lblLearningRate);
		
		learningField = new JTextField();
		learningField.setText("0.1");
		GridBagConstraints gbc_learningField = new GridBagConstraints();
		gbc_learningField.insets = new Insets(0, 0, 5, 5);
		gbc_learningField.fill = GridBagConstraints.HORIZONTAL;
		gbc_learningField.gridx = 1;
		gbc_learningField.gridy = 2;
		trainingpanel.add(learningField, gbc_learningField);
		learningField.setColumns(10);
		
		lblError = new JLabel("Error");
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 3;
		trainingpanel.add(lblError, gbc_lblError);
		
		errorField = new JTextField();
		errorField.setText("0.0001");
		GridBagConstraints gbc_errorField = new GridBagConstraints();
		gbc_errorField.insets = new Insets(0, 0, 5, 5);
		gbc_errorField.fill = GridBagConstraints.HORIZONTAL;
		gbc_errorField.gridx = 1;
		gbc_errorField.gridy = 3;
		trainingpanel.add(errorField, gbc_errorField);
		errorField.setColumns(10);
		
		lblMaxNumberOf = new JLabel("Max Number of iterations");
		GridBagConstraints gbc_lblMaxNumberOf = new GridBagConstraints();
		gbc_lblMaxNumberOf.anchor = GridBagConstraints.EAST;
		gbc_lblMaxNumberOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxNumberOf.gridx = 0;
		gbc_lblMaxNumberOf.gridy = 4;
		trainingpanel.add(lblMaxNumberOf, gbc_lblMaxNumberOf);
		
		iterationField = new JTextField();
		iterationField.setText("10000");
		GridBagConstraints gbc_iterationField = new GridBagConstraints();
		gbc_iterationField.insets = new Insets(0, 0, 5, 5);
		gbc_iterationField.fill = GridBagConstraints.HORIZONTAL;
		gbc_iterationField.gridx = 1;
		gbc_iterationField.gridy = 4;
		trainingpanel.add(iterationField, gbc_iterationField);
		iterationField.setColumns(10);
		
		btnNewButton = new JButton("Display Graph");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGraph();
			}
		});
		
		btnTrain = new JButton("Train");
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new trainInBackground().start();
			}
		});
		
		lblMomentum = new JLabel("Momentum");
		GridBagConstraints gbc_lblMomentum = new GridBagConstraints();
		gbc_lblMomentum.anchor = GridBagConstraints.EAST;
		gbc_lblMomentum.insets = new Insets(0, 0, 5, 5);
		gbc_lblMomentum.gridx = 0;
		gbc_lblMomentum.gridy = 5;
		trainingpanel.add(lblMomentum, gbc_lblMomentum);
		
		momentumField = new JTextField();
		momentumField.setText("0.01");
		GridBagConstraints gbc_momentumField = new GridBagConstraints();
		gbc_momentumField.insets = new Insets(0, 0, 5, 5);
		gbc_momentumField.fill = GridBagConstraints.HORIZONTAL;
		gbc_momentumField.gridx = 1;
		gbc_momentumField.gridy = 5;
		trainingpanel.add(momentumField, gbc_momentumField);
		momentumField.setColumns(10);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 4;
		gbc_progressBar.gridy = 5;
		trainingpanel.add(progressBar, gbc_progressBar);
		
		lblTau = new JLabel("Tau");
		GridBagConstraints gbc_lblTau = new GridBagConstraints();
		gbc_lblTau.anchor = GridBagConstraints.EAST;
		gbc_lblTau.insets = new Insets(0, 0, 5, 5);
		gbc_lblTau.gridx = 0;
		gbc_lblTau.gridy = 6;
		trainingpanel.add(lblTau, gbc_lblTau);
		
		tauField = new JTextField();
		tauField.setText("100");
		GridBagConstraints gbc_tauField = new GridBagConstraints();
		gbc_tauField.insets = new Insets(0, 0, 5, 5);
		gbc_tauField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tauField.gridx = 1;
		gbc_tauField.gridy = 6;
		trainingpanel.add(tauField, gbc_tauField);
		tauField.setColumns(10);
		GridBagConstraints gbc_btnTrain = new GridBagConstraints();
		gbc_btnTrain.insets = new Insets(0, 0, 5, 0);
		gbc_btnTrain.gridx = 4;
		gbc_btnTrain.gridy = 6;
		trainingpanel.add(btnTrain, gbc_btnTrain);
		
		lblC = new JLabel("c");
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.anchor = GridBagConstraints.EAST;
		gbc_lblC.insets = new Insets(0, 0, 5, 5);
		gbc_lblC.gridx = 0;
		gbc_lblC.gridy = 7;
		trainingpanel.add(lblC, gbc_lblC);
		
		cField = new JTextField();
		cField.setText("0.1");
		GridBagConstraints gbc_cField = new GridBagConstraints();
		gbc_cField.insets = new Insets(0, 0, 5, 5);
		gbc_cField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cField.gridx = 1;
		gbc_cField.gridy = 7;
		trainingpanel.add(cField, gbc_cField);
		cField.setColumns(10);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 7;
		trainingpanel.add(btnNewButton, gbc_btnNewButton);
		
		lblN = new JLabel("n0");
		GridBagConstraints gbc_lblN = new GridBagConstraints();
		gbc_lblN.anchor = GridBagConstraints.EAST;
		gbc_lblN.insets = new Insets(0, 0, 5, 5);
		gbc_lblN.gridx = 0;
		gbc_lblN.gridy = 8;
		trainingpanel.add(lblN, gbc_lblN);
		
		n0Field = new JTextField();
		n0Field.setText("0.01");
		GridBagConstraints gbc_n0Field = new GridBagConstraints();
		gbc_n0Field.insets = new Insets(0, 0, 5, 5);
		gbc_n0Field.fill = GridBagConstraints.HORIZONTAL;
		gbc_n0Field.gridx = 1;
		gbc_n0Field.gridy = 8;
		trainingpanel.add(n0Field, gbc_n0Field);
		n0Field.setColumns(10);
		
		lblAdaptation = new JLabel("Adaptation");
		GridBagConstraints gbc_lblAdaptation = new GridBagConstraints();
		gbc_lblAdaptation.anchor = GridBagConstraints.EAST;
		gbc_lblAdaptation.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdaptation.gridx = 0;
		gbc_lblAdaptation.gridy = 9;
		trainingpanel.add(lblAdaptation, gbc_lblAdaptation);
		
		String[] adaptation = {"Non Adaptive","stochastic","STC","self adaption"};
		comboBox_2 = new JComboBox(adaptation);
		
		comboBox_2.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 9;
		trainingpanel.add(comboBox_2, gbc_comboBox_2);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frame.getContentPane()}));
	}
	public class trainInBackground extends Thread {

	    public void run() {
	    	Training.trainTheNeuralNetwork(net,trainin,trainout,returnDouble(learningField),returnDouble(errorField),returnInt(iterationField),returnDouble(momentumField),returnDouble(cField),returnDouble(tauField),(String)comboBox_2.getSelectedItem(),(String)comboBox_1.getSelectedItem());
	    	//Training.trainTheNeuralNetwork(net,trainin,trainout,returnDouble(learningField),returnDouble(errorField),returnInt(iterationField),0.001,0.9,400,"","offline");

	    }

	   

	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private void showOpenFileDialog() {
		/*JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		
		}
		*/
		
		FileDialog f = new FileDialog(frame, "Open File", FileDialog.LOAD);
	    f.setDirectory(directory); // Set the default directory
	    f.show();
	    directory = f.getDirectory(); // Remember new default directory
	    window.setFile(directory, f.getFile(),1); // Calls setfile which chooses a file for reading
	}
	private void showSaveFileDialog() {
		/*JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		
		}
		*/
		
		FileDialog f = new FileDialog(frame, "Save File", FileDialog.SAVE);
	    f.setDirectory(directory); // Set the default directory
	    f.show();
	    directory = f.getDirectory(); // Remember new default directory
	    window.saveFile(directory, f.getFile()); // Calls setfile which chooses a file for reading
	}
	public void itemStateChanged(ItemEvent e) { //Item listener event
	    if (e.getStateChange() == ItemEvent.SELECTED) {
	    	inputField.setEnabled(false);
	    	inputField.setBackground(null); //Disabling the field and changing colour 
	    } else {
	    	inputField.setEnabled(true);
	    	inputField.setBackground(Color.WHITE);
	    }
	}
	public void setFile(String directory, String filename, int check) {
	    if ((filename == null) || (filename.length() == 0))
	      return;
	    File f;
	    FileReader in = null;
	    read="";;
	    // Read and display the file contents. Since we're reading text, we
	    // use a FileReader instead of a FileInputStream.
	    try {
	      f = new File(directory, filename); // Create a file object
	      in = new FileReader(f); // And a char stream to read it
	      char[] buffer = new char[4096]; // Read 4K characters at a time
	      int len; // How many chars read each time
	      while ((len = in.read(buffer)) != -1) { // Read a batch of chars
	    	  
	    	 String s = new String(buffer, 0, len); // Convert to a string
	       read=read+s;
	      }
	      inputFromFile=read.split(","); //Splitting up the string by commas
	      if (check==1){
	      inputs=new double[inputFromFile.length];
	      for (int i=0; i<inputFromFile.length; i++){
	    	  inputs[i]= Double.parseDouble(inputFromFile[i]); // Changing every string to double
	      }
	      }
	      else if(check==0){ //CHANGE THIS TO OWN METHOD
	    	  int k=0;
	    	  trainin=new double[inputFromFile.length/inputCount][inputCount];
	    	  for (int i=0; i<trainin.length; i++){
	    		  for (int j=0; j<trainin[i].length; j++){
	    			  trainin[i][j]=Double.parseDouble(inputFromFile[k]);
	    			  System.out.println(inputFromFile[k] + "  " + k);
	    			  k++;
	    			  
	    		  }
	    	  }
	    	  
	      }
	      else if(check==2){
	    	  
	    	  int k=0;
	    	  trainout=new double[inputFromFile.length/outputCount][outputCount];
	    	  for (int i=0; i<trainout.length; i++){
	    		  for (int j=0; j<trainout[i].length; j++){
	    			  trainout[i][j]=Double.parseDouble(inputFromFile[k]);
	    			  System.out.println(inputFromFile[k] + "  " + k);
	    			  k++;
	    			  
	    		  }
	    	  }
	    	  
	      }
	      
	    }
	    // Display messages if something goes wrong
	    catch (IOException e) {
	      
	    }
	    // Always be sure to close the input stream!
	    finally {
	      try {
	        if (in != null){
	          in.close();
	        
	        }
	      } catch (IOException e) {
	      }
	    }
	  }	
	public void saveFile(String directory, String filename) {
	    if ((filename == null) || (filename.length() == 0))
	      return;
	    
	    // Read and display the file contents. Since we're reading text, we
	    // use a FileReader instead of a FileInputStream.
	    try {
	    	filename+=".txt";
	      File f = new File(directory, filename); // Create a file object
	      PrintWriter fw= new PrintWriter(f);
	      double[] result1 = new double[outputCount];
			result1=net.getOutputs();
			String s=Arrays.toString(result1);
			s = s.substring(1, s.length() - 1); // removes brackets from array
			System.out.println(s);
			fw.println(s);
			for (int i=0; i<outputCount; i++){
				JOptionPane.showMessageDialog(frame, "Output " + (i+1) + " is " + result1[i]);
				//fw.write(result1[i].toString());
			}
			fw.close();
	      
	    
	    }
	    // Display messages if something goes wrong
	    catch (IOException e) {
	      
	    }
	    // Always be sure to close the input stream!
	    
	     
	    
	  }	
	public void startInput(){
		inputCount=returnInt(inputField);
		inputs = new double[inputCount];
		for (int i=0; i<inputCount; i++){
			result=new String(); ;
			result= JOptionPane.showInputDialog(frame, "Enter input " + (i+1) + ": ",JOptionPane.OK_OPTION);
			if (result!=null){
				if(result.isEmpty()){
					JOptionPane.showMessageDialog(frame, "invalid input");
					i--;
					}
				else
					inputs[i]=Double.parseDouble(result);
			}
		}	
	}
	public void startNeuron(){
		neurons = new int[hiddenLayerCount];
		for (int i=0; i<hiddenLayerCount; i++){
			result=new String(); ;
			result= JOptionPane.showInputDialog(frame, "Enter number of neurons " + (i+1) + ": ",JOptionPane.OK_OPTION);
			if (result!=null){
				if(result.isEmpty()){
					JOptionPane.showMessageDialog(frame, "invalid input");
					i--;
					}
				else
					neurons[i]=Integer.parseInt(result);
			}
		}	
	}
	public void createGraph(){
		GraphingLib chart = new GraphingLib(
			      "Iterations vs Error" ,
			      "Iterations vs Error",Training.returnError());

			      chart.pack( );
			      RefineryUtilities.centerFrameOnScreen( chart );
			      chart.setVisible( true );
			      System.out.println(returnDouble(momentumField) + "    \n " + returnDouble(tauField) + "\n" + returnDouble(cField) + (String)comboBox_2.getSelectedItem() + (String)comboBox_1.getSelectedItem()) ;
	}
}
