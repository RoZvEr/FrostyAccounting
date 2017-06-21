package com.frostyllc.frostyaccounting;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Desktop;



public class FrostyAccounting {

	private JFrame frame;
	private JTextField idInput;
	private JButton button;
	private JButton calculate;
	private JButton delete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrostyAccounting window = new FrostyAccounting();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    	
    	
    	
    	
    	private JTextField quantee;
    	private JTextField productName;
    	private JTextField productPrice;
    	private JTextField productTotal;
    	
    	
    	ResultSet rs = null;
    	private JTextField lastTotalInput;
    	
	/**
	 * Create the application.
	 */
	public FrostyAccounting() {
		initialize();
		Connection connection = null;
		connection = SQLiteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.CYAN);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("FrostyAccounting - 1.0");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		
		idInput = new JTextField();
		idInput.setBounds(10, 58, 41, 22);
		frame.getContentPane().add(idInput);
		idInput.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.BLACK);
		lblId.setBounds(23, 29, 56, 16);
		frame.getContentPane().add(lblId);
		
		button = new JButton("\u041D\u0430\u043C\u0435\u0440\u0438");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int idInt;
				String amount;
				String price;
				String name;
				String id;
				
				try {
					
					idInt = Integer.parseInt(idInput.getText());
					id = Integer.toString(idInt);
					
					//Here goes the setting values code						
					
					Function f = new Function();
					ResultSet rs = null;
					
					String fname = "name";
					String lamount = "price";
					rs = f.find(id);
					
					try {
						
						if(rs.next()) {
							productName.setText(rs.getString("name"));
							productPrice.setText(rs.getString("price"));
							
						}
						
						
						
						
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "NO DATA FOR THIS ID !!!");
					}
					
					calculate.addActionListener(new ActionListener() {
						
						//Getting the total and writing to file
						
						public void actionPerformed(ActionEvent e) {
							String id;
							id = idInput.getText();
							
							int idInt;
							
							idInt = Integer.parseInt(id);
							
							
							String name;
							double price;
							double amount;
							
							name = productName.getText();
							price = Double.parseDouble(productPrice.getText());
							amount = Double.parseDouble(quantee.getText());
							double totalInt = price * amount;
							String total = Double.toString(totalInt);
							productTotal.setText(total);
								
							String finalStr = "Номер : " +  id + " - Име : " + name + " - Цена : " + price + " - Количество : " + amount + " Общо : " + total + " лева";
							System.out.println(finalStr);
							
							try {
								 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data.txt", true)));
								    out.println(finalStr + "\n");
								    out.close();
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							Scanner sc = null;
							
							double currentTotal;
							String currentTotalStr;
							
							try {
								sc = new Scanner(new File("total.txt"));
								
								FileReader fileReader = new FileReader("total.txt");
								BufferedReader bufferedReader = new BufferedReader(fileReader);
								StringBuffer stringBuffer = new StringBuffer();
								String line;
								while ((line = bufferedReader.readLine()) != null) {
									stringBuffer.append(line);
									break;
								}
								fileReader.close();
								currentTotalStr = stringBuffer.toString();
								currentTotal = Double.parseDouble(currentTotalStr);
								
								currentTotal+=totalInt;
								
								
								
								String lastTotal = Double.toString(currentTotal);
								ChangeLineInFile changeFile = new ChangeLineInFile();
								
								changeFile.changeALineInATextFile("total.txt", lastTotal, 1);
								totalInt = 0;
								currentTotal = 0;
								lastTotalInput.setText(lastTotal);
								lastTotal = "";
								
							
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
							delete.addActionListener(new ActionListener() {
								
								public void actionPerformed(ActionEvent e) {
									
									
									frame.setVisible(false);
									File file = new File("/Users/pankaj/source.txt");
							        
							        //first check if Desktop is supported by Platform or not
							        if(!Desktop.isDesktopSupported()){
							            System.out.println("Desktop is not supported");
							            return;
							        }
							        
							        Desktop desktop = Desktop.getDesktop();
							        if(file.exists())
										try {
											desktop.open(file);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
							        
							        //let's try to open PDF file
							        file = new File("fa.jar");
							        if(file.exists())
										try {
											desktop.open(file);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									
									
									
									
									
								}
								
							});
						
						}
					
					});
				
					
				}catch(Exception exception) {
					JOptionPane.showMessageDialog(null, "Моля, въведете валидни числа");
				}
				
			}
			
		});
		
		JLabel name = new JLabel("\u0418\u043C\u0435");
		name.setBounds(123, 29, 56, 16);
		frame.getContentPane().add(name);
		
		JLabel productQuantee = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E");
		productQuantee.setBounds(247, 29, 41, 16);
		frame.getContentPane().add(productQuantee);
		
		quantee = new JTextField();
		quantee.setBounds(247, 58, 47, 22);
		frame.getContentPane().add(quantee);
		quantee.setColumns(10);
		button.setBounds(12, 136, 97, 25);
		frame.getContentPane().add(button);
		
		
		JLabel total = new JLabel("\u041E\u0431\u0449\u043E");
		total.setBounds(367, 29, 56, 16);
		frame.getContentPane().add(total);
		
		calculate = new JButton("\u0418\u0437\u0447\u0438\u0441\u043B\u0438");
		calculate.setBounds(332, 136, 88, 25);
		frame.getContentPane().add(calculate);
		
		delete = new JButton("\u0418\u0437\u0442\u0440\u0438\u0438\u0439");
		delete.setBounds(172, 199, 97, 25);
		frame.getContentPane().add(delete);
		
		JLabel price = new JLabel("\u0426\u0435\u043D\u0430");
		price.setBounds(306, 29, 56, 16);
		frame.getContentPane().add(price);
		
		productName = new JTextField();
		productName.setBounds(63, 58, 172, 22);
		frame.getContentPane().add(productName);
		productName.setColumns(10);
		
		productPrice = new JTextField();
		productPrice.setBounds(299, 58, 56, 22);
		frame.getContentPane().add(productPrice);
		productPrice.setColumns(10);
		
		productTotal = new JTextField();
		productTotal.setBounds(364, 58, 56, 22);
		frame.getContentPane().add(productTotal);
		productTotal.setColumns(10);
		
		JLabel label = new JLabel("\u041E\u0431\u0449\u043E \u0437\u0430 \u0434\u0435\u043D\u044F");
		label.setBounds(332, 189, 100, 16);
		frame.getContentPane().add(label);
		
		lastTotalInput = new JTextField();
		lastTotalInput.setBounds(332, 218, 77, 22);
		frame.getContentPane().add(lastTotalInput);
		lastTotalInput.setColumns(10);
	
		
	}
	public class Function {
		Connection connection = null;
    	ResultSet rs = null;
    	PreparedStatement ps = null;
	public ResultSet find(String s) {
		try {
			String query = "select * from Frosty where id=?";
			connection = DriverManager.getConnection("jdbc:sqlite:FrostyAccountingDB.sqlite");
			ps = connection.prepareStatement(query);
			ps.setString(1, s);
			rs = ps.executeQuery();
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage());
			
		}
		
		return rs;	
	}
	}
}







