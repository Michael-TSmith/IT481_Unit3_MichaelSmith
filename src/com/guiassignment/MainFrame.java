package com.guiassignment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JRadioButton;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel MainPanel;
	private static CardLayout cardLayout;
	private JTable resultsTable;
	private JTextField input_Username;
	private JTextField input_Password;
	private static JLabel lbl_PwordError;
	private static JLabel lbl_UsernameError;
	private static JLabel lbl_LoginError;
	private static String usersName;
	private static String usersPword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		// Login Panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 2018, 1132);
		setTitle("Login");
		MainPanel = new JPanel();
		MainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		MainPanel.setLayout(cardLayout);
		MainPanel.setLayout(null);

		input_Username = new JTextField();
		input_Username.setBounds(724, 243, 529, 67);
		MainPanel.add(input_Username);
		input_Username.setColumns(10);

		JLabel lbl_Username = new JLabel("Username");
		lbl_Username.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_Username.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Username.setBounds(724, 164, 529, 59);
		MainPanel.add(lbl_Username);

		JLabel lbl_Password = new JLabel("Password");
		lbl_Password.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Password.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Password.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl_Password.setBounds(724, 387, 529, 59);
		MainPanel.add(lbl_Password);

		input_Password = new JTextField();
		input_Password.setColumns(10);
		input_Password.setBounds(724, 466, 529, 67);
		MainPanel.add(input_Password);

		JButton btn_Login = new JButton("Login");
		btn_Login.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btn_Login.setBounds(781, 618, 400, 52);
		MainPanel.add(btn_Login);

		lbl_UsernameError = new JLabel("Username is missing!");
		lbl_UsernameError.setForeground(new Color(255, 0, 0));
		lbl_UsernameError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_UsernameError.setBounds(899, 321, 200, 43);
		lbl_UsernameError.setVisible(false);
		MainPanel.add(lbl_UsernameError);

		lbl_PwordError = new JLabel("Password is missing!");
		lbl_PwordError.setForeground(Color.RED);
		lbl_PwordError.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_PwordError.setBounds(899, 547, 200, 43);
		lbl_PwordError.setVisible(false);
		MainPanel.add(lbl_PwordError);

		lbl_LoginError = new JLabel("Error logging in - Check Username and Password");
		lbl_LoginError.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LoginError.setForeground(new Color(255, 0, 0));
		lbl_LoginError.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lbl_LoginError.setVisible(false);
		lbl_LoginError.setBounds(522, 694, 956, 82);
		MainPanel.add(lbl_LoginError);
		

		// Dashboard Panel
		JPanel DashboardPanel = new JPanel();
		DashboardPanel.setLayout(null);
		JLabel lbl_databaseConnectionStatic = new JLabel("Database Connection:");
		lbl_databaseConnectionStatic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_databaseConnectionStatic.setBounds(1603, 13, 350, 13);
		DashboardPanel.add(lbl_databaseConnectionStatic);

		JLabel lbl_DbConnectionDynamic = new JLabel("Disconnected");
		lbl_DbConnectionDynamic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_DbConnectionDynamic.setBounds(1731, 13, 222, 13);
		DashboardPanel.add(lbl_DbConnectionDynamic);
		JLabel lblNewLabel = new JLabel("Database Results:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(120, 584, 177, 42);
		DashboardPanel.add(lblNewLabel);

		JButton btn_ConnectToDB = new JButton("Connect");
		btn_ConnectToDB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_ConnectToDB.setBounds(1670, 37, 98, 30);
		DashboardPanel.add(btn_ConnectToDB);
		btn_ConnectToDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lbl_DbConnectionDynamic.setText("Connecting");
				connectToDB(lbl_DbConnectionDynamic);

			}
		});

		connectToDB(lbl_DbConnectionDynamic);
		

		JLabel lbl_AppTitle = new JLabel("Database Viewer");
		lbl_AppTitle.setBounds(28, 13, 857, 29);
		DashboardPanel.add(lbl_AppTitle);
		lbl_AppTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JLabel lbl_FetchError = new JLabel("You Lack Proper Permissions, Contact your Database Admin");
		lbl_FetchError.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_FetchError.setForeground(new Color(255, 0, 0));
		lbl_FetchError.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_FetchError.setBounds(149, 944, 1713, 78);
		lbl_FetchError.setVisible(false);
		DashboardPanel.add(lbl_FetchError);

		JButton btn_getRecords = new JButton("Get Records");
		btn_getRecords.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_getRecords.setBounds(1670, 566, 200, 51);
		DashboardPanel.add(btn_getRecords);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(120, 637, 1750, 268);
		DashboardPanel.add(scrollPane);

		JLabel lblNewLabel_1 = new JLabel("Michael Smith 2023");
		lblNewLabel_1.setBounds(1896, 1070, 98, 14);
		DashboardPanel.add(lblNewLabel_1);
		
		
		JRadioButton rdbtn_Orders = new JRadioButton("Orders");
		rdbtn_Orders.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtn_Orders.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtn_Orders.setBounds(920, 408, 384, 57);
		DashboardPanel.add(rdbtn_Orders);
		
		JRadioButton rdbtn_Customers = new JRadioButton("Customers");
		rdbtn_Customers.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtn_Customers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtn_Customers.setBounds(920, 468, 384, 57);
		DashboardPanel.add(rdbtn_Customers);
		
		JRadioButton rdbtn_Employees = new JRadioButton("Employees");
		rdbtn_Employees.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtn_Employees.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtn_Employees.setBounds(920, 524, 384, 57);
		DashboardPanel.add(rdbtn_Employees);

		ButtonGroup dbButtons = new ButtonGroup();
		dbButtons.add(rdbtn_Orders);
		dbButtons.add(rdbtn_Customers);
		dbButtons.add(rdbtn_Employees);
		
		btn_getRecords.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tableName = null;
				if(rdbtn_Orders.isSelected()) {
					tableName = "Orders";
				} else if(rdbtn_Customers.isSelected()) {
					tableName = "Customers";
				} else if(rdbtn_Employees.isSelected()) {
					tableName = "Employees";
				}
				getRecords(scrollPane, tableName, lbl_FetchError);

			}
		});
		
		btn_Login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateLogin(input_Username.getText(), input_Password.getText(), lbl_AppTitle);

			}
		});

		// Initialize CardLayout and add both panels to it
		cardLayout = new CardLayout();
		getContentPane().setLayout(cardLayout);

		getContentPane().add(MainPanel, "Login");
		getContentPane().add(DashboardPanel, "Dashboard");
		
		
		// Show the login panel by default
		cardLayout.show(getContentPane(), "Login");

	}

	private void validateLogin(String userName, String pword, JLabel label) {
		if(userName.isEmpty() || userName == null) {
			lbl_UsernameError.setVisible(true);
			if(pword.isEmpty()) {
				lbl_PwordError.setVisible(true);
			}
		}
		if(pword.isEmpty() || pword == null) {
			lbl_PwordError.setVisible(true);
		}

		if(!userName.isEmpty() && !pword.isEmpty()) {

			lbl_UsernameError.setVisible(false);
			lbl_PwordError.setVisible(false);
				
			String connectionUrl = "jdbc:sqlserver://developer\\sqlexpress;databaseName=Northwind;";
	
			try (Connection conn = DriverManager.getConnection(connectionUrl, userName, pword)) {
				System.out.println("Connection Successful");
				lbl_LoginError.setVisible(false);
				cardLayout.show(getContentPane(), "Dashboard");
				usersName = userName;
				usersPword = pword;
				label.setText("Welcome, " + userName);
			} catch (SQLException e) {
				System.out.println("Connection to Database Unsuccessful!");
				lbl_LoginError.setVisible(true);
				e.printStackTrace();
			}
		} 
	}
	
	private void getRecords(JScrollPane scrollPane, String table, JLabel label) {

		resultsTable = new JTable();
		String sqlQuery = "SELECT * FROM " + table;
		String connectionUrl = "jdbc:sqlserver://developer\\sqlexpress;databaseName=Northwind;";
		

	    try (Connection conn = DriverManager.getConnection(connectionUrl, usersName, usersPword);
	         Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery(sqlQuery)) {
	         ResultSetMetaData metaData = resultSet.getMetaData();

	        // Create a DefaultTableModel to hold the data
	        DefaultTableModel tableModel = new DefaultTableModel();

	        // Add column names to the table model
	        int columnCount = metaData.getColumnCount();
	        for (int i = 1; i <= columnCount; i++) {
	            tableModel.addColumn(metaData.getColumnName(i));
	        }

	        // Add rows to the table model
	        while (resultSet.next()) {
	            Object[] rowData = new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                rowData[i - 1] = resultSet.getObject(i);
	            }
	            tableModel.addRow(rowData);
	        }
	        label.setVisible(false);
	        resultsTable.setModel(tableModel);

	    } catch (SQLException e) {
	    	label.setVisible(true);
	        e.printStackTrace();
	    }
		scrollPane.setViewportView(resultsTable);
    }

	
	
	private void connectToDB(JLabel dbLabel) {
		
		String connectionUrl = "jdbc:sqlserver://developer\\sqlexpress;databaseName=Northwind;integratedSecurity=true";
	    
	    Connection conn;
		try {
			conn = DriverManager.getConnection(connectionUrl);
			if (conn != null) {
		        dbLabel.setText("Connected");
		    }
		} catch (SQLException e) {
			dbLabel.setText("Connection Failed...Try Again");
			e.printStackTrace();
		}
	}
}
