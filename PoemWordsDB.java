package PoemWords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Creates the database with table
 * Do not run unless you need a new table
 * 
 *  @author Jean Dalmont 
 */
public class PoemWordsDB {

	/**
	 * Main method
	 * @param not used
	 *
	 */
	public static void main(String[] args) {
		Connection c = null;

		Statement stmt = null;

		try {

			Class.forName("org.sqlite.JDBC");

			c = DriverManager.getConnection("jdbc:sqlite:wordOccurrences.db");

			System.out.println("Database Opened...\n");

			stmt = c.createStatement();

			//				String sql = "CREATE TABLE Word " +
			//				"( myWords TEXT, " +
			//				" count INTEGER) " ;
			//		
			//				stmt.executeUpdate(sql);

			stmt.close();

			c.close();

		}

		catch ( Exception e ) {

			System.err.println( e.getClass().getName() + ": " + e.getMessage() );

			System.exit(0);

		}

		System.out.println("Table Product Created Successfully!!!");

	}
}

