package eu.choreos.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
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
    private JButton itemCopy;
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
    
    private WSClient client;
    private List<String> operationNames;
    private String requestItem;
    private String responseItem;
    
    public InputScreen() {
		super();
		initComponents();
		this.setTitle("Rehearsal Item Converter");
		this.setResizable(false);
		this.setContentPane(jDesktopPane1);
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(705, 690));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

  
    private void initComponents() {

        mainPanel = new JPanel();
        jDesktopPane1 = new JDesktopPane();
        jDesktopPane3 = new JDesktopPane();
        wsdlURI = new JTextField();
        jScrollPane1 = new JScrollPane();
        itemEditor = new JEditorPane();
        jScrollPane2 = new JScrollPane();
        itemEditor1 = new JEditorPane();
        jDesktopPane5 = new JDesktopPane();
        jLabel4 = new JLabel();
        jDesktopPane6 = new JDesktopPane();
        jDesktopPane7 = new JDesktopPane();
        jLabel6 = new JLabel();
        requestLabel = new JLabel();
        responseLabel = new JLabel();
        operationNames = new ArrayList<String>();
        jComboBox5 = new JComboBox();
        wsdlLoad = new  JButton();
        wsdlSearch = new  JButton();
        itemGet = new JButton();
        itemCopy = new JButton();
        
        menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu();
        jMenuItem2 = new JMenuItem();
        JMenuItem aboutMenuItem = new JMenuItem();
        JSeparator statusPanelSeparator = new JSeparator();
        
        mainPanel.setName("mainPanel"); // NOI18N

        jDesktopPane1.setName("jDesktopPane1"); // NOI18N
        jDesktopPane1.setBackground(new Color(0,0,0)); 
        jDesktopPane3.setName("jDesktopPane3"); // NOI18N
        jDesktopPane3.setBounds(70, -10, 0, 0);
        jDesktopPane1.add(jDesktopPane3, JLayeredPane.DEFAULT_LAYER);

        
        requestLabel.setFont(new Font("Tahoma",Font.BOLD,14)); // NOI18N
        requestLabel.setText("--> Request Item"); // NOI18N
        requestLabel.setForeground(Color.white);
        requestLabel.setName("request item"); // NOI18N20
        requestLabel.setBounds(10, 105, 180, 20);
        jDesktopPane1.add(requestLabel, JLayeredPane.DEFAULT_LAYER);
        
        jScrollPane1.setName("jScrollPane1"); // NOI18N

       itemEditor.setName("itemEditor"); // NOI18N
       itemEditor.setEditable(false);
       itemEditor.setContentType("text/html");
       itemEditor.setFont(new Font("Tahoma",Font.BOLD,15));
        jScrollPane1.setViewportView(itemEditor);

        jScrollPane1.setBounds(10, 130, 670, 220);
        jDesktopPane1.add(jScrollPane1, JLayeredPane.DEFAULT_LAYER);

        responseLabel.setFont(new Font("Tahoma",Font.BOLD,14)); // NOI18N
        responseLabel.setText("<-- Response Item"); // NOI18N
        responseLabel.setForeground(Color.white);
        responseLabel.setName("response item"); // NOI18N20
        responseLabel.setBounds(10, 355, 180, 20);
        jDesktopPane1.add(responseLabel, JLayeredPane.DEFAULT_LAYER);
        
        jScrollPane2.setName("jScrollPane2"); // NOI18N

       itemEditor1.setName("itemEditor1"); // NOI18N
       itemEditor1.setEditable(false);
       itemEditor1.setContentType("text/html");
       itemEditor1.setFont(new Font("Tahoma",Font.BOLD,15));
        jScrollPane2.setViewportView(itemEditor1);

        jScrollPane2.setBounds(10, 380, 670, 240);
        jDesktopPane1.add(jScrollPane2, JLayeredPane.DEFAULT_LAYER);
        
        jDesktopPane5.setBackground(new Color(235,235,237)); // NOI18N
        jDesktopPane5.setName("jDesktopPane5"); // NOI18N

        wsdlURI.setName("wsdlURI"); // NOI18N
        wsdlURI.setBounds(65, 8, 435, 24);
        wsdlURI.setFont(new Font("Tahoma",Font.PLAIN,12));
        wsdlURI.setForeground(Color.black);
        jDesktopPane5.add(wsdlURI, JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new Font("Tahoma",Font.BOLD,12)); // NOI18N
        jLabel4.setText("WSDL"); // NOI18N
        jLabel4.setForeground(Color.black);
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.setBounds(10, 10, 60, 20);
        jDesktopPane5.add(jLabel4, JLayeredPane.DEFAULT_LAYER);

        jDesktopPane5.setBounds(10, 10, 670, 40);
        jDesktopPane1.add(jDesktopPane5, JLayeredPane.DEFAULT_LAYER);

        jDesktopPane6.setBackground(new Color(235, 235, 237)); // NOI18N
        jDesktopPane6.setName("jDesktopPane6"); // NOI18N

        jDesktopPane6.setBounds(10, 10, 540, 40);
        jDesktopPane1.add(jDesktopPane6, JLayeredPane.DEFAULT_LAYER);

        jDesktopPane7.setBackground(new Color(235, 235, 237)); // NOI18N
        jDesktopPane7.setName("jDesktopPane7"); // NOI18N

        jLabel6.setFont(new Font("Tahoma",Font.BOLD,12)); // NOI18N
        jLabel6.setText("Operation"); // NOI18N
        jLabel6.setForeground(Color.black);
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setBounds(10, 10, 100, 20);
        jDesktopPane7.add(jLabel6, JLayeredPane.DEFAULT_LAYER);

        jComboBox5.setModel(new DefaultComboBoxModel());
        jComboBox5.setName("jComboBox5"); // NOI18N
        jComboBox5.setBounds(100, 8, 270, 24);
        jComboBox5.setForeground(Color.black);
        jComboBox5.setFont(new Font("Tahoma",Font.BOLD,11));
        jDesktopPane7.add(jComboBox5, JLayeredPane.DEFAULT_LAYER);

        jDesktopPane7.setBounds(10, 60, 670, 40);
        jDesktopPane1.add(jDesktopPane7, JLayeredPane.DEFAULT_LAYER);

        wsdlLoad.setBackground(Color.blue); // NOI18N
        wsdlLoad.setForeground(Color.white);
        wsdlLoad.setFont(new Font("Tahoma",Font.BOLD,10)); // NOI18N
        wsdlLoad.setText("Load"); // NOI18N
        wsdlLoad.setName("wsdlLoad"); // NOI18N
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
        
        wsdlLoad.setBounds(590, 5, 75, 29);
        jDesktopPane5.add(wsdlLoad, JLayeredPane.DEFAULT_LAYER);
        
        
        wsdlSearch.setBackground(Color.blue); // NOI18N
        wsdlSearch.setForeground(Color.white);
        wsdlSearch.setFont(new Font("Tahoma",Font.BOLD,10)); // NOI18N
        wsdlSearch.setText("Search"); // NOI18N
        wsdlSearch.setName("wsdlSearch"); // NOI18N
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
        wsdlSearch.setBounds(507, 5, 75, 29);
        jDesktopPane5.add(wsdlSearch, JLayeredPane.DEFAULT_LAYER);

        itemGet.setBackground(Color.blue); // NOI18N
        itemGet.setForeground(Color.white);
        itemGet.setFont(new Font("Tahoma",Font.BOLD,10)); // NOI18N
        itemGet.setText("Get Item"); // NOI18N
        itemGet.setName("itemGet"); // NOI18N
        itemGet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String operationName = jComboBox5.getSelectedItem().toString();
            	requestItem = WSClientFeatures.printRequestItem(client, operationName);
            	String formatedItem = formatItemContent(requestItem);
            	itemEditor.setText(formatedItem);
            	
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
            	formatedItem = formatItemContent(responseItem);
            	itemEditor1.setText(formatedItem);
            }
        });
        itemGet.setBounds(380, 6, 85, 29);
        jDesktopPane7.add(itemGet, JLayeredPane.DEFAULT_LAYER);
        
        itemCopy.setBackground(Color.blue); // NOI18N
        itemCopy.setForeground(Color.white);
        itemCopy.setFont(new Font("Tahoma",Font.BOLD,10)); // NOI18N
        itemCopy.setText("Gopy Item"); // NOI18N
        itemCopy.setName("itemCopy"); // NOI18N
        itemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            	if (requestItem == null)
                		JOptionPane.showMessageDialog(null,"You have to get the Item objects first!" , "No Item objects", JOptionPane.ERROR_MESSAGE);
            else{
            	JFrame frame = new JFrame("JTextArea Test");
            	frame.setLayout(new FlowLayout());
            	

            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	String text = "Request Item \n" + 
            								"-------------------------- \n\n"+
            								formatNames(requestItem)  + "\n\n" + 
        									"Response Item \n" +
        									"-------------------------- \n\n" +
        									formatNames(responseItem);
            	
            		    JTextArea textArea2 = new JTextArea(text, 50,100);
            		   // textArea2.setEditable(false);
            		    textArea2.setPreferredSize(new Dimension(200, 200));
            		    
            		    textArea2.setFont(new Font("Tahoma",Font.BOLD,15));
            		    JScrollPane scrollPane = new JScrollPane(textArea2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            		        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            		    textArea2.setLineWrap(true);
            		    frame.add(scrollPane);
            		    frame.pack();
            		    frame.setVisible(true);
            		    frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
            		    frame.setLocationRelativeTo(null);
            }
            }
        });
        itemCopy.setBounds(470, 6, 95, 29);
        jDesktopPane7.add(itemCopy, JLayeredPane.DEFAULT_LAYER);


        
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N


        helpMenu.setText("Help"); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        jMenuItem2.setText("How to use?");
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String helpText = "To use the tool please follow the steps below:" + "\n" + "\n" + 
            										  "1. Load WSDL: enter a valid WSDL URI and press the button \"Load\"" + "\n" +
            										  "2. Get Item: Choose a web service operation in the combo box and press the button \"Get Item\"" + "\n" +
            										  "3. Item description: Retrieve the adequate Item parameter for invoking the selected operation";
            	
            	JOptionPane.showMessageDialog(null, helpText, "Help =] ", JOptionPane.INFORMATION_MESSAGE);
            }});
        
        helpMenu.add(jMenuItem2);
        
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        aboutMenuItem.setText("More information");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String helpText = "More Information: http://ccsl.ime.usp.br/baile/VandV" + "\n" + "\n" + 
            										  "Problems, doubts, suggestions?" + "\n" + 
    										  		"Please mail me: besson@ime.usp.br";
            										  
            	JOptionPane.showMessageDialog(null, helpText, "More information ", JOptionPane.INFORMATION_MESSAGE);
            }});
        
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);


        this.setJMenuBar(menuBar);

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N
    }              
    
    private String formatItemContent(String itemContent){
    	String formatedItem = itemContent;
    	formatedItem = formatNames(itemContent);
    	formatedItem = formatStringValues(formatedItem);	
    	formatedItem = formatedItem.replace("new", "<b><font color = \"9900CC\">new</b>");
    	formatedItem = formatedItem.replace("\n", "<br>");
    	
    	System.out.println("<html><body><h2><font  face = \"Tahoma, sans-serif\">" + formatedItem + "</font></h2></body></html>");
    	
    	return "<html><body><h2><font  face = \"Tahoma, sans-serif\">" + formatedItem + "</font></h2></body></html>";
    }
    
    private String formatStringValues(String itemContent){
    	Pattern stringValue = Pattern.compile("\"(\\w*|\\?)\"");
		Matcher m = stringValue.matcher(itemContent);
		
		while (m.find()) 
			itemContent = itemContent.replace(m.group(0), "<font color=\"0033FF\">" + m.group(0) + "</font>");
		
		return itemContent;
    }
    
    private String formatNames(String itemContent){
    	itemContent = itemContent.replaceAll("return", "return1");
    	itemContent = itemContent.replaceAll("\"return1\"", "\"return\"");

    	return itemContent;
    }
    
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
			public String getDescription() {
				return "WSDL files";
			}
			
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".wsdl");
			}
		};
		
		return fileFilter;
    }

    
    public static void main(String args[]) {
		new InputScreen();
	}
  
}