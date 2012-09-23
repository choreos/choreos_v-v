package eu.choreos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.support.SoapUIException;

import eu.choreos.logic.WSClientFeatures;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * The application's main frame.
 */
public class InputScreen extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton wsdlLoad;
	private JButton wsdlSearch;
    private JButton itemGet;
    private JTextField wsdlURI;
    private JComboBox jComboBox5;
    private JDesktopPane jDesktopPane1;
    private JDesktopPane jDesktopPane3;
    private JDesktopPane jDesktopPane5;
    private JDesktopPane jDesktopPane6;
    private JDesktopPane jDesktopPane7;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel requestLabel;
    private JLabel responseLabel;
    private JMenuItem jMenuItem2;
    private JScrollPane jScrollPane1;
    private JEditorPane itemEditor;
    private JScrollPane jScrollPane2;
    private JEditorPane itemEditor1;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    
    private WSClient client;
    private List<String> operationNames;
    private String requestItem;
    private String responseItem;
    
    public InputScreen() {
		super();        

		initComponents();
		this.setTitle("Rehearsal Item Explorer");
		this.setResizable(false);
		this.setContentPane(jDesktopPane1);
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(695, 680));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

    private void initComponents() {
    	
    	initContainers();
    	initLabels();
    	initTextFields();
    	initButtonsAndBoxes();
        
        mainPanel.setName("mainPanel"); 
		jDesktopPane1.setName("jDesktopPane1"); 

        createJDesktopPane3();
        jDesktopPane1.add(jDesktopPane3, JLayeredPane.DEFAULT_LAYER);

        createRequestLabel();
        jDesktopPane1.add(requestLabel, JLayeredPane.DEFAULT_LAYER);
        
        createJScrollPane1();
        jDesktopPane1.add(jScrollPane1, JLayeredPane.DEFAULT_LAYER);

        createResponseLabel();
        jDesktopPane1.add(responseLabel, JLayeredPane.DEFAULT_LAYER);
        
        createJScrollPane2();
        jDesktopPane1.add(jScrollPane2, JLayeredPane.DEFAULT_LAYER);
        
        createJDesktopPane5(); 
        createWsdlUriWsdl();
        createWsdlButtonLabel();
        jDesktopPane1.add(jDesktopPane5, JLayeredPane.DEFAULT_LAYER);

        createJDesktopPane6();
        jDesktopPane1.add(jDesktopPane6, JLayeredPane.DEFAULT_LAYER);

        createJDesktopPane7();
        createOperationBoxLabel();
        jDesktopPane7.add(jLabel6, JLayeredPane.DEFAULT_LAYER);

        createJComboBox5();
        jDesktopPane7.add(jComboBox5, JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.add(jDesktopPane7, JLayeredPane.DEFAULT_LAYER);

        createWsdlLoadButton();
        jDesktopPane5.add(wsdlLoad, JLayeredPane.DEFAULT_LAYER);
        
        createWsdlSearchButton();
        jDesktopPane5.add(wsdlSearch, JLayeredPane.DEFAULT_LAYER);

        createGetItemButton();
        jDesktopPane7.add(itemGet, JLayeredPane.DEFAULT_LAYER);

        createMenuBar();
        this.setJMenuBar(menuBar);

    }

	private void createMenuBar() {
		menuBar.setName("menuBar"); 
        helpMenu.setText("Help"); 
        helpMenu.setName("helpMenu"); 
        jMenuItem2.setText("How to use?");
        jMenuItem2.setName("jMenuItem2"); 
        createHelpMenu();
        helpMenu.add(jMenuItem2);
        aboutMenuItem.setName("aboutMenuItem"); 
        aboutMenuItem.setText("More information");
        createAboutMenu(aboutMenuItem);
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);
	}

	private void createGetItemButton() {
		itemGet.setBackground(Color.blue); 
        itemGet.setForeground(Color.white);
        itemGet.setFont(new Font("Tahoma",Font.BOLD,10)); 
        itemGet.setText("Get Item"); 
        itemGet.setName("itemGet"); 
        getItemRepresentation();
        itemGet.setBounds(380, 6, 85, 29);
	}

	private void createWsdlSearchButton() {
		wsdlSearch.setBackground(Color.blue); 
        wsdlSearch.setForeground(Color.white);
        wsdlSearch.setFont(new Font("Tahoma",Font.BOLD,10)); 
        wsdlSearch.setText("Search"); 
        wsdlSearch.setName("wsdlSearch"); 
        detectWsdlImported();
        wsdlSearch.setBounds(507, 5, 75, 29);
	}

	private void createWsdlLoadButton() {
		wsdlLoad.setBackground(Color.blue); 
        wsdlLoad.setForeground(Color.white);
        wsdlLoad.setFont(new Font("Tahoma",Font.BOLD,10)); 
        wsdlLoad.setText("Load"); 
        wsdlLoad.setName("wsdlLoad"); 
        getOperations();
        wsdlLoad.setBounds(590, 5, 75, 29);
	}

	private void createJComboBox5() {
		jComboBox5.setModel(new DefaultComboBoxModel());
        jComboBox5.setName("jComboBox5"); 
        jComboBox5.setBounds(100, 8, 270, 24);
        jComboBox5.setForeground(Color.black);
        jComboBox5.setFont(new Font("Tahoma",Font.BOLD,11));
	}

	private void createOperationBoxLabel() {
		jLabel6.setFont(new Font("Tahoma",Font.BOLD,12)); 
        jLabel6.setText("Operation"); 
        jLabel6.setForeground(Color.black);
        jLabel6.setName("jLabel6"); 
        jLabel6.setBounds(10, 10, 100, 20);
	}

	private void createJDesktopPane7() {
		jDesktopPane7.setBackground(new Color(235, 235, 237)); 
        jDesktopPane7.setName("jDesktopPane7"); 
        jDesktopPane7.setBounds(10, 60, 670, 40);
	}

	private void createJDesktopPane6() {
		jDesktopPane6.setBackground(new Color(235, 235, 237)); 
        jDesktopPane6.setName("jDesktopPane6"); 
        jDesktopPane6.setBounds(10, 10, 540, 40);
	}

	private void createWsdlButtonLabel() {
		jLabel4.setFont(new Font("Tahoma",Font.BOLD,12)); 
        jLabel4.setText("WSDL"); 
        jLabel4.setForeground(Color.black);
        jLabel4.setName("jLabel4"); 
        jLabel4.setBounds(10, 10, 60, 20);
        jDesktopPane5.add(jLabel4, JLayeredPane.DEFAULT_LAYER);
	}

	private void createWsdlUriWsdl() {
		wsdlURI.setName("wsdlURI"); 
        wsdlURI.setBounds(65, 8, 435, 24);
        wsdlURI.setFont(new Font("Tahoma",Font.PLAIN,12));
        wsdlURI.setForeground(Color.black);
        jDesktopPane5.add(wsdlURI, JLayeredPane.DEFAULT_LAYER);
	}

	private void createJDesktopPane5() {
		jDesktopPane5.setBackground(new Color(235,235,237)); 
        jDesktopPane5.setName("jDesktopPane5");
        jDesktopPane5.setBounds(10, 10, 670, 40);
	}

	private void createJScrollPane2() {
		jScrollPane2.setName("jScrollPane2"); 
       itemEditor1.setName("itemEditor1"); 
       itemEditor1.setEditable(false);
       itemEditor1.setContentType("text");
       itemEditor1.setFont(new Font("Tahoma",Font.BOLD,16));
        jScrollPane2.setViewportView(itemEditor1);
        jScrollPane2.setBounds(10, 380, 670, 240);
	}

	private void createResponseLabel() {
		responseLabel.setFont(new Font("Tahoma",Font.BOLD,14)); 
        responseLabel.setText("<-- Response Item"); 
        responseLabel.setForeground(Color.white);
        responseLabel.setName("response item"); 
        responseLabel.setBounds(10, 355, 180, 20);
	}

	private void createJScrollPane1() {
		jScrollPane1.setName("jScrollPane1"); 
       itemEditor.setName("itemEditor"); 
       itemEditor.setEditable(false);
       itemEditor.setContentType("text");
       itemEditor.setFont(new Font("Tahoma",Font.BOLD,16));
        jScrollPane1.setViewportView(itemEditor);

        jScrollPane1.setBounds(10, 130, 670, 220);
	}

	private void createRequestLabel() {
		requestLabel.setFont(new Font("Tahoma",Font.BOLD,14)); 
        requestLabel.setText("--> Request Item"); 
        requestLabel.setForeground(Color.white);
        requestLabel.setName("request item"); 
        requestLabel.setBounds(10, 105, 180, 20);
	}

	private void createJDesktopPane3() {
        jDesktopPane1.setBackground(new Color(0,0,0)); 
        jDesktopPane3.setName("jDesktopPane3"); 
        jDesktopPane3.setBounds(70, -10, 0, 0);
	}
    
    private void initContainers(){
    	mainPanel = new JPanel();
    	jDesktopPane1 = new JDesktopPane();
    	jDesktopPane3 = new JDesktopPane();
    	jScrollPane1 = new JScrollPane();
    	itemEditor = new JEditorPane();
    	jScrollPane2 = new JScrollPane();
    	itemEditor1 = new JEditorPane();
    	jDesktopPane5 = new JDesktopPane();
    	jDesktopPane6 = new JDesktopPane();
    	jDesktopPane7 = new JDesktopPane();
    }

    private void initLabels(){
    	jLabel4 = new JLabel();
    	jLabel6 = new JLabel();
    	requestLabel = new JLabel();
    	responseLabel = new JLabel();
    }
    
    private void initTextFields(){
    	wsdlURI = new JTextField();
    }
    
    private void initButtonsAndBoxes(){
    	jComboBox5 = new JComboBox();
    	wsdlLoad = new  JButton();
    	wsdlSearch = new  JButton();
    	itemGet = new JButton();
    	operationNames = new ArrayList<String>();
    	menuBar = new JMenuBar();
    	helpMenu = new JMenu();
    	jMenuItem2 = new JMenuItem();
    	aboutMenuItem = new JMenuItem();
    	
    }
    
    // Create About menu
	private void createAboutMenu(JMenuItem aboutMenuItem) {
		aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String helpText = "More Information: http://ccsl.ime.usp.br/baile/VandV" + "\n" + "\n" + 
            										  "Problems, doubts, suggestions?" + "\n" + 
    										  		"Please mail me: besson@ime.usp.br";
            										  
            	JOptionPane.showMessageDialog(null, helpText, "More information ", JOptionPane.INFORMATION_MESSAGE);
            }});
	}

	// Create Help Menu
	private void createHelpMenu() {
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String helpText = "To use the tool please follow the steps below:" + "\n" + "\n" + 
            										  "1. Load WSDL: enter a valid WSDL URI and press the button \"Load\"" + "\n" +
            										  "2. Get Item: Choose a web service operation in the combo box and press the button \"Get Item\"" + "\n" +
            										  "3. Item description: Retrieve the adequate Item parameter for invoking the selected operation";
            	
            	JOptionPane.showMessageDialog(null, helpText, "Help =] ", JOptionPane.INFORMATION_MESSAGE);
            }});
	}

  // Get Request and Response Item 
	private void getItemRepresentation() {
		itemGet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String operationName = jComboBox5.getSelectedItem().toString();
            	requestItem = WSClientFeatures.printRequestItem(client, operationName);
            	itemEditor.setText(formatNames(requestItem));
            	
            	responseItem = "";
            	
            	try {
            		responseItem = WSClientFeatures.printResponseItem(client, operationName);
				} catch (XmlException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SoapUIException e) {
					e.printStackTrace();
				}
            	itemEditor1.setText(formatNames(responseItem));
            }
        });
	}

  // Verify if wsdl imported is corrected
	private void detectWsdlImported() {
		wsdlSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
            		wsdlURI.setText(getFilePath());
            	}
            	catch (Exception e) { 
            		JOptionPane.showMessageDialog(null, e.getMessage(), "Error when importing the WSDL", JOptionPane.ERROR_MESSAGE);
				}  
            }
        });
	}
	
    // Get web service operations
	private void getOperations() {
		wsdlLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
					client = WSClientFeatures.load(wsdlURI.getText());
					operationNames = client.getOperations();
					jComboBox5.removeAllItems();
					
					for(String operationName : operationNames)
						jComboBox5.addItem(operationName);
							
					itemEditor.setText("");
					itemEditor1.setText("");
				} 
            	
            	catch (Exception e) { 
            		JOptionPane.showMessageDialog(null, e.getMessage(), "Error when importing the WSDL", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
	}              
	
	private String formatNames(String itemContent){
    	itemContent = itemContent.replaceAll("return", "return1");
    	itemContent = itemContent.replaceAll("\"return1\"", "\"return\"");

    	return itemContent;
    }
    
 //	Get File parh for importing wsdl files
    private String getFilePath(){
    	JFileChooser dir = new JFileChooser();
    	dir.setDialogTitle("Select WSDL file");
    	dir.setFileFilter(getWSDLFilter());
    	dir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dir.showOpenDialog(this);
        dir.setFont(new Font("Tahoma",Font.BOLD,10));
        
        return "file://" + dir.getSelectedFile().getAbsolutePath();
    }
    
    private FileFilter getWSDLFilter(){
    	FileFilter fileFilter = new FileFilter() {
			
			@Override
			public String getDescription() { return "WSDL files"; }
			
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".wsdl");}};
			
		return fileFilter;
    }

    public static void main(String args[]) {
		new InputScreen();
	}
  
}