package eu.choreos.vv.reportviewer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.analysis.AggregatePerformance;
import eu.choreos.vv.analysis.Analyzer;
import eu.choreos.vv.chart.creator.MeanChartCreator;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.reportviewer.convert.exceptions.XMLParseException;
import eu.choreos.vv.reportviewer.convert.xml.ExperimentReportBuilder;

public class Screen extends JFrame {

	private static final String APP_TITLE = "Scalability Explorer - Report Viewer";

	private JDesktopPane mainPane;
	private JDesktopPane fileChooserPane;
	private JDesktopPane chartPane;
	
	public Screen() {
		super();
		initComponents();
		this.setTitle(APP_TITLE);
		this.setContentPane(mainPane);
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(695, 680));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void initComponents() {
		mainPane = new JDesktopPane();
		mainPane.setBackground(new Color(0));
		mainPane.setLayout(new BorderLayout());
		
		initFileChooserPane();
		mainPane.add(fileChooserPane, BorderLayout.PAGE_START);
		
		initCharPane();
		mainPane.add(chartPane, BorderLayout.CENTER);
		
	}
	
	private void initFileChooserPane() {
		fileChooserPane = new JDesktopPane();
		fileChooserPane.setLayout(new BoxLayout(fileChooserPane, BoxLayout.LINE_AXIS));
		
		JLabel fileInputLabel = new JLabel();
        fileInputLabel.setText(" File "); 
        fileChooserPane.add(fileInputLabel);
        
        JTextField fileInputField = new JTextField();
        fileChooserPane.add(fileInputField);
        
        JButton fileChooserButton = new JButton();
        fileChooserButton.setText("Select");
        fileChooserButton.addActionListener(new FileChooserListener(fileInputField));
        fileChooserPane.add(fileChooserButton);
        
        JComboBox<String> analyzerCombo = new JComboBox<String>();
        analyzerCombo.addItem("aggregate performance");
        fileChooserPane.add(analyzerCombo);
        
        JButton loadChartButton = new JButton();
        loadChartButton.setText("Load");
        loadChartButton.addActionListener(new LoadChartListener(fileInputField));
        fileChooserPane.add(loadChartButton);
		
	}
	
	private void initCharPane() {
		chartPane = new JDesktopPane();
		chartPane.setBackground(new Color(0));
		
	}
}


class FileChooserListener implements ActionListener {

	JTextField textField;
	
	public FileChooserListener(JTextField textField) {
		this.textField = textField;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		getFilePath();
		
	}
	
	private void getFilePath(){
    	JFileChooser dir = new JFileChooser();
    	dir.setDialogTitle("Select a report file");
//    	dir.setFileFilter(...); TODO 
    	dir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (dir.showOpenDialog(textField) == JFileChooser.APPROVE_OPTION)
        	textField.setText(dir.getSelectedFile().getAbsolutePath());
    }
	
}

class LoadChartListener implements ActionListener {

	private JTextComponent filepath;

	public LoadChartListener(JTextComponent filepath) {
		this.filepath = filepath;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ExperimentReportBuilder builder = new ExperimentReportBuilder();
		File file = new File(filepath.getText());
		
		try {
			List<ExperimentReport> report = builder.parse(file);
			Analyzer analyzer = new AggregatePerformance("Aggregate Performance", new MeanChartCreator());
			analyzer.analyse(report);
		} catch (XMLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}