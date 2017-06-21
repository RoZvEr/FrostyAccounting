
package com.frostyllc.frostyaccounting;
import java.sql.*;
import javax.swing.*;
public class SQLiteConnection {
	
	Connection conn = null;
	
	public static Connection dbConnector() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:FrostyAccountingDB.sqlite");
			return conn;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "No Database!");
			return null;
		}
		
	}

}
